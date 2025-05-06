package com.ruoyi.monitor.controller;

import com.ruoyi.monitor.core.LogService;
import com.ruoyi.monitor.domain.Index;
import com.ruoyi.monitor.domain.Log;
import com.ruoyi.monitor.domain.QLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitor/track")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/find-span")
    public List<Index> findSpan(@ModelAttribute QLog qLog) {
        return logService.findSpan(qLog);
    }

    @GetMapping("/find-log")
    public Log findLog(@RequestParam String trackId, @RequestParam String id) {
        return logService.findLog(trackId, id);
    }
}
