<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50" @click.self="$emit('close')">
    <div class="bg-white rounded-lg shadow-xl w-full max-w-md p-6">
      <!-- 标题 -->
      <div class="flex justify-between items-center mb-4">
        <h2 class="text-xl font-semibold text-gray-800">{{ isLogin ? '用户登录' : '用户注册' }}</h2>
        <button @click="$emit('close')" class="text-gray-500 hover:text-gray-700">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
      </div>

      <!-- 表单 -->
      <form @submit.prevent="isLogin ? handleLogin() : handleRegister()">
        <!-- 账号 -->
        <div class="mb-4">
          <label for="userAccount" class="block text-sm font-medium text-gray-700 mb-1">账号</label>
          <input
            type="text"
            id="userAccount"
            v-model="formData.userAccount"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="请输入账号（3-12位）"
            required
            minlength="3"
            maxlength="12"
          />
        </div>

        <!-- 密码 -->
        <div class="mb-4">
          <label for="userPassword" class="block text-sm font-medium text-gray-700 mb-1">密码</label>
          <input
            type="password"
            id="userPassword"
            v-model="formData.userPassword"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="请输入密码（8-16位）"
            required
            minlength="8"
            maxlength="16"
          />
        </div>

        <!-- 确认密码（仅注册时显示） -->
        <div v-if="!isLogin" class="mb-4">
          <label for="checkPassword" class="block text-sm font-medium text-gray-700 mb-1">确认密码</label>
          <input
            type="password"
            id="checkPassword"
            v-model="formData.checkPassword"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="请再次输入密码"
            required
            minlength="8"
            maxlength="16"
          />
        </div>

        <!-- 错误信息 -->
        <div v-if="errorMessage" class="mb-4 text-red-500 text-sm">{{ errorMessage }}</div>

        <!-- 提交按钮 -->
        <div class="flex justify-between items-center">
          <button
            type="submit"
            class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
            :disabled="loading"
          >
            {{ isLogin ? '登录' : '注册' }}
            <span v-if="loading" class="ml-2">...</span>
          </button>
          <button
            type="button"
            class="text-blue-600 hover:text-blue-800 text-sm"
            @click="toggleMode"
          >
            {{ isLogin ? '没有账号？去注册' : '已有账号？去登录' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useUserStore } from '@/stores/user'
import { login, register } from '@/api/user'

const emit = defineEmits(['close', 'login-success'])

// 状态
const isLogin = ref(true)
const loading = ref(false)
const errorMessage = ref('')

// 表单数据
const formData = reactive({
  userAccount: '',
  userPassword: '',
  checkPassword: ''
})

// 用户状态管理
const userStore = useUserStore()

// 切换登录/注册模式
const toggleMode = () => {
  isLogin.value = !isLogin.value
  errorMessage.value = ''
}

// 处理登录
const handleLogin = async () => {
  try {
    loading.value = true
    errorMessage.value = ''
    
    const result = await userStore.userLogin(formData.userAccount, formData.userPassword)
    if (result) {
      emit('login-success')
      emit('close')
    }
  } catch (error: any) {
    errorMessage.value = error.message || '登录失败，请检查账号和密码'
  } finally {
    loading.value = false
  }
}

// 处理注册
const handleRegister = async () => {
  try {
    // 验证两次密码是否一致
    if (formData.userPassword !== formData.checkPassword) {
      errorMessage.value = '两次输入的密码不一致'
      return
    }
    
    loading.value = true
    errorMessage.value = ''
    
    const res = await register({
      userAccount: formData.userAccount,
      userPassword: formData.userPassword,
      checkPassword: formData.checkPassword
    })
    
    if (res.statusCode === 200) {
      // 注册成功后自动切换到登录模式
      isLogin.value = true
      errorMessage.value = '注册成功，请登录'
      formData.userPassword = ''
    }
  } catch (error: any) {
    errorMessage.value = error.message || '注册失败，请稍后再试'
  } finally {
    loading.value = false
  }
}
</script>