import request from "@/utils/request";

export function queryDrug(params) {
  return request({
    url: '/drugs/drug/index',
    method: 'get',
    params: params
  })
}

export function queryMakers(params) {
  return request({
    url: '/drugs/makers/makers',
    method: 'get',
    params: params
  })
}

// 根据ID查询药品详细
export function getDrug(DrugId) {
  return request({
    url: '/drugs/drug/' + DrugId,
    method: 'get'
  })
}

// 新增厂家信息
export function addDrug(data) {
  return request({
    url: '/drugs/drug',
    method: 'post',
    data: data
  })
}

// 修改厂家信息
export function updateDrug(data) {
  return request({
    url: '/drugs/drug',
    method: 'put',
    data: data
  })
}

// 删除厂家信息
export function delDrug(drugId) {
  return request({
    url: '/drugs/drug/' + drugId,
    method: 'delete'
  })
}
