import request from './request'

export interface CreateOrderRequest {
    type: string  // 支付方式 alipay 或 wxpay
    money: string  // 金额
    name: string  // 商品名称
    param?: string  // 附加参数
    cid?: string   // 支付渠道
}

// 创建支付订单
export function createOrder(data: CreateOrderRequest): Promise<string> {
    return request.post('/pay/createOrder', data).then(res => res.data)
}

export const payApi = {
    createOrder
}