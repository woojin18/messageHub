import httpClient from '@/common/http-client';

const insertWebCashInfo = (params) => {
    return httpClient.post('/projectApi/cash/insertWebCashInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectCashHist = (params) => {
    return httpClient.post('/projectApi/cash/selectCashHist', params, { headers: {"show-layer": "Yes"} });
};

const selectCashBalance = (params) => {
    return httpClient.post('/projectApi/cash/selectCashBalance', params, { headers: { "show-layer": "Yes" } });
};

const selectUcubeInfo = (params) => {
    return httpClient.post('/projectApi/cash/selectUcubeInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectUcubePopInfo = params => {
  return httpClient.post('/projectApi/cash/selectUcubePopInfo', params, { headers: { 'show-layer': 'Yes' } });
};


const selectProjectInfo = (params) => {
    return httpClient.post('/projectApi/cash/selectProjectInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectProjectSubBillCode = (params) => {
    return httpClient.post('/projectApi/cash/selectProjectSubBillCode', params, { headers: {"show-layer": "Yes"} });
};

const insertUbubeInfo = (params) => {
    return httpClient.post('/projectApi/cash/insertUbubeInfo', params, { headers: {"show-layer": "Yes"} });
};

const updateProjectBillId = (params) => {
    return httpClient.post('/projectApi/cash/updateProjectBillId', params, { headers: {"show-layer": "Yes"} });
};

const saveProjectSubBillCode = (params) => {
    return httpClient.post('/projectApi/cash/saveProjectSubBillCode', params, { headers: {"show-layer": "Yes"} });
};

const deleteProjectSubBillCode = (params) => {
    return httpClient.post('/projectApi/cash/deleteProjectSubBillCode', params, { headers: {"show-layer": "Yes"} });
};

export default {
    insertWebCashInfo,
    selectCashHist,
    selectCashBalance,
    selectUcubeInfo,
    selectUcubePopInfo,
    selectProjectInfo,
    selectProjectSubBillCode,
    insertUbubeInfo,
    updateProjectBillId,
    saveProjectSubBillCode,
    deleteProjectSubBillCode
};