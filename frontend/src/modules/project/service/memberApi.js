import httpClient from '@/common/http-client';

const selectProjectMemberList = (params) => {
	return httpClient.post('/memberApi/manage/selectProjectMemberList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveProjectMember = (params) => {
	return httpClient.post('/memberApi/manage/saveProjectMember', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const deleteProjectMember = (params) => {
	return httpClient.post('/memberApi/manage/deleteProjectMember', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const selectUserList = (params) => {
	return httpClient.post('/memberApi/manage/selectUserList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const registerMember = (params) => {
	return httpClient.post('/memberApi/manage/registerMember', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

export default {
	selectProjectMemberList,
	saveProjectMember,
	deleteProjectMember,
	selectUserList,
	registerMember,
};