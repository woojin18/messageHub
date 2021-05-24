import httpClient from '@/common/http-client';

const selectMemberInfo = (params) => {
    return httpClient.post('/api/public/myPage/selectMemberInfo', params, { headers: {"show-layer": "Yes"} });
};

const saveMemberInfo = (params) => {
    return httpClient.post('/api/public/myPage/saveMemberInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectQnaList = (params) => {
    return httpClient.post('/api/public/myPage/selectQnaList', params, { headers: {"show-layer": "Yes"} });
};

const saveQnaInfo = (params) => {
    return httpClient.post('/api/public/myPage/saveQnaInfo', params, { headers: {"show-layer": "Yes"} });
};

const deleteQnaInfo = (params) => {
    return httpClient.post('/api/public/myPage/deleteQnaInfo', params, { headers: {"show-layer": "Yes"} });
};

const checkPassword = (params) => {
    return httpClient.post('/api/public/myPage/checkPassword', params, { headers: {"show-layer": "Yes"} });
};

const selectCorpInfo = (params) => {
    return httpClient.post('/api/public/myPage/selectCorpInfo', params, { headers: {"show-layer": "Yes"} });
};

const saveCorpInfo = (params) => {
    return httpClient.post('/api/public/myPage/saveCorpInfo', params, { headers: {"show-layer": "Yes"} });
};

export default {
    selectMemberInfo,
    saveMemberInfo,
    selectQnaList,
    saveQnaInfo,
    deleteQnaInfo,
    checkPassword,
    selectCorpInfo,
    saveCorpInfo
};