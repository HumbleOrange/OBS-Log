package com.ruoyi.monitor.task;

import com.ruoyi.monitor.domain.Log;
import com.ruoyi.monitor.mapper.LogMapper;
import com.ruoyi.monitor.service.ObsService;
import com.ruoyi.monitor.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class Task {

    public static final int ONCE_FIND_LIMIT = 1000;

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ObsService obsService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void transferFromRedisToObs() {
        List<String> trackIds = logMapper.getTrackIdForTransfer(ONCE_FIND_LIMIT);
        while (!trackIds.isEmpty()) {
            for (String trackId: trackIds) {
                transfer(trackId);
            }
            trackIds = logMapper.getTrackIdForTransfer(ONCE_FIND_LIMIT);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    @Transactional
    //需要完善 1.logs为空 2.删除不存在的trackId
    public void transfer(String trackId) {
        List<Log> logs = redisService.getFromWrite(trackId);
        if (!logs.isEmpty()) {
            obsService.put(trackId, logs);
        }
        logMapper.changePosition(trackId);
        logMapper.deleteTime(trackId);
        redisService.delete(trackId);
    }
}
