import httpClient from '@/common/http-client';

const selectSmartSendList = (params) => {
    return httpClient.post('/smartSendApi/selectSmartSendList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const smartSendMessage = (params) => {
  return httpClient.post('/smartSendApi/smartSendMessage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data", "activity":"SAVE"} });
};

export default {
    selectSmartSendList,
    smartSendMessage
};