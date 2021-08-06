import httpClient from '@/common/http-client';

const selectGroupRecipientList = (params) => {
    return httpClient.post('/api/alarm/selectGroupRecipientList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectGroupRecipientList1 = (params) => {
    return httpClient.post('/api/alarm/selectGroupRecipientList1', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveGroupRecipient = (params) => {
    return httpClient.post('/api/alarm/saveGroupRecipient', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const delGroupRecipient = (params) => {
    return httpClient.post('/api/alarm/delGroupRecipient', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

export default {
    selectGroupRecipientList,
    selectGroupRecipientList1,
    saveGroupRecipient,
    delGroupRecipient
};