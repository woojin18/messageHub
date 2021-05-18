import httpClient from '@/common/http-client';

const selectAppIdList = (params) => {
  return httpClient.post('/api/public/sendMessage/selectAppIdList', params, { headers: {"show-layer": "Yes"} });
};
const selectCallbackList = (params) => {
  return httpClient.post('/api/public/sendMessage/selectCallbackList', params, { headers: {"show-layer": "Yes"} });
};
const selectAddressList = (params) => {
  return httpClient.post('/api/public/sendMessage/selectAddressList', params, { headers: {"show-layer": "Yes"} });
};
const selectCmCuList = (params) => {
  return httpClient.post('/api/public/sendMessage/selectCmCuList', params, { headers: {"show-layer": "Yes"} });
};
const excelDownSendPushRecvTmplt = (params) => {
  return httpClient.post('/api/public/sendMessage/excelDownSendPushRecvTmplt', params, { headers: {"show-layer": "Yes"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};
const sendPushMessage = (params) => {
  return httpClient.post('/api/public/sendMessage/sendPushMessage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data"} });
};

const excelDownSendSmsRecvTmplt = (params) => {
  return httpClient.post('/api/public/sendMessage/excelDownSendSmsRecvTmplt', params, { headers: {"show-layer": "Yes"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};
const sendSmsMessage = (params) => {
  return httpClient.post('/api/public/sendMessage/sendSmsMessage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data"} });
};
const sendMmsMessage = (params) => {
  return httpClient.post('/api/public/sendMessage/sendMmsMessage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data"} });
};

const excelDownSendFrndTalkRecvTmplt = (params) => {
  return httpClient.post('/api/public/sendMessage/excelDownSendFrndTalkRecvTmplt', params, { headers: {"show-layer": "Yes"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};
const sendFrndTalkMessage = (params) => {
  return httpClient.post('/api/public/sendMessage/sendFrndTalkMessage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data"} });
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
    sendMmsMessage,
    excelDownSendFrndTalkRecvTmplt,
    sendFrndTalkMessage
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