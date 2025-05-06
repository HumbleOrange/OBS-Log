package com.ruoyi.monitor.monitor.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.monitor.monitor.mapper.MonitorIndexMapper;
import com.ruoyi.monitor.monitor.domain.MonitorIndex;
import com.ruoyi.monitor.monitor.service.IMonitorIndexService;

/**
 * 日志索引Service业务层处理
 * 
 * @author HumbleOrange
 * @date 2025-05-07
 */
@Service
public class MonitorIndexServiceImpl implements IMonitorIndexService 
{
    @Autowired
    private MonitorIndexMapper monitorIndexMapper;

    /**
     * 查询日志索引
     * 
     * @param id 日志索引主键
     * @return 日志索引
     */
    @Override
    public MonitorIndex selectMonitorIndexById(String id)
    {
        return monitorIndexMapper.selectMonitorIndexById(id);
    }

    /**
     * 查询日志索引列表
     * 
     * @param monitorIndex 日志索引
     * @return 日志索引
     */
    @Override
    public List<MonitorIndex> selectMonitorIndexList(MonitorIndex monitorIndex)
    {
        return monitorIndexMapper.selectMonitorIndexList(monitorIndex);
    }

    /**
     * 新增日志索引
     * 
     * @param monitorIndex 日志索引
     * @return 结果
     */
    @Override
    public int insertMonitorIndex(MonitorIndex monitorIndex)
    {
        return monitorIndexMapper.insertMonitorIndex(monitorIndex);
    }

    /**
     * 修改日志索引
     * 
     * @param monitorIndex 日志索引
     * @return 结果
     */
    @Override
    public int updateMonitorIndex(MonitorIndex monitorIndex)
    {
        return monitorIndexMapper.updateMonitorIndex(monitorIndex);
    }

    /**
     * 批量删除日志索引
     * 
     * @param ids 需要删除的日志索引主键
     * @return 结果
     */
    @Override
    public int deleteMonitorIndexByIds(String[] ids)
    {
        return monitorIndexMapper.deleteMonitorIndexByIds(ids);
    }

    /**
     * 删除日志索引信息
     * 
     * @param id 日志索引主键
     * @return 结果
     */
    @Override
    public int deleteMonitorIndexById(String id)
    {
        return monitorIndexMapper.deleteMonitorIndexById(id);
    }
}
