package com.ruoyi.monitor.mapper;

import com.ruoyi.monitor.domain.Index;
import com.ruoyi.monitor.domain.Log;
import com.ruoyi.monitor.domain.QLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogMapper {

    @Insert("INSERT INTO monitor_index VALUES(#{id}, #{trackId}, #{time}, #{level}, #{type}, #{businessId})")
    int insertIndex(Log log);

    @Insert("INSERT INTO monitor_log VALUES(#{id}, #{trackId}, #{time}, #{level}, #{group}, #{message}, #{type}, #{address}, #{context}, #{businessId}, #{owner}, #{exception})")
    int insertLog(Log log);

    @Insert("INSERT IGNORE INTO monitor_position VALUES(#{trackId}, 0)")
    int insertPosition(Log log);

    @Insert("INSERT IGNORE INTO monitor_time VALUES(#{trackId}, #{time})")
    int insertTime(Log log);

    List<String> getTrackIdForTransfer(int limit);

    @Update("UPDATE monitor_position SET position = 1 WHERE track_id = #{trackId}")
    int changePosition(String trackId);

    @Delete("DELETE FROM monitor_time WHERE track_id = #{trackId}")
    int deleteTime(String trackId);

    @Select("SELECT position FROM monitor_position WHERE track_id = #{trackId}")
    Integer getPosition(String trackId);

    List<Index> getIndex(QLog qLog);
}
