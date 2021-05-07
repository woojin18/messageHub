import httpClient from '@/common/http-client';

const selectRcsBrandList = (params) => {
    return httpClient.post('/projectApi/channel/selectRcsBrandList', params, { headers: {"show-layer": "Yes"} });
};

const selectRcsRegTmpltList = (params) => {
    return httpClient.post('/projectApi/channel/selectRcsRegTmpltList', params, { headers: {"show-layer": "Yes"} });
};

const selectRcsCallbackList = (params) => {
    return httpClient.post('/projectApi/channel/selectRcsCallbackList', params, { headers: {"show-layer": "Yes"} });
};

const checkApiKey = (params) => {
    return httpClient.post('/projectApi/channel/checkApiKey', params, { headers: {"show-layer": "Yes"} });
};

const fileUploadToApi = (params) => {
    return httpClient.post('/projectApi/channel/fileUploadToApi', params, { headers: {"show-layer": "Yes"} });
};

const saveRcsBrandReqForApi = (params) => {
    return httpClient.post('/projectApi/channel/saveRcsBrandReqForApi', params, { headers: {"show-layer": "Yes"} });
};

const selectPushManageList = (params) => {
    return httpClient.post('/projectApi/channel/selectPushManageList', params, { headers: {"show-layer": "Yes"} });
};

const deletePushManage = (params) => {
    return httpClient.post('/projectApi/channel/deletePushManage', params, { headers: {"show-layer": "Yes"} });
};


export default {
    selectRcsBrandList,
    selectRcsRegTmpltList,
    selectRcsCallbackList,
    checkApiKey,
    saveRcsBrandReqForApi,
    fileUploadToApi,
    selectPushManageList,
    deletePushManage
};