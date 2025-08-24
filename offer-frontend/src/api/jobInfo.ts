import request from './request'
import type {
  BaseResponse,
  PageResponse,
  JobInfo,
  JobInfoDTO,
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
  delete: (id: string): Promise<BaseResponse<boolean>> => {
    const data: DeleteRequest = { id: id as any }
    return request.post('/jobInfo/delete', data).then(res => res.data)
  },

  // 更新招聘信息
  update: (data: JobInfoUpdateRequest): Promise<BaseResponse<boolean>> => {
    return request.post('/jobInfo/update', data).then(res => res.data)
  },

  // 根据ID获取招聘信息
  getById: (id: string): Promise<BaseResponse<JobInfo>> => {
    return request.get(`/jobInfo/get/${id}`).then(res => res.data)
  },

  // 获取招聘信息列表
  getList: (params: JobInfoQueryRequest): Promise<BaseResponse<PageResponse<JobInfo>>> => {
    return request.post('/jobInfo/list', params).then(res => res.data)
  },

  // 分页获取招聘信息（别名方法，兼容JobList.vue）
  getPage: (params: JobInfoQueryRequest): Promise<BaseResponse<PageResponse<JobInfo>>> => {
    return request.post('/jobInfo/list', params).then(res => res.data)
  },

  // 批量导入招聘信息
  batchImport: (file: File): Promise<BaseResponse<string>> => {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/jobInfo/batch-import', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).then(res => {
      return res.data
    })
  }
}

// 健康检查API
export const healthApi = {
  check: (): Promise<string> => {
    return request.get('/health/check').then(res => res.data)
  }
}