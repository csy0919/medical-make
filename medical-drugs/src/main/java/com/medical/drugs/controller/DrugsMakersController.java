package com.medical.drugs.controller;

import com.medical.common.core.controller.BaseController;
import com.medical.common.core.page.TableDataInfo;
import com.medical.drugs.domain.DrugsMakers;
import com.medical.drugs.service.DrugsMakersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/9 12:23
 */
@RestController
@RequestMapping("/drugs/makers")
public class DrugsMakersController extends BaseController {

    @Autowired
    private DrugsMakersService drugsMakersService;

    @PreAuthorize("@ss.hasPermi('drugs:makers:list')")
    @GetMapping("/index")
    public TableDataInfo selectMakersList(DrugsMakers drugsMakers){
        startPage();
        List<DrugsMakers> list = drugsMakersService.selectMakersList(drugsMakers);
        return getDataTable(list);
    }
}
