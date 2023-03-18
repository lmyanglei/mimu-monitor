const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    port: "8080",
    open: false,
    proxy: {
      "/monitor/api": {
        target: "http://localhost:8081/monitor/api",
        changeOrigin: true, //允许跨域
        pathRewrite: { "/monitor/api": "" },
      },
    },
  },
});