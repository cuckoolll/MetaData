<template>
  <el-dialog v-model="showTableDetailDlg" :title="title" width="1200px">
    <el-tabs v-model="tabsActiveName" class="demo-tabs" @tab-click="handleClick" type="border-card">
      <el-tab-pane label="字段" name="first">
        <el-table
            :data="columnsData"
            height="50vh"
            style="width: 100%; ">
          <el-table-column
              type="index"
              label="序号"
              align="center"
              :width="80">
          </el-table-column>
          <el-table-column
              prop="columnName"
              label="字段名称"
              align="center">
          </el-table-column>
          <el-table-column
              prop="dataType"
              label="数据类型"
              align="center">
          </el-table-column>
          <el-table-column
              prop="columnDefault"
              label="默认值"
              align="center">
          </el-table-column>
          <el-table-column
              prop="isPrimaryStr"
              label="是否主键"
              align="center">
          </el-table-column>
          <el-table-column
              prop="isNullableStr"
              label="是否为空"
              align="center">
          </el-table-column>
          <el-table-column
              prop="columnSize"
              label="字段长度"
              align="center">
          </el-table-column>
          <el-table-column
              prop="numberScale"
              label="字段精度"
              align="center">
          </el-table-column>
          <el-table-column
              prop="extra"
              label="扩展方法"
              align="center">
          </el-table-column>
          <el-table-column
              prop="remark"
              label="备注"
              align="center">
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="索引" name="second">
        <el-table
            :data="indexData"
            height="50vh"
            style="width: 100%; ">
          <el-table-column
              type="index"
              label="序号"
              align="center"
              :width="80">
          </el-table-column>
          <el-table-column
              prop="indexName"
              label="索引名称"
              align="center">
          </el-table-column>
          <el-table-column
              prop="columnName"
              label="字段名称"
              align="center">
          </el-table-column>
          <el-table-column
              prop="isUniqueStr"
              label="是否唯一索引"
              align="center">
          </el-table-column>
<!--          <el-table-column-->
<!--              prop="indexType"-->
<!--              label="索引类型"-->
<!--              align="center">-->
<!--          </el-table-column>-->
        </el-table>
      </el-tab-pane>
    </el-tabs>

  </el-dialog>
</template>

<script>
import dbManager from "@/api/dbManager";

export default {
  name: "dbTableDetailDlg",
  data() {
    return {
      tabsActiveName: 'first',
      showTableDetailDlg : false,
      title : '',
      columnsData: [],
      indexData:[],
    }
  },

  methods: {
    showTableDetail(table) {
      this.title = table.tableName;
      if (table.remark) {
        this.title += "（" + table.remark + "）";
      }
      this.getDbColumn(table.tableName, table.schema);
      this.getDbIndex(table.tableName, table.schema);
      this.showTableDetailDlg = true;
    },

    async getDbColumn(tableName, schema) {
      const { code, data, msg } = await dbManager.getDbColumn({'tableName':tableName, 'schema':schema});
      if ('200' == code) {
        if (data && data.length > 0) {
          for (let i = 0; i < data.length; i++) {
            data[i].isPrimaryStr = data[i].isPrimary == 1 ? '是' : '';
            data[i].isNullableStr = data[i].isNullable == 1 ? '' : '否';
            data[i].columnSize = data[i].varcharLength ? data[i].varcharLength : data[i].numberLength;
          }
        }
        this.columnsData = data;
      }
    },

    async getDbIndex(tableName, schema) {
      const { code, data, msg } = await dbManager.getDbIndex({'tableName':tableName, 'schema':schema});
      if ('200' == code) {
        if (data && data.length > 0) {
          for (let i = 0; i < data.length; i++) {
            data[i].isUniqueStr = data[i].isUnique == 1 ? '是' : '否';
          }
        }
        this.indexData = data;
      }
    },

    closeDlg() {
      this.showTableDetailDlg = false;
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