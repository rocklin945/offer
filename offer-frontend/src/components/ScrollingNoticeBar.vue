<template>
    <div v-if="visible"
        class="bg-gradient-to-r from-red-500 to-orange-500 text-white py-2 px-4 relative overflow-hidden">
        <div class="animate-scroll whitespace-nowrap">
            <span class="mx-4">📣 紧急通知：</span>
            <span class="mx-4 font-bold">网站将于3天后进行价格调整！</span>
            <span class="mx-4 font-bold">现在购买会员可享受限时优惠价格！</span>
            <span class="mx-4 font-bold">立即行动，节省更多！</span>
            <span class="mx-4">⭐ 会员专享特权：无限制浏览所有职位、享受投递记录功能、笔试面试资料无限看、优先内推机会、简历优化建议、专属客服支持等</span>
        </div>
        <button @click="closeNotice"
            class="absolute right-5 top-1/2 transform -translate-y-1/2 text-white hover:text-gray-200 focus:outline-none">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
        </button>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

const visible = ref(true)

// 初始化显示状态，只根据管理员设置决定
const initVisibility = () => {
    // 检查管理员是否关闭了滚动提示条幅
    const adminDisabled = localStorage.getItem('adminNoticeBarEnabled')
    visible.value = adminDisabled !== 'false'
}

// 监听管理员控制切换事件
const handleNoticeBarToggled = (event: CustomEvent) => {
    visible.value = event.detail.enabled
}

// 关闭通知（仅临时隐藏，不保存状态）
const closeNotice = () => {
    visible.value = false
}

// 显示通知（仅临时显示，不保存状态）
const showNotice = () => {
    // 检查管理员是否允许显示
    const adminDisabled = localStorage.getItem('adminNoticeBarEnabled')
    if (adminDisabled === 'false') {
        return
    }

    visible.value = true
}

// 初始化组件
onMounted(() => {
    initVisibility()
    // 监听管理员控制切换事件
    window.addEventListener('noticeBarToggled', handleNoticeBarToggled as EventListener)
})

// 组件卸载时移除事件监听
onUnmounted(() => {
    window.removeEventListener('noticeBarToggled', handleNoticeBarToggled as EventListener)
})

// 暴露方法给父组件
defineExpose({
    showNotice,
    hideNotice: closeNotice,
    isVisible: visible
})
</script>

<style scoped>
@keyframes scroll {
    0% {
        transform: translateX(100%);
    }

    100% {
        transform: translateX(-100%);
    }
}

.animate-scroll {
    display: inline-block;
    animation: scroll 30s linear infinite;
    white-space: nowrap;
}
</style>