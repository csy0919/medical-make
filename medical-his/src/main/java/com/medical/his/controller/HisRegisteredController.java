package com.medical.his.controller;

import com.medical.common.annotation.Log;
import com.medical.common.constant.UserConstants;
import com.medical.common.core.controller.BaseController;
import com.medical.common.core.domain.AjaxResult;
import com.medical.common.core.page.TableDataInfo;
import com.medical.common.enums.BusinessType;

import com.medical.his.domain.HisRegistered;
import com.medical.his.service.HisRegisteredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Csy
 * @date 2022/12/6 12:00
 */

@RestController
@RequestMapping("his/registered")
public class HisRegisteredController extends BaseController {

    @Autowired
    private HisRegisteredService hisRegisteredService;

    @PreAuthorize("@ss.hasPermi('his:registered:index')")
    @GetMapping("/index")
    public TableDataInfo selectRegisteredList(HisRegistered hisRegistered){
        startPage();
        List<HisRegistered> hisRegisteredList = hisRegisteredService.selectRegisteredList(hisRegistered);
        return getDataTable(hisRegisteredList);
    }

    /**
     *挂号费用设置新增
     * @param hisRegistered
     * @return
     */
    @PreAuthorize("@ss.hasPermi('his:registered:add')")
    @Log(title = "挂号费用设置",businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addRegistered(@Validated @RequestBody HisRegistered hisRegistered) {
        if (UserConstants.NOT_UNIQUE.equals(hisRegisteredService.checkRegisteredNameUnique(hisRegistered)))
        {
            return error("新增检查项目" + hisRegistered.getRegisteredName() + "'失败，项目名称已存在");
        }
        hisRegistered.setCreateBy(getUsername());
        return toAjax(hisRegisteredService.addRegistered(hisRegistered));
    }

    @PreAuthorize("@ss.hasPermi('his:registered:query')")
    @GetMapping(value="/{registeredId}")
    public AjaxResult getInfo(@PathVariable Long registeredId){
        return success(hisRegisteredService.selectByRegisteredId(registeredId));
    }

    @PreAuthorize("@ss.hasPermi('his:registered:upd')")
    @PutMapping
    public AjaxResult updateRegistered(@Validated @RequestBody HisRegistered hisRegistered){
        if (UserConstants.NOT_UNIQUE.equals(hisRegisteredService.checkRegisteredNameUnique(hisRegistered)))
        {
            return error("修改挂号费用名称" + hisRegistered.getRegisteredName() + "'失败，挂号费用名称已存在");
        }
        hisRegistered.setCreateBy(getUsername());
        return toAjax(hisRegisteredService.updateRegistered(hisRegistered));
    }

    @PreAuthorize("@ss.hasPermi('his:registered:remove')")
    @Log(title = "挂号费用设置",businessType = BusinessType.DELETE)
    @DeleteMapping("/{registeredIds}")
    public AjaxResult deleteByRegisteredIds(@Validated @PathVariable Long[] registeredIds){
        return success(hisRegisteredService.delRegistered(registeredIds));
    }
}
