<template>
    <div class="mt-8">
        <div class="flex justify-between items-center mb-4">
            <h3 class="text-lg font-medium text-gray-900">专业技能</h3>
            <button @click="$emit('add-skill-level')" type="button" class="btn-secondary">
                + 添加专业技能
            </button>
        </div>
        <div v-for="(skill, index) in resumeForm.skillLevel" :key="index"
            class="border border-gray-200 rounded-md p-4 mb-4">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">技能类型</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="skill.skillType" type="text" placeholder="例如：编程语言" class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', skill.skillType, '技能类型')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                                </path>
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">掌握程度</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="skill.level" type="text" placeholder="例如：熟练" class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', skill.level, '掌握程度')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="md:col-span-2 flex justify-end">
                    <button @click="$emit('remove-skill-level', index)" type="button"
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
    'add-skill-level',
    'remove-skill-level',
    'copy-to-clipboard'
])
</script>