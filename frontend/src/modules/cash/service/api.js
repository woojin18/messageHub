import httpClient from '@/common/http-client';

const insertWebCashInfo = (params) => {
    return httpClient.post('/projectApi/cash/insertWebCashInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectCashHist = (params) => {
    return httpClient.post('/projectApi/cash/selectCashHist', params, { headers: {"show-layer": "Yes"} });
};

const selectCashBalance = (params) => {
    return httpClient.post('/projectApi/cash/selectCashBalance', params, { headers: { "show-layer": "Yes" } });
};

const selectUcubeInfo = (params) => {
    return httpClient.post('/projectApi/cash/selectUcubeInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectUcubePopInfo = params => {
  return httpClient.post('/projectApi/cash/selectUcubePopInfo', params, { headers: { 'show-layer': 'Yes' } });
};


const selectProjectInfo = (params) => {
    return httpClient.post('/projectApi/cash/selectProjectInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectProjectSubBillCode = (params) => {
    return httpClient.post('/projectApi/cash/selectProjectSubBillCode', params, { headers: {"show-layer": "Yes"} });
};

const insertUbubeInfo = (params) => {
    return httpClient.post('/projectApi/cash/insertUbubeInfo', params, { headers: {"show-layer": "Yes"} });
};

const updateProjectBillId = (params) => {
    return httpClient.post('/projectApi/cash/updateProjectBillId', params, { headers: {"show-layer": "Yes"} });
};

const saveProjectSubBillCode = (params) => {
    return httpClient.post('/projectApi/cash/saveProjectSubBillCode', params, { headers: {"show-layer": "Yes"} });
};

const deleteProjectSubBillCode = (params) => {
    return httpClient.post('/projectApi/cash/deleteProjectSubBillCode', params, { headers: {"show-layer": "Yes"} });
};

const selectVirAccStatus = (params) => {
    return httpClient.post('/projectApi/cash/selectVirAccStatus', params, { headers: {"show-layer": "Yes"} });
};

const selectVirAccDetail = (params) => {
    return httpClient.post('/projectApi/cash/selectVirAccDetail', params, { headers: {"show-layer": "Yes"} });
};

const selectVirAccUrl = () => {
    return httpClient.post('/projectApi/cash/selectVirAccUrl', { headers: {"show-layer": "Yes"} });
}

const selectCalList = (params) => {
    return httpClient.post('/projectApi/cash/selectCalList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectDeptList = (params) => {
    return httpClient.post('/projectApi/cash/selectDeptList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectBillList = (params) => {
    return httpClient.post('/projectApi/cash/selectBillList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveDept = (params) => {
    return httpClient.post('/projectApi/cash/saveDept', params, { headers: {"show-layer": "Yes", "activity":"SAVE"}, timeout: 150000 });
};

const delDept = (params) => {
    return httpClient.post('/projectApi/cash/delDept', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const excelDownloadCalList = (params) => {
	return httpClient.post('/projectApi/cash/excelDownloadCalList', params, { headers: {"show-layer": "No", "activity":"READ"}, responseType: 'arraybuffer' }).then(function(response) {
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
    insertWebCashInfo,
    selectCashHist,
    selectCashBalance,
    selectUcubeInfo,
    selectUcubePopInfo,
    selectProjectInfo,
    selectProjectSubBillCode,
    insertUbubeInfo,
    updateProjectBillId,
    saveProjectSubBillCode,
    deleteProjectSubBillCode,
    selectVirAccStatus,
    selectVirAccDetail,
    selectVirAccUrl,
    selectCalList,
    selectDeptList,
    selectBillList,
    saveDept,
    delDept,
    excelDownloadCalList
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
