import request from './request'
import type { InviteCommission } from './types'

export interface InviteCommissionPageQueryRequest {
  userId?: number
  userName?: string
  minInvitedCount?: number
  maxInvitedCount?: number
  minPendingCommission?: number
  maxPendingCommission?: number
  pageNum: number
  pageSize: number
  sortField?: string
  sortOrder?: string
}

export interface PageResponse<T> {
  list: T[]
  total: number
  pageNum: number
  pageSize: number
}

export const inviteCommissionApi = {
  // 分页查询邀请佣金列表
  listCommissionByPage: (params: InviteCommissionPageQueryRequest) => {
    return request.post<PageResponse<InviteCommission>>('/invite/commission/page', params)
  },

  // 确认佣金
  confirmCommission: (id: number, amount: number) => {
    return request.post(`/invite/commission/confirm/${id}?amount=${amount}`)
  },

  // 拒绝佣金
  rejectCommission: (id: number) => {
    return request.post(`/invite/commission/reject/${id}`)
  },

  // 兑换会员
  redeemMember: (userId: number, planType: number) => {
    return request.post(`/invite/commission/redeemMember?userId=${userId}&planType=${planType}`)
  }
}