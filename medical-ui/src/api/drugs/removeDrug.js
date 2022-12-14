import request from '@/utils/request'

// 查询db数据库列表
export function listDelDrug(query) {
  return request({
    url: '/drugs/drug/del/list',
    method: 'get',
    params: query
  })
}

// 导入删除药品信息
export function importDrug(data) {
  return request({
    url: '/drugs/drug/importDrug',
    method: 'post',
    params: data
  })
}
