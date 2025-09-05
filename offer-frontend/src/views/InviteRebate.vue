<template>
  <div class="invite-rebate-container" style="width: 100%; overflow-x: hidden;">
    <div class="min-h-screen bg-gray-50 py-8">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 space-y-6">
        <!-- 我的返佣 Header Card -->
        <div class="bg-white rounded-2xl shadow-lg p-6">
          <h1 class="text-2xl font-bold text-gray-900 mb-2">我的返佣</h1>
          <p class="text-gray-600">邀请好友注册使用，获取佣金收入</p>
        </div>

        <!-- Stats Cards -->
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          <!-- Card 1: 已注册用户数 -->
          <div class="bg-white rounded-2xl shadow-lg p-6 flex items-center space-x-4">
            <div class="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center">
              <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z">
                </path>
              </svg>
            </div>
            <div>
              <div class="text-3xl font-bold text-gray-900">{{ commissionData.invitedCount }}</div>
              <div class="text-gray-600">已注册用户数</div>
            </div>
          </div>
          <!-- Card 2: 确认中的佣金 -->
          <div class="bg-white rounded-2xl shadow-lg p-6 flex items-center space-x-4">
            <div class="w-12 h-12 bg-yellow-100 rounded-full flex items-center justify-center">
              <svg class="w-6 h-6 text-yellow-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z">
                </path>
              </svg>
            </div>
            <div>
              <div class="text-3xl font-bold text-gray-900">¥{{ commissionData.pendingCommission.toFixed(2) }}
              </div>
              <div class="text-gray-600">确认中的佣金</div>
            </div>
          </div>
          <!-- Card 3: 累计获得佣金 -->
          <div class="bg-white rounded-2xl shadow-lg p-6 flex items-center space-x-4">
            <div class="w-12 h-12 bg-green-100 rounded-full flex items-center justify-center">
              <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M3 10h18M3 14h18m-9-4v8m-7 0h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </div>
            <div>
              <div class="text-3xl font-bold text-gray-900">¥{{ commissionData.totalCommission.toFixed(2) }}
              </div>
              <div class="text-gray-600">累计获得佣金</div>
            </div>
          </div>
          <!-- Card 4: 佣金比例 -->
          <div class="bg-white rounded-2xl shadow-lg p-6 flex items-center space-x-4">
            <div class="w-12 h-12 bg-purple-100 rounded-full flex items-center justify-center">
              <svg class="w-6 h-6 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 110-2.684m0 2.684l6.632 3.316m-6.632-6.632l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.367 2.684 3 3 0 00-5.367-2.684z">
                </path>
              </svg>
            </div>
            <div>
              <div class="text-3xl font-bold text-gray-900">10%</div>
              <div class="text-gray-600">佣金比例</div>
            </div>
          </div>
        </div>

        <!-- 返佣规则 Section -->
        <div class="bg-white rounded-2xl shadow-lg p-6">
          <h2 class="text-2xl font-bold text-gray-900 mb-4">返佣规则</h2>
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            <!-- Rule 1: 分享 -->
            <div class="flex flex-col items-center text-center p-4">
              <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mb-3">
                <svg class="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M8.684 13.342C8.886 12.938 9 12.482 9 12c0-.482-.114-.938-.316-1.342m0 2.684a3 3 0 110-2.684m0 2.684l6.632 3.316m-6.632-6.632l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.367 2.684 3 3 0 00-5.367-2.684z">
                  </path>
                </svg>
              </div>
              <h3 class="text-lg font-semibold text-gray-900 mb-1">1. 分享</h3>
              <p class="text-gray-600 text-sm">分享邀请链接</p>
            </div>
            <!-- Rule 2: 注册 -->
            <div class="flex flex-col items-center text-center p-4">
              <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mb-3">
                <svg class="w-8 h-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z">
                  </path>
                </svg>
              </div>
              <h3 class="text-lg font-semibold text-gray-900 mb-1">2. 注册</h3>
              <p class="text-gray-600 text-sm">好友完成注册</p>
            </div>
            <!-- Rule 3: 购买 -->
            <div class="flex flex-col items-center text-center p-4">
              <div class="w-16 h-16 bg-red-100 rounded-full flex items-center justify-center mb-3">
                <svg class="w-8 h-8 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z">
                  </path>
                </svg>
              </div>
              <h3 class="text-lg font-semibold text-gray-900 mb-1">3. 购买</h3>
              <p class="text-gray-600 text-sm">好友购买套餐</p>
            </div>
            <!-- Rule 4: 返佣 -->
            <div class="flex flex-col items-center text-center p-4">
              <div class="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mb-3">
                <svg class="w-8 h-8 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z">
                  </path>
                </svg>
              </div>
              <h3 class="text-lg font-semibold text-gray-900 mb-1">4. 返佣</h3>
              <p class="text-gray-600 text-sm">获得10%返佣</p>
            </div>
          </div>
        </div>

        <!-- Invite Link Display -->
        <div class="bg-white rounded-2xl shadow-lg p-6">
          <h2 class="text-2xl font-bold text-gray-900 mb-4">我的邀请链接</h2>
          <div class="flex items-center justify-between bg-gray-50 rounded-lg p-4 mb-4">
            <div class="flex-1 mr-4">
              <input type="text" :value="inviteLink" readonly
                class="w-full bg-transparent border-none focus:outline-none text-gray-700 font-mono text-sm sm:text-base"
                ref="inviteLinkInput" />
            </div>
            <button @click="copyInviteLink"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors flex items-center relative overflow-hidden">
              <transition name="fade" mode="out-in">
                <span v-if="!copied" key="copy" class="flex items-center">
                  <svg class="w-5 h-5 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                    </path>
                  </svg>
                  复制链接
                </span>
                <span v-else key="copied" class="flex items-center">
                  <svg class="w-5 h-5 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                  </svg>
                  已复制
                </span>
              </transition>
            </button>
          </div>
          <p class="text-sm text-gray-500 text-center">
            邀请链接已生成，分享给好友即可获得返利
          </p>
        </div>

        <!-- 佣金余额 Section -->
        <div class="bg-white rounded-2xl shadow-lg p-6">
          <h2 class="text-2xl font-bold text-gray-900 mb-4">佣金余额</h2>
          <div class="flex flex-col sm:flex-row justify-between items-center">
            <div>
              <p class="text-gray-600">可用佣金</p>
              <p class="text-4xl font-bold text-gray-900">¥{{ commissionData.balanceCommission.toFixed(2) }}
              </p>
              <p class="text-sm text-gray-500 mt-2">邀请好友获得的佣金，可以直接划转到消费余额</p>
            </div>
            <div class="flex space-x-4 mt-4 sm:mt-0">
              <button @click="openRedeemModal"
                class="px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors flex items-center">
                <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4"></path>
                </svg>
                兑换会员
              </button>
              <button @click="openWithdrawModal"
                class="px-6 py-3 border border-yellow-300 text-yellow-800 bg-yellow-300 rounded-lg hover:bg-yellow-400 transition-colors flex items-center">
                <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M17 9V7a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2m2 4h10a2 2 0 002-2v-6a2 2 0 00-2-2H9a2 2 0 00-2 2v6a2 2 0 002 2zm7-5a2 2 0 11-4 0 2 2 0 014 0z">
                  </path>
                </svg>
                提现
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 弹窗：兑换会员 -->
    <RedeemMemberModal v-if="showRedeemModal" :available-commission="commissionData.balanceCommission"
      @close="showRedeemModal = false" @confirm="handleRedeemConfirm" />
    <!-- 弹窗：提现 -->
    <WithdrawCashModal v-if="showWithdrawModal" @close="showWithdrawModal = false" />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, reactive, onUnmounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { Message } from '@/components/Message'
import { getInviteCommission } from '@/api/invite'
import { inviteCommissionApi } from '@/api/inviteCommission'
import RedeemMemberModal from '@/components/RedeemMemberModal.vue'
import WithdrawCashModal from '@/components/WithdrawCashModal.vue'

const userStore = useUserStore()
const copied = ref(false)
const inviteLinkInput = ref<HTMLInputElement | null>(null)
const loading = ref(false)
const showRedeemModal = ref(false)
const showWithdrawModal = ref(false)

const openRedeemModal = () => {
  showRedeemModal.value = true
}
const openWithdrawModal = () => {
  showWithdrawModal.value = true
}

interface PlanOption {
  id: number
  price: number
  days: number
  label: string
}

const handleRedeemConfirm = async (plan: PlanOption) => {
  try {
    // 检查用户是否已登录
    if (!userStore.currentUser?.userId) {
      Message.warning('请先登录')
      return
    }

    // 调用后端兑换接口
    const response = await inviteCommissionApi.redeemMember(userStore.currentUser.userId, plan.id)
    if (response.data.statusCode === 200) {
      Message.success(`成功兑换${plan.label}（${plan.days}天）`)
      showRedeemModal.value = false
      // 重新加载佣金数据
      await loadCommissionData()
    } else {
      Message.error(response.data.message || '兑换失败')
    }
  } catch (error) {
    console.error('兑换失败:', error)
    Message.error('兑换失败，请稍后重试')
  }
}

// 佣金数据
const commissionData = reactive({
  invitedCount: 0,
  pendingCommission: 0,
  totalCommission: 0,
  balanceCommission: 0
})

// 生成邀请链接
const inviteLink = computed(() => {
  if (!userStore.currentUser) return ''

  // 获取用户名，去掉"用户_"前缀
  let username = userStore.currentUser.userName || ''
  if (username.startsWith('用户_')) {
    username = username.substring(3)
  }

  return `https://offer.rocklin.top/?code=${encodeURIComponent(username)}`
})

// 复制邀请链接
const copyInviteLink = () => {
  if (!inviteLinkInput.value) return

  inviteLinkInput.value.select()
  document.execCommand('copy')

  copied.value = true
  Message.success('邀请链接已复制！')
  setTimeout(() => {
    copied.value = false
  }, 2000)
}

// 加载佣金数据
const loadCommissionData = async () => {
  loading.value = true
  try {
    // 检查用户是否已登录
    if (!userStore.currentUser?.userId) {
      Message.warning('请先登录查看佣金数据')
      loading.value = false
      return
    }

    const response = await getInviteCommission(userStore.currentUser?.userId)
    if (response.data.statusCode === 200 && response.data.data) {
      const commission = response.data.data
      commissionData.invitedCount = commission.invitedCount || 0
      commissionData.pendingCommission = commission.pendingCommission || 0
      commissionData.balanceCommission = commission.balanceCommission || 0
      commissionData.totalCommission = commission.totalCommission || 0
    }
  } catch (error) {
    console.error('加载佣金数据失败:', error)
    Message.error('加载佣金数据失败')
  } finally {
    loading.value = false
  }
}

// 组件挂载时确保用户信息已加载并加载佣金数据
onMounted(async () => {
  if (!userStore.currentUser && userStore.token) {
    await userStore.initUserInfo()
  }
  await loadCommissionData()
})

// 组件卸载时清理状态
onUnmounted(() => {
  // 清理可能存在的定时器
  copied.value = false
  showRedeemModal.value = false
  showWithdrawModal.value = false
})

</script>

<style scoped>
/* 确保组件内容不会因为弹窗的显示隐藏导致页面高度变化 */
.invite-rebate-container {
  min-height: 100vh;
  width: 100%;
  overflow-x: hidden;
  /* 添加硬件加速 */
  transform: translateZ(0);
  -webkit-transform: translateZ(0);
}

/* 按钮文本过渡效果 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>