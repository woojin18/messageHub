import httpClient from '@/common/http-client';

const selectUseHistory = (params) => {
	return httpClient.post('/useApi/selectUseHistory', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectSixMonthPrePayAmtList = (params) => {
	return httpClient.post('/useApi/selectSixMonthPrePayAmtList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectSixMonthDefPayAmt = (params) => {
	return httpClient.post('/useApi/selectSixMonthDefPayAmt', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectSixMonthUseCount = (params) => {
	return httpClient.post('/useApi/selectSixMonthUseCount', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const excelDownloadUseHistory = (params) => {
	return httpClient.post('/useApi/excelDownloadUseHistory', params, { headers: {"show-layer": "No", "activity":"READ"}, responseType: 'arraybuffer' }).then(function(response) {
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

const selectCorpProductUnitDetail = (params) => {
	return httpClient.post('/useApi/selectCorpProductUnitDetail', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectUseDetailList = (params) => {
	return httpClient.post('/useApi/selectUseDetailList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const excelDownloadUseDetailList = (params) => {
	return httpClient.post('/useApi/excelDownloadUseDetailList', params, { headers: {"show-layer": "No", "activity":"READ"}, responseType: 'arraybuffer' }).then(function(response) {
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
	selectUseHistory,
	excelDownloadUseHistory,
	selectCorpProductUnitDetail,
	selectSixMonthPrePayAmtList,
	selectSixMonthDefPayAmt,
	selectSixMonthUseCount,
	selectUseDetailList,
	excelDownloadUseDetailList
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