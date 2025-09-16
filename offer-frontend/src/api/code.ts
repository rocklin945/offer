import request from './request'
import type { BaseResponse } from './types'

// 卡密兑换请求参数
export interface RedeemCodeRequest {
    account: string
    password: string
    code: string
}

// 卡密兑换响应数据
export interface RedeemCodeResponse {
    success: boolean
    message: string
    // 可以根据实际需求添加更多字段
}

// 获取卡密商品价格响应数据
export interface CodePriceResponse {
    autumnPrice: number
    campusPrice: number
}

// 网站价格响应数据
export interface WebPriceResponse {
    originalPrice: number
    currentPrice: number
}

// 卡密购买请求参数
export interface PurchaseCodeRequest {
    price: string
    quantity: number
}

// 卡密购买响应数据
export interface PurchaseCodeResponse {
    // 根据实际返回的数据结构进行调整
    [key: string]: any
}

/**
 * 卡密兑换
 * @param data 兑换请求参数
 */
export function redeemCode(data: RedeemCodeRequest): Promise<BaseResponse<RedeemCodeResponse>> {
    return request.post('/code/redeem', data).then(res => res.data)
}

/**
 * 根据商家账号获取卡密商品价格
 * @param account 商家账号
 * @param password 商家密码
 */
export function getCodePrice(account: string, password: string): Promise<BaseResponse<CodePriceResponse>> {
    return request.get('/code/price', {
        params: { account, password }
    }).then(res => res.data)
}

/**
 * 获取网站价格信息
 */
export function getWebPrice(): Promise<BaseResponse<WebPriceResponse>> {
    return request.get('/webInfo/price').then(res => res.data)
}

/**
 * 卡密返回接口
 * @param params 返回参数
 */
export function returnCode(params: Record<string, string>): Promise<string[]> {
    return request.get('/code/return', { params }).then(res => res.data)
}

/**
 * 购买卡密
 * @param data 购买请求参数
 * @param account 商家账号
 * @param password 商家密码
 */
export function purchaseCode(data: PurchaseCodeRequest, account?: string, password?: string): Promise<BaseResponse<string>> {
    // 如果提供了账号和密码，则作为查询参数传递
    const config: any = {};
    if (account !== undefined && password !== undefined) {
        config.params = { account, password };
    }
    return request.post('/code/purchase', data, config).then(res => res.data)
}

export const codeApi = {
    redeemCode,
    getCodePrice,
    getWebPrice,
    returnCode,
    purchaseCode
}