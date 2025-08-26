<template>
  <div class="space-y-6">
    <!-- 页面标题和操作按钮 -->
    <div class="flex justify-between items-center">
      <h2 class="text-2xl font-bold text-gray-900">招聘信息管理</h2>
      <div class="flex flex-col items-end space-y-2">
        <div class="flex space-x-3">
          <router-link to="/admin/job-management/add"
            class="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg flex items-center transition-colors">
            <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6">
              </path>
            </svg>
            添加招聘信息
          </router-link>
          <button @click="triggerFileInput" :disabled="isUploading"
            class="bg-green-500 hover:bg-green-600 disabled:bg-green-400 disabled:cursor-not-allowed text-white px-4 py-2 rounded-lg flex items-center transition-colors">
            <svg v-if="isUploading" class="w-5 h-5 mr-2 animate-spin" fill="none" stroke="currentColor"
              viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15">
              </path>
            </svg>
            <svg v-else class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path>
            </svg>
            {{ isUploading ? '正在导入...' : '批量导入Excel' }}
          </button>
          <!-- 隐藏的文件输入 -->
          <input ref="fileInput" type="file" accept=".xlsx,.xls" @change="handleFileSelect" style="display: none;" />
        </div>
        <!-- 下载模板链接 -->
        <a @click="downloadTemplate"
          class="text-blue-600 hover:text-blue-800 text-sm cursor-pointer flex items-center transition-colors">
          <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M12 10v6m0 0l-3-3m3 3l3-3M3 17V7a2 2 0 012-2h6l2 2h6a2 2 0 012 2v10a2 2 0 01-2 2H5a2 2 0 01-2-2z">
            </path>
          </svg>
          下载导入表格模版
        </a>
      </div>
    </div>

    <!-- 搜索筛选区域 -->
    <div class="bg-white rounded-lg border border-gray-200 p-6">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">公司名称</label>
          <input v-model="searchForm.companyName" type="text" placeholder="请输入公司名称"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">公司类型</label>
          <select v-model="searchForm.companyType"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="">全部</option>
            <option value="央国企">央国企</option>
            <option value="银行">银行</option>
            <option value="外企">外企</option>
            <option value="民企">民企</option>
            <option value="事业单位">事业单位</option>
            <option value="其他">其他</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">所属行业</label>
          <input v-model="searchForm.industry" type="text" placeholder="请输入所属行业"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">招聘类型</label>
          <select v-model="searchForm.recruitType"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="">全部</option>
            <option value="实习">实习</option>
            <option value="春招">春招</option>
            <option value="秋招">秋招</option>
            <option value="补录">补录</option>
            <option value="提前批">提前批</option>
            <option value="社招">社招</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">工作地点</label>
          <input v-model="searchForm.workLocation" type="text" placeholder="请输入工作地点"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">招聘对象</label>
          <input v-model="searchForm.recruitTarget" type="text" placeholder="如：2026年毕业"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">岗位名称</label>
          <input v-model="searchForm.positionName" type="text" placeholder="请输入岗位名称"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">开始时间</label>
          <input v-model="searchForm.startTime" type="date"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">投递截止</label>
          <input v-model="searchForm.deadline" type="text" placeholder="请输入截止时间"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">排序字段</label>
          <select v-model="searchForm.sortField"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="">默认排序</option>
            <option value="companyName">公司名称</option>
            <option value="recruitType">招聘类型</option>
            <option value="workLocation">工作地点</option>
            <option value="positionName">岗位名称</option>
            <option value="applicationStatus">投递进度</option>
            <option value="startTime">开始时间</option>
            <option value="deadline">投递截止</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">排序方式</label>
          <select v-model="searchForm.sortOrder"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="desc">从大到小</option>
            <option value="asc">从小到大</option>
          </select>
        </div>
      </div>
      <div class="flex justify-end space-x-2">
        <button @click="resetSearch"
          class="px-4 py-2 text-gray-600 bg-gray-100 hover:bg-gray-200 rounded-md transition-colors">
          重置
        </button>
        <button @click="handleSearch"
          class="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md transition-colors">
          搜索
        </button>
      </div>
    </div>

    <!-- 数据表格 -->
    <div class="bg-white rounded-lg border border-gray-200">
      <div v-if="loading" class="text-center py-12">
        <div class="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
        <p class="mt-2 text-gray-500">加载中...</p>
      </div>

      <div v-else-if="!hasJobs" class="text-center py-12">
        <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
            d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2">
          </path>
        </svg>
        <p class="mt-2 text-gray-500">暂无数据</p>
      </div>

      <div v-else class="overflow-x-auto">
        <div class="table-container" :class="{ 'table-loading': isChangingPage }">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  公司信息
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  岗位信息
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  招聘对象
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  招聘类型
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  投递进度
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  开始时间
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  投递截止
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  操作
                </th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="(job, index) in jobList" :key="job.id" class="hover:bg-gray-50">
                <td class="px-4 py-4 text-center">
                  <div>
                    <div class="text-sm font-medium text-gray-900">{{ job.companyName }}</div>
                    <div class="text-sm text-gray-500">{{ job.companyType || '-' }}</div>
                    <div class="text-sm text-gray-500">{{ job.industry || '-' }}</div>
                  </div>
                </td>
                <td class="px-4 py-4 text-center">
                  <div>
                    <div class="text-sm font-medium text-gray-900">{{ job.positionName || '-' }}</div>
                    <div class="text-sm text-gray-500">{{ job.workLocation || '-' }}</div>
                  </div>
                </td>
                <td class="px-4 py-4 text-center text-sm text-gray-900">
                  {{ job.recruitTarget || '-' }}
                </td>
                <td class="px-4 py-4 text-center">
                  <span class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                    :class="getRecruitTypeClass(job.recruitType)">
                    {{ job.recruitType || '-' }}
                  </span>
                </td>
                <td class="px-4 py-4 text-center">
                  <span class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                    :class="getStatusClass(job.applicationStatus)">
                    {{ job.applicationStatus || '-' }}
                  </span>
                </td>
                <td class="px-4 py-4 text-center text-sm text-gray-900">
                  {{ formatDate(job.startTime) || '-' }}
                </td>
                <td class="px-4 py-4 text-center text-sm text-gray-900">
                  {{ job.deadline || '-' }}
                </td>
                <td class="px-4 py-4 text-center">
                  <div class="flex justify-center space-x-2">
                    <router-link :to="`/admin/job-management/edit/${job.id}`"
                      class="text-blue-600 hover:text-blue-900 text-sm font-medium">
                      编辑
                    </router-link>
                    <button @click="handleDelete(job.id!)" class="text-red-600 hover:text-red-900 text-sm font-medium">
                      删除
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 分页 -->
      <div v-if="hasJobs" class="bg-white px-4 py-3 border-t border-gray-200 sm:px-6">
        <div class="flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
          <div class="text-sm text-gray-700">
            共 {{ total }} 条记录
          </div>
          <div class="flex flex-wrap items-center gap-2 sm:flex-nowrap sm:space-x-2">
            <!-- 上一页 -->
            <button @click="handlePageChange(currentPage - 1)" :disabled="currentPage <= 1"
              class="px-2 py-1 text-xs sm:px-3 sm:py-2 sm:text-sm border border-gray-300 rounded hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:bg-white">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path>
              </svg>
            </button>

            <!-- 页码按钮 -->
            <template v-for="page in getPageNumbers()" :key="page">
              <button v-if="page === '...'" disabled class="px-2 py-1 text-xs sm:px-3 sm:py-2 sm:text-sm text-gray-400 cursor-default">
                ...
              </button>
              <button v-else @click="handlePageChange(page as number)" :class="[
                'px-2 py-1 text-xs sm:px-3 sm:py-2 sm:text-sm border rounded transition-colors',
                currentPage === page
                  ? 'bg-blue-600 text-white border-blue-600'
                  : 'border-gray-300 hover:bg-gray-50'
              ]">
                {{ page }}
              </button>
            </template>

            <!-- 下一页 -->
            <button @click="handlePageChange(currentPage + 1)" :disabled="currentPage >= totalPages"
              class="px-2 py-1 text-xs sm:px-3 sm:py-2 sm:text-sm border border-gray-300 rounded hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:bg-white">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
              </svg>
            </button>

            <!-- 跳转到指定页 -->
            <div class="flex items-center gap-2 ml-0 sm:ml-4 w-full sm:w-auto flex-wrap">
              <span class="text-sm text-gray-700">跳至</span>
              <input v-model="jumpPage" @keyup.enter="handleJumpPage" type="number" min="1" :max="totalPages"
                class="w-14 sm:w-16 px-2 py-1 text-xs sm:text-sm border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
              <span class="text-sm text-gray-700">页</span>
              <button @click="handleJumpPage"
                :disabled="!jumpPage || Number(jumpPage) < 1 || Number(jumpPage) > totalPages"
                class="px-2 py-1 text-xs sm:px-3 sm:py-1 sm:text-sm bg-blue-600 text-white rounded hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:bg-blue-600">
                跳转
              </button>
            </div>

            <!-- 每页显示条数 -->
            <div class="flex items-center gap-2 ml-0 sm:ml-4 flex-wrap">
              <span class="text-sm text-gray-700">每页</span>
              <select v-model="pageSize" @change="handlePageSizeChange"
                class="px-2 py-1 text-xs sm:text-sm border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                <option :value="10">10</option>
                <option :value="20">20</option>
                <option :value="50">50</option>
                <option :value="100">100</option>
              </select>
              <span class="text-sm text-gray-700">条</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { jobInfoApi } from '@/api/jobInfo'
import type { JobInfoQueryRequest, JobInfo } from '@/api/types'
import Message from '@/components/Message'
import Confirm from '@/components/Confirm'

// 响应式数据
const currentPage = ref(1)
const pageSize = ref(10)
const jobList = ref<JobInfo[]>([])
const total = ref(0)
const loading = ref(false)
const jumpPage = ref<number | string>('')
const isChangingPage = ref(false)
const fileInput = ref<HTMLInputElement>()
const isUploading = ref(false)

const searchForm = reactive<JobInfoQueryRequest>({
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
  sortField: '',
  sortOrder: 'desc'
})

// 计算属性
const hasJobs = computed(() => jobList.value.length > 0)
const totalPages = computed(() => Math.ceil(total.value / pageSize.value))

// 方法
const handleSearch = async () => {
  currentPage.value = 1
  await fetchData()
}

const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key as keyof JobInfoQueryRequest] = ''
  })
  searchForm.sortOrder = 'desc'
  handleSearch()
}

// 文件导入相关方法
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click()
  }
}

const handleFileSelect = async (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]

  if (!file) {
    console.log('没有选中文件')
    return
  }

  // 检查文件类型
  const allowedTypes = ['.xlsx', '.xls']
  const fileExtension = file.name.toLowerCase().substring(file.name.lastIndexOf('.'))
  console.log('文件扩展名:', fileExtension)

  if (!allowedTypes.includes(fileExtension)) {
    Message.error('仅支持Excel格式文件（.xlsx或.xls）')
    target.value = '' // 清空文件输入
    return
  }

  // 检查文件大小（限制6410MB）
  const maxSize = 10 * 1024 * 1024 // 10MB
  if (file.size > maxSize) {
    Message.error('文件大小不能超过10MB')
    target.value = ''
    return
  }

  console.log('开始上传文件:', file.name)
  await handleFileUpload(file)
  target.value = '' // 清空文件输入
}

const handleFileUpload = async (file: File) => {
  const confirmed = await Confirm.info(
    `确定要导入文件「${file.name}」吗？\n\n注意：系统会根据“公司名称+招聘类型+招聘对象”进行去重处理。`,
    '批量导入确认'
  )

  if (!confirmed) {
    console.log('用户取消了导入')
    return
  }

  isUploading.value = true

  try {
    const response = await jobInfoApi.batchImport(file)
    Message.success(response.data || '导入成功')
    // 刷新列表数据
    await fetchData()
  } catch (error: any) {
    console.error('批量导入失败:', error)
    // 错误信息已在request.ts中处理
  } finally {
    isUploading.value = false
  }
}

// 下载模板文件
const downloadTemplate = () => {
  // 创建一个隐藏的a标签来下载文件
  const link = document.createElement('a')
  // 使用正确的静态文件路径
  link.href = '/templates/导入表格模版.xlsx'
  link.download = '招聘信息导入模板.xlsx'
  link.style.display = 'none'

  // 添加到DOM中并点击
  document.body.appendChild(link)
  link.click()

  // 清理DOM
  document.body.removeChild(link)

  Message.success('模板下载已开始，请检查浏览器下载文件夹')
}

const handlePageChange = async (page: number) => {
  if (page >= 1 && page <= totalPages.value && !isChangingPage.value) {
    isChangingPage.value = true
    currentPage.value = page

    // 添加动画延迟
    await new Promise(resolve => setTimeout(resolve, 300))

    await fetchData()
    isChangingPage.value = false
  }
}

const handleJumpPage = async () => {
  const page = Number(jumpPage.value)
  if (page >= 1 && page <= totalPages.value && !isChangingPage.value) {
    isChangingPage.value = true
    currentPage.value = page
    jumpPage.value = ''

    // 添加动画延迟
    await new Promise(resolve => setTimeout(resolve, 300))

    await fetchData()
    isChangingPage.value = false
  }
}

const handlePageSizeChange = async () => {
  if (!isChangingPage.value) {
    isChangingPage.value = true
    currentPage.value = 1

    // 添加动画延迟
    await new Promise(resolve => setTimeout(resolve, 300))

    await fetchData()
    isChangingPage.value = false
  }
}

const getPageNumbers = () => {
  const pages: (number | string)[] = []
  const total = totalPages.value
  const current = currentPage.value

  if (total <= 7) {
    // 总页数小于等于7，显示所有页码
    for (let i = 1; i <= total; i++) {
      pages.push(i)
    }
  } else {
    // 总页数大于7，需要省略
    if (current <= 4) {
      // 当前页在前面
      for (let i = 1; i <= 5; i++) {
        pages.push(i)
      }
      pages.push('...')
      pages.push(total)
    } else if (current >= total - 3) {
      // 当前页在后面
      pages.push(1)
      pages.push('...')
      for (let i = total - 4; i <= total; i++) {
        pages.push(i)
      }
    } else {
      // 当前页在中间
      pages.push(1)
      pages.push('...')
      for (let i = current - 1; i <= current + 1; i++) {
        pages.push(i)
      }
      pages.push('...')
      pages.push(total)
    }
  }

  return pages
}

const fetchData = async () => {
  loading.value = true
  try {
    const params: JobInfoQueryRequest = {
      ...searchForm,
      pageNum: currentPage.value,
      pageSize: pageSize.value
    }

    // 过滤空值
    Object.keys(params).forEach(key => {
      if (params[key as keyof JobInfoQueryRequest] === '' || params[key as keyof JobInfoQueryRequest] === null) {
        delete params[key as keyof JobInfoQueryRequest]
      }
    })

    const response = await jobInfoApi.getList(params)
    jobList.value = response.data.list || []
    total.value = response.data.total || 0
  } catch (error: any) {
    console.error('获取招聘信息列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id: string) => {
  const confirmed = await Confirm.danger('确定要删除这条招聘信息吗？删除后无法恢复。', '删除确认')

  if (confirmed) {
    try {
      const response = await jobInfoApi.delete(id)
      if (response.data) {
        Message.success(response.message || '删除成功')
        await fetchData()
      }
    } catch (error: any) {
      console.error('删除失败:', error)
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
    case '秋招':
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
  fetchData()
})
</script>

<style scoped>
/* 表格容器动画 */
.table-container {
  transition: all 0.5s cubic-bezier(0.25, 0.8, 0.25, 1);
  transform: translateY(0);
  opacity: 1;
}

.table-container.table-loading {
  opacity: 0.3;
  transform: translateY(10px);
  pointer-events: none;
}

/* 表格行动画 */
.table-container tbody tr {
  transition: all 0.3s ease;
}

.table-container.table-loading tbody tr {
  opacity: 0.5;
  transform: translateX(-5px);
}
</style>