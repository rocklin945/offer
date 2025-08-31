import request from './request'
import type { BaseResponse, DeleteRequest } from './types'
import type { Resume, ResumeAddRequest, ResumeUpdateRequest } from './resumeTypes'

// 添加简历
export function addResume(data: ResumeAddRequest): Promise<BaseResponse<number>> {
    return request.post('/resume/add', data).then(res => res.data)
}

// 获取我的简历
export function getMyResume(): Promise<BaseResponse<Resume | null>> {
    return request.get('/resume/my').then(res => res.data)
}

// 根据ID获取简历
export function getResumeById(id: number): Promise<BaseResponse<Resume>> {
    return request.get(`/resume/get/${id}`).then(res => res.data)
}

// 更新简历
export function updateResume(data: ResumeUpdateRequest): Promise<BaseResponse<boolean>> {
    return request.post('/resume/update', data).then(res => res.data)
}

// 删除简历
export function deleteResume(data: DeleteRequest): Promise<BaseResponse<boolean>> {
    return request.post('/resume/delete', data).then(res => res.data)
}