import httpClient from '@/common/http-client';

const rcsTemplatePopInit = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/rcsTemplatePopInit', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const rcsTemplateSearch = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/rcsTemplateSearch', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const rcsTemplateContent = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/rcsTemplateContent', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const rcsTemplateDetail = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/rcsTemplateDetail', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectCallbackList = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/selectCallbackList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const rcsMsgSave = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/rcsMsgSave', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const selectRcsMsgList = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/selectRcsMsgList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const deleteRcsTmpMsgbase = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/deleteRcsTmpMsgbase', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectRcsMsgDetail = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/selectRcsMsgDetail', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const sendRcsData = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/sendRcsData', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};



export default {
    rcsTemplatePopInit,
    rcsTemplateSearch,
    rcsTemplateContent,
    rcsTemplateDetail,
    selectCallbackList,
    rcsMsgSave,
    selectRcsMsgList,
    deleteRcsTmpMsgbase,
    selectRcsMsgDetail,
    sendRcsData
};
