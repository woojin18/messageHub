import httpClient from '@/common/http-client';

const selectIntegratedSendList = (params) => {
    return httpClient.post('/integratedSendApi/selectIntegratedSendList', params, { headers: {"show-layer": "Yes"} });
};

const sendIntegratedMessage = (params) => {
  return httpClient.post('/integratedSendApi/sendIntegratedMessage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data"} });
};

export default {
    selectIntegratedSendList,
    sendIntegratedMessage
};