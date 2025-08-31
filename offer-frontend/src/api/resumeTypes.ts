// 简历相关类型定义

export interface Resume {
    id: number
    userId: number
    name: string
    phone?: string
    wechat?: string
    email?: string
    gender?: string
    birthday?: string
    address?: string
    education?: string
    skills?: string
    workExperience?: string
    projectExperience?: string
    internshipExperience?: string
    certificates?: string
    selfEvaluation?: string
    createTime?: string
    updateTime?: string
}

export interface ResumeAddRequest {
    name: string
    phone?: string
    wechat?: string
    email?: string
    gender?: string
    birthday?: string
    address?: string
    education?: string
    skills?: string
    workExperience?: string
    projectExperience?: string
    internshipExperience?: string
    certificates?: string
    selfEvaluation?: string
}

export interface ResumeUpdateRequest {
    id: number
    name?: string
    phone?: string
    wechat?: string
    email?: string
    gender?: string
    birthday?: string
    address?: string
    education?: string
    skills?: string
    workExperience?: string
    projectExperience?: string
    internshipExperience?: string
    certificates?: string
    selfEvaluation?: string
}