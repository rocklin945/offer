<template>
  <!-- 校园经历 -->
  <div class="mt-8">
    <div class="flex justify-between items-center mb-4">
      <h3 class="text-lg font-medium text-gray-900">校园经历</h3>
      <button @click="$emit('add-school-experience')" type="button" class="btn-secondary">
        + 添加校园经历
      </button>
    </div>
    <div v-for="(school, index) in resumeForm.bankSchoolExperience" :key="index"
      class="border border-gray-200 rounded-md p-4 mb-4">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">经历名称</label>
          <div class="flex items-center space-x-2">
            <input v-model="school.name" type="text" placeholder="请输入经历名称" class="input-field flex-1" />
            <button @click="$emit('copy-to-clipboard', school.name, '经历名称')" class="resume-copy-btn">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">担任角色</label>
          <div class="flex items-center space-x-2">
            <input v-model="school.role" type="text" placeholder="请输入担任角色" class="input-field flex-1" />
            <button @click="$emit('copy-to-clipboard', school.role, '担任角色')" class="resume-copy-btn">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">开始时间</label>
          <div class="flex items-center space-x-2">
            <input v-model="school.startTime" type="text" placeholder="例如：2023.01" class="input-field flex-1" />
            <button @click="$emit('copy-to-clipboard', school.startTime, '开始时间')" class="resume-copy-btn">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">结束时间</label>
          <div class="flex items-center space-x-2">
            <input v-model="school.endTime" type="text" placeholder="例如：2023.06" class="input-field flex-1" />
            <button @click="$emit('copy-to-clipboard', school.endTime, '结束时间')" class="resume-copy-btn">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div class="md:col-span-2">
          <label class="block text-sm font-medium text-gray-700 mb-1">经历描述</label>
          <div class="flex space-x-2">
            <textarea v-model="school.description" rows="3" placeholder="请输入经历描述" class="input-field flex-1"></textarea>
            <button @click="$emit('copy-to-clipboard', school.description, '经历描述')"
              class="resume-copy-btn flex-shrink-0 self-start">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div class="md:col-span-2 flex justify-end">
          <button @click="$emit('remove-school-experience', index)" type="button"
            class="px-3 py-1 bg-red-300 text-white rounded-md hover:bg-red-600">
            删除
          </button>
        </div>
      </div>
    </div>
  </div>

  <!-- 专业证书 -->
  <div class="mt-8">
    <div class="flex justify-between items-center mb-4">
      <h3 class="text-lg font-medium text-gray-900">专业证书</h3>
      <button @click="$emit('add-professional-cert')" type="button" class="btn-secondary">
        + 添加专业证书
      </button>
    </div>
    <div v-for="(cert, index) in resumeForm.bankProfessionalCert" :key="index"
      class="border border-gray-200 rounded-md p-4 mb-4">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">证书名称</label>
          <div class="flex items-center space-x-2">
            <input v-model="cert.name" type="text" placeholder="请输入证书名称" class="input-field flex-1" />
            <button @click="$emit('copy-to-clipboard', cert.name, '证书名称')" class="resume-copy-btn">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">获得时间</label>
          <div class="flex items-center space-x-2">
            <input v-model="cert.obtainTime" type="text" placeholder="例如：2023.01" class="input-field flex-1" />
            <button @click="$emit('copy-to-clipboard', cert.obtainTime, '获得时间')" class="resume-copy-btn">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div class="md:col-span-2">
          <label class="block text-sm font-medium text-gray-700 mb-1">证书描述</label>
          <div class="flex space-x-2">
            <textarea v-model="cert.description" rows="3" placeholder="请输入证书描述" class="input-field flex-1"></textarea>
            <button @click="$emit('copy-to-clipboard', cert.description, '证书描述')"
              class="resume-copy-btn flex-shrink-0 self-start">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div class="md:col-span-2 flex justify-end">
          <button @click="$emit('remove-professional-cert', index)" type="button"
            class="px-3 py-1 bg-red-300 text-white rounded-md hover:bg-red-600">
            删除
          </button>
        </div>
      </div>
    </div>
  </div>

  <!-- 奖惩情况 -->
  <div class="mt-8">
    <div class="flex justify-between items-center mb-4">
      <h3 class="text-lg font-medium text-gray-900">奖惩情况</h3>
      <button @click="$emit('add-reward-punishment')" type="button" class="btn-secondary">
        + 添加奖惩情况
      </button>
    </div>
    <div v-for="(reward, index) in resumeForm.bankRewardsPunishments" :key="index"
      class="border border-gray-200 rounded-md p-4 mb-4">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">奖惩名称</label>
          <div class="flex items-center space-x-2">
            <input v-model="reward.name" type="text" placeholder="请输入奖惩名称" class="input-field flex-1" />
            <button @click="$emit('copy-to-clipboard', reward.name, '奖惩名称')" class="resume-copy-btn">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">获得时间</label>
          <div class="flex items-center space-x-2">
            <input v-model="reward.obtainTime" type="text" placeholder="例如：2023.01" class="input-field flex-1" />
            <button @click="$emit('copy-to-clipboard', reward.obtainTime, '获得时间')" class="resume-copy-btn">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div class="md:col-span-2">
          <label class="block text-sm font-medium text-gray-700 mb-1">奖惩描述</label>
          <div class="flex space-x-2">
            <textarea v-model="reward.description" rows="3" placeholder="请输入奖惩描述" class="input-field flex-1"></textarea>
            <button @click="$emit('copy-to-clipboard', reward.description, '奖惩描述')"
              class="resume-copy-btn flex-shrink-0 self-start">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div class="md:col-span-2 flex justify-end">
          <button @click="$emit('remove-reward-punishment', index)" type="button"
            class="px-3 py-1 bg-red-400 text-white rounded-md hover:bg-red-600">
            删除
          </button>
        </div>
      </div>
    </div>
  </div>

  <!-- 培训经历 -->
  <div class="mt-8">
    <div class="flex justify-between items-center mb-4">
      <h3 class="text-lg font-medium text-gray-900">培训经历</h3>
      <button @click="$emit('add-training-experience')" type="button" class="btn-secondary">
        + 添加培训经历
      </button>
    </div>
    <div v-for="(training, index) in resumeForm.bankTrainingExperience" :key="index"
      class="border border-gray-200 rounded-md p-4 mb-4">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">培训名称</label>
          <div class="flex items-center space-x-2">
            <input v-model="training.name" type="text" placeholder="请输入培训名称" class="input-field flex-1" />
            <button @click="$emit('copy-to-clipboard', training.name, '培训名称')" class="resume-copy-btn">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">培训时间</label>
          <div class="flex items-center space-x-2">
            <input v-model="training.trainingTime" type="text" placeholder="例如：2023.01" class="input-field flex-1" />
            <button @click="$emit('copy-to-clipboard', training.trainingTime, '培训时间')" class="resume-copy-btn">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div class="md:col-span-2">
          <label class="block text-sm font-medium text-gray-700 mb-1">培训描述</label>
          <div class="flex space-x-2">
            <textarea v-model="training.description" rows="3" placeholder="请输入培训描述"
              class="input-field flex-1"></textarea>
            <button @click="$emit('copy-to-clipboard', training.description, '培训描述')"
              class="resume-copy-btn flex-shrink-0 self-start">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z">
                </path>
              </svg>
            </button>
          </div>
        </div>
        <div class="md:col-span-2 flex justify-end">
          <button @click="$emit('remove-training-experience', index)" type="button"
            class="px-3 py-1 bg-red-400 text-white rounded-md hover:bg-red-600">
            删除
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  resumeForm: {
    type: Object,
    required: true
  }
})

defineEmits([
  'add-school-experience',
  'remove-school-experience',
  'add-professional-cert',
  'remove-professional-cert',
  'add-reward-punishment',
  'remove-reward-punishment',
  'add-training-experience',
  'remove-training-experience',
  'copy-to-clipboard'
])
</script>