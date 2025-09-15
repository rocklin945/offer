import request from './request'
import type { BaseResponse } from './types'

// 商家信息
export interface Merchant {
    id: number
    account: string
    autumnPrice: number
    campusPrice: number
    // 可以根据实际需求添加更多字段
}

/**
 * 获取所有商家列表
 */
export function getMerchants(): Promise<BaseResponse<Merchant[]>> {
    return request.get('/merchant/list').then(res => res.data)
}

/**
 * 根据ID获取商家信息
 * @param id 商家ID
 */
export function getMerchantById(id: number): Promise<BaseResponse<Merchant>> {
    return request.get(`/merchant/${id}`).then(res => res.data)
}

export const merchantApi = {
    getMerchants,
    getMerchantById
}