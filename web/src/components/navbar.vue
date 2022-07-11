<template>
  <el-menu
      :default-active="activeIndex"
      :active="activeIndex"
      router
      class="nav-menu"
      :collapse="isCollapse"
      @select="selectItems"
  >
    <template v-for="(menu, index) in routes" :key="index" >
      <el-sub-menu v-if="isShowMenu(menu) && menu.children.length > 0 && menu.children.some(ele => !ele.hidden)" :index="menu.path">
        <template #title>
          <el-icon>
            <component :is="menu.icon" />
          </el-icon>
          <span>{{ menu.meta.nav }}</span>
        </template>
        <template :key="index + '-' + childIndex" v-for="(child, childIndex) in menu.children">
          <el-menu-item v-if="!child.hidden"  :index="menu.path+'/'+child.path">
            <el-icon v-if="child.icon">
              <component :is="child.icon" />
            </el-icon>
            <template #title>
              <span>{{ child.meta.nav }}</span>
            </template>
          </el-menu-item>
        </template>
      </el-sub-menu>
      <el-menu-item  v-else-if="isShowMenu(menu)" :index="menu.path">
        <el-icon>
          <component :is="menu.icon" />
        </el-icon>
        <template #title>
          <span>{{ menu.meta.nav }}</span>
        </template>
      </el-menu-item>
    </template>
	
<!--	<el-tooltip class="item" effect="dark" content="数据库设置" placement="right">-->
<!--		<el-menu-item index="" @click="showdbConfDlg()">-->
<!--			<el-button type="text" icon="Setting" style="color: #303133;"></el-button>-->
<!--		</el-menu-item>-->
<!--	</el-tooltip>-->
  </el-menu>
</template>

<script>
import { useRouter } from 'vue-router'
import { defineComponent } from 'vue'

export default defineComponent ({
  emits: ['on-dbConf'],
  setup(props, context) {
    const router = useRouter()
    const routes = router.options.routes

    const isShowMenu = (menu) => {
      if (menu.hidden) {
        return false
      }
      // if (menu.meta.requireAnyRoles && menu.meta.requireAnyRoles.length > 0) {
      //   const hasAnyRoles = user.hasAnyRoles(menu.meta.requireAnyRoles)
      //   if (!hasAnyRoles) {
      //     return false
      //   }
      // }
      return true
    }
	
	const showdbConfDlg = () => {
	  context.emit('on-dbConf')
	}

    return {
      isShowMenu,
      routes,
	    showdbConfDlg,
    }
  },

  data(){
    return {
      activeIndex: '/home',
      isCollapse:true,
    }
  },

  mounted() {
    this.activeIndex = this.$route.path
  },

  methods: {
    onExpand(){
      this.isCollapse = false;
      this.$emit('on-collapse', false);
    },

    onCollapse(){
      this.isCollapse = true;
      this.$emit('on-collapse', true);
    },

    getNavType(){
      //store.state.adminleftnavnum里值变化的时候，设置navselected
      this.activeIndex = this.$store.state.menuActiveIndex;
    },

    selectItems(index){
      //按钮选中之后设置当前的index为store里的值。
      this.$store.state.menuActiveIndex = index;
    }
  },

  watch: {
    // 监测store.state
    '$store.state.menuActiveIndex': 'getNavType'
  }
})
</script>

<style scoped>
  .nav-menu {
    height: 92vh;
    background: #ECF5FF;
  }
  .nav-menu:not(.el-menu--collapse) {
    height: 92vh;
    background: #ECF5FF;
  }
</style>