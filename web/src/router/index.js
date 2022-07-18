import { createRouter, createWebHistory } from 'vue-router';
import home from "@/components/home.vue"

const routes = [
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
]

export const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router
