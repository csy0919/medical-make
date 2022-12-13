package com.medical.drugs.domain;

import com.medical.common.annotation.Excel;
import com.medical.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author Csy
 * @date 2022/12/12 19:42
 */
@Data
public class DrugsInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 药品id */
    private Long drugId;

    /** 药品名称 */
    @Excel(name = "药品名称")
    private String drugName;

    /** 药品编码 */
    @Excel(name = "药品编码")
    private String drugCode;

    /** 厂家ID */
    @Excel(name = "厂家ID")
    private Long makersId;

    /** 药品类型 */
    @Excel(name = "药品类型")
    private String drugType;

    /** 处方类型 */
    @Excel(name = "处方类型")
    private String prescriptionType;

    /** 药品单位 */
    @Excel(name = "药品单位")
    private String drugUnit;

    /** 处方价格 */
    @Excel(name = "处方价格")
    private Long prescriptionPrice;

    /** 药品库存 */
    @Excel(name = "药品库存")
    private Long drugInventory;

    /** 预警值 */
    @Excel(name = "预警值")
    private Long drugAlert;

    /** 换算量 */
    @Excel(name = "换算量")
    private String drugConversion;

    /** 状态 */
    @Excel(name = "状态")
    private String status;
}
