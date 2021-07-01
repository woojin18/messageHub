import httpClient from '@/common/http-client';

/* PUSH */
const savePushTmplt = (params) => {
    return httpClient.post('/uc/template/savePushTmplt', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};
const selectPushTmpltInfo = (params) => {
    return httpClient.post('/uc/template/selectPushTmpltInfo', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const selectPushTmpltList = (params) => {
    return httpClient.post('/uc/template/selectPushTmpltList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const deletePushTmplt = (params) => {
    return httpClient.post('/uc/template/deletePushTmplt', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};
const excelDownloadPushTmplt = (params) => {
  return httpClient.post('/uc/template/excelDownloadPushTmplt', params, { headers: {"show-layer": "Yes", "activity":"READ"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};

/* SMS */
const saveSmsTmplt = (params) => {
  return httpClient.post('/uc/template/saveSmsTmplt', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};
const selectSmsTmpltList = (params) => {
  return httpClient.post('/uc/template/selectSmsTmpltList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const selectSmsTmpltInfo = (params) => {
  return httpClient.post('/uc/template/selectSmsTmpltInfo', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const deleteSmsTmplt = (params) => {
  return httpClient.post('/uc/template/deleteSmsTmplt', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};
const excelDownloadSmsTmplt = (params) => {
  return httpClient.post('/uc/template/excelDownloadSmsTmplt', params, { headers: {"show-layer": "Yes", "activity":"READ"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};

/* 친구톡 */
const saveFrndTalkTmplt = (params) => {
  return httpClient.post('/uc/template/saveFrndTalkTmplt', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};
const selectFrndTalkList = (params) => {
  return httpClient.post('/uc/template/selectFrndTalkList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const selectFrndTalkInfo = (params) => {
  return httpClient.post('/uc/template/selectFrndTalkInfo', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const excelDownloadFrndTalkTmplt = (params) => {
  return httpClient.post('/uc/template/excelDownloadFrndTalkTmplt', params, { headers: {"show-layer": "Yes", "activity":"READ"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};
const deleteFrndTalkTmplt = (params) => {
  return httpClient.post('/uc/template/deleteFrndTalkTmplt', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

/* 알림톡 */
const selectSenderKeyList = (params) => {
  return httpClient.post('/uc/template/selectSenderKeyList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const selectKkoTmpltCatGrpList = (params) => {
  return httpClient.post('/uc/template/selectKkoTmpltCatGrpList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const selectKkoTmpltCatList = (params) => {
  return httpClient.post('/uc/template/selectKkoTmpltCatList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const procApprvRequestKkoTmplt = (params) => {
  return httpClient.post('/uc/template/procApprvRequestKkoTmplt', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};
const selectAlimTalkTmpltList = (params) => {
  return httpClient.post('/uc/template/selectAlimTalkTmpltList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const procDeleteRequestKkoTmplt = (params) => {
  return httpClient.post('/uc/template/procDeleteRequestKkoTmplt', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};
const excelDownloadAlimTalkTmplt = (params) => {
  return httpClient.post('/uc/template/excelDownloadAlimTalkTmplt', params, { headers: {"show-layer": "Yes", "activity":"READ"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};
const selectAlimTalkInfo = (params) => {
  return httpClient.post('/uc/template/selectAlimTalkInfo', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const procInspectRequestKkoTmplt = (params) => {
  return httpClient.post('/uc/template/procInspectRequestKkoTmplt', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};
const procUpdateRequestKkoTmplt = (params) => {
  return httpClient.post('/uc/template/procUpdateRequestKkoTmplt', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

/* RCS */
const corpBrandCnt = (params) => {
  return httpClient.post('/uc/rcsTemplate/corpBrandCnt', params, { headers: {"show-layer": "Yes"} });
};

const selectRcsTemplateList = (params) => {
  return httpClient.post('/uc/rcsTemplate/selectRcsTemplateList', params, {headers: {"show-layer":"yes"}});
};

const rcsTemplateInit = (params) => {
  return httpClient.post('/uc/rcsTemplate/rcsTemplateInit', params, {headers: {"show-layer":"yes"}});
};

const rcsTemplateUpdateInit = (params) => {
  return httpClient.post('/uc/rcsTemplate/rcsTemplateUpdateInit', params, {headers: {"show-layer":"yes"}});
};

const rcsTemplateDeleteApi = (params) => {
  return httpClient.post('/uc/rcsTemplate/rcsTemplateDeleteApi', params, {headers: {"show-layer":"yes"}});
};

const rcsTemplateApi = (params) => {
  return httpClient.post('/uc/rcsTemplate/rcsTemplateApi', params, {headers: {"show-layer":"yes"}});
};

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
    excelDownloadSmsTmplt,
    saveFrndTalkTmplt,
    selectFrndTalkList,
    selectFrndTalkInfo,
    excelDownloadFrndTalkTmplt,
    deleteFrndTalkTmplt,
    selectSenderKeyList,
    selectKkoTmpltCatGrpList,
    selectKkoTmpltCatList,
    selectAlimTalkTmpltList,
    procApprvRequestKkoTmplt,
    procUpdateRequestKkoTmplt,
    procDeleteRequestKkoTmplt,
    excelDownloadAlimTalkTmplt,
    procInspectRequestKkoTmplt,
    selectAlimTalkInfo,
    corpBrandCnt,
    selectRcsTemplateList,
    rcsTemplateInit,
    rcsTemplateUpdateInit,
    rcsTemplateDeleteApi,
    rcsTemplateApi
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