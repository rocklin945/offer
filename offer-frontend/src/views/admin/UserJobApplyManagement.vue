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
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">用户名</label>
          <input
            v-model="searchForm.userName"
            type="text"
            placeholder="请输入用户名"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">公司名称</label>
          <input
            v-model="searchForm.companyName"
            type="text"
            placeholder="请输入公司名称"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">岗位名称</label>
          <input
            v-model="searchForm.positionName"
            type="text"
            placeholder="请输入岗位名称"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">投递状态</label>
          <select v-model="searchForm.applicationStatus" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="">全部状态</option>
            <option value="已投递">已投递</option>
            <option value="简历筛选">简历筛选</option>
            <option value="笔试">笔试</option>
            <option value="面试">面试</option>
            <option value="终面">终面</option>
            <option value="已通过">已通过</option>
            <option value="已拒绝">已拒绝</option>
          </select>
        </div>
      </div>
      <div class="flex justify-end space-x-2">
        <button @click="resetSearch" class="px-4 py-2 text-gray-600 bg-gray-100 hover:bg-gray-200 rounded-md transition-colors">
          重置
        </button>
        <button @click="handleSearch" class="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md transition-colors">
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
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
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
                  公司名称
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  岗位名称
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  投递状态
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  投递时间
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  最后更新
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
                  <div class="text-sm font-medium text-gray-900">{{ item.companyName }}</div>
                </td>
                <td class="px-4 py-4 text-center">
                  <div class="text-sm text-gray-900">{{ item.positionName }}</div>
                </td>
                <td class="px-4 py-4 text-center">
                  <span
                    :class="getStatusClass(item.applicationStatus)"
                    class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                  >
                    {{ item.applicationStatus }}
                  </span>
                </td>
                <td class="px-4 py-4 text-center text-sm text-gray-500">
                  {{ formatDate(item.createTime) }}
                </td>
                <td class="px-4 py-4 text-center text-sm text-gray-500">
                  {{ formatDate(item.updateTime) }}
                </td>
                <td class="px-4 py-4 text-center">
                  <button
                    @click="handleDelete(item)"
                    class="text-red-600 hover:text-red-900 text-sm font-medium"
                  >
                    删除
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 分页 -->
      <div v-if="hasData" class="bg-white px-4 py-3 border-t border-gray-200 sm:px-6">
        <div class="flex items-center justify-between">
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
              <button v-else @click="handlePageChange(page as number)"
                :class="[
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
              <button @click="handleJumpPage" :disabled="!jumpPage || Number(jumpPage) < 1 || Number(jumpPage) > totalPages"
                class="px-3 py-1 text-sm bg-blue-600 text-white rounded hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:bg-blue-600">
                跳转
              </button>
            </div>
            
            <!-- 每页显示条数 -->
            <div class="flex items-center space-x-2 ml-4">
              <span class="text-sm text-gray-700">每页</span>
              <select v-model="pageSize" @change="handlePageSizeChange"
                class="px-2 py-1 text-sm border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
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
  userName: '',
  companyName: '',
  positionName: '',
  applicationStatus: ''
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
    Message.error(error.message || '获取数据失败')
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
    userName: '',
    companyName: '',
    positionName: '',
    applicationStatus: ''
  })
  currentPage.value = 1
  fetchData()
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
      Message.error(error.message || '删除失败')
    }
  }
}

// 获取状态样式
const getStatusClass = (status: string) => {
  const statusMap: Record<string, string> = {
    '已投递': 'bg-blue-100 text-blue-800',
    '简历筛选': 'bg-yellow-100 text-yellow-800',
    '笔试': 'bg-purple-100 text-purple-800',
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