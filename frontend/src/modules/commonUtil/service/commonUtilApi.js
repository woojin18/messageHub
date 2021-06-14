import httpClient from '@/common/http-client';

const selectImageList = (params) => {
    return httpClient.post('/commonApi/selectImageList', params, { headers: {"show-layer": "Yes"} });
};
const deleteImage = (params) => {
    return httpClient.post('/commonApin/deleteImageFile', params, { headers: {"show-layer": "Yes"} });
};
const uploadImage = (params) => {
    return httpClient.post('/commonApi/uploadImage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data"} });
};

const selectCodeList = (params) => {
    return httpClient.post('/commonApi/selectCodeList', params, { headers: {"show-layer": "Yes"} });
};

export default {
    selectImageList,
    deleteImage,
    uploadImage,
    selectCodeList
};