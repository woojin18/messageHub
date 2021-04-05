import httpClient from '@/common/http-client';

const selectImageList = (params) => {
    return httpClient.post('/api/public/common/selectImageList', params, { headers: {"show-layer": "Yes"} });
};
const deleteImage = (params) => {
    return httpClient.post('/api/public/common/deleteImageFile', params, { headers: {"show-layer": "Yes"} });
};

export default {
    selectImageList,
    deleteImage
};