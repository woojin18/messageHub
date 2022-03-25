import httpClient from '@/common/http-client';

const selectUrlInfoList = (params) => {
  return httpClient.post('/uc/urlInfo/selectUrlInfoList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
}
const selectUrlInfoStatList = (params) => {
  return httpClient.post('/uc/urlInfo/selectUrlInfoStatList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
}
const selectUrlInfoStatDetail = (params) => {
  return httpClient.post('/uc/urlInfo/selectUrlInfoStatDetail', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
}
const insertUrlInfo = (params) => {
  return httpClient.post('/uc/urlInfo/insertUrlInfo', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
}
const deleteUrlInfo = (params) => {
  return httpClient.post('/uc/urlInfo/deleteUrlInfo', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
}
const excelDownUrlInfoStatList = (params) => {
	return httpClient.post('/uc/urlInfo/excelDownUrlInfoStatList', params, 
    { headers: {"show-layer": "No", "activity":"READ"}, responseType: 'arraybuffer' })
  .then((res) => {
		try {
			let blob = new Blob([res.data], { type: res.headers['content-type'] })
			let fileName = getFileName(res.headers['content-disposition'])
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
	})
}

export default {
  selectUrlInfoList,
  selectUrlInfoStatList,
  selectUrlInfoStatDetail,
  insertUrlInfo,
  deleteUrlInfo,
  excelDownUrlInfoStatList,
}

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