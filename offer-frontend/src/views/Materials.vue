<template>
  <div class="max-w-6xl mx-auto">
    <!-- 标题与分类 -->
    <div class="mb-6">
      <h2 class="text-2xl font-bold text-gray-900 mb-3">笔试面试资料</h2>
      <div class="flex flex-wrap gap-2">
        <button
          :class="['px-3 py-1.5 rounded-full text-sm transition-colors', selectedCategory === '' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200']"
          @click="changeCategory('')">
          全部
        </button>
        <button v-for="c in categories" :key="c" @click="changeCategory(c)"
          :class="['px-3 py-1.5 rounded-full text-sm transition-colors', selectedCategory === c ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200']">
          {{ c }}
        </button>
      </div>
    </div>

    <!-- 空状态 -->
    <div v-if="!loading && materials.length === 0"
      class="bg-white border border-gray-200 rounded-lg p-8 text-center text-gray-500">
      暂无资料
    </div>

    <!-- 网格布局 + 分类左右切换动画 -->
    <transition name="slide-x" mode="out-in">
      <div :key="selectedCategory" class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4">
        <div v-for="m in materials" :key="m.id" class="break-inside-avoid">
          <div
            class="group relative rounded-lg overflow-hidden shadow-sm hover:shadow transition cursor-pointer transform hover:scale-105 border-2 border-transparent"
            @click="openPreview(m)" :ref="el => observeCard(el, m)" :class="getCardBgClass(m.category)">
            <!-- A4 比例容器 -->
            <div class="relative w-full bg-white/90" style="padding-top: 141.42%;">
              <img :src="srcMap[m.id] || placeholder" :alt="m.fileName"
                class="absolute inset-0 w-full h-full block object-contain" loading="lazy" @error="onImgError(m)" />
              <!-- 种类标签 -->
              <div v-if="m.category"
                :class="['absolute top-2 right-2 text-xs font-medium rounded-full px-2 py-1', getCategoryTagClass(m.category)]">
                {{ m.category }}
              </div>
            </div>
            <div class="absolute inset-x-0 bottom-0 bg-gradient-to-t from-black/60 to-transparent p-2 text-white">
              <div class="text-base font-bold truncate text-gray-700" :title="m.fileName">{{ m.fileName }}</div>
              <div class="text-[10px] opacity-90 mt-0.5 flex items-center justify-between">
                <span></span>
                <span v-if="m.totalPages">共 {{ m.totalPages }} 页</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </transition>

    <!-- 无限滚动观察哨 -->
    <div ref="sentinelRef" class="h-10"></div>

    <!-- 预览弹窗 -->
    <teleport to="body">
      <div v-if="previewing" class="fixed inset-0 z-50 flex items-center justify-center px-4 overflow-y-auto"
        style="z-index: 1000000;">
        <!-- 遮罩层 - 参考RedeemMemberModal.vue -->
        <div class="absolute inset-0 bg-black transition-opacity duration-300"
          :class="previewing ? 'bg-opacity-70' : 'bg-opacity-0'" @click="closePreview">
        </div>

        <!-- 预览内容 -->
        <div
          class="relative bg-white rounded-lg max-w-7xl w-full max-h-[95vh] overflow-hidden transform transition-all duration-300 ease-out my-4 preview-container"
          :class="previewing ? 'scale-100 opacity-100 translate-y-0' : 'scale-95 opacity-0 translate-y-4'">
          <div class="flex items-center justify-between px-6 py-3 border-b preview-header">
            <div class="flex items-center space-x-4">
              <h3 class="text-lg font-semibold text-gray-900 truncate">{{ previewItem?.fileName }}</h3>
              <span class="text-sm text-gray-500">共 {{ previewItem?.totalPages || 0 }} 页</span>
            </div>
            <div class="flex items-center space-x-2">
              <button class="text-gray-500 hover:text-gray-700" @click.stop="closePreview">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            </div>
          </div>
          <!-- 限制消息提示 -->
          <div v-if="limitMessage" class="bg-yellow-50 border-l-4 border-yellow-400 p-4 mx-6 mt-4">
            <div class="flex">
              <div class="flex-shrink-0">
                <svg class="h-5 w-5 text-yellow-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
                  fill="currentColor">
                  <path fill-rule="evenodd"
                    d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z"
                    clip-rule="evenodd" />
                </svg>
              </div>
              <div class="ml-3">
                <p class="text-sm text-yellow-700">
                  {{ limitMessage }}
                </p>
              </div>
            </div>
          </div>
          <div class="p-4 overflow-auto max-h-[90vh] preview-content">
            <!-- 顶部无限滚动观察哨 -->
            <div ref="topPageSentinelRef" class="h-10"></div>

            <!-- 单页无限滚动预览 -->
            <div class="space-y-6">
              <div v-for="page in visiblePages" :key="page" :data-page="page"
                class="relative bg-white border border-gray-200 rounded-lg overflow-hidden shadow-sm mx-auto"
                style="max-width: 800px;">
                <!-- A4 比例容器 -->
                <div class="relative w-full bg-white" style="padding-top: 141.42%;">
                  <img v-if="getPageImage(previewItem!, page)" :src="getPageImage(previewItem!, page)"
                    :alt="`${previewItem?.fileName} - 第 ${page} 页`"
                    class="absolute inset-0 w-full h-full block object-contain bg-white" loading="lazy"
                    @error="onPreviewImgError(previewItem!, page)" />
                  <!-- 加载动画 -->
                  <div v-else class="absolute inset-0 flex items-center justify-center bg-gray-100">
                    <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
                  </div>
                </div>
                <div class="absolute bottom-2 right-2 bg-black/60 text-white text-xs px-2 py-1 rounded">
                  第 {{ page }} 页
                </div>
              </div>
            </div>

            <!-- 无限滚动观察哨 -->
            <div v-if="hasMorePages && !limitMessage" ref="pageSentinelRef"
              class="h-20 flex items-center justify-center">
              <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            </div>
            <div v-else-if="loadingPages" class="h-20 flex items-center justify-center text-gray-500">
              加载中...
            </div>
            <div v-else-if="!hasMorePages && !limitMessage && !limitMessageShown"
              class="h-20 flex items-center justify-center text-gray-500">
              已加载所有页面
            </div>
            <div v-else-if="limitMessage" class="h-20 flex items-center justify-center text-gray-500">
              {{ limitMessage }}
            </div>
          </div>

          <!-- 右下角页面跳转功能 -->
          <div class="absolute bottom-6 right-6 flex items-center space-x-2">
            <input v-model="jumpPageInput" type="number" min="1" :max="previewItem?.totalPages || 1" placeholder="页码"
              class="w-20 px-3 py-2 border border-gray-300 rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              @keyup.enter="jumpToPage" />
            <button @click="jumpToPage"
              class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors text-sm">
              跳转
            </button>
          </div>
        </div>
      </div>
    </teleport>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'
import type { Material, PageResponse } from '@/api/Materials'
import { listMaterials, getMaterialCategories, buildPdfPreviewUrl } from '@/api/Materials'
import axios from 'axios' // 导入原生axios实例用于图片请求

const materials = ref<Material[]>([])
const categories = ref<string[]>([])
const selectedCategory = ref<string>('')

const pageNum = ref(1)
const pageSize = ref(12)
const total = ref(0)
const hasMore = ref(true)
const loading = ref(false)
const autoLoading = ref(true) // IntersectionObserver自动加载
const sentinelRef = ref<HTMLDivElement | null>(null)
let observer: IntersectionObserver | null = null

const previewing = ref(false)
const previewItem = ref<Material | null>(null)
const srcMap = ref<Record<string, string>>({})
const pageSrcMap = ref<Record<string, Record<number, string>>>({})
const fading = ref(false)
const visiblePages = ref<number[]>([])
const loadingPages = ref(false)
const hasMorePages = ref(true)
const pageSentinelRef = ref<HTMLDivElement | null>(null)
const topPageSentinelRef = ref<HTMLDivElement | null>(null) // 添加顶部观察哨引用
let pageObserver: IntersectionObserver | null = null
let topPageObserver: IntersectionObserver | null = null // 添加顶部观察器引用
const limitMessage = ref('') // 添加限制消息状态
const limitMessageShown = ref(false) // 添加限制消息是否显示过的状态

const jumpPageInput = ref<string>('')

const placeholder =
  'data:image/svg+xml;utf8,<svg xmlns=%22http://www.w3.org/2000/svg%22 width=%22300%22 height=%22200%22><rect width=%22300%22 height=%22200%22 fill=%22%23f3f4f6%22/><text x=%2240%22 y=%22105%22 fill=%22%239ca3af%22 font-size=%2214%22>加载中...</text></svg>'

// 预定义的颜色数组（使用JobList的配色方案）
const categoryColors = [
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

const cardBgColors = [
  'bg-blue-50/80',
  'bg-green-50/80',
  'bg-yellow-50/80',
  'bg-purple-50/80',
  'bg-pink-50/80',
  'bg-indigo-50/80',
  'bg-red-50/80',
  'bg-orange-50/80',
  'bg-teal-50/80',
  'bg-cyan-50/80',
  'bg-emerald-50/80',
  'bg-lime-50/80',
  'bg-amber-50/80',
  'bg-rose-50/80',
  'bg-violet-50/80',
  'bg-sky-50/80'
]

// 根据分类获取按钮样式
const getCategoryButtonClass = (category: string) => {
  // 处理"全部"分类的特殊情况
  if (category === '') {
    if (selectedCategory.value === '') {
      // 选中状态使用深蓝色背景和白色文字
      return 'px-3 py-1.5 rounded-full text-sm transition-colors bg-blue-600 text-white hover:opacity-90';
    } else {
      // 未选中状态使用浅蓝色背景和黑色加粗文字
      return 'px-3 py-1.5 rounded-full text-sm transition-colors bg-blue-100 text-black font-bold hover:opacity-90';
    }
  }

  if (selectedCategory.value === category) {
    // 选中状态使用深色背景和白色文字
    const colorClass = getCategoryTagClass(category).split(' ').find(cls => cls.includes('bg-'));
    if (colorClass) {
      // 从bg-xxx-100转换为bg-xxx-600并添加hover效果
      const bgColorClass = colorClass.replace('bg-', 'bg-').replace('-100', '-600');
      return `px-3 py-1.5 rounded-full text-sm transition-colors ${bgColorClass} text-white hover:opacity-90`;
    }
    return 'px-3 py-1.5 rounded-full text-sm transition-colors bg-blue-600 text-white hover:opacity-90';
  } else {
    // 未选中状态使用浅色背景和深色文字（使用JobList的配色方案）
    const colorClass = getCategoryTagClass(category).split(' ').find(cls => cls.includes('bg-'));
    if (colorClass) {
      // 保持bg-xxx-100的浅色背景，但确保文字是黑色加粗
      return `px-3 py-1.5 rounded-full text-sm transition-colors ${colorClass} text-black font-bold hover:opacity-90`;
    }
    return 'px-3 py-1.5 rounded-full text-sm transition-colors bg-gray-100 text-black font-bold hover:opacity-90';
  }
}

// 根据分类获取标签样式
const getCategoryTagClass = (category?: string) => {
  if (!category) return 'bg-gray-100 text-gray-800 border border-gray-200'

  // 根据分类内容生成稳定的颜色索引
  let hash = 0
  for (let i = 0; i < category.length; i++) {
    const char = category.charCodeAt(i)
    hash = ((hash << 5) - hash) + char
    hash = hash & hash // 转换为32位整数
  }

  const colorIndex = Math.abs(hash) % categoryColors.length
  return categoryColors[colorIndex]
}

// 根据分类获取卡片背景样式
const getCardBgClass = (category?: string) => {
  if (!category) return 'bg-white'

  // 根据分类内容生成稳定的颜色索引
  let hash = 0
  for (let i = 0; i < category.length; i++) {
    const char = category.charCodeAt(i)
    hash = ((hash << 5) - hash) + char
    hash = hash & hash // 转换为32位整数
  }

  const colorIndex = Math.abs(hash) % cardBgColors.length
  return cardBgColors[colorIndex]
}
let cardObserver: IntersectionObserver | null = null
const MAX_CONCURRENCY = 2
const loadQueue: Material[] = []
let loadingCount = 0

const observeCard = (el: Element | null, m: Material) => {
  if (!el) return
  if (!('IntersectionObserver' in window)) {
    enqueueLoad(m)
    return
  }
  if (!cardObserver) {
    cardObserver = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        const target = entry.target as HTMLElement
        const item = (target as any).__material as Material | undefined
        if (entry.isIntersecting && item) {
          enqueueLoad(item)
          cardObserver?.unobserve(target)
        }
      })
    }, { root: null, rootMargin: '100px', threshold: 0.01 })
  }
  ; (el as any).__material = m
  cardObserver.observe(el)
}

const processQueue = () => {
  while (loadingCount < MAX_CONCURRENCY && loadQueue.length) {
    const m = loadQueue.shift()!
    loadingCount++
    // 使用原生axios实例发送图片请求，跳过全局拦截器
    axios.get(previewUrl(m), {
      responseType: 'blob',
      baseURL: '/api',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('user_token') || ''}`
      }
    })
      .then(res => {
        // axios 成功响应，res.data 就是 blob
        const blob = res.data as Blob
        srcMap.value[m.id] = URL.createObjectURL(blob)
      })
      .catch(error => {
        let errorMessage = '图片加载失败'
        if (error.response) {
          // 后端返回了响应，但状态码不是 2xx
          if (error.response.status === 401 || error.response.status === 403) {
            errorMessage = '需要登录后才能预览'
          } else if (error.response.data instanceof Blob) {
            // 如果错误响应是 blob，尝试解析为文本
            const reader = new FileReader();
            reader.onload = () => {
              try {
                const json = JSON.parse(reader.result as string);
                errorMessage = json.message || `请求失败: ${error.response.status}`;
              } catch {
                errorMessage = `请求失败: ${error.response.status}`;
              }
              console.warn('图片加载失败:', errorMessage);
              srcMap.value[m.id] = `data:image/svg+xml;utf8,<svg xmlns=%22http://www.w3.org/2000/svg%22 width=%22300%22 height=%22200%22 fill=%22%23f3f4f6%22/><text x=%2220%22 y=%22105%22 fill=%22%239ca3af%22 font-size=%2212%22>${encodeURIComponent(errorMessage)}</text></svg>`;
            };
            reader.readAsText(error.response.data);
            return; // Avoid further processing in this catch block
          } else if (error.response.data && typeof error.response.data === 'object' && (error.response.data as any).message) {
            errorMessage = (error.response.data as any).message;
          } else {
            errorMessage = `请求失败: ${error.response.status}`;
          }
        } else if (error.request) {
          // 请求已发出，但没有收到响应
          errorMessage = '网络错误，无响应';
        } else {
          // 其他错误
          errorMessage = error.message;
        }
        console.warn('图片加载失败:', errorMessage);
        srcMap.value[m.id] = `data:image/svg+xml;utf8,<svg xmlns=%22http://www.w3.org/2000/svg%22 width=%22300%22 height=%22200%22 fill=%22%23f3f4f6%22/><text x=%2220%22 y=%22105%22 fill=%22%239ca3af%22 font-size=%2212%22>${encodeURIComponent(errorMessage)}</text></svg>`;
      })
      .finally(() => {
        loadingCount--
        processQueue()
      })
  }
}

const enqueueLoad = (m: Material) => {
  if (srcMap.value[m.id]) return
  loadQueue.push(m)
  processQueue()
}

const previewUrl = (m: Material) => {
  // 移除时间戳参数
  return buildPdfPreviewUrl(m.fileUuid, 1)
}

const onImgError = async (m: Material) => {
  if (srcMap.value[m.id]) {
    srcMap.value[m.id] =
      'data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="300" height="200"><rect width="300" height="200" fill="#f3f4f6"/><text x="20" y="105" fill="#9ca3af" font-size="12">图片加载失败</text></svg>'
    return
  }
  try {
    const res = await fetch(previewUrl(m), { credentials: 'include' })
    if (!res.ok) {
      if (res.status === 401 || res.status === 403) {
        throw new Error('需要登录后才能预览')
      }
      throw new Error(`请求失败: ${res.status}`)
    }
    // 后端明确返回图片数据，直接创建 ObjectURL
    const blob = await res.blob()
    srcMap.value[m.id] = URL.createObjectURL(blob)
  } catch (err) {
    const errorMsg = err instanceof Error ? err.message : '预览加载失败'
    srcMap.value[m.id] =
      `data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="300" height="200"><rect width="300" height="200" fill="#f3f4f6"/><text x="20" y="105" fill="#9ca3af" font-size="12">${encodeURIComponent(errorMsg)}</text></svg>`
  }
}

// 初始化分类
const fetchCategories = async () => {
  try {
    const list = await getMaterialCategories()
    categories.value = list || []
  } catch (e) {
    console.error('获取分类失败', e)
    categories.value = []
  }
}

// 拉取列表
const fetchList = async (reset = false) => {
  if (loading.value) return
  loading.value = true
  try {
    if (reset) {
      pageNum.value = 1
      materials.value = []
      hasMore.value = true
    }
    const resp: PageResponse<Material> = await listMaterials({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      category: selectedCategory.value || undefined
    })
    const list = resp.list || []
    total.value = Number(resp.total || 0)
    if (pageNum.value === 1) {
      materials.value = list
    } else {
      materials.value = materials.value.concat(list)
    }
    hasMore.value = materials.value.length < total.value
  } catch (e) {
    console.error('获取资料失败', e)
  } finally {
    loading.value = false
  }
}

const changeCategory = async (c: string) => {
  selectedCategory.value = c
  await fetchList(true)
}

const closePreview = () => {
  previewing.value = false
  previewItem.value = null
  visiblePages.value = []
  hasMorePages.value = true
  limitMessage.value = '' // 清除限制消息
  limitMessageShown.value = false // 重置限制消息显示状态

  // 清理页面源映射
  pageSrcMap.value = {}

  // 重置跳转输入
  jumpPageInput.value = ''
}

// 加载更多页面（无限滚动）
const loadMorePages = async (direction: 'up' | 'down' = 'down') => {
  if (loadingPages.value || !hasMorePages.value || !previewItem.value) return

  loadingPages.value = true
  const material = previewItem.value

  try {
    // 计算下一个要加载的页面
    let nextPage: number

    // 如果visiblePages为空，从第1页开始加载
    if (visiblePages.value.length === 0) {
      nextPage = 1
    } else {
      // 获取当前可见页面的最小和最大页码
      const minPage = Math.min(...visiblePages.value)
      const maxPage = Math.max(...visiblePages.value)

      // 根据方向决定加载哪一页
      if (direction === 'down') {
        // 向下加载
        nextPage = maxPage + 1
      } else {
        // 向上加载
        nextPage = minPage - 1
      }
    }

    // 检查是否超出范围
    if (nextPage < 1 || nextPage > (material.totalPages || 1)) {
      hasMorePages.value = false
      return
    }

    const loadedPage = await loadPreviewPage(material, nextPage)

    if (loadedPage === null) {
      hasMorePages.value = false
    } else {
      // 确保页面按顺序添加到visiblePages
      if (!visiblePages.value.includes(loadedPage)) {
        visiblePages.value = [...visiblePages.value, loadedPage].sort((a, b) => a - b)
      }

      // 检查是否还有更多页面
      if (visiblePages.value.length >= (material.totalPages || 1)) {
        hasMorePages.value = false
      }
    }
  } catch (error) {
    console.error('加载更多页面失败:', error)
  } finally {
    loadingPages.value = false
  }
}

// 加载单个预览页面
const getPageImage = (material: Material, page: number): string | null => {
  return pageSrcMap.value[material.id]?.[page] || null
}

const onPreviewImgError = (material: Material, page: number) => {
  console.warn(`预览图片加载失败: ${material.fileName} 第 ${page} 页`)
  if (pageSrcMap.value[material.id]?.[page]) {
    pageSrcMap.value[material.id][page] =
      'data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="300" height="200"><rect width="300" height="200" fill="#f3f4f6"/><text x="20" y="105" fill="#9ca3af" font-size="12">图片加载失败</text></svg>'
  }
}

const loadPreviewPage = async (material: Material, page: number): Promise<number | null> => {
  try {
    const previewUrl = `/pdf/preview/${material.fileUuid}/page/${page}`
    const response = await axios.get(previewUrl, {
      baseURL: '/api',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('user_token') || ''}`
      },
      responseType: 'blob'
    })

    // 检查是否是限制错误
    if (response.data instanceof Blob && response.data.type === 'application/json') {
      const text = await response.data.text()
      try {
        const jsonData = JSON.parse(text)
        if (jsonData.statusCode === 666401) {
          // 显示限制提示
          showLimitMessage(jsonData.message)
          // 停止加载更多页面
          hasMorePages.value = false
          return null
        }
      } catch (e) {
        // 如果不是JSON格式，继续处理为图片
      }
    }

    const blob = response.data as Blob
    if (!pageSrcMap.value[material.id]) {
      pageSrcMap.value[material.id] = {}
    }

    // 如果已经存在该页面的URL，先释放旧的
    if (pageSrcMap.value[material.id][page]) {
      URL.revokeObjectURL(pageSrcMap.value[material.id][page])
    }

    pageSrcMap.value[material.id][page] = URL.createObjectURL(blob)
    return page
  } catch (error: any) {
    // 检查是否是限制错误
    if (error.response && error.response.data instanceof Blob) {
      const reader = new FileReader()
      reader.onload = () => {
        try {
          const jsonData = JSON.parse(reader.result as string)
          if (jsonData.statusCode === 666401) {
            // 显示限制提示
            showLimitMessage(jsonData.message)
            // 停止加载更多页面
            hasMorePages.value = false
          }
        } catch (e) {
          // 如果不是JSON格式，显示通用错误
          console.error(`加载第${page}页失败:`, error)
        }
      }
      reader.readAsText(error.response.data)
      return null
    }
    console.error(`加载第${page}页失败:`, error)
    return null
  }
}

// 显示限制消息
const showLimitMessage = (message: string) => {
  limitMessage.value = message
  limitMessageShown.value = true // 标记限制消息已显示
  // 5秒后自动清除消息
  setTimeout(() => {
    limitMessage.value = ''
  }, 5000)
}

// 页面跳转功能
const jumpToPage = () => {
  if (!previewItem.value || !jumpPageInput.value) return

  const targetPage = parseInt(jumpPageInput.value)
  const totalPages = previewItem.value.totalPages || 1

  if (isNaN(targetPage) || targetPage < 1 || targetPage > totalPages) {
    // 可以添加错误提示
    console.warn('请输入有效的页码')
    return
  }

  // 清空当前可见页面列表，确保上下页导航通过接口调用
  visiblePages.value = []

  // 重置hasMorePages状态
  hasMorePages.value = true
  limitMessage.value = ''
  limitMessageShown.value = false

  // 加载目标页面及其前后几页
  loadSpecificPage(targetPage).then(() => {
    // 加载前面几页（最多5页）
    for (let i = 1; i <= 5; i++) {
      const prevPage = targetPage - i
      if (prevPage >= 1) {
        loadPreviewPage(previewItem.value!, prevPage)
      }
    }

    // 加载后面几页（最多5页）
    for (let i = 1; i <= 5; i++) {
      const nextPage = targetPage + i
      if (nextPage <= totalPages) {
        loadPreviewPage(previewItem.value!, nextPage)
      }
    }
  })
}

// 加载指定页面
const loadSpecificPage = async (page: number) => {
  if (!previewItem.value) return

  try {
    loadingPages.value = true
    const loadedPage = await loadPreviewPage(previewItem.value, page)

    if (loadedPage !== null) {
      // 如果页面不在当前可见列表中，添加到列表并排序
      if (!visiblePages.value.includes(page)) {
        // 添加目标页面及其前后几页到可见列表
        const pagesToAdd = [page]

        // 添加前面几页（最多5页）
        for (let i = 1; i <= 5; i++) {
          const prevPage = page - i
          if (prevPage >= 1 && !visiblePages.value.includes(prevPage)) {
            pagesToAdd.push(prevPage)
          }
        }

        // 添加后面几页（最多5页）
        const totalPages = previewItem.value.totalPages || 1
        for (let i = 1; i <= 5; i++) {
          const nextPage = page + i
          if (nextPage <= totalPages && !visiblePages.value.includes(nextPage)) {
            pagesToAdd.push(nextPage)
          }
        }

        visiblePages.value = [...visiblePages.value, ...pagesToAdd].sort((a, b) => a - b)
      }

      // 滚动到该页面，使用更精确的滚动控制来保持标题稳定
      setTimeout(() => {
        const element = document.querySelector(`[data-page="${page}"]`)
        if (element) {
          // 获取预览内容容器
          const previewContainer = document.querySelector('.p-4.overflow-auto')
          if (previewContainer) {
            // 计算元素相对于容器的位置
            const containerRect = previewContainer.getBoundingClientRect()
            const elementRect = element.getBoundingClientRect()

            // 计算需要滚动到的位置，保持标题在固定位置
            const scrollTop = previewContainer.scrollTop
            const offsetTop = elementRect.top - containerRect.top + scrollTop

            // 滚动到目标位置，留出适当的顶部间距
            previewContainer.scrollTo({
              top: offsetTop - 50, // 留出50px的顶部间距
              behavior: 'smooth'
            })
          } else {
            // 如果找不到容器，使用默认滚动但确保标题可见
            element.scrollIntoView({
              behavior: 'smooth',
              block: 'center'
            })
          }
        }
      }, 100)
    }
  } catch (error) {
    console.error(`加载第${page}页失败:`, error)
  } finally {
    loadingPages.value = false
  }
}

// 初始化页面观察器
const initPageObserver = () => {
  if (!('IntersectionObserver' in window)) return

  pageObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting && hasMorePages.value && !loadingPages.value) {
        // 底部观察器被触发，向下加载更多页面
        loadMorePages('down')
      }
    })
  }, { root: null, rootMargin: '100px', threshold: 0.1 })

  if (pageSentinelRef.value) {
    pageObserver.observe(pageSentinelRef.value)
  }
}

// 初始化顶部页面观察器
const initTopPageObserver = () => {
  if (!('IntersectionObserver' in window)) return

  topPageObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting && hasMorePages.value && !loadingPages.value) {
        // 顶部观察器被触发，向上加载更多页面
        loadMorePages('up')
      }
    })
  }, { root: null, rootMargin: '100px', threshold: 0.1 })

  if (topPageSentinelRef.value) {
    topPageObserver.observe(topPageSentinelRef.value)
  }
}

// 修改打开预览函数
const openPreview = (item: Material) => {
  previewing.value = true
  previewItem.value = item
  visiblePages.value = []
  hasMorePages.value = true
  loadingPages.value = false
  limitMessage.value = ''
  limitMessageShown.value = false

  // 直接使用卡片封面作为第一页预览，提高加载速度
  if (srcMap.value[item.id]) {
    // 如果卡片封面已加载，直接使用它作为第一页
    if (!pageSrcMap.value[item.id]) {
      pageSrcMap.value[item.id] = {}
    }
    // 将卡片封面用作第一页预览
    pageSrcMap.value[item.id][1] = srcMap.value[item.id]
    visiblePages.value = [1]
  }

  // 初始加载第2页及后续页面
  setTimeout(() => {
    loadMorePages('down')
    initPageObserver()
    initTopPageObserver()
  }, 100)
}

// 打开预览时初始化
const initObserver = () => {
  if (!('IntersectionObserver' in window)) {
    autoLoading.value = false
    return
  }
  observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting && hasMore.value && !loading.value) {
        loadMore()
      }
    })
  }, { root: null, rootMargin: '0px', threshold: 0.1 })
  if (sentinelRef.value) {
    observer.observe(sentinelRef.value)
  }
}

onMounted(async () => {
  await fetchCategories()
  await fetchList(true)
  initObserver()
})

onBeforeUnmount(() => {
  if (observer && sentinelRef.value) {
    observer.unobserve(sentinelRef.value)
  }
  observer = null

  if (cardObserver) {
    cardObserver.disconnect()
    cardObserver = null
  }

  // 清理页面观察器
  if (pageObserver && pageSentinelRef.value) {
    pageObserver.unobserve(pageSentinelRef.value)
  }
  pageObserver = null

  // 清理顶部页面观察器
  if (topPageObserver && topPageSentinelRef.value) {
    topPageObserver.unobserve(topPageSentinelRef.value)
  }
  topPageObserver = null

  // 清理页面源映射中的URL对象
  Object.values(pageSrcMap.value).forEach(pageMap => {
    Object.values(pageMap).forEach(url => {
      URL.revokeObjectURL(url)
    })
  })

  // 清理源映射
  Object.values(srcMap.value).forEach(url => {
    URL.revokeObjectURL(url)
  })
})
</script>

<style scoped>
/* 兼容性处理：确保列内元素不被打断 */
.break-inside-avoid {
  break-inside: avoid;
}

/* 横向切换过渡动画 */
.slide-x-enter-active,
.slide-x-leave-active {
  transition: all 0.3s ease;
}

.slide-x-enter-from {
  opacity: 0;
  transform: translateX(24px);
}

.slide-x-leave-to {
  opacity: 0;
  transform: translateX(-24px);
}

.slide-x-enter-to,
.slide-x-leave-from {
  opacity: 1;
  transform: translateX(0);
}

/* 预览弹窗布局稳定性修复 */
.preview-container {
  transform: translateZ(0);
  /* 启用硬件加速 */
  will-change: transform;
  /* 提示浏览器该元素将要改变 */
}

/* 确保预览内容区域在跳转时保持稳定 */
.preview-content {
  scroll-behavior: smooth;
}

/* 标题区域稳定性增强 */
.preview-header {
  position: sticky;
  top: 0;
  z-index: 10;
  background-color: white;
  transform: translateZ(0);
}
</style>