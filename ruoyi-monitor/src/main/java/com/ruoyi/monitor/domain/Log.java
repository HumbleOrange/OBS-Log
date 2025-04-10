package com.ruoyi.monitor.domain;

import com.ruoyi.common.utils.uuid.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    private String id = UUID.randomUUID().toString();
    private String trackId;
    private Timestamp time = Timestamp.from(Instant.now());
    private String level;
    private String group;
    private String message;
    private String type;
    private String address;
    private String context;
    private String businessId;
    private String owner;
    private String exception;
}
