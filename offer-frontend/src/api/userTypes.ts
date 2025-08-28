// 用户相关类型定义

// 用户登录请求
export interface UserLoginRequest {
  userAccount: string
  userPassword: string
}

// 用户登录响应
export interface UserLoginResponse {
  userId: string
  userAccount: string
  userName: string
  userAvatar: string
  userProfile: string
  userRole: number
  token: string
  createTime: string
  updateTime: string
  memberExpireTime?: string
}

// 用户更新请求
export interface UserUpdateRequest {
  id: string
  userPassword?: string
  userName?: string
  userAvatar?: string
  userProfile?: string
  userRole?: number
  memberExpireTime?: string
}

// 用户分页查询请求
export interface UserPageQueryRequest {
  pageNum?: number
  pageSize?: number
  id?: string
  userName?: string
  userAccount?: string
  userProfile?: string
  userRole?: number
  sortField?: string
  sortOrder?: string
}