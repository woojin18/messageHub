import httpClient from '@/common/http-client';

const selectIntegratedSendList = (params) => {
    return httpClient.post('/integratedSendApi/selectIntegratedSendList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const sendIntegratedMessage = (params) => {
  return httpClient.post('/integratedSendApi/sendIntegratedMessage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data", "activity":"READ"} });
};

export default {
    selectIntegratedSendList,
    sendIntegratedMessage
};