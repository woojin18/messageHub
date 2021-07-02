import httpClient from '@/common/http-client';

const logout = () => {
	return httpClient.get('/api/auth/logout', { headers: { "show-layer": "Yes" } });
};

const selectProjectList = (params) => {
	return httpClient.post('/api/home/selectProjectList', params, { headers: { "show-layer": "Yes" } });
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

const selectMonthUsedTimeLineList = (params) => {
	return httpClient.post('/api/home/selectMonthUsedTimeLineList', params, { headers: { "show-layer": "Yes" } });
};

const selectMonthUsedDataList = (params) => {
	return httpClient.post('/api/home/selectMonthUsedDataList', params, { headers: { "show-layer": "Yes" } });
};

const selectSixMonthUsedTimeLineList = (params) => {
	return httpClient.post('/api/home/selectSixMonthUsedTimeLineList', params, { headers: { "show-layer": "Yes" } });
};

const selectSixMonthUsedDataList = (params) => {
	return httpClient.post('/api/home/selectSixMonthUsedDataList', params, { headers: { "show-layer": "Yes" } });
};

export default {
	logout,
	selectProjectList,
	selectNoticeList,
	selectChTotCntInfo,
	selectChSuccFailCntList,
	selectChFailCodeList,
	selectMonthUsedTimeLineList,
	selectMonthUsedDataList,
	selectSixMonthUsedTimeLineList,
	selectSixMonthUsedDataList
};