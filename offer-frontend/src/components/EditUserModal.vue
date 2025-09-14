<template>
    <teleport to="body">
        <div class="modal-backdrop fixed inset-0 bg-black bg-opacity-60" style="z-index: 99999;"></div>
        <div class="modal-container fixed inset-0 flex items-center justify-center" style="z-index: 100000;"
            @click.self="closeModal">
            <div class="bg-white rounded-lg shadow-xl w-full max-w-md p-6 relative">
                <div class="flex justify-between items-center mb-4">
                    <h2 class="text-xl font-semibold text-gray-800">修改个人信息</h2>
                    <button @click="closeModal" class="text-gray-500 hover:text-gray-700">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M6 18L18 6M6 6l12 12"></path>
                        </svg>
                    </button>
                </div>

                <form @submit.prevent="submitForm">
                    <div class="space-y-4">
                        <!-- 用户名 (只读) -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700">用户名</label>
                            <input v-model="form.userName" type="text"
                                class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                                disabled />
                        </div>

                        <!-- 旧账号 -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700">旧账号</label>
                            <input v-model="form.oldUserAccount" type="text"
                                class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
                        </div>

                        <!-- 新账号 -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700">新账号</label>
                            <input v-model="form.userAccount" type="text"
                                class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
                        </div>

                        <!-- 旧密码 -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700">旧密码</label>
                            <input v-model="form.oldUserPassword" type="password"
                                class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
                        </div>

                        <!-- 新密码 -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700">新密码</label>
                            <input v-model="form.userPassword" type="password"
                                class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" />
                        </div>

                        <!-- 用户简介 -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700">用户简介</label>
                            <textarea v-model="form.userProfile" rows="3"
                                class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"></textarea>
                        </div>
                    </div>

                    <!-- 错误信息 -->
                    <div v-if="errorMessage" class="mt-4 text-sm text-red-500">{{ errorMessage }}</div>

                    <div class="mt-6 flex justify-between">
                        <button type="submit"
                            class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
                            :disabled="loading">
                            <span v-if="loading">保存中...</span>
                            <span v-else>保存</span>
                        </button>
                        <button type="button"
                            class="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2"
                            @click="closeModal">
                            取消
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </teleport>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import * as userApi from '../api/user'
import { Message } from './Message'

// 定义 props
const props = defineProps({
    userInfo: {
        type: Object,
        required: true
    }
})

// 定义 emits
const emit = defineEmits(['close', 'success'])

// 表单数据
const form = ref({
    id: props.userInfo.userId,
    userName: props.userInfo.userName,
    oldUserAccount: '',
    userAccount: '',
    oldUserPassword: '',
    userPassword: '',
    userProfile: props.userInfo.userProfile || ''
})

// 加载状态
const loading = ref(false)
const errorMessage = ref('')

// 关闭弹窗
const closeModal = () => {
    emit('close')
}

// 提交表单
const submitForm = async () => {
    if (!form.value.oldUserAccount) {
        errorMessage.value = '请输入旧账号'
        return
    }

    if (!form.value.oldUserPassword) {
        errorMessage.value = '请输入旧密码'
        return
    }

    loading.value = true
    errorMessage.value = ''

    try {
        // 构造符合后端接口要求的请求参数
        const req = {
            id: form.value.id,
            oldUserAccount: form.value.oldUserAccount,
            oldUserPassword: form.value.oldUserPassword,
            userAccount: form.value.userAccount,
            userPassword: form.value.userPassword || form.value.oldUserPassword, // 如果没有新密码，则使用旧密码
            userProfile: form.value.userProfile
        }

        await userApi.updateUserInfo(req)

        // 使用Message组件显示成功消息
        Message.success('信息更新成功')

        // 清除本地的JWT
        localStorage.removeItem('user_token')

        emit('success')
        closeModal()
    } catch (error: any) {
        console.error('更新失败:', error)
        errorMessage.value = error?.message || '更新失败'
    } finally {
        loading.value = false
    }
}
</script>

<style scoped>
.modal-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
}
</style>