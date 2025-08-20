import { createRouter, createWebHistory } from 'vue-router'
import JobList from '@/views/JobList.vue'
import JobAdd from '@/views/JobAdd.vue'
import JobEdit from '@/views/JobEdit.vue'
import JobDetail from '@/views/JobDetail.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'JobList',
      component: JobList
    },
    {
      path: '/add',
      name: 'JobAdd',
      component: JobAdd
    },
    {
      path: '/edit/:id',
      name: 'JobEdit',
      component: JobEdit,
      props: true
    },
    {
      path: '/detail/:id',
      name: 'JobDetail',
      component: JobDetail,
      props: true
    }
  ]
})

export default router