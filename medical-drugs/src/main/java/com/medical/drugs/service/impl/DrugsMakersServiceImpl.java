package com.medical.drugs.service.impl;

import com.medical.drugs.domain.DrugsMakers;
import com.medical.drugs.domain.Makers;
import com.medical.drugs.mapper.DrugsMakersMapper;
import com.medical.drugs.service.DrugsMakersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/9 12:21
 */
@Service
public class DrugsMakersServiceImpl implements DrugsMakersService {

    @Autowired
    private DrugsMakersMapper drugsMakersMapper;

    @Override
    public List<DrugsMakers> selectMakersList(DrugsMakers drugsMakers) {
        return drugsMakersMapper.selectMakersList(drugsMakers);
    }

    @Override
    public DrugsMakers selectMakers(Long makersId) {
        return drugsMakersMapper.selectMakers(makersId);
    }

    @Override
    public int addMakers(DrugsMakers drugsMakers) {
        return drugsMakersMapper.addMakers(drugsMakers);
    }

    @Override
    public int updateMakers(DrugsMakers drugsMakers) {
        return drugsMakersMapper.updateMakers(drugsMakers);
    }

    @Override
    public int removeMakers(Long[] makersIds) {
        return drugsMakersMapper.removeMakers(makersIds);
    }

    @Override
    public List<Makers> queryMakers(DrugsMakers drugsMakers) {
        return drugsMakersMapper.queryMakers(drugsMakers);
    }
}
