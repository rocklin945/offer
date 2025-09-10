<template>
    <div class="mt-8">
        <div class="flex justify-between items-center mb-4">
            <h3 class="text-lg font-medium text-gray-900">计算机技能</h3>
            <button @click="$emit('add-computer-skill')" type="button" class="btn-secondary">
                + 添加计算机技能
            </button>
        </div>
        <div class="space-y-2">
            <div v-for="(skill, index) in resumeForm.bankComputerSkills" :key="index" class="flex items-center">
                <div class="flex items-center space-x-2 flex-1">
                    <input v-model="resumeForm.bankComputerSkills[index]" type="text" placeholder="请输入计算机技能"
                        class="input-field flex-1" />
                    <button @click.stop.prevent="$emit('copy-to-clipboard', skill, '计算机技能')"
                        class="text-blue-600 hover:text-blue-800" type="button">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                        </svg>
                    </button>
                </div>
                <button @click="$emit('remove-computer-skill', index)" type="button"
                    class="ml-2 px-3 py-1 bg-red-400 text-white rounded-md hover:bg-red-600">
                    删除
                </button>
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
    'add-computer-skill',
    'remove-computer-skill',
    'copy-to-clipboard'
])
</script>