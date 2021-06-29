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
}


export default {
    rcsTemplatePopInit,
    rcsTemplateSearch,
    rcsTemplateContent,
    rcsTemplateDetail,
    selectCallbackList
};
