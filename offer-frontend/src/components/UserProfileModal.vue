<template>
    <teleport to="body">
        <div class="modal-backdrop fixed inset-0 bg-black bg-opacity-60" style="z-index: 99999;"></div>
        <div class="modal-container fixed inset-0 flex items-center justify-center" style="z-index: 100000;"
            @click.self="closeModal">
            <div class="bg-white rounded-lg shadow-xl w-full max-w-md p-6 relative">
                <div class="flex justify-between items-center mb-4">
                    <h2 class="text-xl font-semibold text-gray-800">个人信息</h2>
                    <button @click="closeModal" class="text-gray-500 hover:text-gray-700">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M6 18L18 6M6 6l12 12"></path>
                        </svg>
                    </button>
                </div>

                <div class="mt-2">
                    <!-- 用户头像 -->
                    <div class="flex justify-center mb-4">
                        <img :src="userInfo.userAvatar" alt="用户头像" class="w-24 h-24 rounded-full object-cover" />
                    </div>

                    <!-- 用户信息 -->
                    <div class="space-y-4">
                        <div>
                            <label class="block text-sm font-medium text-gray-700">用户名</label>
                            <div class="mt-1 text-sm text-gray-900">{{ userInfo.userName }}</div>
                        </div>

                        <div>
                            <label class="block text-sm font-medium text-gray-700">账号</label>
                            <div class="mt-1 text-sm text-gray-900">{{ userInfo.userAccount }}</div>
                        </div>

                        <div>
                            <label class="block text-sm font-medium text-gray-700">用户简介</label>
                            <div class="mt-1 text-sm text-gray-900 whitespace-pre-wrap">{{ userInfo.userProfile ||
                                '暂无简介' }}</div>
                        </div>
                    </div>
                </div>

                <div class="mt-6 flex justify-between">
                    <button type="button"
                        class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
                        @click="openEditModal">
                        修改信息
                    </button>
                    <button type="button"
                        class="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2"
                        @click="closeModal">
                        关闭
                    </button>
                </div>
            </div>
        </div>
    </teleport>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import * as userApi from '../api/user'

// 定义 emits
const emit = defineEmits(['close', 'open-edit'])

// 用户信息
const userInfo = ref({
    userId: 0,
    userName: '',
    userAccount: '',
    userAvatar: '',
    userProfile: ''
})

// 获取用户信息
const fetchUserInfo = async () => {
    try {
        const res = await userApi.getCurrentUser()
        userInfo.value = res.data
    } catch (error) {
        console.error('获取用户信息失败:', error)
    }
}

// 关闭弹窗
const closeModal = () => {
    emit('close')
}

// 打开编辑弹窗
const openEditModal = () => {
    // 先触发打开编辑弹窗的事件，再关闭当前弹窗
    emit('open-edit', userInfo.value)
    emit('close')
}

// 组件挂载时获取用户信息
onMounted(() => {
    fetchUserInfo()
})
</script>

<style scoped>
.modal-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
}

.modal-container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
}
</style>