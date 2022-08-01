import {
    get,
    post,
    update,
    del
} from '@/utils/axios'

export default {
    data(){
        return {
            base : '/metaData/auth',
        }
    },

    login(param)  {
        return post(this.data().base + "/token", param);
    },

    logout(param)  {
        return post(this.data().base + "/logout", param);
    },
}
