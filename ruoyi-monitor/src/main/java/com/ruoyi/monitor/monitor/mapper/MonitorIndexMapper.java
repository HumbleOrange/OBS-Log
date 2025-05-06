package com.ruoyi.monitor.monitor.mapper;

import java.util.List;
import com.ruoyi.monitor.monitor.domain.MonitorIndex;

/**
 * 日志索引Mapper接口
 * 
 * @author HumbleOrange
 * @date 2025-05-07
 */
public interface MonitorIndexMapper 
{
    /**
     * 查询日志索引
     * 
     * @param id 日志索引主键
     * @return 日志索引
     */
    public MonitorIndex selectMonitorIndexById(String id);

    /**
     * 查询日志索引列表
     * 
     * @param monitorIndex 日志索引
     * @return 日志索引集合
     */
    public List<MonitorIndex> selectMonitorIndexList(MonitorIndex monitorIndex);

    /**
     * 新增日志索引
     * 
     * @param monitorIndex 日志索引
     * @return 结果
     */
    public int insertMonitorIndex(MonitorIndex monitorIndex);

    /**
     * 修改日志索引
     * 
     * @param monitorIndex 日志索引
     * @return 结果
     */
    public int updateMonitorIndex(MonitorIndex monitorIndex);

    /**
     * 删除日志索引
     * 
     * @param id 日志索引主键
     * @return 结果
     */
    public int deleteMonitorIndexById(String id);

    /**
     * 批量删除日志索引
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMonitorIndexByIds(String[] ids);
}
