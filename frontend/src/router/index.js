import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '@/layout/MainLayout.vue'
import AnimeWall from '@/views/anime/AnimeWall.vue'
import AnimeDetail from '@/views/anime/AnimeDetail.vue'
import CollectionList from '@/views/collection/CollectionList.vue'
import RecommendWall from '@/views/recommend/RecommendWall.vue'
import PlaceholderPage from '@/views/PlaceholderPage.vue'

// 5 条路由：番剧库/详情/追番/安利墙完整实现；登录后置（占位）
const routes = [
  {
    path: '/',
    component: MainLayout,
    redirect: '/anime',
    children: [
      { path: 'anime', name: 'anime', component: AnimeWall },
      { path: 'anime/:id', name: 'animeDetail', component: AnimeDetail },
      { path: 'collection', name: 'collection', component: CollectionList },
      { path: 'recommend', name: 'recommend', component: RecommendWall },
      { path: 'login', name: 'login', component: PlaceholderPage }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
