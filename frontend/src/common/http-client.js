import axios from 'axios';
import * as utils from '@/common/utils';
import tokenSvc from '@/common/token-service';
import { consts } from '@/common/config';

const config = {
	// baseURL: apiBaseUrl,
	headers: {
		'X-Requested-With': 'XMLHttpRequest',
		'show-layer': 'Yes'
	},
	timeout: 120000 // timeout은 120초로 설정
};

const httpClient = axios.create(config);

const setLoginInterceptor = config => {
	if (tokenSvc.getToken()) {
		if (config.url !== '/api/auth/logout' && !config.url.includes('/api/public/')) {
			config.headers.loginId = tokenSvc.getToken().principal.loginId;
			config.headers.svcType = jQuery('#M_svcTypeCd').val();
			config.headers.role = jQuery('#M_roleCd').val();
			config.headers.menu = jQuery('#M_menusCd').val();
			if (config.data.corpId == null) {
				config.data.corpId = tokenSvc.getToken().principal.corpId;
			}
			if (config.data.userId == null) {
				config.data.userId = tokenSvc.getToken().principal.userId;
			}
			if (config.data.projectId == null) {
				config.data.projectId = utils.getCookie(consts.projectId);
			}
		}
	}
	return config;
};

const authInterceptor = config => {
	// frontend와 backend의 origin이 다른 경우
	// devServer.proxy 설정을 하던지 baseURL과 withCredentials 설정을 해야 한다.
	// cookie, header 등에 자격정보 설정이 필요한 api는 true 설정으로 호출해야 하고
	// 자격정보 설정이 필요없는 api는 withCredentials=false 설정으로 호출해야 한다.
	// config.withCredentials = !config.url.startsWith('/api/public/');
	return config;
};

const loggerInterceptor = config => {
	//console.log('request url:', config.url, 'params:', config.data);
	return config;
};

let loadOverlap = [];
const loadingLayer = (type, config) => {
  let loadingOverlay = document.querySelector('.loading');
  if(loadingOverlay != null){
    document.querySelector('html > body').style.removeProperty('overflow'); // 스크롤 allow
    loadingOverlay.classList.add('hidden');

    if(loadingOverlay != null){
      if (config.headers['show-layer'] == 'Yes') {
        if (type) {
          loadOverlap.push('add');
        } else {
          loadOverlap.pop();
        }
        if (loadOverlap.length > 0) {
          document.querySelector('html > body').style.overflow = 'hidden'; // 스크롤 block
          loadingOverlay.classList.remove('hidden');
        }
      }
    }
  }
};

const loadingLayerInterceptor = config => {
	loadingLayer(true, config);
	return config;
};

/** Adding the request interceptors */
httpClient.interceptors.request.use(authInterceptor);
httpClient.interceptors.request.use(loggerInterceptor);
httpClient.interceptors.request.use(loadingLayerInterceptor);
httpClient.interceptors.request.use(setLoginInterceptor);

/** Adding the response interceptors */
httpClient.interceptors.response.use(
	response => {
		loadingLayer(false, response.config);
		//console.log('response status:', response.status, 'data:', response.data);
		return response;
	},
	error => {
    console.log(error);
		if (error.response != undefined && error.response != null) loadingLayer(false, error.response.config);
		else loadingLayer(false, error.config);

		if (error.code === 'ECONNABORTED') {
			alert('서비스가 지연되고 있습니다. 잠시 후 확인하시고 다시 시도해주세요.');
			return Promise.reject(error);
		} else if (error.response.status == 403) {
			alert('권한이 없습니다.');
			if (error.config.headers.activity == 'READ') {
				window.history.back();
			}
		} else if (error.response.status == 401 || error.response.status == 418) {
			alert('세션이 만료되었습니다.');
			window.top.location.href = '/login';
		} else if (error.response.status == 500) {
			if (error.response.data != null && error.response.data.message == '511 NETWORK_AUTHENTICATION_REQUIRED') {
				alert('웹템플릿 IP가 브랜드포털에 등록이 필요합니다. 기술지원에 문의해주세요.');
				return Promise.reject(error);
			} else {
				window.top.location.href = '/view/error/500';
			}
		} else if (error.response.status == 511) {
			alert('웹템플릿 IP가 브랜드포털에 등록이 필요합니다. 기술지원에 문의해주세요.');
			return Promise.reject(error);
		} else if (error.message == 'Network Error') {
			alert('네트워크 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
			return Promise.reject(error);
		} else {
			console.log('response error:', error);
			return Promise.reject(error);
		}
	}
);

export default httpClient;