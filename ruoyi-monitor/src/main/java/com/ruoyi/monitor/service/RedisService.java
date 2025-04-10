package com.ruoyi.monitor.service;

import com.ruoyi.monitor.domain.Log;
import com.ruoyi.monitor.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    public static final String REDIS_WRITE_PREFIX = "monitor:redis_write:";
    public static final String REDIS_READ_PREFIX = "monitor:redis_read:";

    @Autowired
    private RedisUtil redisUtil;

    public boolean insert(Log log) {
        return redisUtil.lSet(REDIS_WRITE_PREFIX + log.getTrackId(), log);
    }
}
