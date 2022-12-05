package com.medical.his.controller;

import com.medical.common.core.controller.BaseController;
import com.medical.common.core.page.TableDataInfo;
import com.medical.his.domain.HisDept;
import com.medical.his.domain.HisItems;
import com.medical.his.service.HisItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/4 9:49
 */
@RestController
@RequestMapping("/his/items")
public class HisItemsController extends BaseController {

    @Autowired
    private HisItemsService hisItemsService;

    @PreAuthorize("@ss.hasPermi('his:check:list')")
    @GetMapping("/index")
    public TableDataInfo queryList(HisItems hisItems)
    {
        startPage();
        List<HisItems> list = hisItemsService.selectHisItemsList(hisItems);
        return getDataTable(list);
    }
}
