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
}
