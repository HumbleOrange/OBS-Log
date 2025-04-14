package com.ruoyi.monitor.core;

import com.ruoyi.monitor.domain.Log;
import com.ruoyi.monitor.mapper.LogMapper;
import com.ruoyi.monitor.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LogService {

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private RedisService redisService;

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

}
