import httpClient from '@/common/http-client';

const selectProjectList = (params) => {
    return httpClient.post('/projectApi/manage/selectProjectList', params, { headers: {"show-layer": "Yes"} });
};

export default {
    selectProjectList
};