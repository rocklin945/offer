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
                                class="px-4 py-2 bg-red-400 text-white rounded-md hover:bg-red-500 focus:outline-none focus:ring-2 focus:ring-red-300 focus:ring-offset-2 flex items-center space-x-2">
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
                        <BasicInfo :resume-form="resumeForm" :resume-type="resumeType"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 教育经历 (所有类型都显示) -->
                        <EducationExperience :resume-form="resumeForm" @add-edu-experience="addEduExperience"
                            @remove-edu-experience="removeEduExperience" @copy-to-clipboard="copyToClipboard" />

                        <!-- 根据简历类型显示不同的经历字段 -->
                        <!-- 民企: 实习经历和项目经历 -->
                        <InternshipExperience v-if="resumeType === '民企'" :resume-form="resumeForm"
                            @add-internship="addInternship" @remove-internship="removeInternship"
                            @copy-to-clipboard="copyToClipboard" />

                        <ProjectExperience v-if="resumeType === '民企'" :resume-form="resumeForm"
                            @add-project-experience="addProjectExperience"
                            @remove-project-experience="removeProjectExperience" @copy-to-clipboard="copyToClipboard" />

                        <!-- 央国企: 校园实践和工作经历 -->
                        <CampusPractice v-if="resumeType === '央国企'" :resume-form="resumeForm"
                            @add-campus-practice="addCampusPractice" @remove-campus-practice="removeCampusPractice"
                            @copy-to-clipboard="copyToClipboard" />

                        <WorkExperience v-if="resumeType === '央国企'" :resume-form="resumeForm"
                            @add-work-experience="addWorkExperience" @remove-work-experience="removeWorkExperience"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 银行: 在校经历 -->
                        <SchoolExperience v-if="resumeType === '银行'" :resume-form="resumeForm"
                            @add-school-experience="addSchoolExperience"
                            @remove-school-experience="removeSchoolExperience" @copy-to-clipboard="copyToClipboard" />

                        <!-- 语言水平 (所有类型都显示) -->
                        <LanguageLevel :resume-form="resumeForm" @add-language-level="addLanguageLevel"
                            @remove-language-level="removeLanguageLevel" @copy-to-clipboard="copyToClipboard" />

                        <!-- 专业技能 (所有类型都显示) -->
                        <SkillLevel :resume-form="resumeForm" @add-skill-level="addSkillLevel"
                            @remove-skill-level="removeSkillLevel" @copy-to-clipboard="copyToClipboard" />

                        <!-- 荣誉奖项 (所有类型都显示) -->
                        <Honors :resume-form="resumeForm" @add-honor="addHonor" @remove-honor="removeHonor"
                            @copy-to-clipboard="copyToClipboard" />

                        <!-- 证书 (所有类型都显示) -->
                        <Certificates :resume-form="resumeForm" @add-certificate="addCertificate"
                            @remove-certificate="removeCertificate" @copy-to-clipboard="copyToClipboard" />

                        <!-- 自我评价 (所有类型都显示) -->
                        <SelfEvaluation :resume-form="resumeForm" @copy-to-clipboard="copyToClipboard" />
                    </form>
                </div>
            </transition>

            <!-- 简历预览 -->
            <div v-if="hasResumeData && userStore.currentUser" class="card mt-8">
                <div class="flex justify-between items-center mb-4">
                    <h2 class="text-xl font-semibold text-gray-900">简历预览</h2>
                    <button @click="copyFullResume" class="btn-secondary flex items-center space-x-2">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                        <span>复制完整简历</span>
                    </button>
                </div>
                <div class="bg-gray-50 p-6 rounded-lg">
                    <div class="text-center mb-6">
                        <div class="flex items-center justify-center space-x-2 mb-2">
                            <h3 class="text-2xl font-bold text-gray-900">{{ resumeForm.name || '姓名' }}</h3>
                            <button v-if="resumeForm.name" @click="copyToClipboard(resumeForm.name, '姓名')"
                                class="copy-btn" title="复制姓名">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                        <div class="flex flex-wrap justify-center gap-4 mt-2 text-sm text-gray-600">
                            <div v-if="resumeForm.phone" class="flex items-center space-x-1">
                                <span>📱 {{ resumeForm.phone }}</span>
                                <button @click="copyToClipboard(resumeForm.phone, '手机号')" class="copy-btn-small">
                                    <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                        </path>
                                    </svg>
                                </button>
                            </div>
                            <div v-if="resumeForm.wechat" class="flex items-center space-x-1">
                                <span>💬 {{ resumeForm.wechat }}</span>
                                <button @click="copyToClipboard(resumeForm.wechat, '微信号')" class="copy-btn-small">
                                    <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                        </path>
                                    </svg>
                                </button>
                            </div>
                            <div v-if="resumeForm.email" class="flex items-center space-x-1">
                                <span>📧 {{ resumeForm.email }}</span>
                                <button @click="copyToClipboard(resumeForm.email, '邮箱')" class="copy-btn-small">
                                    <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                        </path>
                                    </svg>
                                </button>
                            </div>
                            <div v-if="resumeForm.gender" class="flex items-center space-x-1">
                                <span>👤 {{ resumeForm.gender }}</span>
                                <button @click="copyToClipboard(resumeForm.gender, '性别')" class="copy-btn-small">
                                    <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                        </path>
                                    </svg>
                                </button>
                            </div>
                            <div v-if="resumeForm.birthday" class="flex items-center space-x-1">
                                <span>📅 {{ resumeForm.birthday }}</span>
                                <button @click="copyToClipboard(resumeForm.birthday, '出生日期')" class="copy-btn-small">
                                    <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                        </path>
                                    </svg>
                                </button>
                            </div>
                        </div>
                        <div v-if="resumeForm.address"
                            class="flex items-center justify-center space-x-1 text-sm text-gray-600 mt-1">
                            <span>📍 {{ resumeForm.address }}</span>
                            <button @click="copyToClipboard(resumeForm.address, '地址')" class="copy-btn-small">
                                <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <!-- 教育经历预览 -->
                    <div v-if="resumeForm.eduExperiences && resumeForm.eduExperiences.length > 0"
                        class="border-l-4 border-blue-500 pl-4 mb-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">🎓 教育经历</h4>
                                <div v-for="(edu, index) in resumeForm.eduExperiences" :key="index" class="mb-3">
                                    <div class="font-medium">{{ edu.schoolName }} - {{ edu.majorName }} ({{ edu.degree
                                    }})</div>
                                    <div class="text-sm text-gray-600">{{ edu.period }}</div>
                                    <div v-if="edu.gpa" class="text-sm text-gray-600">GPA: {{ edu.gpa }}</div>
                                </div>
                            </div>
                            <button @click="copySectionToClipboard('eduExperiences')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <!-- 根据简历类型显示不同的经历预览 -->
                    <!-- 民企: 实习经历和项目经历 -->
                    <div v-if="resumeType === '民企' && resumeForm.privateInternship && resumeForm.privateInternship.length > 0"
                        class="border-l-4 border-purple-500 pl-4 mb-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">💼 实习经历</h4>
                                <div v-for="(internship, index) in resumeForm.privateInternship" :key="index"
                                    class="mb-3">
                                    <div class="font-medium">{{ internship.company }} - {{ internship.position }}</div>
                                    <div class="text-sm text-gray-600">{{ internship.startTime }} - {{
                                        internship.endTime }}</div>
                                    <div v-if="internship.description"
                                        class="text-sm text-gray-600 whitespace-pre-wrap">{{ internship.description }}
                                    </div>
                                </div>
                            </div>
                            <button @click="copySectionToClipboard('privateInternship')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <div v-if="resumeType === '民企' && resumeForm.privateProjectExperience && resumeForm.privateProjectExperience.length > 0"
                        class="border-l-4 border-yellow-500 pl-4 mb-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">🚀 项目经历</h4>
                                <div v-for="(project, index) in resumeForm.privateProjectExperience" :key="index"
                                    class="mb-3">
                                    <div class="font-medium">{{ project.projectName }} - {{ project.role }}</div>
                                    <div class="text-sm text-gray-600">{{ project.projectTime }}</div>
                                    <div v-if="project.description" class="text-sm text-gray-600 whitespace-pre-wrap">{{
                                        project.description }}</div>
                                </div>
                            </div>
                            <button @click="copySectionToClipboard('privateProjectExperience')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <!-- 央国企: 校园实践和工作经历 -->
                    <div v-if="resumeType === '央国企' && resumeForm.govCampusPractice && resumeForm.govCampusPractice.length > 0"
                        class="border-l-4 border-green-500 pl-4 mb-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">🏫 校园实践</h4>
                                <div v-for="(practice, index) in resumeForm.govCampusPractice" :key="index"
                                    class="mb-3">
                                    <div class="text-sm text-gray-600">{{ practice.startTime }} - {{ practice.endTime }}
                                    </div>
                                    <div v-if="practice.description" class="text-sm text-gray-600 whitespace-pre-wrap">
                                        {{ practice.description }}</div>
                                </div>
                            </div>
                            <button @click="copySectionToClipboard('govCampusPractice')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <div v-if="resumeType === '央国企' && resumeForm.govWorkExperience && resumeForm.govWorkExperience.length > 0"
                        class="border-l-4 border-indigo-500 pl-4 mb-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">🏢 工作经历</h4>
                                <div v-for="(work, index) in resumeForm.govWorkExperience" :key="index" class="mb-3">
                                    <div class="font-medium">{{ work.company }} - {{ work.position }}</div>
                                    <div class="text-sm text-gray-600">{{ work.startTime }} - {{ work.endTime }}</div>
                                    <div v-if="work.duty" class="text-sm text-gray-600">职责: {{ work.duty }}</div>
                                </div>
                            </div>
                            <button @click="copySectionToClipboard('govWorkExperience')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <!-- 银行: 在校经历 -->
                    <div v-if="resumeType === '银行' && resumeForm.bankSchoolExperience && resumeForm.bankSchoolExperience.length > 0"
                        class="border-l-4 border-red-500 pl-4 mb-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">📚 在校经历</h4>
                                <div v-for="(school, index) in resumeForm.bankSchoolExperience" :key="index"
                                    class="mb-3">
                                    <div class="font-medium">{{ school.name }}</div>
                                    <div class="text-sm text-gray-600">{{ school.startTime }} - {{ school.endTime }}
                                    </div>
                                    <div v-if="school.description" class="text-sm text-gray-600 whitespace-pre-wrap">{{
                                        school.description }}</div>
                                </div>
                            </div>
                            <button @click="copySectionToClipboard('bankSchoolExperience')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <!-- 语言水平预览 -->
                    <div v-if="resumeForm.languageLevel && resumeForm.languageLevel.length > 0"
                        class="border-l-4 border-teal-500 pl-4 mb-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">🗣️ 语言水平</h4>
                                <div v-for="(language, index) in resumeForm.languageLevel" :key="index" class="mb-1">
                                    <span class="font-medium">{{ language.language }}</span>
                                    <span v-if="language.level">- {{ language.level }}</span>
                                    <span v-if="language.exam"> ({{ language.exam }}: {{ language.score }})</span>
                                </div>
                            </div>
                            <button @click="copySectionToClipboard('languageLevel')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <!-- 专业技能预览 -->
                    <div v-if="resumeForm.skillLevel && resumeForm.skillLevel.length > 0"
                        class="border-l-4 border-orange-500 pl-4 mb-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">🔧 专业技能</h4>
                                <div v-for="(skill, index) in resumeForm.skillLevel" :key="index" class="mb-1">
                                    <span class="font-medium">{{ skill.skillType }}</span>
                                    <span v-if="skill.level">- {{ skill.level }}</span>
                                </div>
                            </div>
                            <button @click="copySectionToClipboard('skillLevel')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <!-- 荣誉奖项预览 -->
                    <div v-if="resumeForm.honors && resumeForm.honors.length > 0"
                        class="border-l-4 border-pink-500 pl-4 mb-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">🏆 荣誉奖项</h4>
                                <div class="flex flex-wrap gap-2">
                                    <span v-for="(honor, index) in resumeForm.honors" :key="index"
                                        class="bg-pink-100 text-pink-800 text-xs font-medium px-2.5 py-0.5 rounded">
                                        {{ honor }}
                                    </span>
                                </div>
                            </div>
                            <button @click="copySectionToClipboard('honors')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <!-- 证书预览 -->
                    <div v-if="resumeForm.certificates && resumeForm.certificates.length > 0"
                        class="border-l-4 border-yellow-600 pl-4 mb-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">📜 证书</h4>
                                <div class="flex flex-wrap gap-2">
                                    <span v-for="(cert, index) in resumeForm.certificates" :key="index"
                                        class="bg-yellow-100 text-yellow-800 text-xs font-medium px-2.5 py-0.5 rounded">
                                        {{ cert }}
                                    </span>
                                </div>
                            </div>
                            <button @click="copySectionToClipboard('certificates')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <!-- 自我评价预览 -->
                    <div v-if="resumeForm.selfEvaluation" class="border-l-4 border-gray-500 pl-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">📝 自我评价</h4>
                                <p class="text-gray-700 whitespace-pre-wrap">{{ resumeForm.selfEvaluation }}</p>
                            </div>
                            <button @click="copyToClipboard(resumeForm.selfEvaluation, '自我评价')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 登录模态框 -->
            <LoginModal v-if="showLoginModal" @close="showLoginModal = false" />
        </div>
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

// 配置PDF.js worker
// 在生产环境中使用动态导入的worker路径


const userStore = useUserStore()
const showLoginModal = ref(false)
const isSaving = ref(false)
const cloudResume = ref<Resume | null>(null)
// 文档解析相关状态
const isProcessing = ref(false)
const fileInput = ref<HTMLInputElement | null>(null)

// 简历类型
const resumeType = ref<'民企' | '央国企' | '银行'>('民企')

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

// 复制完整简历
const copyFullResume = async () => {
    const resumeText = generateResumeText()
    await copyToClipboard(resumeText, '完整简历')
}

// 生成简历文本
const generateResumeText = (): string => {
    let text = ''

    // 基本信息
    text += `姓名：${resumeForm.name || ''}\n`
    if (resumeForm.phone) text += `手机号：${resumeForm.phone}\n`
    if (resumeForm.wechat) text += `微信号：${resumeForm.wechat}\n`
    if (resumeForm.email) text += `邮箱：${resumeForm.email}\n`
    if (resumeForm.gender) text += `性别：${resumeForm.gender}\n`
    if (resumeForm.birthday) text += `出生日期：${resumeForm.birthday}\n`
    if (resumeForm.address) text += `地址：${resumeForm.address}\n`

    text += '\n'

    // 教育经历
    if (resumeForm.eduExperiences && resumeForm.eduExperiences.length > 0) {
        text += '教育经历：\n'
        resumeForm.eduExperiences.forEach((edu, index) => {
            text += `${index + 1}. ${edu.schoolName || ''} - ${edu.majorName || ''} (${edu.degree || ''})\n`
            if (edu.period) text += `   时间：${edu.period}\n`
            if (edu.gpa) text += `   GPA：${edu.gpa}\n`
            if (edu.majorDetail) text += `   研究方向：${edu.majorDetail}\n`
            if (edu.supervisor) text += `   导师：${edu.supervisor}\n`
            text += '\n'
        })
    }

    // 根据简历类型显示不同的经历
    if (resumeType.value === '民企') {
        // 实习经历
        if (resumeForm.privateInternship && resumeForm.privateInternship.length > 0) {
            text += '实习经历：\n'
            resumeForm.privateInternship.forEach((internship, index) => {
                text += `${index + 1}. ${internship.company || ''} - ${internship.position || ''}\n`
                if (internship.startTime || internship.endTime) text += `   时间：${internship.startTime || ''} - ${internship.endTime || ''}\n`
                if (internship.description) text += `   描述：${internship.description}\n`
                text += '\n'
            })
        }

        // 项目经历
        if (resumeForm.privateProjectExperience && resumeForm.privateProjectExperience.length > 0) {
            text += '项目经历：\n'
            resumeForm.privateProjectExperience.forEach((project, index) => {
                text += `${index + 1}. ${project.projectName || ''} - ${project.role || ''}\n`
                if (project.projectTime) text += `   时间：${project.projectTime}\n`
                if (project.projectLink) text += `   链接：${project.projectLink}\n`
                if (project.description) text += `   描述：${project.description}\n`
                text += '\n'
            })
        }
    } else if (resumeType.value === '央国企') {
        // 校园实践
        if (resumeForm.govCampusPractice && resumeForm.govCampusPractice.length > 0) {
            text += '校园实践：\n'
            resumeForm.govCampusPractice.forEach((practice, index) => {
                text += `${index + 1}. ${practice.startTime || ''} - ${practice.endTime || ''}\n`
                if (practice.description) text += `   描述：${practice.description}\n`
                text += '\n'
            })
        }

        // 工作经历
        if (resumeForm.govWorkExperience && resumeForm.govWorkExperience.length > 0) {
            text += '工作经历：\n'
            resumeForm.govWorkExperience.forEach((work, index) => {
                text += `${index + 1}. ${work.company || ''} - ${work.position || ''}\n`
                if (work.startTime || work.endTime) text += `   时间：${work.startTime || ''} - ${work.endTime || ''}\n`
                if (work.duty) text += `   职责：${work.duty}\n`
                if (work.workType) text += `   类型：${work.workType}\n`
                text += '\n'
            })
        }
    } else if (resumeType.value === '银行') {
        // 在校经历
        if (resumeForm.bankSchoolExperience && resumeForm.bankSchoolExperience.length > 0) {
            text += '在校经历：\n'
            resumeForm.bankSchoolExperience.forEach((school, index) => {
                text += `${index + 1}. ${school.name || ''}\n`
                if (school.startTime || school.endTime) text += `   时间：${school.startTime || ''} - ${school.endTime || ''}\n`
                if (school.description) text += `   描述：${school.description}\n`
                text += '\n'
            })
        }
    }

    // 语言水平
    if (resumeForm.languageLevel && resumeForm.languageLevel.length > 0) {
        text += '语言水平：\n'
        resumeForm.languageLevel.forEach((language, index) => {
            text += `${index + 1}. ${language.language || ''}`
            if (language.level) text += ` - ${language.level}`
            if (language.exam) text += ` (${language.exam}: ${language.score || ''})`
            text += '\n'
        })
        text += '\n'
    }

    // 专业技能
    if (resumeForm.skillLevel && resumeForm.skillLevel.length > 0) {
        text += '专业技能：\n'
        resumeForm.skillLevel.forEach((skill, index) => {
            text += `${index + 1}. ${skill.skillType || ''}`
            if (skill.level) text += ` - ${skill.level}`
            text += '\n'
        })
        text += '\n'
    }

    // 荣誉奖项
    if (resumeForm.honors && resumeForm.honors.length > 0) {
        text += '荣誉奖项：\n'
        resumeForm.honors.forEach((honor, index) => {
            text += `${index + 1}. ${honor}\n`
        })
        text += '\n'
    }

    // 证书
    if (resumeForm.certificates && resumeForm.certificates.length > 0) {
        text += '证书：\n'
        resumeForm.certificates.forEach((cert, index) => {
            text += `${index + 1}. ${cert}\n`
        })
        text += '\n'
    }

    // 自我评价
    if (resumeForm.selfEvaluation) {
        text += '自我评价：\n'
        text += resumeForm.selfEvaluation + '\n\n'
    }

    return text.trim()
}

// 复制特定部分到剪贴板
const copySectionToClipboard = async (section: string) => {
    let text = ''

    switch (section) {
        case 'eduExperiences':
            if (resumeForm.eduExperiences && resumeForm.eduExperiences.length > 0) {
                text = '教育经历：\n'
                resumeForm.eduExperiences.forEach((edu, index) => {
                    text += `${index + 1}. ${edu.schoolName || ''} - ${edu.majorName || ''} (${edu.degree || ''})\n`
                    if (edu.period) text += `   时间：${edu.period}\n`
                    if (edu.gpa) text += `   GPA：${edu.gpa}\n`
                    text += '\n'
                })
            }
            break
        case 'privateInternship':
            if (resumeForm.privateInternship && resumeForm.privateInternship.length > 0) {
                text = '实习经历：\n'
                resumeForm.privateInternship.forEach((internship, index) => {
                    text += `${index + 1}. ${internship.company || ''} - ${internship.position || ''}\n`
                    if (internship.startTime || internship.endTime) text += `   时间：${internship.startTime || ''} - ${internship.endTime || ''}\n`
                    if (internship.description) text += `   描述：${internship.description}\n`
                    text += '\n'
                })
            }
            break
        case 'privateProjectExperience':
            if (resumeForm.privateProjectExperience && resumeForm.privateProjectExperience.length > 0) {
                text = '项目经历：\n'
                resumeForm.privateProjectExperience.forEach((project, index) => {
                    text += `${index + 1}. ${project.projectName || ''} - ${project.role || ''}\n`
                    if (project.projectTime) text += `   时间：${project.projectTime}\n`
                    if (project.description) text += `   描述：${project.description}\n`
                    text += '\n'
                })
            }
            break
        case 'govCampusPractice':
            if (resumeForm.govCampusPractice && resumeForm.govCampusPractice.length > 0) {
                text = '校园实践：\n'
                resumeForm.govCampusPractice.forEach((practice, index) => {
                    text += `${index + 1}. ${practice.startTime || ''} - ${practice.endTime || ''}\n`
                    if (practice.description) text += `   描述：${practice.description}\n`
                    text += '\n'
                })
            }
            break
        case 'govWorkExperience':
            if (resumeForm.govWorkExperience && resumeForm.govWorkExperience.length > 0) {
                text = '工作经历：\n'
                resumeForm.govWorkExperience.forEach((work, index) => {
                    text += `${index + 1}. ${work.company || ''} - ${work.position || ''}\n`
                    if (work.startTime || work.endTime) text += `   时间：${work.startTime || ''} - ${work.endTime || ''}\n`
                    if (work.duty) text += `   职责：${work.duty}\n`
                    text += '\n'
                })
            }
            break
        case 'bankSchoolExperience':
            if (resumeForm.bankSchoolExperience && resumeForm.bankSchoolExperience.length > 0) {
                text = '在校经历：\n'
                resumeForm.bankSchoolExperience.forEach((school, index) => {
                    text += `${index + 1}. ${school.name || ''}\n`
                    if (school.startTime || school.endTime) text += `   时间：${school.startTime || ''} - ${school.endTime || ''}\n`
                    if (school.description) text += `   描述：${school.description}\n`
                    text += '\n'
                })
            }
            break
        case 'languageLevel':
            if (resumeForm.languageLevel && resumeForm.languageLevel.length > 0) {
                text = '语言水平：\n'
                resumeForm.languageLevel.forEach((language, index) => {
                    text += `${index + 1}. ${language.language || ''}`
                    if (language.level) text += ` - ${language.level}`
                    if (language.exam) text += ` (${language.exam}: ${language.score || ''})`
                    text += '\n'
                })
            }
            break
        case 'skillLevel':
            if (resumeForm.skillLevel && resumeForm.skillLevel.length > 0) {
                text = '专业技能：\n'
                resumeForm.skillLevel.forEach((skill, index) => {
                    text += `${index + 1}. ${skill.skillType || ''}`
                    if (skill.level) text += ` - ${skill.level}`
                    text += '\n'
                })
            }
            break
        case 'honors':
            if (resumeForm.honors && resumeForm.honors.length > 0) {
                text = '荣誉奖项：\n'
                resumeForm.honors.forEach((honor, index) => {
                    text += `${index + 1}. ${honor}\n`
                })
            }
            break
        case 'certificates':
            if (resumeForm.certificates && resumeForm.certificates.length > 0) {
                text = '证书：\n'
                resumeForm.certificates.forEach((cert, index) => {
                    text += `${index + 1}. ${cert}\n`
                })
            }
            break
        default:
            text = ''
    }

    if (text) {
        await copyToClipboard(text, section)
    } else {
        Message.warning('该部分内容为空，无法复制')
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

// 组件失活时清理数据
onDeactivated(() => {
    cleanupComponentState()
})

// 组件卸载时清理数据
onUnmounted(() => {
    cleanupComponentState()
})

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

    // 清理文件输入
    if (fileInput.value) {
        fileInput.value.value = ''
    }
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
    @apply px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed;
}

.btn-secondary {
    @apply px-4 py-2 bg-gray-200 text-gray-900 rounded-md hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2;
}

.copy-btn {
    @apply p-1 bg-gray-100 text-gray-500 rounded hover:bg-gray-200 hover:text-gray-700 transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2;
}

.resume-copy-btn {
    @apply px-3 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 flex items-center justify-center;
}

.copy-btn-small {
    @apply p-0.5 bg-gray-100 text-gray-400 rounded hover:bg-gray-200 hover:text-gray-600 transition-all duration-200 focus:outline-none focus:ring-1 focus:ring-blue-400;
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
</style>