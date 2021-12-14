import httpClient from '@/common/http-client';

const logout = () => {
	return httpClient.get('/api/auth/logout', { headers: { "show-layer": "Yes" } });
};

const selectProjectInfo = (params) => {
	return httpClient.post('/api/home/selectProjectInfo', params, { headers: { "show-layer": "Yes" } });
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

const selectRtUsedTimeLineList = (params) => {
	return httpClient.post('/api/home/selectRtUsedTimeLineList', params, { headers: { "show-layer": "Yes" } });
};

const selectRtUsedDataList = (params) => {
	return httpClient.post('/api/home/selectRtUsedDataList', params, { headers: { "show-layer": "Yes" } });
};

const selectChMinList = (params) => {
	return httpClient.post('/api/home/selectChMinList', params, { headers: { "show-layer": "Yes" } });
};

export default {
	logout,
	selectProjectInfo,
	selectProjectList,
	selectNoticeList,
	selectChTotCntInfo,
	selectChSuccFailCntList,
	selectChFailCodeList,
	selectRtUsedTimeLineList,
	selectRtUsedDataList,
	selectChMinList
};