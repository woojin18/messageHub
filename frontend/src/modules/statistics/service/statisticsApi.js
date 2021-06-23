import httpClient from '@/common/http-client';

const selectSendByList = (params) => {
	return httpClient.post('/statisticsApi/list/selectSendByList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectSendByCntList = (params) => {
	return httpClient.post('/statisticsApi/list/selectSendByCntList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

export default {
	selectSendByList,
	selectSendByCntList,
};