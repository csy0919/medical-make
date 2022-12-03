import request from '@/utils/request'

//查询科室列表
export function queryDept(params) {
  return request({
    url: '/his/dept/index',
    method: 'get',
    params: params
  })
}
// 查询科室详细
export function getDept(deptId) {
  return request({
    url: '/his/dept/' + deptId,
    method: 'get'
  })
}

// 新增科室
export function addDept(data) {
  return request({
    url: '/his/dept',
    method: 'post',
    data: data
  })
}

// 修改科室
export function updatePost(data) {
  return request({
    url: '/his/dept',
    method: 'put',
    data: data
  })
}

// 删除科室
export function delDept(postId) {
  return request({
    url: '/his/dept/' + postId,
    method: 'delete'
  })
}
