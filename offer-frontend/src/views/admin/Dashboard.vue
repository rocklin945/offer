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
            <p class="text-blue-100 text-sm">总招聘信息</p>
            <p class="text-3xl font-bold">{{ stats.total }}</p>
          </div>
          <div class="bg-blue-400 bg-opacity-30 rounded-full p-3">
            <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-gradient-to-r from-green-500 to-green-600 rounded-lg p-6 text-white">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-green-100 text-sm">已投递</p>
            <p class="text-3xl font-bold">{{ stats.applied }}</p>
          </div>
          <div class="bg-green-400 bg-opacity-30 rounded-full p-3">
            <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-gradient-to-r from-yellow-500 to-yellow-600 rounded-lg p-6 text-white">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-yellow-100 text-sm">面试中</p>
            <p class="text-3xl font-bold">{{ stats.interviewing }}</p>
          </div>
          <div class="bg-yellow-400 bg-opacity-30 rounded-full p-3">
            <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-gradient-to-r from-purple-500 to-purple-600 rounded-lg p-6 text-white">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-purple-100 text-sm">已通过</p>
            <p class="text-3xl font-bold">{{ stats.passed }}</p>
          </div>
          <div class="bg-purple-400 bg-opacity-30 rounded-full p-3">
            <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
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
          <router-link to="/admin/job-add" class="flex items-center p-3 bg-blue-50 hover:bg-blue-100 rounded-lg transition-colors">
            <div class="bg-blue-500 rounded-full p-2 mr-3">
              <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6"></path>
              </svg>
            </div>
            <div>
              <p class="font-medium text-gray-900">添加招聘信息</p>
              <p class="text-sm text-gray-500">发布新的招聘职位</p>
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

const currentTime = ref('')
const stats = ref({
  total: 0,
  applied: 0,
  interviewing: 0,
  passed: 0
})

const recentActivities = ref([
  {
    id: 1,
    type: 'add',
    description: '添加了新的招聘信息：腾讯游戏技术岗位',
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
    const response = await jobInfoApi.getList({ pageSize: 1000 })
    const jobs = response.data.list || []
    
    stats.value.total = jobs.length
    stats.value.applied = jobs.filter(job => job.applicationStatus === '已投递').length
    stats.value.interviewing = jobs.filter(job => job.applicationStatus === '面试中').length
    stats.value.passed = jobs.filter(job => job.applicationStatus === '已通过').length
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