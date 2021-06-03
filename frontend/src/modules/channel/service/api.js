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

const deleteCallbackForApi = (params) => {
    return httpClient.post('/projectApi/channel/deleteRcsBrandForApi', params, { headers: {"show-layer": "Yes"} });
};

const selectPushManageList = (params) => {
    return httpClient.post('/projectApi/channel/selectPushManageList', params, { headers: {"show-layer": "Yes"} });
};

const deletePushManage = (params) => {
    return httpClient.post('/projectApi/channel/deletePushManage', params, { headers: {"show-layer": "Yes"} });
};

const selectRcsBrandMsgBaseList = (params) => {
    return httpClient.post('/projectApi/channel/selectRcsBrandMsgBaseList', params, { headers: {"show-layer": "Yes"} });
};

const selectMoCallbackList = (params) => {
    return httpClient.post('/projectApi/channel/selectMoCallbackList', params, { headers: {"show-layer": "Yes"} });
};

const saveMoCallback = (params) => {
    return httpClient.post('/projectApi/channel/saveMoCallback', params, { headers: {"show-layer": "Yes"} });
};

export default {
    selectRcsBrandList,
    selectRcsRegTmpltList,
    selectRcsCallbackList,
    checkApiKey,
    saveRcsBrandReqForApi,
    deleteCallbackForApi,
    fileUploadToApi,
    selectPushManageList,
    deletePushManage,
    selectRcsBrandMsgBaseList,
    selectMoCallbackList,
    saveMoCallback
};