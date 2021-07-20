import httpClient from '@/common/http-client';

const selectRecipientList = (params) => {
    return httpClient.post('/api/alarm/selectRecipientList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectRecipientList2 = (params) => {
    return httpClient.post('/api/alarm/selectRecipientList2', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveRecipient = (params) => {
    return httpClient.post('/api/alarm/saveRecipient', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const delRecipient = (params) => {
    return httpClient.post('/api/alarm/delRecipient', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

export default {
    selectRecipientList,
    selectRecipientList2,
    saveRecipient,
    delRecipient
};