import httpClient from '@/common/http-client';

const selectAddressCateGrpList = (params) => {
	return httpClient.post('/addressApi/manage/selectAddressCateGrpList', params, { headers: {"show-layer": "Yes"} });
};

const selectAddressList = (params) => {
	return httpClient.post('/addressApi/manage/selectAddressList', params, { headers: {"show-layer": "Yes"} });
};

const selectMemberList = (params) => {
	return httpClient.post('/addressApi/manage/selectMemberList', params, { headers: {"show-layer": "Yes"} });
};

const registerAddr = (params) => {
	return httpClient.post('/addressApi/manage/registerAddr', params, { headers: {"show-layer": "Yes"} });
};

const modifyAddr = (params) => {
	return httpClient.post('/addressApi/manage/modifyAddr', params, { headers: {"show-layer": "Yes"} });
};

const selectProjectList = (params) => {
	return httpClient.post('/addressApi/manage/selectProjectList', params, { headers: {"show-layer": "Yes"} });
};

const selectCmCuList = (params) => {
	return httpClient.post('/addressApi/manage/selectCmCuList', params, { headers: {"show-layer": "Yes"} });
};

const registerMember = (params) => {
	return httpClient.post('/addressApi/manage/registerMember', params, { headers: {"show-layer": "Yes"} });
};

const deleteMember = (params) => {
	return httpClient.post('/addressApi/manage/deleteMember', params, { headers: {"show-layer": "Yes"} });
};

const selectReceiverList = (params) => {
	return httpClient.post('/addressApi/manage/selectReceiverList', params, { headers: {"show-layer": "Yes"} });
};

const saveReceiver = (params) => {
	return httpClient.post('/addressApi/manage/saveReceiver', params, { headers: {"show-layer": "Yes"} });
};

const deleteReceiver = (params) => {
	return httpClient.post('/addressApi/manage/deleteReceiver', params, { headers: {"show-layer": "Yes"} });
};

const saveAddressCategory = (params) => {
	return httpClient.post('/addressApi/manage/saveAddressCategory', params, { headers: {"show-layer": "Yes"} });
};

const deleteAddressCategory = (params) => {
	return httpClient.post('/addressApi/manage/deleteAddressCategory', params, { headers: {"show-layer": "Yes"} });
};



const excelDownloadReceiverTemplate = (params) => {
	return httpClient.post('/addressApi/manage/excelDownloadReceiverTemplate', params, { headers: {"show-layer": "No"}, responseType: 'arraybuffer' }).then(function(response) {
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
}

const excelUploadReceiver = (params) => {
	return httpClient.post('/addressApi/manage/excelUploadReceiver', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data"} });
};

export default {
	selectAddressCateGrpList,
	selectAddressList,
	selectMemberList,
	registerAddr,
	modifyAddr,
	selectProjectList,
	selectCmCuList,
	registerMember,
	deleteMember,
	selectReceiverList,
	saveReceiver,
	excelDownloadReceiverTemplate,
	excelUploadReceiver,
	deleteReceiver,
	saveAddressCategory,
	deleteAddressCategory,
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