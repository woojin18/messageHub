import httpClient from '@/common/http-client';

const selectUrlInfoList = (params) => {
  return httpClient.post('/uc/urlInfo/selectUrlInfoList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
}
const insertUrlInfo = (params) => {
  return httpClient.post('/uc/urlInfo/insertUrlInfo', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
}
const deleteUrlInfo = (params) => {
  return httpClient.post('/uc/urlInfo/deleteUrlInfo', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
}

export default {
  selectUrlInfoList,
  insertUrlInfo,
  deleteUrlInfo,
}