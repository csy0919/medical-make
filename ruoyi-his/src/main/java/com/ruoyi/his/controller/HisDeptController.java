package com.ruoyi.his.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.his.domain.HisDept;
import com.ruoyi.his.service.HisDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/1 21:00
 */
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
    public AjaxResult remove(@PathVariable Long[] deptIds)
    {
        return toAjax(hisDeptService.removeByDeptIds(deptIds));
    }
}
