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
			projectService.saveProject(request, response, params);
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
			@RequestParam String userId,
			@RequestParam String corpId,
			@RequestParam String reqType,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		rtn.setSuccess(true);
		
		Map<String, Object> params = new HashedMap<String, Object>();
		
		params.put("userId", userId);
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
	 * RCS 브랜드 등록 수정 요청
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/saveRcsChatbotReqForApi")
	public RestResult<?> saveRcsChatbotReqForApi(
			@RequestParam String sts,
			@RequestParam String saveCorpId,
			@RequestParam String projectId,
			@RequestParam String brandId,
			@RequestParam String chatbotId,
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
		params.put("sts",				sts);
		params.put("corpId",			saveCorpId);
		params.put("projectId",			projectId);
		params.put("brandId",			brandId);
		params.put("chatbotId",			chatbotId);
		params.put("mainMdn",			mainMdn);
		params.put("certiFile",			certiFile);
		params.put("chatbots",			chatbots);
		
		try {
			projectService.saveRcsChatbotReqForApi(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}
	
	// 발신번호관리 조회
	@PostMapping("/selectCallbackManageList")
	public RestResult<?> selectCallbackList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return projectService.selectCallbackManageList(params);
	}
	
	// 추가발신번호등록요청 브랜드 불러오기
	@PostMapping("/selectApprovalBrandList")
	public RestResult<?> selectApprovalBrandList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return projectService.selectApprovalBrandList(params);
	}
	
	// 발신번호관리 삭제 요청
	@PostMapping("/deleteCallbackForApi")
	public RestResult<?> deleteCallbackForApi(@RequestBody Map<String, Object> params,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		rtn.setSuccess(true);
		
		try {
			projectService.deleteCallbackForApi(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}
	
	//분배서비스관리 - 팝업분배율 테이블 조회(분배서비스ID가 없을 때)
	@PostMapping("/selectBasicDisRatio")
	public RestResult<?> selectBasicRatio(
				@RequestBody Map<String, Object> params,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		return projectService.selectBasicRatio(params);
    }
	
	//분배서비스관리 -팝업분배율 테이블 조회(분배서비스ID가 있을 때)
	@PostMapping("/selectDisRatio")
	public RestResult<?> selectDisRatio(
				@RequestBody Map<String, Object> params,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		return projectService.selectRatio(params);
    }
	
	//분배서비스 등록/수정
	@PostMapping("/saveDisRatio")
	public RestResult<?> saveDisRatio(
				@RequestBody Map<String, Object> params,
				HttpServletRequest request,
				HttpServletResponse response) {
		return projectService.saveDisRatio(params);
    }
	
	//
	@PostMapping("/selectBillIdForApi")
	public RestResult<?> selectBillIdForApi(
				@RequestBody Map<String, Object> params,
				HttpServletRequest request,
				HttpServletResponse response) {
		return projectService.selectBillIdForApi(params);
    }
	

	// 분배정책 가져오기
	@PostMapping("/selectDistDetail")
	public RestResult<?> selectDistDetail(
				@RequestBody Map<String, Object> params,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		return projectService.selectDistDetail(params);
    }

	// 분배정책 가져오기
	@PostMapping("/selectCorpDistId")
	public RestResult<?> selectCorpDistId(
				@RequestBody Map<String, Object> params,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		return projectService.selectCorpDistId(params);
    }
	
}
