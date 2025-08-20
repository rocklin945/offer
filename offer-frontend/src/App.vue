<template>
  <div id="app" class="min-h-screen flex flex-col">
    <!-- 全局顶部导航 -->
    <header class="bg-white shadow-sm border-b border-gray-200">
      <div class="max-w-full mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <div class="flex items-center">
            <h1 class="text-xl font-semibold text-gray-900">招聘信息管理系统</h1>
          </div>
          <nav class="flex space-x-8">
            <router-link
              to="/"
              class="text-gray-500 hover:text-gray-700 px-3 py-2 rounded-md text-sm font-medium transition-colors"
              active-class="text-primary-600 bg-primary-50"
            >
              招聘列表
            </router-link>
            <router-link
              to="/add"
              class="text-gray-500 hover:text-gray-700 px-3 py-2 rounded-md text-sm font-medium transition-colors"
              active-class="text-primary-600 bg-primary-50"
            >
              添加招聘
            </router-link>
          </nav>
        </div>
      </div>
    </header>

    <!-- 内容主体部分 -->
    <main class="flex-1 bg-gray-50">
      <div class="max-w-full mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <router-view />
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
</template>

<script setup lang="ts">
// 这里可以添加全局逻辑
</script>

<style scoped>
/* 组件特定样式 */
</style>