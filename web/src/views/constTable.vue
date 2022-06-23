<template>
  <div>
    <el-button type="primary" @click="showConstTableCreateDlg = true">创建</el-button>

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
<!--        <el-table-column-->
<!--            label="操作"-->
<!--            align="center">-->
<!--          <template #default="scope">-->
<!--            <el-button size="small" type="primary" @click="handleEdit(scope.$index, scope.row)">-->
<!--              导入数据-->
<!--            </el-button>-->
<!--          </template>-->
<!--        </el-table-column>-->
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

      <el-dialog v-model="showConstTableCreateDlg" title="创建常量表" width="500px">
        <el-form :model="constTableForm">
          <el-form-item label="数据库名称" :label-width="formLabelWidth" prop="dbSchema">
            <el-select v-model="constTableForm.tableSchema" class="m-2 inputWidth" filterable placeholder="Select">
              <el-option
                  v-for="item in schemaOption"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="表名" :label-width="formLabelWidth" prop="tableName">
            <el-input type="text" v-model="constTableForm.tableName" autocomplete="off" class="inputWidth"/>
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
            <el-button @click="showConstTableCreateDlg = false">取消</el-button>
            <el-button type="primary" @click="saveConstTable()">确定</el-button>
          </span>
        </template>
      </el-dialog>
  </div>
</template>

<script>
import constTableDataDlg from "@/views/constTableDataDlg";
import dbConstTable from "@/api/dbConstTable";

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

      showConstTableCreateDlg: false,
      showConstTableDataDlg: false,

      schemaOption : [
        {
          value: 'basedata',
          label: 'basedata',
        },
      ],


      constTableForm : {
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

    showConstTableData (row, event, column) {
      let table = {};
      table.tableName = row.tableName;
      table.schema = row.tableSchema;
      table.remark = row.remark;
      this.$refs.constTableDataDlg.showConstTableData(table);
      this.showConstTableDataDlg = true;
    },

    async getConstTable() {
      const { code, data, msg } = await dbConstTable.getConstTable({'currentPage': this.currentPage, 'size': this.pageSize});
      if ('200' == code) {
        this.tableData = data.records;
        this.tableTotal = data.total;
      }
    },

    async saveConstTable() {
      const { code, data, msg } = await dbConstTable.createConstTable(this.constTableForm);
      if ('200' == code) {
        this.$message.success("创建成功");
        await this.getConstTable();
        this.showConstTableCreateDlg = false;
      } else {
        this.$message.error(msg);
      }
    },

	
  },

  created() {
    this.getConstTable();
  }
}
</script>

<style scoped>
  .inputWidth{
    width: 200px;
  }
</style>