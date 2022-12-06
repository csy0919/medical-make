package com.medical.his.service;

import com.medical.his.domain.HisRegistered;


import java.util.List;

/**
 * @author Csy
 * @date 2022/12/6 11:56
 */

public interface HisRegisteredService {

    /**
     * 查询挂号费设置列表
     * @param hisRegistered
     * @return
     */
    List<HisRegistered> selectRegisteredList(HisRegistered hisRegistered);

    /**
     * 通过挂号费Id查询挂号费设置详细信息
     * @param registeredId
     * @return
     */
    HisRegistered selectByRegisteredId(Long registeredId);

    /**
     * 添加挂号费设置
     * @param hisRegistered
     * @return
     */
    int addRegistered(HisRegistered hisRegistered);

    /**
     * 修改挂号费设置
     * @param hisRegistered
     * @return
     */
    int updateRegistered(HisRegistered hisRegistered);

    /**
     * 根据ID批量删除挂号费用设置
     * @param registeredId
     * @return
     */
    int delRegistered(Long[] registeredId);


    /**
     * 校验挂号费名称唯一性
     * @param hisRegistered
     * @return
     */
    String checkRegisteredNameUnique(HisRegistered hisRegistered);
}
