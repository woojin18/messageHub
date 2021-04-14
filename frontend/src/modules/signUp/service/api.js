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

const selectUseTermsPreVersion = () => {
    return httpClient.post('/api/public/signUp/selectUseTermsPreVersion', params, { headers: {"show-layer": "Yes"} });
}

export default {
    signUpRedirect,
    domainChk,
    insertSignUp,
    insertEmailUser,
    selectUseTerms,
    selectUseTermsPreVersion
};