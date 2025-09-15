<template>
    <teleport to="body">
        <div class="fixed inset-0 bg-gradient-to-br from-orange-100 to-amber-100">
            <div class="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
                <div class="max-w-md w-full bg-white rounded-2xl shadow-xl overflow-hidden">
                    <div class="bg-gradient-to-r from-orange-300 to-amber-300 py-8 px-6 text-center">
                        <h1 class="text-3xl font-bold text-gray-800">卡密购买</h1>
                        <p class="mt-2 text-orange-800">选择您需要的卡密商品并完成购买</p>
                    </div>

                    <!-- 未登录状态 -->
                    <div v-if="!isLoggedIn" class="py-8 px-6 sm:px-10">
                        <form @submit.prevent="handleLogin" class="space-y-6">
                            <div>
                                <label class="block text-sm font-medium text-gray-700 mb-1">账号</label>
                                <div class="mt-1 relative rounded-md shadow-sm">
                                    <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
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
                                    <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
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

                    <!-- 已登录状态 -->
                    <div v-else class="bg-white rounded-2xl shadow-xl overflow-hidden">
                        <div class="bg-gradient-to-r from-orange-300 to-amber-300 py-8 px-6 text-center">
                            <h2 class="text-2xl font-bold text-gray-800">选择卡密商品</h2>
                            <p class="mt-2 text-orange-800">欢迎，{{ userStore.currentUser?.userName ||
                                userStore.currentUser?.userAccount }}</p>
                        </div>

                        <div class="py-8 px-6 sm:px-10">
                            <!-- 商品列表 -->
                            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
                                <div v-for="product in products" :key="product.id" @click="selectProduct(product)"
                                    :class="[
                                        'border rounded-xl p-6 cursor-pointer transition-all duration-300 transform hover:scale-[1.02]',
                                        selectedProduct && selectedProduct.id === product.id
                                            ? 'border-orange-500 bg-orange-50 ring-2 ring-orange-200'
                                            : 'border-gray-200 hover:border-orange-300 hover:bg-orange-50'
                                    ]">
                                    <div class="flex items-start">
                                        <div class="flex-shrink-0">
                                            <div class="bg-orange-100 rounded-lg p-3">
                                                <svg class="h-8 w-8 text-orange-600" fill="none" stroke="currentColor"
                                                    viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                        stroke-width="2"
                                                        d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4">
                                                    </path>
                                                </svg>
                                            </div>
                                        </div>
                                        <div class="ml-4">
                                            <h3 class="text-lg font-semibold text-gray-900">{{ product.name }}</h3>
                                            <p class="mt-1 text-sm text-gray-500">{{ product.description }}</p>
                                            <div class="mt-3 flex items-center">
                                                <span class="text-xl font-bold text-orange-600">¥{{ product.price
                                                    }}</span>
                                                <span v-if="product.stock < 10"
                                                    class="ml-2 inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-red-100 text-red-800">
                                                    仅剩{{ product.stock }}件
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- 购买表单 -->
                            <div v-if="selectedProduct" class="border-t border-gray-200 pt-8">
                                <h3 class="text-lg font-medium text-gray-900 mb-6">购买信息</h3>

                                <form @submit.prevent="handlePurchase" class="space-y-6">
                                    <div>
                                        <label class="block text-sm font-medium text-gray-700 mb-1">购买数量</label>
                                        <div class="mt-1 relative rounded-md shadow-sm">
                                            <input v-model.number="quantity" type="number" min="1"
                                                :max="selectedProduct.stock"
                                                class="focus:ring-orange-500 focus:border-orange-500 block w-full py-3 border-gray-300 rounded-lg shadow-sm transition duration-300 ease-in-out transform hover:scale-[1.01]"
                                                placeholder="请输入购买数量" required />
                                        </div>
                                        <p class="mt-1 text-sm text-gray-500">库存：{{ selectedProduct.stock }}件</p>
                                    </div>

                                    <div class="bg-gray-50 rounded-lg p-4">
                                        <div class="flex justify-between items-center">
                                            <span class="text-gray-600">总计：</span>
                                            <span class="text-xl font-bold text-gray-900">
                                                ¥{{ (selectedProduct.price * quantity).toFixed(2) }}
                                            </span>
                                        </div>
                                    </div>

                                    <div class="flex space-x-4">
                                        <button type="button" @click="selectedProduct = null"
                                            class="flex-1 py-3 px-4 border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-orange-500 transition duration-300">
                                            重新选择
                                        </button>
                                        <button type="submit" :disabled="loading"
                                            class="flex-1 flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-gray-800 bg-gradient-to-r from-orange-300 to-amber-300 hover:from-orange-400 hover:to-amber-400 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-orange-200 transition duration-300 ease-in-out disabled:opacity-50">
                                            <svg v-if="loading" class="animate-spin -ml-1 mr-3 h-5 w-5 text-gray-800"
                                                xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor"
                                                    stroke-width="4"></circle>
                                                <path class="opacity-75" fill="currentColor"
                                                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z">
                                                </path>
                                            </svg>
                                            {{ loading ? '处理中...' : '立即购买' }}
                                        </button>
                                    </div>
                                </form>
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
            </div>
        </div>
    </teleport>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { codeApi } from '@/api/code'

// 用户状态管理
const userStore = useUserStore()
const router = useRouter()

// 登录表单数据
const loginForm = ref({
    userAccount: '',
    userPassword: ''
})

// 商品相关数据
const products = ref<any[]>([])
const selectedProduct = ref<any>(null)
const quantity = ref(1)

// 状态控制
const loginLoading = ref(false)
const loading = ref(false)
const loginMessage = ref('')
const message = ref('')
const loginSuccess = ref(false)
const success = ref(false)

// 计算属性：是否已登录
const isLoggedIn = computed(() => {
    return userStore.currentUser && userStore.token
})

// 选择商品
const selectProduct = (product: any) => {
    selectedProduct.value = product
    quantity.value = 1
}

// 获取卡密商品列表
const fetchProducts = async () => {
    try {
        const res = await codeApi.getCodeProducts()
        if (res.statusCode === 200) {
            products.value = res.data
        }
    } catch (error) {
        console.error('获取商品列表失败', error)
    }
}

// 页面加载时获取商品列表
onMounted(() => {
    fetchProducts()
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
        // 登录
        await userStore.userLogin(loginForm.value.userAccount, loginForm.value.userPassword)

        loginMessage.value = '登录成功'
        loginSuccess.value = true

        // 清空登录表单
        loginForm.value.userAccount = ''
        loginForm.value.userPassword = ''
    } catch (error: any) {
        console.error('登录失败', error)
        loginMessage.value = error.message || '登录失败，请检查账号密码'
        loginSuccess.value = false
    } finally {
        loginLoading.value = false
    }
}

// 卡密购买处理
const handlePurchase = async () => {
    if (!selectedProduct.value) {
        message.value = '请选择要购买的商品'
        success.value = false
        return
    }

    if (quantity.value < 1 || quantity.value > selectedProduct.value.stock) {
        message.value = `购买数量必须在1-${selectedProduct.value.stock}之间`
        success.value = false
        return
    }

    loading.value = true
    message.value = ''
    success.value = false

    try {
        // 调用购买API
        const res = await codeApi.purchaseCode({
            productId: selectedProduct.value.id,
            quantity: quantity.value
        })

        if (res.statusCode === 200) {
            message.value = '购买成功，请前往支付页面完成支付'
            success.value = true

            // 可以在这里跳转到支付页面
            // setTimeout(() => {
            //   window.open(res.data.payUrl, '_blank')
            // }, 1500)
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