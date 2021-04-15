import httpClient from '@/common/http-client';

const selectProjectList = (params) => {
    return httpClient.post('/projectApi/manage/selectProjectList', params, { headers: {"show-layer": "Yes"} });
};

const checkProjectNameDuplicate = (params) => {
    return httpClient.post('/projectApi/manage/checkProjectNameDuplicate', params, { headers: {"show-layer": "Yes"} });
};

const saveProject = (params) => {
    return httpClient.post('/projectApi/manage/saveProject', params, { headers: {"show-layer": "Yes"} });
};

const checkPreRegYn = (params) => {
    return httpClient.post('/projectApi/manage/checkPreRegYn', params, { headers: {"show-layer": "Yes"} });
};

const downloadFile = (params) => {
    return httpClient.post('/api/public/common/uploadFile', params, { headers: {"show-layer": "No"}, responseType: 'arraybuffer' });
};

const savePreRegEx = (params) => {
    return httpClient.post('/projectApi/manage/savePreRegEx', params, { headers: {"show-layer": "No"}});
};

export default {
    selectProjectList,
    checkProjectNameDuplicate,
    saveProject,
    checkPreRegYn,
    downloadFile,
    savePreRegEx
};