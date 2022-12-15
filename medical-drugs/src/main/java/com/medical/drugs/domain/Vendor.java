package com.medical.drugs.domain;

import com.medical.common.annotation.Excel;
import com.medical.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author Csy
 * @date 2022/12/15 14:31
 */
@Data
public class Vendor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 供应商ID */
    private Long vendorId;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String vendorName;

    /** 联系人 */
    @Excel(name = "联系人")
    private String vendorLeader;

    /** 联系人手机 */
    @Excel(name = "联系人手机")
    private String vendorPhone;

    /** 联系人电话 */
    @Excel(name = "联系人电话")
    private String vendorLandline;

    /** 银行账号 */
    @Excel(name = "银行账号")
    private String vendorBank;

    /** 地址 */
    @Excel(name = "地址")
    private String vendorAddress;

    /** 状态 */
    @Excel(name = "状态")
    private String status;
}
