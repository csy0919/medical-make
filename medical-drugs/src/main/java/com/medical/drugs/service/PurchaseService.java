package com.medical.drugs.service;


import com.medical.drugs.domain.Purchase;

import java.util.List;

/**
 * @author Csy
 * @date 2023/2/6 13:36
 */
public interface PurchaseService {

    /**
     * 查询供应商信息列表
     *
     * @param purchase 供应商信息
     * @return 供应商信息集合
     */
    List<Purchase> selectPurchaseList(Purchase purchase);
}
