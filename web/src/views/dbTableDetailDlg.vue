<template>
    <el-dialog v-model="showTableDetailDlg" :title="title" width="1000px">
	<el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick" type="border-card">
	    <el-tab-pane label="字段" name="first">
			<el-table
			    :data="tableData"
			    height="50vh"
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
		</el-tab-pane>
	    <el-tab-pane label="索引" name="second"></el-tab-pane>
	</el-tabs>
	
	
      
    </el-dialog>
</template>

<script>
import dbManager from "@/api/dbManager";

export default {
  name: "dbTableDetailDlg",
  data() {
    return {
      showTableDetailDlg : false,
      title : '',
    }
  },

  methods: {
    async showTableDetail(table) {
      this.title = table.tableName;
	  if (table.remark) {
		  this.title += "（" + table.remark + "）";
	  }
      this.showTableDetailDlg = true;
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