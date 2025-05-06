package com.ruoyi.monitor.core;

import com.ruoyi.monitor.domain.Index;
import com.ruoyi.monitor.domain.Log;
import com.ruoyi.monitor.domain.QLog;
import com.ruoyi.monitor.mapper.LogMapper;
import com.ruoyi.monitor.service.ObsService;
import com.ruoyi.monitor.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LogService {

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ObsService obsService;

    public void insertLog(Log log) {
        redisService.insertToWrite(log);
        logMapper.insertIndex(log);
        logMapper.insertPosition(log);
        logMapper.insertTime(log);
        logMapper.insertLog(log);
    }

    public void insertLogs(ArrayList<Log> logs) {
        for(Log log: logs) {
            insertLog(log);
        }
    }

    public List<Index> findSpan(QLog qLog) {
        List<Index> indices = logMapper.getIndex(qLog);
        Set<String> trackIds = new HashSet<>();
        for (Index index: indices) {
            trackIds.add(index.getTrackId());
        }
        for (String trackId: trackIds) {
            transferToRead(trackId);
        }
        return indices;
    }

    public Log findLog(String trackId, String id) {
        Log log = redisService.getFromRead(trackId, id);
        if (log == null) {
            transferToRead(trackId);
            log = redisService.getFromRead(trackId, id);
        }
        return log;
    }

    private void transferToRead(String trackId) {
        Integer position = logMapper.getPosition(trackId);
        if (position != null) {
            List<Log> logs;
            if (position == 0) {
                logs = redisService.getFromWrite(trackId);
                if (logs.isEmpty()) {
                    logs = obsService.get(trackId);
                }
            } else {
                logs = obsService.get(trackId);
            }

            if (!logs.isEmpty()) {
                redisService.insertToRead(trackId, logs);
            }
        }
    }
}
