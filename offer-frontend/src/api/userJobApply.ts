import request from './request'
import type { PageResponse } from './types'

// 用户投递记录相关类型定义
export interface UserJobApplyDTO {
  id: number
  userId: number
  userName: string
  jobId: number
  companyName: string
  companyType?: string
  industry?: string
  recruitType?: string
  workLocation?: string
  recruitTarget?: string
  positionName: string
  applicationStatus: string
  relatedLink: string
  personalNote: string
  createTime: string
  updateTime: string
}

export interface UserJobApplyAddRequest {
  jobId: number
  applicationStatus?: string
}

export interface UserJobApplyUpdateRequest {
  id: number
  applicationStatus: string
  personalNote: string
}

export interface UserJobApplyQueryRequest {
  pageNum?: number
  pageSize?: number
  userId?: number
  jobId?: number
  companyName?: string
  companyType?: string
  industry?: string
  recruitType?: string
  workLocation?: string
  recruitTarget?: string
  positionName?: string
  applicationStatus?: string
  sortField?: string
  sortOrder?: string
}

// 用户投递记录API
export const userJobApplyApi = {
  // 添加投递记录
  add: (data: UserJobApplyAddRequest) => {
    return request.post<number>('/user-job-apply/add', data)
  },

  // 删除投递记录
  delete: (id: number) => {
    return request.post<boolean>('/user-job-apply/delete', { id })
  },

  // 更新投递记录
  update: (data: UserJobApplyUpdateRequest) => {
    return request.post<boolean>('/user-job-apply/update', data)
  },

  // 检查是否已投递
  hasApplied: (jobId: number) => {
    return request.get<boolean>(`/user-job-apply/check?jobId=${jobId}`)
  },

  // 分页查询投递记录
  getPage: (data: UserJobApplyQueryRequest) => {
    return request.post<PageResponse<UserJobApplyDTO>>('/user-job-apply/page', data)
  },

  // 管理员分页查询所有投递记录
  adminGetPage: (data: UserJobApplyQueryRequest) => {
    return request.post<PageResponse<UserJobApplyDTO>>('/user-job-apply/admin/page', data)
  },

  // 管理员删除投递记录
  adminDelete: (id: number) => {
    return request.post<boolean>('/user-job-apply/admin/delete', { id })
  },

  // 管理员更新投递记录状态
  adminUpdate: (data: UserJobApplyUpdateRequest) => {
    return request.post<boolean>('/user-job-apply/admin/update', data)
  }
}