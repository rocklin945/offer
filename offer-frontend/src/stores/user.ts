import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { UserLoginResponse } from '../api/userTypes'
import { login, logout, getCurrentUser } from '../api/user'
import { useRouter } from 'vue-router'
import Message from '../components/Message'

// JWT Token存储的key
const TOKEN_KEY = 'user_token'

export const useUserStore = defineStore('user', () => {
  const router = useRouter()
  const currentUser = ref<UserLoginResponse | null>(null)
  const token = ref<string | null>(localStorage.getItem(TOKEN_KEY))
  const loading = ref(false)

  // 初始化用户信息
  const initUserInfo = async () => {
    if (!token.value) return null

    try {
      loading.value = true
      const res = await getCurrentUser()
      if (res.statusCode === 200 && res.data) {
        currentUser.value = res.data
        return res.data
      }
    } catch (error: any) {
      console.error('获取用户信息失败', error)
      // 如果是限流错误，保持当前用户状态，不清除token
      if (error.message && error.message.includes('请求过于频繁')) {
        return null
      }
      // 其他错误（如 token 过期），清除用户信息
      removeToken()
      currentUser.value = null
      return null
    } finally {
      loading.value = false
    }
  }

  // 用户登录
  const userLogin = async (userAccount: string, userPassword: string) => {
    try {
      loading.value = true
      const res = await login({ userAccount, userPassword })
      if (res.statusCode === 200 && res.data) {
        currentUser.value = res.data
        // 保存token到localStorage
        setToken(res.data.token)
        return res.data
      }
    } catch (error: any) {
      console.error('登录失败', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  // 用户登出
  const userLogout = async () => {
    try {
      if (token.value) {
        await logout()
      }
    } catch (error: any) {
      console.error('登出失败', error)
      // 登出失败不显示错误消息，因为无论如何都会清除本地数据
    } finally {
      // 无论成功失败，都清除本地token和用户信息
      removeToken()
      currentUser.value = null
      router.push('/')
    }
  }

  // 设置token
  const setToken = (newToken: string) => {
    token.value = newToken
    localStorage.setItem(TOKEN_KEY, newToken)
  }

  // 移除token
  const removeToken = () => {
    token.value = null
    localStorage.removeItem(TOKEN_KEY)
  }

  // 判断是否为管理员
  const isAdmin = () => {
    return currentUser.value?.userRole === 0
  }

  return {
    currentUser,
    token,
    loading,
    initUserInfo,
    userLogin,
    userLogout,
    setToken,
    removeToken,
    isAdmin
  }
})