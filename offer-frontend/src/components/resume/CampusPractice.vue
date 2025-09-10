<template>
    <div class="mt-8">
        <div class="flex justify-between items-center mb-4">
            <h3 class="text-lg font-medium text-gray-900">校园实践</h3>
            <button @click="$emit('add-campus-practice')" type="button" class="btn-secondary">
                + 添加校园实践
            </button>
        </div>
        <div v-for="(practice, index) in resumeForm.govCampusPractice" :key="index"
            class="border border-gray-200 rounded-md p-4 mb-4">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">开始时间</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="practice.startTime" type="text" placeholder="例如：2023.01"
                            class="input-field flex-1" />
                        <button @click="$emit('copy-to-clipboard', practice.startTime, '开始时间')" class="resume-copy-btn">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                </path>
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">结束时间</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="practice.endTime" type="text" placeholder="例如：2023.06"
                            class="input-field flex-1" />
                        <button @click="$emit('copy-to-clipboard', practice.endTime, '结束时间')" class="resume-copy-btn">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                </path>
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="md:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-1">实践描述</label>
                    <div class="flex items-center space-x-2">
                        <textarea v-model="practice.description" rows="3" placeholder="请输入实践描述"
                            class="input-field flex-1"></textarea>
                        <button @click="$emit('copy-to-clipboard', practice.description, '实践描述')"
                            class="resume-copy-btn self-start mt-1">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                </path>
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="md:col-span-2 flex justify-end">
                    <button @click="$emit('remove-campus-practice', index)" type="button"
                        class="px-3 py-1 bg-red-500 text-white rounded-md hover:bg-red-600">
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
    'add-campus-practice',
    'remove-campus-practice',
    'copy-to-clipboard'
])
</script>