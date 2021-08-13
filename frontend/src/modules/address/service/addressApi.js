import httpClient from '@/common/http-client';

const selectAddressCateGrpList = (params) => {
	return httpClient.post('/addressApi/manage/selectAddressCateGrpList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectAddressList = (params) => {
	return httpClient.post('/addressApi/manage/selectAddressList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectMemberList = (params) => {
	return httpClient.post('/addressApi/manage/selectMemberList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const registerAddr = (params) => {
	return httpClient.post('/addressApi/manage/registerAddr', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const modifyAddr = (params) => {
	return httpClient.post('/addressApi/manage/modifyAddr', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const selectProjectList = (params) => {
	return httpClient.post('/addressApi/manage/selectProjectList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectCmCuList = (params) => {
	return httpClient.post('/addressApi/manage/selectCmCuList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const registerMember = (params) => {
	return httpClient.post('/addressApi/manage/registerMember', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const deleteMember = (params) => {
	return httpClient.post('/addressApi/manage/deleteMember', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const selectReceiverList = (params) => {
	return httpClient.post('/addressApi/manage/selectReceiverList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveReceiver = (params) => {
	return httpClient.post('/addressApi/manage/saveReceiver', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const deleteReceiver = (params) => {
	return httpClient.post('/addressApi/manage/deleteReceiver', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const saveAddressCategory = (params) => {
	return httpClient.post('/addressApi/manage/saveAddressCategory', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const deleteAddressCategory = (params) => {
	return httpClient.post('/addressApi/manage/deleteAddressCategory', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const deleteAddress = (params) => {
	return httpClient.post('/addressApi/manage/deleteAddress', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};



const excelDownloadReceiverList = (params) => {
	return httpClient.post('/addressApi/manage/excelDownloadReceiverList', params, { headers: {"show-layer": "No", "activity":"READ"}, responseType: 'arraybuffer' }).then(function(response) {
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

const excelUploadTemplate = (params) => {
	return httpClient.post('/addressApi/manage/excelUploadTemplate', params, { headers: {"show-layer": "Yes", "activity":"READ"}, responseType: 'arraybuffer' })
	.then((response) => fnExcelDownCallback(response));
};

const excelUploadReceiver = (params) => {
	return httpClient.post('/addressApi/manage/excelUploadReceiver', params, { headers: {"show-layer": "Yes", "activity":"SAVE", "Content-Type": "multipart/form-data"} });
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
	excelDownloadReceiverList,
	excelUploadTemplate,
	excelUploadReceiver,
	deleteReceiver,
	saveAddressCategory,
	deleteAddressCategory,
	deleteAddress,
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

function fnExcelDownCallback(response){
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
  }