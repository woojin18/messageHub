import httpClient from '@/common/http-client';

const selectPrdUnitInfoList = (params) => {
  return httpClient.post('/api/public/main/selectPrdUnitInfoList', params, { headers: {"show-layer": "Yes"} });
};

export default {
  selectPrdUnitInfoList,
};

