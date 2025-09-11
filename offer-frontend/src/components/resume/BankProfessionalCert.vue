<template>
    <div class="mt-8">
        <div class="flex justify-between items-center mb-4">
            <h3 class="text-lg font-medium text-gray-900">职业资格认证</h3>
            <button @click="$emit('add-bank-professional-cert')" type="button" class="btn-secondary">
                + 添加职业资格认证
            </button>
        </div>
        <div v-for="(cert, index) in resumeForm.bankProfessionalCert" :key="index"
            class="border border-gray-200 rounded-md p-4 mb-4">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">证书名称</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="cert.name" type="text" placeholder="请输入证书名称" class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', cert.name, '证书名称')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">颁发日期</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="cert.issueDate" type="text" placeholder="请输入颁发日期" class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', cert.issueDate, '颁发日期')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">颁发机构</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="cert.issuer" type="text" placeholder="请输入颁发机构" class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', cert.issuer, '颁发机构')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">成绩/分数</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="cert.score" type="text" placeholder="请输入成绩/分数" class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', cert.score, '成绩/分数')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="md:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-1">描述</label>
                    <div class="flex items-center space-x-2">
                        <textarea v-model="cert.description" rows="3" placeholder="请输入描述"
                            class="input-field flex-1"></textarea>
                        <button @click.stop.prevent="$emit('copy-to-clipboard', cert.description, '描述')"
                            class="text-blue-600 hover:text-blue-800 self-start mt-2" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="md:col-span-2 flex justify-end">
                    <button @click="$emit('remove-bank-professional-cert', index)" type="button"
                        class="px-3 py-1 bg-red-400 text-white rounded-md hover:bg-red-600">
                        删除
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
    }
})

defineEmits([
    'add-bank-professional-cert',
    'remove-bank-professional-cert',
    'copy-to-clipboard'
])
</script>