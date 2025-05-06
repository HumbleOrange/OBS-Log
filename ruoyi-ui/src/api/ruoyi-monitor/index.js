import request from '@/utils/request'

// 查询日志索引列表
export function listIndex(query) {
  return request({
    url: '/monitor/track/find-span',
    method: 'get',
    params: query
  })
}

// 查询日志详细信息
export function getLog(trackId, id) {
  return request({
    url: '/monitor/track/find-log',
    method: 'get',
    params: {
      trackId,
      id
    }
  })
}
