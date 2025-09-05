<template>
  <div id="app" class="min-h-screen" style="width: 100%; overflow-x: hidden;">
    <!-- 管理页面布局 -->
    <div v-if="isAdminRoute">
      <router-view />
    </div>

    <!-- 前台页面布局 -->
    <div v-else class="min-h-screen flex flex-col">
      <!-- 全局顶部导航 -->
      <header class="bg-white shadow-sm border-b border-gray-200 relative z-50">
        <div class="flex items-center justify-between h-16 px-3 sm:px-8">
          <div class="flex items-center min-w-0 flex-1">
            <!-- 系统标题 -->
            <h1 class="text-lg sm:text-xl font-semibold text-gray-900 mr-2 sm:mr-4 flex-shrink-0">MyOffer</h1>

            <!-- 导航 - 优化移动端布局 -->
            <nav class="flex flex-nowrap space-x-1 sm:space-x-8 min-w-0 overflow-x-auto relative z-50">
              <router-link to="/"
                class="hover:text-gray-700 px-1 py-1 text-sm sm:px-3 sm:py-2 sm:text-sm rounded-md font-medium transition-colors whitespace-nowrap flex-shrink-0"
                :style="getNavStyle('/')">
                <span class="hidden sm:inline">招聘列表</span>
                <span class="sm:hidden">招聘列表</span>
              </router-link>
              <router-link to="/my-apply"
                class="hover:text-gray-700 px-1 py-1 text-sm sm:px-3 sm:py-2 sm:text-sm rounded-md font-medium transition-colors whitespace-nowrap flex-shrink-0"
                :style="getNavStyle('/my-apply')">
                <span class="hidden sm:inline">我的投递记录</span>
                <span class="sm:hidden">投递记录</span>
              </router-link>
              <router-link to="/my-resume"
                class="hover:text-gray-700 px-1 py-1 text-sm sm:px-3 sm:py-2 sm:text-sm rounded-md font-medium transition-colors whitespace-nowrap flex-shrink-0"
                :style="getNavStyle('/my-resume')">
                <span class="hidden sm:inline">个人简历</span>
                <span class="sm:hidden">个人简历</span>
              </router-link>
              <router-link v-if="userStore.currentUser" to="/become-member" class="hidden sm:inline-flex" :class="[
                'px-3 py-2 rounded-md text-sm font-medium transition-colors',
                userStore.currentUser.userRole === 1
                  ? 'text-orange-500 hover:text-orange-700 bg-orange-50 hover:bg-orange-100'
                  : 'text-blue-500 hover:text-blue-700 bg-blue-50 hover:bg-blue-100'
              ]" active-class="text-orange-600 bg-orange-100">
                {{ userStore.currentUser.userRole === 1 ? '成为会员 ⭐' : '会员中心 💎' }}
              </router-link>
              <router-link v-if="userStore.currentUser" to="/invite-rebate"
                class="hidden sm:inline-flex px-3 py-2 rounded-md text-sm font-medium transition-colors text-green-500 hover:text-green-700 bg-green-100 hover:bg-green-200"
                active-class="text-green-600 bg-green-200">
                邀请返利 💵
              </router-link>
            </nav>
          </div>

          <!-- 用户操作区 - 优化小屏幕下的布局 -->
          <div class="relative flex-shrink-0">
            <!-- 移动端汉堡菜单（已登录） -->
            <div v-if="userStore.currentUser" class="sm:hidden">
              <button @click="showMobileMenu = !showMobileMenu" data-menu-button
                class="p-2 rounded-md border border-gray-300 hover:bg-gray-50">
                <svg class="w-5 h-5 text-gray-700" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16">
                  </path>
                </svg>
              </button>

              <div v-if="showMobileMenu" data-menu-container
                class="absolute right-0 mt-2 w-56 bg-white border border-gray-200 rounded-lg shadow-lg z-40"
                @click.stop>
                <div class="px-4 py-3 border-b">
                  <div class="flex items-center space-x-2">
                    <img :src="userStore.currentUser.userAvatar" alt="用户头像" class="w-6 h-6 rounded-full" />
                    <span class="text-sm font-medium text-gray-700">{{ userStore.currentUser.userName }}</span>
                    <span v-if="userStore.currentUser.userRole === 2"
                      class="text-xs px-2 py-1 bg-yellow-100 text-yellow-700 rounded-full font-medium">
                      尊贵会员
                      <span v-if="memberDaysLeft !== null" class="ml-1">
                        ({{ memberDaysLeft }}天)
                      </span>
                    </span>
                  </div>
                </div>
                <div class="py-1">
                  <router-link to="/become-member" @click="showMobileMenu = false"
                    class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                    {{ userStore.currentUser.userRole === 1 ? '成为会员 ⭐' : '会员中心 💎' }}
                  </router-link>
                  <router-link to="/invite-rebate" @click="showMobileMenu = false" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                    邀请返利 💵
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
                  <span v-if="memberDaysLeft !== null" class="ml-1">
                    ({{ memberDaysLeft }}天)
                  </span>
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
        <div class="max-w-full mx-auto px-4 sm:px-6 lg:px-8 py-8 stable-layout">
          <router-view v-slot="{ Component }">
            <transition name="fade" mode="out-in">
              <component :is="Component" :key="$route.fullPath" />
            </transition>
          </router-view>
        </div>
      </main>

      <!-- 登录/注册模态框 -->
      <LoginModal v-if="showLoginModal" @close="showLoginModal = false" @login-success="showLoginModal = false" />

      <!-- 首页弹窗 -->
      <HomeModal v-if="showHomeModal" @close="closeHomeModal" />

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
import { computed, ref, onMounted, onUnmounted, watch, nextTick } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from './stores/user'
import LoginModal from './components/LoginModal.vue'
import HomeModal from './components/HomeModal.vue'

const route = useRoute()
const userStore = useUserStore()
const showLoginModal = ref(false)
const showMobileMenu = ref(false)
const showHomeModal = ref(false)

// 判断是否为管理页面路由
const isAdminRoute = computed(() => {
  return route.path.startsWith('/admin')
})

// 计算会员剩余天数
const memberDaysLeft = computed(() => {
  if (!userStore.currentUser || userStore.currentUser.userRole !== 2 || !userStore.currentUser.memberExpireTime) {
    return null
  }

  const expireTime = new Date(userStore.currentUser.memberExpireTime)
  const now = new Date()
  const diffTime = expireTime.getTime() - now.getTime()
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24))

  return diffDays > 0 ? diffDays : 0
})

// 处理登出
const handleLogout = () => {
  userStore.userLogout()
}

// 获取导航样式（使用内联样式强制覆盖）
const getNavStyle = (path: string) => {
  const isActive = route.path === path
  return {
    color: isActive ? '#2563eb' : '#6b7280',
    backgroundColor: isActive ? '#eff6ff' : 'transparent'
  }
}

// 点击外部关闭菜单
const handleClickOutside = (event: Event) => {
  const target = event.target as Element
  const menuButton = document.querySelector('[data-menu-button]')
  const menuContainer = document.querySelector('[data-menu-container]')

  if (showMobileMenu.value &&
    !menuButton?.contains(target) &&
    !menuContainer?.contains(target)) {
    showMobileMenu.value = false
  }
}

// 监听点击事件
onMounted(async () => {
  document.addEventListener('click', handleClickOutside)

  if (userStore.token) {
    await userStore.initUserInfo()
  }
  // 检查是否需要显示首页弹窗
  checkShowHomeModal()
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})

// 检查是否显示首页弹窗
const checkShowHomeModal = () => {
  // 只在首页显示弹窗
  if (route.path === '/') {
    // // 首先检查管理员是否关闭了弹窗
    // const adminDisabled = localStorage.getItem('adminDisabledHomeModal')
    // const adminEnabled = localStorage.getItem('adminHomeModalEnabled')

    // // 如果管理员明确关闭了弹窗，则不显示
    // if (adminDisabled === 'true' || adminEnabled === 'false') {
    //   console.log('管理员已关闭首页弹窗')
    //   return
    // }

    // 检查是否已经显示过弹窗（本次会话内）
    const sessionShown = sessionStorage.getItem('homeModalShown')

    if (!sessionShown) {
      // 延迟1秒显示弹窗，让页面加载完成
      setTimeout(() => {
        showHomeModal.value = true
        sessionStorage.setItem('homeModalShown', 'true')
      }, 500)
    }
  }
}

// 关闭弹窗的处理函数
const closeHomeModal = () => {
  showHomeModal.value = false
}

// 监听路由变化并强制刷新
watch(() => route.path, () => {
  checkShowHomeModal()
  // 强制触发重渲染，确保active-class正确应用
  nextTick(() => {
    // 触发DOM更新
    const activeLinks = document.querySelectorAll('.router-link-active')
    activeLinks.forEach(link => {
      link.classList.remove('text-gray-500')
      link.classList.add('text-primary-600', 'bg-primary-50')
    })
  })
}, { immediate: true })
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

/* 强制应用活跃状态样式 */
.router-link-active {
  color: #2563eb !important;
  background-color: #eff6ff !important;
}

/* 确保在移动端也生效 */
@media (max-width: 640px) {
  .router-link-active {
    color: #2563eb !important;
    background-color: #eff6ff !important;
  }
}
</style>
