import httpClient from '@/common/http-client';

const selectProjectBaseInfo = (params) => {
	return httpClient.post('/baseInfoApi/manage/selectProjectBaseInfo', params, { headers: {"show-layer": "Yes"} });
};

const saveProjectBaseInfo = (params) => {
	return httpClient.post('/baseInfoApi/manage/saveProjectBaseInfo', params, { headers: {"show-layer": "Yes"} });
};

export default {
	selectProjectBaseInfo,
	saveProjectBaseInfo,
};