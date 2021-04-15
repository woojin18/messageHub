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
const selectAddressList = (params) => {
    return httpClient.post('/api/public/sendMessage/selectAddressList', params, { headers: {"show-layer": "Yes"} });
};
const selectCmCuList = (params) => {
    return httpClient.post('/api/public/sendMessage/selectCmCuList', params, { headers: {"show-layer": "Yes"} });
};

export default {
    apiTest,
    selectAppIdList,
    selectCallbackList,
    selectAddressList,
    selectCmCuList,
};