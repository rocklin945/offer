<template>
    <teleport to="body">
        <div class="modal-backdrop fixed inset-0 bg-black bg-opacity-50 transition-opacity duration-500 ease-out"
            :class="isVisible ? 'opacity-100' : 'opacity-0'" style="z-index: 99999;"></div>
        <div class="modal-container fixed inset-0 flex items-center justify-center p-4" style="z-index: 100000;"
            @click.self="close">
            <div class="bg-white rounded-2xl w-full max-w-md transform transition-all duration-500 ease-out"
                :class="isVisible ? 'scale-100 opacity-100 translate-y-0' : 'scale-95 opacity-0 -translate-y-4'">
                <div class="p-6">
                    <div class="flex justify-between items-center mb-4">
                        <h3 class="text-xl font-bold text-gray-900">
                            微信支付
                            <span class="ml-2 text-sm font-normal text-gray-500">
                                （推荐使用支付宝支付，立即开通）
                            </span>
                        </h3>
                        <button @click="close" class="text-gray-400 hover:text-gray-500">
                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M6 18L18 6M6 6l12 12"></path>
                            </svg>
                        </button>
                    </div>

                    <div class="text-center mb-6">
                        <div class="inline-block p-4 bg-gray-100 rounded-xl mb-4">
                            <div v-if="loading"
                                class="w-48 h-48 bg-gradient-to-br from-blue-100 to-blue-200 rounded-lg flex items-center justify-center border-2 border-dashed border-blue-300">
                                <div class="text-center">
                                    <div
                                        class="animate-spin w-8 h-8 border-4 border-blue-500 border-t-transparent rounded-full mx-auto mb-2">
                                    </div>
                                    <p class="text-blue-600 text-sm font-medium">加载中...</p>
                                </div>
                            </div>
                            <div v-else-if="imageUrl"
                                class="w-48 h-48 rounded-lg overflow-hidden border-2 border-blue-300">
                                <img :src="imageUrl" alt="微信支付二维码" class="w-full h-full object-cover"
                                    @error="onImageError" />
                            </div>
                        </div>
                        <p class="text-gray-600">微信扫码进群</p>
                        <p class="text-gray-600 mt-2">专业售后+就业咨询</p>
                    </div>

                    <div class="bg-blue-50 rounded-xl p-4">
                        <div class="flex items-start">
                            <svg class="w-5 h-5 text-blue-500 mt-0 mr-2 flex-shrink-0" fill="currentColor"
                                viewBox="0 0 20 20">
                                <path fill-rule="evenodd"
                                    d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z"
                                    clip-rule="evenodd" />
                            </svg>
                            <p class="text-blue-700 text-sm">添加微信群联系群主购买，群聊每天更新内推信息</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </teleport>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits, onMounted, nextTick } from 'vue'

const props = defineProps<{
    imageUrl: string | null,
    loading: boolean
}>()

const emit = defineEmits<{
    (e: 'close'): void
}>()

const isVisible = ref(false)

const close = () => {
    isVisible.value = false
    setTimeout(() => emit('close'), 300)
}

const onImageError = (event: Event) => {
    console.warn('二维码图片加载失败')
}

onMounted(async () => {
    // 给一点延迟确保DOM已经渲染
    await nextTick()
    setTimeout(() => {
        isVisible.value = true
    }, 10)
})
</script>