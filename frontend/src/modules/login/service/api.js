import httpClient from '@/common/http-client';

const login = (params) => {
    return httpClient.post('/api/auth/login', params, { headers: {"show-layer": "Yes"} });
};

const logout = () => {
    return httpClient.get('/api/auth/logout', {headers: {"show-layer": "Yes"}});
};

export default {
    login,
    logout
};