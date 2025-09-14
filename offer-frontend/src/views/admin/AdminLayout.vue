<template>
  <div class="min-h-screen bg-gray-100 flex">
    <!-- 左侧深色汉堡菜单 -->
    <aside :class="[sidebarCollapsed ? 'w-16' : 'w-64']"
      class="bg-gray-800 text-white transition-all duration-300 flex flex-col min-h-screen flex-shrink-0">
      <!-- 菜单头部 -->
      <div class="p-4 border-b border-gray-700">
        <div class="flex items-center justify-center">
          <div class="w-8 h-8 bg-blue-600 rounded flex items-center justify-center">
            <svg class="w-5 h-5 text-white" fill="currentColor" viewBox="0 0 20 20">
              <path
                d="M3 4a1 1 0 011-1h12a1 1 0 011 1v2a1 1 0 01-1 1H4a1 1 0 01-1-1V4zM3 10a1 1 0 011-1h6a1 1 0 011 1v6a1 1 0 01-1 1H4a1 1 0 01-1-1v-6zM14 9a1 1 0 00-1 1v6a1 1 0 001 1h2a1 1 0 001-1v-6a1 1 0 00-1-1h-2z">
              </path>
            </svg>
          </div>
          <div v-if="!sidebarCollapsed" class="ml-3 overflow-hidden">
            <span class="text-lg font-semibold whitespace-nowrap">MyOffer管理系统</span>
          </div>
        </div>
      </div>

      <!-- 菜单项 -->
      <nav class="flex-1 py-4">
        <div class="space-y-1">
          <router-link v-for="item in menuItems" :key="item.name" :to="item.path" :class="[
            'admin-menu-item flex items-center px-4 py-3 text-sm font-medium transition-all duration-300 group relative',
            $route.path === item.path || $route.path.startsWith(item.path + '/')
              ? 'bg-blue-600 text-white'
              : 'text-gray-300 hover:bg-gray-700 hover:text-white'
          ]" :title="sidebarCollapsed ? item.label : ''">
            <div class="w-5 h-5 flex-shrink-0 flex items-center justify-center">
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path v-if="item.name === 'dashboard'" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 17V7m0 10a2 2 0 01-2 2H5a2 2 0 01-2-2V7a2 2 0 012-2h2a2 2 0 012 2m0 10a2 2 0 002 2h2a2 2 0 002-2M9 7a2 2 0 012-2h2a2 2 0 012 2m0 10V7m0 10a2 2 0 002 2h2a2 2 0 002-2V7a2 2 0 00-2-2h-2a2 2 0 00-2 2">
                </path>
                <path v-else-if="item.name === 'job-management'" stroke-linecap="round" stroke-linejoin="round"
                  stroke-width="2"
                  d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4">
                </path>
                <path v-else-if="item.name === 'user-management'" stroke-linecap="round" stroke-linejoin="round"
                  stroke-width="2"
                  d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z">
                </path>
                <path v-else-if="item.name === 'user-job-apply-management'" stroke-linecap="round"
                  stroke-linejoin="round" stroke-width="2"
                  d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z">
                </path>
                <path v-else-if="item.name === 'invite-commission-management'" stroke-linecap="round"
                  stroke-linejoin="round" stroke-width="2"
                  d="M17 9V7a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2m2 4h10a2 2 0 002-2v-6a2 2 0 00-2-2H9a2 2 0 00-2 2v6a2 2 0 002 2zm7-5a2 2 0 11-4 0 2 2 0 014 0z">
                </path>
                <path v-else-if="item.name === 'material-management'" stroke-linecap="round" stroke-linejoin="round"
                  stroke-width="2"
                  d="M19 11H5a2 2 0 01-2-2V7a2 2 0 012-2h14a2 2 0 012 2v2a2 2 0 01-2 2zm0 7H5a2 2 0 01-2-2v-2a2 2 0 012-2h14a2 2 0 012 2v2a2 2 0 01-2 2z">
                </path>
                <path v-else-if="item.name === 'order-management'" stroke-linecap="round" stroke-linejoin="round"
                  stroke-width="2"
                  d="M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z">
                </path>
              </svg>
            </div>
            <div v-if="!sidebarCollapsed" class="ml-3 overflow-hidden">
              <span class="whitespace-nowrap">{{ item.label }}</span>
            </div>
          </router-link>
        </div>
      </nav>
    </aside>

    <!-- 右侧主内容区域 -->
    <main class="flex-1 flex flex-col min-w-0">
      <!-- 面包屑导航 -->
      <div class="bg-white border-b border-gray-200 px-6 py-4">
        <div class="flex items-center justify-between">
          <div class="flex items-center">
            <!-- 汉堡菜单按钮 -->
            <button @click="toggleSidebar" class="p-2 rounded-md hover:bg-gray-100 transition-colors mr-4">
              <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16">
                </path>
              </svg>
            </button>

            <!-- 面包屑 -->
            <nav class="flex items-center text-sm text-gray-500">
              <router-link to="/admin/dashboard" class="hover:text-blue-600 transition-colors">
                首页
              </router-link>
              <svg class="mx-2 w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
              </svg>
              <span class="text-gray-900 font-medium">{{ getCurrentPageTitle() }}</span>
            </nav>
          </div>

          <!-- 右上角按钮组 -->
          <div class="flex items-center gap-3">
            <!-- 首页弹窗控制开关 -->
            <button @click="toggleHomeModal" :class="[
              'inline-flex items-center px-3 py-2 text-sm font-medium rounded-lg transition-colors shadow-sm',
              homeModalEnabled
                ? 'bg-green-600 text-white hover:bg-green-700'
                : 'bg-gray-600 text-white hover:bg-gray-700'
            ]">
              <svg class="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 24 24">
                <path v-if="homeModalEnabled" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                <path v-else d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
              {{ homeModalEnabled ? '弹窗：开' : '弹窗：关' }}
            </button>

            <!-- 查看前台页面按钮 -->
            <router-link to="/"
              class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg flex items-center transition-colors text-sm">
              <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z">
                </path>
              </svg>
              查看前台页面
            </router-link>
          </div>
        </div>
      </div>

      <!-- 页面内容 -->
      <div class="flex-1 p-6 bg-gray-50">
        <router-view v-slot="{ Component }">
          <transition name="slide" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const sidebarCollapsed = ref(false)

// 首页弹窗控制状态
const homeModalEnabled = ref(true)

const toggleSidebar = () => {
  sidebarCollapsed.value = !sidebarCollapsed.value
}

// 初始化弹窗控制状态
const initHomeModalStatus = () => {
  const status = localStorage.getItem('adminHomeModalEnabled')
  homeModalEnabled.value = status !== 'false' // 默认为true，只有明确设置为false才关闭
}

// 切换弹窗显示状态
const toggleHomeModal = () => {
  homeModalEnabled.value = !homeModalEnabled.value

  // 保存设置到localStorage
  localStorage.setItem('adminHomeModalEnabled', homeModalEnabled.value.toString())

  // 清除旧的显示记录，让用户下次访问时能看到效果
  if (homeModalEnabled.value) {
    // 开启弹窗时，清除之前的显示记录
    sessionStorage.removeItem('homeModalShown')
    localStorage.removeItem('homeModalLastShown')
    localStorage.removeItem('adminDisabledHomeModal')
  } else {
    // 关闭弹窗时，设置一个永久标记
    localStorage.setItem('adminDisabledHomeModal', 'true')
  }
}

const getCurrentPageTitle = () => {
  const currentPath = route.path
  const menuItem = menuItems.find(item =>
    currentPath === item.path || currentPath.startsWith(item.path + '/')
  )
  return menuItem?.label || '系统管理'
}

// 菜单项配置
const menuItems = [
  {
    name: 'dashboard',
    path: '/admin/dashboard',
    label: '仪表盘'
  },
  {
    name: 'job-management',
    path: '/admin/job-management',
    label: '招聘信息管理'
  },
  {
    name: 'user-management',
    path: '/admin/user-management',
    label: '用户管理'
  },
  {
    name: 'user-job-apply-management',
    path: '/admin/user-job-apply-management',
    label: '投递记录管理'
  },
  {
    name: 'invite-commission-management',
    path: '/admin/invite-commission-management',
    label: '邀请返利管理'
  },
  {
    name: 'material-management',
    path: '/admin/material-management',
    label: '资料管理'
  },
  {
    name: 'order-management',
    path: '/admin/order-management',
    label: '订单管理'
  }
]

// 初始化组件
onMounted(() => {
  initHomeModalStatus()
})
</script>

<style scoped>
/* 后台管理页面路由过渡动画 */
.slide-enter-active,
.slide-leave-active {
  transition: all 0.4s ease;
}

.slide-enter-from {
  opacity: 0;
  transform: translateY(20px);
}

.slide-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}

.slide-enter-to,
.slide-leave-from {
  opacity: 1;
  transform: translateY(0);
}

/* 确保菜单项的样式优先级 */
.admin-menu-item.router-link-active {
  background-color: #2563eb !important;
  color: white !important;
}

.admin-menu-item:hover {
  background-color: #527bd4 !important;
  color: white !important;
}
</style>