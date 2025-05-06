package com.ruoyi.monitor.store;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.monitor.core.LogService;
import com.ruoyi.monitor.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Service
public class Store {

    @Autowired
    private LogService logService;

    public void store(Log log) {
        logService.insertLog(log);
    }

    public void submit(String level, String message, String exception, String type, Class clazz) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return;
        }

        HttpServletRequest request = attrs.getRequest();
        Log log = new Log();
        String trackId = (String) request.getAttribute("trackId");
        if(trackId == null || trackId.isEmpty()) {
            trackId = UUID.randomUUID().toString();
            request.setAttribute("trackId", trackId);
        }
        log.setTrackId(trackId);
        log.setLevel(level);
        if(clazz != null) {
            log.setGroup(clazz.getName());
        }
        log.setMessage(message);
        log.setType(type);
        log.setAddress(request.getRemoteAddr());
        String requestBody = getRequestBody(request);
        log.setContext(requestBody);
        JSONObject jsonObject = JSON.parseObject(requestBody);
        String businessId = jsonObject.getString("businessId");
        if(businessId == null) {
            businessId = request.getParameter("businessId");
        }
        log.setBusinessId(businessId);
        String username = jsonObject.getString("username");
        if(username == null) {
            username = request.getParameter("username");
        }
        log.setOwner(username == null ? "admin" : username);
        log.setException(exception);
        store(log);
    }

    public void submit(String level, String message) {
        submit(level, message, null, null, null);
    }

    public void submit(String level, String message, String exception) {
        submit(level, message, exception, null, null);
    }

    public void submit(String level, String message, String exception, String type) {
        submit(level, message, exception, type, null);
    }

    public void submit(String level, String message, Class clazz) {
        submit(level, message, null, null, clazz);
    }

    public void submit(String level, String message, String exception, Class clazz) {
        submit(level, message, exception, null, clazz);
    }

    public String getRequestBody(HttpServletRequest request) {

        String context = (String) request.getAttribute("context");
        if(context != null) {
            return context;
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        context = sb.toString();
        request.setAttribute("context", context);
        return context;
    }

}