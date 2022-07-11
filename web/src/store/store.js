import Vuex from "vuex";

export default new Vuex.Store({
    state : {
        count: 0,
        menuActiveIndex: "/home",  //管理后台左侧导航
    },
    mutations : {
        increment(state) {
            state.count++
        }
    }
});