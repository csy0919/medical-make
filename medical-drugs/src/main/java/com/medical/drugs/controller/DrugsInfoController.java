package com.medical.drugs.controller;

import com.medical.common.core.controller.BaseController;
import com.medical.common.core.domain.AjaxResult;
import com.medical.common.core.page.TableDataInfo;
import com.medical.drugs.domain.DrugsInfo;
import com.medical.drugs.domain.DrugsMakers;
import com.medical.drugs.domain.Makers;
import com.medical.drugs.service.DrugsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/12 21:02
 */
@RestController
@RequestMapping("/drugs/drug")
public class DrugsInfoController extends BaseController {

    @Autowired
    private DrugsInfoService drugsInfoService;

    @PreAuthorize("@ss.hasPermi('drugs:drug:list')")
    @GetMapping("/index")
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
    public AjaxResult delDrug(@PathVariable Long[] drugIds){
        return toAjax(drugsInfoService.delDrug(drugIds));
    }
}
