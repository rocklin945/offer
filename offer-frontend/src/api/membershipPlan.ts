import request from './request'
import type { BaseResponse } from './types'
import type { PlanOption } from '@/components/RedeemMemberModal.vue'

// 获取所有上架的会员套餐
export function getAllActivePlans(): Promise<BaseResponse<PlanOption[]>> {
    return request.get('/membership/plan/list').then(res => res.data)
}

export const membershipPlanApi = {
    getAllActivePlans
}