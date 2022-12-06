package com.medical.his.mapper;

import com.medical.his.domain.HisItems;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/4 9:04
 */
public interface HisItemsMapper {

    /**
     * 查询检查费用项目列表
     * @param hisItems
     * @return
     */
    List<HisItems>  selectItemsList(HisItems hisItems);

    /**
     * 新增检查费用项目
     * @param hisItems
     * @return
     */
    int addItems(HisItems hisItems);

    /**
     * 查询单个项目的详细信息
     * @param itemsId
     * @return
     */
    HisItems selectItems(Long itemsId);

    /**
     * 修改单个项目的详细信息
     * @param hisItems
     * @return
     */
    int updateItems(HisItems hisItems);

    /**
     * 校验项目名称唯一性
     * @param itemsName
     * @return
     */
    HisItems checkItemsNameUnique(String itemsName);

    /**
     * 校验项目关键字唯一性
     * @param itemsName
     * @return
     */
    HisItems checkItemsGJZUnique(String itemsName);

    /**
     * 根据项目费用ID批量删除项目信息
     */

    int deleteByItemsIds(Long[] itemsIds);



}
