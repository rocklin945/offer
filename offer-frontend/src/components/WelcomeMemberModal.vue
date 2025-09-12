<template>
    <teleport to="body">
        <div class="modal-backdrop fixed inset-0 bg-black bg-opacity-50" style="z-index: 99999;"></div>
        <div class="modal-container fixed inset-0 flex items-center justify-center" style="z-index: 100000;"
            @click.self="close">
            <div class="bg-white rounded-2xl w-full max-w-md p-8 text-center">
                <div
                    class="w-24 h-24 bg-gradient-to-r from-yellow-400 to-orange-500 rounded-full flex items-center justify-center mx-auto mb-6">
                    <svg class="w-12 h-12 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                    </svg>
                </div>
                <h3 class="text-2xl font-bold text-gray-900 mb-2">恭喜成为会员！</h3>
                <p class="text-gray-600 mb-6">您已成功解锁所有会员特权</p>
                <button @click="close"
                    class="py-3 px-6 rounded-xl bg-gradient-to-r from-blue-500 to-blue-600 text-white font-semibold">
                    立即体验
                </button>
            </div>
        </div>
    </teleport>
</template>

<script setup lang="ts">
import { defineEmits } from 'vue'

const emit = defineEmits<{
    (e: 'close'): void
}>()

const close = () => {
    emit('close')
}
</script>