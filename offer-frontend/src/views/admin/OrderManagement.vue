<template>
    <div class="space-y-6">
        <!-- 标题与操作 -->
        <div class="flex justify-between items-center">
            <h2 class="text-2xl font-bold text-gray-900">订单管理</h2>
        </div>

        <!-- 搜索筛选区域 -->
        <div class="bg-white rounded-lg border border-gray-200 p-6">
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">订单ID</label>
                    <input v-model="query.id" type="text" placeholder="订单ID"
                        class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">订单号</label>
                    <input v-model="query.outTradeNo" type="text" placeholder="订单号"
                        class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">平台订单号</label>
                    <input v-model="query.tradeNo" type="text" placeholder="平台订单号"
                        class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">用户ID</label>
                    <input v-model="query.userId" type="text" placeholder="用户ID"
                        class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">金额</label>
                    <input v-model="query.money" type="number" step="0.01" placeholder="金额"
                        class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">状态</label>
                    <select v-model="query.status"
                        class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                        <option value="">全部</option>
                        <option :value="0">未支付</option>
                        <option :value="1">已支付</option>
                    </select>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">排序字段</label>
                    <select v-model="query.sortField"
                        class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                        <option value="">默认</option>
                        <option value="id">订单ID</option>
                        <option value="outTradeNo">订单号</option>
                        <option value="tradeNo">平台订单号</option>
                        <option value="userId">用户ID</option>
                        <option value="money">金额</option>
                        <option value="status">状态</option>
                        <option value="createTime">创建时间</option>
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
                                <th
                                    class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    ID</th>
                                <th
                                    class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    订单号</th>
                                <th
                                    class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    平台订单号</th>
                                <th
                                    class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    用户ID</th>
                                <th
                                    class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    商品名称</th>
                                <th
                                    class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    金额</th>
                                <th
                                    class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    支付方式</th>
                                <th
                                    class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    状态</th>
                                <th
                                    class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    创建时间</th>
                                <th
                                    class="px-4 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                    操作</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                            <tr v-for="order in list" :key="order.id" class="hover:bg-gray-50">
                                <td class="px-4 py-4 text-center text-sm text-gray-500">{{ order.id }}</td>
                                <td class="px-4 py-4 text-center text-sm font-medium text-gray-900"
                                    :title="order.outTradeNo">{{ order.outTradeNo }}
                                </td>
                                <td class="px-4 py-4 text-center text-sm text-gray-500">{{ order.tradeNo || '-' }}</td>
                                <td class="px-4 py-4 text-center text-sm text-gray-500">{{ order.userId }}</td>
                                <td class="px-4 py-4 text-center text-sm text-gray-500">{{ order.name }}</td>
                                <td class="px-4 py-4 text-center text-sm text-gray-500">¥{{ order.money }}</td>
                                <td class="px-4 py-4 text-center text-sm text-gray-500">{{ order.type }}</td>
                                <td class="px-4 py-4 text-center">
                                    <span v-if="order.status === 0"
                                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full bg-yellow-100 text-yellow-800">
                                        未支付
                                    </span>
                                    <span v-else-if="order.status === 1"
                                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full bg-green-100 text-green-800">
                                        已支付
                                    </span>
                                    <span v-else
                                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full bg-gray-100 text-gray-800">
                                        未知
                                    </span>
                                </td>
                                <td class="px-4 py-4 text-center text-sm text-gray-500">{{ formatDate(order.createTime)
                                }}</td>
                                <td class="px-4 py-4 text-center">
                                    <button @click="viewOrderDetail(order)"
                                        class="text-blue-500 hover:text-blue-700 p-1 rounded-full hover:bg-blue-50 transition-colors"
                                        title="查看订单详情">
                                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z">
                                            </path>
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
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M15 19l-7-7 7-7"></path>
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
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7">
                                </path>
                            </svg>
                        </button>

                        <div class="flex items-center gap-2 ml-0 sm:ml-4 w-full sm:w-auto flex-wrap">
                            <span class="text-sm text-gray-700">跳至</span>
                            <input v-model="jumpPage" @keyup.enter="handleJumpPage" type="number" min="1"
                                :max="totalPages"
                                class="w-14 sm:w-16 px-2 py-1 text-xs sm:text-sm border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent">
                            <span class="text-sm text-gray-700">/ {{ totalPages }}</span>
                            <button @click="handleJumpPage"
                                class="px-2 py-1 text-xs sm:px-3 sm:py-2 sm:text-sm border border-gray-300 rounded hover:bg-gray-50">确定</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 订单详情对话框 -->
        <teleport to="body" v-if="showOrderDetail">
            <div class="modal-backdrop fixed inset-0 bg-black bg-opacity-60" style="z-index: 99999;"></div>
            <div class="modal-container fixed inset-0 flex items-center justify-center p-4" style="z-index: 100000;"
                @click.self="showOrderDetail = false">
                <div class="bg-white rounded-lg w-full max-w-md overflow-hidden">
                    <div class="px-4 py-3 border-b flex items-center justify-between">
                        <h3 class="text-base font-semibold text-gray-900">订单详情</h3>
                        <button class="text-gray-500 hover:text-gray-700" @click="showOrderDetail = false">
                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M6 18L18 6M6 6l12 12" />
                            </svg>
                        </button>
                    </div>
                    <div class="p-4 space-y-4" v-if="orderDetail">
                        <div class="grid grid-cols-2 gap-4">
                            <div>
                                <label class="block text-xs font-medium text-gray-500">订单号</label>
                                <p class="text-sm break-words">{{ orderDetail.out_trade_no }}</p>
                            </div>
                            <div>
                                <label class="block text-xs font-medium text-gray-500">平台订单号</label>
                                <p class="text-sm break-words">{{ orderDetail.trade_no || '-' }}</p>
                            </div>
                            <div>
                                <label class="block text-xs font-medium text-gray-500">商户ID</label>
                                <p class="text-sm break-words">{{ orderDetail.pid }}</p>
                            </div>
                            <div>
                                <label class="block text-xs font-medium text-gray-500">商品名称</label>
                                <p class="text-sm break-words">{{ orderDetail.name }}</p>
                            </div>
                            <div>
                                <label class="block text-xs font-medium text-gray-500">金额</label>
                                <p class="text-sm">¥{{ orderDetail.money }}</p>
                            </div>
                            <div>
                                <label class="block text-xs font-medium text-gray-500">支付方式</label>
                                <p class="text-sm">{{ orderDetail.type }}</p>
                            </div>
                            <div>
                                <label class="block text-xs font-medium text-gray-500">状态</label>
                                <p class="text-sm">
                                    <span v-if="orderDetail.status === '1'"
                                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full bg-green-100 text-green-800">
                                        已支付
                                    </span>
                                    <span v-else
                                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full bg-yellow-100 text-yellow-800">
                                        未支付
                                    </span>
                                </p>
                            </div>
                            <div>
                                <label class="block text-xs font-medium text-gray-500">创建时间</label>
                                <p class="text-sm">{{ orderDetail.addtime }}</p>
                            </div>
                            <div>
                                <label class="block text-xs font-medium text-gray-500">完成时间</label>
                                <p class="text-sm">{{ orderDetail.endtime || '-' }}</p>
                            </div>
                        </div>
                        <div v-if="orderDetailMsg" class="text-sm text-red-500">{{ orderDetailMsg }}</div>
                    </div>
                    <div v-else class="p-4 text-center">
                        <div class="inline-block animate-spin rounded-full h-6 w-6 border-b-2 border-blue-600"></div>
                        <p class="mt-2 text-gray-500">加载中...</p>
                    </div>
                </div>
            </div>
        </teleport>
    </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { adminGetOrderInfo, adminGetOrderDetail, type PayOrder, type PageResponse, type PayOrderPageRequest, type OrderDetailResponse } from '@/api/payOrder'
import Message from '@/components/Message'

// 列表与查询
const list = ref<PayOrder[]>([])
const loading = ref(false)
const isChangingPage = ref(false)
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const totalPages = computed(() => Math.max(1, Math.ceil(total.value / pageSize.value)))

const query = ref<PayOrderPageRequest>({
    pageNum: 1,
    pageSize: 10,
    id: undefined,
    outTradeNo: '',
    tradeNo: '',
    userId: undefined,
    money: undefined,
    status: undefined,
    sortField: '',
    sortOrder: ''
})

// 获取列表
const fetchList = async () => {
    loading.value = true
    try {
        const resp: PageResponse<PayOrder> = await adminGetOrderInfo({
            pageNum: currentPage.value,
            pageSize: pageSize.value,
            id: query.value.id ? String(query.value.id) : undefined,
            outTradeNo: query.value.outTradeNo || undefined,
            tradeNo: query.value.tradeNo || undefined,
            userId: query.value.userId || undefined,
            money: query.value.money || undefined,
            status: query.value.status,
            sortField: query.value.sortField || undefined,
            sortOrder: query.value.sortOrder || undefined
        })
        list.value = resp.list || []
        total.value = Number(resp.total || 0)
    } catch (e: any) {
        console.error('获取订单列表失败', e)
        list.value = []
        total.value = 0
        Message.error(e.message || '获取订单列表失败')
    } finally {
        loading.value = false
        isChangingPage.value = false
    }
}

const resetQuery = () => {
    query.value = {
        pageNum: 1,
        pageSize: 10,
        id: undefined,
        outTradeNo: '',
        tradeNo: '',
        userId: undefined,
        money: undefined,
        status: undefined,
        sortField: '',
        sortOrder: ''
    }
    currentPage.value = 1
    fetchList()
}

const handleQuery = () => {
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

// 订单详情
const showOrderDetail = ref(false)
const orderDetail = ref<OrderDetailResponse | null>(null)
const orderDetailMsg = ref('')

const viewOrderDetail = async (order: PayOrder) => {
    showOrderDetail.value = true
    orderDetail.value = null
    orderDetailMsg.value = ''

    try {
        // 优先使用平台订单号查询，如果没有则使用商户订单号
        const params = order.tradeNo ? { tradeNo: order.tradeNo } : { outTradeNo: order.outTradeNo }
        const detail = await adminGetOrderDetail(params)
        orderDetail.value = detail
    } catch (e: any) {
        console.error('获取订单详情失败', e)
        orderDetailMsg.value = e.message || '获取订单详情失败'
    }
}

onMounted(async () => {
    // 列表
    fetchList()
})
</script>