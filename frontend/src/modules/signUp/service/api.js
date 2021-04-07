import httpClient from '@/common/http-client';

const signUpRedirect = (params) => {
    return httpClient.post('/api/auth/login', params, { headers: {"show-layer": "Yes"} });
};

export default {
    signUpRedirect
};