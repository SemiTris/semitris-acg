import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/dark/css-vars.css'
import App from './App.vue'
import router from './router'
import './theme/variables.css'
import './theme/element-override.css'

// 挂载 Element Plus（暗色主题）+ 路由
const app = createApp(App)
app.use(ElementPlus)
app.use(router)
app.mount('#app')
