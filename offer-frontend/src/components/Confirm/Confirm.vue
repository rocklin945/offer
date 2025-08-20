<template>
  <transition name="modal-fade" appear>
    <div class="fixed inset-0 z-50 overflow-y-auto">
      <!-- 背景遮罩 -->
      <div class="fixed inset-0 bg-black bg-opacity-50 transition-opacity" @click="handleCancel"></div>
      
      <!-- 对话框 -->
      <div class="flex min-h-full items-center justify-center p-4">
        <transition name="modal-scale" appear>
          <div class="relative bg-white rounded-lg shadow-xl max-w-md w-full mx-auto">
            <!-- 头部 -->
            <div class="px-6 py-4 border-b border-gray-200">
              <div class="flex items-center">
                <!-- 图标 -->
                <div class="flex-shrink-0 mr-3">
                  <svg v-if="type === 'warning'" class="w-6 h-6 text-yellow-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L3.732 16.5c-.77.833.192 2.5 1.732 2.5z" />
                  </svg>
                  <svg v-else-if="type === 'danger'" class="w-6 h-6 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L3.732 16.5c-.77.833.192 2.5 1.732 2.5z" />
                  </svg>
                  <svg v-else class="w-6 h-6 text-blue-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                </div>
                
                <!-- 标题 -->
                <h3 class="text-lg font-medium text-gray-900">
                  {{ title || defaultTitle }}
                </h3>
              </div>
            </div>
            
            <!-- 内容 -->
            <div class="px-6 py-4">
              <p class="text-sm text-gray-600">{{ message }}</p>
            </div>
            
            <!-- 按钮 -->
            <div class="px-6 py-4 bg-gray-50 rounded-b-lg flex justify-end space-x-3">
              <button @click="handleCancel" 
                class="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors">
                {{ cancelText || '取消' }}
              </button>
              <button @click="handleConfirm" 
                :class="confirmButtonClass"
                class="px-4 py-2 text-sm font-medium text-white rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 transition-colors">
                {{ confirmText || '确定' }}
              </button>
            </div>
          </div>
        </transition>
      </div>
    </div>
  </transition>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  title?: string
  message: string
  confirmText?: string
  cancelText?: string
  type?: 'warning' | 'danger' | 'info'
}

const props = withDefaults(defineProps<Props>(), {
  type: 'info'
})

const emit = defineEmits<{
  confirm: []
  cancel: []
}>()

const defaultTitle = computed(() => {
  switch (props.type) {
    case 'warning':
      return '警告'
    case 'danger':
      return '危险操作'
    default:
      return '确认'
  }
})

const confirmButtonClass = computed(() => {
  const baseClass = 'focus:ring-2 focus:ring-offset-2'
  
  switch (props.type) {
    case 'warning':
      return `${baseClass} bg-yellow-600 hover:bg-yellow-700 focus:ring-yellow-500`
    case 'danger':
      return `${baseClass} bg-red-600 hover:bg-red-700 focus:ring-red-500`
    default:
      return `${baseClass} bg-blue-600 hover:bg-blue-700 focus:ring-blue-500`
  }
})

const handleConfirm = () => {
  emit('confirm')
}

const handleCancel = () => {
  emit('cancel')
}
</script>

<style scoped>
/* 模态框动画 */
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s ease;
}

.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}

.modal-scale-enter-active,
.modal-scale-leave-active {
  transition: all 0.3s ease;
}

.modal-scale-enter-from,
.modal-scale-leave-to {
  opacity: 0;
  transform: scale(0.9);
}
</style>