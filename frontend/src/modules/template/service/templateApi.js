import tokenSvc from '@/common/token-service';
import httpClient from '@/common/http-client';

const savePushTmplt = (params) => {
    return httpClient.post('/api/public/template/savePushTmplt', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};

const selectPushTmpltInfo = (params) => {
    return httpClient.post('/api/public/template/selectPushTmpltInfo', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};

const selectPushTmpltList = (params) => {
    return httpClient.post('/api/public/template/selectPushTmpltList', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};

const deletePushTmplt = (params) => {
    return httpClient.post('/api/public/template/deletePushTmplt', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};

const excelDownloadPushTmplt = (params) => {
  return httpClient.post('/api/public/template/excelDownloadPushTmplt', params, { headers: {"show-layer": "No", "loginId": tokenSvc.getToken().principal.loginId}, responseType: 'arraybuffer' }).then(function(response) {
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

const saveSmsTmplt = (params) => {
  return httpClient.post('/api/public/template/saveSmsTmplt', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};

const selectSmsTmpltList = (params) => {
  return httpClient.post('/api/public/template/selectSmsTmpltList', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};

const selectSmsTmpltInfo = (params) => {
  return httpClient.post('/api/public/template/selectSmsTmpltInfo', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};

const deleteSmsTmplt = (params) => {
  return httpClient.post('/api/public/template/deleteSmsTmplt', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};

const excelDownloadSmsTmplt = (params) => {
  return httpClient.post('/api/public/template/excelDownloadSmsTmplt', params, { headers: {"show-layer": "No", "loginId": tokenSvc.getToken().principal.loginId}, responseType: 'arraybuffer' }).then(function(response) {
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

export default {
    savePushTmplt,
    selectPushTmpltInfo,
    selectPushTmpltList,
    deletePushTmplt,
    excelDownloadPushTmplt,
    saveSmsTmplt,
    selectSmsTmpltList,
    selectSmsTmpltInfo,
    deleteSmsTmplt,
    excelDownloadSmsTmplt
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