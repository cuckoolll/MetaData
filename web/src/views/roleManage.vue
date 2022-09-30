<template>
  <div>
    <el-button type="primary" @click="showCreate()">创建</el-button>

    <div style="width: 100%;padding-top: 10px">
      <el-table :data="tableData"
                height="75vh">
        <el-table-column
            type="index"
            label="序号"
            align="center"
            :width="80">
        </el-table-column>
        <el-table-column label="角色id" align="center">
          <template #default="scope">
            <label>{{ scope.row.roleId }}</label>
          </template>
        </el-table-column>
        <el-table-column label="角色名称" align="center">
          <template #default="scope">
            <label>{{ scope.row.roleName }}</label>
          </template>
        </el-table-column>
        <el-table-column label="说明" align="center">
          <template #default="scope">
            <label>{{ scope.row.description }}</label>
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
            <el-button type="primary" icon="Edit" circle @click="showEdit(scope.row)"></el-button>
            <el-button type="danger" icon="Delete" circle @click="showDel(scope.row)"></el-button>
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

    <el-dialog v-model="showEditDlg" :title="editDlgTitle" width="500px">
      <el-form :model="form"
               ref="form"
               :rules="rules">
        <el-form-item label="角色名称" :label-width="formLabelWidth" prop="roleName">
          <el-input type="text" v-model="form.roleName" autocomplete="off" class="inputWidth"/>
        </el-form-item>
        <el-form-item label="说明" :label-width="formLabelWidth" prop="description">
          <el-input type="text" v-model="form.description" autocomplete="off" class="inputWidth"/>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="showEditDlg = false">取消</el-button>
            <el-button type="primary" @click="saveRole('form')">保存</el-button>
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

      showEditDlg: false,
      showDeleteDlg: false,

      editDlgTitle: '',

      form: {
      },

      rules: {
        roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' }]
      },

      roleList: [],
    }
  },

  methods: {
    handleSizeChange: function (size) {
      //一页显示多少条
      this.pageSize = size;
      this.getRoles();
    },

    handleCurrentChange: function (currentPage) {
      //页码更改方法
      this.currentPage = currentPage;
      this.getRoles();
    },

    async statusChange(row) {
      const { code, data, msg } = await common.updateRoleStatus({"roleId":row.roleId, "status":row.datastatusid});
      if ('200' == code) {
        this.$message.success("修改成功");
      } else {
        this.$message.error(msg);
      }
    },

    showCreate() {
      if (this.$refs.form) {
        this.$refs.form.resetFields();
      }
      this.form = {};
      this.editDlgTitle = '创建角色';
      this.showEditDlg = true;
    },

    showEdit(row) {
      if (this.$refs.form) {
        this.$refs.form.resetFields();
      }
      this.form = row;
      this.editDlgTitle = '编辑角色';
      this.showEditDlg = true;
    },

    showDel(row) {
      this.$confirm("确定删除该数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            this.delRole(row.roleId);
          });
    },

    async getRoles() {
      const { code, data, msg } = await common.getRoles({"currentPage":this.currentPage, "size":this.pageSize});
      if ('200' == code) {
        this.tableData = data.records;
        this.tableTotal = data.total;
      }
    },

    async saveRole(form) {
      this.$refs[form].validate(async (valid) => {
        if (valid) {
          const { code, data, msg } = await common.saveRole(this.form);
          if ('200' == code) {
            this.$message.success("保存成功");
            await this.getRoles();
            this.showEditDlg = false;
          } else {
            this.$message.error(msg);
          }
        } else {
          return false;
        }
      });
    },

    async delRole(roleId) {
      const { code, data, msg } = await common.delRole({'roleId': roleId});
      if ('200' == code) {
        this.$message.success("删除成功");
        await this.getRoles();
        this.showDeleteDlg = false;
      } else {
        this.$message.error(msg);
      }
    },
  },
  
  created() {
    this.$store.state.menuActiveIndex = "/roleManage";
    this.getRoles();
  }
}
</script>

<style scoped>
  .inputWidth{
    width: 200px;
  }
</style>