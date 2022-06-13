const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  publicPath: '/',
  outputDir: 'dist',
  assetsDir: 'static',
  transpileDependencies: true,
  lintOnSave: false, // 是否开启eslint保存检测，有效值：ture | false | 'error'，
  productionSourceMap: false,
  devServer: {
    port : 17001,
    proxy: {
      '/metaData': {
        target: 'http://localhost:9080', // 配置好的后端接口地址
        // 允许跨域
        changeOrigin: true,
      }
    }
  },
})
