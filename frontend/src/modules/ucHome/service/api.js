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

const selectChTotCntInfo = (params) => {
	return httpClient.post('/api/home/selectChTotCntInfo', params, { headers: { "show-layer": "Yes" } });
};

const selectChSuccFailCntList = (params) => {
	return httpClient.post('/api/home/selectChSuccFailCntList', params, { headers: { "show-layer": "Yes" } });
};

const selectChFailCodeList = (params) => {
	return httpClient.post('/api/home/selectChFailCodeList', params, { headers: { "show-layer": "Yes" } });
};

const selectDayStatsList = (params) => {
	return httpClient.post('/api/home/selectDayStatsList', params, { headers: { "show-layer": "Yes" } });
};

export default {
	logout,
	selectProjectInfo,
	selectNoticeList,
	selectChTotCntInfo,
	selectChSuccFailCntList,
	selectChFailCodeList,
	selectDayStatsList
};