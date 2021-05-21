import httpClient from '@/common/http-client';

const selectIntegratedTemplateList = (params) => {
    return httpClient.post('/integratedTemplateApi/selectIntegratedTemplateList', params, { headers: {"show-layer": "Yes"} });
};

const insertIntegratedTemplate = (params) => {
    return httpClient.post('/integratedTemplateApi/insertIntegratedTemplate', params, { headers: {"show-layer": "Yes"} });
};

const updateIntegratedTemplate = (params) => {
    return httpClient.post('/integratedTemplateApi/updateIntegratedTemplate', params, { headers: {"show-layer": "Yes"} });
};

const excelDownloadIntegratedTemplate = (params) => {
    return httpClient.post('/integratedTemplateApi/excelDownloadIntegratedTemplate', params, { headers: {"show-layer": "No"}, responseType: 'arraybuffer' }).then(function(response) {
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
    selectIntegratedTemplateList,
    excelDownloadIntegratedTemplate,
    insertIntegratedTemplate,
    updateIntegratedTemplate
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