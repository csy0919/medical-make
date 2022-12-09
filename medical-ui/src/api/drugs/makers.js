import request from '@/utils/request'

//查询厂家列表
export function queryMakers(params) {
  return request({
    url: '/drugs/makers/index',
    method: 'get',
    params: params
  })
}
// 查询科室详细
export function getMakers(deptId) {
  return request({
    url: '/his/dept/' + deptId,
    method: 'get'
  })
}

// 新增科室
export function addMakers(data) {
  return request({
    url: '/his/dept',
    method: 'post',
    data: data
  })
}

// 修改科室
export function updateMakers(data) {
  return request({
    url: '/his/dept',
    method: 'put',
    data: data
  })
}

// 删除科室
export function delMakers(postId) {
  return request({
    url: '/his/dept/' + postId,
    method: 'delete'
  })
}
