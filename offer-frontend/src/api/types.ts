// 基础响应类型
export interface BaseResponse<T = any> {
    statusCode: number;
    message: string;
    data: T;
}

// 网站信息响应类型
export interface WebInfoResponse {
    id: number;
    pv: number;
    imageUrl: string;
    userCount: number;
    memberCount: number;
    jobCount: number;
    originalPrice: number;
    currentPrice: number;
    activity1: string;
    activity2: string;
    activity3: string;
    activity4: string;
    activity5: string;
    activity1Time: string;
    activity2Time: string;
    activity3Time: string;
    activity4Time: string;
    activity5Time: string;
    updateTime: string;
}

// 网站价格响应类型
export interface WebPriceResponse {
    originalPrice: number;
    currentPrice: number;
}

// 简历类型
export interface Resume {
    id: number;
    name: string;
    phone?: string;
    wechat?: string;
    email?: string;
    gender?: string;
    birthday?: string;
    address?: string;
    education?: string;
    skills?: string;
    workExperience?: string;
    projectExperience?: string;
    internshipExperience?: string;
    certificates?: string;
    selfEvaluation?: string;
    createTime?: string;
    updateTime?: string;
}

// 简历添加请求类型
export interface ResumeAddRequest {
    name: string;
    phone?: string;
    wechat?: string;
    email?: string;
    gender?: string;
    birthday?: string;
    address?: string;
    education?: string;
    skills?: string;
    workExperience?: string;
    projectExperience?: string;
    internshipExperience?: string;
    certificates?: string;
    selfEvaluation?: string;
}

// 简历更新请求类型
export interface ResumeUpdateRequest {
    id: number;
    name: string;
    phone?: string;
    wechat?: string;
    email?: string;
    gender?: string;
    birthday?: string;
    address?: string;
    education?: string;
    skills?: string;
    workExperience?: string;
    projectExperience?: string;
    internshipExperience?: string;
    certificates?: string;
    selfEvaluation?: string;
}

// 删除请求类型
export interface DeleteRequest {
    id: number;
}