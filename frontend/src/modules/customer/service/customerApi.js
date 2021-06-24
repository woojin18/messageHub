import httpClient from '@/common/http-client';

const selectCodeList = (params) => {
  return httpClient.post('/api/public/customer/selectCodeList', params, { headers: {"show-layer": "Yes"} });
};
const selectFaqList = (params) => {
  return httpClient.post('/api/public/customer/selectFaqList', params, { headers: {"show-layer": "Yes"} });
};
const selectNoticeList = (params) => {
  return httpClient.post('/api/public/customer/selectNoticeList', params, { headers: {"show-layer": "Yes"} });
};
const insertQuestBoard = (params) => {
  return httpClient.post('/api/public/customer/insertQuestBoard', params, { headers: {"show-layer": "Yes"} });
};
const selectLibraryList = (params) => {
  return httpClient.post('/api/public/customer/selectLibraryList', params, { headers: {"show-layer": "Yes"} });
};
const procDownloadLibraryFile = (params) => {
  return httpClient.post('/api/public/customer/procDownloadLibraryFile', params, { headers: {"show-layer": "Yes"}, responseType: 'arraybuffer' })
  .then((response) => fnDownCallback(response));
};

const selectCmUseTermsHistList = (params) => {
  return httpClient.post('/api/public/customer/selectCmUseTermsHistList', params, { headers: {"show-layer": "Yes"} });
};
const selectCmUseTermsInfo = (params) => {
  return httpClient.post('/api/public/customer/selectCmUseTermsInfo', params, { headers: {"show-layer": "Yes"} });
};


export default {
  selectCodeList,
  selectFaqList,
  selectNoticeList,
  insertQuestBoard,
  selectLibraryList,
  procDownloadLibraryFile,
  selectCmUseTermsHistList,
  selectCmUseTermsInfo
};

function fnDownCallback(response){
  try {
    let blob = new Blob([response.data], { type: response.headers['content-type'] });
    let fileName = fnGetFileName(response.headers['content-disposition']);
    fileName = decodeURI(fileName);

    if (window.navigator.msSaveOrOpenBlob) { // IE 10+
      window.navigator.msSaveOrOpenBlob(blob, fileName);
    } else { // not IE
      let link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.target = '_self';
      if (fileName) link.download = fileName;
      link.click();
    }
  } catch (e) {
    console.error(e);
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