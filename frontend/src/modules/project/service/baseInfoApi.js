import httpClient from '@/common/http-client';

const selectProjectBaseInfo = (params) => {
	return httpClient.post('/baseInfoApi/manage/selectProjectBaseInfo', params, { headers: {"show-layer": "Yes"} });
};

const saveProjectBaseInfo = (params) => {
	return httpClient.post('/baseInfoApi/manage/saveProjectBaseInfo', params, { headers: {"show-layer": "Yes"} });
};

const selectApiKeyList = (params) => {
	return httpClient.post('/baseInfoApi/manage/selectApiKeyList', params, { headers: {"show-layer": "Yes"} });
};

const saveApiKey = (params) => {
	return httpClient.post('/baseInfoApi/manage/saveApiKey', params, { headers: {"show-layer": "Yes"} });
};

export default {
	selectProjectBaseInfo,
	saveProjectBaseInfo,
	selectApiKeyList,
	saveApiKey,
};