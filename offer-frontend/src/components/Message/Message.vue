<template>
  <transition name="message-fade" appear @after-leave="$emit('destroy')">
    <div v-if="visible" class="message-container">
      <div :class="messageClass">
        <span :class="badgeClass">{{ message }}</span>
      </div>
    </div>
  </transition>
</template>

<script setup lang="ts">
import { computed, ref, onMounted } from 'vue'

interface Props {
  message: string
  type?: 'success' | 'warning' | 'error' | 'info'
  duration?: number
  id?: string
}

const props = withDefaults(defineProps<Props>(), {
  type: 'info',
  duration: 3000
})

const emit = defineEmits<{
  close: []
  destroy: []
}>()

const visible = ref(true)

const messageClass = computed(() => {
  return 'message-box'
})

const badgeClass = computed(() => {
  const baseClass = 'inline-flex px-3 py-1 text-xs font-semibold rounded-full'
  
  const typeClasses = {
    success: 'bg-green-100 text-green-800',
    warning: 'bg-yellow-100 text-yellow-800', 
    error: 'bg-red-100 text-red-800',
    info: 'bg-blue-100 text-blue-800'
  }
  
  return `${baseClass} ${typeClasses[props.type]}`
})

const close = () => {
  visible.value = false
  emit('close')
}

onMounted(() => {
  if (props.duration > 0) {
    setTimeout(() => {
      close()
    }, props.duration)
  }
})
</script>

<style scoped>
.message-container {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 9999;
}

.message-box {
  pointer-events: auto;
}

/* 动画效果 */
.message-fade-enter-active,
.message-fade-leave-active {
  transition: all 0.3s ease;
}

.message-fade-enter-from {
  opacity: 0;
  transform: translateX(-50%) translateY(-20px);
}

.message-fade-leave-to {
  opacity: 0;
  transform: translateX(-50%) translateY(-20px);
}

.message-fade-enter-to,
.message-fade-leave-from {
  opacity: 1;
  transform: translateX(-50%) translateY(0);
}
</style>
