// 基础响应类型
export interface BaseResponse<T = any> {
    statusCode: number;
    message: string;
    data: T;
}

// 分页响应类型
export interface PageResponse<T> {
    list: T[];
    total: number;
    pageNum: number;
    pageSize: number;
    hasNext: boolean;
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

// 倒计时响应类型
export interface CountResponse {
    // 26届校招结束时间（时间戳，毫秒）
    endTime: number;
    
    // 26届校招累计新开数量
    totalOpenCount: number;
    
    // 本月新开校招数量
    monthOpenCount: number;
    
    // 近三天新开校招数量
    lastThreeDaysOpenCount: number;
    
    // 内推企业总量
    referralCompanyCount: number;
}

// 招聘信息类型
export interface JobInfo {
    id: string;
    companyName: string;
    companyType?: string;
    industry?: string;
    recruitType?: string;
    workLocation?: string;
    recruitTarget?: string;
    positionName?: string;
    startTime?: string;
    deadline?: string;
    relatedLink?: string;
    announcement?: string;
    referralCode?: string;
    remark?: string;
    applicationStatus?: string;
    createTime?: string;
    updateTime?: string;
}

// 招聘信息查询请求类型
export interface JobInfoQueryRequest {
    pageNum?: number;
    pageSize?: number;
    companyName?: string;
    companyType?: string;
    industry?: string;
    recruitType?: string;
    workLocation?: string;
    recruitTarget?: string;
    positionName?: string;
    applicationStatus?: string;
    startTime?: string;
    deadline?: string;
    sortField?: string;
    sortOrder?: string;
    onlyShowInnerCompany?: boolean; // 内推企业筛选字段
}

// 招聘信息添加请求类型
export interface JobInfoAddRequest {
    companyName: string;
    companyType?: string;
    industry?: string;
    recruitType?: string;
    workLocation?: string;
    recruitTarget?: string;
    positionName?: string;
    startTime?: string;
    deadline?: string;
    relatedLink?: string;
    announcement?: string;
    referralCode?: string;
    remark?: string;
}

// 招聘信息更新请求类型
export interface JobInfoUpdateRequest extends JobInfoAddRequest {
    id: string;
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