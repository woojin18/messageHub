import httpClient from '@/common/http-client';

const selectProjectBaseInfo = (params) => {
	return httpClient.post('/baseInfoApi/manage/selectProjectBaseInfo', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveProjectBaseInfo = (params) => {
	return httpClient.post('/baseInfoApi/manage/saveProjectBaseInfo', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const selectApiKeyList = (params) => {
	return httpClient.post('/baseInfoApi/manage/selectApiKeyList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveApiKey = (params) => {
	return httpClient.post('/baseInfoApi/manage/saveApiKey', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

export default {
	selectProjectBaseInfo,
	saveProjectBaseInfo,
	selectApiKeyList,
	saveApiKey,
};