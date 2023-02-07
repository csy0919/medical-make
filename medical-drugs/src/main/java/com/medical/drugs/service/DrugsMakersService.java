package com.medical.drugs.service;

import com.medical.drugs.domain.DrugsMakers;
import com.medical.drugs.domain.Makers;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/9 12:20
 */
public interface DrugsMakersService {

    /**
     * 查询厂家信息列表
     * @param drugsMakers
     * @return
     */
    List<DrugsMakers> selectMakersList(DrugsMakers drugsMakers);

    /**
     * 通过厂家ID查询厂家信息
     * @param makersId
     * @return
     */
    DrugsMakers selectMakers(Long makersId);

    /**
     * 新增厂家信息
     * @param drugsMakers
     * @return
     */
    int addMakers(DrugsMakers drugsMakers);

    /**
     * 修改厂家信息
     * @param drugsMakers
     * @return
     */
    int updateMakers(DrugsMakers drugsMakers);

    /**
     * 批量删除厂家信息
     * @param makersIds
     * @return
     */
    int removeMakers(Long[] makersIds);

    /**
     * 查询厂家信息(ID,名称)
     * @param drugsMakers
     * @return
     */
    List<Makers> queryMakers(DrugsMakers drugsMakers);
}
