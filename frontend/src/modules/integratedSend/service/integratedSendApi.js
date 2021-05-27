import httpClient from '@/common/http-client';

const selectIntegratedSendList = (params) => {
    return httpClient.post('/integratedSendApi/selectIntegratedSendList', params, { headers: {"show-layer": "Yes"} });
};

const integratedSendInfo = (params) => {
    return httpClient.post('/integratedSendApi/selectIntegratedSendInfo', params, { headers: {"show-layer": "Yes"} });
};

const insertIntegratedSend = (params) => {
    return httpClient.post('/integratedSendApi/insertIntegratedSend', params, { headers: {"show-layer": "Yes"} });
};

const updateIntegratedSend = (params) => {
    return httpClient.post('/integratedSendApi/updateIntegratedSend', params, { headers: {"show-layer": "Yes"} });
};

const deleteIntegratedSend = (params) => {
    return httpClient.post('/integratedSendApi/deleteIntegratedSend', params, { headers: {"show-layer": "Yes"} });
};

export default {
    selectIntegratedSendList,
    integratedSendInfo,
    insertIntegratedSend,
    updateIntegratedSend,
    deleteIntegratedSend
};