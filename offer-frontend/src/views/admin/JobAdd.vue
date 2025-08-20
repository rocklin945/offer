<template>
  <div class="space-y-6">
    <!-- 页面标题 -->
    <div class="flex items-center justify-between">
      <div class="flex items-center">
        <router-link to="/admin/job-management" class="text-gray-500 hover:text-gray-700 mr-4">
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path>
          </svg>
        </router-link>
        <h2 class="text-2xl font-bold text-gray-900">添加招聘信息</h2>
      </div>
    </div>

    <!-- 表单 -->
    <div class="bg-white rounded-lg border border-gray-200 p-6">
      <form @submit.prevent="handleSubmit" class="space-y-6">
        <!-- 基本信息 -->
        <div>
          <h3 class="text-lg font-medium text-gray-900 mb-4">基本信息</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">
                公司名称 <span class="text-red-500">*</span>
              </label>
              <input v-model="form.companyName" type="text" required
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                placeholder="请输入公司名称" />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">公司类型</label>
              <select v-model="form.companyType"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                <option value="">请选择公司类型</option>
                <option value="国企">国企</option>
                <option value="外企">外企</option>
                <option value="民企">民企</option>
                <option value="合资">合资</option>
                <option value="事业单位">事业单位</option>
                <option value="其他">其他</option>
              </select>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">所属行业</label>
              <input v-model="form.industry" type="text"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                placeholder="请输入所属行业" />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">招聘类型</label>
              <select v-model="form.recruitType"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                <option value="">请选择招聘类型</option>
                <option value="校招">校招</option>
                <option value="社招">社招</option>
                <option value="实习">实习</option>
              </select>
            </div>
          </div>
        </div>

        <!-- 职位信息 -->
        <div>
          <h3 class="text-lg font-medium text-gray-900 mb-4">职位信息</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">岗位名称</label>
              <input v-model="form.positionName" type="text"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                placeholder="请输入岗位名称" />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">工作地点</label>
              <input v-model="form.workLocation" type="text"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                placeholder="请输入工作地点" />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">招聘对象</label>
              <input v-model="form.recruitTarget" type="text"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                placeholder="如：2025年毕业生" />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">投递进度</label>
              <select v-model="form.applicationStatus"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                <option value="">请选择投递进度</option>
                <option value="未投递">未投递</option>
                <option value="已投递">已投递</option>
                <option value="面试中">面试中</option>
                <option value="已通过">已通过</option>
                <option value="已拒绝">已拒绝</option>
              </select>
            </div>
          </div>
        </div>

        <!-- 时间信息 -->
        <div>
          <h3 class="text-lg font-medium text-gray-900 mb-4">时间信息</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">开始时间</label>
              <input v-model="form.startTime" type="date"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">投递截止</label>
              <input v-model="form.deadline" type="text"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                placeholder="请输入截止时间" />
            </div>
          </div>
        </div>

        <!-- 链接信息 -->
        <div>
          <h3 class="text-lg font-medium text-gray-900 mb-4">链接信息</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">相关链接</label>
              <input v-model="form.relatedLink" type="url"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                placeholder="请输入相关链接" />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">招聘公告</label>
              <input v-model="form.announcement" type="url"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                placeholder="请输入招聘公告链接" />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">内推码</label>
              <input v-model="form.referralCode" type="text"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                placeholder="请输入内推码" />
            </div>
          </div>
        </div>

        <!-- 备注 -->
        <div>
          <h3 class="text-lg font-medium text-gray-900 mb-4">备注信息</h3>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">备注</label>
            <textarea v-model="form.remark" rows="4"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              placeholder="请输入备注信息"></textarea>
          </div>
        </div>

        <!-- 操作按钮 -->
        <div class="flex justify-end space-x-4 pt-6 border-t border-gray-200">
          <router-link to="/admin/job-management"
            class="px-6 py-2 text-gray-600 bg-gray-100 hover:bg-gray-200 rounded-md transition-colors">
            取消
          </router-link>
          <button type="submit" :disabled="loading"
            class="px-6 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md transition-colors disabled:opacity-50 disabled:cursor-not-allowed">
            <span v-if="loading">保存中...</span>
            <span v-else>保存</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { jobInfoApi } from '@/api/jobInfo'
import type { JobInfoAddRequest } from '@/api/types'

const router = useRouter()
const loading = ref(false)

const form = reactive<JobInfoAddRequest>({
  companyName: '',
  companyType: '',
  industry: '',
  recruitType: '',
  workLocation: '',
  recruitTarget: '',
  positionName: '',
  applicationStatus: '',
  startTime: '',
  deadline: '',
  relatedLink: '',
  announcement: '',
  referralCode: '',
  remark: ''
})

const handleSubmit = async () => {
  if (!form.companyName.trim()) {
    alert('请输入公司名称')
    return
  }

  loading.value = true
  try {
    const submitData = { ...form }
    
    // 处理日期格式
    if (submitData.startTime) {
      submitData.startTime = new Date(submitData.startTime).toISOString()
    }

    // 过滤空值
    Object.keys(submitData).forEach(key => {
      if (submitData[key as keyof JobInfoAddRequest] === '') {
        delete submitData[key as keyof JobInfoAddRequest]
      }
    })

    const response = await jobInfoApi.add(submitData)
    
    if (response.data) {
      alert('添加成功')
      router.push('/admin/job-management')
    }
  } catch (error) {
    console.error('添加失败:', error)
    alert('添加失败，请重试')
  } finally {
    loading.value = false
  }
}
</script>