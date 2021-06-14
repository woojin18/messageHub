import httpClient from '@/common/http-client';

const selectFaqTypeList = (params) => {
  return httpClient.post('/api/public/customer/selectFaqTypeList', params, { headers: {"show-layer": "Yes"} });
};

const selectFaqList = (params) => {
  return httpClient.post('/api/public/customer/selectFaqList', params, { headers: {"show-layer": "Yes"} });
};

export default {
  selectFaqTypeList,
  selectFaqList
};