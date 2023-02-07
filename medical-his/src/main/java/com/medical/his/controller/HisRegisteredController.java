package com.medical.his.controller;

import com.medical.common.annotation.Log;
import com.medical.common.constant.UserConstants;
import com.medical.common.core.controller.BaseController;
import com.medical.common.core.domain.AjaxResult;
import com.medical.common.core.page.TableDataInfo;
import com.medical.common.enums.BusinessType;
import com.medical.his.domain.HisRegistered;
import com.medical.his.service.HisRegisteredService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Csy
 * @date 2022/12/6 12:00
 */
@Api(tags = "挂号费用信息接口")
@RestController
@RequestMapping("his/registered")
public class HisRegisteredController extends BaseController {

    @Autowired
    private HisRegisteredService hisRegisteredService;

    /**
     * 查询挂号费用信息列表
     * @param hisRegistered
     * @return
     */
    @PreAuthorize("@ss.hasPermi('his:registered:index')")
    @GetMapping("/index")
    @ApiOperation(value="查询挂号费用信息列表")
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
    @ApiOperation(value="挂号费用设置新增")
    public AjaxResult addRegistered(@Validated @RequestBody HisRegistered hisRegistered) {
        if (UserConstants.NOT_UNIQUE.equals(hisRegisteredService.checkRegisteredNameUnique(hisRegistered)))
        {
            return error("新增检查项目" + hisRegistered.getRegisteredName() + "'失败，项目名称已存在");
        }
        hisRegistered.setCreateBy(getUsername());
        return toAjax(hisRegisteredService.addRegistered(hisRegistered));
    }

    /**
     * 通过Id查询挂号费用信息
     * @param registeredId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('his:registered:query')")
    @GetMapping(value="/{registeredId}")
    @ApiOperation(value="通过Id查询挂号费用信息")
    public AjaxResult getInfo(@PathVariable Long registeredId){
        return success(hisRegisteredService.selectByRegisteredId(registeredId));
    }

    /**
     * 修改挂号费用信息
     * @param hisRegistered
     * @return
     */
    @PreAuthorize("@ss.hasPermi('his:registered:upd')")
    @PutMapping
    @ApiOperation(value="修改挂号费用信息")
    public AjaxResult updateRegistered(@Validated @RequestBody HisRegistered hisRegistered){
        if (UserConstants.NOT_UNIQUE.equals(hisRegisteredService.checkRegisteredNameUnique(hisRegistered)))
        {
            return error("修改挂号费用名称" + hisRegistered.getRegisteredName() + "'失败，挂号费用名称已存在");
        }
        hisRegistered.setCreateBy(getUsername());
        return toAjax(hisRegisteredService.updateRegistered(hisRegistered));
    }

    /**
     * 批量删除挂号费用信息
     * @param registeredIds
     * @return
     */
    @PreAuthorize("@ss.hasPermi('his:registered:remove')")
    @Log(title = "挂号费用设置",businessType = BusinessType.DELETE)
    @DeleteMapping("/{registeredIds}")
    @ApiOperation(value="删除挂号费用信息")
    public AjaxResult deleteByRegisteredIds(@Validated @PathVariable Long[] registeredIds){
        return success(hisRegisteredService.delRegistered(registeredIds));
    }
}
