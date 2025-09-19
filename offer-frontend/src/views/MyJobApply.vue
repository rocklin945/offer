<template>
  <div class="space-y-6">
    <!-- 页面标题 -->
    <div class="text-center mb-8">
      <h1 class="text-4xl font-bold text-gray-900 mb-2">我的投递记录</h1>
      <p class="text-gray-600">查看和管理您的职位投递记录</p>
    </div>

    <!-- 搜索筛选区域 -->
    <div class="card">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">公司名称</label>
          <input v-model="searchForm.companyName" @keyup.enter="handleSearch" type="text" placeholder="请输入公司名称"
            class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">公司类型</label>
          <select v-model="searchForm.companyType" @change="handleSearch" class="input-field">
            <option value="">全部</option>
            <option value="央国企">央国企</option>
            <option value="外企">外企</option>
            <option value="民企">民企</option>
            <option value="合资">合资</option>
            <option value="事业单位">事业单位</option>
            <option value="其他">其他</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">所属行业</label>
          <input v-model="searchForm.industry" @keyup.enter="handleSearch" type="text" placeholder="请输入所属行业"
            class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">招聘类型</label>
          <select v-model="searchForm.recruitType" @change="handleSearch" class="input-field">
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
          <input v-model="searchForm.workLocation" @keyup.enter="handleSearch" type="text" placeholder="请输入工作地点"
            class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">招聘对象</label>
          <input v-model="searchForm.recruitTarget" @keyup.enter="handleSearch" type="text" placeholder="如：2026年毕业"
            class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">岗位名称</label>
          <input v-model="searchForm.positionName" @keyup.enter="handleSearch" type="text" placeholder="请输入岗位名称"
            class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">投递进度</label>
          <select v-model="searchForm.applicationStatus" @change="handleSearch" class="input-field">
            <option value="">全部进度</option>
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
          <select v-model="searchForm.sortField" class="input-field">
            <option value="">默认排序</option>
            <option value="companyName">公司名称</option>
            <option value="companyType">公司类型</option>
            <option value="industry">所属行业</option>
            <option value="recruitType">招聘类型</option>
            <option value="workLocation">工作地点</option>
            <option value="recruitTarget">招聘对象</option>
            <option value="positionName">岗位名称</option>
            <option value="applicationStatus">投递进度</option>
            <option value="createTime">投递时间</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">排序方式</label>
          <select v-model="searchForm.sortOrder" class="input-field">
            <option value="desc">降序</option>
            <option value="asc">升序</option>
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
      <div v-if="!userStore.currentUser" class="text-center py-8">
        <p class="text-gray-500 mb-4">请先登录后查看我的投递记录</p>
        <button @click="showLoginModal = true"
          class="inline-block px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700">
          去登录
        </button>
      </div>
      <template v-else>
        <div v-if="loading" class="text-center py-8">
          <div class="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-primary-500"></div>
          <p class="mt-2 text-gray-500">加载中...</p>
        </div>

        <div v-else-if="!hasData" class="text-center py-8">
          <p class="text-gray-500">暂无投递记录</p>
          <router-link to="/" class="inline-block mt-4 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700">
            去投递职位
          </router-link>
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
                    class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-48 whitespace-nowrap">
                    岗位名称
                  </th>
                  <th
                    class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-32 whitespace-nowrap">
                    招聘类型
                  </th>
                  <th
                    class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-32 whitespace-nowrap">
                    相关链接
                  </th>
                  <th
                    class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-32 whitespace-nowrap">
                    投递进度
                  </th>
                  <th
                    class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-32 whitespace-nowrap">
                    更新进度
                  </th>
                  <th
                    class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-36 whitespace-nowrap">
                    投递时间
                  </th>
                  <th
                    class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-64 whitespace-normal">
                    个人备注
                    <span class="block">(可以备注个人投递进度链接，方便查询进度)</span>
                  </th>

                  <th
                    class="px-2 py-2 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-32 whitespace-nowrap">
                    操作
                  </th>
                </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200">
                <tr v-for="item in tableData" :key="item.id" class="hover:bg-gray-50">
                  <td class="px-2 py-2 whitespace-nowrap text-center">
                    <div>
                      <div class="text-sm font-medium text-gray-900">{{ item.companyName }}</div>
                      <div class="text-sm text-gray-500">{{ item.companyType || '-' }}</div>
                      <div class="text-sm text-gray-500">{{ item.industry || '-' }}</div>
                    </div>
                  </td>
                  <td class="px-2 py-2 whitespace-nowrap text-center">
                    <div class="text-sm font-medium text-gray-900">{{ item.recruitTarget || '-' }}</div>
                  </td>
                  <td class="px-2 py-2 text-center">
                    <div v-if="item.workLocation && item.workLocation !== '-'"
                      class="flex flex-wrap gap-1 items-center justify-center">
                      <span
                        v-for="(tag, index) in getDisplayTags(item.workLocation, expandedItems[item.id]?.workLocation).visible"
                        :key="index"
                        class="inline-flex px-2 py-1 text-xs font-medium rounded-md cursor-help transition-all duration-200 hover:shadow-md"
                        :class="getPositionTagClass(tag, index)" :title="tag.length > 15 ? tag : ''">
                        {{ truncateText(tag, 15) }}
                      </span>
                      <button v-if="getDisplayTags(item.workLocation, false).hasMore"
                        @click="toggleExpanded(item.id, 'workLocation')"
                        class="inline-flex px-2 py-1 text-xs font-medium rounded-md cursor-pointer bg-gray-200 text-gray-600 hover:bg-gray-300 transition-colors">
                        {{ expandedItems[item.id]?.workLocation ? '收起' : `+${getDisplayTags(item.workLocation,
                          false).remaining.length}` }}
                      </button>
                    </div>
                    <div v-else class="text-sm font-medium text-gray-900">{{ item.workLocation || '-' }}</div>
                  </td>
                  <td class="px-2 py-2 text-center">
                    <div v-if="item.positionName && item.positionName !== '-'"
                      class="flex flex-wrap gap-1 items-center justify-center">
                      <span
                        v-for="(tag, index) in getDisplayTags(item.positionName, expandedItems[item.id]?.positionName).visible"
                        :key="index"
                        class="inline-flex px-2 py-1 text-xs font-medium rounded-md cursor-help transition-all duration-200 hover:shadow-md"
                        :class="getPositionTagClass(tag, index)" :title="tag.length > 15 ? tag : ''">
                        {{ truncateText(tag, 15) }}
                      </span>
                      <button v-if="getDisplayTags(item.positionName, false).hasMore"
                        @click="toggleExpanded(item.id, 'positionName')"
                        class="inline-flex px-2 py-1 text-xs font-medium rounded-md cursor-pointer bg-gray-200 text-gray-600 hover:bg-gray-300 transition-colors">
                        {{ expandedItems[item.id]?.positionName ? '收起' : `+${getDisplayTags(item.positionName,
                          false).remaining.length}` }}
                      </button>
                    </div>
                    <div v-else class="text-sm font-medium text-gray-900">{{ item.positionName || '-' }}</div>
                  </td>
                  <td class="px-2 py-2 whitespace-nowrap text-center">
                    <span class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      :class="getRecruitTypeClass(item.recruitType)">
                      {{ item.recruitType || '-' }}
                    </span>
                  </td>
                  <td class="px-2 py-2 whitespace-nowrap text-center">
                    <a v-if="item.relatedLink" :href="item.relatedLink" target="_blank"
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
                    <span :class="getStatusClass(item.applicationStatus)"
                      class="inline-flex px-2 py-1 text-xs font-semibold rounded-full">
                      {{ item.applicationStatus }}
                    </span>
                  </td>
                  <td class="px-2 py-2 whitespace-nowrap text-center">
                    <div class="relative inline-block">
                      <button @click="toggleStatusDropdown(item.id)"
                        class="btn-secondary flex items-center text-xs py-1 px-2">
                        更新进度
                        <svg class="w-3 h-3 ml-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7">
                          </path>
                        </svg>
                      </button>
                      <!-- 下拉列表 -->
                      <div v-if="showStatusDropdown[item.id]"
                        class="absolute z-10 mt-1 w-32 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none">
                        <div class="py-1">
                          <button @click="updateStatus(item.id, '未投递')"
                            class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">未投递</button>
                          <button @click="updateStatus(item.id, '已投递')"
                            class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">已投递</button>
                          <button @click="updateStatus(item.id, '笔试')"
                            class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">笔试</button>
                          <button @click="updateStatus(item.id, '一面')"
                            class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">一面</button>
                          <button @click="updateStatus(item.id, '二面')"
                            class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">二面</button>
                          <button @click="updateStatus(item.id, 'HR面')"
                            class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">HR面</button>
                          <button @click="updateStatus(item.id, '已通过')"
                            class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">已通过</button>
                          <button @click="updateStatus(item.id, '已拒绝')"
                            class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">已拒绝</button>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="px-2 py-2 whitespace-nowrap text-center text-sm text-gray-500">
                    {{ formatDate(item.createTime) }}
                  </td>
                  <td class="px-2 py-2 text-center">
                    <div v-if="item.personalNote" class="text-sm text-gray-700 max-w-xs mx-auto break-words"
                      v-html="renderLinksInText(item.personalNote)"></div>
                    <div v-else class="text-sm text-gray-500">-</div>
                  </td>
                  <td class="px-2 py-2 whitespace-nowrap text-center">
                    <div class="flex justify-center space-x-2">
                      <button @click="handleEditNote(item)" class="text-blue-600 hover:text-blue-900 text-sm">
                        修改备注
                      </button>
                      <button @click="handleDelete(item)" class="text-red-600 hover:text-red-900 text-sm">
                        删除
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </template>
    </div>

    <!-- 分页 -->
    <div v-if="hasData && totalPages > 1"
      class="pagination flex flex-col gap-3 sm:flex-row sm:justify-between sm:items-center">
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
          <button @click="handleJumpPage" :disabled="!jumpPage || jumpPage < 1 || jumpPage > totalPages"
            class="px-2 py-1 text-xs sm:px-3 sm:py-1 sm:text-sm bg-blue-600 text-white rounded hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:bg-blue-600 transition-colors">
            跳转
          </button>
        </div>
      </div>
    </div>

    <!-- 修改备注弹窗 -->
    <teleport to="body">
      <div v-if="showEditNoteModal">
        <div class="modal-backdrop fixed inset-0 bg-black bg-opacity-50" style="z-index: 99999;"></div>
        <div class="modal-container fixed inset-0 flex items-center justify-center" style="z-index: 100000;"
          @click.self="showEditNoteModal = false">
          <div class="bg-white rounded-lg p-6 w-96 max-h-[90vh] overflow-y-auto">
            <h3 class="text-lg font-medium text-gray-900 mb-4">修改个人备注</h3>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700 mb-2">个人备注</label>
              <div class="relative">
                <textarea v-model="editNoteForm.personalNote" class="input-field h-24 resize-none"
                  placeholder="添加个人备注，如投递进度链接等（最多500字）" maxlength="500"></textarea>
                <div class="absolute bottom-2 right-2 text-xs text-gray-500">
                  {{ editNoteForm.personalNote?.length || 0 }}/500
                </div>
              </div>
            </div>
            <div class="flex justify-end space-x-2">
              <button @click="showEditNoteModal = false" class="btn-secondary">
                取消
              </button>
              <button @click="confirmEditNote" class="btn-primary">
                确认
              </button>
            </div>
          </div>
        </div>
      </div>
    </teleport>

    <!-- 登录模态框 -->
    <LoginModal v-if="showLoginModal" @close="showLoginModal = false" @login-success="handleLoginSuccess" />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted, onActivated } from 'vue'
import { userJobApplyApi, type UserJobApplyDTO, type UserJobApplyQueryRequest } from '../api/userJobApply'
import Message from '../components/Message'
import Confirm from '../components/Confirm'
import { useUserStore } from '@/stores/user'
import LoginModal from '@/components/LoginModal.vue'

const userStore = useUserStore()
// 响应式数据
const currentPage = ref(1)
const pageSize = ref(10)
const tableData = ref<UserJobApplyDTO[]>([])
const total = ref(0)
const loading = ref(false)
const jumpPage = ref<number | string>('')
const isChangingPage = ref(false)
const showLoginModal = ref(false)

// 添加展开进度管理
const expandedItems = ref<{ [key: string]: { workLocation: boolean, positionName: boolean } }>({})

// 搜索表单
const searchForm = reactive<UserJobApplyQueryRequest>({
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

// 更新进度相关
const showUpdateModal = ref(false)
const showEditNoteModal = ref(false)
const showStatusDropdown = ref<{ [key: number]: boolean }>({})
const updateForm = reactive({
  id: 0,
  applicationStatus: '',
  personalNote: ''
})

const editNoteForm = reactive({
  id: 0,
  personalNote: ''
})

// 计算属性
const hasData = computed(() => tableData.value.length > 0)
const totalPages = computed(() => Math.ceil(total.value / pageSize.value))

// 获取数据
const fetchData = async () => {
  // 如果用户未登录，直接返回
  if (!userStore.currentUser) {
    return
  }

  loading.value = true
  try {
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

    const response = await userJobApplyApi.getPage(params)

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
  if (!userStore.currentUser) {
    return
  }
  currentPage.value = 1
  await fetchData()
}

// 重置搜索
const resetSearch = () => {
  if (!userStore.currentUser) {
    return
  }
  Object.assign(searchForm, {
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
  handleSearch()
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
const getDisplayTags = (text?: string, isExpanded?: boolean) => {
  if (!text) return { visible: [], remaining: [], hasMore: false }

  const allTags = getPositionTags(text)

  // 如果只有一个标签且不包含空格，也要显示为标签
  if (allTags.length === 0 && text.trim()) {
    allTags.push(text.trim())
  }

  const maxVisible = isExpanded ? allTags.length : 3
  const visible = allTags.slice(0, maxVisible)
  const remaining = allTags.slice(maxVisible)
  const hasMore = allTags.length > 3

  return {
    visible,
    remaining,
    hasMore
  }
}

// 切换展开进度
const toggleExpanded = (itemId: number, field: 'workLocation' | 'positionName') => {
  const key = itemId.toString()
  if (!expandedItems.value[key]) {
    expandedItems.value[key] = { workLocation: false, positionName: false }
  }
  expandedItems.value[key][field] = !expandedItems.value[key][field]
}

// 切换下拉列表显示
const toggleStatusDropdown = (itemId: number) => {
  // 首先关闭所有其他下拉列表
  Object.keys(showStatusDropdown.value).forEach(key => {
    if (parseInt(key) !== itemId) {
      showStatusDropdown.value[parseInt(key)] = false
    }
  })

  // 切换当前项的下拉列表
  showStatusDropdown.value[itemId] = !showStatusDropdown.value[itemId]
}

// 更新进度
const updateStatus = async (itemId: number, status: string) => {
  try {
    const requestData = {
      id: itemId,
      applicationStatus: status
    }

    const response = await userJobApplyApi.update(requestData)
    if (response.data.statusCode === 200) {
      // 更新本地数据
      const index = tableData.value.findIndex(item => item.id === itemId)
      if (index !== -1) {
        tableData.value[index].applicationStatus = status
      }

      Message.success('进度更新成功')
      // 关闭下拉列表
      showStatusDropdown.value[itemId] = false
    } else {
      Message.error(response.data.message || '更新失败')
    }
  } catch (error: any) {
    console.error('更新进度失败:', error)
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

// 修改备注
const handleEditNote = (item: UserJobApplyDTO) => {
  editNoteForm.id = item.id
  editNoteForm.personalNote = item.personalNote || ''
  showEditNoteModal.value = true
}

// 确认修改备注
const confirmEditNote = async () => {
  try {
    const requestData = {
      id: editNoteForm.id,
      personalNote: editNoteForm.personalNote
    }

    const response = await userJobApplyApi.update(requestData)
    if (response.data.statusCode === 200) {
      // 更新本地数据
      const index = tableData.value.findIndex(item => item.id === editNoteForm.id)
      if (index !== -1) {
        tableData.value[index].personalNote = editNoteForm.personalNote
      }

      Message.success('备注更新成功')
      showEditNoteModal.value = false
    } else {
      Message.error(response.data.message || '更新失败')
    }
  } catch (error: any) {
    console.error('更新备注失败:', error)
    Message.error('更新备注失败')
  }
}

// 删除
const handleDelete = async (item: UserJobApplyDTO) => {
  const confirmed = await Confirm.show({
    title: '确认删除',
    message: `确定要删除对 ${item.companyName} - ${item.positionName} 的投递记录吗？`,
    confirmText: '删除',
    cancelText: '取消',
    type: 'danger'
  })

  if (confirmed) {
    try {
      const response = await userJobApplyApi.delete(item.id)
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

// 获取进度样式
const getStatusClass = (status: string) => {
  switch (status) {
    case '已投递':
      return 'bg-yellow-100 text-yellow-800'
    case '简历筛选':
      return 'bg-yellow-100 text-yellow-800'
    case '笔试':
      return 'bg-purple-100 text-purple-800'
    case '一面':
      return 'bg-indigo-100 text-indigo-800'
    case '二面':
      return 'bg-cyan-100 text-cyan-800'
    case 'HR面':
      return 'bg-orange-100 text-orange-800'
    case '面试':
      return 'bg-indigo-100 text-indigo-800'
    case '终面':
      return 'bg-pink-100 text-pink-800'
    case '已通过':
      return 'bg-green-100 text-green-800'
    case '已拒绝':
      return 'bg-red-100 text-red-800'
    default:
      return 'bg-gray-100 text-gray-800'
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

// 登录成功后的处理
const handleLoginSuccess = () => {
  showLoginModal.value = false
  // 登录成功后获取数据
  fetchData()
}

// 页面加载时获取数据（仅在已登录时）
onMounted(() => {
  if (userStore.currentUser) {
    fetchData()
  }
})

// 组件激活时重新加载数据
onActivated(() => {
  // 重新获取用户信息
  if (userStore.token && !userStore.currentUser) {
    userStore.initUserInfo()
  }

  // 重新获取投递记录数据
  if (userStore.currentUser) {
    fetchData()
  }
})
</script>

<style scoped>
/* 表格容器动画 - 和JobList页面一样的简单动画 */
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

/* 弹窗样式 */
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
}

.modal-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
}
</style>
