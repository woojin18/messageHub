import httpClient from '@/common/http-client';

const selectRcsBrandList = (params) => {
    return httpClient.post('/projectApi/channel/selectRcsBrandList', params, { headers: {"show-layer": "Yes"} });
};

const selectRcsRegTmpltList = (params) => {
    return httpClient.post('/projectApi/channel/selectRcsRegTmpltList', params, { headers: {"show-layer": "Yes"} });
};

export default {
    selectRcsBrandList,
    selectRcsRegTmpltList
};