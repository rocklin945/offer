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
      class="relative bg-white rounded-2xl shadow-2xl p-6 sm:p-8 w-full max-w-md mx-auto transform transition-all duration-300 ease-out"
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
      <div>
        <h3 class="text-xl sm:text-2xl font-bold text-gray-900 mb-1">兑换会员</h3>
        <p class="text-gray-600 mb-4">使用佣金兑换不同天数的会员</p>

        <!-- 加载状态 -->
        <div v-if="loading" class="flex justify-center items-center h-32">
          <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
        </div>

        <!-- 错误状态 -->
        <div v-else-if="error" class="bg-red-50 rounded-xl p-4 mb-4">
          <div class="text-red-800 font-semibold">加载失败</div>
          <div class="text-red-600 text-sm mt-1">{{ error }}</div>
          <button @click="fetchPlans" class="mt-2 text-sm text-blue-600 hover:text-blue-800">
            重新加载
          </button>
        </div>

        <!-- 正常内容 -->
        <div v-else>
          <!-- 可用佣金 -->
          <div class="bg-blue-50 rounded-xl p-3 sm:p-4 mb-4 sm:mb-6">
            <div class="text-blue-800 font-semibold">可用佣金：¥{{ (availableCommission ?? 0).toFixed(2) }}</div>
            <div class="text-blue-600 text-xs sm:text-sm">选择对应价位进行兑换</div>
          </div>

          <!-- 方案选择 -->
          <div class="grid grid-cols-2 gap-3 sm:gap-4 mb-4 sm:mb-6">
            <button
              v-for="(plan, idx) in plans"
              :key="plan.id"
              type="button"
              @click="selectedIndex = idx"
              class="border rounded-xl p-4 text-left transition-all duration-200"
              :class="selectedIndex === idx ? 'border-blue-500 ring-2 ring-blue-200' : 'border-gray-200 hover:border-gray-300'"
            >
              <div class="text-gray-900 font-bold text-lg">¥{{ plan.price }}</div>
              <div class="text-gray-600 text-sm mt-1">{{ plan.label }} · {{ plan.days }}天</div>
            </button>
          </div>

          <!-- 提示 -->
          <div class="text-xs text-gray-500 mb-4">
            说明：兑换成功后会员天数实时生效，无法退还，请确认后操作。
          </div>

          <!-- 操作按钮 -->
          <div class="flex space-x-3">
            <button
              @click="handleClose"
              class="flex-1 py-3 rounded-xl bg-gray-100 hover:bg-gray-200 text-gray-700 font-semibold transition-all"
            >
              取消
            </button>
            <button
              @click="handleConfirm"
              class="flex-1 py-3 rounded-xl bg-blue-600 hover:bg-blue-700 text-white font-semibold transition-all disabled:opacity-60 disabled:cursor-not-allowed"
              :disabled="!currentPlan || (availableCommission ?? 0) < currentPlan.price"
            >
              立即兑换
            </button>
          </div>

          <!-- 余额不足提示 -->
          <p v-if="currentPlan && (availableCommission ?? 0) < currentPlan.price" class="text-red-500 text-xs mt-2">
            佣金不足，无法兑换该套餐
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, nextTick, onMounted } from 'vue'
import { Message } from '@/components/Message'
import { membershipPlanApi } from '@/api/membershipPlan'

export interface PlanOption {
  id: number
  price: number
  days: number
  label: string
}

const props = defineProps<{
  availableCommission?: number
}>()

const emit = defineEmits<{
  close: []
  confirm: [payload: PlanOption]
}>()

const isVisible = ref(false)
const selectedIndex = ref(0)
const plans = ref<PlanOption[]>([])
const loading = ref(true)
const error = ref<string | null>(null)

const currentPlan = computed(() => plans.value[selectedIndex.value])

// 获取套餐信息
const fetchPlans = async () => {
  try {
    loading.value = true
    error.value = null
    const response = await membershipPlanApi.getAllActivePlans()
    if (response.statusCode === 200 && response.data) {
      plans.value = response.data.map((plan: any) => ({
        id: plan.id,
        price: plan.price,
        days: plan.days,
        label: plan.label
      }))
      // 默认选中第一个套餐
      if (plans.value.length > 0) {
        selectedIndex.value = 0
      }
    } else {
      error.value = '获取套餐信息失败'
    }
  } catch (err) {
    error.value = '网络错误，请稍后重试'
    console.error('获取套餐信息失败:', err)
  } finally {
    loading.value = false
  }
}

const handleClose = () => {
  isVisible.value = false
  setTimeout(() => emit('close'), 300)
}

const handleConfirm = () => {
  const plan = currentPlan.value
  if (!plan) return
  if ((props.availableCommission ?? 0) < plan.price) {
    Message.warning('佣金不足，无法兑换该套餐')
    return
  }
  // 向父组件抛出事件，交由父组件调用后端
  emit('confirm', plan)
  // 关闭弹窗
  handleClose()
}

onMounted(async () => {
  await nextTick()
  isVisible.value = true
  // 获取套餐信息
  await fetchPlans()
})
</script>

<style scoped>
/* 入场/出场过渡已经用类控制，这里保留媒介适配 */
@media (max-width: 640px) {
  .relative {
    margin-left: 1rem;
    margin-right: 1rem;
  }
}
</style>