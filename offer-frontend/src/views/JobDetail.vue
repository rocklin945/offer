<template>
  <div class="max-w-4xl mx-auto">
    <div class="mb-6 flex justify-between items-center">
      <div>
        <h2 class="text-2xl font-bold text-gray-900">招聘信息详情</h2>
        <p class="mt-1 text-sm text-gray-600">查看完整的招聘信息</p>
      </div>
      <div class="flex space-x-2">
        <router-link
          :to="`/edit/${jobId}`"
          class="btn-primary"
        >
          编辑
        </router-link>
        <router-link
          to="/"
          class="btn-secondary"
        >
          返回列表
        </router-link>
      </div>
    </div>

    <div v-if="loading" class="text-center py-8">
      <div class="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-primary-500"></div>
      <p class="mt-2 text-gray-500">加载中...</p>
    </div>

    <div v-else-if="jobInfo" class="space-y-6">
      <!-- 基本信息 -->
      <div class="card">
        <h3 class="text-lg font-medium text-gray-900 mb-4">基本信息</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">公司名称</label>
            <p class="text-sm text-gray-900">{{ jobInfo.companyName || '-' }}</p>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">公司类型</label>
            <p class="text-sm text-gray-900">{{ jobInfo.companyType || '-' }}</p>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">所属行业</label>
            <p class="text-sm text-gray-900">{{ jobInfo.industry || '-' }}</p>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">招聘类型</label>
            <span v-if="jobInfo.recruitType" 
                  class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                  :class="getRecruitTypeClass(jobInfo.recruitType)">
              {{ jobInfo.recruitType }}
            </span>
            <p v-else class="text-sm text-gray-900">-</p>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">工作地点</label>
            <p class="text-sm text-gray-900">{{ jobInfo.workLocation || '-' }}</p>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">招聘对象</label>
            <p class="text-sm text-gray-900">{{ jobInfo.recruitTarget || '-' }}</p>
          </div>
        </div>
      </div>

      <!-- 职位信息 -->
      <div class="card">
        <h3 class="text-lg font-medium text-gray-900 mb-4">职位信息</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">岗位名称</label>
            <p class="text-sm text-gray-900">{{ jobInfo.positionName || '-' }}</p>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">投递进度</label>
            <span v-if="jobInfo.applicationStatus" 
                  class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                  :class="getStatusClass(jobInfo.applicationStatus)">
              {{ jobInfo.applicationStatus }}
            </span>
            <p v-else class="text-sm text-gray-900">-</p>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">开始时间</label>
            <p class="text-sm text-gray-900">{{ formatDate(jobInfo.startTime) || '-' }}</p>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">投递截止</label>
            <p class="text-sm text-gray-900">{{ jobInfo.deadline || '-' }}</p>
          </div>
        </div>
      </div>

      <!-- 其他信息 -->
      <div class="card">
        <h3 class="text-lg font-medium text-gray-900 mb-4">其他信息</h3>
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">相关链接</label>
            <div class="text-sm text-gray-900">
              <a v-if="jobInfo.relatedLink" 
                 :href="jobInfo.relatedLink" 
                 target="_blank" 
                 class="text-primary-600 hover:text-primary-800 underline">
                {{ jobInfo.relatedLink }}
              </a>
              <span v-else>-</span>
            </div>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">内推码</label>
            <div class="text-sm text-gray-900">
              <span v-if="jobInfo.referralCode" 
                    class="bg-gray-100 px-2 py-1 rounded font-mono">
                {{ jobInfo.referralCode }}
              </span>
              <span v-else>-</span>
            </div>
          </div>
          <div v-if="jobInfo.announcement">
            <label class="block text-sm font-medium text-gray-700 mb-1">招聘公告</label>
            <div class="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg whitespace-pre-wrap">
              {{ jobInfo.announcement }}
            </div>
          </div>
          <div v-if="jobInfo.remark">
            <label class="block text-sm font-medium text-gray-700 mb-1">备注</label>
            <div class="text-sm text-gray-900 bg-gray-50 p-3 rounded-lg whitespace-pre-wrap">
              {{ jobInfo.remark }}
            </div>
          </div>
        </div>
      </div>

      <!-- 操作按钮 -->
      <div class="card">
        <div class="flex justify-between items-center">
          <div class="text-sm text-gray-500">
            ID: {{ jobInfo.id }}
          </div>
          <div class="flex space-x-2">
            <button
              @click="handleDelete"
              class="text-red-600 hover:text-red-800 text-sm font-medium"
            >
              删除此招聘信息
            </button>
          </div>
        </div>
      </div>
    </div>

    <div v-else class="text-center py-8">
      <p class="text-gray-500">招聘信息不存在</p>
      <router-link to="/" class="btn-primary mt-4 inline-block">
        返回列表
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { jobInfoApi } from '@/api/jobInfo'
import type { JobInfo } from '@/api/types'

const router = useRouter()
const route = useRoute()

// 获取路由参数
const jobId = Number(route.params.id)

// 响应式数据
const loading = ref(false)
const jobInfo = ref<JobInfo | null>(null)

// 加载招聘信息详情
const loadJobInfo = async () => {
  loading.value = true
  try {
    const response = await jobInfoApi.getById(jobId)
    jobInfo.value = response.data
  } catch (error) {
    console.error('加载招聘信息失败:', error)
  } finally {
    loading.value = false
  }
}

// 删除招聘信息
const handleDelete = async () => {
  if (confirm('确定要删除这条招聘信息吗？此操作不可恢复。')) {
    try {
      const response = await jobInfoApi.delete(jobId)
      if (response.data) {
        alert('删除成功')
        router.push('/')
      }
    } catch (error) {
      alert('删除失败，请重试')
    }
  }
}

// 格式化日期
const formatDate = (dateString?: string) => {
  if (!dateString) return ''
  
  try {
    const date = new Date(dateString)
    return date.toLocaleDateString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit'
    })
  } catch (error) {
    return dateString
  }
}

// 样式类方法
const getRecruitTypeClass = (type?: string) => {
  switch (type) {
    case '校招':
      return 'bg-blue-100 text-blue-800'
    case '社招':
      return 'bg-green-100 text-green-800'
    case '实习':
      return 'bg-yellow-100 text-yellow-800'
    default:
      return 'bg-gray-100 text-gray-800'
  }
}

const getStatusClass = (status?: string) => {
  switch (status) {
    case '未投递':
      return 'bg-gray-100 text-gray-800'
    case '已投递':
      return 'bg-blue-100 text-blue-800'
    case '面试中':
      return 'bg-yellow-100 text-yellow-800'
    case '已通过':
      return 'bg-green-100 text-green-800'
    case '已拒绝':
      return 'bg-red-100 text-red-800'
    default:
      return 'bg-gray-100 text-gray-800'
  }
}

// 生命周期
onMounted(() => {
  if (!jobId || isNaN(jobId)) {
    alert('无效的招聘信息ID')
    router.push('/')
    return
  }
  
  loadJobInfo()
})
</script>