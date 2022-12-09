package com.medical.drugs.domain;

import com.medical.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author Csy
 * @date 2022/12/9 11:39
 */
@Data
public class DrugsMakers extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 厂家ID */
    private Long makersId;

    /** 厂家名称 */
    private String makersName;

    /** 厂家编码 */
    private String makersCode;

    /** 联系人 */
    private String makersLeader;

    /** 电话 */
    private String makersPhone;

    /** 关键字 */
    private String makersGJZ;

    /** 状态 */
    private String status;




}
