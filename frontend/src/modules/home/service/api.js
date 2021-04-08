import httpClient from '@/common/http-client';

const createdTest = () => {
    return httpClient.get('/api/auth/logout', {headers: {"show-layer": "Yes"}});
};

export default {
    createdTest
};