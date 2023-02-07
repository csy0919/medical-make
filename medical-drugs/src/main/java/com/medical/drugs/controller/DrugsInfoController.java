package com.medical.drugs.controller;


import com.medical.common.core.controller.BaseController;
import com.medical.common.core.domain.AjaxResult;
import com.medical.common.core.page.TableDataInfo;
import com.medical.drugs.domain.DrugsInfo;
import com.medical.drugs.service.DrugsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/12 21:02
 */
@Api(tags = "药品信息接口")
@RestController
@RequestMapping("/drugs/drug")
public class DrugsInfoController extends BaseController {

    @Autowired
    private DrugsInfoService drugsInfoService;

    @PreAuthorize("@ss.hasPermi('drugs:drug:list')")
    @GetMapping("/index")
    @ApiOperation(value = "查询药品信息列表")
    public TableDataInfo selectDrugsInfoList(DrugsInfo drugsInfo){
        startPage();
        List<DrugsInfo> list = drugsInfoService.selectDrugsInfoList(drugsInfo);
        return getDataTable(list);
    }

    /**
     * 通过药品id查询药品信息
     * @param drugId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('drugs:drug:query')")
    @GetMapping(value = "/{drugId}")
    @ApiOperation(value = "通过药品id查询药品信息")
    public AjaxResult selectDrug(@PathVariable Long drugId){
        return success(drugsInfoService.selectDrug(drugId));
    }

    /**
     * 新增药品信息
     * @param drugsInfo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('drugs:drug:add')")
    @PostMapping
    @ApiOperation(value = "新增药品信息")
    public AjaxResult addDrug(@Validated @RequestBody DrugsInfo drugsInfo){
        drugsInfo.setCreateBy(getUsername());
        return toAjax(drugsInfoService.addDrug(drugsInfo));
    }

    /**
     * 修改药品信息
     * @param drugsInfo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('drugs:drug:upd')")
    @PutMapping
    @ApiOperation(value = "修改药品信息")
    public AjaxResult updateMakers(@Validated @RequestBody DrugsInfo drugsInfo){
        drugsInfo.setUpdateBy(getUsername());
        return toAjax(drugsInfoService.updateDrug(drugsInfo));
    }

    /**
     * 根据药品ID批量删除药品信息
     * @param drugIds
     * @return
     */
    @PreAuthorize("@ss.hasPermi('drugs:drug:remove')")
    @DeleteMapping("/{drugIds}")
    @ApiOperation(value = "删除药品信息")
    public AjaxResult delDrug(@PathVariable Long[] drugIds){
        List<DrugsInfo> list =drugsInfoService.selectDrugs(drugIds);
        for (DrugsInfo drugsInfo:list) {
            drugsInfo.setCreateBy(getUsername());
            drugsInfoService.addDrugsDel(drugsInfo);
        }
        return toAjax(drugsInfoService.delDrug(drugIds));
    }

    @PreAuthorize("@ss.hasPermi('drugs:drug:import')")
    @GetMapping("/del/list")
    @ApiOperation(value = "药品删除表列表信息")
    public TableDataInfo selectDrugsDelList(DrugsInfo drugsInfo){
        startPage();
        List<DrugsInfo> list = drugsInfoService.selectDrugsDelList(drugsInfo);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('drugs:drug:import')")
    @PostMapping("/importDrug")
    @ApiOperation(value = "从删除表中导入药品信息")
    public AjaxResult importDrug(Long[] drugIds)
    {
        List<DrugsInfo> list = drugsInfoService.selectDrugsDelById(drugIds);
        for (DrugsInfo drugsInfo:list) {
            drugsInfo.setCreateBy(getUsername());
            drugsInfoService.addDrugId(drugsInfo);
        }
        drugsInfoService.delDrugDel(drugIds);
        return success();
    }



}
