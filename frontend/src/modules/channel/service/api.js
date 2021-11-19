import httpClient from '@/common/http-client';

const selectRcsBrandList = (params) => {
    return httpClient.post('/projectApi/channel/selectRcsBrandList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectRcsRegTmpltList = (params) => {
    return httpClient.post('/projectApi/channel/selectRcsRegTmpltList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectRcsCallbackList = (params) => {
    return httpClient.post('/projectApi/channel/selectRcsCallbackList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const checkApiKey = (params) => {
    return httpClient.post('/projectApi/channel/checkApiKey', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const fileUploadToApi = (params) => {
    return httpClient.post('/projectApi/channel/fileUploadToApi', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const saveRcsBrandReqForApi = (params) => {
    return httpClient.post('/projectApi/channel/saveRcsBrandReqForApi', params, { headers: { "show-layer": "Yes", "activity":"SAVE"} });
};

const deleteCallbackForApi = (params) => {
    return httpClient.post('/projectApi/channel/deleteRcsBrandForApi', params, { headers: { "show-layer": "Yes", "activity":"SAVE"} });
};

const selectPushManageList = (params) => {
    return httpClient.post('/projectApi/channel/selectPushManageList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const deletePushManage = (params) => {
    return httpClient.post('/projectApi/channel/deletePushManage', params, { headers: { "show-layer": "Yes", "activity":"SAVE"} });
};

const selectRcsBrandMsgBaseList = (params) => {
    return httpClient.post('/projectApi/channel/selectRcsBrandMsgBaseList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectMoCallbackList = (params) => {
    return httpClient.post('/projectApi/channel/selectMoCallbackList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveMoCallback = (params) => {
    return httpClient.post('/projectApi/channel/saveMoCallback', params, { headers: { "show-layer": "Yes", "activity":"SAVE"} });
};

const getKkoCategory = (params) => {
    return httpClient.post('/projectApi/channel/getKkoCategory', params, { headers: { "show-layer": "Yes", "activity": "SAVE" } });
};

const selectKkoCh = (params) => {
    return httpClient.post('/projectApi/channel/selectKkoCh', params, { headers: { "show-layer": "Yes", "activity": "SAVE" } });
};

const selectKkoChGroup = (params) => {
    return httpClient.post('/projectApi/channel/selectKkoChGroup', params, { headers: { "show-layer": "Yes", "activity": "SAVE" } });
};

const saveKkoChForApi = (params) => {
    return httpClient.post('/projectApi/channel/saveKkoChForApi', params, { headers: { "show-layer": "Yes", "activity": "SAVE" } });
};

const chkEqualKakaoChannel = (params) => {
    return httpClient.post('/projectApi/channel/chkEqualKakaoChannel', params, { headers: { "show-layer": "Yes", "activity": "SAVE" } });
};

const getSenderKeyToken = (params) => {
    return httpClient.post('/projectApi/channel/getSenderKeyToken', params, { headers: { "show-layer": "Yes", "activity": "SAVE" } });
};
const getApiKeyListForKko = (params) => {
    return httpClient.post('/projectApi/channel/getApiKeyListForKko', params, { headers: { "show-layer": "Yes", "activity": "SAVE" } });
};
const saveKkoChGroupForApi = (params) => {
    return httpClient.post('/projectApi/channel/saveKkoChGroupForApi', params, { headers: { "show-layer": "Yes", "activity": "SAVE" } });
};

const findApiKeyFromProject = (params) => {
    return httpClient.post('/projectApi/channel/findApiKeyFromProject', params, { headers: { "show-layer": "Yes", "activity": "SAVE" } });
};

const saveKkoChRecover = (params) => {
    return httpClient.post('/projectApi/channel/saveKkoChRecover', params, { headers: { "show-layer": "Yes", "activity": "SAVE" } });
};

const checkWebhookUrl = (params) => {
    return httpClient.post('/projectApi/channel/checkWebhookUrl', params, { headers: { "show-layer": "Yes", "activity": "SAVE" } });
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
    saveMoCallback,
    getKkoCategory,
    selectKkoCh,
    selectKkoChGroup,
    saveKkoChForApi,
    chkEqualKakaoChannel,
    getSenderKeyToken,
    getApiKeyListForKko,
    saveKkoChGroupForApi,
    findApiKeyFromProject,
    saveKkoChRecover,
    checkWebhookUrl
};