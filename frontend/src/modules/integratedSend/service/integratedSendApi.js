import httpClient from '@/common/http-client';

const selectIntegratedSendList = (params) => {
    return httpClient.post('/integratedSendApi/selectIntegratedSendList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const sendIntegratedMessage = (params) => {
  return httpClient.post('/integratedSendApi/sendIntegratedMessage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data", "activity":"READ"} });
};

const selectIntegTmpltInfo = (params) => {
  return httpClient.post('/integratedSendApi/selectIntegTmpltInfo', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const excelDownSendIntegRecvTmplt = (params) => {
  return httpClient.post('/integratedSendApi/excelDownSendIntegRecvTmplt', params, { headers: {"show-layer": "Yes", "activity":"READ"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};

export default {
    selectIntegratedSendList,
    sendIntegratedMessage,
    selectIntegTmpltInfo,
    excelDownSendIntegRecvTmplt
};

function fnExcelDownCallback(response){
  try {
    let blob = new Blob([response.data], { type: response.headers['content-type'] })
    let fileName = fnGetFileName(response.headers['content-disposition'])
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

function fnGetFileName (contentDisposition) {
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