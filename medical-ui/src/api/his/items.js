import request from '@/utils/request'

// 查询检查费用设置列表
export function listItems(query) {
  return request({
    url: '/his/items/index',
    method: 'get',
    params: query
  })
}

// 查询岗位详细
export function getItems(itemsId) {
  return request({
    url: '/his/items/' + itemsId,
    method: 'get'
  })
}

// 新增检查项目
export function addItems(data) {
  return request({
    url: '/his/items',
    method: 'post',
    data: data
  })
}

// 修改检查项目
export function updateItems(data) {
  return request({
    url: '/his/items',
    method: 'put',
    data: data
  })
}

// 删除岗位
export function delItems(itemsId) {
  return request({
    url: '/his/items/' + itemsId,
    method: 'delete'
  })
}
