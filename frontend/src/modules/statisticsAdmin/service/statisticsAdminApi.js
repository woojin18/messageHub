import httpClient from '@/common/http-client';

const selectStatisList = (params) => {
	return httpClient.post('/statisticsAdminApi/list/selectStatisList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectProjectList = (params) => {
	return httpClient.post('/statisticsAdminApi/list/selectProjectList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

export default {
	selectStatisList,
	selectProjectList,
};
