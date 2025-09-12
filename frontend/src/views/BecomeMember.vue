// 选择支付方式
const selectPaymentMethod = async (method: string) => {
closePaymentModal()

if (method === 'wxpay') {
// 微信支付，显示二维码
showWechatModal.value = true
// 重新获取二维码图片
await fetchMemberImageUrl()
} else {
// 支付宝支付，在当前页面处理
try {
const money = selectedPlan.value === 'current' ? priceInfo.value.currentPrice.toString() :
priceInfo.value.originalPrice.toString()
const response = await payApi.createOrder({
type: 'alipay',
money: money,
name: selectedPlan.value === 'current' ? '秋招会员' : '校招直通会员'
})

if (response.statusCode === 200 && response.data) {
// 在当前页面打开支付宝支付页面
const newWindow = window.open(response.data, '_blank')
if (!newWindow) {
Message.warning('请允许弹窗以完成支付')
}
} else {
Message.error('创建订单失败，请稍后重试')
}
} catch (error) {
console.error('创建订单失败:', error)
Message.error('创建订单失败，请稍后重试')
}
}
}