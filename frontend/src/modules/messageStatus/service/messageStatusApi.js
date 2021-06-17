import httpClient from '@/common/http-client';

const selectMessageStatusList = (params) => {
    return httpClient.post('/messageStatusApi/selectMessageStatusList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const excelDownloadMessageStatus = (params) => {
    return httpClient.post('/messageStatusApi/excelDownloadMessageStatus', params, { headers: {"show-layer": "No", "activity":"READ"}, responseType: 'arraybuffer' }).then(function(response) {
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

const selectMessageStatusDetail = (params) => {
    return httpClient.post('/messageStatusApi/selectMessageStatusDetail', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectWebSendList = (params) => {
    return httpClient.post('/messageStatusApi/selectWebSendList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectWebSendFailList = (params) => {
    return httpClient.post('/messageStatusApi/selectWebSendFailList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectBookingSendList = (params) => {
    return httpClient.post('/messageStatusApi/selectBookingSendList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const cancelBookingSend = (params) => {
    return httpClient.post('/messageStatusApi/cancelBookingSend', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const selectMoReceptionStatusList = (params) => {
    return httpClient.post('/messageStatusApi/selectMoReceptionList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectReceptionNumberList = (params) => {
    return httpClient.post('/messageStatusApi/selectReceptionNumberList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectConditionList = (params) => {
    return httpClient.post('/messageStatusApi/selectConditionList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};



const excelDownloadWebSend = (params) => {
    return httpClient.post('/messageStatusApi/excelDownloadWebSend', params, { headers: {"show-layer": "No", "activity":"READ"}, responseType: 'arraybuffer' }).then(function(response) {
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


const excelDownloadWebSendFail = (params) => {
    return httpClient.post('/messageStatusApi/excelDownloadWebSendFail', params, { headers: {"show-layer": "No", "activity":"READ"}, responseType: 'arraybuffer' }).then(function(response) {
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



const excelDownloadBookingSend = (params) => {
    return httpClient.post('/messageStatusApi/excelDownloadBookingSend', params, { headers: {"show-layer": "No", "activity":"READ"}, responseType: 'arraybuffer' }).then(function(response) {
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


const excelDownloadMoReceptionStatus = (params) => {
    return httpClient.post('/messageStatusApi/excelDownloadMoReceptionStatus', params, { headers: {"show-layer": "No", "activity":"READ"}, responseType: 'arraybuffer' }).then(function(response) {
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
    selectMessageStatusList,
    excelDownloadMessageStatus,
    selectMessageStatusDetail,
    selectWebSendList,
    selectWebSendFailList,
    selectBookingSendList,
    cancelBookingSend,
    excelDownloadWebSend,
    excelDownloadWebSendFail,
    excelDownloadBookingSend,
    selectMoReceptionStatusList,
    excelDownloadMoReceptionStatus,
    selectReceptionNumberList,
    selectConditionList
    
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