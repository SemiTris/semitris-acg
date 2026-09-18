import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'

// 技术选型权威：Vue3 + Element Plus + Vite；@ 别名 + /api 代理到后端 8080
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        // 剥掉 /api 前缀，转发到后端 controller（/api/anime/page → /anime/page）
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})
