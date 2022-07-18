<template>
  <div>
    <div style="background: #FAFAFA;align-content: center">
      <el-form :model="queryForm" inline="true" style="padding-left: 10px;padding-top: 20px">
        <el-form-item label="操作类型">
          <el-select v-model="queryForm.optType" clearable style="width: 150px;">
            <el-option
                v-for="item in optTypeSelect"
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
        <el-form-item label="当前处理人">
          <el-input v-model="queryForm.target" type="text" style="width: 150px;"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryForm.status" clearable style="width: 150px;">
            <el-option
                v-for="item in statusSelect"
                :key="item.id"
                :label="item.text"
                :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getOption()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div>
      <el-table
          :data="tableData"
          height="71vh"
    		  @row-click="showOption"
          style="width: 100%; ">
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
            prop="tableSchema"
            label="数据库名称"
            align="center"
            :width="100">
        </el-table-column>
        <el-table-column
            prop="tableName"
            label="表名"
            align="center">
        </el-table-column>
        <el-table-column
            prop="optTypeStr"
            label="操作类型"
            align="center"
            :width="100">
        </el-table-column>
        <el-table-column
            prop="description"
            label="描述"
            align="center">
        </el-table-column>
        <el-table-column
            prop="status"
            label="状态"
            align="center"
            :width="100">
        </el-table-column>
        <el-table-column
            prop="target"
            label="当前处理人"
            align="center"
            :width="100">
        </el-table-column>
        <el-table-column
            prop="updateTime"
            label="最后更新时间"
            align="center">
        </el-table-column>
        <el-table-column
            prop="createBy"
            label="创建人"
            align="center"
            :width="100">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="创建时间"
            align="center">
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

    <applicationFormDlg v-model="showApplicationDlg" ref="applicationFormDlg" @on-close="closeApplicationDlg()"></applicationFormDlg>
  </div>
</template>

<script>
import applicationFormDlg from "@/views/applicationFormDlg";
import dbOption from "@/api/dbOption";

export default {
  components:{
    applicationFormDlg
  },

  data() {
    return {
      queryForm: {},
      optTypeSelect: [],
      statusSelect: [
        {id:0, text:'待处理'},
        {id:1, text:'已完成'},
      ],

      optTypeIndex: {},

      tableData:[],
	    tableTotal: 0,
	    currentPage: 1,
	    pageSize: 20,

      showApplicationDlg: false,
    }
  },

  methods: {
    handleSizeChange: function (size) {
      //一页显示多少条
      this.pageSize = size;
      this.getOption();
    },

    handleCurrentChange: function (currentPage) {
      //页码更改方法
      this.currentPage = currentPage;
      this.getOption();
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
      this.showApplicationDlg = false;
    },

    async getOptTypeSelect() {
      const { code, data, msg } = await dbOption.getTableOptTypeSelect();
      if ('200' == code) {
        this.optTypeSelect = data;
        for (let i = 0; i < data.length; i++) {
          this.optTypeIndex[data[i].id] = data[i].text;
        }
      }
    },

    async getOption() {
      this.queryForm.currentPage = this.currentPage;
      this.queryForm.size = this.pageSize;
      const { code, data, msg } = await dbOption.getOption(this.queryForm);
      if ('200' == code) {
        this.tableData = data.records;
        for (let i = 0; i < this.tableData.length; i++) {
          this.tableData[i].optTypeStr = this.optTypeIndex[this.tableData[i].optType];
          this.tableData[i].status = this.tableData[i].step == 1 ? '已完成' : '待处理';
        }
        this.tableTotal = data.total;
      }
    }
	
  },
  
  created() {
    this.$store.state.menuActiveIndex = "/optionRecord";
  	this.getOptTypeSelect();
	  this.getOption();
  }
}
</script>

<style scoped>

</style>