package com.ruoyi.his.service;

import com.ruoyi.his.domain.HisDept;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/1 20:51
 */
public interface HisDeptService {

    /**
     * 查询科室列表
     * @param hisDept
     * @return
     */
    List<HisDept> selectDeptList(HisDept hisDept);

    /**
     * 新增科室
     * @param hisDept
     * @return
     */
    int insertHisDept(HisDept hisDept);

    /**
     * 修改科室
     * @param hisDept
     * @return
     */
    int updateHisDept(HisDept hisDept);

    /**
     * 通过ID查询科室信息
     * @param deptId
     * @return
     */
    HisDept selectByDeptId(Long deptId);

    /**
     * 通过科室ID删除科室信息
     * @param deptId
     * @return 结果
     */
//    int removeByDeptId(Long deptId);

    /**
     * 批量删除科室信息
     * @param deptId
     * @return
     */
    int removeByDeptIds(Long[] deptId);

    /**
     * 校验科室名称
     * @param hisDept
     * @return
     */
    String checkDeptNameUnique(HisDept hisDept);

    /**
     * 校验科室编码
     * @param hisDept
     * @return
     */
    String checkDeptCodeUnique(HisDept hisDept);
}
