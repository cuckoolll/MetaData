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

    saveConstTable(param) {
		return post(this.data().base + "/saveConstTable", param);
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

    exportDataWithSql(param) {
        return post(this.data().base + "/exportDataWithSql", param);
    },

    getData(param) {
        return post(this.data().base + "/getData", param);
    },

    getColumnQuerySelectWithoutCommon(param) {
        return post(this.data().base + "/getColumnQuerySelectWithoutCommon", param);
    },

    saveConstData(param) {
        return post(this.data().base + "/saveConstData", param);
    },

    deleteConstData(param) {
        return post(this.data().base + "/deleteConstData", param);
    },

    deleteConstTable(tableId) {
        return post(this.data().base + "/deleteConstTable?tableId=" + tableId);
    },
}
