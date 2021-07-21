import httpClient from '@/common/http-client';

const signUpRedirect = (params) => {
    return httpClient.post('/api/auth/login', params, { headers: {"show-layer": "Yes"} });
};

const domainChk = (params) => {
    return httpClient.post('/api/public/signUp/domainChk', params, { headers: {"show-layer": "Yes"} });
};

const insertEmailUser = (params) => {
    return httpClient.post('/api/public/signUp/insertEmailUser', params, { headers: {"show-layer": "Yes"} });
};

const insertSignUp = (params) => {
    return httpClient.post('/api/public/signUp/insertSignUp', params, { headers: {"show-layer": "Yes"} });
};

const selectUseTerms = (params) => {
    return httpClient.post('/api/public/signUp/selectUseTerms', params, { headers: {"show-layer": "Yes"} });
};

const selectUseTermsPreVersion = (params) => {
    return httpClient.post('/api/public/signUp/selectUseTermsPreVersion', params, { headers: {"show-layer": "Yes"} });
};

const apiTest = () => {
    return httpClient.post('/api/public/signUp/apiTest', { headers: {"show-layer": "Yes"} });
}

const getNiceCheck = () => {
    return httpClient.post('/api/public/signUp/getNiceCheck', { headers: {"show-layer": "Yes"} });
}

const getNiceCheckSuccess = (params) => {
    return httpClient.post('/api/public/signUp/getNiceCheckSuccess', params, { headers: {"show-layer": "Yes"} });
}

const getNiceCheckFail = (params) => {
    return httpClient.post('/api/public/signUp/getNiceCheckFail', params, { headers: {"show-layer": "Yes"} });
}

const selectCorpCustList = (params) => {
    return httpClient.post('/api/public/signUp/selectCorpCustList', params, { headers: {"show-layer": "Yes"} });
}

const selectSelCorpCustInfo = (params) => {
    return httpClient.post('/api/public/signUp/selectSelCorpCustInfo', params, { headers: {"show-layer": "Yes"} });
}

const getNiceCheckInfo = (params) => {
    return httpClient.post('/api/public/signUp/getNiceCheckInfo', { headers: {"show-layer": "Yes"} });
}

const selectCustAddr = (params) => {
    return httpClient.post("/api/public/signUp/selectCustAddr", params, { headers: {"show-layer": "Yes"}});
}


const certifyMailByLoginId = (params) => {
    return httpClient.post("/api/public/signUp/certifyMailByLoginId", params, { headers: {"show-layer": "Yes"}});
}

export default {
    signUpRedirect,
    domainChk,
    insertSignUp,
    insertEmailUser,
    selectUseTerms,
    selectUseTermsPreVersion,
    apiTest,
    getNiceCheck,
    getNiceCheckSuccess,
    getNiceCheckFail,
    selectCorpCustList,
    selectSelCorpCustInfo,
    getNiceCheckInfo,
    selectCustAddr,
    certifyMailByLoginId
};