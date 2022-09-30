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
        <el-table-column label="功能id" align="center">
          <template #default="scope">
            <label>{{ scope.row.itemId }}</label>
          </template>
        </el-table-column>
        <el-table-column label="功能名称" align="center">
          <template #default="scope">
            <label>{{ scope.row.itemName }}</label>
          </template>
        </el-table-column>
        <el-table-column label="菜单uri" align="center">
          <template #default="scope">
            <label>{{ scope.row.menuUri }}</label>
          </template>
        </el-table-column>
        <el-table-column label="说明" align="center">
          <template #default="scope">
            <label>{{ scope.row.description }}</label>
          </template>
        </el-table-column>
<!--        <el-table-column label="状态" align="center">-->
<!--          <template #default="scope">-->
<!--            <el-switch v-model="scope.row.datastatusid"-->
<!--                       active-text="启用"-->
<!--                       inactive-text="停用"-->
<!--                       active-value="1"-->
<!--                       inactive-value="0"-->
<!--                       @change="statusChange(scope.row)"/>-->
<!--          </template>-->
<!--        </el-table-column>-->
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
        <el-form-item label="功能名称" :label-width="formLabelWidth" prop="itemName">
          <el-input type="text" v-model="form.itemName" autocomplete="off" class="inputWidth"/>
        </el-form-item>
        <el-form-item label="菜单uri" :label-width="formLabelWidth" prop="menuUri">
          <el-input type="text" v-model="form.menuUri" autocomplete="off" class="inputWidth"/>
        </el-form-item>
        <el-form-item label="说明" :label-width="formLabelWidth" prop="description">
          <el-input type="text" v-model="form.description" autocomplete="off" class="inputWidth"/>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="showEditDlg = false">取消</el-button>
            <el-button type="primary" @click="saveFunc('form')">保存</el-button>
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
        itemName: [{ required: true, message: '请输入功能名称', trigger: 'blur' }],
        menuUri: [{ required: true, message: '请输入菜单uri', trigger: 'blur' }]
      },

      roleList: [],
    }
  },

  methods: {
    handleSizeChange: function (size) {
      //一页显示多少条
      this.pageSize = size;
      this.getFuncs();
    },

    handleCurrentChange: function (currentPage) {
      //页码更改方法
      this.currentPage = currentPage;
      this.getFuncs();
    },

    async statusChange(row) {
      const { code, data, msg } = await common.updateFuncStatus({"itemId":row.itemId, "status":row.datastatusid});
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
      this.editDlgTitle = '创建功能';
      this.showEditDlg = true;
    },

    showEdit(row) {
      if (this.$refs.form) {
        this.$refs.form.resetFields();
      }
      this.form = row;
      this.editDlgTitle = '编辑功能';
      this.showEditDlg = true;
    },

    showDel(row) {
      this.$confirm("确定删除该数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            this.delFunc(row.itemId);
          });
    },

    async getFuncs() {
      const { code, data, msg } = await common.getFuncs({"currentPage":this.currentPage, "size":this.pageSize});
      if ('200' == code) {
        this.tableData = data.records;
        this.tableTotal = data.total;
      }
    },

    async saveFunc(form) {
      this.$refs[form].validate(async (valid) => {
        if (valid) {
          const { code, data, msg } = await common.saveFunc(this.form);
          if ('200' == code) {
            this.$message.success("保存成功");
            await this.getFuncs();
            this.showEditDlg = false;
          } else {
            this.$message.error(msg);
          }
        } else {
          return false;
        }
      });
    },

    async delFunc(itemId) {
      const { code, data, msg } = await common.delFunc({'itemId': itemId});
      if ('200' == code) {
        this.$message.success("删除成功");
        await this.getFuncs();
        this.showDeleteDlg = false;
      } else {
        this.$message.error(msg);
      }
    },
  },
  
  created() {
    this.$store.state.menuActiveIndex = "/funcManage";
    this.getFuncs();
  }
}
</script>

<style scoped>
  .inputWidth{
    width: 200px;
  }
</style>