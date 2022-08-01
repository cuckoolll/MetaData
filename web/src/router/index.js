import { createRouter, createWebHistory } from 'vue-router';
import home from "@/components/home.vue"

const routes = [
    {
        path: '/login',
        component: () => import('@/components/login.vue'),
        hidden: true,
        meta: {
            requireAuth: false
        }
    },
    {
        path: '/home',
        name: 'home',
        icon: 'HomeFilled',
        meta: {
            nav: '主页',
        },
        component: home,
        children: [
            {
                path: '/home',
                hidden: true,
                component: () => import('@/views/dbSchema.vue'),
            }
        ]
    },
    {
        path: '/table',
        name: 'table',
        icon: 'Collection',
        meta: {
            nav: '表数据',
        },
        component: home,
        children: [
            {
                path: '/table',
                hidden: true,
                component: () => import('@/views/dbTable.vue'),
            }
        ]
    },
    {
        path: '/optionRecord',
        name: 'optionRecord',
        icon: 'Notebook',
        meta: {
            nav: '变更记录',
        },
        component: home,
        children: [
            {
                path: '/optionRecord',
                hidden: true,
                component: () => import('@/views/optionRecord.vue'),
            }
        ]
    },
    {
        path: '/constTable',
        name: 'constTable',
        icon: 'List',
        meta: {
            nav: '常量表',
        },
        component: home,
        children: [
            {
                path: '/constTable',
                hidden: true,
                component: () => import('@/views/constTable.vue'),
            }
        ]
    },
]

export const router = createRouter({
    history: createWebHistory(),
    routes
});

//路由守卫
router.beforeEach((to, from, next) => {
    if (
        to.query.token != null &&
        to.query.token != "" &&
        to.query.token != undefined
    ) {
        localStorage.setItem("access_token", to.query.token);
        router.push({ path: "/home"});
        return false;
    }
    const isLogin = localStorage.getItem("access_token");
    if (isLogin) {
        next();
        if (to.path === "/login") {
            next("/home");
        }
    } else {
        //如果用户token不存在则跳转到login页面
        if (to.path === "/login") {
            next();
        } else {
            next("/login");
        }
    }
});

export default router
