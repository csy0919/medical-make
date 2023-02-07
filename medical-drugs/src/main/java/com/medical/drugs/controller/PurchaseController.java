package com.medical.drugs.controller;

import com.medical.common.core.controller.BaseController;
import com.medical.common.core.page.TableDataInfo;
import com.medical.drugs.domain.Purchase;
import com.medical.drugs.service.PurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Csy
 * @date 2023/2/6 13:42
 */
@Api(tags = "采购入库信息接口")
@RestController
@RequestMapping("/drugs/purchase")
public class PurchaseController extends BaseController {

    @Autowired
    private PurchaseService purchaseService;

    /**
     * 查询采购入库信息列表
     */
    @PreAuthorize("@ss.hasPermi('drugs:purchase:list')")
    @GetMapping("/index")
    @ApiOperation(value="查询采购入库列表信息")
    public TableDataInfo list(Purchase purchase)
    {
        startPage();
        List<Purchase> list = purchaseService.selectPurchaseList(purchase);
        return getDataTable(list);
    }
}
