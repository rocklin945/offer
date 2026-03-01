<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Hero Section -->
    <div class="bg-blue-100 text-gray-800">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16 sm:py-24">
        <div class="text-center">
          <h1 class="text-4xl sm:text-5xl lg:text-6xl font-bold tracking-tight mb-6">
            升级为会员
          </h1>
          <p class="text-xl sm:text-2xl text-gray-600 mb-12 max-w-3xl mx-auto">
            解锁更多功能，享受更好的求职体验
          </p>

          <!-- Stats -->
          <div class="grid grid-cols-1 sm:grid-cols-3 gap-8 max-w-4xl mx-auto">
            <div class="text-center">
              <div class="text-3xl sm:text-4xl font-bold mb-2 text-blue-600">2000+</div>
              <div class="text-gray-600">优质职位</div>
            </div>
            <div class="text-center">
              <div class="text-3xl sm:text-4xl font-bold mb-2 text-blue-600">500+</div>
              <div class="text-gray-600">合作企业</div>
            </div>
            <div class="text-center">
              <div class="text-3xl sm:text-4xl font-bold mb-2 text-blue-600">98%</div>
              <div class="text-gray-600">满意度</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Pricing Section -->
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16 sm:py-24">
      <!-- Section Header -->
      <div class="text-center mb-16">
        <h2 class="text-3xl sm:text-4xl font-bold text-gray-900 mb-4">
          选择适合您的套餐
        </h2>
        <p class="text-xl text-gray-600 max-w-2xl mx-auto">
          灵活的定价方案，满足不同需求
        </p>
      </div>

      <!-- Pricing Cards -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8 max-w-6xl mx-auto">
        <!-- 免费版 -->
        <div class="bg-white rounded-2xl shadow-lg border border-gray-200 p-8 relative">
          <div class="text-center mb-8">
            <h3 class="text-2xl font-bold text-gray-900 mb-2">免费版</h3>
            <div class="mb-4">
              <span class="text-4xl font-bold text-gray-900">¥0</span>
              <span class="text-gray-500">/永久</span>
            </div>
            <p class="text-gray-600">适合初次求职的用户</p>
          </div>

          <ul class="space-y-4 mb-8">
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">浏览前5页职位</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">基础投递功能</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">笔试面试资料前6页</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-red-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                  clip-rule="evenodd"></path>
              </svg>
              <span class="text-gray-700">无法查看全部职位</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-red-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                  clip-rule="evenodd"></path>
              </svg>
              <span class="text-gray-700">无优先内推机会</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-red-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                  clip-rule="evenodd"></path>
              </svg>
              <span class="text-gray-700">无专属客服支持</span>
            </li>
          </ul>

          <button disabled
            class="w-full py-3 px-6 rounded-xl bg-gray-100 text-gray-400 font-semibold cursor-not-allowed">
            当前方案
          </button>
        </div>

        <!-- 秋招会员版 -->
        <div class="bg-white rounded-2xl shadow-xl border-2 border-blue-500 p-8 relative">
          <div class="absolute -top-4 left-1/2 transform -translate-x-1/2">
            <span class="bg-blue-500 text-white px-6 py-2 rounded-full text-sm font-semibold">
              推荐
            </span>
          </div>

          <div class="text-center mb-8">
            <h3 class="text-2xl font-bold text-gray-900 mb-2">秋招会员</h3>
            <div class="mb-4">
              <span class="text-4xl font-bold text-gray-900">¥{{ priceInfo.currentPrice }}</span>
              <span class="text-gray-500">/120天</span>
            </div>
            <p class="text-gray-600">解锁全部功能</p>
          </div>

          <ul class="space-y-4 mb-8">
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">无限制浏览所有职位</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">享受投递记录功能</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">笔试面试资料无限看</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">优先内推机会</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">简历优化建议</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">专属客服支持</span>
            </li>
          </ul>

          <button @click="openPaymentModal('current')"
            class="w-full py-3 px-6 rounded-xl bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white font-semibold shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-0.5">
            成为会员
          </button>
        </div>

        <!-- 校招直通会员 -->
        <div class="bg-white rounded-2xl shadow-lg border border-gray-200 p-8 relative">
          <div class="text-center mb-8">
            <h3 class="text-2xl font-bold text-gray-900 mb-2">校招直通会员</h3>
            <div class="mb-4">
              <span class="text-4xl font-bold text-gray-900">¥{{ priceInfo.originalPrice }}</span>
              <span class="text-gray-500">/365天</span>
            </div>
            <p class="text-gray-600">持续一年，校招无忧</p>

          </div>

          <ul class="space-y-4 mb-8">
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">无限制浏览所有职位</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">享受投递记录功能</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">笔试面试资料无限看</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">优先内推机会</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">简历优化建议</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">专属客服支持</span>
            </li>
          </ul>

          <button @click="openPaymentModal('original')"
            class="w-full py-3 px-6 rounded-xl bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white font-semibold shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-0.5">
            成为会员
          </button>
        </div>
      </div>
    </div>

    <!-- Features Section -->
    <div class="bg-white py-16 sm:py-24">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="text-3xl sm:text-4xl font-bold text-gray-900 mb-4">
            为什么选择我们的会员服务
          </h2>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-12">
          <div class="text-center">
            <div
              class="w-16 h-16 bg-gradient-to-br from-blue-400 to-blue-600 rounded-2xl flex items-center justify-center mx-auto mb-6">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
              </svg>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-4">无限职位浏览</h3>
            <p class="text-gray-600 leading-relaxed">
              无限制浏览所有职位，不再受页面限制，轻松掌握所有求职机会
            </p>
          </div>

          <div class="text-center">
            <div
              class="w-16 h-16 bg-gradient-to-br from-green-500 to-green-600 rounded-2xl flex items-center justify-center mx-auto mb-6">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z">
                </path>
              </svg>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-4">专属客服支持</h3>
            <p class="text-gray-600 leading-relaxed">
              专业校招团队提供一对一支持，解答求职过程中的任何疑问
            </p>
          </div>

          <div class="text-center">
            <div
              class="w-16 h-16 bg-gradient-to-br from-purple-500 to-purple-600 rounded-2xl flex items-center justify-center mx-auto mb-6">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M19.428 15.428a2 2 0 00-1.022-.547l-2.387-.477a6 6 0 00-3.86.517l-.318.158a6 6 0 01-3.86.517L6.05 15.21a2 2 0 00-1.806.547M8 4h8l-1 1v5.172a2 2 0 00.586 1.414l5 5c1.26 1.26.367 3.414-1.415 3.414H4.828c-1.782 0-2.674-2.154-1.414-3.414l5-5A2 2 0 009 10.172V5L8 4z">
                </path>
              </svg>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-4">丰富学习资源</h3>
            <p class="text-gray-600 leading-relaxed">
              笔试面试资料无限观看，优先内推机会，助您全面提升求职竞争力
            </p>
          </div>
        </div>
      </div>
    </div>
    <!-- FAQ Section -->
    <div class="bg-gray-50 py-16 sm:py-24">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="text-3xl sm:text-4xl font-bold text-gray-900 mb-4">
            常见问题
          </h2>
        </div>

        <div class="space-y-4">
          <div v-for="(faq, index) in faqs" :key="index"
            class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
            <button @click="toggleFaq(index)"
              class="w-full px-6 py-4 text-left flex justify-between items-center hover:bg-gray-50 transition-colors duration-200">
              <h4 class="text-lg font-semibold text-gray-900">{{ faq.question }}</h4>
              <svg :class="['w-5 h-5 text-gray-500 transition-transform duration-200', faq.open ? 'rotate-180' : '']"
                fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                  clip-rule="evenodd" />
              </svg>
            </button>
            <div :class="[
              'overflow-hidden transition-all duration-300 ease-in-out',
              faq.open ? 'max-h-96 opacity-100' : 'max-h-0 opacity-0'
            ]">
              <div class="px-6 pb-4">
                <p class="text-gray-600 leading-relaxed">{{ faq.answer }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 支付方式选择弹窗 -->
    <PaymentModal v-if="showPaymentModal" :price="selectedPrice" @close="closePaymentModal"
      @select-payment="selectPaymentMethod" />

    <!-- 微信支付提示弹窗 -->
    <WechatPaymentModal v-if="showWechatModal" :image-url="imageUrl" :loading="loading" @close="closeWechatModal" />

    <!-- 恭喜成为会员动画 -->
    <WelcomeMemberModal v-if="showWelcomeAnimation" @close="closeWelcomeAnimation" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'
import { webInfoApi } from '@/api/webInfo'
import { payApi } from '@/api/pay'
import Message from '@/components/Message'
import PaymentModal from '@/components/PaymentModal.vue'
import WechatPaymentModal from '@/components/WechatPaymentModal.vue'
import WelcomeMemberModal from '@/components/WelcomeMemberModal.vue'

const router = useRouter()
const isYearly = ref(false)
const imageUrl = ref<string | null>(null)
const priceInfo = ref({ originalPrice: 29.9, currentPrice: 9.9 })
const loading = ref(true)

// 支付弹窗相关
const showPaymentModal = ref(false)
const showWechatModal = ref(false)
const showWelcomeAnimation = ref(false)
const selectedPrice = ref(0)
const selectedPlan = ref('') // 'current' 或 'original'

// 获取价格信息
const fetchPriceInfo = async () => {
  try {
    const response = await webInfoApi.getPrice()
    if (response.statusCode === 200 && response.data) {
      priceInfo.value = response.data
    }
  } catch (error) {
    console.error('获取价格信息失败:', error)
  }
}

// 获取会员图片URL
const fetchMemberImageUrl = async () => {
  try {
    loading.value = true
    const response = await webInfoApi.getMemberImageUrl()
    if (response.statusCode === 200) {
      if (response.data && response.data.trim() !== '') {
        imageUrl.value = response.data
      } else {
        imageUrl.value = null
      }
    } else {
      imageUrl.value = null
    }
  } catch (error) {
    imageUrl.value = null
  } finally {
    loading.value = false
  }
}

const faqs = ref([
  {
    question: '会员权益何时生效？',
    answer: '支付成功后立即生效，您可以马上享受所有会员特权。',
    open: false
  },
  {
    question: '会员能否共享账号，或者二次售卖？',
    answer: 'MyOffer拥有共号检测，如果发现有违规情况，会自动取消会员。',
    open: false
  },
  {
    question: '支持哪些支付方式？',
    answer: '支持微信支付、支付宝等多种支付方式。',
    open: false
  },
  {
    question: '会员可以退款吗？',
    answer: '支持7天无理由退款。',
    open: false
  }
])

const toggleFaq = (index: number) => {
  faqs.value[index].open = !faqs.value[index].open
}

// 打开支付方式选择弹窗
const openPaymentModal = (plan: string) => {
  selectedPlan.value = plan
  selectedPrice.value = plan === 'current' ? priceInfo.value.currentPrice : priceInfo.value.originalPrice
  showPaymentModal.value = true
}

// 关闭支付方式选择弹窗
const closePaymentModal = () => {
  showPaymentModal.value = false
}

// 选择支付方式
const selectPaymentMethod = async (method: string) => {
  closePaymentModal()

  if (method === 'wxpay') {
    // 微信支付，显示二维码
    showWechatModal.value = true
    // 重新获取二维码图片
    await fetchMemberImageUrl()
  } else {
    // 支付宝支付，处理表单跳转
    try {
      const money = selectedPlan.value === 'current' ? priceInfo.value.currentPrice.toString() : priceInfo.value.originalPrice.toString()
      const response = await payApi.createOrder({
        type: 'alipay',
        money: money,
        name: selectedPlan.value === 'current' ? '秋招会员' : '校招直通会员'
      })

      if (response) {
        // 处理返回的HTML表单，直接在当前页面提交
        handlePaymentForm(response)
      } else {
        Message.error('创建订单失败，请稍后重试')
      }
    } catch (error) {
      console.error('创建订单失败:', error)
      Message.error('创建订单失败')
    }
  }
}

const handlePaymentForm = (htmlContent: string) => {
  document.open()
  document.write(htmlContent)
  document.close()
}


// 关闭微信支付弹窗
const closeWechatModal = () => {
  showWechatModal.value = false
}

// 显示欢迎动画
const showWelcome = () => {
  showWelcomeAnimation.value = true
}

// 关闭欢迎动画
const closeWelcomeAnimation = () => {
  showWelcomeAnimation.value = false
}

// 检查URL参数，如果是支付成功跳转则显示欢迎动画
const checkPaymentSuccess = () => {
  const urlParams = new URLSearchParams(window.location.search)
  const status = urlParams.get('status')
  if (status === 'vip') {
    showWelcome()
  }
}

// 组件挂载时获取会员图片URL和检查支付状态
onMounted(() => {
  fetchPriceInfo()
  fetchMemberImageUrl()
  checkPaymentSuccess()
})

// 组件销毁前清理
onBeforeUnmount(() => {
  // 清理可能的定时器或其他资源
})
</script>