import httpClient from '@/common/http-client';

const selectAppIdList = (params) => {
    return httpClient.post('/api/public/sendMessage/selectAppIdList', params, { headers: {"show-layer": "Yes"} });
};

export default {
    selectAppIdList
};