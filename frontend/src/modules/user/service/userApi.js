import httpClient from '@/common/http-client';

const selectUserList = (params) => {
	return httpClient.post('/userApi/manage/selectUserList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const stopUser = (params) => {
	return httpClient.post('/userApi/manage/stopUser', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const releaseUser = (params) => {
	return httpClient.post('/userApi/manage/releaseUser', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const deleteUser = (params) => {
	return httpClient.post('/userApi/manage/deleteUser', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const modifyUser = (params) => {
	return httpClient.post('/userApi/manage/modifyUser', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const registerUser = (params) => {
	return httpClient.post('/userApi/manage/registerUser', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const checkDupcUser = (params) => {
	return httpClient.post('/userApi/manage/checkDupcUser', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const sendCertifyMail = (params) => {
	return httpClient.post('/userApi/manage/sendCertifyMail', params, { headers: {"show-layer": "Yes"} });
};

const selectRoleList = (params) => {
	return httpClient.post('/userApi/manage/selectRoleList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

export default {
	selectUserList,
	stopUser,
	releaseUser,
	deleteUser,
	modifyUser,
	registerUser,
	checkDupcUser,
    sendCertifyMail,
	selectRoleList
};