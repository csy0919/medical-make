package com.medical.his.domain;

import com.medical.common.annotation.Excel;
import com.medical.common.core.domain.BaseEntity;
import lombok.Data;


/**
 * @author Csy
 * @date 2022/12/1 20:12
 */
@Data
public class HisDept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 科室ID */
    private Long deptId;

    /** 科室名称 */
    @Excel(name = "科室名称")
    private String deptName;

    /** 科室编号 */
    @Excel(name = "科室编号")
    private String deptCode;

    /** 当前挂号量 */
    @Excel(name = "当前挂号量")
    private Long deptQuantity;

    /** 负责人 */
    @Excel(name = "负责人")
    private String deptLeader;

    /** 电话 */
    @Excel(name = "电话")
    private String deptPhone;

    /** 状态 */
    @Excel(name = "状态")
    private String status;


}

