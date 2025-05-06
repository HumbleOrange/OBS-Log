package com.ruoyi.monitor.service;

import com.alibaba.fastjson2.JSON;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ObsObject;
import com.ruoyi.monitor.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
public class ObsService {

    public static final String BUCKET_NAME = "obs-log-service";

    @Autowired
    private ObsClient obsClient;

    public void put(String key, List<Log> logs) {
        try {
            String content = JSON.toJSONString(logs);
            obsClient.putObject(BUCKET_NAME, key, new ByteArrayInputStream(content.getBytes()));
        } catch (ObsException e) {
            e.printStackTrace();
        }
    }

    public List<Log> get(String key) {
        try {
            ObsObject obsObject = obsClient.getObject(BUCKET_NAME, key);
            InputStream input = obsObject.getObjectContent();
            String jsonStr = readAllBytes(input);
            return JSON.parseArray(jsonStr, Log.class);
        } catch (ObsException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void delete(String key) {
        try {
            obsClient.deleteObject(BUCKET_NAME, key);
        } catch (ObsException e) {
            e.printStackTrace();
        }
    }

    private String readAllBytes(InputStream input) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }
}
