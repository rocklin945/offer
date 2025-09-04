import request from './request'
import type { BaseResponse } from './types'
import type { UserLoginRequest, UserLoginResponse, UserPageQueryRequest, UserUpdateRequest, UserRegisterRequest } from '@/api/userTypes'

// 用户登录
export function login(data: UserLoginRequest): Promise<BaseResponse<UserLoginResponse>> {
  return request.post('/user/login', data).then(res => res.data)
}

// 用户注册
export function register(data: UserRegisterRequest): Promise<BaseResponse<any>> {
  return request.post('/user/register', data).then(res => res.data)
}

// 获取当前登录用户信息
export function getCurrentUser(): Promise<BaseResponse<UserLoginResponse>> {
  return request.post('/user/getCurrentUser').then(res => res.data)
}

// 用户登出
export function logout(): Promise<BaseResponse<void>> {
  return request.post('/user/logout').then(res => res.data)
}

// 管理员接口：根据ID获取用户
export function getUserById(id: string): Promise<BaseResponse<UserLoginResponse>> {
  return request.get(`/user/getById?id=${id}`).then(res => res.data)
}

// 管理员接口：删除用户
export function deleteUser(id: string): Promise<BaseResponse<boolean>> {
  return request.post(`/user/delete?id=${id}`).then(res => res.data)
}

// 管理员接口：更新用户
export function updateUser(data: UserUpdateRequest): Promise<BaseResponse<boolean>> {
  return request.post('/user/update', data).then(res => res.data)
}

// 管理员接口：分页获取用户列表
export function listUserByPage(data: UserPageQueryRequest): Promise<BaseResponse<any>> {
  return request.post('/user/list/page', data).then(res => res.data)
}