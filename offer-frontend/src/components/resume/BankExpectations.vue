<template>
    <div class="mt-8">
        <h3 class="text-lg font-medium text-gray-900 mb-4">薪酬福利期望</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">期望薪酬</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.bankSalaryExpectation" type="text" placeholder="请输入期望薪酬"
                        class="input-field flex-1" />
                    <button @click.stop.prevent="$emit('copy-to-clipboard', resumeForm.bankSalaryExpectation, '期望薪酬')"
                        class="text-blue-600 hover:text-blue-800" type="button">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                        </svg>
                    </button>
                </div>
            </div>
            <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">期望工作地点</label>
                <div class="flex items-center space-x-2">
                    <input v-model="resumeForm.bankExpectedLocation" type="text" placeholder="请输入期望工作地点"
                        class="input-field flex-1" />
                    <button @click.stop.prevent="$emit('copy-to-clipboard', resumeForm.bankExpectedLocation, '期望工作地点')"
                        class="text-blue-600 hover:text-blue-800" type="button">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
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
    }
})

defineEmits([
    'copy-to-clipboard'
])
</script>