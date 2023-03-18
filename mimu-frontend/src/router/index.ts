import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {path: '/',name: 'home',component: () => import('../views/HomeView.vue')},
  {path: '/monitor',name: 'monitor',component: () => import('../views/monitor/indexView.vue')},
  {path: '/about',name: 'about',component: () => import('../views/AboutView.vue')}
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
