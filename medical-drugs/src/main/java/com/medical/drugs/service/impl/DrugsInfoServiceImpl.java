package com.medical.drugs.service.impl;

import com.medical.drugs.domain.DrugsInfo;
import com.medical.drugs.domain.Makers;
import com.medical.drugs.mapper.DrugsInfoMapper;
import com.medical.drugs.service.DrugsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/12 21:01
 */
@Service
public class DrugsInfoServiceImpl implements DrugsInfoService {

    @Autowired
    private DrugsInfoMapper drugsInfoMapper;

    @Override
    public List<DrugsInfo> selectDrugsInfoList(DrugsInfo drugsInfo) {
        return drugsInfoMapper.selectDrugsInfoList(drugsInfo);
    }

    @Override
    public DrugsInfo selectDrug(Long drugId) {
        return drugsInfoMapper.selectDrug(drugId);
    }

    @Override
    public int addDrug(DrugsInfo drugsInfo) {
        return drugsInfoMapper.addDrug(drugsInfo);
    }

    @Override
    public int updateDrug(DrugsInfo drugsInfo) {
        return drugsInfoMapper.updateDrug(drugsInfo);
    }

    @Override
    public int delDrug(Long[] drugIds) {
        return drugsInfoMapper.delDrug(drugIds);
    }

}
