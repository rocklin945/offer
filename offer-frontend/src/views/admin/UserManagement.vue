<template>
  <div class="bg-white rounded-lg shadow p-6">
    <h1 class="text-2xl font-bold mb-6">用户管理</h1>
    
    <!-- 搜索筛选区域 -->
    <div class="mb-6 bg-gray-50 p-4 rounded-lg">
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">用户ID</label>
          <input
            v-model="queryParams.id"
            type="text"
            class="w-full px-3 py-2 border border-gray-300 rounded-md"
            placeholder="请输入用户ID"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">用户名</label>
          <input
            v-model="queryParams.userName"
            type="text"
            class="w-full px-3 py-2 border border-gray-300 rounded-md"
            placeholder="请输入用户名"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">账号</label>
          <input
            v-model="queryParams.userAccount"
            type="text"
            class="w-full px-3 py-2 border border-gray-300 rounded-md"
            placeholder="请输入账号"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">用户角色</label>
          <select
            v-model="queryParams.userRole"
            class="w-full px-3 py-2 border border-gray-300 rounded-md"
          >
            <option value="">全部</option>
            <option :value="0">管理员</option>
            <option :value="1">普通用户</option>
            <option :value="2">会员</option>
          </select>
        </div>
      </div>
      <div class="flex justify-end mt-4">
        <button
          @click="resetQuery"
          class="px-4 py-2 border border-gray-300 rounded-md mr-2 hover:bg-gray-50"
        >
          重置
        </button>
        <button
          @click="handleQuery"
          class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
        >
          查询
        </button>
      </div>
    </div>
    
    <!-- 用户列表 -->
    <div class="overflow-x-auto">
      <table class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              ID
            </th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              头像
            </th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              用户名
            </th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              账号
            </th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              角色
            </th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              创建时间
            </th>
            <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              操作
            </th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="user in userList" :key="user.userId">
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ user.userId }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <img :src="user.userAvatar" alt="头像" class="h-10 w-10 rounded-full" />
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
              {{ user.userName }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ user.userAccount }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              <span
                :class="[
                  'px-2 py-1 text-xs font-medium rounded-full',
                  user.userRole === 0 ? 'bg-blue-100 text-blue-800' : 'bg-gray-100 text-gray-800'
                ]"
              >
                {{ user.userRole === 0 ? '管理员' : '普通用户' }}
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
              {{ user.createTime }}
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
              <button
                @click="handleEdit(user)"
                class="text-blue-600 hover:text-blue-900 mr-3"
              >
                编辑
              </button>
              <button
                @click="handleDelete(user)"
                class="text-red-600 hover:text-red-900"
              >
                删除
              </button>
            </td>
          </tr>
          <tr v-if="userList.length === 0">
            <td colspan="7" class="px-6 py-4 text-center text-sm text-gray-500">
              暂无数据
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <!-- 分页 -->
    <div class="flex justify-between items-center mt-6">
      <div class="text-sm text-gray-700">
        共 <span class="font-medium">{{ total }}</span> 条记录
      </div>
      <div class="flex space-x-2">
        <button
          @click="handlePrevPage"
          :disabled="currentPage === 1"
          :class="[
            'px-3 py-1 border rounded-md',
            currentPage === 1
              ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
              : 'bg-white text-gray-700 hover:bg-gray-50'
          ]"
        >
          上一页
        </button>
        <button
          @click="handleNextPage"
          :disabled="!hasNextPage"
          :class="[
            'px-3 py-1 border rounded-md',
            !hasNextPage
              ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
              : 'bg-white text-gray-700 hover:bg-gray-50'
          ]"
        >
          下一页
        </button>
      </div>
    </div>
    
    <!-- 编辑用户对话框 -->
    <div v-if="showEditDialog" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-lg shadow-xl w-full max-w-md p-6">
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
            <input
              type="text"
              id="userName"
              v-model="editForm.userName"
              class="w-full px-3 py-2 border border-gray-300 rounded-md"
              required
            />
          </div>
          
          <div class="mb-4">
            <label for="userProfile" class="block text-sm font-medium text-gray-700 mb-1">用户简介</label>
            <textarea
              id="userProfile"
              v-model="editForm.userProfile"
              class="w-full px-3 py-2 border border-gray-300 rounded-md"
              rows="3"
            ></textarea>
          </div>
          
          <div class="mb-4">
            <label for="userRole" class="block text-sm font-medium text-gray-700 mb-1">用户角色</label>
            <select
              id="userRole"
              v-model="editForm.userRole"
              class="w-full px-3 py-2 border border-gray-300 rounded-md"
              required
            >
              <option :value="0">管理员</option>
              <option :value="1">普通用户</option>
            </select>
          </div>
          
          <div class="mb-4">
            <label for="userPassword" class="block text-sm font-medium text-gray-700 mb-1">
              密码（留空表示不修改）
            </label>
            <input
              type="password"
              id="userPassword"
              v-model="editForm.userPassword"
              class="w-full px-3 py-2 border border-gray-300 rounded-md"
              placeholder="请输入新密码"
              minlength="8"
              maxlength="16"
            />
          </div>
          
          <div class="flex justify-end">
            <button
              type="button"
              @click="showEditDialog = false"
              class="px-4 py-2 border border-gray-300 rounded-md mr-2 hover:bg-gray-50"
            >
              取消
            </button>
            <button
              type="submit"
              class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
              :disabled="loading"
            >
              {{ loading ? '提交中...' : '确认' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { listUserByPage, updateUser, deleteUser } from '@/api/user'
import type { UserLoginResponse, UserUpdateRequest } from '@/api/userTypes'
import Message from '@/components/Message'

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
const hasNextPage = ref(false)
const loading = ref(false)

// 编辑表单
const showEditDialog = ref(false)
const editForm = reactive({
  id: '',
  userName: '',
  userProfile: '',
  userRole: 0,
  userPassword: ''
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

// 编辑用户
const handleEdit = (user: UserLoginResponse) => {
  editForm.id = user.userId
  editForm.userName = user.userName
  editForm.userProfile = user.userProfile
  editForm.userRole = user.userRole
  editForm.userPassword = ''
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
    
    const res = await updateUser(updateData)
    if (res.statusCode === 200) {
      showEditDialog.value = false
      fetchUserList()
      Message.success(res.message || '更新成功')
    }
  } catch (error: any) {
    Message.error(error.response?.data?.message || `更新失败: ${error.message || '未知错误'}`)
  } finally {
    loading.value = false
  }
}

// 删除用户
const handleDelete = async (user: UserLoginResponse) => {
  if (!confirm(`确定要删除用户 "${user.userName}" 吗？`)) {
    return
  }
  
  try {
    loading.value = true
    const res = await deleteUser(user.userId)
    if (res.statusCode === 200) {
      fetchUserList()
      Message.success(res.message || '删除成功')
    }
  } catch (error: any) {
    Message.error(error.response?.data?.message || `删除失败: ${error.message || '未知错误'}`)
  } finally {
    loading.value = false
  }
}
</script>