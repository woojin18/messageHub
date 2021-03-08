import httpClient from '@/common/http-client';

const commonUpload = file => {
  let formData = new FormData();
  formData.append('sourceFile', file);

  return httpClient.post('/api/file/common/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
};

const commonMultiUpload = files => {
  let formData = new FormData();

  for (let x = 0; x < files.length; x++) {
    formData.append('sourceFiles', files[x]);
  }

  return httpClient.post('/api/file/common/upload/multi', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
};

export default {
  commonUpload,
  commonMultiUpload
};
