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
    <el-button @click="exportData()" style="float: right;">导出</el-button>
    <el-button @click="showImport()" style="float: right;">导入</el-button>
    <el-button @click="showDelete()" style="float: right;">删除</el-button>
    <el-button @click="showEdit()" style="float: right;">新增/修改</el-button>
    <el-table
        :data="dataList"
        height="50vh"
        style="width: 100%; "
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
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
        limit=1
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

  <el-dialog v-model="showEditDlg" title="新增/修改" width="1000px">
    <el-button type="primary" @click="addConstItem()">新增</el-button>
    <el-table
        :data="constItemList"
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
        <template #default="scope">
          <el-input v-model="scope.row[col.prop]" type="text" :disabled="col.isPk == 1"></el-input>
        </template>
      </el-table-column>
      <el-table-column
          type="index"
          label="操作"
          align="center"
          :width="80">
        <template #default="scope">
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="deleteRow(scope.$index)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <template #footer>
        <span class="dialog-footer">
          <el-button @click="showEditDlg = false">取消</el-button>
          <el-button type="primary" @click="saveConstData()">保存</el-button>
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
      showEditDlg: false,

      title : '',
      tableName: '',
      dataList: [],
      columns: '',

      constItemList: [],

      table:{},

      tableTotal: 0,
      currentPage: 1,
      pageSize: 20,

      fileList : [],
      file:'',

      columnName: '',
      columnValue: '',

      columnNameSelect: [],
      isExport: false,

      selection:[],
    }
  },

  methods: {
    handleSelectionChange(val) {
      this.selection = val
    },

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
      this.columnName = '';
      this.columnValue = '';
      this.selection = [];

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

    showImport() {
      this.file = '';
      this.fileList = [];
      this.showImportDataDlg = true;
    },

    showEdit() {
      this.constItemList = [];
      this.constItemList = this.selection;
      this.showEditDlg = true;
    },

    showDelete() {
      if (this.selection.length == 0) {
        this.$message.warning("请选择至少一条数据");
        return;
      }
      this.$confirm("确定删除该数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            this.deleteConstData();
          });
    },

    addConstItem() {
      let item = {};
      for (let i = 0; i < this.columns.length; i++) {
        item[this.columns[i]] = '';
      }
      this.constItemList.push(item);
    },

    deleteRow(index) {
      this.constItemList.splice(index, 1);
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
      if (this.fileList.length == 0) {
        this.$message.warning("请选择导入文件");
        return;
      }
      let formData = new FormData();
      formData.append("file", this.file.raw);
      formData.append("tableName", this.tableName);
      const { code, data, msg } = await dbConstTable.importData(formData);
      if ('200' == code) {
        this.$message.success("导入成功");
        await this.getData();
        this.showImportDataDlg = false;
      } else {
        this.$message.error(msg);
      }
    },

    async exportData() {
      if (this.selection.length > 20) {
        this.$message.warning("选中导出最多支持20条数据");
        return;
      }

      if (this.isExport) {
        return;
      }
      this.isExport = true;

      let param = {};
      param.tableName = this.table.tableName;
      param.schema = this.table.schema;
      param.columnName = this.columnName;
      param.columnValue = this.columnValue;
      param.dataList = this.selection;
      const { code, data, msg } = await dbConstTable.exportDataWithSql(param);
      if ('200' != code) {
        this.$message.error(msg);
        return;
      }
      let blob = new Blob([data], { type: "application/octet-stream" });
      let downloadElement = document.createElement("a");

      let href = window.URL.createObjectURL(blob); //创建下载的链接
      downloadElement.href = href;
      downloadElement.download = this.tableName + `数据.sql`; //下载后文件名

      document.body.appendChild(downloadElement);
      downloadElement.click(); //点击下载
      document.body.removeChild(downloadElement); //下载完成移除元素
      window.URL.revokeObjectURL(href); //释放掉blob对象
      this.$message.success(`导出成功`);
      this.isExport = false;
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

    async saveConstData() {
      let param = {};
      param.tableName = this.table.tableName;
      param.schema = this.table.schema;
      param.constDataList = this.constItemList;
      const { code, data, msg } = await dbConstTable.saveConstData(param);
      if ('200' == code) {
        this.$message.success("保存成功");
        this.selection = [];
        this.showEditDlg = false;
        await this.getData();
      } else {
        this.$message.error(msg);
      }
    },

    async deleteConstData() {
      let param = {};
      param.tableName = this.table.tableName;
      param.schema = this.table.schema;
      param.constDataList = this.selection;
      const { code, data, msg } = await dbConstTable.deleteConstData(param);
      if ('200' == code) {
        this.$message.success("删除成功");
        this.selection = [];
        await this.getData();
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