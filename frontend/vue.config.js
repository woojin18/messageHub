module.exports = {
  /** build config */
  outputDir: process.env.NODE_ENV === 'production'
    ? '../src/main/resources/templates' : 'dist', // html, ico 파일 저장
  assetsDir: process.env.NODE_ENV === 'production'
    ? '../static' : '', // css, img, js 저장. outputDir에 대한 상대 경로

  /** 
   * /api/** 요청은 backend 서버로 전달한다.
   * 이 설정이 없으면 axios에서 baseURL 설정하고 backend에서는 cors 설정 해야한다.
   */
  devServer: {
    proxy: {
      '^/api': {
        target: 'http://localhost:7070',
        changeOrigin: true
      },
      '^/listCtl': {
        target: 'http://localhost:7070',
        changeOrigin: true
      },
      '^/projectApi': {
        target: 'http://localhost:7070',
        changeOrigin: true
      }
    }
  },

  runtimeCompiler: true,
}