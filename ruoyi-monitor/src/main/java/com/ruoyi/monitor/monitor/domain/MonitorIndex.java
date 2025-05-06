package com.ruoyi.monitor.monitor.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 日志索引对象 monitor_index
 * 
 * @author HumbleOrange
 * @date 2025-05-07
 */
public class MonitorIndex extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 跟踪号 */
    @Excel(name = "跟踪号")
    private String trackId;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /** 日志等级 */
    @Excel(name = "日志等级")
    private String level;

    /** 日志类型 */
    @Excel(name = "日志类型")
    private String type;

    /** 业务键 */
    @Excel(name = "业务键")
    private String businessId;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setTrackId(String trackId) 
    {
        this.trackId = trackId;
    }

    public String getTrackId() 
    {
        return trackId;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setBusinessId(String businessId) 
    {
        this.businessId = businessId;
    }

    public String getBusinessId() 
    {
        return businessId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("trackId", getTrackId())
            .append("time", getTime())
            .append("level", getLevel())
            .append("type", getType())
            .append("businessId", getBusinessId())
            .toString();
    }
}
