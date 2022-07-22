import {
    get,
    post,
    update,
    del
} from '@/utils/axios'

export default {
    data(){
        return {
            base : '/metaData/db/manager',
        }
    },

    testConnection(param) {
        return post(this.data().base + "/testConnection", param);
    },

    syncMetaData(param) {
        return post(this.data().base + "/syncMetaData", param);
    },
	
	getDbTable(param) {
		return post(this.data().base + "/getDbTable", param);
	},

    getDbColumn(param) {
        return post(this.data().base + "/getDbColumn", param);
    },

    getDbIndex(param) {
        return post(this.data().base + "/getDbIndex", param);
    },

    getDb(param) {
        return post(this.data().base + "/getDb", param);
    },

    saveDb(param) {
        return post(this.data().base + "/saveDb", param);
    },

    delDb(delId) {
        return post(this.data().base + '/delDb?projectId=' + delId);
    },

    exportTableSql(param) {
        return post(this.data().base + '/exportTableSql?tableId=' + param);
    },
}
