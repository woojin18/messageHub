package kr.co.uplus.cm.project.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.project.service.BaseInfoService;
import kr.co.uplus.cm.utils.CommonUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/baseInfoApi/manage")

public class BaseInfoController {

	@Autowired
	private BaseInfoService baseInfoSvc;

	@Autowired
	private CommonService commonService;

	@Value("${night.send.st.hh}") String nightSendSthh;
	@Value("${night.send.st.mm}") String nightSendStmm;
	@Value("${night.send.ed.hh}") String nightSendEdhh;
	@Value("${night.send.ed.mm}") String nightSendEdmm;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}
	
	/**
	 * 프로젝트 기본정보 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectProjectBaseInfo")
	public RestResult<?> selectProjectBaseInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = baseInfoSvc.selectProjectBaseInfo(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	/**
	 * 프로젝트 기본정보 저장
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/saveProjectBaseInfo")
	public RestResult<?> saveProjectBaseInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
 
		try {
			rtn = baseInfoSvc.saveProjectBaseInfo(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	/**
	 * APIKEY 목록조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectApiKeyList")
	public RestResult<?> selectApiKeyList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {


		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = baseInfoSvc.selectApiKeyList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	/**
	 * APIKEY 저장/수정
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/saveApiKey")
	public RestResult<?> saveApiKey(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
 
		try {
			rtn = baseInfoSvc.saveApiKey(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	//야간 메시지 발송 제한 확인
	@PostMapping("/selectNightSendTime")
	public RestResult<?> selectNightSendTime(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nightSendSthh", nightSendSthh);
			map.put("nightSendStmm", nightSendStmm);
			map.put("nightSendEdhh", nightSendEdhh);
			map.put("nightSendEdmm", nightSendEdmm);
			
			if(params.containsKey("isChk") && CommonUtils.getString(params.get("isChk")).equals("Y")){
				map.put("nightSendYn", baseInfoSvc.selectProjectNightSendYn(params));
			}
			rtn.setData(map);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}
}

