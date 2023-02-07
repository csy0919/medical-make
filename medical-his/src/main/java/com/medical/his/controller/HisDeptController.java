package com.medical.his.controller;

import com.medical.common.annotation.Log;
import com.medical.common.constant.UserConstants;
import com.medical.common.core.controller.BaseController;
import com.medical.common.core.domain.AjaxResult;
import com.medical.common.core.page.TableDataInfo;
import com.medical.common.enums.BusinessType;
import com.medical.his.domain.HisDept;
import com.medical.his.service.HisDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/1 21:00
 */
@Api(tags = "科室信息接口")
@RestController
@RequestMapping("/his/dept")
public class HisDeptController extends BaseController {

    @Autowired
    private HisDeptService hisDeptService;

    /**
     * 查询科室列表
     * @param hisDept
     * @return
     */
    @PreAuthorize("@ss.hasPermi('his:dept:index')")
    @GetMapping("/index")
    @ApiOperation(value="查询科室列表")
    public TableDataInfo queryList(HisDept hisDept)
    {
        startPage();
        List<HisDept> list = hisDeptService.selectDeptList(hisDept);
        return getDataTable(list);
    }

    /**
     * 新增科室
     * @param hisDept
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('his:dept:add')")
    @Log(title = "科室管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value="新增科室")
    public AjaxResult add(@Validated @RequestBody HisDept hisDept )
    {
        if (UserConstants.NOT_UNIQUE.equals(hisDeptService.checkDeptNameUnique(hisDept)))
        {
            return error("新增科室" + hisDept.getDeptName() + "'失败，科室名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(hisDeptService.checkDeptCodeUnique(hisDept)))
        {
            return error("新增科室'" + hisDept.getDeptName() + "'失败，科室编码已存在");
        }
        hisDept.setCreateBy(getUsername());
        return toAjax(hisDeptService.insertHisDept(hisDept));
    }

    /**
     * 修改科室信息
     * @param hisDept
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('his:dept:upd')")
    @Log(title = "科室管理",businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value="修改科室信息")
    public AjaxResult update(@Validated @RequestBody HisDept hisDept){
        if (UserConstants.NOT_UNIQUE.equals(hisDeptService.checkDeptNameUnique(hisDept)))
        {
            return error("修改科室" + hisDept.getDeptName() + "'失败，科室名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(hisDeptService.checkDeptCodeUnique(hisDept)))
        {
            return error("修改科室'" + hisDept.getDeptName() + "'失败，科室编码已存在");
        }
        hisDept.setUpdateBy(getUsername());
        return toAjax(hisDeptService.updateHisDept(hisDept));
    }

    /**
     * 通过科室ID查询科室对象信息
     * @param deptId
     * @return 科室对象信息
     */
    @PreAuthorize("@ss.hasPermi('his:dept:query')")
    @GetMapping(value = "/{deptId}")
    @ApiOperation(value="通过科室ID查询科室对象信息")
    public AjaxResult getInfo(@PathVariable Long deptId)
    {
        return success(hisDeptService.selectByDeptId(deptId));
    }

    /**
     * 批量删除科室信息
     * @param deptIds
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('his:dept:remove')")
    @Log(title = "科室管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptIds}")
    @ApiOperation(value="批量删除科室信息")
    public AjaxResult remove(@PathVariable Long[] deptIds)
    {
        return toAjax(hisDeptService.removeByDeptIds(deptIds));
    }
}
