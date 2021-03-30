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


export default {
    selectProjectList,
    checkProjectNameDuplicate,
    saveProject
};