import request from './request'
import type { InviteCommission } from './types'

/**
 * 获取用户佣金信息
 * @param userId 用户ID
 */
export const getInviteCommission = (userId: number): Promise<InviteCommission> => {
  return request.get(`/invite/commission/getByUserId?userId=${userId}`)
}