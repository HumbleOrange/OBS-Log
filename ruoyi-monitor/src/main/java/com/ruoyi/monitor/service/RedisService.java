package com.ruoyi.monitor.service;

import com.ruoyi.monitor.domain.Log;
import com.ruoyi.monitor.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedisService {

    public static final String REDIS_WRITE_PREFIX = "monitor:redis_write:";
    public static final String REDIS_READ_PREFIX = "monitor:redis_read:";
    public static final long EXPIRED_TIME = 600L;

    @Autowired
    private RedisUtil redisUtil;

    public boolean insertToWrite(Log log) {
        return redisUtil.lSet(REDIS_WRITE_PREFIX + log.getTrackId(), log, EXPIRED_TIME);
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

    public boolean insertToRead(String trackId, List<Log> logs) {
        Map<String, Object> map = new HashMap<>();
        for (Log log: logs) {
            map.put(log.getId(), log);
        }
        return redisUtil.hmset(REDIS_READ_PREFIX + trackId, map, EXPIRED_TIME);
    }

    public Log getFromRead(String trackId, String id) {
        return (Log) redisUtil.hget(REDIS_READ_PREFIX + trackId, id);
    }
}
