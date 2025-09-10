<template>
    <div class="mt-8">
        <div class="flex justify-between items-center mb-4">
            <h3 class="text-lg font-medium text-gray-900">项目经历</h3>
            <button @click="$emit('add-project-experience')" type="button" class="btn-secondary">
                + 添加项目经历
            </button>
        </div>
        <div v-for="(project, index) in resumeForm.privateProjectExperience" :key="index"
            class="border border-gray-200 rounded-md p-4 mb-4">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">项目名称</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="project.projectName" type="text" placeholder="请输入项目名称"
                            class="input-field flex-1" />
                        <button @click="$emit('copy-to-clipboard', project.projectName, '项目名称')"
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
                    <label class="block text-sm font-medium text-gray-700 mb-1">担任角色</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="project.role" type="text" placeholder="请输入担任角色" class="input-field flex-1" />
                        <button @click="$emit('copy-to-clipboard', project.role, '担任角色')"
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
                    <label class="block text-sm font-medium text-gray-700 mb-1">项目时间</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="project.projectTime" type="text" placeholder="例如：2023.01-2023.06"
                            class="input-field flex-1" />
                        <button @click="$emit('copy-to-clipboard', project.projectTime, '项目时间')"
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
                    <label class="block text-sm font-medium text-gray-700 mb-1">项目链接</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="project.projectLink" type="text" placeholder="请输入项目链接"
                            class="input-field flex-1" />
                        <button @click="$emit('copy-to-clipboard', project.projectLink, '项目链接')"
                            class="text-blue-600 hover:text-blue-800">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                </path>
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="md:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-1">项目描述</label>
                    <div class="flex space-x-2">
                        <textarea v-model="project.description" rows="3" placeholder="请输入项目描述"
                            class="input-field flex-1"></textarea>
                        <button @click="$emit('copy-to-clipboard', project.description, '项目描述')"
                            class="text-blue-600 hover:text-blue-800 flex-shrink-0 self-start">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                </path>
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="md:col-span-2 flex justify-end">
                    <button @click="$emit('remove-project-experience', index)" type="button"
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
    'add-project-experience',
    'remove-project-experience',
    'copy-to-clipboard'
])
</script>