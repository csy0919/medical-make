package com.medical.drugs.controller;

import com.medical.common.annotation.Log;
import com.medical.common.core.controller.BaseController;
import com.medical.common.core.domain.AjaxResult;
import com.medical.common.core.page.TableDataInfo;
import com.medical.common.core.redis.RedisCache;
import com.medical.common.enums.BusinessType;
import com.medical.drugs.domain.Vendor;
import com.medical.drugs.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/15 14:52
 */
@RestController
@RequestMapping("/drugs/vendor")
public class VendorController extends BaseController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询供应商信息列表
     */
    @PreAuthorize("@ss.hasPermi('drugs:vendor:list')")
    @GetMapping("/index")
    public TableDataInfo list(Vendor vendor)
    {
        startPage();
        List<Vendor> list = vendorService.selectVendorList(vendor);
        return getDataTable(list);
    }


    /**
     * 获取供应商信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('drugs:vendor:query')")
    @GetMapping(value = "/{vendorId}")
    public AjaxResult getInfo(@PathVariable Long vendorId)
    {
        return success(vendorService.selectVendorByVendorId(vendorId));
    }

    /**
     * 新增供应商信息
     */
    @PreAuthorize("@ss.hasPermi('drugs:vendor:add')")
    @Log(title = "供应商信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Vendor vendor)
    {
        return toAjax(vendorService.insertVendor(vendor));
    }

    /**
     * 修改供应商信息
     */
    @PreAuthorize("@ss.hasPermi('drugs:vendor:upd')")
    @Log(title = "供应商信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Vendor vendor)
    {
        return toAjax(vendorService.updateVendor(vendor));
    }

    /**
     * 删除供应商信息
     */
    @PreAuthorize("@ss.hasPermi('drugs:vendor:remove')")
    @Log(title = "供应商信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{vendorIds}")
    public AjaxResult remove(@PathVariable Long[] vendorIds)
    {
        return toAjax(vendorService.deleteVendorByVendorIds(vendorIds));
    }
}
