<template>
    <teleport to="body">
        <div class="modal-backdrop fixed inset-0 bg-black bg-opacity-50" style="z-index: 99999;"></div>
        <div class="modal-container fixed inset-0 flex items-center justify-center" style="z-index: 100000;"
            @click.self="close">
            <div
                class="bg-gradient-to-br from-yellow-50 to-amber-50 rounded-2xl w-full max-w-md p-8 text-center relative overflow-hidden shadow-xl">
                <!-- 彩带动画 -->
                <div class="confetti-container absolute inset-0 pointer-events-none">
                    <div v-for="i in 150" :key="i" class="confetti-piece" :style="getConfettiStyle(i)"></div>
                </div>

                <!-- 金黄色打钩图标容器 -->
                <div class="relative w-24 h-24 mx-auto mb-6">
                    <!-- 优化的金黄色圆圈背景 -->
                    <div
                        class="w-24 h-24 bg-gradient-to-r from-amber-400 to-yellow-400 rounded-full flex items-center justify-center mx-auto shadow-lg">
                        <!-- 打钩SVG图标 -->
                        <svg class="w-12 h-12 text-white checkmark" fill="none" stroke="currentColor"
                            viewBox="0 0 24 24">
                            <path class="checkmark-path" stroke-linecap="round" stroke-linejoin="round" stroke-width="3"
                                d="M5 13l4 4L19 7"></path>
                        </svg>
                    </div>
                </div>

                <h3 class="text-2xl font-bold text-amber-800 mb-2">恭喜成为会员！</h3>
                <p class="text-amber-700 mb-6">您已成功解锁所有会员特权</p>
                <button @click="close"
                    class="py-3 px-6 rounded-xl bg-gradient-to-r from-amber-500 to-yellow-400 text-white font-semibold hover:from-amber-600 hover:to-yellow-600 transition-all duration-300 transform hover:-translate-y-0.5 shadow-md hover:shadow-lg">
                    立即体验
                </button>
            </div>
        </div>
    </teleport>
</template>

<script setup lang="ts">
import { defineEmits, onMounted } from 'vue'

const emit = defineEmits<{
    (e: 'close'): void
}>()

const close = () => {
    emit('close')
}

// 生成彩带样式
const getConfettiStyle = (index: number) => {
    const colors = ['#f59e0b', '#f97316', '#fbbf24', '#fcd34d', '#fef3c7', '#ffd700']
    const color = colors[Math.floor(Math.random() * colors.length)]
    const left = `${Math.random() * 100}%`
    const animationDelay = `${Math.random() * 2}s`
    const animationDuration = `${Math.random() * 3 + 2}s`

    return {
        backgroundColor: color,
        left,
        animationDelay,
        animationDuration
    }
}

// 组件挂载时触发动画
onMounted(() => {
    // 可以在这里添加其他初始化逻辑
})
</script>

<style scoped>
.modal-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
}

.modal-container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
}

/* 打钩动画 */
.checkmark {
    animation: scaleCheck 0.5s ease-in-out 0.3s both;
}

.checkmark-path {
    stroke-dasharray: 40;
    stroke-dashoffset: 40;
    animation: drawCheck 0.5s ease-in-out 0.6s both;
}

@keyframes scaleCheck {
    0% {
        transform: scale(0);
    }

    50% {
        transform: scale(1.2);
    }

    100% {
        transform: scale(1);
    }
}

@keyframes drawCheck {
    to {
        stroke-dashoffset: 0;
    }
}

/* 彩带动画 */
.confetti-container {
    overflow: hidden;
}

.confetti-piece {
    position: absolute;
    width: 10px;
    height: 10px;
    background-color: #f59e0b;
    top: -10px;
    opacity: 0;
    animation: fall linear forwards;
}

@keyframes fall {
    0% {
        transform: translateY(0) rotate(0deg);
        opacity: 1;
    }

    100% {
        transform: translateY(100vh) rotate(360deg);
        opacity: 0;
    }
}
</style>