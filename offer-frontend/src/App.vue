<template>
  <div id="app" class="min-h-screen">
    <!-- 管理页面布局 -->
    <div v-if="isAdminRoute">
      <router-view />
    </div>

    <!-- 前台页面布局 -->
    <div v-else class="min-h-screen flex flex-col">
      <!-- 全局顶部导航 -->
      <header class="bg-white shadow-sm border-b border-gray-200">
        <div class="flex items-center h-16 space-x-0">
          <!-- 系统标题 -->
          <h1 class="px-8 text-xl font-semibold text-gray-900">MyOffer</h1>

          <!-- 导航 -->
          <nav class="flex space-x-8">
            <router-link to="/"
              class="text-gray-500 hover:text-gray-700 px-3 py-2 rounded-md text-sm font-medium transition-colors"
              active-class="text-primary-600 bg-primary-50">
              招聘列表
            </router-link>
          </nav>
        </div>
      </header>

      <!-- 内容主体部分 -->
      <main class="flex-1 bg-gray-50">
        <div class="max-w-full mx-auto px-4 sm:px-6 lg:px-8 py-8">
          <router-view v-slot="{ Component }">
            <transition name="fade" mode="out-in">
              <component :is="Component" />
            </transition>
          </router-view>
        </div>
      </main>

      <!-- 全局页脚 -->
      <footer class="bg-white border-t border-gray-200">
        <div class="max-w-full mx-auto px-4 sm:px-6 lg:px-8 py-6">
          <div class="text-center text-sm text-gray-500">
            <p>&copy; 2025 招聘信息管理系统. All rights reserved.</p>
            <p class="mt-1">Powered by Vue 3 + TypeScript + TailwindCSS</p>
          </div>
        </div>
      </footer>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

// 判断是否为管理页面路由
const isAdminRoute = computed(() => {
  return route.path.startsWith('/admin')
})
</script>

<style scoped>
/* 路由过渡动画 */
.fade-enter-active,
.fade-leave-active {
  transition: all 0.3s ease;
}

.fade-enter-from {
  opacity: 0;
  transform: translateX(20px);
}

.fade-leave-to {
  opacity: 0;
  transform: translateX(-20px);
}

.fade-enter-to,
.fade-leave-from {
  opacity: 1;
  transform: translateX(0);
}
</style>
