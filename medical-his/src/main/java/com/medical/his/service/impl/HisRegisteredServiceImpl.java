package com.medical.his.service.impl;

import com.medical.common.constant.UserConstants;
import com.medical.common.enums.UserStatus;
import com.medical.common.utils.StringUtils;
import com.medical.his.domain.HisRegistered;
import com.medical.his.mapper.HisRegisteredMapper;
import com.medical.his.service.HisRegisteredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/6 11:58
 */
@Service
public class HisRegisteredServiceImpl implements HisRegisteredService {

    @Autowired
    private HisRegisteredMapper hisRegisteredMapper;


    @Override
    public List<HisRegistered> selectRegisteredList(HisRegistered hisRegistered) {
        return hisRegisteredMapper.selectRegisteredList(hisRegistered);
    }

    @Override
    public HisRegistered selectByRegisteredId(Long registeredId) {
        return hisRegisteredMapper.selectByRegisteredId(registeredId);
    }

    @Override
    public int addRegistered(HisRegistered hisRegistered) {
        return hisRegisteredMapper.addRegistered(hisRegistered);
    }

    @Override
    public int updateRegistered(HisRegistered hisRegistered) {
        return hisRegisteredMapper.updateRegistered(hisRegistered);
    }

    @Override
    public int delRegistered(Long[] registeredId) {
        return hisRegisteredMapper.delRegistered(registeredId);
    }

    @Override
    public String checkRegisteredNameUnique(HisRegistered hisRegistered) {
        Long getRegisteredIds = StringUtils.isNull(hisRegistered.getRegisteredId())?-1L:hisRegistered.getRegisteredId();
        HisRegistered info =hisRegisteredMapper.checkRegisteredNameUnique(hisRegistered.getRegisteredName());
        if (StringUtils.isNotNull(info)&&info.getRegisteredId().longValue()!=getRegisteredIds.longValue()
        ){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
