package kr.co.uplus.cm.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.co.uplus.cm.common.model.AuthUser;
import kr.co.uplus.cm.login.service.AuthService;

public class BaseController {

    @Autowired
    AuthService authSvc;

    /**
     * 사용자 정보 Get
     * @param request
     * @return
     */
    protected AuthUser getUserInfo(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return (AuthUser) authSvc.loadUserByUsername(request.getHeader("loginId"));
    }

    /**
     * 사용자 정보 Get
     * @param request
     * @return
     */
    protected AuthUser getUserInfo(String loginId){
        return (AuthUser) authSvc.loadUserByUsername(loginId);
    }

    /**
     * 사용자 정보 Set
     * @param params
     * @param isContainIgnore
     * @return
     */
    protected Map<String, Object> setUserInfo(Map<String, Object> params, boolean isContainIgnore){
        AuthUser user = getUserInfo();

        if(user != null) {
            String userId = StringUtils.defaultIfBlank(user.getUserId(), "");
            String corpId = StringUtils.defaultIfBlank(user.getCorpId(), "");
            String projectId = StringUtils.defaultIfBlank(user.getRepProjectId(), "");

            if(isContainIgnore) {
                params.put("userId", userId);
                params.put("corpId",corpId);
                params.put("projectId", projectId);
            } else {
                if(!params.containsKey("userId")) params.put("userId", userId);
                if(!params.containsKey("corpId")) params.put("corpId",corpId);
                if(!params.containsKey("projectId")) params.put("projectId", projectId);
            }
        }

        return params;
    }

    /**
     * 사용자 정보 Set
     * @param params
     * @return
     */
    protected Map<String, Object> setContainIgnoreUserInfo(Map<String, Object> params){
        return setUserInfo(params, true);
    }

}
