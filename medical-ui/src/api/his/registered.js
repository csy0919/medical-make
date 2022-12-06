import request from '@/utils/request'

// 查询检查费用设置列表
export function listRegistered(query) {
  return request({
    url: '/his/registered/index',
    method: 'get',
    params: query
  })
}

// 查询检查费用设置详细
export function getRegistered(itemsId) {
  return request({
    url: '/his/registered/' + itemsId,
    method: 'get'
  })
}

// 新增检查费用设置
export function addRegistered(data) {
  return request({
    url: '/his/registered',
    method: 'post',
    data: data
  })
}

// 修改检查费用设置
export function updateRegistered(data) {
  return request({
    url: '/his/registered',
    method: 'put',
    data: data
  })
}

// 删除检查费用设置
export function delRegistered(itemsId) {
  return request({
    url: '/his/registered/' + itemsId,
    method: 'delete'
  })
}
