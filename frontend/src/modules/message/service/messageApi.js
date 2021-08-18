import httpClient from '@/common/http-client';

const selectAppIdList = (params) => {
  return httpClient.post('/uc/sendMessage/selectAppIdList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const selectCallbackList = (params) => {
  return httpClient.post('/uc/sendMessage/selectCallbackList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const selectAddressList = (params) => {
  return httpClient.post('/uc/sendMessage/selectAddressList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const selectCmCuList = (params) => {
  return httpClient.post('/uc/sendMessage/selectCmCuList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const excelDownSendPushRecvTmplt = (params) => {
  return httpClient.post('/uc/sendMessage/excelDownSendPushRecvTmplt', params, { headers: {"show-layer": "Yes", "activity":"READ"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};
const sendPushMessage = (params) => {
  return httpClient.post('/uc/sendMessage/sendPushMessage', params, { headers: {"show-layer": "Yes", "activity":"SAVE", "Content-Type": "multipart/form-data"} });
};

const excelDownSendSmsRecvTmplt = (params) => {
  return httpClient.post('/uc/sendMessage/excelDownSendSmsRecvTmplt', params, { headers: {"show-layer": "Yes", "activity":"READ"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};
const sendSmsMessage = (params) => {
  return httpClient.post('/uc/sendMessage/sendSmsMessage', params, { headers: {"show-layer": "Yes", "activity":"SAVE", "Content-Type": "multipart/form-data"} });
};
const sendMmsMessage = (params) => {
  return httpClient.post('/uc/sendMessage/sendMmsMessage', params, { headers: {"show-layer": "Yes", "activity":"SAVE", "Content-Type": "multipart/form-data"} });
};

const excelDownSendFrndTalkRecvTmplt = (params) => {
  return httpClient.post('/uc/sendMessage/excelDownSendFrndTalkRecvTmplt', params, { headers: {"show-layer": "Yes", "activity":"READ"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};
const sendFrndTalkMessage = (params) => {
  return httpClient.post('/uc/sendMessage/sendFrndTalkMessage', params, { headers: {"show-layer": "Yes", "activity":"SAVE", "Content-Type": "multipart/form-data"} });
};
const selectKkoSenderKeyList = (params) => {
  return httpClient.post('/uc/sendMessage/selectKkoSenderKeyList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const excelDownSendAlimTalkRecvTmplt = (params) => {
  return httpClient.post('/uc/sendMessage/excelDownSendAlimTalkRecvTmplt', params, { headers: {"show-layer": "Yes", "activity":"READ"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};
const sendAlimTalkMessage = (params) => {
  return httpClient.post('/uc/sendMessage/sendAlimTalkMessage', params, { headers: {"show-layer": "Yes", "activity":"SAVE", "Content-Type": "multipart/form-data"} });
};

const selectSmartTmpltList = (params) => {
  return httpClient.post('/uc/sendMessage/selectSmartTmpltList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const selectSmartTmpltInfo = (params) => {
  return httpClient.post('/uc/sendMessage/selectSmartTmpltInfo', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const sendSmartMessage = (params) => {
  return httpClient.post('/uc/sendMessage/sendSmartMessage', params, { headers: {"show-layer": "Yes", "activity":"SAVE", "Content-Type": "multipart/form-data"} });
};
const excelDownSendSmartRecvTmplt = (params) => {
  return httpClient.post('/uc/sendMessage/excelDownSendSmartRecvTmplt', params, { headers: {"show-layer": "Yes", "activity":"READ"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};

const selectValidUseChGrp = (params) => {
  return httpClient.post('/uc/sendMessage/selectValidUseChGrp', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const selectApiKey = (params) => {
  return httpClient.post('/commonApi/getApiKey', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const insertRejectPhone = (params) => {
  return httpClient.post('/uc/sendMessage/insertRejectPhone', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};
const selectRejectPhoneList = (params) => {
  return httpClient.post('/uc/sendMessage/selectRejectPhoneList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const deleteRejectPhoneList = (params) => {
  return httpClient.post('/uc/sendMessage/deleteRejectPhoneList', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
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
  sendFrndTalkMessage,
  selectKkoSenderKeyList,
  excelDownSendAlimTalkRecvTmplt,
  sendAlimTalkMessage,
  selectSmartTmpltList,
  selectSmartTmpltInfo,
  sendSmartMessage,
  excelDownSendSmartRecvTmplt,
  selectValidUseChGrp,
  selectApiKey,
  insertRejectPhone,
  selectRejectPhoneList,
  deleteRejectPhoneList
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