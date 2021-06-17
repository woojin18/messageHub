import httpClient from '@/common/http-client';

const selectProjectList = (params) => {
    return httpClient.post('/projectApi/manage/selectProjectList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const checkProjectNameDuplicate = (params) => {
    return httpClient.post('/projectApi/manage/checkProjectNameDuplicate', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveProject = (params) => {
    return httpClient.post('/projectApi/manage/saveProject', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const checkPreRegYn = (params) => {
    return httpClient.post('/projectApi/manage/checkPreRegYn', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const downloadFile = (params) => {
    return httpClient.post('/commonApi/uploadFile', params, { headers: {"show-layer": "No"}, responseType: 'arraybuffer' });
};

const savePreRegEx = (params) => {
    return httpClient.post('/projectApi/manage/savePreRegEx', params, { headers: { "show-layer": "No", "activity": "SAVE"}});
};

const selectCallbackManageList = (params) => {
    return httpClient.post('/projectApi/manage/selectCallbackManageList', params, { headers: { "show-layer": "No", "activity": "READ"}});
};

const updateCallbackForApi = (params) => {
    return httpClient.post('/projectApi/manage/updateCallbackForApi', params, { headers: { "show-layer": "No", "activity": "SAVE"}});
};

const deleteCallbackForApi = (params) => {
    return httpClient.post('/projectApi/manage/deleteCallbackForApi', params, { headers: { "show-layer": "No", "activity": "SAVE"}});
};

const selectApprovalBrandList = (params) => {
    return httpClient.post('/projectApi/manage/selectApprovalBrandList', params, { headers: { "show-layer": "No", "activity": "READ"}});
};

const saveDisRatio= (params) => {
    return httpClient.post('/projectApi/manage/saveDisRatio', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectBasicDisRatio= (params) => {
    return httpClient.post('/projectApi/manage/selectBasicDisRatio', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectDisRatio= (params) => {
    return httpClient.post('/projectApi/manage/selectDisRatio', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

export default {
    selectProjectList,
    checkProjectNameDuplicate,
    saveProject,
    checkPreRegYn,
    downloadFile,
    savePreRegEx,
    selectCallbackManageList,
    updateCallbackForApi,
    deleteCallbackForApi,
    selectApprovalBrandList,
    saveDisRatio,
    selectBasicDisRatio,
    selectDisRatio,
};