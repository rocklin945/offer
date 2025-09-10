<template>
    <div class="mt-8">
        <div class="flex justify-between items-center mb-4">
            <h3 class="text-lg font-medium text-gray-900">语言水平</h3>
            <button @click="$emit('add-language-level')" type="button" class="btn-secondary">
                + 添加语言水平
            </button>
        </div>
        <div v-for="(language, index) in resumeForm.languageLevel" :key="index"
            class="border border-gray-200 rounded-md p-4 mb-4">
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">语言</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="language.language" type="text" placeholder="例如：英语" class="input-field flex-1" />
                        <button @click="$emit('copy-to-clipboard', language.language, '语言')"
                            class="text-blue-600 hover:text-blue-800">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">水平</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="language.level" type="text" placeholder="例如：熟练" class="input-field flex-1" />
                        <button @click="$emit('copy-to-clipboard', language.level, '水平')"
                            class="text-blue-600 hover:text-blue-800">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">考试名称</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="language.exam" type="text" placeholder="例如：CET-6" class="input-field flex-1" />
                        <button @click="$emit('copy-to-clipboard', language.exam, '考试名称')"
                            class="text-blue-600 hover:text-blue-800">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">成绩</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="language.score" type="text" placeholder="请输入成绩" class="input-field flex-1" />
                        <button @click="$emit('copy-to-clipboard', language.score, '成绩')"
                            class="text-blue-600 hover:text-blue-800">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="lg:col-span-4 flex justify-end">
                    <button @click="$emit('remove-language-level', index)" type="button"
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
    'add-language-level',
    'remove-language-level',
    'copy-to-clipboard'
])
</script>