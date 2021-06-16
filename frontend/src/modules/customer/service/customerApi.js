import httpClient from '@/common/http-client';

const selectFaqTypeList = (params) => {
  return httpClient.post('/api/public/customer/selectFaqTypeList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectFaqList = (params) => {
  return httpClient.post('/api/public/customer/selectFaqList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectNoticeList = (params) => {
  return httpClient.post('/api/public/customer/selectNoticeList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

export default {
  selectFaqTypeList,
  selectFaqList,
  selectNoticeList
};