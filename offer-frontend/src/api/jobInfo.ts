import request from './request'
import type {
  BaseResponse,
  PageResponse,
  JobInfo,
  JobInfoAddRequest,
  JobInfoUpdateRequest,
  JobInfoQueryRequest,
  DeleteRequest
} from './types'

// 招聘信息API服务
export const jobInfoApi = {
  // 添加招聘信息
  add: (data: JobInfoAddRequest): Promise<BaseResponse<boolean>> => {
    return request.post('/jobInfo/add', data).then(res => res.data)
  },

  // 删除招聘信息
  delete: (id: number): Promise<BaseResponse<boolean>> => {
    const data: DeleteRequest = { id }
    return request.post('/jobInfo/delete', data).then(res => res.data)
  },

  // 更新招聘信息
  update: (data: JobInfoUpdateRequest): Promise<BaseResponse<boolean>> => {
    return request.post('/jobInfo/update', data).then(res => res.data)
  },

  // 根据ID获取招聘信息
  getById: (id: number): Promise<BaseResponse<JobInfo>> => {
    return request.get(`/jobInfo/get/${id}`).then(res => res.data)
  },

  // 获取招聘信息列表
  getList: (params: JobInfoQueryRequest): Promise<BaseResponse<PageResponse<JobInfo>>> => {
    return request.post('/jobInfo/list', params).then(res => res.data)
  }
}

// 健康检查API
export const healthApi = {
  check: (): Promise<string> => {
    return request.get('/health/check').then(res => res.data)
  }
}