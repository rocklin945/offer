<template>
  <div class="relative w-full max-w-7xl mx-auto px-4 min-h-[120px]">
    <!-- 左侧倒计时部分 (绝对定位) -->
    <div class="absolute left-0 top-1/2 transform -translate-y-1/2 text-center countdown-container">
      <div class="text-base font-bold text-red-500 mb-2 countdown-title">26届校招结束倒计时</div>
      <div class="flex items-center justify-center gap-1">
        <div class="flex flex-col items-center">
          <div class="bg-red-500 text-white font-bold text-lg rounded-md px-2 py-1 min-w-[45px]">{{ timeLeft.months }}</div>
          <div class="text-xs text-red-500 font-medium mt-1">月</div>
        </div>
        <div class="text-red-500 font-bold text-lg">:</div>
        <div class="flex flex-col items-center">
          <div class="bg-red-500 text-white font-bold text-lg rounded-md px-2 py-1 min-w-[45px]">{{ timeLeft.days }}</div>
          <div class="text-xs text-red-500 font-medium mt-1">天</div>
        </div>
        <div class="text-red-500 font-bold text-lg">:</div>
        <div class="flex flex-col items-center">
          <div class="bg-red-500 text-white font-bold text-lg rounded-md px-2 py-1 min-w-[45px]">{{ timeLeft.hours }}</div>
          <div class="text-xs text-red-500 font-medium mt-1">时</div>
        </div>
        <div class="text-red-500 font-bold text-lg">:</div>
        <div class="flex flex-col items-center">
          <div class="bg-red-500 text-white font-bold text-lg rounded-md px-2 py-1 min-w-[45px]">{{ timeLeft.minutes }}</div>
          <div class="text-xs text-red-500 font-medium mt-1">分</div>
        </div>
        <div class="text-red-500 font-bold text-lg">:</div>
        <div class="flex flex-col items-center">
          <div class="bg-red-500 text-white font-bold text-lg rounded-md px-2 py-1 min-w-[45px]">{{ timeLeft.seconds }}</div>
          <div class="text-xs text-red-500 font-medium mt-1">秒</div>
        </div>
      </div>
    </div>

    <!-- 中间标题部分 (保持居中) -->
    <div class="text-center py-6">
      <h1 class="text-4xl font-bold text-gray-900 mb-2">全网最全招聘信息</h1>
      <p class="text-gray-600">最新招聘信息汇总</p>
    </div>

    <!-- 右侧统计信息部分 (绝对定位) -->
    <div class="absolute right-0 top-1/2 transform -translate-y-1/2 stats-container">
      <div class="flex gap-2">
        <div class="bg-white rounded-lg p-2 text-center shadow-sm min-w-[95px]">
          <div class="text-sm font-semibold text-blue-600 mb-1">26届校招累计新开</div>
          <div class="text-2xl font-extrabold text-blue-600">{{ formatNumber(countdownData.totalOpenCount) }}</div>
        </div>
        <div class="bg-white rounded-lg p-2 text-center shadow-sm min-w-[95px]">
          <div class="text-sm font-semibold text-purple-600 mb-1">本月新开校招</div>
          <div class="text-2xl font-extrabold text-purple-600">{{ formatNumber(countdownData.monthOpenCount) }}</div>
        </div>
        <div class="bg-white rounded-lg p-2 text-center shadow-sm min-w-[95px]">
          <div class="text-sm font-semibold text-orange-600 mb-1">近三天新开校招</div>
          <div class="text-2xl font-extrabold text-orange-600">{{ formatNumber(countdownData.lastThreeDaysOpenCount) }}</div>
        </div>
        <div class="bg-white rounded-lg p-2 text-center shadow-sm min-w-[95px]">
          <div class="text-sm font-semibold text-green-600 mb-1">内推企业总量</div>
          <div class="text-2xl font-extrabold text-green-600">{{ formatNumber(countdownData.referralCompanyCount) }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, onMounted, onUnmounted } from 'vue'
import { webInfoApi } from '@/api/webInfo'
import type { CountResponse } from '@/api/types'
import dayjs from 'dayjs'

const countdownData = reactive<CountResponse>({
  endTime: 0,
  totalOpenCount: 0,
  monthOpenCount: 0,
  lastThreeDaysOpenCount: 0,
  referralCompanyCount: 0
})

const timeLeft = reactive({
  months: '00',
  days: '00',
  hours: '00',
  minutes: '00',
  seconds: '00'
})

let timer: NodeJS.Timeout | null = null

const formatNumber = (num: number): string => {
  if (num >= 1000) return (num / 1000).toFixed(1).replace('.0', '') + 'k'
  return num.toString()
}

const calculateTimeLeft = () => {
  if (!countdownData.endTime || countdownData.endTime === 0) return;

  const now = dayjs();
  const endTime = dayjs(countdownData.endTime);
  
  if (endTime.isBefore(now)) {
    if (timer) clearInterval(timer);
    Object.assign(timeLeft, { months: '00', days: '00', hours: '00', minutes: '00', seconds: '00' });
    return;
  }

  const months = endTime.diff(now, 'month');
  const dateAfterMonths = now.add(months, 'month');
  
  const remainingDuration = endTime.diff(dateAfterMonths);

  const days = Math.floor(remainingDuration / (1000 * 60 * 60 * 24));
  const hours = Math.floor((remainingDuration % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  const minutes = Math.floor((remainingDuration % (1000 * 60 * 60)) / (1000 * 60));
  const seconds = Math.floor((remainingDuration % (1000 * 60)) / 1000);

  timeLeft.months = String(months).padStart(2, '0');
  timeLeft.days = String(days).padStart(2, '0');
  timeLeft.hours = String(hours).padStart(2, '0');
  timeLeft.minutes = String(minutes).padStart(2, '0');
  timeLeft.seconds = String(seconds).padStart(2, '0');
}

const startTimer = () => {
  if (timer) clearInterval(timer);
  if (countdownData.endTime > 0) {
    calculateTimeLeft(); // Initial calculation
    timer = setInterval(calculateTimeLeft, 1000);
  }
}

const fetchCountdownData = async () => {
  try {
    const response = await webInfoApi.getCountdown();
    if (response.data && response.data.endTime) {
      // 将API返回的字符串转换为数字
      const data = {
        endTime: Number(response.data.endTime),
        totalOpenCount: Number(response.data.totalOpenCount),
        monthOpenCount: Number(response.data.monthOpenCount),
        lastThreeDaysOpenCount: Number(response.data.lastThreeDaysOpenCount),
        referralCompanyCount: Number(response.data.referralCompanyCount)
      };
      Object.assign(countdownData, data);
    } else {
      throw new Error("Invalid data from API");
    }
  } catch (error) {
    console.error('获取倒计时数据失败:', error);
    countdownData.endTime = 1782835199000; // Use fallback
  } finally {
    // ALWAYS start the timer after attempting to fetch data
    startTimer();
  }
}

onMounted(() => {
  fetchCountdownData();
});

onUnmounted(() => {
  if (timer) clearInterval(timer);
});
</script>

<style scoped>
/* 响应式设计 */
@media (max-width: 1280px) {
  .countdown-container {
    left: -20px;
  }
  .stats-container {
    right: -20px;
  }
}

@media (max-width: 1024px) {
  .relative {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1.5rem;
    min-height: auto;
    padding-top: 1rem;
    padding-bottom: 1rem;
  }
  .absolute {
    position: static;
    transform: none;
  }
  .py-6 {
    padding-top: 0;
    padding-bottom: 0;
  }
  .countdown-container {
    order: 1;
  }
  .text-center {
    order: 2;
  }
  .stats-container {
    order: 3;
  }
  .stats-container .flex {
    justify-content: center;
    flex-wrap: wrap;
  }
}

@media (max-width: 768px) {
  .stats-container .flex {
    flex-wrap: nowrap; /* 强制一行显示 */
  }
  .min-w-\[95px\] {
    min-width: 80px; /* 缩小统计组件 */
    padding: 0.3rem;
  }
  .text-2xl {
    font-size: 1.25rem; /* 调整字体 */
  }
  .text-sm {
    font-size: 0.75rem;
  }
  .text-4xl {
    font-size: 1.5rem;
  }
}

@media (max-width: 480px) {
  .min-w-\[95px\] {
    min-width: 70px; /* 进一步缩小 */
  }
  .text-2xl {
    font-size: 1.1rem;
  }
  .text-sm {
    font-size: 0.65rem;
  }
  .min-w-\[45px\] {
    min-width: 40px; /* 缩小倒计时框 */
  }
  .text-lg {
    font-size: 1rem;
  }
}
</style>