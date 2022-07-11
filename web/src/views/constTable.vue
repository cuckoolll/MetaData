<template>
  <div>
    <el-button type="primary" @click="showCreate">创建</el-button>

    <div style="padding-top:10px;">
      <el-table
          :data="tableData"
          height="75vh"
          @row-click="showConstTableData"
          style="width: 100%; ">
        <el-table-column
            type="index"
            label="序号"
            align="center"
            :width="80">
        </el-table-column>
        <el-table-column
            prop="tableSchema"
            label="数据库名称"
            align="center">
        </el-table-column>
        <el-table-column
            prop="tableName"
            label="表名"
            align="center">
        </el-table-column>
        <el-table-column
            prop="remark"
            label="备注"
            align="center">
        </el-table-column>
        <el-table-column
            prop="updateTime"
            label="修改时间"
            align="center">
        </el-table-column>
        <el-table-column
            prop="updater"
            label="修改人"
            align="center">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="创建时间"
            align="center">
        </el-table-column>
        <el-table-column
            prop="creator"
            label="创建人"
            align="center">
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button type="primary" icon="Edit" circle @click="showEdit(scope.row)"></el-button>
            <el-button type="danger" icon="Delete" circle @click="showDel(scope.row)"></el-button>
<!--            <el-dropdown trigger="click" style="padding-left: 12px;">-->
<!--              <el-button type="info" icon="MoreFilled" circle></el-button>-->
<!--              <template #dropdown>-->
<!--                <el-dropdown-menu>-->
<!--                  <el-dropdown-item icon="Setting" @click="showSetDbConf(scope.row)">设置</el-dropdown-item>-->
<!--                  <el-dropdown-item icon="Refresh" @click="syncMetaData(scope.row)">同步</el-dropdown-item>-->
<!--                </el-dropdown-menu>-->
<!--              </template>-->
<!--            </el-dropdown>-->
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

      <constTableDataDlg v-model="showConstTableDataDlg" ref="constTableDataDlg" @on-close="showConstTableDataDlg=false"></constTableDataDlg>

      <el-dialog v-model="showConstTableEditDlg" :title="editDlgTitle" width="500px">
        <el-form :model="constTableForm">
          <el-form-item label="数据库名称" :label-width="formLabelWidth" prop="dbSchema">
            <el-select v-model="constTableForm.tableSchema" class="m-2 inputWidth" filterable placeholder="Select" :disabled="isUpdate">
              <el-option
                  v-for="item in schemaOption"
                  :key="item.id"
                  :label="item.text"
                  :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="表名" :label-width="formLabelWidth" prop="tableName">
            <el-input type="text" v-model="constTableForm.tableName" autocomplete="off" class="inputWidth" :disabled="isUpdate"/>
          </el-form-item>
          <el-form-item label="备注" :label-width="formLabelWidth" prop="remark">
            <el-input type="text" v-model="constTableForm.remark" autocomplete="off" class="inputWidth"/>
          </el-form-item>
<!--          <el-form-item label="sql文件" :label-width="formLabelWidth" prop="remark">-->
<!--            <el-upload-->
<!--                class="upload-demo"-->
<!--                action="https://jsonplaceholder.typicode.com/posts/"-->
<!--                :on-change="handleChange"-->
<!--                :file-list="fileList"-->
<!--                accept=".sql,.SQL"-->
<!--            >-->
<!--              <el-button type="primary">点击上传文件</el-button>-->
<!--            </el-upload>-->
<!--          </el-form-item>-->
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="showConstTableEditDlg = false">取消</el-button>
            <el-button type="primary" @click="saveConstTable()">保存</el-button>
          </span>
        </template>
      </el-dialog>
  </div>
</template>

<script>
import constTableDataDlg from "@/views/constTableDataDlg";
import dbConstTable from "@/api/dbConstTable";
import dbConf from "@/api/dbConf";
import dbManager from "@/api/dbManager";

export default {
  components:{
    constTableDataDlg
  },

  data() {
    return {
      formLabelWidth : "140px",

      tableData:[],
      tableTotal: 0,
      currentPage: 1,
      pageSize: 20,

      showConstTableEditDlg: false,
      showConstTableDataDlg: false,

      isUpdate: false,
      editDlgTitle: '',

      schemaOption : [],

      constTableForm : {
        tableId: '',
        tableSchema: '',
        tableName: '',
        remark: '',
      },

      fileList : [],
      file:'',
    }
  },

  methods: {
    handleSizeChange: function (size) {
      //一页显示多少条
      this.pageSize = size;
      this.getConstTable();
    },

    handleCurrentChange: function (currentPage) {
      //页码更改方法
      this.currentPage = currentPage;
      this.getConstTable();
    },

    handleChange(file, fileList) {
      this.file = file;
      this.fileList = fileList.slice(-3);
    },

    showCreate() {
      // if (this.$refs.dbFormRel) {
      //   this.$refs.dbFormRel.resetFields();
      // }
      this.constTableForm.tableSchema = '';
      this.constTableForm.tableName = '';
      this.constTableForm.remark = '';
      this.constTableForm.tableId = '';
      this.dbEditDlgTitle = '创建';
      this.isUpdate = false;
      this.showConstTableEditDlg = true;
    },

    showEdit(row) {
      // if (this.$refs.dbFormRel) {
      //   this.$refs.dbFormRel.resetFields();
      // }
      this.constTableForm.tableSchema = row.tableSchema;
      this.constTableForm.tableName = row.tableName;
      this.constTableForm.remark = row.remark;
      this.constTableForm.tableId = row.tableId;
      this.editDlgTitle = '修改';
      this.isUpdate = true;
      this.showConstTableEditDlg = true;
    },

    showDel(row) {
      this.$confirm("确定删除该数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(async () => {
            const { code, data, msg } = await dbConstTable.deleteConstTable(row.tableId);
            if ('200' == code) {
              this.$message.success("删除成功");
              await this.getConstTable();
              this.showDeleteDlg = false;
            } else {
              this.$message.error(msg);
            }
          });
    },

    showConstTableData (row, event, column) {
      if ('操作' != event.label) {
        let table = {};
        table.tableName = row.tableName;
        table.schema = row.tableSchema;
        table.remark = row.remark;
        this.$refs.constTableDataDlg.showConstTableData(table);
        this.showConstTableDataDlg = true;
      }
    },

    async getConstTable() {
      const { code, data, msg } = await dbConstTable.getConstTable({'currentPage': this.currentPage, 'size': this.pageSize});
      if ('200' == code) {
        this.tableData = data.records;
        this.tableTotal = data.total;
      }
    },

    async saveConstTable() {
      const { code, data, msg } = await dbConstTable.saveConstTable(this.constTableForm);
      if ('200' == code) {
        this.$message.success("保存成功");
        await this.getConstTable();
        this.showConstTableEditDlg = false;
      } else {
        this.$message.error(msg);
      }
    },

    async getSchemaSelect() {
      const { code, data, msg } = await dbConf.getSchemaSelect();
      if ('200' == code) {
        this.schemaOption = data;
      }
    },

  },

  created() {
    this.getConstTable();
    this.getSchemaSelect();
  }
}
</script>

<style scoped>
  .inputWidth{
    width: 200px;
  }
</style>