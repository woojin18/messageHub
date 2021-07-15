import httpClient from '@/common/http-client';

const selectAlarmList = (params) => {
    return httpClient.post('/api/noti/selectAlarmList', params, { headers: {"show-layer": "Yes", "activity":"READ"} });
};

const saveAlarm = (params) => {
    return httpClient.post('/api/noti/saveAlarm', params, { headers: {"show-layer": "Yes", "activity":"SAVE"} });
};

export default {
    selectAlarmList,
    saveAlarm
}; 