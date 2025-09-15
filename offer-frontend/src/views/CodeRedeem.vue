<template>
    <div class="min-h-screen bg-gradient-to-br from-teal-50 to-cyan-100 py-12 px-4 sm:px-6 lg:px-8">
        <div class="max-w-md w-full mx-auto bg-white rounded-2xl shadow-xl overflow-hidden">
            <div class="bg-gradient-to-r from-teal-500 to-cyan-600 py-8 px-6 text-center">
                <h1 class="text-3xl font-bold text-white">卡密兑换</h1>
                <p class="mt-2 text-teal-100">输入您的卡密信息兑换会员权益</p>
            </div>

            <div class="py-8 px-6 sm:px-10">
                <form @submit.prevent="handleRedeem" class="space-y-6">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">账号</label>
                        <div class="mt-1 relative rounded-md shadow-sm">
                            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                <svg class="h-5 w-5 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
                                    <path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z"
                                        clip-rule="evenodd" />
                                </svg>
                            </div>
                            <input v-model="form.userAccount" type="text"
                                class="focus:ring-teal-500 focus:border-teal-500 block w-full pl-10 py-3 border-gray-300 rounded-lg shadow-sm transition duration-300 ease-in-out transform hover:scale-[1.01]"
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
                            <input v-model="form.userPassword" type="password"
                                class="focus:ring-teal-500 focus:border-teal-500 block w-full pl-10 py-3 border-gray-300 rounded-lg shadow-sm transition duration-300 ease-in-out transform hover:scale-[1.01]"
                                placeholder="请输入您的密码" required />
                        </div>
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">卡密</label>
                        <div class="mt-1 relative rounded-md shadow-sm">
                            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                <svg class="h-5 w-5 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
                                    <path fill-rule="evenodd"
                                        d="M18 8a6 6 0 01-7.743 5.743L10 14l-1 1-1 1H6v2H2v-4l4.257-4.257A6 6 0 1118 8zm-6-4a1 1 0 100 2 2 2 0 012 2 1 1 0 102 0 4 4 0 00-4-4z"
                                        clip-rule="evenodd" />
                                </svg>
                            </div>
                            <input v-model="form.code" type="text"
                                class="focus:ring-teal-500 focus:border-teal-500 block w-full pl-10 py-3 border-gray-300 rounded-lg shadow-sm transition duration-300 ease-in-out transform hover:scale-[1.01]"
                                placeholder="请输入您的卡密" required />
                        </div>
                    </div>

                    <div>
                        <button type="submit" :disabled="loading"
                            class="w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white bg-gradient-to-r from-teal-500 to-cyan-600 hover:from-teal-600 hover:to-cyan-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500 transition duration-300 ease-in-out transform hover:scale-[1.02] disabled:opacity-50">
                            <svg v-if="loading" class="animate-spin -ml-1 mr-3 h-5 w-5 text-white"
                                xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor"
                                    stroke-width="4"></circle>
                                <path class="opacity-75" fill="currentColor"
                                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z">
                                </path>
                            </svg>
                            {{ loading ? '兑换中...' : '立即兑换' }}
                        </button>
                    </div>
                </form>

                <div v-if="message" :class="[
                    'mt-6 p-4 rounded-lg text-center',
                    success ? 'bg-green-50 text-green-800 border border-green-200' : 'bg-red-50 text-red-800 border border-red-200'
                ]">
                    {{ message }}
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { codeApi } from '@/api/code'

// 用户状态管理
const userStore = useUserStore()
const router = useRouter()

// 表单数据
const form = ref({
    userAccount: '',
    userPassword: '',
    code: ''
})

// 状态控制
const loading = ref(false)
const message = ref('')
const success = ref(false)

// 卡密兑换处理
const handleRedeem = async () => {
    if (!form.value.userAccount || !form.value.userPassword || !form.value.code) {
        message.value = '请填写所有字段'
        success.value = false
        return
    }

    loading.value = true
    message.value = ''
    success.value = false

    try {
        // 先登录
        await userStore.userLogin(form.value.userAccount, form.value.userPassword)

        // 调用卡密兑换API
        const res = await codeApi.redeemCode({ code: form.value.code })

        if (res.statusCode === 200) {
            message.value = res.data.message || '卡密兑换成功！'
            success.value = res.data.success

            // 如果兑换成功，清空卡密输入框
            if (res.data.success) {
                form.value.code = ''
            }
        } else {
            message.value = res.message || '兑换失败'
            success.value = false
        }
    } catch (error: any) {
        console.error('兑换失败', error)
        message.value = error.message || '兑换失败，请检查账号密码和卡密'
        success.value = false
    } finally {
        loading.value = false
    }
}
</script>

<style scoped>
/* 可以添加一些自定义样式 */
</style>