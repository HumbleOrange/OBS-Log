package com.ruoyi.monitor.monitor.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.monitor.monitor.domain.MonitorIndex;
import com.ruoyi.monitor.monitor.service.IMonitorIndexService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 日志索引Controller
 * 
 * @author HumbleOrange
 * @date 2025-05-07
 */
@RestController
@RequestMapping("/ruoyi-monitor/index")
public class MonitorIndexController extends BaseController
{
    @Autowired
    private IMonitorIndexService monitorIndexService;

    /**
     * 查询日志索引列表
     */
    @PreAuthorize("@ss.hasPermi('ruoyi-monitor:index:list')")
    @GetMapping("/list")
    public TableDataInfo list(MonitorIndex monitorIndex)
    {
        startPage();
        List<MonitorIndex> list = monitorIndexService.selectMonitorIndexList(monitorIndex);
        return getDataTable(list);
    }

    /**
     * 导出日志索引列表
     */
    @PreAuthorize("@ss.hasPermi('ruoyi-monitor:index:export')")
    @Log(title = "日志索引", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MonitorIndex monitorIndex)
    {
        List<MonitorIndex> list = monitorIndexService.selectMonitorIndexList(monitorIndex);
        ExcelUtil<MonitorIndex> util = new ExcelUtil<MonitorIndex>(MonitorIndex.class);
        util.exportExcel(response, list, "日志索引数据");
    }

    /**
     * 获取日志索引详细信息
     */
    @PreAuthorize("@ss.hasPermi('ruoyi-monitor:index:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(monitorIndexService.selectMonitorIndexById(id));
    }

    /**
     * 新增日志索引
     */
    @PreAuthorize("@ss.hasPermi('ruoyi-monitor:index:add')")
    @Log(title = "日志索引", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MonitorIndex monitorIndex)
    {
        return toAjax(monitorIndexService.insertMonitorIndex(monitorIndex));
    }

    /**
     * 修改日志索引
     */
    @PreAuthorize("@ss.hasPermi('ruoyi-monitor:index:edit')")
    @Log(title = "日志索引", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MonitorIndex monitorIndex)
    {
        return toAjax(monitorIndexService.updateMonitorIndex(monitorIndex));
    }

    /**
     * 删除日志索引
     */
    @PreAuthorize("@ss.hasPermi('ruoyi-monitor:index:remove')")
    @Log(title = "日志索引", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(monitorIndexService.deleteMonitorIndexByIds(ids));
    }
}
