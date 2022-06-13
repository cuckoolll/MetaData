import axios from "axios";
import { Message, Loading } from "element-plus";
import router from "../router";

//公共加载动画

// let loading;
// let tips
// function startLoading() {
//   loading = Loading.service({
//     lock: true,
//     text: tips,
//     background: "rgba(0,0,0,0.7)",
//   });
// }

// function endLoading() {
//   loading.close();
// }

const service = axios.create({
  baseURL: process.env.BASE_API,
  // 超时时间 单位是ms，这里设置了3s的超时时间
  timeout: 3 * 1000 * 60,
});
//请求拦截
service.interceptors.request.use(
  (config) => {
    let token = localStorage.getItem("eleToken") || ""; //获取token
    if (token) {
      // //设置统一请求头
      config.headers.common = {
        Authorization: "Bearer " + token,
      };
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
//响应拦截
service.interceptors.response.use(
  (response) => {
    if (response.data.code == "A0230") {
      localStorage.removeItem("eleToken");
      router.push("/login");
    }
    //结束加载动画
    //endLoading();
    return response;
  },
  (error) => {
    // //错误提醒
   // endLoading();
    //获取错误状态码
    const { status } = error.response;
    if (status == 201) {
      // debugger
      Message.error("token失效，请重新登录");
      //清除token
      localStorage.removeItem("eleToken");
      //跳转到登录页面
      router.push("/login");
    }else {
        Message.error(error);
    }
    return Promise.reject(error);
  }
);
export default service;
