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
export default {
    selectMemberInfo,
    saveMemberInfo,
    selectQnaList
};