<template>
  <div class="space-y-6">
    <!-- 页面标题 -->
    <div class="flex justify-between items-center">
      <div>
        <h1 class="text-2xl font-bold text-gray-900">投递记录管理</h1>
        <p class="text-sm text-gray-600 mt-1">管理所有用户的职位投递记录</p>
      </div>
    </div>

    <!-- 搜索筛选区域 -->
    <div class="bg-white rounded-lg border border-gray-200 p-6">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">用户ID</label>
          <input v-model="searchForm.userId" type="string" placeholder="请输入用户ID"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
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
          <label class="block text-sm font-medium text-gray-700 mb-1">投递状态</label>
          <select v-model="searchForm.applicationStatus"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="">全部状态</option>
            <option value="未投递">未投递</option>
            <option value="已投递">已投递</option>
            <option value="笔试">笔试</option>
            <option value="一面">一面</option>
            <option value="二面">二面</option>
            <option value="HR面">HR面</option>
            <option value="已通过">已通过</option>
            <option value="已拒绝">已拒绝</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">排序字段</label>
          <select v-model="searchForm.sortField"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="">默认排序</option>
            <option value="companyName">公司名称</option>
            <option value="companyType">公司类型</option>
            <option value="industry">所属行业</option>
            <option value="recruitType">招聘类型</option>
            <option value="workLocation">工作地点</option>
            <option value="recruitTarget">招聘对象</option>
            <option value="positionName">岗位名称</option>
            <option value="applicationStatus">投递状态</option>
            <option value="createTime">投递时间</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">排序方式</label>
          <select v-model="searchForm.sortOrder"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="desc">降序</option>
            <option value="asc">升序</option>
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

      <div v-else-if="!hasData" class="text-center py-12">
        <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
            d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2">
          </path>
        </svg>
        <p class="mt-2 text-gray-500">暂无投递记录</p>
      </div>

      <div v-else class="overflow-x-auto">
        <div class="table-container" :class="{ 'table-loading': isChangingPage }">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  用户信息
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  公司信息
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  招聘信息
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  岗位信息
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  投递状态
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  投递时间
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  个人备注
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  操作
                </th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="item in tableData" :key="item.id" class="hover:bg-gray-50">
                <td class="px-4 py-4 text-center">
                  <div>
                    <div class="text-sm font-medium text-gray-900">{{ item.userName || '未知用户' }}</div>
                    <div class="text-sm text-gray-500">ID: {{ item.userId }}</div>
                  </div>
                </td>
                <td class="px-4 py-4 text-center">
                  <div>
                    <div class="text-sm font-medium text-gray-900">{{ item.companyName }}</div>
                    <div class="text-sm text-gray-500">{{ item.companyType || '-' }}</div>
                    <div class="text-sm text-gray-500">{{ item.industry || '-' }}</div>
                  </div>
                </td>
                <td class="px-4 py-4 text-center">
                  <div>
                    <span class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      :class="getRecruitTypeClass(item.recruitType)">
                      {{ item.recruitType || '-' }}
                    </span>
                    <div class="text-sm text-gray-500 mt-1">{{ item.recruitTarget || '-' }}</div>
                    <div class="text-sm text-gray-500">{{ item.workLocation || '-' }}</div>
                  </div>
                </td>
                <td class="px-4 py-4 text-center">
                  <div class="text-sm font-medium text-gray-900">{{ item.positionName }}</div>
                </td>
                <td class="px-4 py-4 text-center">
                  <span :class="getStatusClass(item.applicationStatus)"
                    class="inline-flex px-2 py-1 text-xs font-semibold rounded-full">
                    {{ item.applicationStatus }}
                  </span>
                </td>
                <td class="px-4 py-4 text-center text-sm text-gray-500">
                  {{ formatDate(item.createTime) }}
                </td>
                <td class="px-4 py-4 text-center">
                  <div v-if="item.personalNote" class="text-sm text-gray-700 max-w-xs mx-auto break-words"
                    v-html="renderLinksInText(item.personalNote)"></div>
                  <div v-else class="text-sm text-gray-500">-</div>
                </td>
                <td class="px-4 py-4 text-center">
                  <div class="flex justify-center space-x-2">
                    <button @click="handleUpdateStatus(item)"
                      class="text-blue-600 hover:text-blue-900 text-sm font-medium">
                      更新状态
                    </button>
                    <button @click="handleDelete(item)" class="text-red-600 hover:text-red-900 text-sm font-medium">
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
      <div v-if="hasData" class="bg-white px-4 py-3 border-t border-gray-200 sm:px-6">
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
              <button v-if="page === '...'" disabled
                class="px-2 py-1 text-xs sm:px-3 sm:py-2 sm:text-sm text-gray-400 cursor-default">
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

    <!-- 更新状态弹窗 -->
    <teleport to="body">
      <div v-if="showUpdateModal" class="fixed inset-0 z-50 flex items-center justify-center px-4 overflow-y-auto"
        style="z-index: 1000000;">
        <div class="absolute inset-0 bg-black transition-opacity duration-300 bg-opacity-50"
          @click="showUpdateModal = false"></div>
        <div
          class="relative bg-white rounded-lg p-6 w-96 my-8 transform transition-all duration-300 ease-out scale-100 opacity-100 translate-y-0">
          <h3 class="text-lg font-medium text-gray-900 mb-4">更新投递状态</h3>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">选择状态</label>
            <select v-model="updateForm.applicationStatus"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
              <option value="未投递">未投递</option>
              <option value="已投递">已投递</option>
              <option value="笔试">笔试</option>
              <option value="一面">一面</option>
              <option value="二面">二面</option>
              <option value="HR面">HR面</option>
              <option value="已通过">已通过</option>
              <option value="已拒绝">已拒绝</option>
            </select>
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">个人备注</label>
            <div class="relative">
              <textarea v-model="updateForm.personalNote"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent h-24 resize-none"
                placeholder="添加个人备注，如投递进度链接等（最多500字）" maxlength="500"></textarea>
              <div class="absolute bottom-2 right-2 text-xs text-gray-500">
                {{ updateForm.personalNote?.length || 0 }}/500
              </div>
            </div>
          </div>
          <div class="flex justify-end space-x-2">
            <button @click="showUpdateModal = false"
              class="px-4 py-2 text-gray-600 bg-gray-100 hover:bg-gray-200 rounded-md transition-colors">
              取消
            </button>
            <button @click="confirmUpdate"
              class="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md transition-colors">
              确认
            </button>
          </div>
        </div>
      </div>
    </teleport>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { userJobApplyApi, type UserJobApplyDTO, type UserJobApplyQueryRequest } from '../../api/userJobApply'
import Message from '../../components/Message'
import Confirm from '../../components/Confirm'

// 响应式数据
const loading = ref(false)
const tableData = ref<UserJobApplyDTO[]>([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const jumpPage = ref<number | string>('')
const isChangingPage = ref(false)

// 搜索表单
const searchForm = reactive<UserJobApplyQueryRequest>({
  userId: undefined,
  companyName: '',
  companyType: '',
  industry: '',
  recruitType: '',
  workLocation: '',
  recruitTarget: '',
  positionName: '',
  applicationStatus: '',
  sortField: '',
  sortOrder: 'desc'
})

// 更新状态相关
const showUpdateModal = ref(false)
const updateForm = reactive({
  id: 0,
  applicationStatus: '',
  personalNote: ''
})

// 计算属性
const hasData = computed(() => tableData.value.length > 0)
const totalPages = computed(() => Math.ceil(total.value / pageSize.value))

// 获取数据
const fetchData = async () => {
  try {
    loading.value = true
    const params: UserJobApplyQueryRequest = {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      ...searchForm
    }

    // 过滤空值
    Object.keys(params).forEach(key => {
      if (params[key as keyof UserJobApplyQueryRequest] === '') {
        delete params[key as keyof UserJobApplyQueryRequest]
      }
    })

    const response = await userJobApplyApi.adminGetPage(params)

    // axios响应拦截器返回response对象，实际数据在response.data中
    if (response && response.data && response.data.data) {
      const pageData = response.data.data
      tableData.value = pageData.list || pageData.records || []
      total.value = Number(pageData.total) || 0
    } else {
      Message.error('响应格式错误')
    }
  } catch (error: any) {
    console.error('获取投递记录失败:', error)
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = async () => {
  currentPage.value = 1
  await fetchData()
}

// 重置搜索
const resetSearch = () => {
  Object.assign(searchForm, {
    userId: undefined,
    companyName: '',
    companyType: '',
    industry: '',
    recruitType: '',
    workLocation: '',
    recruitTarget: '',
    positionName: '',
    applicationStatus: '',
    sortField: '',
    sortOrder: 'desc'
  })
  currentPage.value = 1
  fetchData()
}

// 获取招聘类型样式类
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

// 分页处理
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

// 更新状态
const handleUpdateStatus = (item: UserJobApplyDTO) => {
  updateForm.id = item.id
  updateForm.applicationStatus = item.applicationStatus
  updateForm.personalNote = item.personalNote || ''
  showUpdateModal.value = true
}

// 确认更新
const confirmUpdate = async () => {
  try {
    const response = await userJobApplyApi.adminUpdate(updateForm)
    // axios拦截器返回response对象，实际数据在response.data中
    if (response.data.statusCode === 200) {
      // 更新本地数据
      const index = tableData.value.findIndex(item => item.id === updateForm.id)
      if (index !== -1) {
        tableData.value[index].applicationStatus = updateForm.applicationStatus
        tableData.value[index].personalNote = updateForm.personalNote
      }

      Message.success('状态更新成功')
      showUpdateModal.value = false
    } else {
      Message.error(response.data.message || '更新失败')
    }
  } catch (error: any) {
    console.error('更新状态失败:', error)
  }
}

// 删除
const handleDelete = async (item: UserJobApplyDTO) => {
  const confirmed = await Confirm.show({
    title: '确认删除',
    message: `确定要删除用户 ${item.userName} 对 ${item.companyName} - ${item.positionName} 的投递记录吗？`,
    confirmText: '删除',
    cancelText: '取消',
    type: 'danger'
  })

  if (confirmed) {
    try {
      const response = await userJobApplyApi.adminDelete(item.id)
      // 修复响应格式判断
      if (response.data.statusCode === 200) {
        Message.success('删除成功')
        fetchData()
      } else {
        Message.error(response.data.message || '删除失败')
      }
    } catch (error: any) {
      console.error('删除失败:', error)
    }
  }
}

// 获取状态样式
const getStatusClass = (status: string) => {
  const statusMap: Record<string, string> = {
    '已投递': 'bg-yellow-100 text-yellow-800',
    '简历筛选': 'bg-yellow-100 text-yellow-800',
    '笔试': 'bg-purple-100 text-purple-800',
    '一面': 'bg-indigo-100 text-indigo-800',
    '二面': 'bg-cyan-100 text-cyan-800',
    'HR面': 'bg-orange-100 text-orange-800',
    '面试': 'bg-indigo-100 text-indigo-800',
    '终面': 'bg-pink-100 text-pink-800',
    '已通过': 'bg-green-100 text-green-800',
    '已拒绝': 'bg-red-100 text-red-800'
  }
  return statusMap[status] || 'bg-gray-100 text-gray-800'
}

// 格式化日期
const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleString('zh-CN')
}

// 将文本中的链接转换为可点击的链接
const renderLinksInText = (text: string) => {
  if (!text) return ''

  // URL正则表达式，匹配http/https/ftp开头的URL
  const urlRegex = /(https?:\/\/|ftp:\/\/)(www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)/gi

  // 替换文本中的URL为HTML链接
  return text.replace(urlRegex, (url) => {
    return `<a href="${url}" target="_blank" class="text-blue-600 hover:text-blue-800 hover:underline">${url}</a>`
  })
}

// 页面加载时获取数据
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