import {
    get,
    post,
    update,
    del
} from '@/utils/axios'

export default {
    data(){
        return {
            base : '/metaData/db/option',
        }
    },

    getOption(param)  {
        return post(this.data().base + "/getOption", param);
    },

    getOptionById(param)  {
        return post(this.data().base + "/getOptionById?optId=" + param);
    },

    createOption(param)  {
        return post(this.data().base + "/createOption", param);
    },

    finishOption(param) {
        return post(this.data().base + "/finishOption?optId=" + param.optId + "&step=" + param.step + "&userId=" + param.userId);
    },

    getTableOptTypeSelect() {
        return post(this.data().base + "/getTableOptTypeSelect");
    },

    getColumnOptTypeSelect() {
        return post(this.data().base + "/getColumnOptTypeSelect");
    },

    getIndexOptTypeSelect() {
        return post(this.data().base + "/getIndexOptTypeSelect");
    },

    isOptionInProc(param) {
        return post(this.data().base + "/isOptionInProc", param);
    },

    getOptionByTableAndSchema(param) {
        return post(this.data().base + "/getOptionByTableAndSchema", param);
    },

    exportApplicationForm(param) {
        return post(this.data().base + "/exportApplicationForm?optId=" + param);
    },
}
