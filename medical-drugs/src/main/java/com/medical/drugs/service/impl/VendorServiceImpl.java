package com.medical.drugs.service.impl;

import com.medical.drugs.domain.Vendor;
import com.medical.drugs.mapper.VendorMapper;
import com.medical.drugs.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Csy
 * @date 2022/12/15 14:50
 */
@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorMapper vendorMapper;

    @Override
    public List<Vendor> selectVendorList(Vendor vendor) {
        return vendorMapper.selectVendorList(vendor);
    }

    @Override
    public int insertVendor(Vendor vendor) {
        return vendorMapper.insertVendor(vendor);
    }

    @Override
    public int updateVendor(Vendor vendor) {
        return vendorMapper.updateVendor(vendor);
    }

    @Override
    public int deleteVendorByVendorId(Long vendorId) {
        return vendorMapper.deleteVendorByVendorId(vendorId);
    }

    @Override
    public int deleteVendorByVendorIds(Long[] vendorIds) {
        return vendorMapper.deleteVendorByVendorIds(vendorIds);
    }

    @Override
    public Vendor selectVendorByVendorId(Long vendorId) {
        return vendorMapper.selectVendorByVendorId(vendorId);
    }
}
