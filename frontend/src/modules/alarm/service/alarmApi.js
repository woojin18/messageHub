import httpClient from '@/common/http-client';

const selectAlarmTypeList = (params) => {
    return httpClient.post('/api/alarm/selectAlarmTypeList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectAlarm = (params) => {
    return httpClient.post('/api/alarm/selectAlarm', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectAlarmType = (params) => {
    return httpClient.post('/api/alarm/selectAlarmType', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectAlarmList = (params) => {
    return httpClient.post('/api/alarm/selectAlarmList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectApikeyList = (params) => {
    return httpClient.post('/api/alarm/selectApikeyList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectCollectTargetApiList = (params) => {
    return httpClient.post('/api/alarm/selectCollectTargetApiList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveAlarm = (params) => {
    return httpClient.post('/api/alarm/saveAlarm', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

const delAlarm = (params) => {
    return httpClient.post('/api/alarm/delAlarm', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

export default {
    selectAlarmTypeList,
    selectAlarm,
    selectAlarmType,
    selectAlarmList,
    selectApikeyList,
    selectCollectTargetApiList,
    saveAlarm,
    delAlarm
}; 