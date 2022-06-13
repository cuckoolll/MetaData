<template>
    <el-dialog v-model="showSetDbConfDlg" title="数据库设置" width="500px">
      <el-form :model="dbForm">
        <el-form-item label="用户名" :label-width="formLabelWidth" prop="dbUsername">
          <el-input v-model="dbForm.dbUsername" type="text" autocomplete="off" class="inputWidth"/>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth" prop="dbPassword">
          <el-input type="password" v-model="dbForm.dbPassword" show-password autocomplete="off" class="inputWidth"/>
        </el-form-item>
        <el-form-item label="数据库类型" :label-width="formLabelWidth">
          <el-select v-model="dbForm.dbType" placeholder="请选择数据库类型" class="inputWidth">
            <el-option label="mysql" value="mysql" />
          </el-select>
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input type="text" v-model="dbForm.dbUrl" autocomplete="off" class="inputWidth"/>
        </el-form-item>
        <el-form-item label="数据库名称" :label-width="formLabelWidth">
          <el-input type="text" v-model="dbForm.dbName" autocomplete="off" class="inputWidth"/>
        </el-form-item>
        <el-form-item label="Schema 名称" :label-width="formLabelWidth">
          <el-input type="text" v-model="dbForm.dbSchema" autocomplete="off" class="inputWidth"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="testConnection()" style="float: left">测试连接</el-button>
          <el-button @click="closeDlg()">取消</el-button>
          <el-button type="primary" @click="saveDbConf()">保存</el-button>
        </span>
      </template>
    </el-dialog>
</template>

<script>
import dbConf from "@/api/dbConf";
import dbManager from "@/api/dbManager";

export default {
  name: "dbConfDlg",
  data() {
    return {
      formLabelWidth : "140px",
      showSetDbConfDlg : false,
      dbForm : {
        dbId: '',
        dbUsername: '',
        dbPassword: '',
        dbType: '',
        dbUrl: '',
        dbName: '',
        dbSchema: '',
      },
    }
  },

  methods: {
    async showSetDbConf() {
      const { code, data, msg } = await dbConf.getDbConfByPost({"currentPage":1, "size":1});
      if ('200' == code && data != null && data.records.length > 0) {
        this.dbForm = data.records[0];
      }
      this.showSetDbConfDlg = true;
    },

    async saveDbConf() {
      const { code, data, msg } = await dbConf.saveDbConf(this.dbForm);
      if ('200' == code) {
        this.$message.info("保存成功");
        let res = await dbConf.getDbConfList();
        if (res != null) {
          this.dbForm = res;
        }
      } else {
        this.$message.info(msg);
      }
    },

    async testConnection() {
      const { code, data, msg } = await dbManager.testConnection(this.dbForm);
      if ("200" == code) {
        this.$message.success(msg);
      } else {
        this.$message.error(msg);
      }
    },

    closeDlg() {
      this.showSetDbConfDlg = false;
      this.$emit("on-close");
    }
  },

  create() {
    this.showSetDbConf();
  }
}
</script>

<style scoped>
  .inputWidth{
    width: 200px;
  }
</style>