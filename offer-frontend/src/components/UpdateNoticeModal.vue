<template>
    <div class="fixed inset-0 z-50 flex items-center justify-center px-4">
        <!-- 遮罩层 -->
        <div class="absolute inset-0 bg-black transition-opacity duration-500"
            :class="isVisible ? 'bg-opacity-50' : 'bg-opacity-0'" @click="handleClose"></div>

        <!-- 弹窗主体 -->
        <div ref="modalRef" class="relative bg-white rounded-2xl shadow-2xl p-6 sm:p-8 max-w-sm sm:max-w-md mx-auto transform"
            :class="modalClasses">
            <!-- 关闭按钮 -->
            <button @click="handleClose"
                class="absolute top-1 right-2 sm:top-2 sm:right-3 w-6 h-6 sm:w-7 sm:h-7 bg-gray-100 hover:bg-gray-200 rounded-full flex items-center justify-center transition-all duration-200 hover:scale-110"
                aria-label="关闭弹窗">
                <svg class="w-3 h-3 sm:w-4 sm:h-4 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12">
                    </path>
                </svg>
            </button>

            <!-- 弹窗内容 -->
            <div class="text-center">
                <!-- 新用户注册赠送三天会员提示 -->
                <div v-if="showRegisterPromotion"
                    class="bg-gradient-to-r from-yellow-400 to-orange-500 rounded-xl p-4 mb-4 text-white">
                    <div class="flex items-center justify-center">
                        <svg class="w-6 h-6 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                        </svg>
                        <span class="font-bold text-lg">新用户注册即赠3天会员体验</span>
                        <svg class="w-6 h-6 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                        </svg>
                    </div>
                    <p class="mt-2 text-sm">立即注册享受尊贵会员特权</p>
                </div>

                <!-- 标题 -->
                <h3 class="text-lg sm:text-xl font-bold text-gray-900 mb-3 sm:mb-4 flex items-center justify-center">
                    <!-- 更新图标 -->
                    <svg class="w-5 h-5 sm:w-6 sm:h-6 mr-2 text-green-500" fill="none" stroke="currentColor"
                        viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15">
                        </path>
                    </svg>
                    <span>新版本更新内容</span>
                </h3>

                <!-- 提示文字 -->
                <p class="text-base sm:text-lg text-gray-700 mb-4 sm:mb-6 leading-relaxed">
                    我们为您带来了以下重要更新
                </p>

                <!-- 更新内容列表 -->
                <div class="mb-4 sm:mb-6 text-left">
                    <div class="bg-green-50 rounded-xl p-4 mb-4">
                        <div class="flex items-start">
                            <div class="flex-shrink-0 mt-1">
                                <div class="w-3 h-3 bg-green-500 rounded-full"></div>
                            </div>
                            <div class="ml-3">
                                <h4 class="font-semibold text-green-800">笔试面试资料库</h4>
                                <p class="text-green-600 text-sm mt-1">在线查看测评、央国企、银行、互联网大厂的内部资料</p>
                            </div>
                        </div>
                    </div>

                    <div class="bg-green-50 rounded-xl p-4">
                        <div class="flex items-start">
                            <div class="flex-shrink-0 mt-1">
                                <div class="w-3 h-3 bg-green-500 rounded-full"></div>
                            </div>
                            <div class="ml-3">
                                <h4 class="font-semibold text-green-800">内推企业合集</h4>
                                <p class="text-green-600 text-sm mt-1">一键查看所有内推企业，内推简历更先一步到达HR</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 确认按钮 -->
                <div class="mt-4 sm:mt-6">
                    <button @click="handleClose"
                        class="w-full bg-green-300 hover:bg-green-400 text-white font-semibold py-3 px-6 rounded-xl transition-all duration-200 transform hover:scale-105 shadow-lg hover:shadow-xl focus:ring-2 focus:ring-green-200 focus:outline-none">
                        我知道了
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick, computed } from 'vue'

// 定义props
interface Props {
    showRegisterPromotion?: boolean
}

const props = withDefaults(defineProps<Props>(), {
    showRegisterPromotion: true
})

// 定义事件
const emit = defineEmits<{
    close: []
}>()

const isVisible = ref(false)
const modalRef = ref<HTMLElement | null>(null)

// 计算属性，用于动态设置弹窗类
const modalClasses = computed(() => {
    return {
        'transition-all': true,
        'duration-500': true,
        'ease-out': true,
        'scale-100': isVisible.value,
        'opacity-100': isVisible.value,
        'translate-y-0': isVisible.value,
        'scale-95': !isVisible.value,
        'opacity-0': !isVisible.value,
        'translate-y-4': !isVisible.value
    }
})

// 处理关闭弹窗
const handleClose = () => {
    isVisible.value = false
    // 等待动画完成后关闭
    setTimeout(() => {
        emit('close')
    }, 500)
}

// 组件挂载时显示动画
onMounted(async () => {
    // 使用nextTick确保DOM渲染完成后再显示动画
    await nextTick()
    // 延迟一小段时间确保初始状态正确设置
    setTimeout(() => {
        isVisible.value = true
    }, 10)
})
</script>

<style scoped>
/* 移动端优化 */
@media (max-width: 640px) {
    .relative {
        margin-left: 1rem;
        margin-right: 1rem;
    }
}
</style>