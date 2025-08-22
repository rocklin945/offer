<template>
  <div class="space-y-6">
    <!-- 页面标题 -->
    <div class="flex items-center justify-between">
      <h2 class="text-2xl font-bold text-gray-900">仪表盘</h2>
      <div class="text-sm text-gray-500">
        {{ currentTime }}
      </div>
    </div>

    <!-- 统计卡片 -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <div class="bg-gradient-to-r from-blue-500 to-blue-600 rounded-lg p-6 text-white">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-blue-100 text-sm">用户数量</p>
            <p class="text-3xl font-bold">{{ stats.userCount }}</p>
          </div>
          <div class="bg-blue-400 bg-opacity-30 rounded-full p-3">
            <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197m13.5-9a2.5 2.5 0 11-5 0 2.5 2.5 0 015 0z"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-gradient-to-r from-green-500 to-green-600 rounded-lg p-6 text-white">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-green-100 text-sm">招聘信息数量</p>
            <p class="text-3xl font-bold">{{ stats.jobCount }}</p>
          </div>
          <div class="bg-green-400 bg-opacity-30 rounded-full p-3">
            <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-gradient-to-r from-yellow-500 to-yellow-600 rounded-lg p-6 text-white">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-yellow-100 text-sm">网站总浏览量</p>
            <p class="text-3xl font-bold">{{ stats.totalViews }}</p>
          </div>
          <div class="bg-yellow-400 bg-opacity-30 rounded-full p-3">
            <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-gradient-to-r from-purple-500 to-purple-600 rounded-lg p-6 text-white">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-purple-100 text-sm">网站会员数量</p>
            <p class="text-3xl font-bold">{{ stats.memberCount }}</p>
          </div>
          <div class="bg-purple-400 bg-opacity-30 rounded-full p-3">
            <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"></path>
            </svg>
          </div>
        </div>
      </div>
    </div>

    <!-- 快捷操作 -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <div class="bg-white rounded-lg border border-gray-200 p-6">
        <h3 class="text-lg font-semibold text-gray-900 mb-4">快捷操作</h3>
        <div class="space-y-3">
          <router-link to="/admin/user-management" class="flex items-center p-3 bg-blue-50 hover:bg-blue-100 rounded-lg transition-colors">
            <div class="bg-blue-500 rounded-full p-2 mr-3">
              <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197m13.5-9a2.5 2.5 0 11-5 0 2.5 2.5 0 015 0z"></path>
              </svg>
            </div>
            <div>
              <p class="font-medium text-gray-900">管理用户信息</p>
              <p class="text-sm text-gray-500">查看和管理系统用户</p>
            </div>
          </router-link>

          <router-link to="/admin/job-management" class="flex items-center p-3 bg-green-50 hover:bg-green-100 rounded-lg transition-colors">
            <div class="bg-green-500 rounded-full p-2 mr-3">
              <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
              </svg>
            </div>
            <div>
              <p class="font-medium text-gray-900">管理招聘信息</p>
              <p class="text-sm text-gray-500">查看和编辑现有职位</p>
            </div>
          </router-link>

          <router-link to="/" class="flex items-center p-3 bg-purple-50 hover:bg-purple-100 rounded-lg transition-colors">
            <div class="bg-purple-500 rounded-full p-2 mr-3">
              <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>
              </svg>
            </div>
            <div>
              <p class="font-medium text-gray-900">查看前台页面</p>
              <p class="text-sm text-gray-500">预览用户看到的页面</p>
            </div>
          </router-link>
        </div>
      </div>

      <div class="bg-white rounded-lg border border-gray-200 p-6">
        <h3 class="text-lg font-semibold text-gray-900 mb-4">最近活动</h3>
        <div class="space-y-4">
          <div v-for="activity in recentActivities" :key="activity.id" class="flex items-start">
            <div class="flex-shrink-0">
              <div :class="[
                'w-2 h-2 rounded-full mt-2',
                activity.type === 'add' ? 'bg-green-400' : 
                activity.type === 'edit' ? 'bg-blue-400' : 'bg-red-400'
              ]"></div>
            </div>
            <div class="ml-3 flex-1">
              <p class="text-sm text-gray-900">{{ activity.description }}</p>
              <p class="text-xs text-gray-500">{{ activity.time }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { jobInfoApi } from '@/api/jobInfo'
import { listUserByPage } from '@/api/user'

const currentTime = ref('')
const stats = ref({
  userCount: 0,
  jobCount: 0,
  totalViews: 12580,
  memberCount: 0
})

const recentActivities = ref([
  {
    id: 1,
    type: 'add',
    description: '新用户注册：张三',
    time: '2小时前'
  },
  {
    id: 2,
    type: 'edit',
    description: '更新了字节跳动产品经理职位信息',
    time: '4小时前'
  },
  {
    id: 3,
    type: 'add',
    description: '添加了新的招聘信息：阿里巴巴前端开发',
    time: '1天前'
  }
])

const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

const fetchStats = async () => {
  try {
    // 获取招聘信息数量
    const jobResponse = await jobInfoApi.getList({ pageSize: 1000 })
    const jobs = jobResponse.data.list || []
    stats.value.jobCount = jobs.length
    
    // 获取用户数量
    const userResponse = await listUserByPage({ pageSize: 1000 })
    const users = userResponse.data.list || []
    stats.value.userCount = users.length
    
    // 计算会员数量（userRole为0或2的用户为会员）
    stats.value.memberCount = users.filter(user => user.userRole === 0 || user.userRole === 2).length
    
    // 模拟浏览量增长
    stats.value.totalViews = 12580 + Math.floor(Math.random() * 100)
  } catch (error) {
    console.error('获取统计数据失败:', error)
  }
}

onMounted(() => {
  updateTime()
  setInterval(updateTime, 1000)
  fetchStats()
})
</script>