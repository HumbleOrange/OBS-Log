package com.ruoyi.monitor;

import com.ruoyi.monitor.domain.Log;
import com.ruoyi.monitor.service.RedisService;
import com.ruoyi.monitor.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/monitor/test")
public class TestController123 {

    @Autowired
    private RedisService redisService;

    @Autowired
    private Store store;

    @PostMapping("/lset")
    public boolean redisInsert(HttpServletRequest request, @RequestBody Log log) {
        store.submit(request, "INFO", "方法开始调用", null, "测试", this.getClass());
        boolean succeed = redisService.insert(log);
        store.submit(request, "INFO", "方法结束调用", null, "测试", this.getClass());
        return succeed;
    }
}
