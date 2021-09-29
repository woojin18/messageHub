import httpClient from '@/common/http-client';

const selectStatisList = (params) => {
	return httpClient.post('/statisticsAdminApi/list/selectStatisList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectProjectList = (params) => {
	return httpClient.post('/statisticsAdminApi/list/selectProjectList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const excelDownloadStatisList = (params) => {
	return httpClient.post('/statisticsAdminApi/list/excelDownloadStatisList', params, { headers: {"show-layer": "No", "activity":"READ"}, responseType: 'arraybuffer' }).then(function(response) {
		try {
			let blob = new Blob([response.data], { type: response.headers['content-type'] })
			let fileName = getFileName(response.headers['content-disposition'])
			fileName = decodeURI(fileName)

		if (window.navigator.msSaveOrOpenBlob) { // IE 10+
			window.navigator.msSaveOrOpenBlob(blob, fileName)
		} else { // not IE
				let link = document.createElement('a')
				link.href = window.URL.createObjectURL(blob)
				link.target = '_self'
				if (fileName) link.download = fileName
				link.click()
			}
		} catch (e) {
			console.error(e)
		}
	});
};

export default {
	selectStatisList,
	selectProjectList,
	excelDownloadStatisList,
};


function getFileName (contentDisposition) {
	let fileName = contentDisposition
		.split(';')
		.filter((ele) => {
		return ele.indexOf('filename') > -1
	})
	.map((ele) => {
		return ele
			.replace(/"/g, '')
			.split('=')[1]
	})
	return fileName[0] ? fileName[0] : null
}