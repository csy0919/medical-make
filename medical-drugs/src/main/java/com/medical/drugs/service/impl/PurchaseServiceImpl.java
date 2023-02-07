package com.medical.drugs.service.impl;

import com.medical.drugs.domain.Purchase;
import com.medical.drugs.mapper.PurchaseMapper;
import com.medical.drugs.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Csy
 * @date 2023/2/6 13:40
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseMapper purchaseMapper;


    @Override
    public List<Purchase> selectPurchaseList(Purchase purchase) {
        return purchaseMapper.selectPurchaseList(purchase);
    }
}
