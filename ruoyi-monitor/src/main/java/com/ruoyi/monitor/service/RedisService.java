package com.ruoyi.monitor.service;

import com.ruoyi.monitor.domain.Log;
import com.ruoyi.monitor.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisService {

    public static final String REDIS_WRITE_PREFIX = "monitor:redis_write:";
    public static final String REDIS_READ_PREFIX = "monitor:redis_read:";

    @Autowired
    private RedisUtil redisUtil;

    public boolean insertToWrite(Log log) {
        return redisUtil.lSet(REDIS_WRITE_PREFIX + log.getTrackId(), log);
    }

    public List<Log> getFromWrite(String trackId) {
        List<Object> logs = redisUtil.lGet(REDIS_WRITE_PREFIX + trackId, 0, -1);
        List<Log> results = new ArrayList<>();
        for (Object log: logs) {
            results.add((Log) log);
        }
        return results;
    }

    public void delete(String trackId) {
        redisUtil.del(REDIS_WRITE_PREFIX + trackId);
    }
}
