package com.ruoyi.monitor.domain;

import com.ruoyi.common.utils.uuid.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QLog {

    private String trackId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String level;
    private String group;
    private String type;
    private String businessId;
}
