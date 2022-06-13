<template>
  <div>
    <!-- <el-button @click="showSetDbConf()">设置</el-button> -->
    <el-button type="primary" @click="syncMetaData()">同步</el-button>
	<!-- <el-button type="primary" @click="getDbTable()" style="float: right;">查询</el-button> -->

    <div style="padding-top:10px;">
      <el-table
          :data="tableData"
          height="75vh"
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
            :width=tableColumnWidth
            align="center">
        </el-table-column>
        <el-table-column
            prop="tableName"
            label="表名"
            :width=tableColumnWidth
            align="center">
        </el-table-column>
		<el-table-column
		    prop="remark"
		    label="备注"
		    :width=tableColumnWidth
		    align="center">
		</el-table-column>
        <el-table-column
            prop="updateTime"
            label="修改时间"
            :width=tableColumnWidth
            align="center">
        </el-table-column>
        <el-table-column
            prop="updater"
            label="修改人"
            :width=tableColumnWidth
            align="center">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="创建时间"
            :width=tableColumnWidth
            align="center">
        </el-table-column>
        <el-table-column
            prop="creator"
            label="创建人"
            :width=tableColumnWidth
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

    <dbConfDlg v-model="showSetDbConfDlg" ref="dbConfDlg" @on-close="showSetDbConfDlg=false"></dbConfDlg>
	<dbTableDetailDlg v-model="showTableDetailDlg" ref="dbTableDetailDlg" @on-close="showTableDetailDlg=false"></dbTableDetailDlg>
  </div>
</template>

<script>
import dbConfDlg from "@/views/dbConfDlg";
import dbTableDetailDlg from "@/views/dbTableDetailDlg";
import dbConf from "@/api/dbConf";
import dbManager from "@/api/dbManager";

export default {
  components:{
    dbConfDlg,
	dbTableDetailDlg
  },

  data() {
    return {
      tableColumnWidth:document.body.clientWidth / 8,
      tableHeight:document.body.clientHeight * 0.8,

      tableData:[],
	  tableTotal: 0,
	  currentPage: 1,
	  pageSize: 20,

      showSetDbConfDlg: false,
	  showTableDetailDlg: false,
	  
	  dbConf:{},
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
		let table = {};
		table.tableName = row.tableName;
		table.schema = row.tableSchema;
		table.remark = row.remark;
		this.$refs.dbTableDetailDlg.showTableDetail(table);
		this.showTableDetailDlg = true;
	},
	
	async syncMetaData() {
		const { code, data, msg } = await dbManager.syncMetaData(this.dbConf);
		if ("200" == code) {
			this.getDbTable();
			this.$message.success(msg);
		} else {
			this.$message.error(msg);
		}
	},
	
	async getDbConf() {
		const { code, data, msg } = await dbConf.getDbConfByPost({"currentPage":1, "size":1});
		if ('200' == code && data != null && data.records.length > 0) {
			this.dbConf = data.records[0];
		}
	},
	
	async getDbTable() {
		const { code, data, msg } = await dbManager.getDbTable({"currentPage":this.currentPage, "size":this.pageSize});
		if ('200' == code && data != null && data.records.length > 0) {
			this.tableData = data.records;
			this.tableTotal = data.total;
		}
	}
	
  },
  
  created() {
  	this.getDbConf();
	this.getDbTable();
  }
}
</script>

<style scoped>

</style>