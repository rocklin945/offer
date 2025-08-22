import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import './style.css'
import Message from './components/Message'

const app = createApp(App)

app.use(createPinia())
app.use(router)

// 全局注册Message组件
app.config.globalProperties.$message = Message

app.mount('#app')
