import httpClient from '@/common/http-client';

const selectImageList = (params) => {
    return httpClient.post('/commonApi/selectImageList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};
const deleteImage = (params) => {
    return httpClient.post('/commonApi/deleteImageFile', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};
const uploadImage = (params) => {
    return httpClient.post('/commonApi/uploadImage', params, { headers: {"show-layer": "Yes", "activity":"SAVE", "Content-Type": "multipart/form-data"} });
};
const selectImageUrlInfo = (params) => {
  return httpClient.post('/commonApi/selectImageUrlInfo', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectCodeList = (params) => {
    return httpClient.post('/commonApi/selectCodeList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

export default {
    selectImageList,
    deleteImage,
    uploadImage,
    selectImageUrlInfo,
    selectCodeList
};