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
      class="relative bg-white rounded-2xl shadow-2xl p-6 sm:p-8 max-w-sm sm:max-w-md mx-auto transform transition-all duration-300 ease-out"
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
        <h3 class="text-xl sm:text-2xl font-bold text-gray-900 mb-2">提现说明</h3>
        <p class="text-base sm:text-lg text-gray-700 mb-4 sm:mb-5 leading-relaxed">
          添加群聊后，私聊群主 / 技术支持，提现现金
        </p>

        <!-- 二维码或图片 -->
        <div class="mb-4 sm:mb-6">
          <div class="inline-block p-3 sm:p-4 bg-gray-100 rounded-xl">
            <div
              v-if="loading"
              class="w-40 h-40 sm:w-48 sm:h-48 bg-gradient-to-br from-blue-100 to-blue-200 rounded-lg flex items-center justify-center border-2 border-dashed border-blue-300"
            >
              <div class="text-center">
                <div class="animate-spin w-6 h-6 sm:w-8 sm:h-8 border-4 border-blue-500 border-t-transparent rounded-full mx-auto mb-2"></div>
                <p class="text-blue-600 text-sm font-medium">加载中...</p>
              </div>
            </div>

            <div v-else-if="imageUrl" class="w-40 h-40 sm:w-48 sm:h-48 rounded-lg overflow-hidden border-2 border-blue-300">
              <img :src="imageUrl" alt="群聊二维码" class="w-full h-full object-cover" @error="onImageError" />
            </div>
          </div>
        </div>

        <!-- 操作说明 -->
        <div class="bg-blue-50 rounded-xl p-3 sm:p-4 text-center mb-3 sm:mb-4">
          <p class="text-blue-800 font-medium mb-1 text-sm sm:text-base flex items-center justify-center">
            <svg class="w-4 h-4 mr-2 text-green-500" fill="currentColor" viewBox="0 0 24 24">
              <path
                d="M8.691 2.188C3.891 2.188 0 5.476 0 9.53c0 2.212 1.17 4.203 3.002 5.55a.59.59 0 0 1 .213.665l-.39 1.298c-.1.333.23.63.54.48l1.693-.82a.56.56 0 0 1 .486-.065c.711.248 1.46.379 2.147.379 4.8 0 8.691-3.288 8.691-7.342 0-4.054-3.891-7.342-8.691-7.342zm-2.46 9.638a.896.896 0 0 1-.896-.896.896.896 0 0 1 .896-.896.896.896 0 0 1 .896.896.896.896 0 0 1-.896.896z"
              />
              <path
                d="M15.54 9.72c.8 0 1.458.65 1.458 1.458s-.658 1.458-1.458 1.458-1.458-.65-1.458-1.458.658-1.458 1.458-1.458z"
              />
              <path
                d="M24 15.591c0-3.348-3.016-6.062-6.734-6.062-3.719 0-6.734 2.714-6.734 6.062s3.015 6.062 6.734 6.062c.537 0 1.057-.074 1.548-.212a.473.473 0 0 1 .413.055l1.436.698c.262.127.574-.125.459-.39l-.331-1.102a.5.5 0 0 1 .181-.564C22.832 19.438 24 17.665 24 15.591zm-9.983-.459c-.664 0-1.202-.538-1.202-1.202s.538-1.202 1.202-1.202 1.202.538 1.202 1.202-.538 1.202-1.202 1.202zm4.92 0c-.664 0-1.202-.538-1.202-1.202s.538-1.202 1.202-1.202 1.202.538 1.202 1.202-.538 1.202-1.202 1.202z"
              />
            </svg>
            微信扫码进群
          </p>
          <p class="text-blue-600 text-xs sm:text-sm">进群后私聊群主或技术支持进行提现</p>
        </div>

        <!-- 按钮 -->
        <div class="mt-4 sm:mt-6">
          <button
            @click="handleClose"
            class="w-full bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white font-semibold py-3 px-6 rounded-xl transition-all duration-200 transform hover:scale-105 shadow-lg hover:shadow-xl"
          >
            我知道了
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue'
import { webInfoApi } from '@/api/webInfo'

const emit = defineEmits<{
  close: []
}>()

const imageUrl = ref<string | null>(null)
const loading = ref(true)
const isVisible = ref(false)

const handleClose = () => {
  isVisible.value = false
  setTimeout(() => emit('close'), 300)
}

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
      console.warn('WithdrawCashModal: 响应状态码异常:', response)
      imageUrl.value = null
    }
  } catch (error) {
    console.error('WithdrawCashModal: 获取二维码图片URL失败:', error)
    imageUrl.value = null
  } finally {
    loading.value = false
  }
}

const onImageError = () => {
  console.warn('提现二维码图片加载失败')
}

onMounted(async () => {
  await fetchMemberImageUrl()
  await nextTick()
  isVisible.value = true
})
</script>

<style scoped>
@media (max-width: 640px) {
  .relative {
    margin-left: 1rem;
    margin-right: 1rem;
  }
}
</style>