import httpClient from '@/common/http-client';

const selectProjectList = (params) => {
    return httpClient.post('/projectApi/manage/selectProjectList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const checkProjectNameDuplicate = (params) => {
    return httpClient.post('/projectApi/manage/checkProjectNameDuplicate', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveProject = (params) => {
    return httpClient.post('/projectApi/manage/saveProject', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const checkPreRegYn = (params) => {
    return httpClient.post('/projectApi/manage/checkPreRegYn', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const downloadFile = (params) => {
    return httpClient.post('/commonApi/uploadFile', params, { headers: {"show-layer": "No"}, responseType: 'arraybuffer' });
};

const savePreRegEx = (params) => {
    return httpClient.post('/projectApi/manage/savePreRegEx', params, { headers: { "show-layer": "No", "activity": "SAVE"}});
};

const selectCallbackManageList = (params) => {
    return httpClient.post('/projectApi/manage/selectCallbackManageList', params, { headers: { "show-layer": "Yes", "activity": "READ"}});
};

const updateCallbackForApi = (params) => {
    return httpClient.post('/projectApi/manage/updateCallbackForApi', params, { headers: { "show-layer": "Yes", "activity": "SAVE"}});
};

const deleteCallbackForApi = (params) => {
    return httpClient.post('/projectApi/manage/deleteCallbackForApi', params, { headers: { "show-layer": "Yes", "activity": "SAVE"}});
};

const selectApprovalBrandList = (params) => {
    return httpClient.post('/projectApi/manage/selectApprovalBrandList', params, { headers: { "show-layer": "Yes", "activity": "READ"}});
};

const saveDisRatio= (params) => {
    return httpClient.post('/projectApi/manage/saveDisRatio', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectBasicDisRatio= (params) => {
    return httpClient.post('/projectApi/manage/selectBasicDisRatio', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectDisRatio= (params) => {
    return httpClient.post('/projectApi/manage/selectDisRatio', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectBillIdForApi = (params) => {
    return httpClient.post('/projectApi/manage/selectBillIdForApi', params, { headers: { "show-layer": "Yes", "activity": "READ" } });
};

const selectDistDetail = (params) => {
    return httpClient.post('/projectApi/manage/selectDistDetail', params, { headers: { "show-layer": "Yes", "activity": "READ" } });
};
const selectCorpDistId = (params) => {
    return httpClient.post('/projectApi/manage/selectCorpDistId', params, { headers: { "show-layer": "Yes", "activity": "READ" } });
};
const getNiceCheck = () => {
    return httpClient.post('/projectApi/manage/getNiceCheck', { headers: {"show-layer": "Yes", "activity": "READ" } });
}
const getNiceCheckSuccess = (params) => {
    return httpClient.post('/projectApi/manage/getNiceCheckSuccess', params, { headers: {"show-layer": "Yes", "activity": "READ" } });
}
const getNiceCheckFail = (params) => {
    return httpClient.post('/projectApi/manage/getNiceCheckFail', params, { headers: {"show-layer": "Yes", "activity": "READ" } });
}
const getCallNumProxy = (params) => {
    return httpClient.post('/projectApi/manage/getCallNumProxy', params, { headers: {"show-layer": "Yes", "activity": "READ" } });
}
const getCallNumList = (params) => {
    return httpClient.post('/projectApi/manage/getCallNumList', params, { headers: {"show-layer": "Yes", "activity": "READ" } });
}
const getRcsCallNumList = (params) => {
    return httpClient.post('/projectApi/manage/getRcsCallNumList', params, { headers: {"show-layer": "Yes", "activity": "READ" } });
}
const getSmsCallNumList = (params) => {
    return httpClient.post('/projectApi/manage/getSmsCallNumList', params, { headers: {"show-layer": "Yes", "activity": "READ" } });
}
const saveCallNumPro = (params) => {
    return httpClient.post('/projectApi/manage/saveCallNumPro', params, { headers: {"show-layer": "Yes", "activity": "SAVE" } });
}
const saveProjectCallNum = (params) => {
    return httpClient.post('/projectApi/manage/saveProjectCallNum', params, { headers: {"show-layer": "Yes", "activity": "SAVE" } });
}
const delCallNum = (params) => {
    return httpClient.post('/projectApi/manage/delCallNum', params, { headers: {"show-layer": "Yes", "activity": "SAVE" } });
}
const selectApikeyManageList = (params) => {
    return httpClient.post('/projectApi/manage/selectApikeyManageList', params, { headers: { "show-layer": "Yes", "activity": "READ" } });
};
const saveApikeyManageGenerate = (params) => {
    return httpClient.post('/projectApi/manage/saveApikeyManageGenerate', params, { headers: {"show-layer": "Yes", "activity": "SAVE" } });
}
const updateApikeyManageList = (params) => {
    return httpClient.post('/projectApi/manage/updateApikeyManageList', params, { headers: {"show-layer": "Yes", "activity": "SAVE" } });
}

export default {
    selectProjectList,
    checkProjectNameDuplicate,
    saveProject,
    checkPreRegYn,
    downloadFile,
    savePreRegEx,
    selectCallbackManageList,
    updateCallbackForApi,
    deleteCallbackForApi,
    selectApprovalBrandList,
    saveDisRatio,
    selectBasicDisRatio,
    selectDisRatio,
    selectBillIdForApi,
    selectDistDetail,
    selectCorpDistId,
    getNiceCheck,
    getNiceCheckSuccess,
    getNiceCheckFail,
    getCallNumProxy,
    getCallNumList,
    getRcsCallNumList,
    getSmsCallNumList,
    saveCallNumPro,
    saveProjectCallNum,
    delCallNum,
    selectApikeyManageList,
    saveApikeyManageGenerate,
    updateApikeyManageList
};