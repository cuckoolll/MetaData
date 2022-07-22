<template>
  <div>
    <div style="background: #FAFAFA;align-content: center">
      <el-form :model="queryForm" inline="true" style="padding-left: 10px;padding-top: 20px">
        <el-form-item label="数据库名称">
          <el-select v-model="queryForm.schema" clearable style="width: 150px;">
            <el-option
                v-for="item in schemaSelect"
                :key="item.id"
                :label="item.text"
                :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="表名">
          <el-input v-model="queryForm.tableName" type="text" style="width: 150px;"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="queryForm.remark" type="text" style="width: 150px;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDbTable()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div style="margin-top: 5px;">
      <el-button type="primary" icon="plus" text @click="showCreateTable">创建</el-button>
    </div>
    <div>
      <el-table
          :data="tableData"
          height="66vh"
    		  @row-click="showTableDetail"
          style="width: 100%; ">
        <el-table-column
            type="index"
            label="序号"
            align="center"
            :width="80">
        </el-table-column>
        <!-- <el-table-column
            prop="projectName"
            label="项目"
            :width=tableColumnWidth
            align="center">
        </el-table-column> -->
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
        <el-table-column
            label="操作"
            fixed="right"
            align="center">
          <template #default="scope">
            <el-button type="primary" icon="Edit" circle @click="showEditTable(scope.row)"></el-button>
            <el-button type="danger" icon="Delete" circle @click="showDelTable(scope.row)"></el-button>
            <el-dropdown trigger="click" style="padding-left: 12px;">
              <el-button type="info" icon="MoreFilled" circle></el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item icon="View" @click="showTableLog(scope.row)">变更记录</el-dropdown-item>
                  <el-dropdown-item icon="Download" @click="exportTable(scope.row)">导出</el-dropdown-item>
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

    <dbConfDlg v-model="showSetDbConfDlg" ref="dbConfDlg" @on-close="showSetDbConfDlg=false"></dbConfDlg>
	  <dbTableDetailDlg v-model="showTableDetailDlg" ref="dbTableDetailDlg" @on-close="showTableDetailDlg=false"></dbTableDetailDlg>
    <applicationFormDlg v-model="showApplicationDlg" ref="applicationFormDlg" @on-close="closeApplicationDlg()"></applicationFormDlg>

    <el-dialog v-model="showTableLogDlg"
               title="变更记录"
               width="1000px">
      <el-table
          :data="tableLogData"
          height="50vh"
          style="width: 100%; "
          @row-click="showOption">
        <el-table-column
            type="index"
            label="序号"
            align="center"
            :width="80">
        </el-table-column>
        <el-table-column
            prop="optId"
            label="单号"
            align="center"
            :width="100">
        </el-table-column>
        <el-table-column
            prop="createBy"
            label="变更人"
            align="center"
            :width="100">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="变更时间"
            align="center"
            :width="180">
        </el-table-column>
        <el-table-column
            prop="description"
            label="描述"
            align="center">
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import dbConfDlg from "@/views/dbConfDlg";
import dbTableDetailDlg from "@/views/dbTableDetailDlg";
import applicationFormDlg from "@/views/applicationFormDlg";
import dbConf from "@/api/dbConf";
import dbManager from "@/api/dbManager";
import dbOption from "@/api/dbOption";
import dbConstTable from "@/api/dbConstTable";
import {doExport} from "@/utils/export";

export default {
  components:{
    dbConfDlg,
	  dbTableDetailDlg,
    applicationFormDlg
  },

  data() {
    return {
      queryForm: {
        schema: '',
        tableName: '',
        remark: ''
      },
      schemaSelect: [],

      tableData:[],
	    tableTotal: 0,
	    currentPage: 1,
	    pageSize: 20,

      showSetDbConfDlg: false,
	    showTableDetailDlg: false,
      showApplicationDlg: false,

      showTableLogDlg: false,

      tableLogData: [],

    }
  },

  methods: {
    showSetDbConf() {
      this.$refs.dbConfDlg.showSetDbConf();
      this.showSetDbConfDlg = true;
    },
	
    handleSizeChange: function (size) {
      //一页显示多少条
      this.pageSize = size;
      this.getDbTable();
    },

    handleCurrentChange: function (currentPage) {
      //页码更改方法
      this.currentPage = currentPage;
      this.getDbTable();
    },

    showTableDetail (row, event, column) {
      if ('操作' != event.label) {
        let table = {};
        table.tableName = row.tableName;
        table.schema = row.tableSchema;
        table.remark = row.remark;
        this.$refs.dbTableDetailDlg.showTableDetail(table);
        this.showTableDetailDlg = true;
      }
    },

    showCreateTable() {
      let form = {};
      form.title = "创建表";
      form.tableSchema = this.queryForm.schema;
      form.optType = 'create_table';
      this.$refs.applicationFormDlg.showApplicationForm(form);
      this.showApplicationDlg = true;
    },

    async showEditTable(row) {
      let form = {};
      form.title = "编辑表";
      form.tableId = row.tableId;
      form.tableName = row.tableName;
      form.tableSchema = row.tableSchema;
      form.remark = row.remark;
      form.optType = 'edit_table';
      const {code, data, msg} = await dbOption.isOptionInProc(form);
      if ('200' == code && data == false) {
        this.$refs.applicationFormDlg.showApplicationForm(form);
        this.showApplicationDlg = true;
      } else {
        this.$message.warning(form.tableSchema + "库" + form.tableName + "表，存在未完成的变更记录")
      }
    },

    async showDelTable(row) {
      let form = {};
      form.title = "删除表";
      form.tableId = row.tableId;
      form.tableName = row.tableName;
      form.tableSchema = row.tableSchema;
      form.remark = row.remark;
      form.optType = 'del_table';
      const {code, data, msg} = await dbOption.isOptionInProc(form);
      if ('200' == code && data == false) {
        this.$refs.applicationFormDlg.showApplicationForm(form);
        this.showApplicationDlg = true;
      } else {
        this.$message.warning(form.tableSchema + "库" + form.tableName + "表，存在未完成的变更记录")
      }
    },

    showOption (row, event, column) {
      let form = {};
      form.title = "变更记录单: " + row.optId;
      form.optId = row.optId;
      form.optType = "show_table";
      this.$refs.applicationFormDlg.showApplicationForm(form);
      this.showApplicationDlg = true;
    },

    async closeApplicationDlg() {
      await this.getDbTable();
      this.showApplicationDlg = false;
    },

    async getSchemaSelect() {
      const { code, data, msg } = await dbConf.getSchemaSelect();
      if ('200' == code) {
        this.schemaSelect = data;
      }
    },

    async getDbTable() {
      this.queryForm.currentPage = this.currentPage;
      this.queryForm.size = this.pageSize;
      const { code, data, msg } = await dbManager.getDbTable(this.queryForm);
      if ('200' == code) {
        this.tableData = data.records;
        this.tableTotal = data.total;
      }
    },

    async showTableLog(row) {
      let param = {};
      param.tableName = row.tableName;
      param.tableSchema = row.tableSchema;
      const { code, data, msg } = await dbOption.getOptionByTableAndSchema(param);
      if ('200' == code) {
        this.tableLogData = data.records;
      }
      this.showTableLogDlg = true;
    },

    async exportTable(row) {
      const { code, data, msg } = await dbManager.exportTableSql(row.tableId);
      if ('200' != code) {
        this.$message.error(msg);
        return;
      }
      doExport(data, row.tableName + `.sql`);
    },
  },
  
  created() {
    this.$store.state.menuActiveIndex = "/table";
  	this.getSchemaSelect();
    this.queryForm.schema = this.$route.query.dbSchema;
	  this.getDbTable();
  }
}
</script>

<style scoped>

</style>