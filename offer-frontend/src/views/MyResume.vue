<template>
    <div class="space-y-6">
        <!-- 页面标题 -->
        <div class="text-center mb-8">
            <h1 class="text-4xl font-bold text-gray-900 mb-2">个人简历</h1>
            <p class="text-gray-600">告别填写简历时繁琐的复制粘贴</p>
        </div>

        <!-- 存储方式选择 -->
        <div class="card">
            <h2 class="text-xl font-semibold text-gray-900 mb-4">存储方式</h2>
            <div class="flex space-x-4">
                <button @click="storageMode = 'local'"
                    :class="storageMode === 'local' ? 'btn-primary' : 'btn-secondary'"
                    class="flex items-center space-x-2">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z">
                        </path>
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M15 12a3 3 0 11-6 0 3 3 0 016 0z">
                        </path>
                    </svg>
                    <span>本地存储</span>
                </button>
                <button @click="storageMode = 'cloud'"
                    :class="storageMode === 'cloud' ? 'btn-primary' : 'btn-secondary'"
                    class="flex items-center space-x-2">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12">
                        </path>
                    </svg>
                    <span>云端存储</span>
                </button>
            </div>
            <div class="mt-2 text-sm text-gray-500">
                <p v-if="storageMode === 'local'">⚙️ 本地存储：数据保存在浏览器中，换设备时需要重新填写</p>
                <p v-else>☁️ 云端存储：数据保存在服务器，可在任意设备访问（需要登录）</p>
            </div>
        </div>

        <!-- 未登录提示（仅云端模式显示） -->
        <div v-if="storageMode === 'cloud' && !userStore.currentUser" class="card bg-yellow-50 border-yellow-200">
            <div class="text-center py-8">
                <p class="text-yellow-800 mb-4">使用云端存储需要先登录账户</p>
                <button @click="showLoginModal = true"
                    class="inline-block px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700">
                    去登录
                </button>
            </div>
        </div>

        <!-- 简历表单 -->
        <div v-if="storageMode === 'local' || userStore.currentUser" class="card">
            <!-- 文档导入区域 -->
            <div class="mb-6 p-4 bg-blue-50 border border-blue-200 rounded-lg">
                <h3 class="text-lg font-medium text-gray-900 mb-3">📄 导入简历文档</h3>
                <div class="flex items-center space-x-4">
                    <input ref="fileInput" type="file" accept=".pdf,.doc,.docx" @change="handleFileUpload"
                        class="hidden" />
                    <button @click="$refs.fileInput?.click()"
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

            <div class="flex justify-between items-center mb-6">
                <h2 class="text-xl font-semibold text-gray-900">简历信息</h2>
                <div class="flex space-x-2">
                    <button v-if="hasResumeData" @click="clearResume" class="btn-secondary">
                        清空简历
                    </button>
                    <button v-if="hasResumeData && storageMode === 'cloud' && cloudResume" @click="clearCloudResume"
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

        <!-- 简历预览 -->
        <div v-if="hasResumeData && (storageMode === 'local' || userStore.currentUser)" class="card">
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

        <!-- 存储信息 -->
        <div v-if="storageMode === 'local' || userStore.currentUser" class="card bg-blue-50 border-blue-200">
            <div class="flex items-center space-x-2 text-blue-800">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
                <div>
                    <p class="font-medium">存储信息</p>
                    <p class="text-sm" v-if="storageMode === 'local'">
                        数据已保存在浏览器本地存储中，清除浏览器数据会导致简历丢失
                    </p>
                    <p class="text-sm" v-else>
                        数据已保存在云端服务器，您可以在任意设备登录查看
                    </p>
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
// 导入文档解析库
import { getDocument, GlobalWorkerOptions } from 'pdfjs-dist/legacy/build/pdf'
import pdfjsWorker from 'pdfjs-dist/build/pdf.worker?url'
import mammoth from 'mammoth'
// 导入简历API
import { getMyResume, addResume, updateResume, deleteResume } from '@/api/resume'
// 导入登录模态框组件
import LoginModal from '@/components/LoginModal.vue'

// 配置PDF.js worker
// 在生产环境中使用动态导入的worker路径
if (import.meta.env.PROD) {
    // 使用动态路径，避免硬编码文件名
    // 为了处理MIME类型问题，我们使用一个更兼容的配置
    try {
        // 尝试设置worker源
        GlobalWorkerOptions.workerSrc = pdfjsWorker
    } catch (e) {
        console.warn('Failed to set PDF worker source, using fallback:', e)
        // 如果设置失败，使用空字符串让pdfjs使用fake worker
        GlobalWorkerOptions.workerSrc = ''
    }
} else {
    GlobalWorkerOptions.workerSrc = pdfjsWorker
}

// 添加额外的配置以处理MIME类型问题
GlobalWorkerOptions.workerPort = null // 确保不使用worker port

const userStore = useUserStore()
const showLoginModal = ref(false)
const storageMode = ref<'local' | 'cloud'>('local')
const isSaving = ref(false)
const cloudResume = ref<Resume | null>(null)
// 文档解析相关状态
const isProcessing = ref(false)
const fileInput = ref<HTMLInputElement | null>(null)

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
    return Object.values(resumeForm).some(value => value && value.trim() !== '')
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
    const allowedTypes = ['application/pdf', 'application/msword', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    if (!allowedTypes.includes(file.type)) {
        Message.error('仅支持 PDF、DOC、DOCX 格式的文件')
        return
    }

    isProcessing.value = true

    try {
        let extractedText = ''

        if (file.type === 'application/pdf') {
            extractedText = await parsePDF(file)
        } else if (file.type === 'application/msword' || file.type === 'application/vnd.openxmlformats-officedocument.wordprocessingml.document') {
            extractedText = await parseWord(file)
        }

        if (extractedText) {
            await parseResumeText(extractedText)
            Message.success('文档解析成功，已自动填入简历信息')
        } else {
            Message.warning('未能从文档中提取到有效内容')
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

// 解析PDF文件（使用PDF.js）
const parsePDF = async (file: File): Promise<string> => {
    try {
        const arrayBuffer = await file.arrayBuffer()
        const pdf = await getDocument({
            data: arrayBuffer,
            // 添加更多配置以提高兼容性
            disableWorker: false,     // 尝试使用worker
            disableStream: true       // 禁用流式处理以提高稳定性
        }).promise

        let fullText = ''
        const numPages = pdf.numPages

        // 逐页提取文本
        for (let pageNum = 1; pageNum <= numPages; pageNum++) {
            const page = await pdf.getPage(pageNum)
            const textContent = await page.getTextContent()
            const pageText = textContent.items
                .map((item: any) => item.str)
                .join(' ')
            fullText += pageText + '\n'
        }
        return fullText.trim()
    } catch (error) {
        console.error('PDF解析失败:', error)

        // 尝试使用不同的配置重新解析
        try {
            // 使用不依赖worker的配置重新尝试
            const arrayBuffer = await file.arrayBuffer()
            const pdf = await getDocument({
                data: arrayBuffer,
                disableWorker: true,  // 禁用worker，使用主线程处理
                disableStream: true   // 禁用流式处理
            }).promise

            let fullText = ''
            const numPages = pdf.numPages

            // 逐页提取文本
            for (let pageNum = 1; pageNum <= numPages; pageNum++) {
                const page = await pdf.getPage(pageNum)
                const textContent = await page.getTextContent()
                const pageText = textContent.items
                    .map((item: any) => item.str)
                    .join(' ')
                fullText += pageText + '\n'
            }
            console.warn('PDF解析使用降级方案成功')
            return fullText.trim()
        } catch (fallbackError) {
            console.error('PDF解析降级方案也失败了:', fallbackError)

            // 最后的降级方案：尝试使用不同的PDF.js版本
            try {
                // 如果上面的方法都失败了，尝试使用更简单的文本提取方法
                const arrayBuffer = await file.arrayBuffer()
                const pdf = await getDocument({
                    data: arrayBuffer,
                    disableWorker: true,
                    disableStream: true,
                    disableAutoFetch: true  // 禁用自动获取
                }).promise

                let fullText = ''
                const numPages = Math.min(pdf.numPages, 10) // 限制页数以提高性能

                // 逐页提取文本
                for (let pageNum = 1; pageNum <= numPages; pageNum++) {
                    const page = await pdf.getPage(pageNum)
                    const textContent = await page.getTextContent()
                    const pageText = textContent.items
                        .map((item: any) => item.str)
                        .join(' ')
                    fullText += pageText + '\n'
                }
                console.warn('PDF解析使用最终降级方案成功')
                return fullText.trim()
            } catch (finalFallbackError) {
                console.error('PDF解析最终降级方案也失败了:', finalFallbackError)

                // 如果PDF解析失败，尝试从文件名提取信息
                const fileName = file.name.replace(/\.[^/.]+$/, '')
                const nameMatch = fileName.match(/[\u4e00-\u9fa5]{2,4}/)

                if (nameMatch) {
                    return `PDF解析失败，但从文件名检测到可能的姓名：${nameMatch[0]}\n请手动填写其他简历信息`
                }

                throw new Error('PDF文件解析失败，请检查文件格式或尝试转换为Word格式')
            }
        }
    }
}

// 解析Word文件
const parseWord = async (file: File): Promise<string> => {
    return new Promise((resolve, reject) => {
        const reader = new FileReader()
        reader.onload = () => {
            mammoth.extractRawText({ arrayBuffer: reader.result as ArrayBuffer })
                .then((result: any) => {
                    resolve(result.value)
                })
                .catch((error: any) => {
                    console.error('Word解析失败:', error)
                    reject(error)
                })
        }

        reader.onerror = () => reject(new Error('文件读取失败'))
        reader.readAsArrayBuffer(file)
    })
}

// 通用文档解析函数 - 适用于PDF和Word
const parseResumeText = async (text: string) => {

    // 特殊处理PDF解析失败的情况
    if (text.includes('PDF解析失败')) {
        Message.error('文档解析失败，请检查文件格式或手动填写简历信息')
        return
    }

    // 文本预处理：统一格式，去除多余空格和句号
    let processedText = text
        .replace(/\r\n/g, '\n')  // 统一换行符
        .replace(/\s{2,}/g, ' ')  // 多个空格替换为单个空格
        .replace(/([\uff1a:])/g, ':')  // 统一冒号格式
        .replace(/(\s*。\s*){2,}/g, '。')  // 多个连续句号替换为单个句号
        .replace(/^\s*。\s*$/gm, '')  // 移除只包含句号的行
        .replace(/(\s*。\s*){2,}/g, '。')  // 再次处理可能残留的多个句号
        .replace(/\s*。\s*$/gm, '')  // 移除行尾的句号和空格
    
    // 进一步清理：移除开头和结尾的多余句号
    processedText = processedText
        .replace(/^[\s。]+/g, '')  // 移除开头的空格和句号
        .replace(/[\s。]+$/g, '')  // 移除结尾的空格和句号
        .trim()

    // 添加调试信息：打印未处理的原文本
    console.log('=== 未处理的原文本 ===')
    console.log(text)
    console.log('=== 预处理后的文本 ===')
    console.log(processedText)
    console.log('=====================')

    // 添加更多调试信息
    const debugLines = processedText.split('\n');

    const extractedData: any = {}
    let extractedCount = 0

    // === 第一部分：提取有明确格式特征的基础信息 ===

    // 1. 姓名提取 - 只匹配常见格式
    if (!extractedData.name) {
        const namePatterns = [
            // 格式1: "姓名：张三" 或 "姓名 张三"
            /姓名[\s:：]+([\u4e00-\u9fa5]{2,4})(?=[\s\n]|$)/,
            // 格式2: "张三/男" 格式
            /([\u4e00-\u9fa5]{2,4})\s*[\/\|]\s*[\u7537\u5973]/,
            // 格式3: "姓名*张三" 格式（处理特殊符号）
            /姓名[\s*:：]*([\u4e00-\u9fa5]{2,4})(?=[\s\n]|$)/
        ]

        for (const pattern of namePatterns) {
            const match = processedText.match(pattern)
            if (match && match[1]) {
                const name = match[1].trim()
                // 验证是否为合理的姓名（2-4个中文字符）
                if (name.length >= 2 && name.length <= 4 && /^[\u4e00-\u9fa5]+$/.test(name)) {
                    extractedData.name = name
                    extractedCount++
                    break
                }
            }
        }
    }

    // 2. 手机号提取 - 多种格式支持
    if (!extractedData.phone) {
        const phonePatterns = [
            // 直接匹配11位手机号
            /1[3-9]\d{9}/,
            // 带标签的手机号
            /手机[\s:：]*(1[3-9]\d{9})/,
            /电话[\s:：]*(1[3-9]\d{9})/,
            /联系方式[\s:：]*(1[3-9]\d{9})/,
            // 带空格或连字符的手机号
            /1[3-9]\d[\s-]?\d{4}[\s-]?\d{4}/
        ]

        for (const pattern of phonePatterns) {
            const match = processedText.match(pattern)
            if (match) {
                let phone = match[1] || match[0]
                // 去除所有非数字字符，保留纯数字
                phone = phone.replace(/[^\d]/g, '')
                // 验证是否为11位手机号
                if (phone.length === 11 && /^1[3-9]\d{9}$/.test(phone)) {
                    extractedData.phone = phone
                    extractedCount++
                    break
                }
            }
        }
    }

    // 3. 邮箱提取 - 标准邮箱格式
    if (!extractedData.email) {
        const emailMatch = processedText.match(/[\w\.-]+@[\w\.-]+\.[a-zA-Z]{2,}/)
        if (emailMatch) {
            extractedData.email = emailMatch[0]
            extractedCount++
        }
    }

    // 4. 性别提取
    if (!extractedData.gender) {
        const genderPatterns = [
            /性别[\s:：]*([\u7537\u5973])/,
            /([\u7537\u5973])\s*[\/\|]/,
            /[\/\|]\s*([\u7537\u5973])/
        ]

        for (const pattern of genderPatterns) {
            const match = processedText.match(pattern)
            if (match && match[1]) {
                extractedData.gender = match[1]
                extractedCount++
                break
            }
        }
    }

    // 5. 出生日期提取 - 多种日期格式
    if (!extractedData.birthday) {
        const birthdayPatterns = [
            // 格式1: YYYY-MM-DD
            /(\d{4}[-\/]\d{1,2}[-\/]\d{1,2})/,
            // 格式2: YYYY.MM
            /(\d{4}\.\d{1,2})/,
            // 格式3: YYYY年MM月DD日
            /(\d{4}[年]\d{1,2}[月]\d{1,2}[日]?)/,
            // 格式4: 带标签的日期
            /出生[\s:：]*(\d{4}[-\/\.]年?月?\d{1,2}[-\/\.]月?日?\d{0,2})日?/,
            /生日[\s:：]*(\d{4}[-\/\.]年?月?\d{1,2}[-\/\.]月?日?\d{0,2})日?/,
            /出生日期[\s:：]*(\d{4}[-\/\.]年?月?\d{1,2}[-\/\.]月?日?\d{0,2})日?/
        ]

        for (const pattern of birthdayPatterns) {
            const match = processedText.match(pattern)
            if (match && match[1]) {
                let birthday = match[1]

                // 格式化为标准日期格式 YYYY-MM-DD
                if (birthday.includes('年') || birthday.includes('月')) {
                    // 中文日期格式转换
                    birthday = birthday.replace(/[年月]/g, '-').replace(/日/g, '')
                } else if (birthday.includes('.')) {
                    // 点号格式转换
                    const parts = birthday.split('.')
                    if (parts.length === 2) {
                        birthday = `${parts[0]}-${parts[1].padStart(2, '0')}-01`
                    }
                } else if (birthday.includes('/')) {
                    // 斜杠格式转换
                    birthday = birthday.replace(/\//g, '-')
                }

                // 确保日期格式正确
                if (/\d{4}-\d{1,2}(-\d{1,2})?/.test(birthday)) {
                    // 补全月份和日期的零
                    const dateParts = birthday.split('-')
                    if (dateParts.length === 2) {
                        birthday = `${dateParts[0]}-${dateParts[1].padStart(2, '0')}-01`
                    } else if (dateParts.length === 3) {
                        birthday = `${dateParts[0]}-${dateParts[1].padStart(2, '0')}-${dateParts[2].padStart(2, '0')}`
                    }

                    extractedData.birthday = birthday
                    extractedCount++
                    break
                }
            }
        }
    }

    // 6. 教育背景提取 - 学校+专业+学历格式
    if (!extractedData.education) {
        const educationPatterns = [
            // 格式1: "XX大学 XX专业 本科"
            /([\u4e00-\u9fa5]{2,8}大学)[\s,\uff0c]*([\u4e00-\u9fa5]{2,8}[\u5de5\u7a0b\u4e13\u4e1a\u7cfb\u5b66\u9662])[\s,\uff0c]*(本科|硕士|博士|学士)/,
            // 格式2: "教育背景：XX大学"
            /教育背景[\s:：]*([^。\n]+)/,
            // 格式3: "学历：XX" 或 "学位：XX"
            /(学历|学位)[\s:：]*([^。\n]+)/,
            // 格式4: 包含学校名称的行
            /([\u4e00-\u9fa5]{2,8}(大学|学院))[^。\n]*/,
            // 格式5: 包含学历信息的行
            /[^。\n]*(本科|硕士|博士|学士)[^。\n]*/
        ]

        for (const pattern of educationPatterns) {
            const match = processedText.match(pattern)
            if (match) {
                let educationText = '';
                if (match.length >= 4 && match[1] && match[2] && match[3]) {
                    // 完整格式：学校 专业 学历
                    educationText = `${match[1]} ${match[2]} ${match[3]}`
                } else if (match.length >= 2 && match[1]) {
                    // 简单格式：直接使用匹配内容
                    educationText = match[1]
                } else if (match[0]) {
                    // 使用整个匹配
                    educationText = match[0]
                }

                // 清理教育背景内容
                if (educationText) {
                    // 移除标题前缀
                    educationText = educationText.replace(/^(教育背景|学历|学位)[\s:：]*/i, '').trim()
                    // 只保留到句号或段落结束的部分
                    educationText = educationText.split('。')[0].trim()

                    // 进一步清理，确保只包含教育相关信息
                    if (educationText.length > 2) {
                        extractedData.education = educationText
                        extractedCount++
                        break
                    }
                }
            }
        }
    }

    // === 第二部分：通过标题格式提取其他内容 ===

    // 定义标题到字段的映射关系
    const titleMappings = {
        'skills': ['专业技能', '技能', '技术技能', '核心技能', '专业能力'],
        'workExperience': ['工作经验', '工作经历', '职业经历', '工作实践', '职场经历'],
        'projectExperience': ['项目经历', '项目经验', '项目描述', '项目实践', '项目参与'],
        'internshipExperience': ['实习经历', '实习经验', '实习情况', '实习表现'],
        'certificates': ['荣誉证书', '证书', '荣誉奖项', '竞赛奖项', '获奖情况', '奖励情况'],
        'selfEvaluation': ['自我评价', '个人评价', '自我介绍', '个人简介', '个人总结', '自我总结']
    }

    // 确保所有字段都初始化为空字符串，特别是项目经历字段
    for (const fieldName of Object.keys(titleMappings)) {
        if (!extractedData[fieldName]) {
            extractedData[fieldName] = '';
        }
    }

    // 按行分割文本进行标题匹配
    const lines = processedText.split('\n').map(line => line.trim()).filter(line => line.length > 0)


    // 遍历每一行，寻找标题格式
    for (let i = 0; i < lines.length; i++) {
        const line = lines[i]

        // 检查是否为标题行（以冒号结尾或包含标题关键词）
        for (const [fieldName, titles] of Object.entries(titleMappings)) {
            for (const title of titles) {
                // 匹配格式："标题：" 或 "标题 " 或单独的标题行
                if (line.includes(title + ':') || line.includes(title + '：') ||
                    line === title || (line.startsWith(title) && line.length <= title.length + 3)) {


                    // 提取该标题下的内容
                    const content = extractContentUnderTitle(lines, i, title)
                    if (content && content.trim().length > 0) {
                        extractedData[fieldName] = content.trim()
                        extractedCount++
                    }
                    break
                }
            }
        }
    }

    // === 第二部分半：特殊规则处理 ===

    // 如果没有通过标题提取到专业技能，则使用关键词规则提取
    if (!extractedData.skills) {
        // 查找包含"熟悉"、"熟练"、"了解"、"掌握"的内容作为专业技能
        const skillsLines: string[] = []

        lines.forEach(line => {
            // 使用更精确的正则表达式匹配技能相关的内容
            // 匹配"熟悉"、"熟练"、"了解"、"掌握"前后到句号的内容
            const skillPatterns = [
                /([^。]*熟悉[^。]*)/g,
                /([^。]*熟练[^。]*)/g,
                /([^。]*了解[^。]*)/g,
                /([^。]*掌握[^。]*)/g
            ];

            for (const pattern of skillPatterns) {
                const skillMatches = line.match(pattern);
                if (skillMatches && skillMatches.length > 0) {
                    skillsLines.push(...skillMatches);
                }
            }
        });

        if (skillsLines.length > 0) {
            // 去重并限制数量，避免过多重复内容
            const uniqueSkills = Array.from(new Set(skillsLines)).slice(0, 10);
            extractedData.skills = uniqueSkills.join('\n');
            extractedCount++;
        }
    }

    // 如果没有通过标题提取到荣誉证书，则使用关键词规则提取
    if (!extractedData.certificates) {
        // 查找包含"奖"字的内容作为荣誉证书
        const certificateLines: string[] = [];

        lines.forEach(line => {
            // 使用更精确的正则表达式匹配奖项相关的内容
            // 匹配包含"奖"字的短语，前后到句号为止
            const awardMatches = line.match(/([^。]*奖[^。]*)/g);
            if (awardMatches && awardMatches.length > 0) {
                certificateLines.push(...awardMatches);
            }
        });

        if (certificateLines.length > 0) {
            // 去重并限制数量，避免过多重复内容
            const uniqueCertificates = Array.from(new Set(certificateLines)).slice(0, 10);
            extractedData.certificates = uniqueCertificates.join('\n');
            extractedCount++;
        }
    }

    // 辅助函数：提取标题下的内容
    function extractContentUnderTitle(lines: string[], titleIndex: number, title: string): string {
        let content = ''
        let currentLine = lines[titleIndex]

        // 如果标题行本身包含内容（如"专业技能：Java, Python"），先提取这部分
        const titleWithContent = currentLine.replace(new RegExp(`${title}[\\s:：]*`), '')
        if (titleWithContent.trim().length > 0) {
            // 只保留到句号为止的内容
            content += titleWithContent.split('。')[0].trim() + '\n'
        }

        // 继续读取后续行，直到遇到下一个标题或文档结束
        for (let j = titleIndex + 1; j < lines.length; j++) {
            const nextLine = lines[j]

            // 检查是否遇到了新的标题
            let isNewTitle = false
            for (const titles of Object.values(titleMappings)) {
                for (const t of titles) {
                    if (nextLine.includes(t + ':') || nextLine.includes(t + '：') ||
                        nextLine === t || (nextLine.startsWith(t) && nextLine.length <= t.length + 3)) {
                        isNewTitle = true
                        break
                    }
                }
                if (isNewTitle) break
            }

            // 如果遇到新标题，停止提取
            if (isNewTitle) break

            // 添加内容行，但只保留到句号为止
            if (nextLine.trim().length > 0) {
                content += nextLine.split('。')[0] + '\n'
            }

            // 如果当前行包含句号，停止提取
            if (nextLine.includes('。')) {
                break
            }
        }

        return content
    }

    // === 第三部分：填入表单 ===

    let filledCount = 0
    // 注意：extractedCount 已经在前面声明并使用了

    // 收集所有已匹配到的内容，用于后续排除
    const matchedContent: string[] = []
    Object.values(extractedData).forEach(content => {
        if (typeof content === 'string' && content.trim()) {
            // 将内容按行分割并添加到匹配内容数组中
            content.split('\n').forEach(line => {
                if (line.trim()) {
                    matchedContent.push(line.trim())
                }
            })
        }
    })

    // 根据用户需求：对未处理的原文本，剔除这些匹配后填入的文本后直接塞到项目经历
    // 从原始文本中移除已匹配的内容，剩余部分放入项目经历
    let remainingText = processedText;
    
    // 创建一个副本用于匹配检查，避免修改原始文本
    let textToCheck = processedText;
    
    // 按长度排序，优先移除较长的匹配片段，避免部分移除
    const sortedMatchedContent = [...matchedContent].sort((a, b) => b.length - a.length);
    
    // 移除已匹配的内容
    sortedMatchedContent.forEach(content => {
        // 只移除长度大于3的片段，避免移除过短的通用词汇
        if (content.length > 3) {
            // 转义特殊字符
            const escapedContent = content.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
            const regex = new RegExp(escapedContent, 'g');
            textToCheck = textToCheck.replace(regex, '');
        }
    });
    
    // 清理文本：移除多余的空白行和空格
    remainingText = textToCheck
        .replace(/\n{3,}/g, '\n\n')  // 多个换行符合并为两个
        .replace(/^\s+|\s+$/gm, '')   // 移除每行首尾空格
        .replace(/\n\s*\n/g, '\n\n')  // 移除空行
        .trim();
    
    // 在填入项目经历前，进一步清理多余的句号
    remainingText = remainingText
        .replace(/(\s*。\s*){2,}/g, '。')  // 多个连续句号替换为单个句号
        .replace(/^\s*。\s*$/gm, '')  // 移除只包含句号的行
        .replace(/(\s*。\s*){2,}/g, '。')  // 再次处理可能残留的多个句号
        .replace(/\s*。\s*$/gm, '')  // 移除行尾的句号和空格
        .replace(/^[\s。]+/g, '')  // 移除开头的空格和句号
        .replace(/[\s。]+$/g, '')  // 移除结尾的空格和句号
        .trim();
    
    // 如果有剩余文本，放入项目经历字段
    if (remainingText.length > 0) {
        // 如果原来有项目经历内容，追加未匹配的内容
        if (extractedData.projectExperience && extractedData.projectExperience.trim()) {
            extractedData.projectExperience = extractedData.projectExperience.trim() + '\n\n' + remainingText;
        } else {
            // 否则直接使用未匹配的内容
            extractedData.projectExperience = remainingText;
        }
        extractedCount++;
    } else if (!extractedData.projectExperience) {
        // 确保项目经历字段存在
        extractedData.projectExperience = '';
    }

    /*
    // 删除旧的处理逻辑
    // 收集未被匹配到的文本行 - 简化逻辑，直接收集所有未匹配的内容
    const unmatchedLines: string[] = []

    // 根据用户需求：对上传的文本，除去前面正则匹配到的文本，剩下的直接全部丢到项目经历中
    // 收集所有未匹配的文本行
    lines.forEach(line => {
        // 跳过空行
        if (!line.trim()) return

        // 检查这一行是否已经被匹配到某个字段中
        let isMatched = false
        for (const content of matchedContent) {
            // 使用更宽松的匹配条件
            if (content.includes(line) || line.includes(content) ||
                (line.length > 5 && content.includes(line.substring(0, Math.min(5, line.length))))) {
                isMatched = true
                break
            }
        }

        // 排除已匹配的内容
        if (!isMatched) {
            unmatchedLines.push(line)
        }
    })

    // 将所有未匹配的内容直接放入项目经历字段
    if (unmatchedLines.length > 0) {
        // 合并未匹配的行，添加适当的换行符
        let unmatchedText = unmatchedLines.join('\n')

        // 清理文本，移除多余的空白行
        unmatchedText = unmatchedText.replace(/\n{3,}/g, '\n\n').trim()

        // 如果原来有项目经历内容，追加未匹配的内容
        if (extractedData.projectExperience && extractedData.projectExperience.trim()) {
            extractedData.projectExperience = extractedData.projectExperience.trim() + '\n\n' + unmatchedText
        } else {
            // 否则直接使用未匹配的内容
            extractedData.projectExperience = unmatchedText
        }
        // 确保计数增加
        extractedCount++
    } else {
        // 如果没有未匹配的内容，但项目经历字段为空，则创建一个空字符串以确保字段存在
        if (!extractedData.projectExperience) {
            extractedData.projectExperience = ''
        }
    }
    */

    // 处理填入表单的逻辑，确保所有字段都能被填入
    // 注意：这里不再重复声明filledCount，使用之前已声明的变量
    Object.keys(extractedData).forEach(key => {
        if (extractedData[key] && (resumeForm as any)[key] !== undefined) {
            // 只填入空字段，保留用户已填写的内容
            if (!(resumeForm as any)[key] || (resumeForm as any)[key].trim() === '') {
                (resumeForm as any)[key] = extractedData[key];
                filledCount++;
            } else if (key === 'projectExperience' && extractedData[key]) {
                // 特殊处理项目经历：即使字段已有内容，也将解析出的内容追加到现有内容中
                (resumeForm as any)[key] = (resumeForm as any)[key].trim() + '\n\n' + extractedData[key];
                filledCount++;
            }
        }
    });

    // 如果没有提取到姓名，尝试从文件名获取
    if (!extractedData.name && fileInput.value?.files?.[0]) {
        const fileName = fileInput.value.files[0].name.replace(/\.(pdf|doc|docx)$/i, '')
        if (fileName.length <= 10 && /[\u4e00-\u9fa5]/.test(fileName)) {
            if (!resumeForm.name || resumeForm.name.trim() === '') {
                resumeForm.name = fileName
                filledCount++
            }
        }
    }

    // 调试信息
    console.log('解析结果:', { extractedData, extractedCount, filledCount });
    console.log('项目经历内容:', extractedData.projectExperience);

    // 显示提取结果
    if (filledCount > 0) {
        Message.success(`成功提取并填入 ${filledCount} 个字段的信息`)
    } else if (extractedCount > 0) {
        Message.info('已提取到信息，但相关字段已有内容，未覆盖')
    } else {
        Message.warning('未能从文档中提取到有效信息，请手动填写')
    }
}

// 本地存储键名
const LOCAL_STORAGE_KEY = 'my_resume_data'

// 保存到本地存储
const saveToLocalStorage = () => {
    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(resumeForm))
}

// 从本地存储加载
const loadFromLocalStorage = () => {
    const saved = localStorage.getItem(LOCAL_STORAGE_KEY)
    if (saved) {
        try {
            const data = JSON.parse(saved)
            Object.assign(resumeForm, data)
        } catch (error) {
            console.error('加载本地简历数据失败:', error)
        }
    }
}

// 清空本地存储
const clearLocalStorage = () => {
    localStorage.removeItem(LOCAL_STORAGE_KEY)
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
        if (storageMode.value === 'local') {
            saveToLocalStorage()
            Message.success('简历已保存到本地')
        } else {
            await saveToCloud()
        }
    } catch (error) {
        console.error('保存简历失败:', error)
        Message.error('保存失败，请重试')
    } finally {
        isSaving.value = false
    }
}

// 清空简历
const clearResume = async () => {
    const confirmMessage = storageMode.value === 'local'
        ? '确定要清空所有简历数据吗？此操作不可恢复。'
        : '确定要清空表单中的简历数据吗？这不会删除云端已保存的数据。'

    const confirmed = await Confirm.show({
        title: '清空简历',
        message: confirmMessage,
        type: 'warning',
        confirmText: '确定清空',
        cancelText: '取消'
    })

    if (confirmed) {
        Object.keys(resumeForm).forEach(key => {
            (resumeForm as any)[key] = ''
        })

        if (storageMode.value === 'local') {
            clearLocalStorage()
            Message.success('简历数据已清空')
        } else {
            Message.success('表单数据已清空')
        }
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
const copyToClipboard = async (text: string, fieldName: string) => {
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

// 监听存储模式变化
watch(storageMode, async (newMode) => {
    if (newMode === 'local') {
        loadFromLocalStorage()
    } else if (newMode === 'cloud' && userStore.currentUser) {
        await loadFromCloud()
    }
})

// 监听用户登录状态变化
watch(() => userStore.currentUser, async (user) => {
    if (user && storageMode.value === 'cloud') {
        await loadFromCloud()
    }
})

// 页面加载时初始化
onMounted(() => {
    if (storageMode.value === 'local') {
        loadFromLocalStorage()
    } else if (userStore.currentUser) {
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
    if (storageMode.value === 'local') {
        loadFromLocalStorage()
    } else if (userStore.currentUser) {
        loadFromCloud()
    }
})

// 组件失活时保存数据
onDeactivated(() => {
    // 可以在这里添加清理逻辑
    // 例如保存当前表单数据
    if (storageMode.value === 'local' && hasResumeData.value) {
        saveToLocalStorage()
    }
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
</style>
