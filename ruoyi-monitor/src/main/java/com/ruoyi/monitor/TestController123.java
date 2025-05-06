package com.ruoyi.monitor;

import com.ruoyi.monitor.core.LogService;
import com.ruoyi.monitor.domain.Index;
import com.ruoyi.monitor.domain.Log;
import com.ruoyi.monitor.domain.QLog;
import com.ruoyi.monitor.service.ObsService;
import com.ruoyi.monitor.service.RedisService;
import com.ruoyi.monitor.store.Store;
import com.ruoyi.monitor.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/monitor/test")
public class TestController123 {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ObsService obsService;

    @Autowired
    private Store store;

    @Autowired
    private Task task;

    @PostMapping("/lset")
    public boolean redisInsert(HttpServletRequest request, @RequestBody Log log) {
        boolean succeed = redisService.insertToWrite(log);
        return succeed;
    }

    @GetMapping("/lget")
    public List<Log> redisGet(@RequestParam String trackId) {
        return redisService.getFromWrite(trackId);
    }

    @PostMapping("/test")
    public boolean test() {
        store.submit("DEBUG", "方法开始调用", null, "测试", this.getClass());
        store.submit("DEBUG", "方法调用中", null, "测试", this.getClass());
        store.submit("DEBUG", "方法结束调用", null, "测试", this.getClass());
        return true;
    }

    @PostMapping("/obs-put")
    public boolean obsPut(@RequestParam String key, @RequestParam String content) {
        //obsService.put(key, content);
        return true;
    }

    @GetMapping("/obs-get")
    public List<Log> obsGet(@RequestParam String key) {
        return obsService.get(key);
    }

    @GetMapping("/obs-del")
    public boolean obsDel(@RequestParam String key) {
        obsService.delete(key);
        return true;
    }

    @GetMapping("/transfer")
    public void transfer() {
        task.transferFromRedisToObs();
    }

    @GetMapping("/delete")
    public void deleteExpiredLogs() {
        task.deleteExpiredLogs();
    }
}
