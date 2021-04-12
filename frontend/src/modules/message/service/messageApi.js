import httpClient from '@/common/http-client';

const apiTest = (params) => {
    return httpClient.post('/api/public/sendMessage/apiTest', params, { headers: {"show-layer": "Yes"} });
};
const selectAppIdList = (params) => {
    return httpClient.post('/api/public/sendMessage/selectAppIdList', params, { headers: {"show-layer": "Yes"} });
};
const selectCallbackList = (params) => {
    return httpClient.post('/api/public/sendMessage/selectCallbackList', params, { headers: {"show-layer": "Yes"} });
};

export default {
    apiTest,
    selectAppIdList,
    selectCallbackList,
};