<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Hero Section -->
    <div class="bg-blue-100 text-gray-800">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16 sm:py-24">
        <div class="text-center">
          <h1 class="text-4xl sm:text-5xl lg:text-6xl font-bold tracking-tight mb-6">
            升级为会员
          </h1>
          <p class="text-xl sm:text-2xl text-gray-600 mb-12 max-w-3xl mx-auto">
            解锁更多功能，享受更好的求职体验
          </p>

          <!-- Stats -->
          <div class="grid grid-cols-1 sm:grid-cols-3 gap-8 max-w-4xl mx-auto">
            <div class="text-center">
              <div class="text-3xl sm:text-4xl font-bold mb-2 text-blue-600">2000+</div>
              <div class="text-gray-600">优质职位</div>
            </div>
            <div class="text-center">
              <div class="text-3xl sm:text-4xl font-bold mb-2 text-blue-600">500+</div>
              <div class="text-gray-600">合作企业</div>
            </div>
            <div class="text-center">
              <div class="text-3xl sm:text-4xl font-bold mb-2 text-blue-600">98%</div>
              <div class="text-gray-600">满意度</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Pricing Section -->
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16 sm:py-24">
      <!-- Section Header -->
      <div class="text-center mb-16">
        <h2 class="text-3xl sm:text-4xl font-bold text-gray-900 mb-4">
          选择适合您的套餐
        </h2>
        <p class="text-xl text-gray-600 max-w-2xl mx-auto">
          灵活的定价方案，满足不同需求
        </p>
      </div>

      <!-- Pricing Cards -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8 max-w-6xl mx-auto">
        <!-- 免费版 -->
        <div class="bg-white rounded-2xl shadow-lg border border-gray-200 p-8 relative">
          <div class="text-center mb-8">
            <h3 class="text-2xl font-bold text-gray-900 mb-2">免费版</h3>
            <div class="mb-4">
              <span class="text-4xl font-bold text-gray-900">¥0</span>
              <span class="text-gray-500">/永久</span>
            </div>
            <p class="text-gray-600">适合初次求职的用户</p>
          </div>

          <ul class="space-y-4 mb-8">
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">浏览前5页职位</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">基础投递功能</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-red-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                  clip-rule="evenodd"></path>
              </svg>
              <span class="text-gray-700">无法查看全部职位</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-red-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                  clip-rule="evenodd"></path>
              </svg>
              <span class="text-gray-700">无优先内推机会</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-red-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                  clip-rule="evenodd"></path>
              </svg>
              <span class="text-gray-700">无投递数据分析</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-red-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                  clip-rule="evenodd"></path>
              </svg>
              <span class="text-gray-700">无专属客服支持</span>
            </li>
          </ul>

          <button disabled class="w-full py-3 px-6 rounded-xl bg-gray-100 text-gray-400 font-semibold cursor-not-allowed">
            当前方案
          </button>
        </div>

        <!-- 会员版 -->
        <div class="bg-white rounded-2xl shadow-xl border-2 border-blue-500 p-8 relative">
          <div class="absolute -top-4 left-1/2 transform -translate-x-1/2">
            <span class="bg-blue-500 text-white px-6 py-2 rounded-full text-sm font-semibold">
              推荐
            </span>
          </div>

          <div class="text-center mb-8">
            <h3 class="text-2xl font-bold text-gray-900 mb-2">会员版</h3>
            <div class="mb-4">
              <span class="text-4xl font-bold text-gray-900">¥29.9</span>
              <span class="text-gray-500">/年</span>
            </div>
            <p class="text-gray-600">解锁全部功能</p>
          </div>

          <ul class="space-y-4 mb-8">
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">无限制浏览所有职位</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">享受投递记录功能</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">优先内推机会</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">投递数据分析</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">简历优化建议</span>
            </li>
            <li class="flex items-center">
              <svg class="w-5 h-5 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                  clip-rule="evenodd" />
              </svg>
              <span class="text-gray-700">专属客服支持</span>
            </li>
          </ul>
        </div>

        <!-- 扫码支付 -->
        <div class="bg-white rounded-2xl shadow-lg border border-gray-200 p-8 relative">
          <div class="text-center mb-8">
            <h3 class="text-2xl font-bold text-gray-900 mb-2">秋招求职季特惠¥9.9</h3>
            <div class="mb-4">
              <span class="text-lg text-gray-600">入群限时享特惠</span>
            </div>
            <p class="text-gray-600">专业售后+就业咨询</p>
          </div>

          <div class="text-center mb-8">
            <div class="inline-block p-4 bg-gray-100 rounded-xl">
              <!-- 加载状态 -->
              <div v-if="loading"
                class="w-48 h-48 bg-gradient-to-br from-blue-100 to-blue-200 rounded-lg flex items-center justify-center border-2 border-dashed border-blue-300">
                <div class="text-center">
                  <div
                    class="animate-spin w-8 h-8 border-4 border-blue-500 border-t-transparent rounded-full mx-auto mb-2">
                  </div>
                  <p class="text-blue-600 text-sm font-medium">加载中...</p>
                </div>
              </div>

              <!-- 显示二维码图片 -->
              <div v-else-if="imageUrl" class="w-48 h-48 rounded-lg overflow-hidden border-2 border-blue-300">
                <img :src="imageUrl" alt="会员二维码" class="w-full h-full object-cover" @error="onImageError" />
              </div>
            </div>
          </div>

          <div class="bg-blue-50 rounded-xl p-4 text-center">
            <div class="text-blue-800 font-semibold mb-1">¥9.9/年</div>
            <div class="text-blue-600 text-sm">享受所有会员特权</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Features Section -->
    <div class="bg-white py-16 sm:py-24">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="text-3xl sm:text-4xl font-bold text-gray-900 mb-4">
            为什么选择我们的会员服务
          </h2>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-12">
          <div class="text-center">
            <div
              class="w-16 h-16 bg-gradient-to-br from-blue-400 to-blue-600 rounded-2xl flex items-center justify-center mx-auto mb-6">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2-2v2m8 0V6a2 2 0 012 2v6a2 2 0 01-2 2H8a2 2 0 01-2-2V8a2 2 0 012-2V6">
                </path>
              </svg>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-4">海量优质职位</h3>
            <p class="text-gray-600 leading-relaxed">
              与500+知名企业合作，提供2000+优质职位，覆盖各行各业
            </p>
          </div>

          <div class="text-center">
            <div
              class="w-16 h-16 bg-gradient-to-br from-blue-500 to-blue-600 rounded-2xl flex items-center justify-center mx-auto mb-6">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z">
                </path>
              </svg>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-4">智能匹配推荐</h3>
            <p class="text-gray-600 leading-relaxed">
              基于AI算法，根据您的技能和偏好，精准推荐最适合的职位
            </p>
          </div>

          <div class="text-center">
            <div
              class="w-16 h-16 bg-gradient-to-br from-blue-600 to-blue-700 rounded-2xl flex items-center justify-center mx-auto mb-6">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z">
                </path>
              </svg>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-4">数据分析洞察</h3>
            <p class="text-gray-600 leading-relaxed">
              详细的投递数据分析，帮助您了解求职进展，优化求职策略
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- 用户评价 Section -->
    <div class="py-16 sm:py-24">
      <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="text-3xl sm:text-4xl font-bold text-gray-900 mb-4">
            看看他们都是如何评价MyOffer的
          </h2>
          <p class="text-xl text-gray-600">来自真实用户的反馈</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <!-- 评价卡片 1 - 校招 -->
          <div
            class="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
            <div class="flex items-center mb-4">
              <div
                class="w-12 h-12 bg-gradient-to-r from-blue-400 to-blue-600 rounded-full flex items-center justify-center text-white font-bold text-lg">
                张
              </div>
              <div class="ml-4">
                <h4 class="font-semibold text-gray-900">张同学</h4>
                <p class="text-sm text-gray-500">清华大学 · 现就职于字节跳动</p>
              </div>
            </div>
            <div class="flex mb-3">
              <span class="text-yellow-400">★★★★★</span>
            </div>
            <p class="text-gray-700 leading-relaxed">
              "通过MyOffer会员功能，我能够查看到字节跳动的内推职位，投递记录管理很方便。最终顺利拿到了抖音前端开发的offer！"
            </p>
          </div>

          <!-- 评价卡片 2 - 社招跳槽 -->
          <div
            class="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
            <div class="flex items-center mb-4">
              <div
                class="w-12 h-12 bg-gradient-to-r from-green-500 to-teal-600 rounded-full flex items-center justify-center text-white font-bold text-lg">
                李
              </div>
              <div class="ml-4">
                <h4 class="font-semibold text-gray-900">李女士</h4>
                <p class="text-sm text-gray-500">3年经验 · 从阿里跳槽至腾讯</p>
              </div>
            </div>
            <div class="flex mb-3">
              <span class="text-yellow-400">★★★★★</span>
            </div>
            <p class="text-gray-700 leading-relaxed">
              "工作3年想跳槽涨薪，MyOffer会员功能让我看到了腾讯高级产品经理的内推机会。薪资涨了40%，非常满意！"
            </p>
          </div>

          <!-- 评价卡片 3 - 技术转管理 -->
          <div
            class="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
            <div class="flex items-center mb-4">
              <div
                class="w-12 h-12 bg-gradient-to-r from-blue-500 to-blue-600 rounded-full flex items-center justify-center text-white font-bold text-lg">
                王
              </div>
              <div class="ml-4">
                <h4 class="font-semibold text-gray-900">王先生</h4>
                <p class="text-sm text-gray-500">5年经验 · 现任美团技术总监</p>
              </div>
            </div>
            <div class="flex mb-3">
              <span class="text-yellow-400">★★★★★</span>
            </div>
            <p class="text-gray-700 leading-relaxed">
              "从Java开发转技术管理，MyOffer帮我找到了美团技术总监岗位。平台的高端职位资源很丰富，投递流程专业！"
            </p>
          </div>

          <!-- 评价卡片 4 - 校招 -->
          <div
            class="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
            <div class="flex items-center mb-4">
              <div
                class="w-12 h-12 bg-gradient-to-r from-orange-500 to-red-600 rounded-full flex items-center justify-center text-white font-bold text-lg">
                陈
              </div>
              <div class="ml-4">
                <h4 class="font-semibold text-gray-900">陈同学</h4>
                <p class="text-sm text-gray-500">上海交通大学 · 现就职于小红书</p>
              </div>
            </div>
            <div class="flex mb-3">
              <span class="text-yellow-400">★★★★★</span>
            </div>
            <p class="text-gray-700 leading-relaxed">
              "应届毕业找工作很迷茫，MyOffer的投递记录管理和数据分析功能帮了大忙。现在已经成功入职小红书算法工程师！"
            </p>
          </div>

          <!-- 评价卡片 5 - 转行 -->
          <div
            class="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
            <div class="flex items-center mb-4">
              <div
                class="w-12 h-12 bg-gradient-to-r from-indigo-500 to-blue-600 rounded-full flex items-center justify-center text-white font-bold text-lg">
                刘
              </div>
              <div class="ml-4">
                <h4 class="font-semibold text-gray-900">刘女士</h4>
                <p class="text-sm text-gray-500">转行成功 · 从传统行业到网易</p>
              </div>
            </div>
            <div class="flex mb-3">
              <span class="text-yellow-400">★★★★★</span>
            </div>
            <p class="text-gray-700 leading-relaxed">
              "从传统制造业转行互联网，MyOffer会员功能让我看到了网易的UI设计师岗位。转行成功，薪资翻倍！"
            </p>
          </div>

          <!-- 评价卡片 6 - 职业发展 -->
          <div
            class="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
            <div class="flex items-center mb-4">
              <div
                class="w-12 h-12 bg-gradient-to-r from-teal-500 to-green-600 rounded-full flex items-center justify-center text-white font-bold text-lg">
                赵
              </div>
              <div class="ml-4">
                <h4 class="font-semibold text-gray-900">赵先生</h4>
                <p class="text-sm text-gray-500">7年经验 · 现任滴滴运营总监</p>
              </div>
            </div>
            <div class="flex mb-3">
              <span class="text-yellow-400">★★★★★</span>
            </div>
            <p class="text-gray-700 leading-relaxed">
              "从运营专员到运营总监，MyOffer陪伴了我的职业成长。现在在滴滴负责用户增长，平台的职位信息准确及时！"
            </p>
          </div>

          <!-- 评价卡片 7 - 海归求职 -->
          <div
            class="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
            <div class="flex items-center mb-4">
              <div
                class="w-12 h-12 bg-gradient-to-r from-purple-500 to-pink-600 rounded-full flex items-center justify-center text-white font-bold text-lg">
                周
              </div>
              <div class="ml-4">
                <h4 class="font-semibold text-gray-900">周女士</h4>
                <p class="text-sm text-gray-500">海归硕士 · 现就职于蚂蚁金服</p>
              </div>
            </div>
            <div class="flex mb-3">
              <span class="text-yellow-400">★★★★★</span>
            </div>
            <p class="text-gray-700 leading-relaxed">
              "英国留学回国求职，MyOffer帮我快速了解国内互联网行情。成功拿到蚂蚁金服风控算法工程师offer，待遇很满意！"
            </p>
          </div>

          <!-- 评价卡片 8 - 中年跳槽 -->
          <div
            class="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
            <div class="flex items-center mb-4">
              <div
                class="w-12 h-12 bg-gradient-to-r from-yellow-500 to-orange-600 rounded-full flex items-center justify-center text-white font-bold text-lg">
                吴
              </div>
              <div class="ml-4">
                <h4 class="font-semibold text-gray-900">吴先生</h4>
                <p class="text-sm text-gray-500">10年经验 · 现任京东架构师</p>
              </div>
            </div>
            <div class="flex mb-3">
              <span class="text-yellow-400">★★★★★</span>
            </div>
            <p class="text-gray-700 leading-relaxed">
              "35岁跳槽压力很大，MyOffer的高级会员功能让我看到了京东架构师岗位。年龄不是问题，技术实力才是王道！"
            </p>
          </div>

          <!-- 评价卡片 9 - 创业回归 -->
          <div
            class="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
            <div class="flex items-center mb-4">
              <div
                class="w-12 h-12 bg-gradient-to-r from-red-500 to-pink-600 rounded-full flex items-center justify-center text-white font-bold text-lg">
                孙
              </div>
              <div class="ml-4">
                <h4 class="font-semibold text-gray-900">孙女士</h4>
                <p class="text-sm text-gray-500">前创业者 · 现任拼多多产品总监</p>
              </div>
            </div>
            <div class="flex mb-3">
              <span class="text-yellow-400">★★★★★</span>
            </div>
            <p class="text-gray-700 leading-relaxed">
              "创业失败后重新求职，MyOffer帮我重新规划职业方向。现在在拼多多做产品总监，创业经历反而成了加分项！"
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- FAQ Section -->
    <div class="bg-gray-50 py-16 sm:py-24">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="text-3xl sm:text-4xl font-bold text-gray-900 mb-4">
            常见问题
          </h2>
        </div>

        <div class="space-y-4">
          <div v-for="(faq, index) in faqs" :key="index"
            class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
            <button @click="toggleFaq(index)"
              class="w-full px-6 py-4 text-left flex justify-between items-center hover:bg-gray-50 transition-colors duration-200">
              <h4 class="text-lg font-semibold text-gray-900">{{ faq.question }}</h4>
              <svg :class="['w-5 h-5 text-gray-500 transition-transform duration-200', faq.open ? 'rotate-180' : '']"
                fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                  d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                  clip-rule="evenodd" />
              </svg>
            </button>
            <div :class="[
              'overflow-hidden transition-all duration-300 ease-in-out',
              faq.open ? 'max-h-96 opacity-100' : 'max-h-0 opacity-0'
            ]">
              <div class="px-6 pb-4">
                <p class="text-gray-600 leading-relaxed">{{ faq.answer }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { webInfoApi } from '@/api/webInfo'

const isYearly = ref(false)
const imageUrl = ref<string | null>(null)
const loading = ref(true)

// 获取会员图片URL
const fetchMemberImageUrl = async () => {
  try {
    console.log('BecomeMember: 开始获取二维码图片URL...')
    loading.value = true
    const response = await webInfoApi.getMemberImageUrl()
    console.log('BecomeMember: 获取到响应:', response)
    if (response.statusCode === 200) {
      if (response.data && response.data.trim() !== '') {
        imageUrl.value = response.data
        console.log('BecomeMember: 设置图片URL:', response.data)
      } else {
        console.warn('BecomeMember: 返回的图片URL为空，使用占位图')
        imageUrl.value = null
      }
    } else {
      console.warn('BecomeMember: 响应状态码异常:', response)
      imageUrl.value = null
    }
  } catch (error) {
    console.error('BecomeMember: 获取会员图片URL失败:', error)
    imageUrl.value = null
  } finally {
    loading.value = false
    console.log('BecomeMember: 加载完成, imageUrl:', imageUrl.value)
  }
}

const faqs = ref([
  {
    question: '会员权益何时生效？',
    answer: '支付成功后立即生效，您可以马上享受所有会员特权。',
    open: false
  },
  {
    question: '会员能否共享账号，或者二次售卖？',
    answer: 'MyOffer拥有共号检测，如果发现有违规情况，会自动取消会员。',
    open: false
  },
  {
    question: '支持哪些支付方式？',
    answer: '支持微信支付、支付宝等多种支付方式。',
    open: false
  },
  {
    question: '会员可以退款吗？',
    answer: '支持7天无理由退款。',
    open: false
  }
])

const toggleFaq = (index: number) => {
  faqs.value[index].open = !faqs.value[index].open
}

// 处理图片加载失败
const onImageError = (event: Event) => {
  console.warn('二维码图片加载失败')
  // 可以在这里设置默认图片或者隐藏图片
}

// 组件挂载时获取会员图片URL
onMounted(() => {
  fetchMemberImageUrl()
})
</script>
