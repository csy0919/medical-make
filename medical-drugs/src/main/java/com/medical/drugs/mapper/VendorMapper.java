package com.medical.drugs.mapper;

import com.medical.drugs.domain.Vendor;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/15 14:31
 */
public interface VendorMapper {
    /**
     * 查询供应商信息列表
     *
     * @param vendor 供应商信息
     * @return 供应商信息集合
     */
    List<Vendor> selectVendorList(Vendor vendor);

    /**
     * 新增供应商信息
     *
     * @param vendor 供应商信息
     * @return 结果
     */
     int insertVendor(Vendor vendor);

    /**
     * 修改供应商信息
     *
     * @param vendor 供应商信息
     * @return 结果
     */
    int updateVendor(Vendor vendor);

    /**
     * 删除供应商信息
     *
     * @param vendorId 供应商信息主键
     * @return 结果
     */
    int deleteVendorByVendorId(Long vendorId);

    /**
     * 批量删除供应商信息
     *
     * @param vendorIds 需要删除的数据主键集合
     * @return 结果
     */
     int deleteVendorByVendorIds(Long[] vendorIds);

    /**
     * 查询供应商信息
     *
     * @param vendorId 供应商信息主键
     * @return 供应商信息
     */
     Vendor selectVendorByVendorId(Long vendorId);
}


