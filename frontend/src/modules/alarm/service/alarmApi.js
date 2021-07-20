import httpClient from '@/common/http-client';

const selectAlarmTypeList = (params) => {
    return httpClient.post('/api/alarm/selectAlarmTypeList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectAlarmType = (params) => {
    return httpClient.post('/api/alarm/selectAlarmType', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectAlarmList = (params) => {
    return httpClient.post('/api/alarm/selectAlarmList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const selectCollectTargetApiList = (params) => {
    return httpClient.post('/api/alarm/selectCollectTargetApiList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveAlarm = (params) => {
    return httpClient.post('/api/noti/saveAlarm', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

export default {
    selectAlarmTypeList,
    selectAlarmType,
    selectAlarmList,
    selectCollectTargetApiList,
    saveAlarm
}; 