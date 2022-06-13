<template>
    <el-container >
		<el-header style="background: #C6E2FF;" height="8vh" >
			<span style="line-height: 8vh">MetaData</span>
		</el-header>

		<el-container>
			<el-aside :width="navWidth" style="overflow: hidden">
				<navbar @on-collapse="onCollapse($event)" @on-dbConf="showDbConfDlg()"></navbar>
			</el-aside>
			<el-main>
				<router-view></router-view>
			</el-main>
		</el-container>
		
		<dbConfDlg v-model="showSetDbConfDlg" ref="dbConfDlg" @on-close="showSetDbConfDlg=false"></dbConfDlg>
    </el-container>
</template>
<script>
import navbar from "@/components/navbar";
import dbConfDlg from "@/views/dbConfDlg";

export default {
  components: {
    navbar,
	dbConfDlg
  },

  data() {
    return {
      isCollapse : true,
      navWidth: '70px',
	  showSetDbConfDlg: false,
    }
  },

  methods:{
    onCollapse(data) {
      this.isCollapse = data;
      this.isCollapse == false ? this.navWidth = '120px' : this.navWidth = '70px';
    },
	
	showDbConfDlg() {
		this.$refs.dbConfDlg.showSetDbConf();
		this.showSetDbConfDlg = true;
	}
  }

}

</script>

<style>

</style>