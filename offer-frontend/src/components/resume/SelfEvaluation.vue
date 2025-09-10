<template>
    <div class="mt-8">
        <h3 class="text-lg font-medium text-gray-900">自我评价</h3>
        <div class="mt-4">
            <label class="block text-sm font-medium text-gray-700 mb-1">自我评价</label>
            <div class="flex space-x-2">
                <textarea v-model="resumeForm.selfEvaluation" rows="5" placeholder="请输入自我评价"
                    class="input-field flex-1"></textarea>
                <button @click="$emit('copy-to-clipboard', resumeForm.selfEvaluation, '自我评价')"
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
</template>

<script setup>
defineProps({
    resumeForm: {
        type: Object,
        required: true
    }
})

defineEmits(['copy-to-clipboard'])
</script>