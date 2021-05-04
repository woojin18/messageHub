import httpClient from '@/common/http-client';
import tokenSvc from '@/common/token-service';

const selectImageList = (params) => {
    return httpClient.post('/api/public/common/selectImageList', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};
const deleteImage = (params) => {
    return httpClient.post('/api/public/common/deleteImageFile', params, { headers: {"show-layer": "Yes", "loginId": tokenSvc.getToken().principal.loginId} });
};
const uploadImage = (params) => {
    return httpClient.post('/api/public/common/uploadImage', params, { headers: {"show-layer": "Yes", "Content-Type": "multipart/form-data", "loginId": tokenSvc.getToken().principal.loginId} });
};

export default {
    selectImageList,
    deleteImage,
    uploadImage
};