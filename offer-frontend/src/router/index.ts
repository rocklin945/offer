import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'
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
    {
      path: '/my-apply',
      name: 'MyJobApply',
      component: () => import('@/views/MyJobApply.vue')
    },
    {
      path: '/my-resume',
      name: 'MyResume',
      component: () => import('@/views/MyResume.vue')
    },
    {
      path: '/become-member',
      name: 'BecomeMember',
      component: () => import('@/views/BecomeMember.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/invite-rebate',
      name: 'InviteRebate',
      component: () => import('@/views/InviteRebate.vue'),
      meta: { requiresAuth: true }
    },
    // 后台管理路由
    {
      path: '/admin',
      name: 'Admin',
      component: () => import('@/views/admin/AdminLayout.vue'),
      redirect: '/admin/dashboard',
      meta: { requiresAuth: true, requiresAdmin: true },
      children: [
        {
          path: 'dashboard',
          name: 'Dashboard',
          component: () => import('@/views/admin/Dashboard.vue'),
          meta: { title: '仪表盘', icon: 'dashboard', requiresAuth: true, requiresAdmin: true }
        },
        {
          path: 'job-management',
          name: 'JobManagement',
          component: () => import('@/views/admin/JobManagement.vue'),
          meta: { title: '招聘信息管理', icon: 'job', requiresAuth: true, requiresAdmin: true }
        },
        {
          path: 'job-management/add',
          name: 'AdminJobAdd',
          component: () => import('@/views/admin/JobAdd.vue'),
          meta: { title: '添加招聘信息', icon: 'add', requiresAuth: true, requiresAdmin: true }
        },
        {
          path: 'job-management/edit/:id',
          name: 'AdminJobEdit',
          component: () => import('@/views/admin/JobEdit.vue'),
          meta: { title: '编辑招聘信息', icon: 'edit', requiresAuth: true, requiresAdmin: true },
          props: true
        },
        {
          path: 'user-management',
          name: 'UserManagement',
          component: () => import('@/views/admin/UserManagement.vue'),
          meta: { title: '用户管理', icon: 'user', requiresAuth: true, requiresAdmin: true }
        },
        {
          path: 'user-job-apply-management',
          name: 'UserJobApplyManagement',
          component: () => import('@/views/admin/UserJobApplyManagement.vue'),
          meta: { title: '投递记录管理', icon: 'apply', requiresAuth: true, requiresAdmin: true }
        }
      ]
    }
  ]
})

// 路由守卫
router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()

  // 检查是否需要认证
  if (to.meta.requiresAuth) {
    // 如果没有token，跳转到首页
    if (!userStore.token) {
      next('/')
      return
    }

    // 如果有token但没有用户信息，尝试获取用户信息
    if (!userStore.currentUser) {
      try {
        await userStore.initUserInfo()
      } catch (error) {
        // 获取用户信息失败，清除token并跳转到首页
        userStore.userLogout()
        next('/')
        return
      }
    }

    // 检查是否需要管理员权限
    if (to.meta.requiresAdmin && !userStore.isAdmin()) {
      next('/')
      return
    }
  }

  next()
})

export default router
