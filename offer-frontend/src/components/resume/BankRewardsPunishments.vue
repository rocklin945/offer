<template>
    <div class="mt-8">
        <div class="flex justify-between items-center mb-4">
            <h3 class="text-lg font-medium text-gray-900">奖惩情况</h3>
            <button @click="$emit('add-rewards-punishments')" type="button" class="btn-secondary">
                + 添加奖惩情况
            </button>
        </div>
        <div v-for="(item, index) in resumeForm.bankRewardsPunishments" :key="index"
            class="border border-gray-200 rounded-md p-4 mb-4">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">类型</label>
                    <div class="flex items-center space-x-2">
                        <select v-model="item.type" class="input-field flex-1">
                            <option value="奖励">奖励</option>
                            <option value="惩罚">惩罚</option>
                        </select>
                        <button @click.stop.prevent="$emit('copy-to-clipboard', item.type, '类型')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">级别</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="item.level" type="text" placeholder="请输入级别" class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', item.level, '级别')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">名称</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="item.name" type="text" placeholder="请输入名称" class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', item.name, '名称')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">日期</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="item.date" type="text" placeholder="请输入日期" class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', item.date, '日期')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">单位</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="item.unit" type="text" placeholder="请输入单位" class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', item.unit, '单位')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="md:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-1">原因</label>
                    <div class="flex items-center space-x-2">
                        <textarea v-model="item.reason" rows="3" placeholder="请输入原因"
                            class="input-field flex-1"></textarea>
                        <button @click.stop.prevent="$emit('copy-to-clipboard', item.reason, '原因')"
                            class="text-blue-600 hover:text-blue-800 self-start mt-2" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="md:col-span-2 flex justify-end">
                    <button @click="$emit('remove-rewards-punishments', index)" type="button"
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
    'add-rewards-punishments',
    'remove-rewards-punishments',
    'copy-to-clipboard'
])
</script>