<template>
    <!-- 弹窗：兑换会员 -->
    <div v-if="showRedeemModal" class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black bg-opacity-50">
        <RedeemMemberModal :available-commission="commissionData.balanceCommission" @close="showRedeemModal = false"
            @confirm="handleRedeemConfirm" />
    </div>
    <!-- 弹窗：提现 -->
    <div v-if="showWithdrawModal"
        class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black bg-opacity-50">
        <WithdrawCashModal @close="showWithdrawModal = false" />
    </div>
</template>