package com.ruoyi.monitor.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.monitor.core.LogService;
import com.ruoyi.monitor.domain.Index;
import com.ruoyi.monitor.domain.Log;
import com.ruoyi.monitor.domain.QLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitor/track")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @GetMapping("/find-span")
    public TableDataInfo findSpan(@ModelAttribute QLog qLog) {
        startPage();
        List<Index> list = logService.findSpan(qLog);
        return getDataTable(list);
    }

    @GetMapping("/find-log")
    public Log findLog(@RequestParam String trackId, @RequestParam String id) {
        return logService.findLog(trackId, id);
    }
}
