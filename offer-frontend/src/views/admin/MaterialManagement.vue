<template>
  <div class="space-y-6">
    <!-- 标题与操作 -->
    <div class="flex justify-between items-center">
      <h2 class="text-2xl font-bold text-gray-900">资料管理</h2>
      <button class="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md transition-colors"
        @click="showUpload = true">
        导入PDF
      </button>
    </div>

    <!-- 搜索筛选区域 -->
    <div class="bg-white rounded-lg border border-gray-200 p-6">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">关键词</label>
          <input v-model="query.keyword" type="text" placeholder="文件名关键字"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">分类</label>
          <select v-model="query.category"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="">全部</option>
            <option v-for="c in categories" :key="c" :value="c">{{ c }}</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">排序字段</label>
          <select v-model="query.sortField"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="">默认</option>
            <option value="createTime">创建时间</option>
            <option value="viewCount">浏览量</option>
            <option value="fileSize">文件大小</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">排序方式</label>
          <select v-model="query.sortOrder"
            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
            <option value="">默认</option>
            <option value="asc">从小到大</option>
            <option value="desc">从大到小</option>
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

      <div v-else-if="list.length === 0" class="text-center py-12">
        <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
            d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
        </svg>
        <p class="mt-2 text-gray-500">暂无数据</p>
      </div>

      <div v-else class="overflow-x-auto">
        <div class="table-container" :class="{ 'table-loading': isChangingPage }">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">名称</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">分类</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">页数</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">大小/MB</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">浏览量</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">创建时间</th>
                <th class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">操作</th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="m in list" :key="m.id" class="hover:bg-gray-50">
                <td class="px-4 py-4 text-center text-sm text-gray-500">{{ m.id }}</td>
                <td class="px-4 py-4 text-center text-sm font-medium text-gray-900" :title="m.fileName">{{ m.fileName }}
                </td>
                <td class="px-4 py-4 text-center">
                  <span class="inline-flex px-2 py-1 text-xs font-semibold rounded-full bg-indigo-100 text-indigo-800">
                    {{ m.category }}
                  </span>
                </td>
                <td class="px-4 py-4 text-center text-sm text-gray-500">{{ m.totalPages ?? '-' }}</td>
                <td class="px-4 py-4 text-center text-sm text-gray-500">{{ m.fileSize }}</td>
                <td class="px-4 py-4 text-center text-sm text-gray-500">{{ m.viewCount }}</td>
                <td class="px-4 py-4 text-center text-sm text-gray-500">{{ formatDate(m.createTime) }}</td>
                <td class="px-4 py-4 text-center">
                  <button @click="handleDelete(m.id, m.fileName)"
                    class="text-red-500 hover:text-red-700 p-1 rounded-full hover:bg-red-50 transition-colors"
                    title="删除">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1-1v3M4 7h16" />
                    </svg>
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 分页 -->
      <div v-if="list.length > 0" class="bg-white px-4 py-3 border-t border-gray-200 sm:px-6">
        <div class="flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
          <div class="text-sm text-gray-700">共 {{ total }} 条记录</div>
          <div class="flex flex-wrap items-center gap-2 sm:flex-nowrap sm:space-x-2">
            <button @click="handlePageChange(currentPage - 1)" :disabled="currentPage <= 1"
              class="px-2 py-1 text-xs sm:px-3 sm:py-2 sm:text-sm border border-gray-300 rounded hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:bg-white">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path>
              </svg>
            </button>

            <template v-for="page in getPageNumbers()" :key="page">
              <button v-if="page === '...'" disabled
                class="px-2 py-1 text-xs sm:px-3 sm:py-2 sm:text-sm text-gray-400 cursor-default">...</button>
              <button v-else @click="handlePageChange(page as number)" :class="[
                'px-2 py-1 text-xs sm:px-3 sm:py-2 sm:text-sm border rounded transition-colors',
                currentPage === page ? 'bg-blue-600 text-white border-blue-600' : 'border-gray-300 hover:bg-gray-50'
              ]">
                {{ page }}
              </button>
            </template>

            <button @click="handlePageChange(currentPage + 1)" :disabled="currentPage >= totalPages"
              class="px-2 py-1 text-xs sm:px-3 sm:py-2 sm:text-sm border border-gray-300 rounded hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:bg-white">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
              </svg>
            </button>

            <div class="flex items-center gap-2 ml-0 sm:ml-4 w-full sm:w-auto flex-wrap">
              <span class="text-sm text-gray-700">跳至</span>
              <input v-model="jumpPage" @keyup.enter="handleJumpPage" type="number" min="1" :max="totalPages"
                class="w-14 sm:w-16 px-2 py-1 text-xs sm:text-sm border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
              <span class="text-sm text-gray-700">/ {{ totalPages }}</span>
              <button @click="handleJumpPage"
                class="px-2 py-1 text-xs sm:px-3 sm:py-2 sm:text-sm border border-gray-300 rounded hover:bg-gray-50">确定</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 导入PDF对话框 -->
    <div v-if="showUpload" class="fixed inset-0 bg-black/50 z-50 flex items-center justify-center p-4"
      @click.self="showUpload = false">
      <div class="bg-white rounded-lg w-full max-w-md overflow-hidden">
        <div class="px-4 py-3 border-b flex items-center justify-between">
          <h3 class="text-base font-semibold text-gray-900">导入PDF</h3>
          <button class="text-gray-500 hover:text-gray-700" @click="showUpload = false">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
        <div class="p-4 space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">选择或输入分类</label>
            <div class="relative">
              <select v-model="uploadCategory" @change="handleCategoryChange"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent appearance-none">
                <option value="" disabled>请选择或输入分类</option>
                <option v-for="c in categories" :key="c" :value="c">{{ c }}</option>
              </select>
              <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                <svg class="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                  <path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z" />
                </svg>
              </div>
            </div>
            <input v-model="uploadCategory" type="text" placeholder="或直接输入新分类"
              class="w-full mt-2 px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">选择PDF文件</label>
            <input type="file" accept="application/pdf" @change="onFileChange" class="w-full" />
            <p v-if="uploadFileName" class="text-sm text-gray-500 mt-1">已选择：{{ uploadFileName }}</p>
          </div>
        </div>
        <div class="px-4 py-3 border-t flex justify-end gap-2">
          <button class="px-4 py-2 text-gray-700 bg-gray-100 rounded hover:bg-gray-200"
            @click="showUpload = false">取消</button>
          <button class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700" :disabled="uploading"
            @click="submitUpload">
            {{ uploading ? '上传中...' : '开始上传' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { listMaterials, getMaterialCategories, uploadPdf, deleteMaterial, type Material, type PageResponse } from '@/api/Materials'
import Confirm from '@/components/Confirm'
import Message from '@/components/Message'

// 列表与查询
const list = ref<Material[]>([])
const loading = ref(false)
const isChangingPage = ref(false)
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const totalPages = computed(() => Math.max(1, Math.ceil(total.value / pageSize.value)))

const query = ref<{
  keyword: string
  category: string
  sortField: string
  sortOrder: string
}>({
  keyword: '',
  category: '',
  sortField: '',
  sortOrder: ''
})

const categories = ref<string[]>([])
const jumpPage = ref<number | ''>('')

// 获取列表
const fetchList = async () => {
  loading.value = true
  try {
    const resp: PageResponse<Material> = await listMaterials({
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      keyword: query.value.keyword || undefined,
      category: query.value.category || undefined,
      sortField: query.value.sortField || undefined,
      sortOrder: query.value.sortOrder || undefined
    })
    list.value = resp.list || []
    total.value = Number(resp.total || 0)
  } catch (e) {
    console.error('获取资料列表失败', e)
    list.value = []
    total.value = 0
  } finally {
    loading.value = false
    isChangingPage.value = false
  }
}

const handleQuery = () => {
  currentPage.value = 1
  fetchList()
}

const resetQuery = () => {
  query.value = { keyword: '', category: '', sortField: '', sortOrder: '' }
  currentPage.value = 1
  fetchList()
}

const handlePageChange = (page: number) => {
  if (page < 1 || page > totalPages.value || page === currentPage.value) return
  isChangingPage.value = true
  currentPage.value = page
  fetchList()
}

const getPageNumbers = (): Array<number | string> => {
  const totalP = totalPages.value
  const current = currentPage.value
  const pages: Array<number | string> = []
  if (totalP <= 7) {
    for (let i = 1; i <= totalP; i++) pages.push(i)
    return pages
  }
  const add = (p: number | string) => pages.push(p)
  add(1)
  if (current > 4) add('...')
  const start = Math.max(2, current - 1)
  const end = Math.min(totalP - 1, current + 1)
  for (let i = start; i <= end; i++) add(i)
  if (current < totalP - 3) add('...')
  add(totalP)
  return pages
}

const handleJumpPage = () => {
  const n = Number(jumpPage.value)
  if (!n || n < 1 || n > totalPages.value) return
  handlePageChange(n)
  jumpPage.value = ''
}

// 工具
const formatDate = (ts: number) => {
  if (!ts) return '-'
  const d = new Date(ts)
  const yyyy = d.getFullYear()
  const mm = String(d.getMonth() + 1).padStart(2, '0')
  const dd = String(d.getDate()).padStart(2, '0')
  const hh = String(d.getHours()).padStart(2, '0')
  const mi = String(d.getMinutes()).padStart(2, '0')
  return `${yyyy}-${mm}-${dd} ${hh}:${mi}`
}

// 删除资料
const handleDelete = async (id: string, fileName: string) => {
  const result = await Confirm.danger(`确定要删除资料 "${fileName}" 吗？此操作不可恢复。`, '删除确认')
  if (result) {
    try {
      const success = await deleteMaterial(id)
      if (success) {
        Message.success('删除成功')
        // 重新加载列表
        fetchList()
      } else {
        Message.error('删除失败')
      }
    } catch (e) {
      console.error('删除失败', e)
      Message.error('删除失败，请重试')
    }
  }
}

// 上传
const showUpload = ref(false)
const uploadFileObj = ref<File | null>(null)
const uploadFileName = ref('')
const uploadCategory = ref('')
const uploading = ref(false)

// 添加处理分类变化的方法
const handleCategoryChange = (e: Event) => {
  const target = e.target as HTMLSelectElement
  if (target.value) {
    uploadCategory.value = target.value
  }
}

const onFileChange = (e: Event) => {
  const input = e.target as HTMLInputElement
  const file = input.files && input.files[0]
  if (file) {
    if (!file.name.toLowerCase().endsWith('.pdf')) {
      alert('仅支持PDF文件')
      return
    }
    uploadFileObj.value = file
    uploadFileName.value = file.name
  }
}

const submitUpload = async () => {
  if (!uploadCategory.value) {
    Message.warning('请选择分类')
    return
  }
  if (!uploadFileObj.value) {
    Message.warning('请选择PDF文件')
    return
  }
  uploading.value = true
  try {
    const msg = await uploadPdf(uploadFileObj.value, uploadCategory.value)
    // 上传成功后刷新列表
    showUpload.value = false
    uploadFileObj.value = null
    uploadFileName.value = ''
    uploadCategory.value = ''
    Message.success(typeof msg === 'string' ? msg : '上传成功')
    fetchList()
  } catch (e) {
    console.error('上传失败', e)
    Message.error('上传失败，请重试')
  } finally {
    uploading.value = false
  }
}

onMounted(async () => {
  // 分类
  try {
    categories.value = await getMaterialCategories()
  } catch {
    categories.value = []
  }
  // 列表
  fetchList()
})
</script>