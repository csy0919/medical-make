package com.medical.drugs.mapper;

import com.medical.drugs.domain.DrugsMakers;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/9 11:46
 */
public interface DrugsMakersMapper {

    List<DrugsMakers> selectMakersList(DrugsMakers drugsMakers);
}
