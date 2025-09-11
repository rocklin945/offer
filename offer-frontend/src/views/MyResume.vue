<template>
    <div>
        <div class="space-y-6">
            <!-- 页面标题 -->
            <div class="text-center mb-8">
                <h1 class="text-4xl font-bold text-gray-900 mb-2">网申助手</h1>
                <p class="text-gray-600">为什么不直接复制自己的简历？</p>
                <p class="text-[20px] text-gray-600">网申填写时大多琐碎的信息简历上并没有，一键复制功能也可以节省您宝贵的时间</p>
            </div>

            <!-- 简历类型选择和文档导入 -->
            <div class="p-4 bg-blue-50 border border-blue-200 rounded-md mb-8">
                <div class="flex justify-between items-start">
                    <!-- 简历类型选择 -->
                    <div class="flex-1">
                        <h2 class="text-xl font-semibold text-gray-900 mb-3">简历类型</h2>
                        <div class="flex space-x-4">
                            <button @click="resumeType = '民企'"
                                :class="resumeType === '民企' ? 'btn-primary' : 'btn-secondary'"
                                class="flex items-center space-x-2">
                                <span>民企</span>
                            </button>
                            <button @click="resumeType = '央国企'"
                                :class="resumeType === '央国企' ? 'btn-primary' : 'btn-secondary'"
                                class="flex items-center space-x-2">
                                <span>央国企</span>
                            </button>
                            <button @click="resumeType = '银行'"
                                :class="resumeType === '银行' ? 'btn-primary' : 'btn-secondary'"
                                class="flex items-center space-x-2">
                                <span>银行</span>
                            </button>
                        </div>
                    </div>
                    <!-- 检索跳转功能 -->
                    <div class="ml-8 p-3 rounded-md mt-4 relative">
                        <div class="flex items-center">
                            <div class="relative min-w-[320px]">
                                <input type="text" v-model="searchKeyword" @input="querySearch"
                                    @focus="showSuggestions = true" @blur="hideSuggestions"
                                    @keyup.enter="handleSearchEnter" placeholder="搜索简历内容（如：项目经历、考试）"
                                    class="search-input" />
                                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                    <svg class="search-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                                    </svg>
                                </div>
                            </div>
                            <button @click="handleSearchClick" class="search-button">
                                搜索
                            </button>
                        </div>
                        <div v-if="showSuggestions && filteredSuggestions.length > 0" class="search-suggestions">
                            <div v-for="item in filteredSuggestions" :key="item.value"
                                @mousedown.prevent="handleSelect(item)" class="search-suggestion-item">
                                <span>{{ item.value }}</span>
                                <span class="text-gray-400 text-sm">{{ item.type }}</span>
                            </div>
                        </div>
                    </div>
                    <!-- 文档导入区域 -->
                    <div class="ml-8 p-3 rounded-md mt-4">
                        <input ref="fileInput" type="file" accept=".pdf,.doc,.docx" @change="handleFileUpload"
                            class="hidden" />
                        <div class="flex items-center space-x-3">
                            <button @click="handleImportClick"
                                class="px-4 py-2 bg-green-500 text-white rounded-md hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-300 focus:ring-offset-2 flex items-center space-x-2">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M7 16a4 4 0 01-.88-7.903A5 5 0 0115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12">
                                    </path>
                                </svg>
                                <span>导入简历自动解析</span>
                            </button>
                        </div>
                        <div v-if="isProcessing" class="mt-3 flex items-center space-x-2 text-green-600">
                            <svg class="animate-spin h-4 w-4" fill="none" viewBox="0 0 24 24">
                                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor"
                                    stroke-width="4">
                                </circle>
                                <path class="opacity-75" fill="currentColor"
                                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z">
                                </path>
                            </svg>
                            <span class="text-sm font-medium">正在解析文档...</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 简历信息 -->
        <div class="card mt-4">
            <transition name="slide-x" mode="out-in">
                <div :key="resumeType">
                    <div class="flex justify-between items-center mb-6">
                        <h2 class="text-xl font-semibold text-gray-900">简历信息</h2>
                        <div class="flex space-x-2">
                            <button v-if="userStore.currentUser && hasResumeData" @click="clearResume"
                                class="btn-secondary">
                                清空简历
                            </button>
                            <button v-if="userStore.currentUser && !hasResumeData && cloudResume" @click="loadFromCloud"
                                class="btn-primary">
                                加载简历数据
                            </button>
                            <button v-if="userStore.currentUser && hasResumeData && cloudResume"
                                @click="clearCloudResume"
                                class="px-4 py-2 bg-red-400 text-white rounded-md hover:bg-red-400 focus:outline-none focus:ring-2 focus:ring-red-300 focus:ring-offset-2 flex items-center space-x-2">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1-1H8a1 1 0 00-1 1v3M4 7h16">
                                    </path>
                                </svg>
                                <span>删除简历数据</span>
                            </button>
                            <button @click="handleSaveClick" :disabled="isSaving" class="btn-primary">
                                {{ isSaving ? '保存中...' : '保存简历' }}
                            </button>
                        </div>
                    </div>

                    <form @submit.prevent="saveResume" class="space-y-6">
                        <!-- 基本信息 -->
                        <BasicInfo id="basic-info" :resume-form="resumeForm" :resume-type="resumeType"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 教育经历 (所有类型都显示) -->
                        <EducationExperience id="education" :resume-form="resumeForm"
                            @add-edu-experience="addEduExperience" @remove-edu-experience="removeEduExperience"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 根据简历类型显示不同的经历字段 -->
                        <!-- 民企: 实习经历和项目经历 -->
                        <InternshipExperience id="internship-experience" v-if="resumeType === '民企'"
                            :resume-form="resumeForm" @add-internship="addInternship"
                            @remove-internship="removeInternship" @copy-to-clipboard="copyToClipboard" />

                        <ProjectExperience id="project-experience" v-if="resumeType === '民企'" :resume-form="resumeForm"
                            @add-project-experience="addProjectExperience"
                            @remove-project-experience="removeProjectExperience" @copy-to-clipboard="copyToClipboard" />

                        <!-- 央国企: 校园实践和工作经历 -->
                        <CampusPractice id="campus-practice" v-if="resumeType === '央国企'" :resume-form="resumeForm"
                            @add-campus-practice="addCampusPractice" @remove-campus-practice="removeCampusPractice"
                            @copy-to-clipboard="copyToClipboard" />

                        <WorkExperience id="work-experience" v-if="resumeType === '央国企'" :resume-form="resumeForm"
                            @add-work-experience="addWorkExperience" @remove-work-experience="removeWorkExperience"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 央国企: 家庭信息 -->
                        <FamilyInfo id="family-info" v-if="resumeType === '央国企'" :resume-form="resumeForm"
                            @add-family-info="addFamilyInfo" @remove-family-info="removeFamilyInfo"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 银行: 在校经历 -->
                        <SchoolExperience id="school-experience" v-if="resumeType === '银行'" :resume-form="resumeForm"
                            @add-school-experience="addSchoolExperience"
                            @remove-school-experience="removeSchoolExperience" @copy-to-clipboard="copyToClipboard" />

                        <!-- 银行: 专业证书 -->
                        <BankProfessionalCert id="bank-professional-cert" v-if="resumeType === '银行'"
                            :resume-form="resumeForm" @add-bank-professional-cert="addBankProfessionalCert"
                            @remove-bank-professional-cert="removeBankProfessionalCert"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 银行: 计算机技能 -->
                        <BankComputerSkills id="bank-computer-skills" v-if="resumeType === '银行'"
                            :resume-form="resumeForm" @add-bank-computer-skill="addBankComputerSkill"
                            @remove-bank-computer-skill="removeBankComputerSkill"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 银行: 培训经历 -->
                        <BankTrainingExperience id="bank-training-experience" v-if="resumeType === '银行'"
                            :resume-form="resumeForm" @add-bank-training-experience="addBankTrainingExperience"
                            @remove-bank-training-experience="removeBankTrainingExperience"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 银行: 奖惩情况 -->
                        <BankRewardsPunishments id="bank-rewards-punishments" v-if="resumeType === '银行'"
                            :resume-form="resumeForm" @add-bank-reward-punishment="addBankRewardPunishment"
                            @remove-bank-reward-punishment="removeBankRewardPunishment"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 银行: 期望信息 -->
                        <BankExpectations id="bank-expectations" v-if="resumeType === '银行'" :resume-form="resumeForm"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 银行: 家庭信息 -->
                        <FamilyInfo id="family-info-bank" v-if="resumeType === '银行'" :resume-form="resumeForm"
                            @add-family-info="addFamilyInfo" @remove-family-info="removeFamilyInfo"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 私企: 论文/文章 -->
                        <PrivatePaper id="private-paper" v-if="resumeType === '民企'" :resume-form="resumeForm"
                            @add-private-paper="addPrivatePaper" @remove-private-paper="removePrivatePaper"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 私企: 竞赛经历 -->
                        <PrivateCompetition id="private-competition" v-if="resumeType === '民企'"
                            :resume-form="resumeForm" @add-private-competition="addPrivateCompetition"
                            @remove-private-competition="removePrivateCompetition"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 语言水平 (所有类型都显示) -->
                        <LanguageLevel id="language-level" :resume-form="resumeForm"
                            @add-language-level="addLanguageLevel" @remove-language-level="removeLanguageLevel"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 专业技能 (所有类型都显示) -->
                        <SkillLevel id="skills" :resume-form="resumeForm" @add-skill-level="addSkillLevel"
                            @remove-skill-level="removeSkillLevel" @copy-to-clipboard="copyToClipboard" />

                        <!-- 荣誉奖项 (所有类型都显示) -->
                        <Honors id="honors" :resume-form="resumeForm" @add-honor="addHonor" @remove-honor="removeHonor"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 证书 (所有类型都显示) -->
                        <Certificates id="certificates" :resume-form="resumeForm" @add-certificate="addCertificate"
                            @remove-certificate="removeCertificate" @copy-to-clipboard="copyToClipboard" />

                        <!-- 自我评价 (所有类型都显示) -->
                        <SelfEvaluation id="self-evaluation" :resume-form="resumeForm"
                            @copy-to-clipboard="copyToClipboard" />
                    </form>
                </div>
            </transition>
        </div>

        <!-- 登录模态框 -->
        <LoginModal v-if="showLoginModal" @close="showLoginModal = false" />
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted, watch, onActivated, onDeactivated, onUnmounted } from 'vue'
import { useUserStore } from '@/stores/user'
import type { Resume, ResumeAddRequest, ResumeUpdateRequest } from '@/api/resumeTypes'
import type { DeleteRequest } from '@/api/types'
import Message from '@/components/Message'
import Confirm from '@/components/Confirm'
// 文档解析将改为后端处理
// 导入简历API
import { getMyResume, addResume, updateResume, deleteResume, parsePdfResume } from '@/api/resume'
// 导入登录模态框组件
import LoginModal from '@/components/LoginModal.vue'

// 导入拆分后的组件
import BasicInfo from '@/components/resume/BasicInfo.vue'
import EducationExperience from '@/components/resume/EducationExperience.vue'
import LanguageLevel from '@/components/resume/LanguageLevel.vue'
import SkillLevel from '@/components/resume/SkillLevel.vue'
import Honors from '@/components/resume/Honors.vue'
import Certificates from '@/components/resume/Certificates.vue'
import SelfEvaluation from '@/components/resume/SelfEvaluation.vue'
import InternshipExperience from '@/components/resume/InternshipExperience.vue'
import ProjectExperience from '@/components/resume/ProjectExperience.vue'
import CampusPractice from '@/components/resume/CampusPractice.vue'
import WorkExperience from '@/components/resume/WorkExperience.vue'
import SchoolExperience from '@/components/resume/SchoolExperience.vue'

// 添加银行相关的组件导入
import BankComputerSkills from '@/components/resume/BankComputerSkills.vue'
import BankExpectations from '@/components/resume/BankExpectations.vue'
import BankProfessionalCert from '@/components/resume/BankProfessionalCert.vue'
import BankRewardsPunishments from '@/components/resume/BankRewardsPunishments.vue'
import BankTrainingExperience from '@/components/resume/BankTrainingExperience.vue'
import FamilyInfo from '@/components/resume/FamilyInfo.vue'
import PrivateCompetition from '@/components/resume/PrivateCompetition.vue'
import PrivatePaper from '@/components/resume/PrivatePaper.vue'

const userStore = useUserStore()
const showLoginModal = ref(false)
const isSaving = ref(false)
const cloudResume = ref<Resume | null>(null)
// 文档解析相关状态
const isProcessing = ref(false)
const fileInput = ref<HTMLInputElement | null>(null)

// 简历类型
const resumeType = ref<'民企' | '央国企' | '银行'>('民企')

// 搜索相关状态
const searchKeyword = ref('')
const showSuggestions = ref(false)
const filteredSuggestions = ref<Array<{ value: string, type: string }>>([])

// 搜索建议数据
const searchSuggestions = [
    { value: '个人信息', type: '基本信息' },
    { value: '教育经历', type: '教育背景' },
    { value: '语言水平', type: '技能' },
    { value: '专业技能', type: '技能' },
    { value: '荣誉奖项', type: '成就' },
    { value: '证书', type: '资质' },
    { value: '自我评价', type: '个人描述' },
    { value: '实习经历', type: '工作经历' },
    { value: '项目经历', type: '工作经历' },
    { value: '校园实践', type: '活动经历' },
    { value: '工作经历', type: '工作经历' },
    { value: '在校经历', type: '教育背景' },
    { value: '专业证书', type: '资质' },
    { value: '计算机技能', type: '技能' },
    { value: '培训经历', type: '教育背景' },
    { value: '奖惩情况', type: '成就' },
    { value: '期望信息', type: '求职意向' },
    { value: '家庭信息', type: '个人信息' },
    { value: '论文', type: '学术成果' },
    { value: '竞赛经历', type: '活动经历' }
]

// 简历表单数据
const resumeForm = reactive<ResumeAddRequest>({
    name: '',
    phone: '',
    wechat: '',
    email: '',
    gender: '',
    birthday: '',
    address: '',
    idNumber: '',

    // 央国企特有字段
    govCurrentResidence: '',
    govRegisteredResidence: '',
    govNativePlace: '',
    govGaokaoPlace: '',
    govPoliticalStatus: '',
    govEmergencyName: '',
    govEmergencyPhone: '',
    govEmergencyRelation: '',

    // 银行特有字段
    bankPoliticalStatus: '',
    bankHeight: '',
    bankWeight: '',
    bankHealth: '',
    bankMarriageStatus: '',
    bankChildStatus: '',
    bankInfoChannel: '',
    bankEmergencyName: '',
    bankEmergencyPhone: '',
    bankEmergencyRelation: '',

    // 数组类型字段
    eduExperiences: [],
    privateInternship: [],
    govCampusPractice: [],
    govWorkExperience: [],
    privateProjectExperience: [],
    bankSchoolExperience: [],
    languageLevel: [],
    honors: [],
    certificates: [],
    skillLevel: [],
    privatePaper: [],
    privateCompetition: [],
    familyInfo: [],
    bankProfessionalCert: [],
    bankComputerSkills: [],
    bankTrainingExperience: [],
    bankRewardsPunishments: [],

    // 银行特有字段
    bankSalaryExpectation: '',
    bankExpectedLocation: '',

    // 自我评价
    selfEvaluation: ''
})

// 是否有简历数据
const hasResumeData = computed(() => {
    // 检查基本字段
    const basicFields: Array<keyof ResumeAddRequest> = [
        'name', 'phone', 'wechat', 'email', 'gender', 'birthday', 'address', 'idNumber',
        'selfEvaluation'
    ];

    // 检查是否有基本字段不为空
    const hasBasicData = basicFields.some(key => {
        const value = resumeForm[key];
        return typeof value === 'string' && value.trim() !== '';
    });

    // 检查是否有数组字段不为空
    const arrayFields: Array<keyof ResumeAddRequest> = [
        'eduExperiences', 'privateInternship', 'govCampusPractice', 'govWorkExperience',
        'privateProjectExperience', 'bankSchoolExperience', 'languageLevel', 'honors',
        'certificates', 'skillLevel', 'privatePaper', 'privateCompetition', 'familyInfo',
        'bankProfessionalCert', 'bankComputerSkills', 'bankTrainingExperience', 'bankRewardsPunishments'
    ];

    const hasArrayData = arrayFields.some(key => {
        const value = resumeForm[key];
        return Array.isArray(value) && value.length > 0;
    });

    return hasBasicData || hasArrayData;
})

// 文档解析相关函数
// 处理文件上传
const handleFileUpload = async (event: Event) => {
    const target = event.target as HTMLInputElement
    const file = target.files?.[0]

    if (!file) return

    // 文件大小限制 10MB
    if (file.size > 10 * 1024 * 1024) {
        Message.error('文件大小不能超过 10MB')
        return
    }

    // 文件类型检查
    const allowedTypes = ['application/pdf']
    if (!allowedTypes.includes(file.type)) {
        Message.error('仅支持 PDF 格式的文件')
        return
    }

    isProcessing.value = true

    try {
        // 调用后端接口解析PDF
        const response = await parsePdfResume(file)

        if (response.statusCode === 200 && response.data) {
            // 将解析结果映射到表单中
            mapParsedDataToForm(response.data)
            Message.success('文档解析成功，已自动填入简历信息')
        } else {
            Message.error(response.message || '文档解析失败')
        }
    } catch (error) {
        console.error('文档解析失败:', error)
        Message.error('文档解析失败，请检查文件格式是否正确')
    } finally {
        isProcessing.value = false
        // 清空文件输入
        if (target) target.value = ''
    }
}

// 触发文件输入点击
const triggerFileInputClick = () => {
    fileInput.value?.click();
};

// 将解析的数据映射到表单中
const mapParsedDataToForm = (parsedData: Record<string, string>) => {
    // 映射字段
    const fieldMapping: Record<string, keyof ResumeAddRequest> = {
        'name': 'name',
        'phone': 'phone',
        'wechat': 'wechat',
        'email': 'email',
        'gender': 'gender',
        'birthday': 'birthday',
        'address': 'address',
        'idNumber': 'idNumber',
        'selfEvaluation': 'selfEvaluation'
    }

    // 遍历解析的数据并填入表单
    Object.keys(parsedData).forEach(key => {
        const formKey = fieldMapping[key]
        if (formKey && (resumeForm as any)[formKey] !== undefined) {
            // 只填入空字段，保留用户已填写的内容
            if (!(resumeForm as any)[formKey] || (resumeForm as any)[formKey].trim() === '') {
                (resumeForm as any)[formKey] = parsedData[key]
            }
        }
    })
}

// 从云端加载简历
const loadFromCloud = async () => {
    if (!userStore.currentUser) return

    try {
        const response = await getMyResume()
        if (response.statusCode === 200 && response.data) {
            cloudResume.value = response.data
            // 将云端数据填充到表单
            Object.assign(resumeForm, {
                name: response.data.name || '',
                phone: response.data.phone || '',
                wechat: response.data.wechat || '',
                email: response.data.email || '',
                gender: response.data.gender || '',
                birthday: response.data.birthday || '',
                address: response.data.address || '',
                idNumber: response.data.idNumber || '',

                // 央国企特有字段
                govCurrentResidence: response.data.govCurrentResidence || '',
                govRegisteredResidence: response.data.govRegisteredResidence || '',
                govNativePlace: response.data.govNativePlace || '',
                govGaokaoPlace: response.data.govGaokaoPlace || '',
                govPoliticalStatus: response.data.govPoliticalStatus || '',
                govEmergencyName: response.data.govEmergencyName || '',
                govEmergencyPhone: response.data.govEmergencyPhone || '',
                govEmergencyRelation: response.data.govEmergencyRelation || '',

                // 银行特有字段
                bankPoliticalStatus: response.data.bankPoliticalStatus || '',
                bankHeight: response.data.bankHeight || '',
                bankWeight: response.data.bankWeight || '',
                bankHealth: response.data.bankHealth || '',
                bankMarriageStatus: response.data.bankMarriageStatus || '',
                bankChildStatus: response.data.bankChildStatus || '',
                bankInfoChannel: response.data.bankInfoChannel || '',
                bankEmergencyName: response.data.bankEmergencyName || '',
                bankEmergencyPhone: response.data.bankEmergencyPhone || '',
                bankEmergencyRelation: response.data.bankEmergencyRelation || '',

                // 数组类型字段
                eduExperiences: response.data.eduExperiences || [],
                privateInternship: response.data.privateInternship || [],
                govCampusPractice: response.data.govCampusPractice || [],
                govWorkExperience: response.data.govWorkExperience || [],
                privateProjectExperience: response.data.privateProjectExperience || [],
                bankSchoolExperience: response.data.bankSchoolExperience || [],
                languageLevel: response.data.languageLevel || [],
                honors: response.data.honors || [],
                certificates: response.data.certificates || [],
                skillLevel: response.data.skillLevel || [],
                privatePaper: response.data.privatePaper || [],
                privateCompetition: response.data.privateCompetition || [],
                familyInfo: response.data.familyInfo || [],
                bankProfessionalCert: response.data.bankProfessionalCert || [],
                bankComputerSkills: response.data.bankComputerSkills || [],
                bankTrainingExperience: response.data.bankTrainingExperience || [],
                bankRewardsPunishments: response.data.bankRewardsPunishments || [],

                // 银行特有字段
                bankSalaryExpectation: response.data.bankSalaryExpectation || '',
                bankExpectedLocation: response.data.bankExpectedLocation || '',

                // 自我评价
                selfEvaluation: response.data.selfEvaluation || ''
            })
        } else {
            cloudResume.value = null
        }
    } catch (error) {
        console.error('加载简历失败:', error)
    }
}

// 保存到云端
const saveToCloud = async () => {
    if (!userStore.currentUser) return

    try {
        if (cloudResume.value) {
            // 更新现有简历
            const updateData: ResumeUpdateRequest = {
                id: cloudResume.value.id,
                ...resumeForm
            }
            const response = await updateResume(updateData)
            if (response.statusCode === 200) {
                Message.success('简历更新成功')
            } else {
                Message.error(response.message || '简历更新失败')
            }
        } else {
            // 创建新简历
            const response = await addResume(resumeForm)
            if (response.statusCode === 200) {
                Message.success('简历创建成功')
                // 重新加载云端数据
                await loadFromCloud()
            } else {
                Message.error(response.message || '简历创建失败')
            }
        }
    } catch (error: any) {
        console.error('保存简历失败:', error)
        Message.error(error.message || '保存失败')
    }
}

// 保存简历
const saveResume = async () => {
    if (!resumeForm.name.trim()) {
        Message.error('请填写姓名')
        return
    }

    isSaving.value = true

    try {
        await saveToCloud()
    } catch (error) {
        console.error('保存简历失败:', error)
        Message.error('保存失败，请重试')
    } finally {
        isSaving.value = false
    }
}

// 清空简历
const clearResume = async () => {
    const confirmed = await Confirm.show({
        title: '清空简历',
        message: '确定要清空表单中的简历数据吗？这不会删除已保存的数据。',
        type: 'warning',
        confirmText: '确定清空',
        cancelText: '取消'
    })

    if (confirmed) {
        // 重置表单数据
        Object.assign(resumeForm, {
            name: '',
            phone: '',
            wechat: '',
            email: '',
            gender: '',
            birthday: '',
            address: '',
            idNumber: '',

            // 央国企特有字段
            govCurrentResidence: '',
            govRegisteredResidence: '',
            govNativePlace: '',
            govGaokaoPlace: '',
            govPoliticalStatus: '',
            govEmergencyName: '',
            govEmergencyPhone: '',
            govEmergencyRelation: '',

            // 银行特有字段
            bankPoliticalStatus: '',
            bankHeight: '',
            bankWeight: '',
            bankHealth: '',
            bankMarriageStatus: '',
            bankChildStatus: '',
            bankInfoChannel: '',
            bankEmergencyName: '',
            bankEmergencyPhone: '',
            bankEmergencyRelation: '',

            // 数组类型字段
            eduExperiences: [],
            privateInternship: [],
            govCampusPractice: [],
            govWorkExperience: [],
            privateProjectExperience: [],
            bankSchoolExperience: [],
            languageLevel: [],
            honors: [],
            certificates: [],
            skillLevel: [],
            privatePaper: [],
            privateCompetition: [],
            familyInfo: [],
            bankProfessionalCert: [],
            bankComputerSkills: [],
            bankTrainingExperience: [],
            bankRewardsPunishments: [],

            // 银行特有字段
            bankSalaryExpectation: '',
            bankExpectedLocation: '',

            // 自我评价
            selfEvaluation: ''
        })
        Message.success('表单数据已清空')
    }
}

// 清除云端数据
const clearCloudResume = async () => {
    if (!cloudResume.value) {
        Message.warning('没有简历数据可清除')
        return
    }

    const confirmed = await Confirm.show({
        title: '删除数据',
        message: '确定要永久删除简历数据吗？此操作不可恢复。',
        type: 'danger',
        confirmText: '确定删除',
        cancelText: '取消'
    })

    if (confirmed) {
        try {
            const response = await deleteResume({ id: cloudResume.value.id })
            if (response.statusCode === 200) {
                // 清空表单数据
                Object.assign(resumeForm, {
                    name: '',
                    phone: '',
                    wechat: '',
                    email: '',
                    gender: '',
                    birthday: '',
                    address: '',
                    idNumber: '',

                    // 央国企特有字段
                    govCurrentResidence: '',
                    govRegisteredResidence: '',
                    govNativePlace: '',
                    govGaokaoPlace: '',
                    govPoliticalStatus: '',
                    govEmergencyName: '',
                    govEmergencyPhone: '',
                    govEmergencyRelation: '',

                    // 银行特有字段
                    bankPoliticalStatus: '',
                    bankHeight: '',
                    bankWeight: '',
                    bankHealth: '',
                    bankMarriageStatus: '',
                    bankChildStatus: '',
                    bankInfoChannel: '',
                    bankEmergencyName: '',
                    bankEmergencyPhone: '',
                    bankEmergencyRelation: '',

                    // 数组类型字段
                    eduExperiences: [],
                    privateInternship: [],
                    govCampusPractice: [],
                    govWorkExperience: [],
                    privateProjectExperience: [],
                    bankSchoolExperience: [],
                    languageLevel: [],
                    honors: [],
                    certificates: [],
                    skillLevel: [],
                    privatePaper: [],
                    privateCompetition: [],
                    familyInfo: [],
                    bankProfessionalCert: [],
                    bankComputerSkills: [],
                    bankTrainingExperience: [],
                    bankRewardsPunishments: [],

                    // 银行特有字段
                    bankSalaryExpectation: '',
                    bankExpectedLocation: '',

                    // 自我评价
                    selfEvaluation: ''
                })
                // 清空云端简历引用
                cloudResume.value = null
                Message.success('简历数据已删除')
            } else {
                Message.error(response.message || '删除失败')
            }
        } catch (error: any) {
            console.error('删除简历失败:', error)
            Message.error(error.message || '删除失败，请重试')
        }
    }
}

// 复制功能
const copyToClipboard = async (text: string | undefined | null, fieldName: string) => {
    // 如果内容为空，提示用户
    if (!text || text.trim() === '') {
        Message.warning(`${fieldName}内容为空，无法复制`)
        return
    }

    try {
        await navigator.clipboard.writeText(text)
        Message.success(`${fieldName}已复制到剪贴板`)
    } catch (err) {
        // 降级方案：使用传统的复制方法
        try {
            const textArea = document.createElement('textarea')
            textArea.value = text
            textArea.style.position = 'fixed'
            textArea.style.left = '-999999px'
            textArea.style.top = '-999999px'
            document.body.appendChild(textArea)
            textArea.focus()
            textArea.select()
            document.execCommand('copy')
            document.body.removeChild(textArea)
            Message.success(`${fieldName}已复制到剪贴板`)
        } catch (err2) {
            console.error('复制失败:', err2)
            Message.error('复制失败，请手动选择复制')
        }
    }
}

// 添加教育经历
const addEduExperience = () => {
    resumeForm.eduExperiences?.push({
        schoolName: '',
        collegeName: '',
        majorName: '',
        majorDetail: '',
        degree: '',
        period: '',
        gpa: '',
        rank: '',
        fullTime: false,
        lab: '',
        supervisor: ''
    })
}

// 删除教育经历
const removeEduExperience = (index: number) => {
    resumeForm.eduExperiences?.splice(index, 1)
}

// 添加实习经历
const addInternship = () => {
    resumeForm.privateInternship?.push({
        company: '',
        position: '',
        startTime: '',
        endTime: '',
        description: ''
    })
}

// 删除实习经历
const removeInternship = (index: number) => {
    resumeForm.privateInternship?.splice(index, 1)
}

// 添加项目经历
const addProjectExperience = () => {
    resumeForm.privateProjectExperience?.push({
        projectName: '',
        role: '',
        projectTime: '',
        projectLink: '',
        description: ''
    })
}

// 删除项目经历
const removeProjectExperience = (index: number) => {
    resumeForm.privateProjectExperience?.splice(index, 1)
}

// 添加校园实践
const addCampusPractice = () => {
    resumeForm.govCampusPractice?.push({
        startTime: '',
        endTime: '',
        description: ''
    })
}

// 删除校园实践
const removeCampusPractice = (index: number) => {
    resumeForm.govCampusPractice?.splice(index, 1)
}

// 添加工作经历
const addWorkExperience = () => {
    resumeForm.govWorkExperience?.push({
        company: '',
        position: '',
        startTime: '',
        endTime: '',
        duty: '',
        workType: ''
    })
}

// 删除工作经历
const removeWorkExperience = (index: number) => {
    resumeForm.govWorkExperience?.splice(index, 1)
}

// 添加在校经历
const addSchoolExperience = () => {
    resumeForm.bankSchoolExperience?.push({
        name: '',
        startTime: '',
        endTime: '',
        description: ''
    })
}

// 删除在校经历
const removeSchoolExperience = (index: number) => {
    resumeForm.bankSchoolExperience?.splice(index, 1)
}

// 添加语言水平
const addLanguageLevel = () => {
    resumeForm.languageLevel?.push({
        language: '',
        level: '',
        exam: '',
        score: ''
    })
}

// 删除语言水平
const removeLanguageLevel = (index: number) => {
    resumeForm.languageLevel?.splice(index, 1)
}

// 添加专业技能
const addSkillLevel = () => {
    resumeForm.skillLevel?.push({
        skillType: '',
        level: ''
    })
}

// 删除专业技能
const removeSkillLevel = (index: number) => {
    resumeForm.skillLevel?.splice(index, 1)
}

// 添加荣誉奖项
const addHonor = () => {
    resumeForm.honors?.push('')
}

// 删除荣誉奖项
const removeHonor = (index: number) => {
    resumeForm.honors?.splice(index, 1)
}

// 添加证书
const addCertificate = () => {
    resumeForm.certificates?.push('')
}

// 删除证书
const removeCertificate = (index: number) => {
    resumeForm.certificates?.splice(index, 1)
}

// 添加银行专业证书
const addBankProfessionalCert = () => {
    resumeForm.bankProfessionalCert?.push({
        name: '',
        issueDate: '',
        issuer: '',
        score: '',
        description: ''
    })
}

// 删除银行专业证书
const removeBankProfessionalCert = (index: number) => {
    resumeForm.bankProfessionalCert?.splice(index, 1)
}

// 添加银行计算机技能
const addBankComputerSkill = () => {
    resumeForm.bankComputerSkills?.push('')
}

// 删除银行计算机技能
const removeBankComputerSkill = (index: number) => {
    resumeForm.bankComputerSkills?.splice(index, 1)
}

// 添加银行培训经历
const addBankTrainingExperience = () => {
    resumeForm.bankTrainingExperience?.push({
        trainingName: '',
        startTime: '',
        endTime: '',
        institution: '',
        certificate: '',
        description: ''
    })
}

// 删除银行培训经历
const removeBankTrainingExperience = (index: number) => {
    resumeForm.bankTrainingExperience?.splice(index, 1)
}

// 添加银行奖惩情况
const addBankRewardPunishment = () => {
    resumeForm.bankRewardsPunishments?.push({
        type: '',
        level: '',
        name: '',
        date: '',
        unit: '',
        reason: ''
    })
}

// 删除银行奖惩情况
const removeBankRewardPunishment = (index: number) => {
    resumeForm.bankRewardsPunishments?.splice(index, 1)
}

// 添加家庭信息
const addFamilyInfo = () => {
    resumeForm.familyInfo?.push({
        name: '',
        relation: '',
        workplace: '',
        department: '',
        jobTitle: '',
        age: 0,
        phone: '',
        foreignIdentity: false
    })
}

// 删除家庭信息
const removeFamilyInfo = (index: number) => {
    resumeForm.familyInfo?.splice(index, 1)
}

// 添加私企论文/文章
const addPrivatePaper = () => {
    resumeForm.privatePaper?.push({
        name: '',
        publishChannel: '',
        authorOrder: '',
        impactFactor: '',
        link: ''
    })
}

// 删除私企论文/文章
const removePrivatePaper = (index: number) => {
    resumeForm.privatePaper?.splice(index, 1)
}

// 添加私企竞赛经历
const addPrivateCompetition = () => {
    resumeForm.privateCompetition?.push('')
}

// 删除私企竞赛经历
const removePrivateCompetition = (index: number) => {
    resumeForm.privateCompetition?.splice(index, 1)
}

// 处理保存按钮点击
const handleSaveClick = async () => {
    // 检查用户是否登录
    if (!userStore.currentUser) {
        Message.info('请先登录以保存简历')
        showLoginModal.value = true
        return
    }

    // 调用原有的保存函数
    await saveResume()
}

// 处理导入按钮点击
const handleImportClick = () => {
    // 检查用户是否登录
    if (!userStore.currentUser) {
        Message.info('请先登录以导入简历')
        showLoginModal.value = true
        return
    }

    // 调用原有的导入函数
    triggerFileInputClick()
}

// 监听用户登录状态变化
watch(() => userStore.currentUser, async (user) => {
    if (user) {
        await loadFromCloud()
    }
})

// 页面加载时初始化
onMounted(() => {
    if (userStore.currentUser) {
        loadFromCloud()
    }
})

// 组件激活时重新加载数据
onActivated(() => {
    // 重新初始化用户信息
    if (userStore.token && !userStore.currentUser) {
        userStore.initUserInfo()
    }

    // 重新加载简历数据
    if (userStore.currentUser) {
        loadFromCloud()
    }
})

// 组件失活时清理临时状态（保留表单数据）
onDeactivated(() => {
    cleanupTemporaryState()
})

// 组件卸载时清理所有数据
onUnmounted(() => {
    cleanupComponentState()
})

// 搜索功能实现
const querySearch = () => {
    if (!searchKeyword.value.trim()) {
        filteredSuggestions.value = []
        return
    }

    const keyword = searchKeyword.value.toLowerCase()
    filteredSuggestions.value = searchSuggestions.filter(item =>
        item.value.toLowerCase().includes(keyword) || item.type.toLowerCase().includes(keyword)
    )
}

// 处理搜索建议选择
const handleSelect = (item: { value: string, type: string }) => {
    searchKeyword.value = item.value
    showSuggestions.value = false

    // 滚动到对应的组件
    scrollToComponent(item.value)
}

// 隐藏搜索建议
const hideSuggestions = () => {
    // 使用setTimeout确保点击事件先处理
    setTimeout(() => {
        showSuggestions.value = false
    }, 200)
}

// 回车键搜索
const handleSearchEnter = () => {
    if (searchKeyword.value.trim() === '') {
        return
    }

    if (filteredSuggestions.value.length > 0) {
        handleSelect(filteredSuggestions.value[0])
    } else {
        // 全文搜索：搜索页面上的所有文本内容
        performFullTextSearch(searchKeyword.value)
    }
}

// 点击搜索按钮
const handleSearchClick = () => {
    if (searchKeyword.value.trim() === '') {
        return
    }

    // 总是执行全文搜索，而不仅仅是在没有建议时才搜索
    performFullTextSearch(searchKeyword.value)
}

// 全文搜索功能
const performFullTextSearch = (keyword: string) => {
    // 移除之前的高亮
    removeHighlights()

    if (!keyword.trim()) {
        Message.error('请输入搜索关键词')
        return
    }

    // 获取简历内容区域的文本（排除搜索框本身）
    const resumeContent = document.querySelector('.card.mt-4')
    if (!resumeContent) {
        Message.error('未找到简历内容')
        return
    }

    const contentText = resumeContent.textContent?.toLowerCase() || ''
    const searchTerm = keyword.toLowerCase()

    // 检查是否包含搜索关键词
    if (contentText.includes(searchTerm)) {
        // 查找所有匹配的元素
        const matchedElements: HTMLElement[] = []
        const allElements = resumeContent.querySelectorAll('*')

        allElements.forEach(element => {
            if (element.childNodes.length === 1 && element.childNodes[0].nodeType === Node.TEXT_NODE) {
                const text = element.textContent?.toLowerCase() || ''
                if (text.includes(searchTerm)) {
                    matchedElements.push(element as HTMLElement)
                }
            }
        })

        if (matchedElements.length > 0) {
            // 高亮所有匹配的文本
            highlightAllMatches(keyword)

            // 滚动到第一个匹配项
            scrollToFirstMatch()

            Message.success(`找到 ${matchedElements.length} 个匹配项`)
        } else {
            Message.error(`未找到包含"${keyword}"的内容`)
        }
    } else {
        // 如果没有找到匹配项，显示提示信息
        Message.error(`未找到包含"${keyword}"的内容`)
    }
}

// 高亮所有匹配的文本
const highlightAllMatches = (keyword: string) => {
    // 移除之前的高亮
    removeHighlights()

    // 创建高亮样式（如果还不存在）
    let style = document.getElementById('search-highlight-style')
    if (!style) {
        style = document.createElement('style')
        style.id = 'search-highlight-style'
        style.textContent = `
            .search-highlight {
                background-color: #ffeb3b;
                color: #000;
                padding: 2px 0;
                border-radius: 2px;
                font-weight: bold;
            }
            .search-highlight-animation {
                animation: highlightAnimation 1s ease-in-out;
            }
            @keyframes highlightAnimation {
                0% { background-color: #ffeb3b; }
                50% { background-color: #ffc107; }
                100% { background-color: #ffeb3b; }
            }
        `
        document.head.appendChild(style)
    }

    // 获取简历内容区域
    const resumeContent = document.querySelector('.card.mt-4')
    if (!resumeContent) return

    // 查找并高亮所有匹配的文本节点
    const walker = document.createTreeWalker(
        resumeContent,
        NodeFilter.SHOW_TEXT,
        {
            acceptNode: (node) => {
                // 排除脚本标签和样式标签中的文本
                if (node.parentElement?.tagName === 'SCRIPT' ||
                    node.parentElement?.tagName === 'STYLE' ||
                    node.parentElement?.classList.contains('search-highlight')) {
                    return NodeFilter.FILTER_REJECT
                }
                return node.textContent?.toLowerCase().includes(keyword.toLowerCase()) ?
                    NodeFilter.FILTER_ACCEPT : NodeFilter.FILTER_REJECT
            }
        }
    )

    const textNodes: Text[] = []
    let node
    while (node = walker.nextNode()) {
        if (node.nodeType === Node.TEXT_NODE) {
            textNodes.push(node as Text)
        }
    }

    // 反向处理节点以避免修改DOM时影响后续操作
    for (let i = textNodes.length - 1; i >= 0; i--) {
        const textNode = textNodes[i]
        const text = textNode.textContent || ''
        const lowerText = text.toLowerCase()
        const lowerKeyword = keyword.toLowerCase()

        if (lowerText.includes(lowerKeyword)) {
            const parts = text.split(new RegExp(`(${keyword.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')})`, 'gi'))
            const fragment = document.createDocumentFragment()

            parts.forEach((part, index) => {
                if (index % 2 === 0) {
                    // 普通文本
                    if (part) fragment.appendChild(document.createTextNode(part))
                } else {
                    // 匹配的关键词
                    const span = document.createElement('span')
                    span.className = 'search-highlight search-highlight-animation'
                    span.textContent = part
                    fragment.appendChild(span)
                }
            })

            textNode.parentNode?.replaceChild(fragment, textNode)
        }
    }
}

// 移除所有高亮
const removeHighlights = () => {
    const highlights = document.querySelectorAll('.search-highlight')
    highlights.forEach(highlight => {
        const parent = highlight.parentNode
        if (parent) {
            // 将高亮元素替换为其文本内容
            parent.replaceChild(document.createTextNode(highlight.textContent || ''), highlight)
        }
    })

    // 合并相邻的文本节点
    const resumeContent = document.querySelector('.card.mt-4')
    if (resumeContent) {
        resumeContent.normalize()
    }
}

// 滚动到第一个匹配项
const scrollToFirstMatch = () => {
    const firstHighlight = document.querySelector('.search-highlight')
    if (firstHighlight) {
        firstHighlight.scrollIntoView({ behavior: 'smooth', block: 'center' })

        // 添加闪烁动画效果
        firstHighlight.classList.add('search-highlight-animation')

        // 3秒后移除动画类
        setTimeout(() => {
            firstHighlight.classList.remove('search-highlight-animation')
        }, 3000)
    }
}

// 滚动到对应的组件
const scrollToComponent = (componentName: string) => {
    const componentMap: Record<string, string> = {
        '个人信息': 'basic-info',
        '教育经历': 'education',
        '语言水平': 'language-level',
        '专业技能': 'skills',
        '荣誉奖项': 'honors',
        '证书': 'certificates',
        '自我评价': 'self-evaluation',
        '实习经历': 'internship-experience',
        '项目经历': 'project-experience',
        '校园实践': 'campus-practice',
        '工作经历': 'work-experience',
        '在校经历': 'school-experience',
        '专业证书': 'bank-professional-cert',
        '计算机技能': 'bank-computer-skills',
        '培训经历': 'bank-training-experience',
        '奖惩情况': 'bank-rewards-punishments',
        '期望信息': 'bank-expectations',
        '家庭信息': 'family-info',
        '论文': 'private-paper',
        '竞赛经历': 'private-competition'
    }

    const elementId = componentMap[componentName]
    if (elementId) {
        const element = document.getElementById(elementId)
        if (element) {
            element.scrollIntoView({ behavior: 'smooth', block: 'start' })

            // 添加高亮效果
            element.classList.add('highlight-search-result')
            setTimeout(() => {
                element.classList.remove('highlight-search-result')
            }, 2000)
        }
    }
}

// 清理组件状态
const cleanupComponentState = () => {
    // 重置表单数据
    Object.assign(resumeForm, {
        name: '',
        phone: '',
        wechat: '',
        email: '',
        gender: '',
        birthday: '',
        address: '',
        idNumber: '',

        // 央国企特有字段
        govCurrentResidence: '',
        govRegisteredResidence: '',
        govNativePlace: '',
        govGaokaoPlace: '',
        govPoliticalStatus: '',
        govEmergencyName: '',
        govEmergencyPhone: '',
        govEmergencyRelation: '',

        // 银行特有字段
        bankPoliticalStatus: '',
        bankHeight: '',
        bankWeight: '',
        bankHealth: '',
        bankMarriageStatus: '',
        bankChildStatus: '',
        bankInfoChannel: '',
        bankEmergencyName: '',
        bankEmergencyPhone: '',
        bankEmergencyRelation: '',

        // 数组类型字段
        eduExperiences: [],
        privateInternship: [],
        govCampusPractice: [],
        govWorkExperience: [],
        privateProjectExperience: [],
        bankSchoolExperience: [],
        languageLevel: [],
        honors: [],
        certificates: [],
        skillLevel: [],
        privatePaper: [],
        privateCompetition: [],
        familyInfo: [],
        bankProfessionalCert: [],
        bankComputerSkills: [],
        bankTrainingExperience: [],
        bankRewardsPunishments: [],

        // 银行特有字段
        bankSalaryExpectation: '',
        bankExpectedLocation: '',

        // 自我评价
        selfEvaluation: ''
    })

    // 重置其他状态
    isProcessing.value = false
    cloudResume.value = null
    resumeType.value = '民企'
    searchKeyword.value = ''
    showSuggestions.value = false
    filteredSuggestions.value = []

    // 清理文件输入
    if (fileInput.value) {
        fileInput.value.value = ''
    }
}

// 清理临时状态（保留表单数据）
const cleanupTemporaryState = () => {
    // 只清理临时状态，不重置表单数据
    isProcessing.value = false
    cloudResume.value = null
    searchKeyword.value = ''
    showSuggestions.value = false
    filteredSuggestions.value = []
}
</script>

<style scoped>
.card {
    @apply bg-white shadow-sm rounded-lg p-6 border border-gray-200;
}

.input-field {
    @apply w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500;
}

.btn-primary {
    @apply px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition duration-200;
}

.btn-secondary {
    @apply px-4 py-2 bg-gray-200 text-gray-900 rounded-md hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2 transition duration-200;
}

.copy-btn {
    @apply p-1 bg-gray-100 text-gray-500 rounded hover:bg-green-300 hover:text-green-700 transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-green-200 focus:ring-offset-2;
}

.resume-copy-btn {
    @apply px-3 py-2 bg-green-300 text-white rounded-md hover:bg-green-400 transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-green-200 focus:ring-offset-2 flex items-center justify-center;
}

.copy-btn-small {
    @apply p-0.5 bg-gray-100 text-gray-400 rounded hover:bg-green-300 hover:text-green-600 transition-all duration-200 focus:outline-none focus:ring-1 focus:ring-green-200;
}

.copy-btn:hover,
.copy-btn-small:hover {
    transform: scale(1.05);
}

.group:hover .copy-btn {
    @apply shadow-sm;
}

/* Horizontal slide transition styles */
.slide-x-enter-active,
.slide-x-leave-active {
    transition: all 0.5s ease;
}

.slide-x-enter-from {
    opacity: 0;
    transform: translateX(24px);
}

.slide-x-leave-to {
    opacity: 0;
    transform: translateX(-24px);
}

.slide-x-enter-to,
.slide-x-leave-from {
    opacity: 1;
    transform: translateX(0);
}

/* 搜索高亮效果 */
.highlight-search-result {
    animation: highlight-pulse 2s ease-in-out;
    box-shadow: 0 0 0 2px #3b82f6;
    border-radius: 8px;
}

@keyframes highlight-pulse {
    0% {
        box-shadow: 0 0 0 0 rgba(59, 130, 246, 0.7);
    }

    50% {
        box-shadow: 0 0 0 8px rgba(59, 130, 246, 0.3);
    }

    100% {
        box-shadow: 0 0 0 0 rgba(59, 130, 246, 0);
    }
}

/* 搜索框样式优化 */
.search-input {
    @apply w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition duration-200;
}

.search-button {
    @apply ml-2 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition duration-200 flex items-center;
}

.search-icon {
    @apply w-5 h-5 text-gray-400;
}

.search-suggestions {
    @apply absolute z-10 w-80 bg-white border border-gray-200 rounded-lg shadow-lg mt-1 max-h-60 overflow-y-auto;
}

.search-suggestion-item {
    @apply px-4 py-2 cursor-pointer hover:bg-gray-100 flex justify-between items-center;
}
</style>