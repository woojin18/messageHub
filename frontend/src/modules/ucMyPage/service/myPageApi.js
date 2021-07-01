import httpClient from '@/common/http-client';

const selectMemberInfo = (params) => {
    return httpClient.post('/api/myPage/selectMemberInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectQnaList = (params) => {
    return httpClient.post('/api/myPage/selectQnaList', params, { headers: {"show-layer": "Yes"} });
};

const saveQnaInfo = (params) => {
    return httpClient.post('/api/myPage/saveQnaInfo', params, { headers: {"show-layer": "Yes"} });
};

const deleteQnaInfo = (params) => {
    return httpClient.post('/api/myPage/deleteQnaInfo', params, { headers: {"show-layer": "Yes"} });
};

export default {
    selectMemberInfo,
    selectQnaList,
    saveQnaInfo,
    deleteQnaInfo
};