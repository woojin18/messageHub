module.exports = {
	/** build config */
	outputDir: '../src/main/resources/static', // html, ico 파일 저장
	publicPath: '/',
	assetsDir: 'assets', // css, img, js 저장. outputDir에 대한 상대 경로
	productionSourceMap: undefined,
	/** 
	 * /api/** 요청은 backend 서버로 전달한다.
	 * 이 설정이 없으면 axios에서 baseURL 설정하고 backend에서는 cors 설정 해야한다.
	 */
	devServer: {
		port: process.env.VUE_APP_PORT || 3000,
		proxy: {
			'^/api': {
				target: process.env.VUE_APP_TARGET_URL || 'http://localhost:7070',
				changeOrigin: true
			},
			'^/listCtl': {
				target: process.env.VUE_APP_TARGET_URL || 'http://localhost:7070',
				changeOrigin: true
			},
			'^/projectApi': {
				target: process.env.VUE_APP_TARGET_URL || 'http://localhost:7070',
				changeOrigin: true
			},
			'^/userApi': {
				target: process.env.VUE_APP_TARGET_URL || 'http://localhost:7070',
				changeOrigin: true
			},
			'^/messageStatusApi': {
				target: process.env.VUE_APP_TARGET_URL || 'http://localhost:7070',
				changeOrigin: true
			},
			'^/integratedTemplateApi': {
				target: process.env.VUE_APP_TARGET_URL || 'http://localhost:7070',
				changeOrigin: true
			},
			'^/addressApi': {
				target: process.env.VUE_APP_TARGET_URL || 'http://localhost:7070',
				changeOrigin: true
			}
		}
	},
	runtimeCompiler: true,
}