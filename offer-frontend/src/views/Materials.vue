<template>
  <div class="max-w-6xl mx-auto">
    <!-- 标题与分类 -->
    <div class="mb-6">
      <h2 class="text-2xl font-bold text-gray-900 mb-3">笔试面试资料</h2>
      <div class="flex flex-wrap gap-2">
        <button
          :class="['px-3 py-1.5 rounded-full text-sm transition-colors', selectedCategory === '' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200']"
          @click="changeCategory('')"
        >
          全部
        </button>
        <button
          v-for="c in categories"
          :key="c"
          @click="changeCategory(c)"
          :class="['px-3 py-1.5 rounded-full text-sm transition-colors', selectedCategory === c ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200']"
        >
          {{ c }}
        </button>
      </div>
    </div>

    <!-- 空状态 -->
    <div v-if="!loading && materials.length === 0" class="bg-white border border-gray-200 rounded-lg p-8 text-center text-gray-500">
      暂无资料
    </div>

    <!-- 瀑布流（CSS columns） + 分类左右切换动画 -->
    <transition name="slide-x" mode="out-in">
      <div :key="selectedCategory" class="columns-1 sm:columns-2 lg:columns-3 gap-4">
        <div
          v-for="m in materials"
          :key="m.id"
          class="mb-4 break-inside-avoid"
        >
          <div
            class="group relative bg-white border border-gray-200 rounded-lg overflow-hidden shadow-sm hover:shadow transition cursor-pointer"
            @click="openPreview(m)"
            :ref="el => observeCard(el, m)"
          >
            <!-- A4 比例容器 -->
            <div class="relative w-full bg-white" style="padding-top: 141.42%;">
              <img
                :src="srcMap[m.id] || placeholder"
                :alt="m.fileName"
                class="absolute inset-0 w-full h-full block object-contain bg-white"
                loading="lazy"
                @error="onImgError(m)"
              />
            </div>
            <div class="absolute inset-x-0 bottom-0 bg-gradient-to-t from-black/60 to-transparent p-3 text-white">
              <div class="text-sm font-medium truncate" :title="m.fileName">{{ m.fileName }}</div>
              <div class="text-xs opacity-80 mt-0.5 flex items-center justify-between">
                <span>{{ m.category }}</span>
                <span v-if="m.totalPages">共 {{ m.totalPages }} 页</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </transition>

    <!-- 加载更多 / 观察哨 -->
    <div ref="sentinelRef" class="h-10 flex items-center justify-center text-sm text-gray-500">
      <button
        v-if="hasMore && !autoLoading"
        class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
        :disabled="loading"
        @click="loadMore"
      >
        {{ loading ? '加载中...' : '加载更多' }}
      </button>
      <span v-else-if="!hasMore">没有更多了</span>
    </div>

    <!-- 预览弹窗 -->
    <div
      v-if="previewing"
      class="fixed inset-0 bg-black/70 z-50 flex items-center justify-center p-4"
      @click.self="closePreview"
    >
      <div class="bg-white rounded-lg max-w-6xl w-full max-h-[90vh] overflow-hidden">
        <div class="flex items-center justify-between px-6 py-4 border-b">
          <div class="flex items-center space-x-4">
            <h3 class="text-lg font-semibold text-gray-900 truncate">{{ previewItem?.fileName }}</h3>
            <span class="text-sm text-gray-500">第 {{ currentPage }} 页 / 共 {{ previewItem?.totalPages || 0 }} 页</span>
          </div>
          <div class="flex items-center space-x-2">
            <button
              class="px-3 py-1.5 bg-blue-600 text-white rounded hover:bg-blue-700 disabled:bg-gray-300 disabled:cursor-not-allowed"
              :disabled="currentPage <= 1"
              @click.stop="prevPage"
            >
              上一页
            </button>
            <button
              class="px-3 py-1.5 bg-blue-600 text-white rounded hover:bg-blue-700 disabled:bg-gray-300 disabled:cursor-not-allowed"
              :disabled="currentPage >= (previewItem?.totalPages || 1)"
              @click.stop="nextPage"
            >
              下一页
            </button>
            <button class="text-gray-500 hover:text-gray-700 ml-2" @click.stop="closePreview">
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
              </svg>
            </button>
          </div>
        </div>
        <div class="p-6 overflow-auto max-h-[80vh]">
          <!-- 瀑布流多页预览 -->
          <div class="columns-1 md:columns-2 lg:columns-3 gap-6">
            <div
              v-for="page in visiblePages"
              :key="page"
              class="mb-6 break-inside-avoid"
            >
              <div class="relative bg-white border border-gray-200 rounded-lg overflow-hidden shadow-sm">
                <!-- A4 比例容器 -->
                <div class="relative w-full bg-white" style="padding-top: 141.42%;">
                  <img
                    :src="getPageImage(previewItem!, page) || placeholder"
                    :alt="`${previewItem?.fileName} - 第 ${page} 页`"
                    class="absolute inset-0 w-full h-full block object-contain bg-white"
                    loading="lazy"
                    @error="onPreviewImgError(previewItem!, page)"
                  />
                </div>
                <div class="absolute bottom-2 right-2 bg-black/60 text-white text-xs px-2 py-1 rounded">
                  第 {{ page }} 页
                </div>
              </div>
            </div>
          </div>
          
          <!-- 加载更多观察哨 -->
          <div
            v-if="hasMorePages && !loadingPages"
            ref="pageSentinelRef"
            class="h-20 flex items-center justify-center"
          >
            <button
              class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
              @click.stop="loadMorePages"
            >
              加载更多页面
            </button>
          </div>
          <div
            v-else-if="loadingPages"
            class="h-20 flex items-center justify-center text-gray-500"
          >
            加载中...
          </div>
          <div
            v-else-if="!hasMorePages"
            class="h-20 flex items-center justify-center text-gray-500"
          >
            已加载所有页面
          </div>
        </div>
      </div>
    </div>
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
const currentPage = ref(1)
const visiblePages = ref<number[]>([])
const loadingPages = ref(false)
const hasMorePages = ref(true)
const pageSentinelRef = ref<HTMLDivElement | null>(null)
let pageObserver: IntersectionObserver | null = null

const placeholder =
  'data:image/svg+xml;utf8,<svg xmlns=%22http://www.w3.org/2000/svg%22 width=%22300%22 height=%22200%22><rect width=%22300%22 height=%22200%22 fill=%22%23f3f4f6%22/><text x=%2240%22 y=%22105%22 fill=%22%239ca3af%22 font-size=%2214%22>加载中...</text></svg>'

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
  ;(el as any).__material = m
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
        'Authorization': `Bearer ${localStorage.getItem('user_token')}`
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
      'data:image/svg+xml;utf8,<svg xmlns=%22http://www.w3.org/2000/svg%22 width=%22300%22 height=%22200%22><rect width=%22300%22 height=%22200%22 fill=%22%23f3f4f6%22/><text x=%2220%22 y=%22105%22 fill=%22%239ca3af%22 font-size=%2212%22>图片加载失败</text></svg>'
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
      `data:image/svg+xml;utf8,<svg xmlns=%22http://www.w3.org/2000/svg%22 width=%22300%22 height=%22200%22><rect width=%22300%22 height=%22200%22 fill=%22%23f3f4f6%22/><text x=%2220%22 y=%22105%22 fill=%22%239ca3af%22 font-size=%2212%22>${encodeURIComponent(errorMsg)}</text></svg>`
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

const loadMore = async () => {
  if (!hasMore.value || loading.value) return
  pageNum.value += 1
  await fetchList(false)
}



const closePreview = () => {
  previewing.value = false
  previewItem.value = null
  currentPage.value = 1
  visiblePages.value = []
  hasMorePages.value = true
}

// 加载更多页面（懒加载）
const loadMorePages = async () => {
  if (loadingPages.value || !hasMorePages.value || !previewItem.value) return
  
  loadingPages.value = true
  const material = previewItem.value
  
  try {
    // 一次加载3页
    const pagesToLoad = 3
    const startPage = visiblePages.value.length + 1
    
    const loadPromises = []
    for (let page = startPage; page < startPage + pagesToLoad; page++) {
      loadPromises.push(loadPreviewPage(material, page))
    }
    
    const newPages = await Promise.all(loadPromises)
    const validPages = newPages.filter(page => page !== null) as number[]
    
    if (validPages.length === 0) {
      hasMorePages.value = false
    } else {
      visiblePages.value = [...visiblePages.value, ...validPages]
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
      'data:image/svg+xml;utf8,<svg xmlns=%22http://www.w3.org/2000/svg%22 width=%22300%22 height=%22200%22><rect width=%22300%22 height=%22200%22 fill=%22%23f3f4f6%22/><text x=%2220%22 y=%22105%22 fill=%22%239ca3af%22 font-size=%2212%22>图片加载失败</text></svg>'
  }
}

const loadPreviewPage = async (material: Material, page: number): Promise<number | null> => {
  try {
    const previewUrl = `/pdf/preview/${material.fileUuid}/page/${page}`
    const response = await axios.get(previewUrl, {
      baseURL: '/api',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('user_token')}`
      },
      responseType: 'blob'
    })
    
    const blob = response.data as Blob
    if (!pageSrcMap.value[material.id]) {
      pageSrcMap.value[material.id] = {}
    }
    pageSrcMap.value[material.id][page] = URL.createObjectURL(blob)
    return page
  } catch (error) {
    console.error(`加载第${page}页失败:`, error)
    return null
  }
}

// 初始化页面观察器
const initPageObserver = () => {
  if (!('IntersectionObserver' in window)) return
  
  pageObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting && hasMorePages.value && !loadingPages.value) {
        loadMorePages()
      }
    })
  }, { root: null, rootMargin: '100px', threshold: 0.1 })
  
  if (pageSentinelRef.value) {
    pageObserver.observe(pageSentinelRef.value)
  }
}

// 打开预览时初始化
const openPreview = (item: Material) => {
  previewing.value = true
  previewItem.value = item
  currentPage.value = 1
  visiblePages.value = []
  hasMorePages.value = true
  loadingPages.value = false
  
  // 初始加载前5页
  setTimeout(() => {
    loadMorePages()
    initPageObserver()
  }, 100)
}

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
</style>