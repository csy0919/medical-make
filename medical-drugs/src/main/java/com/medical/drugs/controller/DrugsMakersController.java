package com.medical.drugs.controller;

import com.medical.common.core.controller.BaseController;
import com.medical.common.core.domain.AjaxResult;
import com.medical.common.core.page.TableDataInfo;
import com.medical.drugs.domain.DrugsMakers;
import com.medical.drugs.domain.Makers;
import com.medical.drugs.service.DrugsMakersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Csy
 * @date 2022/12/9 12:23
 */
@RestController
@RequestMapping("/drugs/makers")
public class DrugsMakersController extends BaseController {

    @Autowired
    private DrugsMakersService drugsMakersService;

    /**
     * 查询厂家信息列表
     * @param drugsMakers
     * @return
     */
    @PreAuthorize("@ss.hasPermi('drugs:makers:list')")
    @GetMapping("/index")
    public TableDataInfo selectMakersList(DrugsMakers drugsMakers){
        startPage();
        List<DrugsMakers> list = drugsMakersService.selectMakersList(drugsMakers);
        return getDataTable(list);
    }

    @GetMapping("/makers")
    public TableDataInfo selectMakers(DrugsMakers drugsMakers){
        startPage();
        return getDataTable(drugsMakersService.queryMakers(drugsMakers));
    }

    /**
     * 通过厂家id查询厂家信息
     * @param makersId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('drugs:makers:query')")
    @GetMapping(value = "/{makersId}")
    public AjaxResult selectMakers(@PathVariable Long makersId){
        return success(drugsMakersService.selectMakers(makersId));
    }

    /**
     * 新增厂家信息
     * @param drugsMakers
     * @return
     */
    @PreAuthorize("@ss.hasPermi('drugs:makers:add')")
    @PostMapping
    public AjaxResult addMakers(@Validated @RequestBody DrugsMakers drugsMakers){
        drugsMakers.setCreateBy(getUsername());
        return toAjax(drugsMakersService.addMakers(drugsMakers));
    }

    /**
     * 修改厂家信息
     * @param drugsMakers
     * @return
     */
    @PreAuthorize("@ss.hasPermi('drugs:makers:upd')")
    @PutMapping
    public AjaxResult updateMakers(@Validated @RequestBody DrugsMakers drugsMakers){
        drugsMakers.setUpdateBy(getUsername());
        return toAjax(drugsMakersService.updateMakers(drugsMakers));
    }

    /**
     * 根据厂家ID批量删除厂家信息
     * @param makersIds
     * @return
     */
    @PreAuthorize("@ss.hasPermi('drugs:makers:remove')")
    @DeleteMapping("/{makersIds}")
    public AjaxResult removeMakers(@PathVariable Long[] makersIds){
        return toAjax(drugsMakersService.removeMakers(makersIds));
    }
}
