package com.ruoyi.monitor.mapper;

import com.ruoyi.monitor.domain.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    @Insert("INSERT INTO monitor_index VALUES(#{id}, #{trackId}, #{time}, #{level}, #{group}, #{type}, #{businessId}})")
    int insert(Log log);

    @Insert("INSERT INTO monitor_log VALUES(#{id}, #{trackId}, #{time}, #{level}, #{group}, #{message}, #{type}, #{address}, #{context}, #{businessId}, #{owner}, #{exception})")
    int insertLog(Log log);

    @Insert("INSERT IGNORE INTO monitor_position VALUES(#{trackId}, 0")
    int insertPosition(Log log);

    @Insert("INSERT INTO monitor_time VALUES(#{trackId}, #{time}")
    int insertTime(Log log);

}
