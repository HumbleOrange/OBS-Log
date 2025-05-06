package com.ruoyi.web.controller.pill;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.monitor.store.Store;
import com.ruoyi.pill.domain.PillFactory;
import com.ruoyi.pill.service.IPillFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 生产厂家信息Controller
 * 
 * @author xiaoniu
 * @date 2023-06-29
 */
@RestController
@RequestMapping("/pill/factory")
public class PillFactoryController extends BaseController
{
    @Autowired
    private IPillFactoryService pillFactoryService;

    @Autowired
    private Store store;

    /**
     * 查询生产厂家信息列表
     */
    @PreAuthorize("@ss.hasPermi('pill:factory:list')")
    @GetMapping("/list")
    public TableDataInfo list(PillFactory pillFactory)
    {
        store.submit("INFO", "开始查询生产厂家信息列表", null, "查询", this.getClass());
        store.submit("INFO", "生产厂家信息分页", null, "查询", this.getClass());
        startPage();
        List<PillFactory> list = pillFactoryService.selectPillFactoryList(pillFactory);
        store.submit("INFO", "结束查询生产厂家信息列表", null, "查询", this.getClass());
        return getDataTable(list);
    }

    /**
     * 导出生产厂家信息列表
     */
    @PreAuthorize("@ss.hasPermi('pill:factory:export')")
    @Log(title = "生产厂家信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PillFactory pillFactory)
    {
        List<PillFactory> list = pillFactoryService.selectPillFactoryList(pillFactory);
        ExcelUtil<PillFactory> util = new ExcelUtil<PillFactory>(PillFactory.class);
        util.exportExcel(response, list, "生产厂家信息数据");
    }

    /**
     * 获取生产厂家信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('pill:factory:query')")
    @GetMapping(value = "/{factoryId}")
    public AjaxResult getInfo(@PathVariable("factoryId") Long factoryId)
    {
        store.submit("INFO", "开始获取生产厂家信息详细信息", null, "查询", this.getClass());
        store.submit("INFO", "结束获取生产厂家信息详细信息", null, "查询", this.getClass());
        return success(pillFactoryService.selectPillFactoryByFactoryId(factoryId));
    }

    /**
     * 新增生产厂家信息
     */
    @PreAuthorize("@ss.hasPermi('pill:factory:add')")
    @Log(title = "生产厂家信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PillFactory pillFactory)
    {
        store.submit("INFO", "开始新增生产厂家信息", null, "新增", this.getClass());
        store.submit("INFO", "结束新增生产厂家信息", null, "新增", this.getClass());
        return toAjax(pillFactoryService.insertPillFactory(pillFactory));
    }

    /**
     * 修改生产厂家信息
     */
    @PreAuthorize("@ss.hasPermi('pill:factory:edit')")
    @Log(title = "生产厂家信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PillFactory pillFactory)
    {
        store.submit("INFO", "开始修改生产厂家信息", null, "修改", this.getClass());
        store.submit("INFO", "结束修改生产厂家信息", null, "修改", this.getClass());
        return toAjax(pillFactoryService.updatePillFactory(pillFactory));
    }

    /**
     * 删除生产厂家信息
     */
    @PreAuthorize("@ss.hasPermi('pill:factory:remove')")
    @Log(title = "生产厂家信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{factoryIds}")
    public AjaxResult remove(@PathVariable Long[] factoryIds)
    {
        store.submit("INFO", "开始删除生产厂家信息", null, "删除", this.getClass());
        store.submit("INFO", "结束删除生产厂家信息", null, "删除", this.getClass());
        return toAjax(pillFactoryService.deletePillFactoryByFactoryIds(factoryIds));
    }
}
