<template>
    <div>
        <h3 class="text-lg font-medium text-gray-900">个人信息</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-4">
            <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">姓名 <span
                        class="text-red-500">*</span></label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.name" type="text" required placeholder="请输入您的姓名"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.name, '姓名')"
                        class="text-blue-600 hover:text-blue-800">
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
                    <input v-model="resumeForm.phone" type="tel" placeholder="请输入手机号" class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.phone, '手机号')"
                        class="text-blue-600 hover:text-blue-800">
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
                    <input v-model="resumeForm.wechat" type="text" placeholder="请输入微信号" class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.wechat, '微信号')"
                        class="text-blue-600 hover:text-blue-800">
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
                    <input v-model="resumeForm.email" type="email" placeholder="请输入邮箱" class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.email, '邮箱')"
                        class="text-blue-600 hover:text-blue-800">
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
                    <button @click="$emit('copy-to-clipboard', resumeForm.gender, '性别')"
                        class="text-blue-600 hover:text-blue-800">
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
                    <button @click="$emit('copy-to-clipboard', resumeForm.birthday, '出生日期')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">地址</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.address" type="text" placeholder="请输入您的地址" class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.address, '地址')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">证件号码</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.idNumber" type="text" placeholder="请输入证件号码" class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.idNumber, '证件号码')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>

            <!-- 根据简历类型显示特有字段 -->
            <!-- 央国企特有字段 -->
            <div v-if="resumeType === '央国企'">
                <label class="block text-sm font-medium text-gray-700 mb-1">现居住地</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.govCurrentResidence" type="text" placeholder="请输入现居住地"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.govCurrentResidence, '现居住地')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '央国企'">
                <label class="block text-sm font-medium text-gray-700 mb-1">户籍所在地</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.govRegisteredResidence" type="text" placeholder="请输入户籍所在地"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.govRegisteredResidence, '户籍所在地')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '央国企'">
                <label class="block text-sm font-medium text-gray-700 mb-1">政治面貌</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.govPoliticalStatus" type="text" placeholder="请输入政治面貌"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.govPoliticalStatus, '政治面貌')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '央国企'">
                <label class="block text-sm font-medium text-gray-700 mb-1">紧急联系人</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.govEmergencyName" type="text" placeholder="请输入紧急联系人"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.govEmergencyName, '紧急联系人')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '央国企'">
                <label class="block text-sm font-medium text-gray-700 mb-1">紧急联系人电话</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.govEmergencyPhone" type="tel" placeholder="请输入紧急联系人电话"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.govEmergencyPhone, '紧急联系人电话')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '央国企'">
                <label class="block text-sm font-medium text-gray-700 mb-1">紧急联系人关系</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.govEmergencyRelation" type="text" placeholder="请输入与紧急联系人关系"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.govEmergencyRelation, '紧急联系人关系')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>

            <div v-if="resumeType === '银行'">
                <label class="block text-sm font-medium text-gray-700 mb-1">政治面貌</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.bankPoliticalStatus" type="text" placeholder="请输入政治面貌"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.bankPoliticalStatus, '政治面貌')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '银行'">
                <label class="block text-sm font-medium text-gray-700 mb-1">身高(cm)</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.bankHeight" type="text" placeholder="请输入身高" class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.bankHeight, '身高')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '银行'">
                <label class="block text-sm font-medium text-gray-700 mb-1">体重(kg)</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.bankWeight" type="text" placeholder="请输入体重" class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.bankWeight, '体重')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '银行'">
                <label class="block text-sm font-medium text-gray-700 mb-1">健康状况</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.bankHealth" type="text" placeholder="请输入健康状况"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.bankHealth, '健康状况')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '银行'">
                <label class="block text-sm font-medium text-gray-700 mb-1">婚姻状况</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.bankMarriageStatus" type="text" placeholder="请输入婚姻状况"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.bankMarriageStatus, '婚姻状况')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '银行'">
                <label class="block text-sm font-medium text-gray-700 mb-1">生育状况</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.bankChildStatus" type="text" placeholder="请输入生育状况"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.bankChildStatus, '生育状况')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '银行'">
                <label class="block text-sm font-medium text-gray-700 mb-1">获取信息渠道</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.bankInfoChannel" type="text" placeholder="请输入获取信息渠道"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.bankInfoChannel, '获取信息渠道')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '银行'">
                <label class="block text-sm font-medium text-gray-700 mb-1">紧急联系人</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.bankEmergencyName" type="text" placeholder="请输入紧急联系人"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.bankEmergencyName, '紧急联系人')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '银行'">
                <label class="block text-sm font-medium text-gray-700 mb-1">紧急联系人电话</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.bankEmergencyPhone" type="tel" placeholder="请输入紧急联系人电话"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.bankEmergencyPhone, '紧急联系人电话')"
                        class="text-blue-600 hover:text-blue-800">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                            </path>
                        </svg>
                    </button>
                </div>
            </div>
            <div v-if="resumeType === '银行'">
                <label class="block text-sm font-medium text-gray-700 mb-1">紧急联系人关系</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.bankEmergencyRelation" type="text" placeholder="请输入与紧急联系人关系"
                        class="input-field flex-1" />
                    <button @click="$emit('copy-to-clipboard', resumeForm.bankEmergencyRelation, '紧急联系人关系')"
                        class="text-blue-600 hover:text-blue-800">
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
</template>

<script setup>
defineProps({
    resumeForm: {
        type: Object,
        required: true
    },
    resumeType: {
        type: String,
        required: true
    }
})

defineEmits(['copy-to-clipboard'])
</script>