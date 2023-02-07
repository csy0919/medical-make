package com.medical.his.controller;

import com.medical.common.annotation.Log;
import com.medical.common.constant.UserConstants;
import com.medical.common.core.controller.BaseController;
import com.medical.common.core.domain.AjaxResult;
import com.medical.common.core.page.TableDataInfo;
import com.medical.common.enums.BusinessType;
import com.medical.his.domain.HisItems;
import com.medical.his.service.HisItemsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/4 9:49
 */
@Api(tags = "检查费用信息接口")
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
    @ApiOperation(value="项目费用查询集合")
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
    @ApiOperation(value="项目新增")
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

    /**
     * 通过Id查询项目信息
     * @param itemsId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('his:items:query')")
    @GetMapping(value="/{itemsId}")
    @ApiOperation(value="通过Id查询项目信息")
    public AjaxResult getInfo(@PathVariable Long itemsId){
        return success(hisItemsService.selectItems(itemsId));
    }

    /**
     * 修改项目信息
     * @param hisItems
     * @return
     */
    @PreAuthorize("@ss.hasPermi('his:items:upd')")
    @PutMapping
    @ApiOperation(value="修改项目信息")
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

    /**
     * 通过Id批量删除项目信息
     * @param itemsIds
     * @return
     */
    @PreAuthorize("@ss.hasPermi('his:items:remove')")
    @Log(title = "检查费用设置",businessType = BusinessType.DELETE)
    @DeleteMapping("/{itemsIds}")
    @ApiOperation(value="批量删除项目信息")
    public AjaxResult deleteByItemsIds(@Validated @PathVariable Long[] itemsIds){
        return success(hisItemsService.deleteByItemsIds(itemsIds));
    }

}
