<template>
  <div class="space-y-6">
    <!-- 页面标题 -->
    <div class="text-center mb-8">
      <h1 class="text-4xl font-bold text-gray-900 mb-2">全网最全招聘信息</h1>
      <p class="text-gray-600">最新招聘信息汇总</p>
    </div>

    <!-- 搜索筛选区域 -->
    <div class="card">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">公司名称</label>
          <input v-model="searchForm.companyName" type="text" placeholder="请输入公司名称" class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">公司类型</label>
          <select v-model="searchForm.companyType" class="input-field">
            <option value="">全部</option>
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
          <input v-model="searchForm.industry" type="text" placeholder="请输入所属行业" class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">招聘类型</label>
          <select v-model="searchForm.recruitType" class="input-field">
            <option value="">全部</option>
            <option value="校招">校招</option>
            <option value="社招">社招</option>
            <option value="实习">实习</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">工作地点</label>
          <input v-model="searchForm.workLocation" type="text" placeholder="请输入工作地点" class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">招聘对象</label>
          <input v-model="searchForm.recruitTarget" type="text" placeholder="如：2025年毕业" class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">岗位名称</label>
          <input v-model="searchForm.positionName" type="text" placeholder="请输入岗位名称" class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">开始时间</label>
          <input v-model="searchForm.startTime" type="date" class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">投递截止</label>
          <input v-model="searchForm.deadline" type="text" placeholder="请输入截止时间" class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">排序字段</label>
          <select v-model="searchForm.sortField" class="input-field">
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
          <select v-model="searchForm.sortOrder" class="input-field">
            <option value="desc">从大到小</option>
            <option value="asc">从小到大</option>
          </select>
        </div>
      </div>
      <div class="flex justify-end space-x-2">
        <button @click="resetSearch" class="btn-secondary">重置</button>
        <button @click="handleSearch" class="btn-primary">搜索</button>
      </div>
    </div>

    <!-- 数据表格 -->
    <div class="card">
      <div v-if="loading" class="text-center py-8">
        <div class="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-primary-500"></div>
        <p class="mt-2 text-gray-500">加载中...</p>
      </div>

      <div v-else-if="!hasJobs" class="text-center py-8">
        <p class="text-gray-500">暂无数据</p>
      </div>

      <div v-else class="overflow-x-auto">
        <div class="table-container" :class="{ 'table-loading': isChangingPage }">
          <table class="min-w-full table-fixed divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th
                  class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-48 whitespace-nowrap">
                  公司信息
                </th>
                <th
                  class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-40 whitespace-nowrap">
                  招聘对象
                </th>
                <th
                  class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-48 whitespace-nowrap">
                  工作地点
                </th>
                <th
                  class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-64 whitespace-nowrap">
                  岗位信息
                </th>
                <th
                  class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-32 whitespace-nowrap">
                  招聘类型
                </th>
                <th
                  class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-36 whitespace-nowrap">
                  开始时间
                </th>
                <th
                  class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-36 whitespace-nowrap">
                  截止时间
                </th>
                <th
                  class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-32 whitespace-nowrap">
                  相关链接
                </th>
                <th
                  class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-32 whitespace-nowrap">
                  招聘公告
                </th>
                <th
                  class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-28 whitespace-nowrap">
                  内推码
                </th>
                <th
                  class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-44 whitespace-nowrap">
                  备注
                </th>
                <th
                  class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-32 whitespace-nowrap">
                  加入我的投递
                </th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="job in jobList" :key="job.id" class="hover:bg-gray-50">
                <td class="px-2 py-2 whitespace-nowrap text-center">
                  <div>
                    <div class="text-sm font-medium text-gray-900">{{ job.companyName }}</div>
                    <div class="text-sm text-gray-500">{{ job.companyType || '-' }}</div>
                    <div class="text-sm text-gray-500">{{ job.industry || '-' }}</div>
                  </div>
                </td>
                <td class="px-2 py-2 whitespace-nowrap text-center">
                  <div class="text-sm font-medium text-gray-900">{{ job.recruitTarget || '-' }}</div>
                </td>
                <td class="px-2 py-2 text-center">
                  <div v-if="job.workLocation && job.workLocation !== '-'"
                    class="flex flex-wrap gap-1 items-center justify-center">
                    <span v-for="(tag, index) in getDisplayTags(job.workLocation).visible" :key="index"
                      class="inline-flex px-2 py-1 text-xs font-medium rounded-md cursor-help transition-all duration-200 hover:shadow-md"
                      :class="getPositionTagClass(tag, index)" :title="tag.length > 15 ? tag : ''">
                      {{ truncateText(tag, 15) }}
                    </span>
                    <span v-if="getDisplayTags(job.workLocation).hasMore"
                      class="inline-flex px-2 py-1 text-xs font-medium rounded-md cursor-help bg-gray-200 text-gray-600 hover:bg-gray-300"
                      :title="getDisplayTags(job.workLocation).remaining.join(', ')">
                      ...
                    </span>
                  </div>
                  <div v-else class="text-sm font-medium text-gray-900">{{ job.workLocation || '-' }}</div>
                </td>
                <td class="px-2 py-2 text-center">
                  <div v-if="job.positionName && job.positionName !== '-'"
                    class="flex flex-wrap gap-1 items-center justify-center">
                    <span v-for="(tag, index) in getDisplayTags(job.positionName).visible" :key="index"
                      class="inline-flex px-2 py-1 text-xs font-medium rounded-md cursor-help transition-all duration-200 hover:shadow-md"
                      :class="getPositionTagClass(tag, index)" :title="tag.length > 15 ? tag : ''">
                      {{ truncateText(tag, 15) }}
                    </span>
                    <span v-if="getDisplayTags(job.positionName).hasMore"
                      class="inline-flex px-2 py-1 text-xs font-medium rounded-md cursor-help bg-gray-200 text-gray-600 hover:bg-gray-300"
                      :title="getDisplayTags(job.positionName).remaining.join(', ')">
                      ...
                    </span>
                  </div>
                  <div v-else class="text-sm font-medium text-gray-900">{{ job.positionName || '-' }}</div>
                </td>
                <td class="px-2 py-2 whitespace-nowrap text-center">
                  <span class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                    :class="getRecruitTypeClass(job.recruitType)">
                    {{ job.recruitType || '-' }}
                  </span>
                </td>
                <td class="px-2 py-2 whitespace-nowrap text-center text-sm text-gray-900">
                  {{ formatDate(job.startTime) || '-' }}
                </td>
                <td class="px-2 py-2 whitespace-nowrap text-center text-sm text-gray-900">
                  {{ job.deadline || '-' }}
                </td>
                <td class="px-2 py-2 whitespace-nowrap text-center">
                  <a v-if="job.relatedLink" :href="job.relatedLink" target="_blank"
                    class="inline-flex items-center px-2 py-1 text-xs font-medium text-blue-600 bg-blue-100 rounded-full hover:bg-blue-200 transition-colors">
                    <svg class="w-3 h-3 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14"></path>
                    </svg>
                    投递链接
                  </a>
                  <span v-else class="text-gray-400">-</span>
                </td>
                <td class="px-2 py-2 whitespace-nowrap text-center">
                  <a v-if="job.announcement" :href="job.announcement" target="_blank"
                    class="inline-flex items-center px-2 py-1 text-xs font-medium text-green-600 bg-green-100 rounded-full hover:bg-green-200 transition-colors">
                    <svg class="w-3 h-3 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z">
                      </path>
                    </svg>
                    查看公告
                  </a>
                  <span v-else class="text-gray-400">-</span>
                </td>
                <td class="px-2 py-2 whitespace-nowrap text-center">
                  <span v-if="job.referralCode && job.referralCode !== '-'"
                    class="inline-flex items-center px-2 py-1 text-xs font-medium text-purple-600 bg-purple-100 rounded-full font-mono">
                    {{ job.referralCode }}
                  </span>
                  <span v-else class="text-gray-400">-</span>
                </td>
                <td class="px-2 py-2 max-w-xs text-center">
                  <div v-if="job.remark && job.remark !== '-'" class="text-sm text-gray-900 truncate" :title="job.remark">
                    {{ job.remark }}
                  </div>
                  <span v-else class="text-gray-400">-</span>
                </td>
                <td class="px-2 py-2 whitespace-nowrap text-center">
                  <button
                    @click="handleApply(job)"
                    class="inline-flex items-center px-2 py-1 text-xs font-medium text-blue-600 bg-blue-100 rounded-full hover:bg-blue-200 transition-colors"
                  >
                    <svg class="w-3 h-3 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6"></path>
                    </svg>
                    加入我的投递
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- 分页 -->
    <div v-if="hasJobs && totalPages > 1" class="flex justify-between items-center">
      <div class="text-sm text-gray-700">
        共 {{ total }} 条记录
      </div>
      <div class="flex items-center space-x-2">
        <!-- 上一页 -->
        <button @click="handlePageChange(currentPage - 1)" :disabled="currentPage <= 1"
          class="px-3 py-2 text-sm border border-gray-300 rounded hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:bg-white">
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path>
          </svg>
        </button>

        <!-- 页码按钮 -->
        <template v-for="page in getPageNumbers()" :key="page">
          <button v-if="page === '...'" disabled class="px-3 py-2 text-sm text-gray-400 cursor-default">
            ...
          </button>
          <button v-else @click="handlePageChange(page as number)" :class="[
            'px-3 py-2 text-sm border rounded transition-colors',
            currentPage === page
              ? 'bg-blue-600 text-white border-blue-600'
              : 'border-gray-300 hover:bg-gray-50'
          ]">
            {{ page }}
          </button>
        </template>

        <!-- 下一页 -->
        <button @click="handlePageChange(currentPage + 1)" :disabled="currentPage >= totalPages"
          class="px-3 py-2 text-sm border border-gray-300 rounded hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:bg-white">
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
          </svg>
        </button>

        <!-- 跳转到指定页 -->
        <div class="flex items-center space-x-2 ml-4">
          <span class="text-sm text-gray-700">跳至</span>
          <input v-model="jumpPage" @keyup.enter="handleJumpPage" type="number" min="1" :max="totalPages"
            class="w-16 px-2 py-1 text-sm border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
          <span class="text-sm text-gray-700">页</span>
          <button @click="handleJumpPage" :disabled="!jumpPage || jumpPage < 1 || jumpPage > totalPages"
            class="px-3 py-1 text-sm bg-blue-600 text-white rounded hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:bg-blue-600 transition-colors">
            跳转
          </button>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { jobInfoApi } from '@/api/jobInfo'
import { userJobApplyApi } from '@/api/userJobApply'
import type { JobInfoQueryRequest, JobInfo } from '@/api/types'
import Message from '@/components/Message'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

// 响应式数据
const currentPage = ref(1)
const pageSize = ref(10)
const jobList = ref<JobInfo[]>([])
const total = ref(0)
const loading = ref(false)
const jumpPage = ref<number | string>('')
const isChangingPage = ref(false)

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
  handleSearch()
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

    // 处理日期格式
    if (params.startTime) {
      params.startTime = new Date(params.startTime).toISOString()
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
    // 优先显示后端返回的错误信息
    const errorMessage = error.message || error.response?.data?.message || '获取数据失败，请重试'
    Message.error(errorMessage)
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id: string) => {
  if (confirm('确定要删除这条招聘信息吗？')) {
    try {
      const response = await jobInfoApi.delete(id)
      if (response.data) {
        Message.success(response.message || '删除成功')
        // 删除成功后刷新列表
        await fetchData()
      }
    } catch (error: any) {
      console.error('删除失败:', error)
      Message.error(error.response?.data?.message || '删除失败')
    }
  }
}

// 处理投递
const handleApply = async (job: JobInfo) => {
  // 检查用户是否已登录
  if (!userStore.currentUser) {
    Message.warning('请先登录后再投递职位')
    return
  }

  try {
    const response = await userJobApplyApi.add({
      jobId: job.id,
      applicationStatus: '已投递'
    })
    
    if (response.code === 0) {
      Message.success('投递成功！')
    } else {
      Message.error(response.message || '投递失败')
    }
  } catch (error: any) {
    console.error('投递失败:', error)
    const errorMessage = error.message || error.response?.data?.message || '投递失败'
    
    // 如果是重复投递的错误，给出友好提示
    if (errorMessage.includes('已经投递过')) {
      Message.warning('您已经投递过该职位了')
    } else if (errorMessage.includes('登录信息失效')) {
      Message.warning('登录信息已失效，请重新登录')
      userStore.removeToken()
    } else {
      Message.error(errorMessage)
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

// 岗位信息处理方法
const getPositionTags = (positionInfo?: string) => {
  if (!positionInfo) return []

  // 按空格分割岗位信息
  const tags = positionInfo
    .split(/\s+/) // 按空格分割
    .map(tag => tag.trim())
    .filter(tag => tag.length > 0)

  return tags
}

// 获取显示标签（限制显示数量）
const getDisplayTags = (text?: string) => {
  if (!text) return { visible: [], remaining: [], hasMore: false }

  const allTags = getPositionTags(text)

  // 如果只有一个标签且不包含空格，也要显示为标签
  if (allTags.length === 0 && text.trim()) {
    allTags.push(text.trim())
  }

  const maxVisible = 3
  const visible = allTags.slice(0, maxVisible)
  const remaining = allTags.slice(maxVisible)
  const hasMore = remaining.length > 0

  return {
    visible,
    remaining,
    hasMore
  }
}

const truncateText = (text: string, maxLength: number) => {
  if (text.length <= maxLength) return text
  return text.substring(0, maxLength) + '...'
}

const getPositionTagClass = (tag: string, index: number) => {
  // 预定义的颜色数组
  const colors = [
    'bg-blue-100 text-blue-800 border border-blue-200',
    'bg-green-100 text-green-800 border border-green-200',
    'bg-yellow-100 text-yellow-800 border border-yellow-200',
    'bg-purple-100 text-purple-800 border border-purple-200',
    'bg-pink-100 text-pink-800 border border-pink-200',
    'bg-indigo-100 text-indigo-800 border border-indigo-200',
    'bg-red-100 text-red-800 border border-red-200',
    'bg-orange-100 text-orange-800 border border-orange-200',
    'bg-teal-100 text-teal-800 border border-teal-200',
    'bg-cyan-100 text-cyan-800 border border-cyan-200',
    'bg-emerald-100 text-emerald-800 border border-emerald-200',
    'bg-lime-100 text-lime-800 border border-lime-200',
    'bg-amber-100 text-amber-800 border border-amber-200',
    'bg-rose-100 text-rose-800 border border-rose-200',
    'bg-violet-100 text-violet-800 border border-violet-200',
    'bg-sky-100 text-sky-800 border border-sky-200'
  ]

  // 根据标签内容生成一个稳定的索引（同样的标签总是相同颜色）
  let hash = 0
  for (let i = 0; i < tag.length; i++) {
    const char = tag.charCodeAt(i)
    hash = ((hash << 5) - hash) + char
    hash = hash & hash // 转换为32位整数
  }

  // 使用绝对值确保索引为正数
  const colorIndex = Math.abs(hash) % colors.length

  return colors[colorIndex]
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
onMounted(async () => {
  // 初始化用户信息
  await userStore.initUserInfo()
  // 获取招聘信息
  fetchData()
})
</script>

<style scoped>
/* 表格容器动画 - 和后台管理页面一样的简单动画 */
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
