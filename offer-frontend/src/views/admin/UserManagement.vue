<template>
  <div class="space-y-6">
    <!-- 页面标题 -->
    <div class="flex justify-between items-center">
      <h2 class="text-2xl font-bold text-gray-900">用户管理</h2>
    </div>

    <!-- 搜索筛选区域 -->
    <div class="bg-white rounded-lg border border-gray-200 p-6">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">用户ID</label>
          <input v-model="queryParams.id" type="text" placeholder="请输入用户ID"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">用户名</label>
          <input v-model="queryParams.userName" type="text" placeholder="请输入用户名"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">账号</label>
          <input v-model="queryParams.userAccount" type="text" placeholder="请输入账号"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">用户角色</label>
          <select v-model="queryParams.userRole"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="">全部</option>
            <option :value="0">管理员</option>
            <option :value="1">普通用户</option>
            <option :value="2">会员</option>
          </select>
        </div>
      </div>
      <div class="flex justify-end space-x-2">
        <button @click="resetQuery"
          class="px-4 py-2 text-gray-600 bg-gray-100 hover:bg-gray-200 rounded-md transition-colors">
          重置
        </button>
        <button @click="handleQuery"
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

      <div v-else-if="userList.length === 0" class="text-center py-12">
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
                  ID
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  头像
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  用户名
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  账号
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  角色
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  会员到期时间
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  创建时间
                </th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                  操作
                </th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="user in userList" :key="user.userId" class="hover:bg-gray-50">
                <td class="px-4 py-4 text-center text-sm text-gray-500">
                  {{ user.userId }}
                </td>
                <td class="px-4 py-4 text-center">
                  <div class="flex justify-center">
                    <img :src="user.userAvatar" alt="头像" class="h-10 w-10 rounded-full" />
                  </div>
                </td>
                <td class="px-4 py-4 text-center text-sm font-medium text-gray-900">
                  {{ user.userName }}
                </td>
                <td class="px-4 py-4 text-center text-sm text-gray-500">
                  {{ user.userAccount }}
                </td>
                <td class="px-4 py-4 text-center">
                  <span :class="[
                    'inline-flex px-2 py-1 text-xs font-semibold rounded-full',
                    user.userRole === 0
                      ? 'bg-blue-100 text-blue-800'
                      : user.userRole === 1
                        ? 'bg-gray-100 text-gray-800'
                        : 'bg-yellow-100 text-yellow-800'
                  ]">
                    {{ user.userRole === 0 ? '管理员' : user.userRole === 1 ? '普通用户' : '会员' }}
                  </span>
                </td>

                <td class="px-4 py-4 text-center text-sm text-gray-500">
                  <span v-if="user.userRole === 2">
                    <span v-if="user.memberExpireTime" :class="[
                      'text-sm',
                      isMemberExpired(user.memberExpireTime) ? 'text-red-600' : 'text-green-600'
                    ]">
                      {{ formatMemberExpireTime(user.memberExpireTime) }}
                      <span v-if="isMemberExpired(user.memberExpireTime)" class="ml-1 text-red-500">(已过期)</span>
                    </span>
                    <span v-else class="text-gray-400">未设置</span>
                  </span>
                  <span v-else class="text-gray-400">-</span>
                </td>

                <td class="px-4 py-4 text-center text-sm text-gray-500">
                  {{ formatDate(user.createTime) }}
                </td>
                <td class="px-4 py-4 text-center">
                  <div class="flex justify-center space-x-2">
                    <button @click="handleEdit(user)" class="text-blue-600 hover:text-blue-900 text-sm font-medium">
                      编辑
                    </button>
                    <button @click="handleDelete(user)" class="text-red-600 hover:text-red-900 text-sm font-medium">
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
      <div v-if="userList.length > 0" class="bg-white px-4 py-3 border-t border-gray-200 sm:px-6">
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

    <!-- 编辑用户对话框 -->
    <teleport to="body">
      <div v-if="showEditDialog" class="fixed inset-0 z-50 flex items-center justify-center px-4 overflow-y-auto" style="z-index: 1000000;">
        <div class="absolute inset-0 bg-black transition-opacity duration-300 bg-opacity-50" @click="showEditDialog = false"></div>
        <div class="relative bg-white rounded-lg shadow-xl w-full max-w-md p-6 my-8 transform transition-all duration-300 ease-out scale-100 opacity-100 translate-y-0">
          <div class="flex justify-between items-center mb-4">
            <h2 class="text-xl font-semibold text-gray-800">编辑用户</h2>
            <button @click="showEditDialog = false" class="text-gray-500 hover:text-gray-700">
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
              </svg>
            </button>
          </div>

          <form @submit.prevent="submitEdit">
            <div class="mb-4">
              <label for="userName" class="block text-sm font-medium text-gray-700 mb-1">用户名</label>
              <input type="text" id="userName" v-model="editForm.userName"
                class="w-full px-3 py-2 border border-gray-300 rounded-md" required />
            </div>

            <div class="mb-4">
              <label for="userProfile" class="block text-sm font-medium text-gray-700 mb-1">用户简介</label>
              <textarea id="userProfile" v-model="editForm.userProfile"
                class="w-full px-3 py-2 border border-gray-300 rounded-md" rows="3"></textarea>
            </div>

            <div class="mb-4">
              <label for="userRole" class="block text-sm font-medium text-gray-700 mb-1">用户角色</label>
              <select id="userRole" v-model="editForm.userRole" class="w-full px-3 py-2 border border-gray-300 rounded-md"
                required @change="handleRoleChange">
                <option :value="0">管理员</option>
                <option :value="1">普通用户</option>
                <option :value="2">会员</option>
              </select>
            </div>

            <!-- 会员到期时间设置 -->
            <div v-if="editForm.userRole === 2" class="mb-4">
              <label for="memberExpireTime" class="block text-sm font-medium text-gray-700 mb-1">
                会员到期时间
              </label>
              <input 
                type="datetime-local" 
                id="memberExpireTime" 
                v-model="editForm.memberExpireTime"
                class="w-full px-3 py-2 border border-gray-300 rounded-md" 
                min="" 
                :min="getCurrentDateTime()"
              />
              <p class="text-xs text-gray-500 mt-1">请设置会员到期时间</p>
            </div>

            <div class="mb-4">
              <label for="userPassword" class="block text-sm font-medium text-gray-700 mb-1">
                密码（留空表示不修改）
              </label>
              <input type="password" id="userPassword" v-model="editForm.userPassword"
                class="w-full px-3 py-2 border border-gray-300 rounded-md" placeholder="请输入新密码" minlength="8"
                maxlength="16" />
            </div>

            <div class="flex justify-end">
              <button type="button" @click="showEditDialog = false"
                class="px-4 py-2 border border-gray-300 rounded-md mr-2 hover:bg-gray-50">
                取消
              </button>
              <button type="submit" class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
                :disabled="loading">
                {{ loading ? '提交中...' : '确认' }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </teleport>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { listUserByPage, updateUser, deleteUser } from '@/api/user'
import type { UserLoginResponse, UserUpdateRequest } from '@/api/userTypes'
import Message from '@/components/Message'
import Confirm from '@/components/Confirm'

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  id: '',
  userName: '',
  userAccount: '',
  userRole: null as number | null
})

// 用户列表数据
const userList = ref<UserLoginResponse[]>([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const hasNextPage = ref(false)
const loading = ref(false)
const jumpPage = ref<number | string>('')
const isChangingPage = ref(false)

// 计算属性
const totalPages = computed(() => Math.ceil(total.value / pageSize.value))

// 编辑表单
const showEditDialog = ref(false)
const editForm = reactive({
  id: '',
  userName: '',
  userProfile: '',
  userRole: 0,
  userPassword: '',
  memberExpireTime: ''
})

// 初始化加载数据
onMounted(() => {
  fetchUserList()
})

// 获取用户列表
const fetchUserList = async () => {
  try {
    loading.value = true
    const params = {
      ...queryParams,
      userRole: queryParams.userRole === null ? undefined : queryParams.userRole
    }

    const res = await listUserByPage(params)
    if (res.statusCode === 200 && res.data) {
      userList.value = res.data.list
      total.value = res.data.total
      currentPage.value = res.data.pageNum
      hasNextPage.value = res.data.hasNext
    }
  } catch (error) {
    console.error('获取用户列表失败', error)
  } finally {
    loading.value = false
  }
}

// 查询
const handleQuery = () => {
  queryParams.pageNum = 1
  fetchUserList()
}

// 重置查询
const resetQuery = () => {
  queryParams.id = ''
  queryParams.userName = ''
  queryParams.userAccount = ''
  queryParams.userRole = null
  queryParams.pageNum = 1
  fetchUserList()
}

// 上一页
const handlePrevPage = () => {
  if (currentPage.value > 1) {
    queryParams.pageNum = currentPage.value - 1
    fetchUserList()
  }
}

// 下一页
const handleNextPage = () => {
  if (hasNextPage.value) {
    queryParams.pageNum = currentPage.value + 1
    fetchUserList()
  }
}

// 分页处理
const handlePageChange = async (page: number) => {
  if (page >= 1 && page <= totalPages.value && !isChangingPage.value) {
    isChangingPage.value = true
    queryParams.pageNum = page
    currentPage.value = page

    // 添加动画延迟
    await new Promise(resolve => setTimeout(resolve, 300))

    await fetchUserList()
    isChangingPage.value = false
  }
}

const handleJumpPage = async () => {
  const page = Number(jumpPage.value)
  if (page >= 1 && page <= totalPages.value && !isChangingPage.value) {
    isChangingPage.value = true
    queryParams.pageNum = page
    currentPage.value = page
    jumpPage.value = ''

    // 添加动画延迟
    await new Promise(resolve => setTimeout(resolve, 300))

    await fetchUserList()
    isChangingPage.value = false
  }
}

const handlePageSizeChange = async () => {
  if (!isChangingPage.value) {
    isChangingPage.value = true
    queryParams.pageSize = pageSize.value
    queryParams.pageNum = 1
    currentPage.value = 1

    // 添加动画延迟
    await new Promise(resolve => setTimeout(resolve, 300))

    await fetchUserList()
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

// 编辑用户
const handleEdit = (user: UserLoginResponse) => {
  editForm.id = user.userId
  editForm.userName = user.userName
  editForm.userProfile = user.userProfile
  editForm.userRole = user.userRole
  editForm.userPassword = ''
  // 处理会员到期时间，将后端格式转换为datetime-local格式用于显示
  if (user.memberExpireTime) {
    editForm.memberExpireTime = formatFromBackendString(user.memberExpireTime)
  } else {
    editForm.memberExpireTime = ''
  }
  showEditDialog.value = true
}

// 提交编辑
const submitEdit = async () => {
  try {
    loading.value = true

    const updateData: UserUpdateRequest = {
      id: editForm.id,
      userName: editForm.userName,
      userProfile: editForm.userProfile,
      userRole: editForm.userRole
    }

    // 如果密码不为空，则更新密码
    if (editForm.userPassword) {
      updateData.userPassword = editForm.userPassword
    }

    // 如果是会员并且设置了到期时间，则传递会员到期时间（转换为后端格式）
    if (editForm.userRole === 2 && editForm.memberExpireTime) {
      updateData.memberExpireTime = formatToBackendString(editForm.memberExpireTime)
    }

    const res = await updateUser(updateData)
    if (res.statusCode === 200) {
      showEditDialog.value = false
      fetchUserList()
      Message.success(res.message || '更新成功')
    }
  } catch (error: any) {
    console.error('更新失败:', error)
  } finally {
    loading.value = false
  }
}

// 删除用户
const handleDelete = async (user: UserLoginResponse) => {
  const confirmed = await Confirm.show({
    title: '确认删除',
    message: `确定要删除用户 "${user.userName}" 吗？此操作不可恢复。`,
    confirmText: '删除',
    cancelText: '取消',
    type: 'danger'
  })

  if (confirmed) {
    try {
      loading.value = true
      const res = await deleteUser(user.userId)
      if (res.statusCode === 200) {
        fetchUserList()
        Message.success(res.message || '删除成功')
      }
    } catch (error: any) {
      console.error('删除失败:', error)
    } finally {
      loading.value = false
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

// 格式化会员到期时间（显示时分秒）
const formatMemberExpireTime = (dateString?: string) => {
  if (!dateString) return ''
  try {
    const date = new Date(dateString)
    return date.toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false
    })
  } catch (error) {
    return dateString
  }
}

// 格式化日期时间为 datetime-local 格式（用于input显示）
const formatDateTimeLocal = (date: Date) => {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  return `${year}-${month}-${day}T${hours}:${minutes}`
}

// 将datetime-local格式转换为后端期望的格式（yyyy-MM-dd HH:mm:ss）
const formatToBackendString = (datetimeLocal: string) => {
  if (!datetimeLocal) return ''
  // datetime-local格式：2025-08-28T10:53
  // 转换为后端格式：2025-08-28 10:53:00
  return datetimeLocal.replace('T', ' ') + ':00'
}

// 将后端格式转换为datetime-local格式
const formatFromBackendString = (backendString: string) => {
  if (!backendString) return ''
  // 后端格式：2025-08-28 10:53:00
  // 转换为datetime-local格式：2025-08-28T10:53
  return backendString.replace(' ', 'T').substring(0, 16)
}

// 获取当前时间（用于设置日期输入框的最小值）
const getCurrentDateTime = () => {
  const now = new Date()
  return formatDateTimeLocal(now)
}

// 检查会员是否过期
const isMemberExpired = (expireTime: string) => {
  if (!expireTime) return false
  const now = new Date()
  const expire = new Date(expireTime)
  return expire < now
}

// 处理角色变更
const handleRoleChange = () => {
  // 如果不是会员，清空会员到期时间
  if (editForm.userRole !== 2) {
    editForm.memberExpireTime = ''
  }
}
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
