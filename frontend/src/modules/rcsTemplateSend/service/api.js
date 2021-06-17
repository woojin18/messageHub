import httpClient from '@/common/http-client';

/* PUSH */
const rcsTemplatePopInit = (params) => {
    return httpClient.post('/uc/rcsTemplateSend/rcsTemplatePopInit', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

export default {
    rcsTemplatePopInit
};
