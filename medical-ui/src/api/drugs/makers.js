import request from '@/utils/request'

//查询厂家列表
export function queryMakers(params) {
  return request({
    url: '/drugs/makers/index',
    method: 'get',
    params: params
  })
}
// 根据ID查询厂家详细
export function getMakers(makersId) {
  return request({
    url: '/drugs/makers/' + makersId,
    method: 'get'
  })
}

// 新增厂家信息
export function addMakers(data) {
  return request({
    url: '/drugs/makers',
    method: 'post',
    data: data
  })
}

// 修改厂家信息
export function updateMakers(data) {
  return request({
    url: '/drugs/makers',
    method: 'put',
    data: data
  })
}

// 删除厂家信息
export function delMakers(makersId) {
  return request({
    url: '/drugs/makers/' + makersId,
    method: 'delete'
  })
}
