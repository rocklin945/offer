<template>
  <div class="space-y-6">
    <!-- 页面标题 -->
    <div class="flex justify-between items-center">
      <h2 class="text-2xl font-bold text-gray-900">邀请返利管理</h2>
    </div>

    <!-- 搜索筛选区域 -->
    <div class="bg-white rounded-lg border border-gray-200 p-6">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">用户ID</label>
          <input v-model="searchForm.userId" type="number" placeholder="请输入用户ID"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">用户名</label>
          <input v-model="searchForm.username" type="text" placeholder="请输入用户名"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">状态</label>
          <select v-model="searchForm.status"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="">全部</option>
            <option value="0">待确认</option>
            <option value="1">已确认</option>
            <option value="2">已拒绝</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">排序字段</label>
          <select v-model="searchForm.sortField"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="">默认排序</option>
            <option value="id">ID</option>
            <option value="userId">用户ID</option>
            <option value="invitedCount">邀请人数</option>
            <option value="pendingCommission">待确认金额</option>
            <option value="balanceCommission">已确认金额</option>
            <option value="totalCommission">总佣金</option>
            <option value="createTime">创建时间</option>
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
      <div v-else>
        <div v-if="commissionList.length === 0" class="text-center py-12">
          <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
          </svg>
          <h3 class="mt-2 text-sm font-medium text-gray-900">暂无数据</h3>
          <p class="mt-1 text-sm text-gray-500">没有找到任何返利记录</p>
        </div>
        <div v-else class="overflow-x-auto">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">用户ID</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">邀请人数</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">总佣金</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">待确认</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">已确认</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">状态</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">操作</th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="item in commissionList" :key="item.id">
                <td class="px-4 py-4 text-center text-sm text-gray-900">{{ item.id }}</td>
                <td class="px-4 py-4 text-center text-sm text-gray-900">{{ item.userId }}</td>
                <td class="px-4 py-4 text-center text-sm text-gray-900">{{ item.invitedCount }}</td>
                <td class="px-4 py-4 text-center text-sm text-gray-900">¥{{ item.totalCommission }}</td>
                <td class="px-4 py-4 text-center text-sm text-gray-900">¥{{ item.pendingCommission }}</td>
                <td class="px-4 py-4 text-center text-sm text-gray-900">¥{{ item.balanceCommission }}</td>
                <td class="px-4 py-4 text-center text-sm text-gray-900">
                  <span v-if="item.status === 0" class="inline-flex px-2 py-1 text-xs font-semibold rounded-full bg-yellow-100 text-yellow-800">待确认</span>
                  <span v-else-if="item.status === 1" class="inline-flex px-2 py-1 text-xs font-semibold rounded-full bg-green-100 text-green-800">已确认</span>
                  <span v-else class="inline-flex px-2 py-1 text-xs font-semibold rounded-full bg-red-100 text-red-800">已拒绝</span>
                </td>
                <td class="px-4 py-4 text-center text-sm text-gray-900">
                  <div class="flex justify-center space-x-2">
                    <button v-if="item.status === 0" @click="handleConfirm(item.id, item.pendingCommission)" class="text-green-600 hover:text-green-900 text-sm font-medium">确认</button>
                    <button v-if="item.status === 0" @click="handleReject(item.id)" class="text-red-600 hover:text-red-900 text-sm font-medium">拒绝</button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 分页 -->
      <div v-if="hasCommission" class="bg-white px-4 py-3 border-t border-gray-200 sm:px-6">
        <div class="flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
          <div class="text-sm text-gray-700">
            共 {{ pagination.total }} 条记录
          </div>
          <div class="flex flex-wrap items-center gap-2 sm:flex-nowrap sm:space-x-2">
            <!-- 上一页 -->
            <button @click="prevPage" :disabled="pagination.current <= 1"
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
              <button v-else @click="handlePageChange(page)" :class="[
                'px-2 py-1 text-xs sm:px-3 sm:py-2 sm:text-sm border rounded transition-colors',
                pagination.current === page
                  ? 'bg-blue-600 text-white border-blue-600'
                  : 'border-gray-300 hover:bg-gray-50'
              ]">
                {{ page }}
              </button>
            </template>

            <!-- 下一页 -->
            <button @click="nextPage" :disabled="pagination.current >= totalPages"
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
              <select v-model="pagination.pageSize" @change="handlePageSizeChange"
                class="w-16 px-2 py-1 text-xs sm:text-sm border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                <option>10</option>
                <option>20</option>
                <option>50</option>
                <option>100</option>
              </select>
              <span class="text-sm text-gray-700">条</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { inviteCommissionApi } from '@/api/inviteCommission'
import Message from '@/components/Message'
import Confirm from '@/components/Confirm'

const searchForm = ref({
  userId: '',
  status: '',
  sortField: '',
  sortOrder: 'desc'
})

const commissionList = ref([])
const loading = ref(false)
const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 0
})

const handleSearch = async () => {
  loading.value = true
  try {
    const response = await inviteCommissionApi.listCommissionByPage({
      pageNum: pagination.value.current,
      pageSize: pagination.value.pageSize,
      ...searchForm.value
    })
    if (response && response.data && Array.isArray(response.data.list)) {
      commissionList.value = response.data.list
      pagination.value.total = response.data.total
    } else if (response && response.data && Array.isArray(response.data.data.list)) { // Check for nested data
      commissionList.value = response.data.data.list
      pagination.value.total = response.data.data.total
    }
    else {
      commissionList.value = []
      pagination.value.total = 0
      console.warn('API response data.list is not an array or is missing:', response);
    }
  } catch (error) {
    console.error('搜索失败:', error)
    commissionList.value = []
    pagination.value.total = 0
  } finally {
    loading.value = false
  }
}

const resetSearch = () => {
  searchForm.value = {
    userId: '',
    status: '',
    sortField: '',
    sortOrder: 'desc'
  }
  pagination.value.current = 1
  handleSearch()
}

const prevPage = () => {
  if (pagination.value.current > 1) {
    pagination.value.current--
    handleSearch()
  }
}

const nextPage = () => {
  if (pagination.value.current * pagination.value.pageSize < pagination.value.total) {
    pagination.value.current++
    handleSearch()
  }
}

const jumpPage = ref('')

const totalPages = computed(() => {
  return Math.ceil(pagination.value.total / pagination.value.pageSize)
})

const handleJumpPage = () => {
  const page = parseInt(jumpPage.value)
  if (page >= 1 && page <= totalPages.value) {
    pagination.value.current = page
    handleSearch()
  }
  jumpPage.value = ''
}

const handlePageSizeChange = () => {
  pagination.value.current = 1
  handleSearch()
}

const hasCommission = computed(() => {
  return commissionList.value && commissionList.value.length > 0
})

const handlePageChange = (page) => {
  pagination.value.current = page
  handleSearch()
}

const getPageNumbers = () => {
  const total = totalPages.value
  const current = pagination.value.current
  const delta = 2 // Number of pages to show around the current page
  const range = []
  const pages = []

  for (let i = Math.max(2, current - delta); i <= Math.min(total - 1, current + delta); i++) {
    range.push(i)
  }

  if (current - delta > 2) {
    pages.push(1, '...')
  } else {
    pages.push(1)
  }

  pages.push(...range)

  if (current + delta < total - 1) {
    pages.push('...', total)
  } else if (total > 1) {
    pages.push(total)
  }

  return pages
}

const handleConfirm = async (id, amount) => {
  const confirmed = await Confirm.show({
    title: '确认佣金',
    message: '确定要确认这笔佣金吗？',
    type: 'warning',
    confirmText: '确认',
    cancelText: '取消'
  })
  
  if (confirmed) {
    try {
      await inviteCommissionApi.confirmCommission(id, amount)
      Message.success('佣金确认成功')
      handleSearch()
    } catch (error) {
      Message.error('确认失败: ' + error.message)
    }
  }
}

const handleReject = async (id) => {
  const confirmed = await Confirm.show({
    title: '拒绝佣金',
    message: '确定要拒绝这笔佣金吗？',
    type: 'danger',
    confirmText: '拒绝',
    cancelText: '取消'
  })
  
  if (confirmed) {
    try {
      await inviteCommissionApi.rejectCommission(id)
      Message.success('佣金拒绝成功')
      handleSearch()
    } catch (error) {
      Message.error('拒绝失败: ' + error.message)
    }
  }
}

onMounted(() => {
  handleSearch()
})
</script>

<style scoped>
table {
  border-collapse: separate;
  border-spacing: 0;
}

th {
  position: sticky;
  top: 0;
  background-color: #f9fafb;
}
</style>