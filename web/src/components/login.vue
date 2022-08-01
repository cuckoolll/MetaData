<template>
  <div style="background: #EFEFEE; position: absolute; height: 100vh; width: 100%; " @keyup.enter.native="login('loginForm')">
    <div style="margin-top: 12%; margin-bottom: 20%; margin-left: 37%;
             position: inherit; width: 25%; height: 40%; border-radius:5px;background: white;
          ">
      <h3 style="text-align: center; margin-top: 10%">MetaData</h3>
      <el-form :model="loginForm" ref="loginForm" :rules="loginFormRule" style="margin-left: 15%">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" style="width: 80%; margin-top: 5%" type="text" placeholder="用户名"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" style="width: 80%; " type="text" show-password placeholder="密码"/>
        </el-form-item>
        <div class="form-action mb-3">
          <el-button type="primary" size="large" @click="login('loginForm')" style="margin-left: 27%; margin-top: 10%; width: 30%" round>登&nbsp;&nbsp;录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonApi from '@/api/common'

export default {
  data() {
    return {
      loginForm: {
        username: null,
        password: null
      },

      loginFormRule: {
        username: [{required: true,message: '请输入用户名',trigger: 'blur'}],
        password: [{required: true,message: '请输入密码',trigger: 'blur'}],
      },
    }
  },

  methods: {
    toHome() {
      this.$router.push({path: '/home'})
    },

    async login(loginForm) {
      this.$refs[loginForm].validate(async (valid) => {
        if (valid) {
          let formData = new FormData();
          formData.append("username", this.loginForm.username);
          formData.append("password", this.loginForm.password);
          formData.append("client_id", "client");
          formData.append("client_secret", "secret");
          formData.append("grant_type", "password");
          const {code, data, msg} = await commonApi.login(formData);
          if ('200' == code) {
            // user.saveUserLoginData(resp.data)
            // this.$store.commit('userUpdate', {
            //   nickname: data.nickname,
            //   username: data.username,
            //   email: data.email,
            // })
            localStorage.setItem("access_token", data.access_token);
            localStorage.setItem("refresh_token", data.refresh_token);

            let userInfo = {};
            userInfo.userId = data.userId;
            userInfo.userName = data.userName;
            userInfo.nickName = data.nickName;
            userInfo.email = data.email;
            localStorage.setItem("userInfo", JSON.stringify(userInfo));
            this.toHome();
          } else {
            if ('400' === code) {
              this.$message.error("用户名或密码错误");
            } else {
              this.$message.error("登录失败，请联系管理员");
            }
          }
        }
      })
    },
  },
}
</script>
<style scoped>

</style>