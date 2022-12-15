import request from '@/utils/request'

export function queryVendors(params){
  return request({
    url: '/drugs/vendor/index',
    method: 'get',
    params: params
  })
}

// 根据ID查询供应商详细
export function getVendor(vendorId) {
  return request({
    url: '/drugs/vendor/' + vendorId,
    method: 'get'
  })
}

// 新增供应商信息
export function addVendor(data) {
  return request({
    url: '/drugs/vendor',
    method: 'post',
    data: data
  })
}

// 修改供应商信息
export function updateVendor(data) {
  return request({
    url: '/drugs/vendor',
    method: 'put',
    data: data
  })
}

// 删除供应商信息
export function delVendor(vendorId) {
  return request({
    url: '/drugs/vendor/' + vendorId,
    method: 'delete'
  })
}
