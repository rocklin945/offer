<template>
  <div class="space-y-6">
    <!-- 页面标题和操作按钮 -->
    <div class="flex justify-between items-center">
      <h2 class="text-2xl font-bold text-gray-900">招聘信息列表</h2>
      <router-link to="/add" class="btn-primary">
        添加招聘信息
      </router-link>
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
          <label class="block text-sm font-medium text-gray-700 mb-1">投递进度</label>
          <select v-model="searchForm.applicationStatus" class="input-field">
            <option value="">全部</option>
            <option value="未投递">未投递</option>
            <option value="已投递">已投递</option>
            <option value="面试中">面试中</option>
            <option value="已通过">已通过</option>
            <option value="已拒绝">已拒绝</option>
          </select>
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
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                公司信息
              </th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                招聘对象
              </th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                岗位信息
              </th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                招聘类型
              </th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                投递进度
              </th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                开始时间
              </th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                截止时间
              </th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                相关链接
              </th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                招聘公告
              </th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                内推码
              </th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                备注
              </th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="job in jobList" :key="job.id" class="hover:bg-gray-50">
              <td class="px-4 py-4 whitespace-nowrap">
                <div>
                  <div class="text-sm font-medium text-gray-900">{{ job.companyName }}</div>
                  <div class="text-sm text-gray-500">{{ job.companyType || '-' }}</div>
                  <div class="text-sm text-gray-500">{{ job.industry || '-' }}</div>
                </div>
              </td>
              <td class="px-4 py-4 whitespace-nowrap">
                <div class="text-sm font-medium text-gray-900">{{ job.recruitTarget || '-' }}</div>
              </td>
              <td class="px-4 py-4">
                <div class="space-y-2">
                  <div class="text-sm font-medium text-gray-900">{{ job.positionName || '-' }}</div>
                  <div class="text-sm text-gray-500">{{ job.workLocation || '-' }}</div>
                  <div v-if="job.positionInfo" class="flex flex-wrap gap-1">
                    <span v-for="(tag, index) in getPositionTags(job.positionInfo)" :key="index"
                      class="inline-flex px-2 py-1 text-xs font-medium rounded-md cursor-help transition-all duration-200 hover:shadow-md"
                      :class="getPositionTagClass(tag, index)" :title="tag.length > 15 ? tag : ''">
                      {{ truncateText(tag, 15) }}
                    </span>
                  </div>
                </div>
              </td>
              <td class="px-4 py-4 whitespace-nowrap">
                <span class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                  :class="getRecruitTypeClass(job.recruitType)">
                  {{ job.recruitType || '-' }}
                </span>
              </td>
              <td class="px-4 py-4 whitespace-nowrap">
                <span class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                  :class="getStatusClass(job.applicationStatus)">
                  {{ job.applicationStatus || '-' }}
                </span>
              </td>
              <td class="px-4 py-4 whitespace-nowrap text-sm text-gray-900">
                {{ formatDate(job.startTime) || '-' }}
              </td>
              <td class="px-4 py-4 whitespace-nowrap text-sm text-gray-900">
                {{ job.deadline || '-' }}
              </td>
              <td class="px-4 py-4 whitespace-nowrap">
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
              <td class="px-4 py-4 whitespace-nowrap">
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
              <td class="px-4 py-4 whitespace-nowrap">
                <span v-if="job.referralCode && job.referralCode !== '-'"
                  class="inline-flex items-center px-2 py-1 text-xs font-medium text-purple-600 bg-purple-100 rounded-full font-mono">
                  {{ job.referralCode }}
                </span>
                <span v-else class="text-gray-400">-</span>
              </td>
              <td class="px-4 py-4 max-w-xs">
                <div v-if="job.remark && job.remark !== '-'" class="text-sm text-gray-900 truncate" :title="job.remark">
                  {{ job.remark }}
                </div>
                <span v-else class="text-gray-400">-</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 分页 -->
    <div v-if="hasJobs" class="flex justify-between items-center">
      <div class="text-sm text-gray-700">
        共 {{ total }} 条记录
      </div>
      <div class="flex space-x-2">
        <button @click="handlePageChange(currentPage - 1)" :disabled="currentPage <= 1"
          class="px-3 py-1 text-sm border rounded-md disabled:opacity-50 disabled:cursor-not-allowed">
          上一页
        </button>
        <span class="px-3 py-1 text-sm">
          第 {{ currentPage }} 页，共 {{ totalPages }} 页
        </span>
        <button @click="handlePageChange(currentPage + 1)" :disabled="currentPage >= totalPages"
          class="px-3 py-1 text-sm border rounded-md disabled:opacity-50 disabled:cursor-not-allowed">
          下一页
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { jobInfoApi } from '@/api/jobInfo'
import type { JobInfoQueryRequest, JobInfo } from '@/api/types'

// 响应式数据
const currentPage = ref(1)
const pageSize = ref(10)
const jobList = ref<JobInfo[]>([])
const total = ref(0)
const loading = ref(false)

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
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page
    await fetchData()
  }
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

    console.log('请求参数:', params) // 调试日志

    const response = await jobInfoApi.getList(params)

    console.log('API响应:', response) // 调试日志

    jobList.value = response.data.list || []
    total.value = response.data.total || 0

    console.log('设置的数据:', { jobList: jobList.value, total: total.value }) // 调试日志
  } catch (error) {
    console.error('获取招聘信息列表失败:', error)
    alert('获取数据失败，请重试')
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id: number) => {
  if (confirm('确定要删除这条招聘信息吗？')) {
    try {
      const response = await jobInfoApi.delete(id)
      if (response.data) {
        alert('删除成功')
        // 删除成功后刷新列表
        await fetchData()
      }
    } catch (error) {
      console.error('删除失败:', error)
      alert('删除失败')
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

  console.log('原始岗位信息:', positionInfo) // 调试日志

  // 使用正则表达式匹配以"类"结尾的词组
  // 匹配模式：一个或多个中文、英文、数字、符号 + "类"
  const regex = /[\u4e00-\u9fa5a-zA-Z0-9&（）()与]+类/g
  const matches = positionInfo.match(regex)

  console.log('正则匹配结果:', matches) // 调试日志

  if (matches && matches.length > 0) {
    const tags = matches
      .map(tag => tag.trim())
      .filter(tag => tag.length > 0)
    
    console.log('处理后的标签:', tags) // 调试日志
    return tags
  }

  // 如果没有找到"类"字符，则按分号、逗号等分割
  const fallbackTags = positionInfo
    .split(/[;；,，\n\r\s]+/)
    .map(tag => tag.trim())
    .filter(tag => tag.length > 0)
  
  console.log('备用分割结果:', fallbackTags) // 调试日志
  return fallbackTags
}

const truncateText = (text: string, maxLength: number) => {
  if (text.length <= maxLength) return text
  return text.substring(0, maxLength) + '...'
}

const getPositionTagClass = (tag: string, index: number) => {
  // 根据"类"的类型选择不同颜色
  if (tag.includes('产品') && tag.includes('类')) {
    return 'bg-blue-100 text-blue-800 border border-blue-200'
  } else if (tag.includes('技术') && tag.includes('类')) {
    return 'bg-green-100 text-green-800 border border-green-200'
  } else if (tag.includes('发行') && tag.includes('类')) {
    return 'bg-yellow-100 text-yellow-800 border border-yellow-200'
  } else if (tag.includes('美术') && tag.includes('类')) {
    return 'bg-purple-100 text-purple-800 border border-purple-200'
  } else if (tag.includes('职能') && tag.includes('类')) {
    return 'bg-pink-100 text-pink-800 border border-pink-200'
  } else if (tag.includes('运营') && tag.includes('类')) {
    return 'bg-indigo-100 text-indigo-800 border border-indigo-200'
  } else if (tag.includes('市场') && tag.includes('类')) {
    return 'bg-red-100 text-red-800 border border-red-200'
  } else if (tag.includes('设计') && tag.includes('类')) {
    return 'bg-orange-100 text-orange-800 border border-orange-200'
  } else if (tag.includes('供应链') && tag.includes('类')) {
    return 'bg-teal-100 text-teal-800 border border-teal-200'
  } else if (tag.includes('操作') && tag.includes('类')) {
    return 'bg-cyan-100 text-cyan-800 border border-cyan-200'
  } else {
    // 其他情况使用灰色
    return 'bg-gray-100 text-gray-800 border border-gray-200'
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