import request from './request'

export interface PayOrder {
    id: number
    outTradeNo: string
    tradeNo: string
    userId: number
    name: string
    money: number
    type: string
    status: number
    param: string
    notifyTime: number
    createTime: number
    updateTime: number
}

export interface PayOrderPageRequest {
    pageNum: number
    pageSize: number
    id?: number
    outTradeNo?: string
    tradeNo?: string
    userId?: number
    money?: number
    status?: number
    sortField?: string
    sortOrder?: string
}

export interface PayOrderQueryRequest {
    outTradeNo?: string
    tradeNo?: string
}

export interface OrderDetailResponse {
    code: string
    msg: string
    status: string
    name: string
    money: string
    type: string
    param: string
    pid: string
    buyer: string
    out_trade_no: string
    trade_no: string
    addtime: string
    endtime: string
}

export interface PageResponse<T> {
    list: T[]
    total: number | string
    pageNum: number
    pageSize: number
    pages: number
    hasNext: boolean
    hasPrevious: boolean
}

// 管理员获取订单信息
export function adminGetOrderInfo(params: PayOrderPageRequest): Promise<PageResponse<PayOrder>> {
    return request.post('/pay/listOrder', params).then(res => res.data.data)
}

// 管理员查询第三方支付订单信息
export function adminGetOrderDetail(params: PayOrderQueryRequest): Promise<OrderDetailResponse> {
    return request.post('/pay/getOrderDetail', params).then(res => res.data.data)
}