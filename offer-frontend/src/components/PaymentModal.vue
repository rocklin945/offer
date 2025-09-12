<template>
    <teleport to="body">
        <div class="modal-backdrop fixed inset-0 bg-black bg-opacity-50 transition-opacity duration-500 ease-out"
            :class="isVisible ? 'opacity-100' : 'opacity-0'" style="z-index: 99999;"></div>
        <div class="modal-container fixed inset-0 flex items-center justify-center p-4" style="z-index: 100000;"
            @click.self="close">
            <div class="bg-white rounded-2xl w-full max-w-md transform transition-all duration-500 ease-out"
                :class="isVisible ? 'scale-100 opacity-100 translate-y-0' : 'scale-95 opacity-0 -translate-y-4'">
                <div class="p-6">
                    <div class="flex justify-between items-center mb-4">
                        <h3 class="text-xl font-bold text-gray-900">选择支付方式</h3>
                        <button @click="close" class="text-gray-400 hover:text-gray-500">
                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M6 18L18 6M6 6l12 12"></path>
                            </svg>
                        </button>
                    </div>

                    <div class="mb-6">
                        <p class="text-gray-600 mb-2">支付金额：</p>
                        <p class="text-2xl font-bold text-gray-900">¥{{ price }}</p>
                    </div>

                    <div class="space-y-4">
                        <button @click="selectPaymentMethod('alipay')"
                            class="w-full flex items-center p-4 border border-gray-200 rounded-xl hover:border-blue-500 hover:bg-blue-50 transition-colors">
                            <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center mr-4">
                                <svg t="1757667686966" class="icon w-6 h-6 text-blue-600" viewBox="0 0 1024 1024"
                                    version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1481" width="200"
                                    height="200">
                                    <path
                                        d="M230.4 576.512c-12.288 9.728-25.088 24.064-28.672 41.984-5.12 24.576-1.024 55.296 22.528 79.872 28.672 29.184 72.704 37.376 91.648 38.912 51.2 3.584 105.984-22.016 147.456-50.688 16.384-11.264 44.032-34.304 70.144-69.632-59.392-30.72-133.632-64.512-212.48-61.44-40.448 1.536-69.632 9.728-90.624 20.992z m752.64 135.68c26.112-61.44 40.96-129.024 40.96-200.192C1024 229.888 794.112 0 512 0S0 229.888 0 512s229.888 512 512 512c170.496 0 321.536-83.968 414.72-211.968-88.064-43.52-232.96-115.712-322.56-159.232-42.496 48.64-105.472 97.28-176.64 118.272-44.544 13.312-84.992 18.432-126.976 9.728-41.984-8.704-72.704-28.16-90.624-47.616-9.216-10.24-19.456-22.528-27.136-37.888 0.512 1.024 1.024 2.048 1.024 3.072 0 0-4.608-7.68-7.68-19.456-1.536-6.144-3.072-11.776-3.584-17.92-0.512-4.096-0.512-8.704 0-12.8-0.512-7.68 0-15.872 1.536-24.064 4.096-20.48 12.8-44.032 35.328-65.536 49.152-48.128 114.688-50.688 148.992-50.176 50.176 0.512 138.24 22.528 211.968 48.64 20.48-43.52 33.792-90.112 41.984-121.344h-307.2v-33.28h157.696v-66.56H272.384V302.08h190.464V235.52c0-9.216 2.048-16.384 16.384-16.384h74.752V302.08h207.36v33.28h-207.36v66.56h165.888s-16.896 92.672-68.608 184.32c115.2 40.96 278.016 104.448 331.776 125.952z"
                                        fill="#06B4FD" p-id="1482"></path>
                                </svg>
                            </div>
                            <div class="text-left">
                                <div class="font-semibold text-gray-900">支付宝支付</div>
                                <div class="text-sm text-gray-500">推荐使用支付宝支付</div>
                            </div>
                        </button>

                        <button @click="selectPaymentMethod('wxpay')"
                            class="w-full flex items-center p-4 border border-gray-200 rounded-xl hover:border-green-500 hover:bg-green-50 transition-colors">
                            <div class="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center mr-4">
                                <svg t="1757667738388" class="icon w-6 h-6 text-green-600" viewBox="0 0 1144 1024"
                                    version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2527" width="200"
                                    height="200">
                                    <path
                                        d="M436.314353 632.771765c-68.517647 36.321882-78.667294-20.389647-78.667294-20.389647l-85.835294-190.524236c-33.039059-90.533647 28.581647-40.839529 28.581647-40.839529s52.856471 38.038588 93.003294 61.229176c40.086588 23.190588 85.835294 6.806588 85.835294 6.806589l561.212235-246.362353C936.899765 80.112941 765.891765 0 572.235294 0 256.180706 0 0 213.232941 0 476.310588c0 151.311059 84.811294 285.967059 216.937412 373.248l-23.792941 130.288941s-11.625412 38.038588 28.611764 20.389647c27.437176-12.047059 97.370353-55.115294 138.992941-81.347764 65.445647 21.684706 136.734118 33.731765 211.486118 33.731764 316.024471 0 572.235294-213.232941 572.235294-476.310588 0-76.197647-21.594353-148.178824-59.843764-212.028235-178.808471 102.309647-594.733176 340.118588-648.312471 368.489412z"
                                        fill="#43C93E" p-id="2528"></path>
                                </svg>
                            </div>
                            <div class="text-left">
                                <div class="font-semibold text-gray-900">微信支付</div>
                                <div class="text-sm text-gray-500">微信扫码支付</div>
                            </div>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </teleport>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits, onMounted, nextTick } from 'vue'

const props = defineProps<{
    price: number
}>()

const emit = defineEmits<{
    (e: 'close'): void
    (e: 'selectPayment', method: string): void
}>()

const isVisible = ref(false)

const close = () => {
    isVisible.value = false
    setTimeout(() => emit('close'), 300)
}

const selectPaymentMethod = (method: string) => {
    emit('selectPayment', method)
}

onMounted(async () => {
    // 给一点延迟确保DOM已经渲染
    await nextTick()
    setTimeout(() => {
        isVisible.value = true
    }, 10)
})
</script>