import Vuex from "vuex";

export default new Vuex.Store({
    state() {
        const data = {
            count: 0,
            menuActiveIndex: "/home",  //管理后台左侧导航

            user: {
                username: null,
                nickname: null,
                email: null,
                role: null,
            },
        }

        // const loginData = user.loadUserLoginData()
        // if (loginData) {
        //     data.user.nickname = loginData.nickname
        //     data.user.usernmae = loginData.usernmae
        //     data.user.email = loginData.email
        //     data.user.avatar = loginData.avatar
        // }

        return data;
    },
    mutations : {
        increment(state) {
            state.data.count++
        },

        userUpdate(state, param) {
            if (param.usernmae) {
                state.user.usernmae = param.usernmae
            }
            if (param.nickname) {
                state.user.nickname = param.nickname
            }
            if (param.email) {
                state.user.email = param.email
            }
            if (param.role) {
                state.user.role = param.role
            }
        },
    }
});