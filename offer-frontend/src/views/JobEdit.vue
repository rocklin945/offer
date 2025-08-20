<template>
  <div class="max-w-4xl mx-auto">
    <div class="mb-6">
      <h2 class="text-2xl font-bold text-gray-900">编辑招聘信息</h2>
      <p class="mt-1 text-sm text-gray-600">修改招聘信息内容</p>
    </div>

    <div v-if="loading && !form.companyName" class="text-center py-8">
      <div class="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-primary-500"></div>
      <p class="mt-2 text-gray-500">加载中...</p>
    </div>

    <form v-else @submit.prevent="handleSubmit" class="space-y-6">
      <div class="card">
        <h3 class="text-lg font-medium text-gray-900 mb-4">基本信息</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">
              公司名称 <span class="text-red-500">*</span>
            </label>
            <input
              v-model="form.companyName"
              type="text"
              required
              placeholder="请输入公司名称"
              class="input-field"
              :class="{ 'border-red-500': errors.companyName }"
            />
            <p v-if="errors.companyName" class="mt-1 text-sm text-red-600">{{ errors.companyName }}</p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">公司类型</label>
            <select v-model="form.companyType" class="input-field">
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
            <label class="block text-sm font-medium text-gray-700 mb-1">所属行业</label>
            <input
              v-model="form.industry"
              type="text"
              placeholder="请输入所属行业"
              class="input-field"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">招聘类型</label>
            <select v-model="form.recruitType" class="input-field">
              <option value="">请选择招聘类型</option>
              <option value="校招">校招</option>
              <option value="社招">社招</option>
              <option value="实习">实习</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">工作地点</label>
            <input
              v-model="form.workLocation"
              type="text"
              placeholder="请输入工作地点"
              class="input-field"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">招聘对象</label>
            <input
              v-model="form.recruitTarget"
              type="text"
              placeholder="如：2025年毕业"
              class="input-field"
            />
          </div>
        </div>
      </div>

      <div class="card">
        <h3 class="text-lg font-medium text-gray-900 mb-4">职位信息</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">岗位名称</label>
            <input
              v-model="form.positionName"
              type="text"
              placeholder="请输入岗位名称"
              class="input-field"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">投递进度</label>
            <select v-model="form.applicationStatus" class="input-field">
              <option value="">请选择投递进度</option>
              <option value="未投递">未投递</option>
              <option value="已投递">已投递</option>
              <option value="面试中">面试中</option>
              <option value="已通过">已通过</option>
              <option value="已拒绝">已拒绝</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">开始时间</label>
            <input
              v-model="form.startTime"
              type="date"
              class="input-field"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">投递截止</label>
            <input
              v-model="form.deadline"
              type="text"
              placeholder="如：尽快投递 或 2025.12.31截止"
              class="input-field"
            />
          </div>
        </div>
      </div>

      <div class="card">
        <h3 class="text-lg font-medium text-gray-900 mb-4">其他信息</h3>
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">相关链接</label>
            <input
              v-model="form.relatedLink"
              type="url"
              placeholder="请输入相关链接"
              class="input-field"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">内推码</label>
            <input
              v-model="form.referralCode"
              type="text"
              placeholder="请输入内推码"
              class="input-field"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">招聘公告</label>
            <textarea
              v-model="form.announcement"
              rows="4"
              placeholder="请输入招聘公告内容"
              class="input-field resize-none"
            ></textarea>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">备注</label>
            <textarea
              v-model="form.remark"
              rows="3"
              placeholder="请输入备注信息"
              class="input-field resize-none"
            ></textarea>
          </div>
        </div>
      </div>

      <div class="flex justify-end space-x-4">
        <router-link
          to="/"
          class="btn-secondary"
        >
          取消
        </router-link>
        <button
          type="submit"
          :disabled="submitLoading"
          class="btn-primary disabled:opacity-50 disabled:cursor-not-allowed"
        >
          <span v-if="submitLoading" class="inline-block animate-spin rounded-full h-4 w-4 border-b-2 border-white mr-2"></span>
          {{ submitLoading ? '更新中...' : '更新' }}
        </button>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { jobInfoApi } from '@/api/jobInfo'
import type { JobInfoUpdateRequest } from '@/api/types'

const router = useRouter()
const route = useRoute()

// 获取路由参数
const jobId = Number(route.params.id)

// 响应式数据
const loading = ref(false)
const submitLoading = ref(false)
const form = reactive<JobInfoUpdateRequest>({
  id: jobId,
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

const errors = reactive({
  companyName: ''
})

// 加载招聘信息详情
const loadJobInfo = async () => {
  loading.value = true
  try {
    const response = await jobInfoApi.getById(jobId)
    const jobInfo = response.data
    if (jobInfo) {
      // 填充表单数据
      Object.keys(form).forEach(key => {
        if (key !== 'id' && jobInfo[key as keyof typeof jobInfo] !== undefined) {
          form[key as keyof JobInfoUpdateRequest] = jobInfo[key as keyof typeof jobInfo] as any
        }
      })
      
      // 处理日期格式
      if (jobInfo.startTime) {
        const date = new Date(jobInfo.startTime)
        form.startTime = date.toISOString().split('T')[0]
      }
    }
  } catch (error) {
    alert('加载招聘信息失败')
    router.push('/')
  } finally {
    loading.value = false
  }
}

// 表单验证
const validateForm = () => {
  errors.companyName = ''
  
  if (!form.companyName.trim()) {
    errors.companyName = '公司名称不能为空'
    return false
  }
  
  return true
}

// 提交表单
const handleSubmit = async () => {
  if (!validateForm()) {
    return
  }
  
  submitLoading.value = true
  
  try {
    // 处理日期格式
    const submitData = { ...form }
    if (submitData.startTime) {
      submitData.startTime = new Date(submitData.startTime).toISOString()
    }
    
    const response = await jobInfoApi.update(submitData)
    if (response.data) {
      alert('更新成功')
      router.push('/')
    }
  } catch (error) {
    alert('更新失败，请重试')
    console.error('更新招聘信息失败:', error)
  } finally {
    submitLoading.value = false
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