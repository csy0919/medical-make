package com.medical.his.service.impl;

import com.medical.common.constant.UserConstants;
import com.medical.common.utils.StringUtils;
import com.medical.his.domain.HisDept;
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

    @Override
    public int addItems(HisItems hisItems) {
        return hisItemsMapper.addItems(hisItems);
    }

    @Override
    public HisItems selectItems(Long itemsId) {
        return hisItemsMapper.selectItems(itemsId);
    }

    @Override
    public int updateItems(HisItems hisItems) {
        return hisItemsMapper.updateItems(hisItems);
    }

    @Override
    public String checkItemsNameUnique(HisItems hisItems) {
        Long getItemsId = StringUtils.isNull(hisItems.getItemsId()) ? -1L : hisItems.getItemsId();
        HisItems info = hisItemsMapper.checkItemsNameUnique(hisItems.getItemsName());
        if (StringUtils.isNotNull(info) && info.getItemsId().longValue() != getItemsId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkItemsGJZUnique(HisItems hisItems) {
        Long getItemsId = StringUtils.isNull(hisItems.getItemsId()) ? -1L : hisItems.getItemsId();
        HisItems info = hisItemsMapper.checkItemsGJZUnique(hisItems.getItemsGJZ());
        if (StringUtils.isNotNull(info) && info.getItemsId().longValue() != getItemsId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int deleteByItemsIds(Long[] itemsIds) {
        return hisItemsMapper.deleteByItemsIds(itemsIds);
    }

}
