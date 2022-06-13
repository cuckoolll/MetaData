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
	
}
