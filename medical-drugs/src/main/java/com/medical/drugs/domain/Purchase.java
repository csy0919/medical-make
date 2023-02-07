package com.medical.drugs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medical.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 *
 * 供应商信息对象 purchase_information
 *
 * @author Csy
 * @date 2023/2/6 13:18
 */
@Data
public class Purchase extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 单据ID */
    private String purchaseId;

    /** 供应商 */
    private String vendorName;

    /** 采购批发总额 */
    private String purchaseMoney;

    /** 入库状态 */
    private String purchaseStatus;

    /** 申请人 */
    private String purchaseApplicant;

    /** 入库操作人 */
    private String purchaseOperate;

    /** 入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date purchaseTime;

    /** 状态 */
    private String status;
}
