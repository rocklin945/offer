import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { jobInfoApi } from '@/api/jobInfo'
import type { JobInfo, JobInfoQueryRequest, JobInfoAddRequest, JobInfoUpdateRequest } from '@/api/types'

export const useJobInfoStore = defineStore('jobInfo', () => {
  // 状态
  const jobList = ref<JobInfo[]>([])
  const total = ref(0)
  const loading = ref(false)
  const currentJob = ref<JobInfo | null>(null)

  // 计算属性
  const hasJobs = computed(() => jobList.value.length > 0)

  // 获取招聘信息列表
  const fetchJobList = async (params: JobInfoQueryRequest = {}) => {
    loading.value = true
    try {
      // 设置默认分页参数，但不覆盖传入的参数
      const requestParams = {
        pageNum: 1,
        pageSize: 10,
        ...params
      }
      
      console.log('请求参数:', requestParams) // 调试日志
      
      const response = await jobInfoApi.getList(requestParams)
      
      console.log('API响应:', response) // 调试日志
      
      jobList.value = response.data.list || []
      total.value = response.data.total || 0
      
      console.log('设置的数据:', { jobList: jobList.value, total: total.value }) // 调试日志
    } catch (error) {
      console.error('获取招聘信息列表失败:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  // 根据ID获取招聘信息
  const fetchJobById = async (id: number) => {
    loading.value = true
    try {
      const response = await jobInfoApi.getById(id)
      currentJob.value = response.data
      return response.data
    } catch (error) {
      console.error('获取招聘信息详情失败:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  // 添加招聘信息
  const addJob = async (data: JobInfoAddRequest) => {
    loading.value = true
    try {
      const response = await jobInfoApi.add(data)
      if (response.data) {
        // 添加成功后刷新列表
        await fetchJobList()
      }
      return response.data
    } catch (error) {
      console.error('添加招聘信息失败:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  // 更新招聘信息
  const updateJob = async (data: JobInfoUpdateRequest) => {
    loading.value = true
    try {
      const response = await jobInfoApi.update(data)
      if (response.data) {
        // 更新成功后刷新列表
        await fetchJobList()
      }
      return response.data
    } catch (error) {
      console.error('更新招聘信息失败:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  // 删除招聘信息
  const deleteJob = async (id: number) => {
    loading.value = true
    try {
      const response = await jobInfoApi.delete(id)
      if (response.data) {
        // 删除成功后刷新列表
        await fetchJobList()
      }
      return response.data
    } catch (error) {
      console.error('删除招聘信息失败:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  // 重置状态
  const resetState = () => {
    jobList.value = []
    total.value = 0
    currentJob.value = null
    loading.value = false
  }

  return {
    // 状态
    jobList,
    total,
    loading,
    currentJob,
    // 计算属性
    hasJobs,
    // 方法
    fetchJobList,
    fetchJobById,
    addJob,
    updateJob,
    deleteJob,
    resetState
  }
})