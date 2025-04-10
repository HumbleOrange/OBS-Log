package com.ruoyi.monitor.store;

import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.monitor.domain.Log;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Service
public class Store {

    public void store(Log log) {

    }

    public void submit(HttpServletRequest request, String level, String message, String exception, String type, Class clazz) {
        Log log = new Log();
        String trackId = (String) request.getAttribute("trackId");
        if(trackId == null || trackId.isEmpty()) {
            trackId = UUID.randomUUID().toString();
            request.setAttribute("trackId", trackId);
        }
        log.setLevel(level);
        if(clazz != null) {
            log.setGroup(clazz.getName());
        }
        log.setMessage(message);
        log.setType(type);
        log.setAddress(request.getRemoteAddr());
        log.setContext(getRequestBody(request));
        log.setBusinessId(request.getParameter("businessId"));
        String username = request.getParameter("username");
        log.setOwner(username == null ? "admin" : username);
        log.setException(exception);
        store(log);
    }

    public void submit(HttpServletRequest request, String level, String message) {
        submit(request, level, message, null, null, null);
    }

    public void submit(HttpServletRequest request, String level, String message, String exception) {
        submit(request,level, message, exception, null, null);
    }

    public void submit(HttpServletRequest request, String level, String message, String exception, String type) {
        submit(request,level, message, exception, type, null);
    }

    public void submit(HttpServletRequest request, String level, String message, Class clazz) {
        submit(request, level, message, null, null, clazz);
    }

    public void submit(HttpServletRequest request, String level, String message, String exception, Class clazz) {
        submit(request, level, message, exception, null, clazz);
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