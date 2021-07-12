import httpClient from '@/common/http-client';

const selectMemberInfo = (params) => {
    return httpClient.post('/api/myPage/selectMemberInfo', params, { headers: {"show-layer": "Yes"} });
};

const saveMemberInfo = (params) => {
    return httpClient.post('/api/myPage/saveMemberInfo', params, { headers: {"show-layer": "Yes"} });
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

const checkPassword = (params) => {
    return httpClient.post('/api/myPage/checkPassword', params, { headers: {"show-layer": "Yes"} });
};

const selectCorpInfo = (params) => {
    return httpClient.post('/api/myPage/selectCorpInfo', params, { headers: {"show-layer": "Yes"} });
};

const saveCorpInfo = (params) => {
    return httpClient.post('/api/myPage/saveCorpInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectProejctList = () => {
    return httpClient.post('/api/myPage/selectProejctList', { headers: {"show-layer": "Yes"} });
};

const downloadRegCardImage = (params) => {
    return httpClient.post('/api/myPage/downloadRegCardImage', params, { headers: {"show-layer": "Yes"}, responseType: 'arraybuffer' })
    .then((response) => fnDownCallback(response));
  };

export default {
    selectMemberInfo,
    saveMemberInfo,
    selectQnaList,
    saveQnaInfo,
    deleteQnaInfo,
    checkPassword,
    selectCorpInfo,
    saveCorpInfo,
    selectProejctList,
    downloadRegCardImage
};