package com.medical.his.domain;

import com.medical.common.annotation.Excel;
import com.medical.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author Csy
 * @date 2022/12/4 9:05
 */
@Data
public class HisItems extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 项目费用ID */
    private Long itemsId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String itemsName;

    /** 项目关键字 */
    @Excel(name = "项目关键字")
    private String itemsGJZ;

    /** 项目单价 */
    @Excel(name = "项目单价")
    private String itemsPrice;

    /** 项目成本 */
    @Excel(name = "项目成本")
    private String itemsCost;

    /** 单位 */
    @Excel(name = "单位")
    private String itemsUnit;

    /** 类别 */
    @Excel(name = "类别")
    private String itemsType;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

}
