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
    idNumber?: string

    // 央国企特有字段
    govCurrentResidence?: string
    govRegisteredResidence?: string
    govNativePlace?: string
    govGaokaoPlace?: string
    govPoliticalStatus?: string
    govEmergencyName?: string
    govEmergencyPhone?: string
    govEmergencyRelation?: string

    // 银行特有字段
    bankPoliticalStatus?: string
    bankHeight?: string
    bankWeight?: string
    bankHealth?: string
    bankMarriageStatus?: string
    bankChildStatus?: string
    bankInfoChannel?: string
    bankEmergencyName?: string
    bankEmergencyPhone?: string
    bankEmergencyRelation?: string

    // 数组类型字段
    eduExperiences?: EducationExperience[]
    privateInternship?: Internship[]
    govCampusPractice?: CampusPractice[]
    govWorkExperience?: WorkExperience[]
    privateProjectExperience?: ProjectExperience[]
    bankSchoolExperience?: SchoolExperience[]
    languageLevel?: LanguageLevel[]
    honors?: string[]
    certificates?: string[]
    skillLevel?: SkillLevel[]
    privatePaper?: Paper[]
    privateCompetition?: string[]
    familyInfo?: FamilyInfo[]
    bankProfessionalCert?: ProfessionalCert[]
    bankComputerSkills?: string[]
    bankTrainingExperience?: TrainingExperience[]
    bankRewardsPunishments?: RewardPunishment[]

    // 银行特有字段
    bankSalaryExpectation?: string
    bankExpectedLocation?: string

    // 自我评价
    selfEvaluation?: string
    createTime?: string
    updateTime?: string
}

// 基本信息字段
export interface ResumeAddRequest {
    name: string
    phone?: string
    wechat?: string
    email?: string
    gender?: string
    birthday?: string
    address?: string
    idNumber?: string

    // 央国企特有字段
    govCurrentResidence?: string
    govRegisteredResidence?: string
    govNativePlace?: string
    govGaokaoPlace?: string
    govPoliticalStatus?: string
    govEmergencyName?: string
    govEmergencyPhone?: string
    govEmergencyRelation?: string

    // 银行特有字段
    bankPoliticalStatus?: string
    bankHeight?: string
    bankWeight?: string
    bankHealth?: string
    bankMarriageStatus?: string
    bankChildStatus?: string
    bankInfoChannel?: string
    bankEmergencyName?: string
    bankEmergencyPhone?: string
    bankEmergencyRelation?: string

    // 数组类型字段
    eduExperiences?: EducationExperience[]
    privateInternship?: Internship[]
    govCampusPractice?: CampusPractice[]
    govWorkExperience?: WorkExperience[]
    privateProjectExperience?: ProjectExperience[]
    bankSchoolExperience?: SchoolExperience[]
    languageLevel?: LanguageLevel[]
    honors?: string[]
    certificates?: string[]
    skillLevel?: SkillLevel[]
    privatePaper?: Paper[]
    privateCompetition?: string[]
    familyInfo?: FamilyInfo[]
    bankProfessionalCert?: ProfessionalCert[]
    bankComputerSkills?: string[]
    bankTrainingExperience?: TrainingExperience[]
    bankRewardsPunishments?: RewardPunishment[]

    // 银行特有字段
    bankSalaryExpectation?: string
    bankExpectedLocation?: string

    // 自我评价
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
    idNumber?: string

    // 央国企特有字段
    govCurrentResidence?: string
    govRegisteredResidence?: string
    govNativePlace?: string
    govGaokaoPlace?: string
    govPoliticalStatus?: string
    govEmergencyName?: string
    govEmergencyPhone?: string
    govEmergencyRelation?: string

    // 银行特有字段
    bankPoliticalStatus?: string
    bankHeight?: string
    bankWeight?: string
    bankHealth?: string
    bankMarriageStatus?: string
    bankChildStatus?: string
    bankInfoChannel?: string
    bankEmergencyName?: string
    bankEmergencyPhone?: string
    bankEmergencyRelation?: string

    // 数组类型字段
    eduExperiences?: EducationExperience[]
    privateInternship?: Internship[]
    govCampusPractice?: CampusPractice[]
    govWorkExperience?: WorkExperience[]
    privateProjectExperience?: ProjectExperience[]
    bankSchoolExperience?: SchoolExperience[]
    languageLevel?: LanguageLevel[]
    honors?: string[]
    certificates?: string[]
    skillLevel?: SkillLevel[]
    privatePaper?: Paper[]
    privateCompetition?: string[]
    familyInfo?: FamilyInfo[]
    bankProfessionalCert?: ProfessionalCert[]
    bankComputerSkills?: string[]
    bankTrainingExperience?: TrainingExperience[]
    bankRewardsPunishments?: RewardPunishment[]

    // 银行特有字段
    bankSalaryExpectation?: string
    bankExpectedLocation?: string

    // 自我评价
    selfEvaluation?: string
}

// 内部类定义
export interface EducationExperience {
    schoolName?: string
    collegeName?: string
    majorName?: string
    majorDetail?: string
    degree?: string
    period?: string
    gpa?: string
    rank?: string
    fullTime?: boolean
    lab?: string
    supervisor?: string
}

export interface Internship {
    company?: string
    position?: string
    startTime?: string
    endTime?: string
    description?: string
}

export interface ProjectExperience {
    projectName?: string
    role?: string
    projectTime?: string
    projectLink?: string
    description?: string
}

export interface CampusPractice {
    startTime?: string
    endTime?: string
    description?: string
}

export interface WorkExperience {
    company?: string
    position?: string
    startTime?: string
    endTime?: string
    duty?: string
    workType?: string
}

export interface SchoolExperience {
    name?: string
    startTime?: string
    endTime?: string
    description?: string
}

export interface LanguageLevel {
    language?: string
    level?: string
    exam?: string
    score?: string
}

export interface SkillLevel {
    skillType?: string
    level?: string
}

export interface Paper {
    name?: string
    publishChannel?: string
    authorOrder?: string
    impactFactor?: string
    link?: string
}

export interface FamilyInfo {
    name?: string
    relation?: string
    workplace?: string
    department?: string
    jobTitle?: string
    age?: number
    phone?: string
    foreignIdentity?: boolean
}

export interface ProfessionalCert {
    name?: string
    issueDate?: string
    issuer?: string
    score?: string
    description?: string
}

export interface TrainingExperience {
    trainingName?: string
    startTime?: string
    endTime?: string
    institution?: string
    certificate?: string
    description?: string
}

export interface RewardPunishment {
    type?: string
    level?: string
    name?: string
    date?: string
    unit?: string
    reason?: string
}