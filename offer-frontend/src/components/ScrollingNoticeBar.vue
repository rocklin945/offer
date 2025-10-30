<template>
    <div class="bg-gradient-to-r from-red-500 to-orange-500 text-white py-2 px-4 relative overflow-hidden">
        <div class="animate-scroll whitespace-nowrap">
            <span class="mx-4 font-bold">📣 紧急通知：</span>
            <span class="mx-4 font-bold">网站将于3天后进行价格调整！</span>
            <span class="mx-4 font-bold">现在购买会员可享受限时优惠价格！</span>
            <span class="mx-4 font-bold">立即行动，节省更多！</span>
            <span class="mx-4">⭐ 会员专享特权：无限制浏览所有职位、享受投递记录功能、笔试面试资料无限看、优先内推机会、简历优化建议、专属客服支持等</span>
        </div>
    </div>
</template>

<script setup lang="ts">
// 滚动提示条幅组件
</script>

<style scoped>
@keyframes scroll {
    0% {
        transform: translateX(100%);
    }

    100% {
        transform: translateX(-100%);
    }
}

.animate-scroll {
    display: inline-block;
    animation: scroll 30s linear infinite;
    white-space: nowrap;
}
</style>