package com.ruoyi.his.mapper;

import com.ruoyi.his.domain.HisDept;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/1 20:29
 */
public interface HisDeptMapper {

    /**
     * 查询科室列表
     * @param hisDept
     * @return
     */
    List<HisDept> selectDeptList(HisDept hisDept);

    /**
     * 根据科室ID查询科室信息
     * @param DeptId
     * @return 科室对象信息
     */
    HisDept selectByDeptId(Long DeptId);

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
     * 校验科室名称
     * @param deptName
     * @return
     */
    HisDept checkDeptNameUnique(String deptName);

    /**
     * 校验科室编号
     * @param deptCode
     * @return
     */
    HisDept checkDeptCodeUnique(String deptCode);

    /**
     * 删除科室信息
     *
     * @param deptId 科室ID
     * @return 结果
     */
    int deleteDeptById(Long deptId);

    /**
     * 批量删除科室信息
     *
     * @param deptIds 需要删除的科室ID集
     * @return 结果
     */
    int deleteDeptByIds(Long[] deptIds);
}
