import {
    get,
    post,
    update,
    del
} from '@/utils/axios'

export default {
    data(){
        return {
            auth : '/metaData/auth',
            sys : '/metaData/sys',
        }
    },

    login(param)  {
        return post(this.data().auth + "/token", param);
    },

    logout(param)  {
        return post(this.data().auth + "/logout", param);
    },

    getRoleRelByUserId(param)  {
        return post(this.data().sys + "/getRoleRelByUserId?userId=" + param);
    },

    getStepBySort(param)  {
        return post(this.data().sys + "/getStepBySort?sort=" + param.sort + "&stepVersion=" + param.stepVersion);
    },

    getTargetSelect(param)  {
        return post(this.data().sys + "/getTargetSelect?stepId=" + param);
    },

    hasStepAble(param)  {
        return post(this.data().sys + "/hasStepAble?stepId=" + param.step + "&userId=" + param.userId);
    },
}
