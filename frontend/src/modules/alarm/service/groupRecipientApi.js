import httpClient from '@/common/http-client';

const selectGroupRecipientList = (params) => {
    return httpClient.post('/api/alarm/selectGroupRecipientList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveGroupRecipient = (params) => {
    return httpClient.post('/api/alarm/saveGroupRecipient', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const delGroupRecipient = (params) => {
    return httpClient.post('/api/alarm/delGroupRecipient', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

export default {
    selectGroupRecipientList,
    saveGroupRecipient,
    delGroupRecipient
};