import httpClient from '@/common/http-client';

const selectUserList = (params) => {
	return httpClient.post('/userApi/manage/selectUserList', params, { headers: {"show-layer": "Yes"} });
};

const stopUser = (params) => {
	return httpClient.post('/userApi/manage/stopUser', params, { headers: {"show-layer": "Yes"} });
};

const releaseUser = (params) => {
	return httpClient.post('/userApi/manage/releaseUser', params, { headers: {"show-layer": "Yes"} });
};

const deleteUser = (params) => {
	return httpClient.post('/userApi/manage/deleteUser', params, { headers: {"show-layer": "Yes"} });
};

const modifyUser = (params) => {
	return httpClient.post('/userApi/manage/modifyUser', params, { headers: {"show-layer": "Yes"} });
};

const registerUser = (params) => {
	return httpClient.post('/userApi/manage/registerUser', params, { headers: {"show-layer": "Yes"} });
};

const checkDupcUser = (params) => {
	return httpClient.post('/userApi/manage/checkDupcUser', params, { headers: {"show-layer": "Yes"} });
};

export default {
	selectUserList,
	stopUser,
	releaseUser,
	deleteUser,
	modifyUser,
	registerUser,
	checkDupcUser
};