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
        <div class="flex items-center justify-between h-16 px-3 sm:px-8">
          <div class="flex items-center min-w-0 flex-1">
            <!-- 系统标题 -->
            <h1 class="text-lg sm:text-xl font-semibold text-gray-900 mr-2 sm:mr-4 flex-shrink-0">MyOffer</h1>

            <!-- 导航 - 在小屏幕下限制宽度 -->
            <nav class="flex flex-nowrap space-x-1 sm:space-x-8 min-w-0 overflow-hidden">
              <router-link to="/"
                class="text-gray-500 hover:text-gray-700 px-1 py-1 text-sm sm:px-3 sm:py-2 sm:text-sm rounded-md font-medium transition-colors whitespace-nowrap flex-shrink-0"
                active-class="text-primary-600 bg-primary-50">
                <span class="hidden sm:inline">招聘列表</span>
                <span class="sm:hidden">招聘列表</span>
              </router-link>
              <router-link to="/my-apply"
                class="text-gray-500 hover:text-gray-700 px-1 py-1 text-sm sm:px-3 sm:py-2 sm:text-sm rounded-md font-medium transition-colors whitespace-nowrap flex-shrink-0"
                active-class="text-primary-600 bg-primary-50">
                <span class="hidden sm:inline">我的投递记录</span>
                <span class="sm:hidden">我的投递记录</span>
              </router-link>
              <router-link v-if="userStore.currentUser" to="/become-member" class="hidden sm:inline-flex" :class="[
                'px-3 py-2 rounded-md text-sm font-medium transition-colors',
                userStore.currentUser.userRole === 1
                  ? 'text-orange-500 hover:text-orange-700 bg-orange-50 hover:bg-orange-100'
                  : 'text-blue-500 hover:text-blue-700 bg-blue-50 hover:bg-blue-100'
              ]" active-class="text-orange-600 bg-orange-100">
                {{ userStore.currentUser.userRole === 1 ? '成为会员 ⭐' : '会员中心 💎' }}
              </router-link>
            </nav>
          </div>

          <!-- 用户操作区 - 优化小屏幕下的布局 -->
          <div class="relative flex-shrink-0">
            <!-- 移动端汉堡菜单（已登录） -->
            <div v-if="userStore.currentUser" class="sm:hidden">
              <button @click="showMobileMenu = !showMobileMenu"
                class="p-2 rounded-md border border-gray-300 hover:bg-gray-50">
                <svg class="w-5 h-5 text-gray-700" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16">
                  </path>
                </svg>
              </button>

              <!-- 透明遮罩层 -->
              <div v-if="showMobileMenu" class="fixed inset-0 z-40" @click="showMobileMenu = false"></div>

              <div v-if="showMobileMenu"
                class="absolute right-0 mt-2 w-56 bg-white border border-gray-200 rounded-lg shadow-lg z-50">
                <div class="px-4 py-3 border-b">
                  <div class="flex items-center space-x-2">
                    <img :src="userStore.currentUser.userAvatar" alt="用户头像" class="w-6 h-6 rounded-full" />
                    <span class="text-sm font-medium text-gray-700">{{ userStore.currentUser.userName }}</span>
                  </div>
                </div>
                <div class="py-1">
                  <router-link to="/become-member" @click="showMobileMenu = false"
                    class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                    {{ userStore.currentUser.userRole === 1 ? '成为会员 ⭐' : '会员中心 💎' }}
                  </router-link>
                  <router-link v-if="userStore.isAdmin()" to="/admin/dashboard" @click="showMobileMenu = false"
                    class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                    后台管理
                  </router-link>
                  <button @click="() => { handleLogout(); showMobileMenu = false; }"
                    class="w-full text-left block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                    退出登录
                  </button>
                </div>
              </div>
            </div>
            <!-- 已登录状态 -->
            <div v-if="userStore.currentUser" class="hidden sm:flex items-center space-x-4">
              <div class="flex items-center space-x-2">
                <!-- 用户身份标识 -->
                <span v-if="userStore.currentUser.userRole === 0"
                  class="text-xs px-2 py-1 bg-red-100 text-red-700 rounded-full font-medium">
                  管理员
                </span>
                <span v-else-if="userStore.currentUser.userRole === 2"
                  class="text-xs px-2 py-1 bg-yellow-100 text-yellow-700 rounded-full font-medium">
                  尊贵会员
                </span>
                <span v-else class="text-xs px-2 py-1 bg-gray-100 text-gray-700 rounded-full font-medium">
                  普通用户
                </span>

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

            <!-- 未登录状态 - 优化小屏幕按钮大小 -->
            <button v-else @click="showLoginModal = true" class="px-4 py-2 text-sm bg-blue-600 text-white rounded 
         hover:bg-blue-700 transition-colors whitespace-nowrap">
              <span class="sm:hidden">登录/注册</span>
              <span class="hidden sm:inline">登录/注册</span>
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
const showMobileMenu = ref(false)

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
