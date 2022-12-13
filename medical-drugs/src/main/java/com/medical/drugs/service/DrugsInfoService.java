package com.medical.drugs.service;

import com.medical.drugs.domain.DrugsInfo;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/12 21:00
 */
public interface DrugsInfoService {


    /**
     * 查询药品信息列表
     * @param drugsInfo
     * @return
     */
    List<DrugsInfo> selectDrugsInfoList(DrugsInfo drugsInfo);

    /**
     * 通过药品ID查询药品信息
     * @param drugId
     * @return
     */
    DrugsInfo selectDrug(Long drugId);

    /**
     * 新增药品信息
     * @param drugsInfo
     * @return
     */
    int addDrug(DrugsInfo drugsInfo);

    /**
     * 修改药品信息
     * @param drugsInfo
     * @return
     */
    int updateDrug(DrugsInfo drugsInfo);

    /**
     * 删除药品信息
     * @param drugIds
     * @return
     */
    int delDrug(Long[] drugIds);

}
