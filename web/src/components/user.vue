<template>
  <div style="float: right; line-height: 8vh; height: 8vh">
    <span style="margin-right: 5px; font-size: 14px;">{{nickName}}</span>
    <el-button key="primary" type="primary" link @click="logout">退出</el-button>
  </div>
</template>

<script>
import commonApi from "@/api/common";

export default {
  name: "user",

  data() {
    return {
      nickName : '',
    }
  },

  methods: {
    tologin() {
      this.$router.push({path: '/login'})
    },

    async logout() {
      let formData = new FormData();
      formData.append("token", localStorage.getItem("access_token"));
      const {code, data, msg} = await commonApi.logout(formData);
      if ('200' == code) {
        localStorage.setItem("access_token", '');
        localStorage.setItem("refresh_token", '');
        this.tologin();
      }
    },
  },

  created() {
    let userInfo = localStorage.getItem("userInfo");
    if (userInfo) {
      this.nickName = JSON.parse(userInfo).nickName;
    }
  }
}
</script>

<style scoped>

</style>