import httpClient from '@/common/http-client';

const selectSmartProductList = (params) => {
    return httpClient.post('/smartTemplateApi/selectSmartProductList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectSmartProductInfo = (params) => {
    return httpClient.post('/smartTemplateApi/selectSmartProductInfo', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};


const selectSmartTemplateList = (params) => {
    return httpClient.post('/smartTemplateApi/selectSmartTemplateList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const smartTemplateInfo = (params) => {
    return httpClient.post('/smartTemplateApi/selectSmartTemplateInfo', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const insertSmartTemplate = (params) => {
    return httpClient.post('/smartTemplateApi/insertSmartTemplate', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const updateSmartTemplate = (params) => {
    return httpClient.post('/smartTemplateApi/updateSmartTemplate', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const deleteSmartTemplate = (params) => {
    return httpClient.post('/smartTemplateApi/deleteSmartTemplate', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const selectBrandList = (params) => {
    return httpClient.post('/smartTemplateApi/selectBrandList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const excelDownloadSmartTemplate = (params) => {
    return httpClient.post('/smartTemplateApi/excelDownloadSmartTemplate', params, { headers: {"show-layer": "No", "activity":"READ"}, responseType: 'arraybuffer' }).then(function(response) {
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
    selectSmartProductList,
    selectSmartProductInfo,
    selectSmartTemplateList,
    smartTemplateInfo,
    excelDownloadSmartTemplate,
    insertSmartTemplate,
    updateSmartTemplate,
    deleteSmartTemplate,
    selectBrandList
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