import axios, { AxiosInstance, InternalAxiosRequestConfig, AxiosResponse } from 'axios'
import type { BaseResponse } from './types'

// 创建axios实例
const request: AxiosInstance = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
request.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 可以在这里添加token等认证信息
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  (response: AxiosResponse<BaseResponse>) => {
    const { data } = response
    
    // 如果是健康检查接口，直接返回
    if (typeof data === 'string') {
      return response
    }
    
    // 检查业务状态码 - 后端使用statusCode字段，成功时为200
    if (data.statusCode !== 200) {
      console.error('API Error:', data.message)
      return Promise.reject(new Error(data.message || '请求失败'))
    }
    
    return response
  },
  (error) => {
    console.error('Request Error:', error)
    return Promise.reject(error)
  }
)

export default request