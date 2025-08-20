import { createRouter, createWebHistory } from 'vue-router'
import JobList from '@/views/JobList.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // 前台路由
    {
      path: '/',
      name: 'JobList',
      component: JobList
    },
    // 后台管理路由
    {
      path: '/admin',
      name: 'Admin',
      component: () => import('@/views/admin/AdminLayout.vue'),
      redirect: '/admin/dashboard',
      children: [
        {
          path: 'dashboard',
          name: 'Dashboard',
          component: () => import('@/views/admin/Dashboard.vue'),
          meta: { title: '仪表盘', icon: 'dashboard' }
        },
        {
          path: 'job-management',
          name: 'JobManagement',
          component: () => import('@/views/admin/JobManagement.vue'),
          meta: { title: '招聘信息管理', icon: 'job' }
        },
        {
          path: 'job-management/add',
          name: 'AdminJobAdd',
          component: () => import('@/views/admin/JobAdd.vue'),
          meta: { title: '添加招聘信息', icon: 'add' }
        },
        {
          path: 'job-management/edit/:id',
          name: 'AdminJobEdit',
          component: () => import('@/views/admin/JobEdit.vue'),
          meta: { title: '编辑招聘信息', icon: 'edit' },
          props: true
        },
        {
          path: 'user-management',
          name: 'UserManagement',
          component: () => import('@/views/admin/UserManagement.vue'),
          meta: { title: '用户管理', icon: 'user' }
        }
      ]
    }
  ]
})

export default router
