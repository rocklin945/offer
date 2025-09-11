<template>
    <div class="mt-8">
        <div class="flex justify-between items-center mb-4">
            <h3 class="text-lg font-medium text-gray-900">论文</h3>
            <button @click="$emit('add-private-paper')" type="button" class="btn-secondary">
                + 添加论文
            </button>
        </div>
        <div v-for="(paper, index) in resumeForm.privatePaper" :key="index"
            class="border border-gray-200 rounded-md p-4 mb-4">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">论文名称</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="paper.name" type="text" placeholder="请输入论文名称" class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', paper.name, '论文名称')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">发表渠道</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="paper.publishChannel" type="text" placeholder="请输入发表渠道"
                            class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', paper.publishChannel, '发表渠道')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">作者顺序</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="paper.authorOrder" type="text" placeholder="请输入作者顺序"
                            class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', paper.authorOrder, '作者顺序')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">影响因子</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="paper.impactFactor" type="text" placeholder="请输入影响因子"
                            class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', paper.impactFactor, '影响因子')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="md:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-1">链接</label>
                    <div class="flex items-center space-x-2">
                        <input v-model="paper.link" type="text" placeholder="请输入论文链接" class="input-field flex-1" />
                        <button @click.stop.prevent="$emit('copy-to-clipboard', paper.link, '链接')"
                            class="text-blue-600 hover:text-blue-800" type="button">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                            </svg>
                        </button>
                    </div>
                </div>
                <div class="md:col-span-2 flex justify-end">
                    <button @click="$emit('remove-private-paper', index)" type="button"
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
    'add-private-paper',
    'remove-private-paper',
    'copy-to-clipboard'
])
</script>