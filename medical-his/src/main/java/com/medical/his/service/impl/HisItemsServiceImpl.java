package com.medical.his.service.impl;

import com.medical.his.domain.HisItems;
import com.medical.his.mapper.HisItemsMapper;
import com.medical.his.service.HisItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/4 9:45
 */
@Service
public class HisItemsServiceImpl implements HisItemsService {

    @Autowired
    private HisItemsMapper hisItemsMapper;

    @Override
    public List<HisItems> selectHisItemsList(HisItems hisItems) {
        return hisItemsMapper.selectItemsList(hisItems);
    }
}
