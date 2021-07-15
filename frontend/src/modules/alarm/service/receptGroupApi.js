import httpClient from '@/common/http-client';

const selectReceptGroupList = (params) => {
    return httpClient.post('/api/noti/selectReceptGroupList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectReceptGroupList2 = (params) => {
    return httpClient.post('/api/noti/selectReceptGroupList2', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectReceptGroupList3 = (params) => {
    return httpClient.post('/api/noti/selectReceptGroupList3', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectReceptGroupList4 = (params) => {
    return httpClient.post('/api/noti/selectReceptGroupList4', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveReceptGroup = (params) => {
    return httpClient.post('/api/noti/saveReceptGroup', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const delReceptGroup = (params) => {
    return httpClient.post('/api/noti/delReceptGroup', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

export default {
    selectReceptGroupList,
    selectReceptGroupList2,
    selectReceptGroupList3,
    selectReceptGroupList4,
    saveReceptGroup,
    delReceptGroup
};