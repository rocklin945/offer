<template>
  <div class="min-h-screen bg-gray-100 flex">
    <!-- 左侧深色汉堡菜单 -->
    <aside :class="[
      'bg-gray-800 text-white transition-all duration-300 flex flex-col min-h-screen',
      sidebarCollapsed ? 'w-16' : 'w-64'
    ]">
      <!-- 菜单头部 -->
      <div class="p-4 border-b border-gray-700">
        <div class="flex items-center justify-center">
          <div class="w-8 h-8 bg-blue-600 rounded flex items-center justify-center">
            <svg class="w-5 h-5 text-white" fill="currentColor" viewBox="0 0 20 20">
              <path d="M3 4a1 1 0 011-1h12a1 1 0 011 1v2a1 1 0 01-1 1H4a1 1 0 01-1-1V4zM3 10a1 1 0 011-1h6a1 1 0 011 1v6a1 1 0 01-1 1H4a1 1 0 01-1-1v-6zM14 9a1 1 0 00-1 1v6a1 1 0 001 1h2a1 1 0 001-1v-6a1 1 0 00-1-1h-2z"></path>
            </svg>
          </div>
          <div v-if="!sidebarCollapsed" class="ml-3 overflow-hidden">
            <span class="text-lg font-semibold whitespace-nowrap">管理系统</span>
          </div>
        </div>
      </div>

      <!-- 菜单项 -->
      <nav class="flex-1 py-4">
        <div class="space-y-1">
          <router-link
            v-for="item in menuItems"
            :key="item.name"
            :to="item.path"
            :class="[
              'flex items-center px-4 py-3 text-sm font-medium transition-all duration-300 group relative',
              $route.path === item.path || $route.path.startsWith(item.path + '/')
                ? 'bg-blue-600 text-white'
                : 'text-gray-300 hover:bg-gray-700 hover:text-white'
            ]"
            :title="sidebarCollapsed ? item.label : ''"
          >
            <div class="w-5 h-5 flex-shrink-0 flex items-center justify-center">
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path v-if="item.name === 'dashboard'" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 5a1 1 0 011-1h14a1 1 0 011 1v2a1 1 0 01-1 1H5a1 1 0 01-1-1V5zM4 13a1 1 0 011-1h6a1 1 0 011 1v6a1 1 0 01-1 1H5a1 1 0 01-1-1v-6zM16 13a1 1 0 011-1h2a1 1 0 011 1v6a1 1 0 01-1 1h-2a1 1 0 01-1-1v-6z"></path>
                <path v-else-if="item.name === 'job-management'" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"></path>
                <path v-else-if="item.name === 'user-management'" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197m13.5-9a2.5 2.5 0 11-5 0 2.5 2.5 0 015 0z"></path>
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
    <main class="flex-1 flex flex-col">
      <!-- 面包屑导航 -->
      <div class="bg-white border-b border-gray-200 px-6 py-4">
        <div class="flex items-center">
          <!-- 汉堡菜单按钮 -->
          <button @click="toggleSidebar" class="p-2 rounded-md hover:bg-gray-100 transition-colors mr-4">
            <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path>
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
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const sidebarCollapsed = ref(false)

const toggleSidebar = () => {
  sidebarCollapsed.value = !sidebarCollapsed.value
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
  }
]
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
</style>
