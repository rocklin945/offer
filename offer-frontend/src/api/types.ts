// API响应类型定义
export interface BaseResponse<T = any> {
  statusCode: number
  message: string
  data: T
}

export interface PageResponse<T> {
  list: T[]
  total: number
  pageNum: number
  pageSize: number
  pages: number
  hasNext: boolean
  hasPrevious: boolean
}

// 招聘信息相关类型
export interface JobInfo {
  id?: number
  companyName: string
  companyType?: string
  industry?: string
  recruitType?: string
  workLocation?: string
  recruitTarget?: string
  positionName?: string
  applicationStatus?: string
  startTime?: string
  deadline?: string
  relatedLink?: string
  announcement?: string
  referralCode?: string
  remark?: string
}

export interface JobInfoAddRequest {
  companyName: string
  companyType?: string
  industry?: string
  recruitType?: string
  workLocation?: string
  recruitTarget?: string
  positionName?: string
  applicationStatus?: string
  startTime?: string
  deadline?: string
  relatedLink?: string
  announcement?: string
  referralCode?: string
  remark?: string
}

export interface JobInfoUpdateRequest extends JobInfoAddRequest {
  id: number
}

export interface JobInfoQueryRequest {
  pageNum?: number
  pageSize?: number
  companyName?: string
  companyType?: string
  industry?: string
  recruitType?: string
  workLocation?: string
  recruitTarget?: string
  positionName?: string
  applicationStatus?: string
  startTime?: string
  deadline?: string
  sortField?: string
  sortOrder?: string
}

export interface DeleteRequest {
  id: number
}