package com.medical.his.service;

import com.medical.his.domain.HisItems;
import java.util.List;

/**
 * @author Csy
 * @date 2022/12/4 9:43
 */
public interface HisItemsService {
    List<HisItems> selectHisItemsList(HisItems hisItems);

    int addItems(HisItems hisItems);

    HisItems selectItems(Long itemsId);

    int updateItems(HisItems hisItems);

    String checkItemsNameUnique(HisItems hisItems);

    String checkItemsGJZUnique(HisItems hisItems);

    int deleteByItemsIds(Long[] itemsIds);
}
