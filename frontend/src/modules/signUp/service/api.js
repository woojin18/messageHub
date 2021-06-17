import httpClient from '@/common/http-client';

const signUpRedirect = (params) => {
    return httpClient.post('/api/auth/login', params, { headers: {"show-layer": "Yes"} });
};

const domainChk = (params) => {
    return httpClient.post('/api/public/signUp/domainChk', params, { headers: {"show-layer": "Yes"} });
};

const insertEmailUser = (params) => {
    return httpClient.post('/api/public/signUp/insertEmailUser', params, { headers: {"show-layer": "Yes"} });
};

const insertSignUp = (params) => {
    return httpClient.post('/api/public/signUp/insertSignUp', params, { headers: {"show-layer": "Yes"} });
};

const selectUseTerms = (params) => {
    return httpClient.post('/api/public/signUp/selectUseTerms', params, { headers: {"show-layer": "Yes"} });
};

const selectUseTermsPreVersion = (params) => {
    return httpClient.post('/api/public/signUp/selectUseTermsPreVersion', params, { headers: {"show-layer": "Yes"} });
};

const apiTest = () => {
    return httpClient.post('/api/public/signUp/apiTest', { headers: {"show-layer": "Yes"} });
}

const getNiceCheck = () => {
    return httpClient.post('/api/public/signUp/getNiceCheck', { headers: {"show-layer": "Yes"} });
}

const getNiceCheckSuccess = (params) => {
    return httpClient.post('/api/public/signUp/getNiceCheckSuccess', params, { headers: {"show-layer": "Yes"} });
}

const getNiceCheckFail = (params) => {
    return httpClient.post('/api/public/signUp/getNiceCheckFail', params, { headers: {"show-layer": "Yes"} });
}

export default {
    signUpRedirect,
    domainChk,
    insertSignUp,
    insertEmailUser,
    selectUseTerms,
    selectUseTermsPreVersion,
    apiTest,
    getNiceCheck,
    getNiceCheckSuccess,
    getNiceCheckFail
};