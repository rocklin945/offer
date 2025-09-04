// 基础响应类型
export interface BaseResponse<T = any> {
  statusCode: number
  data: T
  message: string
}

// 分页响应类型
export interface PageResponse<T> {
  records: T[]
  total: number
  size: number
  current: number
  pages: number
}

// 删除请求类型
export interface DeleteRequest {
  id: string | number
}

// 招聘信息相关类型
export interface JobInfo {
  id: number
  companyName: string
  companyType: string
  industry: string
  recruitType: string
  workLocation: string
  recruitTarget: string
  positionName: string
  startTime: string
  deadline: string
  relatedLink: string
  announcement: string
  referralCode: string
  remark: string
}

export interface JobInfoDTO {
  id: number
  companyName: string
  companyType: string
  industry: string
  recruitType: string
  workLocation: string
  recruitTarget: string
  positionName: string
  startTime: string
  deadline: string
  relatedLink: string
  announcement: string
  referralCode: string
  remark: string
}

export interface JobInfoAddRequest {
  companyName: string
  companyType?: string
  industry?: string
  recruitType?: string
  workLocation?: string
  recruitTarget?: string
  positionName: string
  startTime?: string
  deadline?: string
  relatedLink?: string
  announcement?: string
  referralCode?: string
  remark?: string
}

export interface JobInfoUpdateRequest {
  id: number
  companyName?: string
  companyType?: string
  industry?: string
  recruitType?: string
  workLocation?: string
  recruitTarget?: string
  positionName?: string
  startTime?: string
  deadline?: string
  relatedLink?: string
  announcement?: string
  referralCode?: string
  remark?: string
}

export interface JobInfoQueryRequest {
  companyName?: string
  positionName?: string
  recruitType?: string
  workLocation?: string
  pageNum?: number
  pageSize?: number
  sortField?: string
  sortOrder?: string
}

// 网站信息相关类型
export interface WebInfoResponse {
  id: number
  pv: number
  imageUrl: string
  userCount: number
  memberCount: number
  jobCount: number
  activity1: string
  activity1Time: string
  activity2: string
  activity2Time: string
  activity3: string
  activity3Time: string
  activity4: string
  activity4Time: string
  activity5: string
  activity5Time: string
  updateTime: string
}

// 邀请佣金相关类型
export interface InviteCommission {
  id?: number
  userId: number
  invitedCount: number
  pendingCommission: number
  totalCommission: number
  balanceCommission: number
  createTime?: string
  updateTime?: string
}