import httpClient from '@/common/http-client';

const login = (params) => {
    return httpClient.post('/api/auth/login', params, { headers: {"show-layer": "Yes"} });
};

const logout = () => {
    return httpClient.get('/api/auth/logout', { headers: {"show-layer": "Yes"} });
};

const getMenuForRole = (params) => {
    return httpClient.post('/api/auth/getMenuForRole', params, { headers: {"show-layer": "Yes"} });
};

const getProjectForUser = (params) => {
	return httpClient.post('/api/auth/getProjectForUser', params, { headers: {"show-layer": "Yes"} });
};

const getProjectChUseListForUser = (params) => {
	return httpClient.post('/api/auth/getProjectChUseListForUser', params, { headers: {"show-layer": "Yes"} });
};

const updatePassword = (params) => {
	return httpClient.post('/api/public/updatePassword', params, { headers: {"show-layer": "Yes"} });
};

const findLoginId = (params) => {
	return httpClient.post('/api/public/findLoginId', params, { headers: {"show-layer": "Yes"} });
};

export default {
    login,
    logout,
    getMenuForRole,
	getProjectForUser,
    getProjectChUseListForUser,
    updatePassword,
    findLoginId
};