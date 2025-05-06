import request from '@/utils/request'

// 查询日志索引列表
export function listIndex(query) {
  return request({
    url: '/ruoyi-monitor/index/list',
    method: 'get',
    params: query
  })
}

// 查询日志索引详细
export function getIndex(id) {
  return request({
    url: '/ruoyi-monitor/index/' + id,
    method: 'get'
  })
}

// 新增日志索引
export function addIndex(data) {
  return request({
    url: '/ruoyi-monitor/index',
    method: 'post',
    data: data
  })
}

// 修改日志索引
export function updateIndex(data) {
  return request({
    url: '/ruoyi-monitor/index',
    method: 'put',
    data: data
  })
}

// 删除日志索引
export function delIndex(id) {
  return request({
    url: '/ruoyi-monitor/index/' + id,
    method: 'delete'
  })
}
