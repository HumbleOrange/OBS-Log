package com.ruoyi.monitor.service;

import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class ObsService {

    public static final String BUCKET_NAME = "obs-log-service";

    @Autowired
    private ObsClient obsClient;

    public void put(String key, String content) {
        obsClient.putObject(BUCKET_NAME, key, new ByteArrayInputStream(content.getBytes()));
    }

    public String get(String key) {
        ObsObject obsObject = obsClient.getObject(BUCKET_NAME, key);
        InputStream input = obsObject.getObjectContent();
        try {
            return readAllBytes(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(String key) {
        obsClient.deleteObject(BUCKET_NAME, key);
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
