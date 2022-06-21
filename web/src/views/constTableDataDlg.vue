<template>
  <el-dialog v-model="showConstTableDataDlg" :title="title" width="1000px">
    <el-button type="primary" @click="showImportDataDlg = true">导入数据</el-button>
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

      fileList : [],
      file:'',
    }
  },

  methods: {
    showConstTableData(table) {
      this.tableName = table.tableName;
      this.title = table.tableName;
      if (table.remark) {
        this.title += "（" + table.remark + "）";
      }
      this.getGridColumn(table.tableName, table.schema);
      this.showConstTableDataDlg = true;
    },

    handleChange(file, fileList) {
      this.file = file;
      this.fileList = fileList.slice(-3);
    },

    async getGridColumn(tableName, schema) {
      const { code, data, msg } = await dbConstTable.getGridColumn({'tableName': tableName, 'schema': schema});
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
        this.showImportDataDlg = false;
      } else {
        this.$message.error(msg);
      }
    },

    async getData(tableName, schema) {
      const { code, data, msg } = await dbConstTable.getData(tableName, schema);
      if ('200' == code) {
        this.dataList = data.records;
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
    this.showTableDetail();
  }
}
</script>

<style scoped>
.inputWidth{
  width: 200px;
}
</style>