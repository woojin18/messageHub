import httpClient from '@/common/http-client';

const selectMemberInfo = (params) => {
    return httpClient.post('/api/myPage/selectMemberInfo', params, { headers: {"show-layer": "Yes"} });
};

const saveMemberInfo = (params) => {
    return httpClient.post('/api/myPage/saveMemberInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectQnaList = (params) => {
    return httpClient.post('/api/myPage/selectQnaList', params, { headers: {"show-layer": "Yes"} });
};

const saveQnaInfo = (params) => {
    return httpClient.post('/api/myPage/saveQnaInfo', params, { headers: {"show-layer": "Yes"} });
};

const deleteQnaInfo = (params) => {
    return httpClient.post('/api/myPage/deleteQnaInfo', params, { headers: {"show-layer": "Yes"} });
};

const checkPassword = (params) => {
    return httpClient.post('/api/myPage/checkPassword', params, { headers: {"show-layer": "Yes"} });
};

const selectCorpInfo = (params) => {
    return httpClient.post('/api/myPage/selectCorpInfo', params, { headers: {"show-layer": "Yes"} });
};

const saveCorpInfo = (params) => {
    return httpClient.post('/api/myPage/saveCorpInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectProejctList = () => {
    return httpClient.post('/api/myPage/selectProejctList', { headers: {"show-layer": "Yes"} });
};

const downloadRegCardImage = (params) => {
    return httpClient.post('/api/myPage/downloadRegCardImage', params, { headers: {"show-layer": "Yes"}, responseType: 'arraybuffer' })
    .then((response) => fnDownCallback(response));
  };

export default {
    selectMemberInfo,
    saveMemberInfo,
    selectQnaList,
    saveQnaInfo,
    deleteQnaInfo,
    checkPassword,
    selectCorpInfo,
    saveCorpInfo,
    selectProejctList,
    downloadRegCardImage
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