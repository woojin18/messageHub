import httpClient from '@/common/http-client';
import tokenSvc from '@/common/token-service';

const selectAppIdList = (params) => {
    return httpClient.post('/api/public/sendMessage/selectAppIdList', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};
const selectCallbackList = (params) => {
    return httpClient.post('/api/public/sendMessage/selectCallbackList', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};
const selectAddressList = (params) => {
    return httpClient.post('/api/public/sendMessage/selectAddressList', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};
const selectCmCuList = (params) => {
    return httpClient.post('/api/public/sendMessage/selectCmCuList', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};
const excelDownSendPushRecvTmplt = (params) => {
  return httpClient.post('/api/public/sendMessage/excelDownSendPushRecvTmplt', params, { headers: {"show-layer": "No", "loginId": tokenSvc.getToken().principal.loginId}, responseType: 'arraybuffer' }).then(function(response) {
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
const sendPushMessage = (params) => {
  return httpClient.post('/api/public/sendMessage/sendPushMessage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data", "loginId": tokenSvc.getToken().principal.loginId} });
};

const excelDownSendSmsRecvTmplt = (params) => {
  return httpClient.post('/api/public/sendMessage/excelDownSendSmsRecvTmplt', params, { headers: {"show-layer": "No", "loginId": tokenSvc.getToken().principal.loginId}, responseType: 'arraybuffer' }).then(function(response) {
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
const sendSmsMessage = (params) => {
  return httpClient.post('/api/public/sendMessage/sendSmsMessage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data", "loginId": tokenSvc.getToken().principal.loginId} });
};
const sendMmsMessage = (params) => {
  return httpClient.post('/api/public/sendMessage/sendMmsMessage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data", "loginId": tokenSvc.getToken().principal.loginId} });
};


export default {
    selectAppIdList,
    selectCallbackList,
    selectAddressList,
    selectCmCuList,
    excelDownSendPushRecvTmplt,
    sendPushMessage,
    excelDownSendSmsRecvTmplt,
    sendSmsMessage,
    sendMmsMessage
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