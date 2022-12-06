package com.medical.his.domain;

import com.medical.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author Csy
 * @date 2022/12/5 21:30
 */
@Data
public class HisRegistered extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**挂号费用ID*/
    private Long registeredId;

    /**挂号费名称*/
    private String registeredName;

    /**挂号费*/
    private String registeredCost;

    /**状态*/
    private String status;
}
