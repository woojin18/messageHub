import httpClient from '@/common/http-client';
import tokenSvc from '@/common/token-service';

const selectAddressCateGrpList = (params) => {
	return httpClient.post('/addressApi/manage/selectAddressCateGrpList', params, { headers: {"show-layer": "Yes"} });
};

const selectAddressList = (params) => {
	return httpClient.post('/addressApi/manage/selectAddressList', params, { headers: {"show-layer": "Yes"} });
};

const selectMemberList = (params) => {
	return httpClient.post('/addressApi/manage/selectMemberList', params, { headers: {"show-layer": "Yes"} });
};

const registerAddr = (params) => {
	return httpClient.post('/addressApi/manage/registerAddr', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};

const modifyAddr = (params) => {
	return httpClient.post('/addressApi/manage/modifyAddr', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};
const selectProjectList = (params) => {
	return httpClient.post('/addressApi/manage/selectProjectList', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};

export default {
	selectAddressCateGrpList,
	selectAddressList,
	selectMemberList,
	registerAddr,
	modifyAddr,
	selectProjectList
};