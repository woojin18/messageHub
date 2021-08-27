import httpClient from '@/common/http-client';

const rcsTemplatePopInit = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/rcsTemplatePopInit', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const rcsTemplateSearch = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/rcsTemplateSearch', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const rcsTemplateContent = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/rcsTemplateContent', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const rcsTemplateDetail = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/rcsTemplateDetail', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectCallbackList = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/selectCallbackList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const rcsMsgSave = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/rcsMsgSave', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const selectRcsMsgList = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/selectRcsMsgList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const deleteRcsTmpMsgbase = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/deleteRcsTmpMsgbase', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectRcsMsgDetail = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/selectRcsMsgDetail', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const sendRcsData = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/sendRcsData', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const excelDownSendRcsRecvTmplt = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/excelDownSendRcsRecvTmplt', params, { headers: {"show-layer": "Yes", "activity":"READ"}, responseType: 'arraybuffer' })
    .then((response) => fnExcelDownCallback(response));
  };



export default {
    rcsTemplatePopInit,
    rcsTemplateSearch,
    rcsTemplateContent,
    rcsTemplateDetail,
    selectCallbackList,
    rcsMsgSave,
    selectRcsMsgList,
    deleteRcsTmpMsgbase,
    selectRcsMsgDetail,
    sendRcsData,
    excelDownSendRcsRecvTmplt
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