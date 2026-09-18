import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '@/layout/MainLayout.vue'
import AnimeWall from '@/views/anime/AnimeWall.vue'
import PlaceholderPage from '@/views/PlaceholderPage.vue'

// 5 条路由（批次 A 满足可导航）；本期仅 /anime 为完整页，
// 详情/追番/安利墙/登录 注册但指向占位页（批次 C 后置实现）。
const routes = [
  {
    path: '/',
    component: MainLayout,
    redirect: '/anime',
    children: [
      { path: 'anime', name: 'anime', component: AnimeWall },
      { path: 'anime/:id', name: 'animeDetail', component: PlaceholderPage },
      { path: 'collection', name: 'collection', component: PlaceholderPage },
      { path: 'recommend', name: 'recommend', component: PlaceholderPage },
      { path: 'login', name: 'login', component: PlaceholderPage }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
