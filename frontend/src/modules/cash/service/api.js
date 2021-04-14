import httpClient from '@/common/http-client';

const insertWebCashInfo = (params) => {
    return httpClient.post('/projectApi/cash/insertWebCashInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectCashHist = (params) => {
    return httpClient.post('/projectApi/cash/selectCashHist', params, { headers: {"show-layer": "Yes"} });
};

export default {
    insertWebCashInfo,
    selectCashHist
};