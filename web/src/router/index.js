import { createRouter, createWebHistory } from 'vue-router';
import home from "@/components/home.vue"

const routes = [
    {
        path: '/home',
        icon: 'HomeFilled',
        meta: {
            nav: '主页',
        },
        component: home,
        children: [
            {
                path: '',
                hidden: true,
                component: () => import('@/views/project.vue'),
            }
        ]
    },
    // {
    //     path: '/setting',
    //     icon: 'Setting',
    //     meta: {
    //         nav: '设置',
    //     },
    //     component: home,
    //     children: [
    //         {
    //             path: '',
    //             hidden: true,
    //             component: () => import('@/views/dbConfDlg.vue'),
    //         }
    //     ]
    // },
]

export const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router
