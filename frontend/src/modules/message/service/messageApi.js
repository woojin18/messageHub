import httpClient from '@/common/http-client';

const apiTest = (params) => {
    return httpClient.post('/api/public/sendMessage/apiTest', params, { headers: {"show-layer": "Yes"} });
};
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
  return httpClient.post('/api/public/sendMessage/excelDownSendPushRecvTmplt', params, { headers: {"show-layer": "No"}, responseType: 'arraybuffer' }).then(function(response) {
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
    apiTest,
    selectAppIdList,
    selectCallbackList,
    selectAddressList,
    selectCmCuList,
    excelDownSendPushRecvTmplt
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