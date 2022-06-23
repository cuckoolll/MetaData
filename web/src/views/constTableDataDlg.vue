<template>
  <el-dialog v-model="showConstTableDataDlg" :title="title" width="1200px">
    <span>字段:</span>
    <el-select v-model="columnName" clearable style="margin-left: 10px; width: 150px;">
      <el-option
          v-for="item in columnNameSelect"
          :key="item.id"
          :label="item.text"
          :value="item.id"
      >
      </el-option>
    </el-select>
    <span style="margin-left: 10px;">值:</span>
    <el-input v-model="columnValue" type="text" style="margin-left: 10px; width: 150px;"></el-input>
    <el-button type="primary" @click="getData()" style="margin-left: 10px;">查询</el-button>
    <el-button @click="showImportDataDlg = true" style="float: right;">导入数据</el-button>
    <el-table
        :data="dataList"
        height="50vh"
        style="width: 100%; ">
      <el-table-column
          type="index"
          label="序号"
          align="center"
          :width="80">
      </el-table-column>
      <el-table-column
          v-for="col in columns"
          :prop="col.prop"
          :label="col.label"
          align="center">
      </el-table-column>
    </el-table>

    <el-pagination
        style="padding-top: 10px;"
        :total="tableTotal"
        :page-size="pageSize"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        layout="->,total,sizes,prev,pager,next,jumper">
    </el-pagination>
  </el-dialog>

  <el-dialog v-model="showImportDataDlg" title="导入数据" width="400px">
    <el-upload
        class="upload-demo"
        action="https://jsonplaceholder.typicode.com/posts/"
        :on-change="handleChange"
        :file-list="fileList"
        accept=".sql,.SQL"
    >
      <el-button type="primary">点击上传文件</el-button>
    </el-upload>
    <template #footer>
          <span class="dialog-footer">
            <el-button @click="showImportDataDlg = false">取消</el-button>
            <el-button type="primary" @click="importData()">确定</el-button>
          </span>
    </template>
  </el-dialog>
</template>

<script>
import dbConstTable from "@/api/dbConstTable";

export default {
  name: "constTableDataDlg",
  data() {
    return {
      showConstTableDataDlg : false,
      showImportDataDlg: false,

      title : '',
      tableName: '',
      dataList: [],
      columns: '',

      table:{},

      tableTotal: 0,
      currentPage: 1,
      pageSize: 20,

      fileList : [],
      file:'',

      columnName: '',
      columnValue: '',

      columnNameSelect: [],
    }
  },

  methods: {
    handleSizeChange: function (size) {
      //一页显示多少条
      this.pageSize = size;
      this.getData();
    },

    handleCurrentChange: function (currentPage) {
      //页码更改方法
      this.currentPage = currentPage;
      this.getData();
    },

    showConstTableData(table) {
      this.table = table;
      this.tableName = table.tableName;
      this.title = table.tableName;
      if (table.remark) {
        this.title += "（" + table.remark + "）";
      }
      this.getColumnQuerySelectWithoutCommon();
      this.getGridColumnWithoutCommon(table.tableName, table.schema);
      this.getData();
      this.showConstTableDataDlg = true;
    },

    handleChange(file, fileList) {
      this.file = file;
      this.fileList = fileList.slice(-3);
    },

    async getGridColumnWithoutCommon(tableName, schema) {
      const { code, data, msg } = await dbConstTable.getGridColumnWithoutCommon({'tableName': tableName, 'schema': schema});
      if ('200' == code) {
        this.columns = data;
      } else {
        this.$message.error(msg);
      }
    },

    async importData() {
      let formData = new FormData();
      formData.append("file", this.file.raw);
      formData.append("tableName", this.tableName);
      const { code, data, msg } = await dbConstTable.importData(formData);
      if ('200' == code) {
        this.$message.success("导入成功");
        this.getData();
        this.showImportDataDlg = false;
      } else {
        this.$message.error(msg);
      }
    },

    async getData() {
      let param = {};
      param.tableName = this.table.tableName;
      param.schema = this.table.schema;
      param.columnName = this.columnName;
      param.columnValue = this.columnValue;
      param.currentPage = this.currentPage;
      param.size = this.pageSize;
      const { code, data, msg } = await dbConstTable.getData(param);
      if ('200' == code) {
        this.dataList = data.records;
        this.tableTotal = data.total;
      } else {
        this.$message.error(msg);
      }
    },

    async getColumnQuerySelectWithoutCommon() {
      let param = {};
      param.tableName = this.table.tableName;
      param.schema = this.table.schema;
      const { code, data, msg } = await dbConstTable.getColumnQuerySelectWithoutCommon(param);
      if ('200' == code) {
        this.columnNameSelect = data;
      } else {
        this.$message.error(msg);
      }
    },

    closeDlg() {
      this.showConstTableDataDlg = false;
      this.$emit("on-close");
    }
  },

  create() {
  }
}
</script>

<style scoped>
.inputWidth{
  width: 200px;
}
</style>