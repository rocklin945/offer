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
        <div class="flex items-center justify-between h-16">
          <div class="flex items-center">
            <!-- 系统标题 -->
            <h1 class="px-8 text-xl font-semibold text-gray-900">MyOffer</h1>

            <!-- 导航 -->
            <nav class="flex space-x-8">
              <router-link to="/"
                class="text-gray-500 hover:text-gray-700 px-3 py-2 rounded-md text-sm font-medium transition-colors"
                active-class="text-primary-600 bg-primary-50">
                招聘列表
              </router-link>
              <router-link v-if="userStore.currentUser" to="/my-apply"
                class="text-gray-500 hover:text-gray-700 px-3 py-2 rounded-md text-sm font-medium transition-colors"
                active-class="text-primary-600 bg-primary-50">
                我的投递记录
              </router-link>
            </nav>
          </div>

          <!-- 用户操作区 -->
          <div class="px-8">
            <!-- 已登录状态 -->
            <div v-if="userStore.currentUser" class="flex items-center space-x-4">
              <div class="flex items-center space-x-2">
                <img :src="userStore.currentUser.userAvatar" alt="用户头像" class="w-8 h-8 rounded-full" />
                <span class="text-sm font-medium text-gray-700">{{ userStore.currentUser.userName }}</span>
              </div>

              <div class="flex space-x-2">
                <!-- 管理员显示后台入口 -->
                <router-link v-if="userStore.isAdmin()" to="/admin/dashboard"
                  class="text-sm px-3 py-1 bg-blue-100 text-blue-700 rounded hover:bg-blue-200 transition-colors">
                  后台管理
                </router-link>

                <button @click="handleLogout"
                  class="text-sm px-3 py-1 bg-gray-100 text-gray-700 rounded hover:bg-gray-200 transition-colors">
                  退出登录
                </button>
              </div>
            </div>

            <!-- 未登录状态 -->
            <button v-else @click="showLoginModal = true"
              class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition-colors">
              登录/注册
            </button>
          </div>
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

      <!-- 登录/注册模态框 -->
      <LoginModal v-if="showLoginModal" @close="showLoginModal = false" @login-success="showLoginModal = false" />

      <!-- 全局页脚 -->
      <footer class="bg-white border-t border-gray-200">
        <div class="max-w-full mx-auto px-4 sm:px-6 lg:px-8 py-6">
          <div class="text-center text-sm text-gray-500">
            <p>&copy; 2025 MyOffer</p>
          </div>
        </div>
      </footer>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from './stores/user'
import LoginModal from './components/LoginModal.vue'

const route = useRoute()
const userStore = useUserStore()
const showLoginModal = ref(false)

// 判断是否为管理页面路由
const isAdminRoute = computed(() => {
  return route.path.startsWith('/admin')
})

// 处理登出
const handleLogout = () => {
  userStore.userLogout()
}

// 页面加载时尝试获取用户信息
onMounted(async () => {
  if (userStore.token) {
    await userStore.initUserInfo()
  }
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
