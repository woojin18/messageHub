import httpClient from '@/common/http-client';

const logout = () => {
	return httpClient.get('/api/auth/logout', { headers: { "show-layer": "Yes" } });
};

const selectProjectInfo = (params) => {
	return httpClient.post('/api/home/selectProjectInfo', params, { headers: { "show-layer": "Yes" } });
};

const selectNoticeList = (params) => {
	return httpClient.post('/api/home/selectNoticeList', params, { headers: { "show-layer": "Yes" } });
};

export default {
	logout,
	selectProjectInfo,
	selectNoticeList
};