import httpClient from '@/common/http-client';

const fnSearch = (params) => {
    return httpClient.post('/listCtl/test', params, { headers: {"show-layer": "Yes"} });
};

const insertList = (params) => {
    return httpClient.post('/listCtl/insertList', params, { headers: {"show-layer": "Yes"} });
};

const updateList = (params) => {
    return httpClient.post('/listCtl/updateList', params, { headers: {"show-layer": "Yes"} });
};

const excelUpload = (params) => {
    return httpClient.post('/listCtl/excelUpload', params, { headers: {"show-layer": "Yes"} });
}

const excelDownload = (params) => {
    return httpClient.post('/listCtl/excelDownload', params, { headers: {"show-layer": "No"}, responseType: 'arraybuffer' }).then(function(response) {
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
    fnSearch,
    insertList,
    updateList,
    excelUpload,
    excelDownload
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