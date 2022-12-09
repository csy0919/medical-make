package com.medical.drugs.service;

import com.medical.drugs.domain.DrugsMakers;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/9 12:20
 */
public interface DrugsMakersService {

    List<DrugsMakers> selectMakersList(DrugsMakers drugsMakers);
}
