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

    getUsers(param)  {
        return post(this.data().sys + "/getUsers", param);
    },

    updateUserStatus(param)  {
        return post(this.data().sys + "/updateUserStatus?userId=" + param.userId + "&status=" + param.status);
    },

    getRoleSelect(param)  {
        return post(this.data().sys + "/getRoleSelect", param);
    },

    delUser(param)  {
        return post(this.data().sys + "/delUser?userId=" + param.userId);
    },

    saveUser(param)  {
        return post(this.data().sys + "/saveUser", param);
    },

    resetPassword(param)  {
        return post(this.data().sys + "/resetPassword?userId=" + param.userId);
    },

    getRoles(param)  {
        return post(this.data().sys + "/getRoles", param);
    },

    updateRoleStatus(param)  {
        return post(this.data().sys + "/updateRoleStatus?roleId=" + param.roleId + "&status=" + param.status);
    },

    delRole(param)  {
        return post(this.data().sys + "/delRole?roleId=" + param.roleId);
    },

    saveRole(param)  {
        return post(this.data().sys + "/saveRole", param);
    },

    getFuncs(param)  {
        return post(this.data().sys + "/getFuncs", param);
    },

    delFunc(param)  {
        return post(this.data().sys + "/delFunc?itemId=" + param.itemId);
    },

    saveFunc(param)  {
        return post(this.data().sys + "/saveFunc", param);
    },
}
