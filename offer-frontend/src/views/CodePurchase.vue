<template>
    <div>
        <teleport to="body">
            <div class="fixed inset-0 bg-gradient-to-br from-orange-100 to-amber-100">
                <!-- 登录状态下的大尺寸布局 -->
                <div v-if="isLoggedIn" class="min-h-full flex items-center justify-center p-4">
                    <div class="w-full max-w-4xl bg-white rounded-2xl shadow-xl overflow-hidden">
                        <div class="bg-gradient-to-r from-orange-300 to-amber-300 py-8 px-6 text-center">
                            <h1 class="text-3xl font-bold text-gray-800">卡密购买</h1>
                            <p class="mt-2 text-orange-800">选择您需要的卡密商品并完成购买</p>
                        </div>

                        <div class="bg-white py-8 px-6 text-center border-b border-gray-200">
                            <div class="flex justify-between items-center">
                                <div>
                                    <h2 class="text-2xl font-bold text-gray-800">选择卡密商品</h2>
                                    <p class="mt-1 text-gray-600">欢迎，{{ getMerchantAccount() }}</p>
                                </div>
                                <button @click="handleLogout"
                                    class="py-2 px-4 border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-200 transition duration-300">
                                    退出登录
                                </button>
                            </div>
                        </div>

                        <div class="py-8 px-6 sm:px-10">
                            <!-- 商品列表 -->
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
                                <!-- 秋招会员卡密 -->
                                <div v-for="product in products" :key="product.id" @click="selectProduct(product)"
                                    :class="[
                                        'border rounded-xl p-6 cursor-pointer transition-all duration-300 transform hover:scale-[1.02]',
                                        selectedProduct && selectedProduct.id === product.id
                                            ? 'ring-2 ring-amber-300 border-amber-300' : 'border-gray-200 hover:border-amber-200'
                                    ]">
                                    <div class="flex items-start">
                                        <div class="flex-shrink-0">
                                            <div :class="[
                                                'rounded-lg p-3',
                                                product.id === 1 ? 'bg-blue-100' : 'bg-purple-100'
                                            ]">
                                                <svg v-if="product.id === 1" class="h-8 w-8 text-blue-600" fill="none"
                                                    stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                        stroke-width="2"
                                                        d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                                                </svg>
                                                <svg v-else class="h-8 w-8 text-purple-600" fill="none"
                                                    stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                        stroke-width="2"
                                                        d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
                                                </svg>
                                            </div>
                                        </div>
                                        <div class="ml-4 flex-1">
                                            <h3 class="text-lg font-semibold text-gray-900">{{ product.name }}</h3>
                                            <p class="mt-1 text-sm text-gray-500">{{ product.description }}</p>
                                            <div class="mt-3 flex items-center">
                                                <span class="text-xl font-bold text-gray-900">¥{{ product.price
                                                    }}</span>
                                                <!-- 显示被划掉的原价 -->
                                                <span v-if="webPrices && product.id === 2"
                                                    class="ml-2 text-gray-500 line-through">¥{{ webPrices.originalPrice
                                                    }}</span>
                                                <span v-else-if="webPrices && product.id === 1"
                                                    class="ml-2 text-gray-500 line-through">¥{{ webPrices.currentPrice
                                                    }}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- 精简的购买控件 -->
                            <div v-if="selectedProduct" class="mt-8 bg-gray-50 rounded-xl p-6">
                                <div class="flex items-center justify-between">
                                    <div class="flex items-center">
                                        <span class="text-gray-700 mr-4">数量:</span>
                                        <div class="flex items-center">
                                            <button @click="decreaseQuantity"
                                                class="w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-500">
                                                <svg class="w-4 h-4 text-gray-700" fill="none" stroke="currentColor"
                                                    viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                        stroke-width="2" d="M20 12H4" />
                                                </svg>
                                            </button>
                                            <span class="mx-3 text-lg font-semibold text-gray-900 w-8 text-center">{{
                                                quantity }}</span>
                                            <button @click="increaseQuantity"
                                                class="w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-500">
                                                <svg class="w-4 h-4 text-gray-700" fill="none" stroke="currentColor"
                                                    viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                        stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                                                </svg>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="flex items-center">
                                        <span class="text-gray-700 mr-4">总价:</span>
                                        <span class="text-xl font-bold text-gray-900 mr-6">¥{{ (selectedProduct.price *
                                            quantity).toFixed(2) }}</span>
                                        <button @click="handlePurchase" :disabled="loading"
                                            class="py-2 px-6 bg-gradient-to-r from-amber-400 to-amber-500 hover:from-amber-500 hover:to-amber-600 text-white font-medium rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-amber-300 focus:ring-offset-2 transition duration-300 flex items-center">
                                            <svg v-if="loading" class="animate-spin -ml-1 mr-2 h-4 w-4 text-white"
                                                xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor"
                                                    stroke-width="4"></circle>
                                                <path class="opacity-75" fill="currentColor"
                                                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z">
                                                </path>
                                            </svg>
                                            购买
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <div v-if="message" :class="[
                                'mt-6 p-4 rounded-lg text-center',
                                success ? 'bg-green-50 text-green-800 border border-green-200' : 'bg-red-50 text-red-800 border border-red-200'
                            ]">
                                {{ message }}
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 未登录状态下的小尺寸布局 -->
                <div v-else class="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
                    <div class="max-w-md w-full bg-white rounded-2xl shadow-xl overflow-hidden">
                        <div class="bg-gradient-to-r from-orange-300 to-amber-300 py-8 px-6 text-center">
                            <h1 class="text-3xl font-bold text-gray-800">卡密购买</h1>
                            <p class="mt-2 text-orange-800">选择您需要的卡密商品并完成购买</p>
                        </div>

                        <div class="py-8 px-6 sm:px-10">
                            <form @submit.prevent="handleLogin" class="space-y-6">
                                <div>
                                    <label class="block text-sm font-medium text-gray-700 mb-1">账号</label>
                                    <div class="mt-1 relative rounded-md shadow-sm">
                                        <div
                                            class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                            <svg class="h-5 w-5 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
                                                <path fill-rule="evenodd"
                                                    d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z"
                                                    clip-rule="evenodd" />
                                            </svg>
                                        </div>
                                        <input v-model="loginForm.userAccount" type="text"
                                            class="focus:ring-orange-500 focus:border-orange-500 block w-full pl-10 py-3 border-gray-300 rounded-lg shadow-sm transition duration-300 ease-in-out transform hover:scale-[1.01]"
                                            placeholder="请输入您的账号" required />
                                    </div>
                                </div>

                                <div>
                                    <label class="block text-sm font-medium text-gray-700 mb-1">密码</label>
                                    <div class="mt-1 relative rounded-md shadow-sm">
                                        <div
                                            class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                            <svg class="h-5 w-5 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
                                                <path fill-rule="evenodd"
                                                    d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z"
                                                    clip-rule="evenodd" />
                                            </svg>
                                        </div>
                                        <input v-model="loginForm.userPassword" type="password"
                                            class="focus:ring-orange-500 focus:border-orange-500 block w-full pl-10 py-3 border-gray-300 rounded-lg shadow-sm transition duration-300 ease-in-out transform hover:scale-[1.01]"
                                            placeholder="请输入您的密码" required />
                                    </div>
                                </div>

                                <div>
                                    <button type="submit" :disabled="loginLoading"
                                        class="w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-gray-800 bg-gradient-to-r from-orange-300 to-amber-300 hover:from-orange-400 hover:to-amber-400 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-orange-200 transition duration-300 ease-in-out transform hover:scale-[1.02] disabled:opacity-50">
                                        <svg v-if="loginLoading" class="animate-spin -ml-1 mr-3 h-5 w-5 text-gray-800"
                                            xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                            <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor"
                                                stroke-width="4"></circle>
                                            <path class="opacity-75" fill="currentColor"
                                                d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z">
                                            </path>
                                        </svg>
                                        {{ loginLoading ? '登录中...' : '立即登录' }}
                                    </button>
                                </div>

                                <div v-if="loginMessage" :class="[
                                    'mt-6 p-4 rounded-lg text-center',
                                    loginSuccess ? 'bg-green-50 text-green-800 border border-green-200' : 'bg-red-50 text-red-800 border border-red-200'
                                ]">
                                    {{ loginMessage }}
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </teleport>

        <!-- 卡密显示弹窗 -->
        <teleport to="body">
            <div v-if="showCodesModal"
                class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
                <div class="bg-white rounded-xl shadow-2xl max-w-2xl w-full max-h-[80vh] overflow-hidden">
                    <div class="bg-gradient-to-r from-amber-500 to-amber-600 py-4 px-6">
                        <h3 class="text-xl font-bold text-white">购买成功</h3>
                    </div>
                    <div class="p-6 overflow-y-auto max-h-[60vh]">
                        <p class="text-gray-700 mb-4">您的卡密已生成，请妥善保管：</p>
                        <div class="space-y-3">
                            <div v-for="(code, index) in purchasedCodes" :key="index"
                                class="flex items-center justify-between bg-gray-50 rounded-lg p-4 border border-gray-200">
                                <span class="font-mono text-gray-800">{{ code }}</span>
                                <button @click="copyCode(code)"
                                    class="ml-4 py-1 px-3 text-sm bg-amber-100 hover:bg-amber-200 text-amber-700 rounded-lg transition duration-200">
                                    复制
                                </button>
                            </div>
                        </div>
                        <div class="mt-6 flex justify-end">
                            <button @click="closeCodesModal"
                                class="py-2 px-6 bg-gradient-to-r from-amber-400 to-amber-500 hover:from-amber-500 hover:to-amber-600 text-white font-medium rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-amber-300 focus:ring-offset-2 transition duration-300">
                                确定
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </teleport>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { codeApi } from '@/api/code'
import { Message } from '@/components/Message'

// 用户状态管理
const userStore = useUserStore()
const router = useRouter()

// 登录表单数据
const loginForm = ref({
    userAccount: '',
    userPassword: ''
})

// 商品相关数据
const products = ref<any[]>([
    {
        id: 1,
        name: '秋招会员卡密',
        description: '适用于秋季招聘季，有效期120天',
        price: 0 // 价格将从接口获取
    },
    {
        id: 2,
        name: '校招会员卡密',
        description: '适用于校园招聘，有效期365天',
        price: 0 // 价格将从接口获取
    }
])
const selectedProduct = ref<any>(null)
const quantity = ref(1)
const webPrices = ref<any>(null) // 网站价格信息

// 状态控制
const loginLoading = ref(false)
const loading = ref(false)
const loginMessage = ref('')
const message = ref('')
const loginSuccess = ref(false)
const success = ref(false)
const isLoggedIn = ref(false) // 商家登录状态

// 卡密弹窗控制
const showCodesModal = ref(false)
const purchasedCodes = ref<string[]>([])

// 商家账号密码存储键名
const MERCHANT_ACCOUNT_KEY = 'merchant_account'
const MERCHANT_PASSWORD_KEY = 'merchant_password'

// 获取商家账号
const getMerchantAccount = () => {
    return localStorage.getItem(MERCHANT_ACCOUNT_KEY) || loginForm.value.userAccount
}

// 选择商品
const selectProduct = (product: any) => {
    selectedProduct.value = product
    quantity.value = 1
}

// 增加数量
const increaseQuantity = () => {
    quantity.value++
}

// 减少数量
const decreaseQuantity = () => {
    if (quantity.value > 1) {
        quantity.value--
    }
}

// 复制卡密到剪贴板
const copyCode = (code: string) => {
    navigator.clipboard.writeText(code).then(() => {
        // 使用Message组件显示复制成功的提示
        Message.success('卡密已复制到剪贴板')
    }).catch(err => {
        console.error('复制失败', err)
        Message.error('复制失败，请手动复制')
    })
}

// 关闭卡密弹窗
const closeCodesModal = () => {
    showCodesModal.value = false
    purchasedCodes.value = []
}

// 获取网站价格信息
const fetchWebPrices = async () => {
    try {
        const res = await codeApi.getWebPrice()
        if (res.statusCode === 200) {
            webPrices.value = res.data
        }
    } catch (error) {
        console.error('获取网站价格失败', error)
    }
}

// 获取卡密商品价格
const fetchProductPrices = async (account: string, password: string) => {
    try {
        const res = await codeApi.getCodePrice(account, password)
        if (res.statusCode === 200) {
            // 更新商品价格
            products.value[0].price = res.data.autumnPrice
            products.value[1].price = res.data.campusPrice
            return true
        }
        return false
    } catch (error) {
        console.error('获取商品价格失败', error)
        return false
    }
}

// 页面加载时检查是否已登录并获取价格信息
onMounted(() => {
    // 获取网站价格信息
    fetchWebPrices()

    const account = localStorage.getItem(MERCHANT_ACCOUNT_KEY)
    const password = localStorage.getItem(MERCHANT_PASSWORD_KEY)

    if (account && password) {
        // 验证账号密码是否有效
        fetchProductPrices(account, password).then(isValid => {
            if (isValid) {
                isLoggedIn.value = true
            } else {
                // 如果验证失败，清除存储的账号密码
                localStorage.removeItem(MERCHANT_ACCOUNT_KEY)
                localStorage.removeItem(MERCHANT_PASSWORD_KEY)
            }
        })
    }
})

// 登录处理
const handleLogin = async () => {
    if (!loginForm.value.userAccount || !loginForm.value.userPassword) {
        loginMessage.value = '请填写账号和密码'
        loginSuccess.value = false
        return
    }

    loginLoading.value = true
    loginMessage.value = ''
    loginSuccess.value = false

    try {
        // 调用商家登录接口验证账号密码并获取价格
        const isValid = await fetchProductPrices(loginForm.value.userAccount, loginForm.value.userPassword)

        if (isValid) {
            Message.success('登录成功')
            loginMessage.value = '登录成功'
            loginSuccess.value = true
            isLoggedIn.value = true

            // 将商家账号密码存储到localStorage中
            localStorage.setItem(MERCHANT_ACCOUNT_KEY, loginForm.value.userAccount)
            localStorage.setItem(MERCHANT_PASSWORD_KEY, loginForm.value.userPassword)

            // 清空登录表单
            loginForm.value.userAccount = ''
            loginForm.value.userPassword = ''
        } else {
            loginMessage.value = '登录失败，请检查账号密码'
            loginSuccess.value = false
        }
    } catch (error: any) {
        console.error('登录失败', error)
        loginMessage.value = error.message || '登录失败，请检查账号密码'
        loginSuccess.value = false
    } finally {
        loginLoading.value = false
    }
}

// 退出登录
const handleLogout = () => {
    // 清除存储的账号密码
    localStorage.removeItem(MERCHANT_ACCOUNT_KEY)
    localStorage.removeItem(MERCHANT_PASSWORD_KEY)

    // 重置状态
    isLoggedIn.value = false
    selectedProduct.value = null
    loginForm.value.userAccount = ''
    loginForm.value.userPassword = ''
}

// 卡密购买处理
const handlePurchase = async () => {
    if (!selectedProduct.value) {
        message.value = '请选择要购买的商品'
        success.value = false
        return
    }

    loading.value = true
    message.value = ''
    success.value = false

    try {
        // 从localStorage中获取商家账号密码
        const account = localStorage.getItem(MERCHANT_ACCOUNT_KEY)
        const password = localStorage.getItem(MERCHANT_PASSWORD_KEY)

        // 检查账号密码是否存在
        if (!account || !password) {
            message.value = '商家账号信息不存在，请重新登录'
            success.value = false
            loading.value = false
            return
        }

        // 调用购买API，传递账号密码作为查询参数
        const res = await codeApi.purchaseCode({
            price: selectedProduct.value.price.toString(),
            quantity: quantity.value
        }, account, password)

        if (res.statusCode === 200) {
            // 显示卡密弹窗
            purchasedCodes.value = res.data
            showCodesModal.value = true

            // 使用Message组件显示购买成功的提示
            Message.success('购买成功！卡密已生成')

            // 清除消息提示
            message.value = ''
            success.value = false
        } else {
            message.value = res.message || '购买失败'
            success.value = false
        }
    } catch (error: any) {
        console.error('购买失败', error)
        message.value = error.message || '购买失败'
        success.value = false
    } finally {
        loading.value = false
    }
}
</script>

<style scoped>
/* 可以添加一些自定义样式 */
</style>