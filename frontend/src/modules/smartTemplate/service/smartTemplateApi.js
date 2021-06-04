import httpClient from '@/common/http-client';

const selectSmartTemplateList = (params) => {
    return httpClient.post('/smartTemplateApi/selectSmartTemplateList', params, { headers: {"show-layer": "Yes"} });
};

const smartTemplateInfo = (params) => {
    return httpClient.post('/smartTemplateApi/selectSmartTemplateInfo', params, { headers: {"show-layer": "Yes"} });
};

const insertSmartTemplate = (params) => {
    return httpClient.post('/smartTemplateApi/insertSmartTemplate', params, { headers: {"show-layer": "Yes"} });
};

const updateSmartTemplate = (params) => {
    return httpClient.post('/smartTemplateApi/updateSmartTemplate', params, { headers: {"show-layer": "Yes"} });
};

const deleteSmartTemplate = (params) => {
    return httpClient.post('/smartTemplateApi/deleteSmartTemplate', params, { headers: {"show-layer": "Yes"} });
};


const excelDownloadSmartTemplate = (params) => {
    return httpClient.post('/smartTemplateApi/excelDownloadSmartTemplate', params, { headers: {"show-layer": "No"}, responseType: 'arraybuffer' }).then(function(response) {
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
    selectSmartTemplateList,
    smartTemplateInfo,
    excelDownloadSmartTemplate,
    insertSmartTemplate,
    updateSmartTemplate,
    deleteSmartTemplate
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