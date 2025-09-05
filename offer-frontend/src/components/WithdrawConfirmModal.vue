<template>
  <div class="fixed inset-0 z-50 flex items-center justify-center px-4">
    <!-- 遮罩 -->
    <div
      class="absolute inset-0 bg-black transition-opacity duration-300"
      :class="isVisible ? 'bg-opacity-50' : 'bg-opacity-0'"
      @click="handleClose"
    ></div>

    <!-- 弹窗主体 -->
    <div
      class="relative bg-white rounded-2xl shadow-2xl p-6 sm:p-8 max-w-sm sm:max-w-md w-full mx-auto transform transition-all duration-300 ease-out"
      :class="isVisible ? 'scale-100 opacity-100 translate-y-0' : 'scale-95 opacity-0 translate-y-4'"
    >
      <!-- 关闭按钮 -->
      <button
        @click="handleClose"
        class="absolute top-3 right-3 sm:top-4 sm:right-4 w-8 h-8 bg-gray-100 hover:bg-gray-200 rounded-full flex items-center justify-center transition-all duration-200 hover:scale-110"
        aria-label="关闭弹窗"
      >
        <svg class="w-4 h-4 sm:w-5 sm:h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
        </svg>
      </button>

      <!-- 内容 -->
      <div class="text-center">
        <h3 class="text-xl sm:text-2xl font-bold text-gray-900 mb-2">{{ title }}</h3>
        <p class="text-base sm:text-lg text-gray-700 mb-4 sm:mb-5 leading-relaxed">
          {{ message }}
        </p>

        <!-- 金额输入 -->
        <div class="mb-4 sm:mb-6">
          <label class="block text-left text-sm font-medium text-gray-700 mb-2">
            {{ amountLabel }}
          </label>
          <div class="relative">
            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <span class="text-gray-500 sm:text-sm">¥</span>
            </div>
            <input
              v-model="amount"
              @input="handleAmountInput"
              type="number"
              :placeholder="amountPlaceholder"
              class="block w-full pl-7 pr-12 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
              step="0.01"
              min="0"
            />
            <div class="absolute inset-y-0 right-0 flex items-center pr-3">
              <button
                @click="fillMaxAmount"
                class="px-2 py-1 text-xs bg-blue-100 text-blue-700 rounded hover:bg-blue-200 transition-colors"
              >
                全部
              </button>
            </div>
          </div>
          <div class="mt-2 text-sm text-gray-500 flex justify-between">
            <span>{{ maxAmountText }}: ¥{{ maxAmount.toFixed(2) }}</span>
            <span v-if="amount">当前金额: ¥{{ parseFloat(amount).toFixed(2) }}</span>
          </div>
        </div>

        <!-- 按钮 -->
        <div class="mt-6 sm:mt-8 flex flex-col sm:flex-row gap-3 sm:gap-4">
          <button
            @click="handleClose"
            class="flex-1 px-4 py-3 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors font-medium"
          >
            取消
          </button>
          <button
            @click="handleConfirm"
            :disabled="!isValidAmount"
            :class="[
              'flex-1 px-4 py-3 rounded-lg font-medium transition-all duration-200',
              isValidAmount
                ? 'bg-blue-600 hover:bg-blue-700 text-white shadow-lg hover:shadow-xl transform hover:scale-105'
                : 'bg-gray-200 text-gray-500 cursor-not-allowed'
            ]"
          >
            确认
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, nextTick, onMounted, onUnmounted } from 'vue'

const props = defineProps<{
    title: string
    message: string
    maxAmount: number
    actionType?: string // 新增属性，用于区分操作类型
}>()

const emit = defineEmits<{
    close: []
    confirm: [amount: number]
}>()

const isVisible = ref(false)
const amount = ref<string>('')

// 根据操作类型计算显示的文案
const amountLabel = computed(() => {
    if (props.actionType === 'confirm') {
        return '确认金额'
    } else if (props.actionType === 'reject') {
        return '拒绝金额'
    } else if (props.actionType === 'withdraw') {
        return '提现金额'
    }
    return '操作金额'
})

const amountPlaceholder = computed(() => {
    if (props.actionType === 'confirm') {
        return '请输入确认金额'
    } else if (props.actionType === 'reject') {
        return '请输入拒绝金额'
    } else if (props.actionType === 'withdraw') {
        return '请输入提现金额'
    }
    return '请输入操作金额'
})

const maxAmountText = computed(() => {
    if (props.actionType === 'confirm') {
        return '最大可确认金额'
    } else if (props.actionType === 'reject') {
        return '最大可拒绝金额'
    } else if (props.actionType === 'withdraw') {
        return '最大可提现金额'
    }
    return '最大可操作金额'
})

const isValidAmount = computed(() => {
    const numAmount = parseFloat(amount.value)
    return (
        !isNaN(numAmount) &&
        numAmount > 0 &&
        numAmount <= props.maxAmount
    )
})

const handleAmountInput = () => {
    const numAmount = parseFloat(amount.value)
    if (!isNaN(numAmount) && numAmount > props.maxAmount) {
        amount.value = props.maxAmount.toString()
    }
}

const fillMaxAmount = () => {
    amount.value = props.maxAmount.toString()
}

const handleClose = () => {
    isVisible.value = false
    // 延迟触发关闭事件，确保动画完成
    setTimeout(() => {
        emit('close')
    }, 300)
}

const handleConfirm = () => {
    const numAmount = parseFloat(amount.value)
    if (isValidAmount.value) {
        emit('confirm', numAmount)
        handleClose()
    }
}

// 清理函数
const cleanup = () => {
    // 重置状态
    isVisible.value = false
    amount.value = ''
}

onMounted(async () => {
    await nextTick()
    isVisible.value = true
})

// 组件卸载时清理状态
onUnmounted(() => {
    cleanup()
})
</script>

<style scoped>
/* 确保数字输入框在移动设备上显示正确的键盘 */
input[type="number"] {
  -moz-appearance: textfield;
}

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
</style>