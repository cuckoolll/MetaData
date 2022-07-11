import {
    get,
    post,
    update,
    del
} from '@/utils/axios'

export default {
    data(){
        return {
            base : '/metaData/db/conf',
        }
    },

    saveDbConf(param)  {
        return post(this.data().base + "/save", param);
    },

    // getDbConf() {
    //     return get(this.data().base + "/getDbconf");
    // },

    getDbConfByPost(param) {
        return post(this.data().base + "/getDbconfByPost", param);
    },

    getSchemaSelect(param) {
        return post(this.data().base + "/getSchemaSelect", param);
    }
}
