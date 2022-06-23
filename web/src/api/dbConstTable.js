import {
    get,
    post,
    update,
    del, postMutipartForm
} from '@/utils/axios'

export default {
    data(){
        return {
            base : '/metaData/db/constTable',
        }
    },

    createConstTable(param) {
		return post(this.data().base + "/createConstTable", param);
	},

    getConstTable(param) {
        return post(this.data().base + "/getConstTable", param);
    },

    getGridColumn(param) {
        return post(this.data().base + "/getGridColumn", param);
    },

    getGridColumnWithoutCommon(param) {
        return post(this.data().base + "/getGridColumnWithoutCommon", param);
    },

    importData(param) {
        return postMutipartForm(this.data().base + "/importData", param);
    },

    getData(param) {
        return post(this.data().base + "/getData", param);
    },

    getColumnQuerySelectWithoutCommon(param) {
        return post(this.data().base + "/getColumnQuerySelectWithoutCommon", param);
    },
}
