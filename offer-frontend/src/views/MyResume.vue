<template>
    <div class="space-y-6">
        <!-- 页面标题 -->
        <div class="text-center mb-8">
            <h1 class="text-4xl font-bold text-gray-900 mb-2">网申助手</h1>
            <p class="text-gray-600">告别填写简历时繁琐的复制粘贴</p>
        </div>



        <!-- 简历表单 -->
        <div v-if="userStore.currentUser" class="card">
            <!-- 文档导入区域 -->
            <div class="mb-6 p-4 bg-blue-50 border border-blue-200 rounded-lg">
                <h3 class="text-lg font-medium text-gray-900 mb-3">📄 导入简历文档</h3>
                <div class="flex items-center space-x-4">
                    <input ref="fileInput" type="file" accept=".pdf,.doc,.docx" @change="handleFileUpload"
                        class="hidden" />
                    <button @click="triggerFileInputClick"
                        class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 flex items-center space-x-2">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12">
                            </path>
                        </svg>
                        <span>选择文档</span>
                    </button>
                    <span class="text-sm text-gray-600">支持 PDF、DOC、DOCX 格式，最大 10MB（自动解析并填入简历信息）</span>
                </div>
                <div v-if="isProcessing" class="mt-3 flex items-center space-x-2 text-blue-600">
                    <svg class="animate-spin h-4 w-4" fill="none" viewBox="0 0 24 24">
                        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4">
                        </circle>
                        <path class="opacity-75" fill="currentColor"
                            d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z">
                        </path>
                    </svg>
                    <span class="text-sm">正在解析文档...</span>
                </div>
            </div>
        </div>

        <!-- 简历类型选择 -->
        <div class="card">
            <h2 class="text-xl font-semibold text-gray-900 mb-4">简历类型</h2>
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

        <!-- 简历信息 -->
        <div v-if="userStore.currentUser" class="card">
            <transition name="slide" mode="out-in">
                <div :key="resumeType">
                    <div class="flex justify-between items-center mb-6">
                        <h2 class="text-xl font-semibold text-gray-900">简历信息</h2>
                        <div class="flex space-x-2">
                            <button v-if="hasResumeData" @click="clearResume" class="btn-secondary">
                                清空简历
                            </button>
                            <button v-if="hasResumeData && cloudResume" @click="clearCloudResume"
                                class="px-4 py-2 bg-red-400 text-white rounded-md hover:bg-red-500 focus:outline-none focus:ring-2 focus:ring-red-300 focus:ring-offset-2 flex items-center space-x-2">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1-1H8a1 1 0 00-1 1v3M4 7h16">
                                    </path>
                                </svg>
                                <span>清除云端数据</span>
                            </button>
                            <button @click="saveResume" :disabled="isSaving" class="btn-primary">
                                {{ isSaving ? '保存中...' : '保存简历' }}
                            </button>
                        </div>
                    </div>

                    <form @submit.prevent="saveResume" class="space-y-6">
                <!-- 基本信息 -->
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">姓名 <span
                                class="text-red-500">*</span></label>
                        <div class="flex items-center space-x-2">
                            <input v-model="resumeForm.name" type="text" required placeholder="请输入您的姓名"
                                class="input-field flex-1" />
                            <button @click="copyToClipboard(resumeForm.name, '姓名')" class="resume-copy-btn">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">手机号</label>
                        <div class="flex items-center space-x-2">
                            <input v-model="resumeForm.phone" type="tel" placeholder="请输入手机号"
                                class="input-field flex-1" />
                            <button @click="copyToClipboard(resumeForm.phone, '手机号')" class="resume-copy-btn">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">微信号</label>
                        <div class="flex items-center space-x-2">
                            <input v-model="resumeForm.wechat" type="text" placeholder="请输入微信号"
                                class="input-field flex-1" />
                            <button @click="copyToClipboard(resumeForm.wechat, '微信号')" class="resume-copy-btn">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">邮箱</label>
                        <div class="flex items-center space-x-2">
                            <input v-model="resumeForm.email" type="email" placeholder="请输入邮箱"
                                class="input-field flex-1" />
                            <button @click="copyToClipboard(resumeForm.email, '邮箱')" class="resume-copy-btn">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">性别</label>
                        <div class="flex items-center space-x-2">
                            <select v-model="resumeForm.gender" class="input-field flex-1">
                                <option value="">请选择</option>
                                <option value="男">男</option>
                                <option value="女">女</option>
                            </select>
                            <button @click="copyToClipboard(resumeForm.gender, '性别')" class="resume-copy-btn">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">出生日期</label>
                        <div class="flex items-center space-x-2">
                            <input v-model="resumeForm.birthday" type="date" class="input-field flex-1" />
                            <button @click="copyToClipboard(resumeForm.birthday, '出生日期')" class="resume-copy-btn">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div class="md:col-span-2 lg:col-span-3">
                        <label class="block text-sm font-medium text-gray-700 mb-1">地址</label>
                        <div class="flex items-center space-x-2">
                            <input v-model="resumeForm.address" type="text" placeholder="请输入您的地址"
                                class="input-field flex-1" />
                            <button @click="copyToClipboard(resumeForm.address, '地址')" class="resume-copy-btn">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                </div>

                <!-- 详细信息 -->
                <div class="space-y-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">教育背景</label>
                        <div class="flex space-x-2">
                            <textarea v-model="resumeForm.education" rows="4" placeholder="请填写您的教育背景，如：学校、专业、时间等"
                                class="input-field resize-none flex-1"></textarea>
                            <button @click="copyToClipboard(resumeForm.education, '教育背景')"
                                class="resume-copy-btn flex-shrink-0 self-start">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">专业技能</label>
                        <div class="flex space-x-2">
                            <textarea v-model="resumeForm.skills" rows="4" placeholder="请填写您的专业技能"
                                class="input-field resize-none flex-1"></textarea>
                            <button @click="copyToClipboard(resumeForm.skills, '专业技能')"
                                class="resume-copy-btn flex-shrink-0 self-start">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">工作经验</label>
                        <div class="flex space-x-2">
                            <textarea v-model="resumeForm.workExperience" rows="4" placeholder="请填写您的工作经验"
                                class="input-field resize-none flex-1"></textarea>
                            <button @click="copyToClipboard(resumeForm.workExperience, '工作经验')"
                                class="resume-copy-btn flex-shrink-0 self-start">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">项目经历</label>
                        <div class="flex space-x-2">
                            <textarea v-model="resumeForm.projectExperience" rows="4" placeholder="请填写您的项目经历"
                                class="input-field resize-none flex-1"></textarea>
                            <button @click="copyToClipboard(resumeForm.projectExperience, '项目经历')"
                                class="resume-copy-btn flex-shrink-0 self-start">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">实习经历</label>
                        <div class="flex space-x-2">
                            <textarea v-model="resumeForm.internshipExperience" rows="4" placeholder="请填写您的实习经历"
                                class="input-field resize-none flex-1"></textarea>
                            <button @click="copyToClipboard(resumeForm.internshipExperience, '实习经历')"
                                class="resume-copy-btn flex-shrink-0 self-start">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">荣誉证书</label>
                        <div class="flex space-x-2">
                            <textarea v-model="resumeForm.certificates" rows="4" placeholder="请填写您的荣誉证书"
                                class="input-field resize-none flex-1"></textarea>
                            <button @click="copyToClipboard(resumeForm.certificates, '荣誉证书')"
                                class="resume-copy-btn flex-shrink-0 self-start">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">自我评价</label>
                        <div class="flex space-x-2">
                            <textarea v-model="resumeForm.selfEvaluation" rows="4" placeholder="请填写您的自我评价"
                                class="input-field resize-none flex-1"></textarea>
                            <button @click="copyToClipboard(resumeForm.selfEvaluation, '自我评价')"
                                class="resume-copy-btn flex-shrink-0 self-start">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </transition>
</div> <!-- Close the card div for "简历信息" -->
        <!-- 简历预览 -->
        <div v-if="hasResumeData && userStore.currentUser" class="card">
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
                        <button v-if="resumeForm.name" @click="copyToClipboard(resumeForm.name, '姓名')" class="copy-btn"
                            title="复制姓名">
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

                <div class="space-y-4">
                    <div v-if="resumeForm.education" class="border-l-4 border-blue-500 pl-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">🎓 教育背景</h4>
                                <p class="text-gray-700 whitespace-pre-wrap">{{ resumeForm.education }}</p>
                            </div>
                            <button @click="copyToClipboard(resumeForm.education, '教育背景')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div v-if="resumeForm.skills" class="border-l-4 border-green-500 pl-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">💼 专业技能</h4>
                                <p class="text-gray-700 whitespace-pre-wrap">{{ resumeForm.skills }}</p>
                            </div>
                            <button @click="copyToClipboard(resumeForm.skills, '专业技能')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div v-if="resumeForm.workExperience" class="border-l-4 border-purple-500 pl-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">💻 工作经验</h4>
                                <p class="text-gray-700 whitespace-pre-wrap">{{ resumeForm.workExperience }}</p>
                            </div>
                            <button @click="copyToClipboard(resumeForm.workExperience, '工作经验')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div v-if="resumeForm.projectExperience" class="border-l-4 border-yellow-500 pl-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">🚀 项目经历</h4>
                                <p class="text-gray-700 whitespace-pre-wrap">{{ resumeForm.projectExperience }}</p>
                            </div>
                            <button @click="copyToClipboard(resumeForm.projectExperience, '项目经历')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div v-if="resumeForm.internshipExperience" class="border-l-4 border-red-500 pl-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">🌟 实习经历</h4>
                                <p class="text-gray-700 whitespace-pre-wrap">{{ resumeForm.internshipExperience }}</p>
                            </div>
                            <button @click="copyToClipboard(resumeForm.internshipExperience, '实习经历')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div v-if="resumeForm.certificates" class="border-l-4 border-indigo-500 pl-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">🏆 荣誉证书</h4>
                                <p class="text-gray-700 whitespace-pre-wrap">{{ resumeForm.certificates }}</p>
                            </div>
                            <button @click="copyToClipboard(resumeForm.certificates, '荣誉证书')"
                                class="copy-btn ml-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                    </path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div v-if="resumeForm.selfEvaluation" class="border-l-4 border-pink-500 pl-4 relative group">
                        <div class="flex items-start justify-between">
                            <div class="flex-1">
                                <h4 class="font-semibold text-gray-900 mb-2">✨ 自我评价</h4>
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
        </div>



        <!-- 登录模态框 -->
        <LoginModal v-if="showLoginModal" @close="showLoginModal = false" />
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted, watch, onActivated, onDeactivated } from 'vue'
import { useUserStore } from '@/stores/user'
import type { Resume, ResumeAddRequest, ResumeUpdateRequest } from '@/api/types'
import type { DeleteRequest } from '@/api/types'
import Message from '@/components/Message'
import Confirm from '@/components/Confirm'
// 文档解析将改为后端处理
// 导入简历API
import { getMyResume, addResume, updateResume, deleteResume, parsePdfResume } from '@/api/resume'
// 导入登录模态框组件
import LoginModal from '@/components/LoginModal.vue'

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
    education: '',
    skills: '',
    workExperience: '',
    projectExperience: '',
    internshipExperience: '',
    certificates: '',
    selfEvaluation: ''
})

// 是否有简历数据
const hasResumeData = computed(() => {
    const stringFields: Array<keyof ResumeAddRequest> = [
        'name', 'phone', 'wechat', 'email', 'gender', 'birthday', 'address',
        'education', 'skills', 'workExperience', 'projectExperience',
        'internshipExperience', 'certificates', 'selfEvaluation'
    ];
    return stringFields.some(key => {
        const value = resumeForm[key];
        return typeof value === 'string' && value.trim() !== '';
    });
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
        'education': 'education',
        'skills': 'skills',
        'workExperience': 'workExperience',
        'projectExperience': 'projectExperience',
        'internshipExperience': 'internshipExperience',
        'certificates': 'certificates',
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
                education: response.data.education || '',
                skills: response.data.skills || '',
                workExperience: response.data.workExperience || '',
                projectExperience: response.data.projectExperience || '',
                internshipExperience: response.data.internshipExperience || '',
                certificates: response.data.certificates || '',
                selfEvaluation: response.data.selfEvaluation || ''
            })
        } else {
            cloudResume.value = null
        }
    } catch (error) {
        console.error('加载云端简历失败:', error)
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
        console.error('保存云端简历失败:', error)
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
        message: '确定要清空表单中的简历数据吗？这不会删除云端已保存的数据。',
        type: 'warning',
        confirmText: '确定清空',
        cancelText: '取消'
    })

    if (confirmed) {
        Object.keys(resumeForm).forEach(key => {
            (resumeForm as any)[key] = ''
        })
        Message.success('表单数据已清空')
    }
}

// 清除云端数据
const clearCloudResume = async () => {
    if (!cloudResume.value) {
        Message.warning('没有云端简历数据可清除')
        return
    }

    const confirmed = await Confirm.show({
        title: '删除云端数据',
        message: '确定要永久删除云端简历数据吗？此操作不可恢复。',
        type: 'danger',
        confirmText: '确定删除',
        cancelText: '取消'
    })

    if (confirmed) {
        try {
            const response = await deleteResume({ id: cloudResume.value.id })
            if (response.statusCode === 200) {
                // 清空表单数据
                Object.keys(resumeForm).forEach(key => {
                    (resumeForm as any)[key] = ''
                })
                // 清空云端简历引用
                cloudResume.value = null
                Message.success('云端简历数据已删除')
            } else {
                Message.error(response.message || '删除失败')
            }
        } catch (error: any) {
            console.error('删除云端简历失败:', error)
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

    // 详细信息
    if (resumeForm.education) {
        text += '教育背景：\n'
        text += resumeForm.education + '\n\n'
    }

    if (resumeForm.skills) {
        text += '专业技能：\n'
        text += resumeForm.skills + '\n\n'
    }

    if (resumeForm.workExperience) {
        text += '工作经验：\n'
        text += resumeForm.workExperience + '\n\n'
    }

    if (resumeForm.projectExperience) {
        text += '项目经历：\n'
        text += resumeForm.projectExperience + '\n\n'
    }

    if (resumeForm.internshipExperience) {
        text += '实习经历：\n'
        text += resumeForm.internshipExperience + '\n\n'
    }

    if (resumeForm.certificates) {
        text += '荣誉证书：\n'
        text += resumeForm.certificates + '\n\n'
    }

    if (resumeForm.selfEvaluation) {
        text += '自我评价：\n'
        text += resumeForm.selfEvaluation + '\n\n'
    }

    return text.trim()
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

// 组件失活时保存数据
onDeactivated(() => {
    // 可以在这里添加清理逻辑
    // 例如保存当前表单数据
    // 由于现在是完全云端存储，这里不需要额外保存逻辑
})
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

/* Slide transition styles */
.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s ease-in-out, opacity 0.3s ease-in-out;
}

.slide-enter-from {
  transform: translateX(100%);
  opacity: 0;
}

.slide-leave-to {
  transform: translateX(-100%);
  opacity: 0;
}
</style>
