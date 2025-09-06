import request from './request'

export interface Material {
  id: string
  fileName: string
  category: string
  fileUuid: string
  totalPages: number | null
  fileSize: string | number
  viewCount: number
  createTime: number
  updateTime: number
}

export interface PageResponse<T> {
  list: T[]
  total: number | string
  pageNum: number
  pageSize: number
  pages: number
  hasNext: boolean
  hasPrevious: boolean
}

// 获取所有分类
export function getMaterialCategories(): Promise<string[]> {
  return request.get('/material/categories').then(res => res.data.data)
}

// 分页获取资料列表（遵循后端返回值）
export function listMaterials(params: {
  pageNum: number
  pageSize: number
  category?: string
  keyword?: string
  sortField?: string
  sortOrder?: string
}): Promise<PageResponse<Material>> {
  return request.post('/material/list', params).then(res => res.data.data)
}

// 根据ID获取资料详情（后端路径为 /material/get/{id}）
export function getMaterialById(id: string): Promise<Material> {
  return request.get(`/material/get/${id}`).then(res => res.data.data)
}

// 构建PDF预览图片URL（第一页预览）
export function buildPdfPreviewUrl(bookId: string, pageNum = 1): string {
  return `/api/pdf/preview/${bookId}/page/${pageNum}`
}

// 上传PDF（管理员）
export async function uploadPdf(file: File, category: string): Promise<string> {
  const form = new FormData()
  form.append('file', file)
  form.append('category', category)
  const res = await request.post('/pdf/upload', form, {
    headers: { 'Content-Type': 'multipart/form-data' }
  })
  return res.data as unknown as string
}