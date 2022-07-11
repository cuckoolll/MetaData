import { createApp } from 'vue'
import App from './App.vue'
import { router } from './router'
import element from 'element-plus';
import 'element-plus/theme-chalk/index.css'
import 'element-plus/dist/index.css'
import * as ElIconModules from '@element-plus/icons-vue';
import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import store from "@/store/store";

const app = createApp(App)
app.use(router)
app.use(element, {
	locale: zhCn,
})
app.use(store)
app.mount('#app')

// 全局注册element-plus icon图标组件
Object.keys(ElIconModules).forEach((key) => {
    app.component(key, ElIconModules[key]);
});