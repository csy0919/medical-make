package com.medical.his.service;

import com.medical.his.domain.HisItems;
import java.util.List;

/**
 * @author Csy
 * @date 2022/12/4 9:43
 */
public interface HisItemsService {
    List<HisItems> selectHisItemsList(HisItems hisItems);
}
