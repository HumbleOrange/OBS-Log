package com.ruoyi.monitor.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Index {

    private String id;
    private String trackId;
    private Timestamp time;
    private String level;
    private String type;
    private String businessId;
}
