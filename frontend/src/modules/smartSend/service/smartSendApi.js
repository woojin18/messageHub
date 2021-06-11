import httpClient from '@/common/http-client';

const selectSmartSendList = (params) => {
    return httpClient.post('/smartSendApi/selectSmartSendList', params, { headers: {"show-layer": "Yes"} });
};

const sendSmartMessage = (params) => {
  return httpClient.post('/smartSendApi/sendSmartMessage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data"} });
};

export default {
    selectSmartSendList,
    sendSmartMessage
};