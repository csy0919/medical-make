package com.medical.his.service.impl;

import com.medical.common.constant.UserConstants;
import com.medical.common.exception.ServiceException;
import com.medical.common.utils.StringUtils;
import com.medical.his.domain.HisDept;
import com.medical.his.mapper.HisDeptMapper;
import com.medical.his.service.HisDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/1 20:57
 */
@Service
public class HisDeptServiceImpl implements HisDeptService {

    @Autowired
    private HisDeptMapper hisDeptMapper;

    @Override
    public List<HisDept> selectDeptList(HisDept hisDept) {
        return hisDeptMapper.selectDeptList(hisDept);
    }

    @Override
    public int insertHisDept(HisDept hisDept) {
        return hisDeptMapper.insertHisDept(hisDept);
    }

    @Override
    public int updateHisDept(HisDept hisDept) {
        return hisDeptMapper.updateHisDept(hisDept);
    }

    @Override
    public HisDept selectByDeptId(Long deptId) {
        return hisDeptMapper.selectByDeptId(deptId);
    }

//    @Override
//    public int removeByDeptId(Long deptId) {
//        return hisDeptMapper.deleteDeptById(deptId);
//    }

    @Override
    public int removeByDeptIds(Long[] deptIds) {
        for (Long deptId : deptIds)
        {
            HisDept hisDept = selectByDeptId(deptId);
            if (hisDept.getDeptQuantity()> 0)
            {
                throw new ServiceException(String.format("%1$s仍有挂号未看完,不能删除", hisDept.getDeptName()));
            }
        }
        return hisDeptMapper.deleteDeptByIds(deptIds);
    }

    @Override
    public String checkDeptNameUnique(HisDept hisDept) {
        Long getDeptId = StringUtils.isNull(hisDept.getDeptId()) ? -1L : hisDept.getDeptId();
        HisDept info = hisDeptMapper.checkDeptNameUnique(hisDept.getDeptName());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != getDeptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkDeptCodeUnique(HisDept hisDept) {
        Long getDeptId = StringUtils.isNull(hisDept.getDeptId()) ? -1L : hisDept.getDeptId();
        HisDept info = hisDeptMapper.checkDeptCodeUnique(hisDept.getDeptCode());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != getDeptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
