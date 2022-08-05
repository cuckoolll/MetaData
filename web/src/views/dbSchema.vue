<template>
  <div>
    <el-button v-if="roleContains('home_create')" id="home_create" type="primary" @click="showDbCreate()">创建</el-button>

    <div style="width: 100%;padding-top: 10px">
      <el-table :data="tableData"
                :height="tableHeight"
                @row-click="showDbTable">
        <el-table-column
            type="index"
            label="序号"
            align="center"
            :width="80">
        </el-table-column>
        <el-table-column label="数据库名称" align="center">
          <template #default="scope">
            <label>{{ scope.row.dbSchema }}</label>
          </template>
        </el-table-column>
        <el-table-column label="数据库类型" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.dbType == 'mysql'" type="success">{{ scope.row.dbType }}</el-tag>
            <el-tag v-else>{{ scope.row.dbType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="表数量" align="center">
          <template #default="scope">
            <label>{{ scope.row.dbCount }}</label>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center">
          <template #default="scope">
            <label>{{ scope.row.remark }}</label>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center">
          <template #default="scope">
            <label>{{ scope.row.createTime }}</label>
          </template>
        </el-table-column>
        <el-table-column label="更新时间" align="center">
          <template #default="scope">
            <label>{{ scope.row.updateTime }}</label>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" id="home_opt" v-if="roleContains('home_opt')">
          <template #default="scope">
            <el-button type="primary" icon="Edit" circle @click="showDbEdit(scope.row)"></el-button>
            <el-button type="danger" icon="Delete" circle @click="showDbDel(scope.row)"></el-button>
            <el-dropdown trigger="click" style="padding-left: 12px;">
              <el-button type="info" icon="MoreFilled" circle></el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item icon="Setting" @click="showSetDbConf(scope.row)">设置</el-dropdown-item>
                  <el-dropdown-item icon="Refresh" @click="syncMetaData(scope.row)">同步</el-dropdown-item>
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

    <el-dialog v-model="showDbEditDlg" :title="dbEditDlgTitle" width="500px">
      <el-form :model="dbForm"
               ref="dbFormRel"
               :rules="rules">
        <el-form-item label="数据库名称" :label-width="formLabelWidth" prop="dbSchema">
          <el-input type="text" v-model="dbForm.dbSchema" autocomplete="off" class="inputWidth"/>
        </el-form-item>
        <el-form-item label="数据库类型" :label-width="formLabelWidth" prop="dbType">
          <el-select v-model="dbForm.dbType" class="m-2 inputWidth" filterable placeholder="Select">
            <el-option
                v-for="item in dbTypeList"
                :key="item.id"
                :label="item.text"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth" prop="remark">
          <el-input type="text" v-model="dbForm.remark" autocomplete="off" class="inputWidth"/>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="showDbEditDlg = false">取消</el-button>
            <el-button type="primary" @click="saveDb('dbFormRel')">保存</el-button>
          </span>
      </template>
    </el-dialog>

    <dbConfDlg v-model="showSetDbConfDlg" ref="dbConfDlg" @on-close="showSetDbConfDlg=false"></dbConfDlg>

  </div>
</template>

<script>
import dbConfDlg from "@/views/dbConfDlg";
import dbConf from "@/api/dbConf";
import dbManager from "@/api/dbManager";
import * as arrays from "@/utils/arrays";

export default {
  components:{
    dbConfDlg
  },

  data() {
    return {
      roles: JSON.parse(localStorage.getItem("roles")),

      formLabelWidth : "140px",
      tableHeight: '',

      tableData: [],
      tableTotal: 0,
      currentPage: 1,
      pageSize: 20,

      showDbEditDlg: false,
      showSetDbConfDlg: false,
      showDeleteDlg: false,

      dbEditDlgTitle: '',

      dbForm: {
        projectId: '',
        dbSchema: '',
        dbType: '',
        remark: ''
      },

      rules: {
        dbSchema: [{ required: true, message: '请输入数据库名称', trigger: 'blur' }],
        dbType: [{ required: true, message: '请选择数据库类型', trigger: 'blur' }]
      },

      dbTypeList: [{id : 'mysql', text : 'mysql'}],
    }
  },

  methods: {
    handleSizeChange: function (size) {
      //一页显示多少条
      this.pageSize = size;
    },

    handleCurrentChange: function (currentPage) {
      //页码更改方法
      this.currentPage = currentPage;
    },

    showDbCreate() {
      if (this.$refs.dbFormRel) {
        this.$refs.dbFormRel.resetFields();
      }
      this.dbForm.projectId = '';
      this.dbForm.dbSchema = '';
      this.dbForm.dbType = '';
      this.dbForm.remark = '';
      this.dbEditDlgTitle = '创建数据库';
      this.showDbEditDlg = true;
    },

    showDbEdit(row) {
      if (this.$refs.dbFormRel) {
        this.$refs.dbFormRel.resetFields();
      }
      this.dbForm.projectId = row.projectId;
      this.dbForm.dbSchema = row.dbSchema;
      this.dbForm.dbType = row.dbType;
      this.dbForm.remark = row.remark;
      this.dbEditDlgTitle = '修改数据库';
      this.showDbEditDlg = true;
    },

    showDbDel(row) {
      this.$confirm("确定删除该数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            this.delDb(row.projectId);
          });
    },

    showSetDbConf(row) {
      let projectId = row.projectId;
      this.$refs.dbConfDlg.showSetDbConf(projectId);
      this.showSetDbConfDlg = true;
    },

    showDbTable(row, event, column) {
      if ('操作' != event.label) {
        this.$router.push({path:'/table', query: {dbSchema: row.dbSchema}});
      }
    },

    async getDb() {
      const { code, data, msg } = await dbManager.getDb({"currentPage":this.currentPage, "size":this.pageSize});
      if ('200' == code) {
        this.tableData = data.records;
        this.tableTotal = data.total;
      }
    },

    async saveDb(dbFormRel) {
      this.$refs[dbFormRel].validate(async (valid) => {
        if (valid) {
          const { code, data, msg } = await dbManager.saveDb(this.dbForm);
          if ('200' == code) {
            this.$message.success("保存成功");
            await this.getDb();
            this.showDbEditDlg = false;
          } else {
            this.$message.error(msg);
          }
        } else {
          return false;
        }
      });
    },

    async delDb(delId) {
      const { code, data, msg } = await dbManager.delDb(delId);
      if ('200' == code) {
        this.$message.success("删除成功");
        await this.getDb();
        this.showDeleteDlg = false;
      } else {
        this.$message.error(msg);
      }
    },

    async syncMetaData(row) {
      let res = await dbConf.getDbConfByPost({"projectId":row.projectId, "currentPage":1, "size":1});
      if ('200' == res.code && res.data != null && res.data.records.length > 0) {
        let dbConf = res.data.records[0];
        const { code, data, msg } = await dbManager.syncMetaData(dbConf);
        if ("200" == code) {
          await this.getDb();
          this.$message.success(msg);
        } else {
          this.$message.error(msg);
        }
      } else {
        this.$message.warning("未配置连接数据库");
      }
    },

    roleContains(roleId) {
      return arrays.contains(this.roles, roleId);
    },
  },
  
  created() {
    this.$store.state.menuActiveIndex = "/home";
    this.tableHeight = this.roleContains('home_create') ? '75vh' : '79vh';
    this.getDb();
  }
}
</script>

<style scoped>
  .inputWidth{
    width: 200px;
  }
</style>