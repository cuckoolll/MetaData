<template>
  <div>
    <el-button type="primary" @click="showUserCreate()">创建</el-button>

    <div style="width: 100%;padding-top: 10px">
      <el-table :data="tableData"
                height="75vh">
        <el-table-column
            type="index"
            label="序号"
            align="center"
            :width="80">
        </el-table-column>
        <el-table-column label="用户id" align="center">
          <template #default="scope">
            <label>{{ scope.row.userId }}</label>
          </template>
        </el-table-column>
        <el-table-column label="用户名" align="center">
          <template #default="scope">
            <label>{{ scope.row.username }}</label>
          </template>
        </el-table-column>
        <el-table-column label="昵称" align="center">
          <template #default="scope">
            <label>{{ scope.row.nickName }}</label>
          </template>
        </el-table-column>
        <el-table-column label="电话" align="center">
          <template #default="scope">
            <label>{{ scope.row.phone }}</label>
          </template>
        </el-table-column>
        <el-table-column label="邮箱" align="center">
          <template #default="scope">
            <label>{{ scope.row.email }}</label>
          </template>
        </el-table-column>
        <el-table-column label="角色" align="center">
          <template #default="scope">
            <label>{{ scope.row.roleName }}</label>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center">
          <template #default="scope">
            <el-switch v-model="scope.row.datastatusid"
                       active-text="启用"
                       inactive-text="停用"
                       active-value="1"
                       inactive-value="0"
                       @change="statusChange(scope.row)"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button type="primary" icon="Edit" circle @click="showUserEdit(scope.row)"></el-button>
            <el-button type="danger" icon="Delete" circle @click="showUserDel(scope.row)"></el-button>
            <el-dropdown trigger="click" style="padding-left: 12px;">
              <el-button type="info" icon="MoreFilled" circle></el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item icon="RefreshLeft" @click="resetPassword(scope.row)">重置密码</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          style="padding-top: 10px; float: right;"
          :total="tableTotal"
          :page-size="pageSize"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          layout="total,sizes,prev,pager,next,jumper">
      </el-pagination>
    </div>

    <el-dialog v-model="showUserEditDlg" :title="userEditDlgTitle" width="500px">
      <el-form :model="userForm"
               ref="userForm"
               :rules="rules">
        <el-form-item label="用户名" :label-width="formLabelWidth" prop="username">
          <el-input v-if="'编辑用户' == userEditDlgTitle" disabled="true" type="text" v-model="userForm.username" autocomplete="off" class="inputWidth"/>
          <el-input v-else type="text" v-model="userForm.username" autocomplete="off" class="inputWidth"/>
        </el-form-item>
        <el-form-item label="昵称" :label-width="formLabelWidth" prop="nickName">
          <el-input type="text" v-model="userForm.nickName" autocomplete="off" class="inputWidth"/>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth" prop="phone">
          <el-input type="text" v-model="userForm.phone" autocomplete="off" class="inputWidth"/>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth" prop="email">
          <el-input type="text" v-model="userForm.email" autocomplete="off" class="inputWidth"/>
        </el-form-item>
        <el-form-item label="角色" :label-width="formLabelWidth" prop="roleId">
          <el-select v-model="userForm.roleId" class="m-2 inputWidth" filterable placeholder="请选择角色">
            <el-option
                v-for="item in roleList"
                :key="item.id"
                :label="item.text"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="showUserEditDlg = false">取消</el-button>
            <el-button type="primary" @click="saveUser('userForm')">保存</el-button>
          </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import common from "@/api/common";
import dbManager from "@/api/dbManager";

export default {
  components:{
  },

  data() {
    return {
      formLabelWidth : "140px",

      tableData: [],
      tableTotal: 0,
      currentPage: 1,
      pageSize: 20,

      showUserEditDlg: false,
      showDeleteDlg: false,

      userEditDlgTitle: '',

      userForm: {
      },

      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        nickName: [{ required: true, message: '请输入昵称', trigger: 'blur' }]
      },

      roleList: [],
    }
  },

  methods: {
    handleSizeChange: function (size) {
      //一页显示多少条
      this.pageSize = size;
      this.getUsers();
    },

    handleCurrentChange: function (currentPage) {
      //页码更改方法
      this.currentPage = currentPage;
      this.getUsers();
    },

    async statusChange(row) {
      const { code, data, msg } = await common.updateUserStatus({"userId":row.userId, "status":row.datastatusid});
      if ('200' == code) {
        this.$message.success("修改成功");
      } else {
        this.$message.error(msg);
      }
    },

    showUserCreate() {
      if (this.$refs.userForm) {
        this.$refs.userForm.resetFields();
      }
      this.userForm = {};
      this.userEditDlgTitle = '创建用户';
      this.showUserEditDlg = true;
    },

    showUserEdit(row) {
      if (this.$refs.userForm) {
        this.$refs.userForm.resetFields();
      }
      this.userForm = row;
      this.userEditDlgTitle = '编辑用户';
      this.showUserEditDlg = true;
    },

    showUserDel(row) {
      this.$confirm("确定删除该数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            this.delUser(row.userId);
          });
    },

    async getUsers() {
      const { code, data, msg } = await common.getUsers({"currentPage":this.currentPage, "size":this.pageSize});
      if ('200' == code) {
        this.tableData = data.records;
        this.tableTotal = data.total;
      }
    },

    async saveUser(userForm) {
      this.$refs[userForm].validate(async (valid) => {
        if (valid) {
          const { code, data, msg } = await common.saveUser(this.userForm);
          if ('200' == code) {
            this.$message.success("保存成功");
            await this.getUsers();
            this.showUserEditDlg = false;
          } else {
            this.$message.error(msg);
          }
        } else {
          return false;
        }
      });
    },

    async delUser(delId) {
      const { code, data, msg } = await common.delUser({'userId': delId});
      if ('200' == code) {
        this.$message.success("删除成功");
        await this.getUsers();
        this.showDeleteDlg = false;
      } else {
        this.$message.error(msg);
      }
    },

    async getRoleSelect() {
      const { code, data, msg } = await common.getRoleSelect();
      if ('200' == code) {
        this.roleList = data;
      }
    },

    async resetPassword(row) {
      const { code, data, msg } = await common.resetPassword({'userId': row.userId});
      if ('200' == code) {
        this.$message.success("重置成功");
      } else {
        this.$message.error(msg);
      }
    }
  },
  
  created() {
    this.$store.state.menuActiveIndex = "/userManage";
    this.getRoleSelect();
    this.getUsers();
  }
}
</script>

<style scoped>
  .inputWidth{
    width: 200px;
  }
</style>