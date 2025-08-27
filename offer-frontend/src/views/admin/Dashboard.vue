<template>
  <div class="space-y-6">
    <!-- 页面标题 -->
    <div class="mb-6">
      <h2 class="text-2xl font-bold text-gray-900">仪表盘</h2>
    </div>

    <!-- 统计卡片 -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <div class="bg-blue-50 rounded-lg p-6 border border-gray-200">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-600 text-sm">用户数量</p>
            <p class="text-3xl font-bold text-gray-900">
              <span v-if="loading" class="animate-pulse">...</span>
              <span v-else>{{ stats.userCount }}</span>
            </p>
          </div>
          <div class="bg-blue-500 rounded-full p-3">
            <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z">
              </path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-green-50 rounded-lg p-6 border border-gray-200">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-600 text-sm">招聘信息数量</p>
            <p class="text-3xl font-bold text-gray-900">
              <span v-if="loading" class="animate-pulse">...</span>
              <span v-else>{{ stats.jobCount }}</span>
            </p>
          </div>
          <div class="bg-green-500 rounded-full p-3">
            <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2">
              </path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-purple-50 rounded-lg p-6 border border-gray-200">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-600 text-sm">网站总浏览量</p>
            <p class="text-3xl font-bold text-gray-900">
              <span v-if="loading" class="animate-pulse">...</span>
              <span v-else>{{ webInfo?.pv || stats.totalViews }}</span>
            </p>
          </div>
          <div class="bg-purple-500 rounded-full p-3">
            <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z">
              </path>
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z">
              </path>
            </svg>
          </div>
        </div>
      </div>

      <div class="bg-yellow-50 rounded-lg p-6 border border-gray-200">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-600 text-sm">网站会员数量</p>
            <p class="text-3xl font-bold text-gray-900">
              <span v-if="loading" class="animate-pulse">...</span>
              <span v-else>{{ stats.memberCount }}</span>
            </p>
          </div>
          <div class="bg-yellow-500 rounded-full p-3">
            <svg class="w-8 h-8 text-white" fill="currentColor" viewBox="0 0 24 24">
              <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z" />
            </svg>
          </div>
        </div>
      </div>
    </div>

    <!-- 数据统计图表 -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <div class="bg-white rounded-lg border border-gray-200 p-6">
        <h3 class="text-lg font-semibold text-gray-900 mb-4">数据统计</h3>
        <div id="pieChart" style="width: 100%; height: 400px;"></div>
      </div>

      <div class="bg-white rounded-lg border border-gray-200 p-6">
        <h3 class="text-lg font-semibold text-gray-900 mb-4">最近活动</h3>
        <div class="space-y-4">
          <div v-if="webInfo" class="space-y-4">
            <div v-if="webInfo.activity1" class="flex items-start">
              <div class="flex-shrink-0">
                <div class="w-2 h-2 rounded-full mt-2 bg-green-400"></div>
              </div>
              <div class="ml-3 flex-1">
                <p class="text-sm text-gray-900">{{ webInfo.activity1 }}</p>
                <p class="text-xs text-gray-500">{{ formatTime(webInfo.activity1Time) }}</p>
              </div>
            </div>

            <div v-if="webInfo.activity2" class="flex items-start">
              <div class="flex-shrink-0">
                <div class="w-2 h-2 rounded-full mt-2 bg-blue-400"></div>
              </div>
              <div class="ml-3 flex-1">
                <p class="text-sm text-gray-900">{{ webInfo.activity2 }}</p>
                <p class="text-xs text-gray-500">{{ formatTime(webInfo.activity2Time) }}</p>
              </div>
            </div>

            <div v-if="webInfo.activity3" class="flex items-start">
              <div class="flex-shrink-0">
                <div class="w-2 h-2 rounded-full mt-2 bg-purple-400"></div>
              </div>
              <div class="ml-3 flex-1">
                <p class="text-sm text-gray-900">{{ webInfo.activity3 }}</p>
                <p class="text-xs text-gray-500">{{ formatTime(webInfo.activity3Time) }}</p>
              </div>
            </div>

            <div v-if="webInfo.activity4" class="flex items-start">
              <div class="flex-shrink-0">
                <div class="w-2 h-2 rounded-full mt-2 bg-yellow-400"></div>
              </div>
              <div class="ml-3 flex-1">
                <p class="text-sm text-gray-900">{{ webInfo.activity4 }}</p>
                <p class="text-xs text-gray-500">{{ formatTime(webInfo.activity4Time) }}</p>
              </div>
            </div>

            <div v-if="webInfo.activity5" class="flex items-start">
              <div class="flex-shrink-0">
                <div class="w-2 h-2 rounded-full mt-2 bg-red-400"></div>
              </div>
              <div class="ml-3 flex-1">
                <p class="text-sm text-gray-900">{{ webInfo.activity5 }}</p>
                <p class="text-xs text-gray-500">{{ formatTime(webInfo.activity4Time) }}</p>
              </div>
            </div>
          </div>

          <!-- 如果没有网站信息数据，显示默认活动 -->
          <div v-else>
            <div v-for="activity in recentActivities" :key="activity.id" class="flex items-start">
              <div class="flex-shrink-0">
                <div :class="[
                  'w-2 h-2 rounded-full mt-2',
                  activity.type === 'add' ? 'bg-green-400' :
                    activity.type === 'edit' ? 'bg-blue-400' : 'bg-red-400'
                ]"></div>
              </div>
              <div class="ml-3 flex-1">
                <p class="text-sm text-gray-900">{{ activity.description }}</p>
                <p class="text-xs text-gray-500">{{ activity.time }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick, watch } from 'vue'
import * as echarts from 'echarts'
import { webInfoApi } from '@/api/webInfo'
import type { WebInfoResponse } from '@/api/types'

type EChartsOption = echarts.EChartsOption

const currentTime = ref('')
const stats = ref({
  userCount: 0,
  jobCount: 0,
  totalViews: 0,
  memberCount: 0
})

const webInfo = ref<WebInfoResponse | null>(null)
const loading = ref(true)
let myChart: echarts.ECharts | null = null

const initChart = async () => {
  await nextTick()
  const chartDom = document.getElementById('pieChart')
  if (!chartDom) return

  if (myChart) {
    myChart.dispose()
  }

  myChart = echarts.init(chartDom)

  const option: EChartsOption = {
    tooltip: {
      trigger: 'item',
      formatter: function (params: any) {
        // 获取原始数据值（统计卡片中显示的数值），并转换为数字
        const originalValue = params.name === '用户数量' ? Number(stats.value.userCount) :
          params.name === '招聘信息数量' ? Number(stats.value.jobCount) :
            params.name === '网站总浏览量' ? Number(stats.value.totalViews) :
              params.name === '网站会员数量' ? Number(stats.value.memberCount) : Number(params.value);

        // 计算统计卡片数据的总和（确保所有数据都为数字类型）
        const displayTotal = Number(stats.value.userCount) + Number(stats.value.jobCount) + Number(stats.value.totalViews) + Number(stats.value.memberCount);

        // 计算百分比
        const percent = displayTotal > 0 ? ((originalValue / displayTotal) * 100).toFixed(1) : '0.0';

        // 调试信息
        console.log('百分比计算调试:', {
          '选中项': params.name,
          '原始值': originalValue,
          '总数': displayTotal,
          '百分比': percent
        });

        return `${params.seriesName} <br/>${params.name}: ${originalValue} (${percent}%)`;
      }
    },
    legend: {
      top: '5%',
      left: 'center'
    },
    color: ['#FFB6C1', '#98D8C8', '#DDA0DD', '#F7DC6F', '#B0E0E6'],
    series: [
      {
        name: '统计数据',
        type: 'pie',
        radius: ['40%', '70%'],
        center: ['50%', '60%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: true,
          position: 'outside',
          formatter: function (params: any) {
            const originalValue = params.name === '用户数量' ? Number(stats.value.userCount) :
              params.name === '招聘信息数量' ? Number(stats.value.jobCount) :
                params.name === '网站总浏览量' ? Number(stats.value.totalViews) :
                  params.name === '网站会员数量' ? Number(stats.value.memberCount) : Number(params.value);
            return `${params.name}: ${originalValue}`;
          },
          fontSize: 12,
          fontWeight: 'normal'
        },
        labelLine: {
          show: true,
          length: 15,
          length2: 10
        },
        data: [
          { value: stats.value.userCount * 100, name: '用户数量' },
          { value: stats.value.jobCount, name: '招聘信息数量' },
          { value: stats.value.totalViews, name: '网站总浏览量' },
          { value: stats.value.memberCount * 100, name: '网站会员数量' }
        ]
      }
    ]
  }

  option && myChart.setOption(option)
}

// 监听统计数据变化，更新图表
watch(stats, () => {
  if (myChart && !loading.value) {
    initChart()
  }
}, { deep: true })

const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

const fetchStats = async () => {
  try {
    loading.value = true
    // 获取网站信息（包含所有统计数据）
    const response = await webInfoApi.getWebInfo()
    if (response.statusCode === 200 && response.data) {
      webInfo.value = response.data

      // 更新统计数据（确保所有数据都转换为数字类型）
      stats.value = {
        userCount: Number(response.data.userCount) || 0,
        jobCount: Number(response.data.jobCount) || 0,
        totalViews: Number(response.data.pv) || 0,
        memberCount: Number(response.data.memberCount) || 0
      }

    } else {
      console.error('获取网站信息失败:', response.message)
      // 如果获取失败，使用测试数据
      stats.value = {
        userCount: 12,
        jobCount: 8,
        totalViews: 245,
        memberCount: 5
      }
      console.log('使用测试数据:', stats.value); // 调试信息
    }
  } catch (error) {
    console.error('获取网站统计数据失败:', error)
    // 如果请求失败，使用测试数据
    stats.value = {
      userCount: 12,
      jobCount: 8,
      totalViews: 245,
      memberCount: 5
    }
    console.log('网络错误，使用测试数据:', stats.value); // 调试信息
  } finally {
    loading.value = false
  }
}

// 格式化时间显示
const formatTime = (timeString: string) => {
  if (!timeString) return '刚刚'

  const now = new Date()
  const time = new Date(timeString)
  const diff = now.getTime() - time.getTime()

  const minutes = Math.floor(diff / (1000 * 60))
  const hours = Math.floor(diff / (1000 * 60 * 60))
  const days = Math.floor(diff / (1000 * 60 * 60 * 24))

  if (days > 0) {
    return `${days}天前`
  } else if (hours > 0) {
    return `${hours}小时前`
  } else if (minutes > 0) {
    return `${minutes}分钟前`
  } else {
    return '刚刚'
  }
}

onMounted(async () => {
  updateTime()
  setInterval(updateTime, 1000)

  // 获取统计数据
  await fetchStats()

  // 初始化图表
  await initChart()

  // 每 30 秒刷新一次数据
  setInterval(async () => {
    await fetchStats()
  }, 30000)
})
</script>