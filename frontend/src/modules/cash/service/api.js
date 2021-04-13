import httpClient from '@/common/http-client';

const insertWebCashInfo = (params) => {
    return httpClient.post('/projectApi/cash/insertWebCashInfo', params, { headers: {"show-layer": "Yes"} });
};

export default {
    insertWebCashInfo
};