import httpClient from '@/common/http-client';

const selectRcsBrandList = (params) => {
    return httpClient.post('/projectApi/channel/selectRcsBrandList', params, { headers: {"show-layer": "Yes"} });
};

export default {
    selectRcsBrandList
};