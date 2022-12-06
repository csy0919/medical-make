package com.medical.his.controller;

import com.medical.common.annotation.Log;
import com.medical.common.constant.UserConstants;
import com.medical.common.core.controller.BaseController;
import com.medical.common.core.domain.AjaxResult;
import com.medical.common.core.page.TableDataInfo;
import com.medical.common.enums.BusinessType;
import com.medical.his.domain.HisItems;
import com.medical.his.service.HisItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     *项目费用查询集合
     * @param hisItems
     * @return
     */
    @PreAuthorize("@ss.hasPermi('his:items:list')")
    @GetMapping("/index")
    public TableDataInfo queryList(HisItems hisItems)
    {
        startPage();
        List<HisItems> list = hisItemsService.selectHisItemsList(hisItems);
        return getDataTable(list);
    }

    /**
     *项目新增
     * @param hisItems
     * @return
     */
    @PreAuthorize("@ss.hasPermi('his:items:add')")
    @Log(title = "检查费用设置",businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addItems(@Validated @RequestBody HisItems hisItems) {
        if (UserConstants.NOT_UNIQUE.equals(hisItemsService.checkItemsNameUnique(hisItems)))
        {
            return error("新增检查项目" + hisItems.getItemsName() + "'失败，项目名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(hisItemsService.checkItemsGJZUnique(hisItems)))
        {
            return error("新增检查项目'" + hisItems.getItemsGJZ() + "'失败，项目关键字已存在");
        }
        hisItems.setCreateBy(getUsername());
        return toAjax(hisItemsService.addItems(hisItems));
    }

    @PreAuthorize("@ss.hasPermi('his:items:query')")
    @GetMapping(value="/{itemsId}")
    public AjaxResult getInfo(@PathVariable Long itemsId){
        return success(hisItemsService.selectItems(itemsId));
    }

    @PreAuthorize("@ss.hasPermi('his:items:upd')")
    @PutMapping
    public AjaxResult updateItems(@Validated @RequestBody HisItems hisItems){
        if (UserConstants.NOT_UNIQUE.equals(hisItemsService.checkItemsNameUnique(hisItems)))
        {
            return error("修改检查项目" + hisItems.getItemsName() + "'失败，项目名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(hisItemsService.checkItemsGJZUnique(hisItems)))
        {
            return error("修改检查项目'" + hisItems.getItemsGJZ() + "'失败，项目关键字已存在");
        }
        hisItems.setCreateBy(getUsername());
        return toAjax(hisItemsService.updateItems(hisItems));
    }

    @PreAuthorize("@ss.hasPermi('his:items:remove')")
    @Log(title = "检查费用设置",businessType = BusinessType.DELETE)
    @DeleteMapping("/{itemsIds}")
    public AjaxResult deleteByItemsIds(@Validated @PathVariable Long[] itemsIds){
        return success(hisItemsService.deleteByItemsIds(itemsIds));
    }

}
