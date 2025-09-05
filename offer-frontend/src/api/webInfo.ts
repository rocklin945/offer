import request from './request'
import type { BaseResponse, WebInfoResponse, WebPriceResponse } from './types'

// 获取网站信息
export function getWebInfo(): Promise<BaseResponse<WebInfoResponse>> {
    return request.get('/webInfo/get').then(res => res.data)
}

// 获取会员图片URL（公开接口）
export function getMemberImageUrl(): Promise<BaseResponse<string>> {
    return request.get('/webInfo/member-image').then(res => res.data)
}

// 获取价格信息
export function getPrice(): Promise<BaseResponse<WebPriceResponse>> {
    return request.get('/webInfo/price').then(res => res.data)
}

export const webInfoApi = {
    getWebInfo,
    getMemberImageUrl,
    getPrice
}