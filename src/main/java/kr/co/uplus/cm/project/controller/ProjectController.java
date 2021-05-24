package kr.co.uplus.cm.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.project.service.ProjectService;

@RestController
@RequestMapping("/projectApi/manage")
public class ProjectController {

	@Autowired
	ProjectService projectService;

    @Autowired
    private CommonService commonSvc;
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}
    
	// 프로젝트 리스트 조회
	@PostMapping("/selectProjectList")
	public RestResult<?> selectProjectList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return projectService.selectProjectList(params);
	}

	// 프로젝트명 중복 조회
	@PostMapping("/checkProjectNameDuplicate")
	public RestResult<?> checkProjectNameDuplicate(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return projectService.checkProjectNameDuplicate(params);
	}

	// 프로젝트 저장
	@PostMapping("/saveProject")
	public RestResult<?> saveProject(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			projectService.saveProject(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
	}
	

	// 발신번호관리 > 사전등록예외대상 사업자 구분 확인
	@PostMapping("/checkPreRegYn")
	public RestResult<?> checkPreRegYn(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return projectService.checkPreRegYn(params);
	}
	
	// 사전등록예외대상 저장
	@PostMapping("/savePreRegExWithUploadFiles")
	public RestResult<?> savePreRegExWithUploadFiles(
			@RequestParam List<MultipartFile> uploadFiles,
			@RequestParam String loginId,
			@RequestParam String corpId,
			@RequestParam String reqType,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		rtn.setSuccess(true);
		
		Map<String, Object> params = new HashedMap<String, Object>();
		
		params.put("loginId", loginId);
		params.put("corpId", corpId);
		params.put("reqType", reqType);
		
		try {
			projectService.savePreRegExWithUploadFiles(uploadFiles, params);
			rtn.setSuccess(true);
			rtn.setData(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}
	

	/**
	 * RCS 브랜드 등록요청
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/saveRcsChatbotReqForApi")
	public RestResult<?> saveRcsChatbotReqForApi(
			@RequestParam String loginId,
			@RequestParam String corpId,
			@RequestParam String projectId,
			@RequestParam String brandId,
			@RequestParam String mainMdn,
			@RequestParam String mainTitle,
			
			@RequestParam(required = false) MultipartFile certiFile,
			
			@RequestParam(required = false) String chatbots,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		RestResult<Object> rtn = new RestResult<Object>();
		rtn.setSuccess(true);
		
		// 파라미터 정리
		params.put("loginId",			loginId);
		params.put("corpId",			corpId);
		params.put("projectId",			projectId);
		params.put("brandId",			brandId);
		params.put("mainMdn",			mainMdn);
		params.put("certiFile",			certiFile);
		params.put("chatbots",			chatbots);
		
		System.out.println("----------------------------------------params : " + params);
		
//		try {
			projectService.saveRcsChatbotReqForApi(params);
			rtn.setSuccess(true);
//		} catch (Exception e) {
//			rtn.setSuccess(false);
//			rtn.setMessage(e.getMessage());
//		}
		
		return rtn;
	}
	
	// 발신번호관리 조회
	@PostMapping("/selectCallbackManageList")
	public RestResult<?> selectCallbackList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println("-------------------------------------@@ selectCallbackManageList params : " + params);
		
		return projectService.selectCallbackManageList(params);
	}
	
	
	// 발신번호관리 수정 요청
	@PostMapping("/updateCallbackForApi")
	public RestResult<?> updateCallbackForApi(
			@RequestBody Map<String, Object> params,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		rtn.setSuccess(true);
		
		System.out.println("-------------------------------------@@ params : " + params);
		
		try {
			projectService.updateCallbackForApi(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}
	
	// 발신번호관리 수정 요청
	@PostMapping("/deleteCallbackForApi")
	public RestResult<?> deleteCallbackForApi(
			@RequestBody Map<String, Object> params,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		rtn.setSuccess(true);
		
		System.out.println("-------------------------------------@@ deleteCallbackForApi params : " + params);
		
		try {
			projectService.deleteCallbackForApi(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}
}
