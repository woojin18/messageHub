import httpClient from '@/common/http-client';

/* PUSH */
const savePushTmplt = (params) => {
    return httpClient.post('/uc/template/savePushTmplt', params, { headers: {"show-layer": "Yes"} });
};
const selectPushTmpltInfo = (params) => {
    return httpClient.post('/uc/template/selectPushTmpltInfo', params, { headers: {"show-layer": "Yes"} });
};
const selectPushTmpltList = (params) => {
    return httpClient.post('/uc/template/selectPushTmpltList', params, { headers: {"show-layer": "Yes"} });
};
const deletePushTmplt = (params) => {
    return httpClient.post('/uc/template/deletePushTmplt', params, { headers: {"show-layer": "Yes"} });
};
const excelDownloadPushTmplt = (params) => {
  return httpClient.post('/uc/template/excelDownloadPushTmplt', params, { headers: {"show-layer": "Yes"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};

/* SMS */
const saveSmsTmplt = (params) => {
  return httpClient.post('/uc/template/saveSmsTmplt', params, { headers: {"show-layer": "Yes"} });
};
const selectSmsTmpltList = (params) => {
  return httpClient.post('/uc/template/selectSmsTmpltList', params, { headers: {"show-layer": "Yes"} });
};
const selectSmsTmpltInfo = (params) => {
  return httpClient.post('/uc/template/selectSmsTmpltInfo', params, { headers: {"show-layer": "Yes"} });
};
const deleteSmsTmplt = (params) => {
  return httpClient.post('/uc/template/deleteSmsTmplt', params, { headers: {"show-layer": "Yes"} });
};
const excelDownloadSmsTmplt = (params) => {
  return httpClient.post('/uc/template/excelDownloadSmsTmplt', params, { headers: {"show-layer": "Yes"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};

/* 친구톡 */
const saveFrndTalkTmplt = (params) => {
  return httpClient.post('/uc/template/saveFrndTalkTmplt', params, { headers: {"show-layer": "Yes"} });
};
const selectFrndTalkList = (params) => {
  return httpClient.post('/uc/template/selectFrndTalkList', params, { headers: {"show-layer": "Yes"} });
};
const selectFrndTalkInfo = (params) => {
  return httpClient.post('/uc/template/selectFrndTalkInfo', params, { headers: {"show-layer": "Yes"} });
};
const excelDownloadFrndTalkTmplt = (params) => {
  return httpClient.post('/uc/template/excelDownloadFrndTalkTmplt', params, { headers: {"show-layer": "Yes"}, responseType: 'arraybuffer' })
  .then((response) => fnExcelDownCallback(response));
};
const deleteFrndTalkTmplt = (params) => {
  return httpClient.post('/uc/template/deleteFrndTalkTmplt', params, { headers: {"show-layer": "Yes"} });
};

/* 알림톡 */
const selectSenderKeyList = (params) => {
  return httpClient.post('/uc/template/selectSenderKeyList', params, { headers: {"show-layer": "Yes"} });
};
const selectKkoTmpltCatGrpList = (params) => {
  return httpClient.post('/uc/template/selectKkoTmpltCatGrpList', params, { headers: {"show-layer": "Yes"} });
};
const selectKkoTmpltCatList = (params) => {
  return httpClient.post('/uc/template/selectKkoTmpltCatList', params, { headers: {"show-layer": "Yes"} });
};

/* RCS */
const corpBrandCnt = (params) => {
  return httpClient.post('/uc/public/rcsTemplate/corpBrandCnt', params, { headers: {"show-layer": "Yes"} });
};

const selectRcsTemplateList = (params) => {
  return httpClient.post('/uc/public/rcsTemplate/selectRcsTemplateList', params, {headers: {"show-layer":"yes"}});
};

const rcsTemplateInit = (params) => {
  return httpClient.post('/uc/public/rcsTemplate/rcsTemplateInit', params, {headers: {"show-layer":"yes"}});
};

const rcsTemplateUpdateInit = (params) => {
  return httpClient.post('/uc/public/rcsTemplate/rcsTemplateUpdateInit', params, {headers: {"show-layer":"yes"}});
};

const rcsTemplateDeleteApi = (params) => {
  return httpClient.post('/uc/public/rcsTemplate/rcsTemplateDeleteApi', params, {headers: {"show-layer":"yes"}});
};

const rcsTemplateApi = (params) => {
  return httpClient.post('/uc/public/rcsTemplate/rcsTemplateApi', params, {headers: {"show-layer":"yes"}});
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