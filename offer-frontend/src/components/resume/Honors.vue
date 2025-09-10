<template>
    <div class="mt-8">
        <div class="flex justify-between items-center mb-4">
            <h3 class="text-lg font-medium text-gray-900">荣誉奖项</h3>
            <button @click="$emit('add-honor')" type="button" class="btn-secondary">
                + 添加荣誉奖项
            </button>
        </div>
        <div class="flex flex-wrap gap-2">
            <div v-for="(honor, index) in resumeForm.honors" :key="index"
                class="flex items-center bg-blue-100 rounded-full px-3 py-1">
                <input v-model="resumeForm.honors[index]" type="text"
                    class="bg-transparent border-none focus:outline-none flex-1" />
                <button @click.stop.prevent="$emit('copy-to-clipboard', resumeForm.honors[index], '荣誉奖项')"
                    class="ml-2 text-blue-600 hover:text-blue-800" type="button">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                        </path>
                    </svg>
                </button>
                <button @click="$emit('remove-honor', index)" type="button"
                    class="ml-2 text-red-500 hover:text-red-700">
                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12">
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

defineEmits([
    'add-honor',
    'remove-honor',
    'copy-to-clipboard'
])
</script>