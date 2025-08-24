import request from './request'
import type { BaseResponse, WebInfoResponse } from './types'

// 获取网站信息
export function getWebInfo(): Promise<BaseResponse<WebInfoResponse>> {
    return request.get('/webInfo/get').then(res => res.data)
}

export const webInfoApi = {
    getWebInfo
}