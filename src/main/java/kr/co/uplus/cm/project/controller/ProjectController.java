package kr.co.uplus.cm.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import kr.co.uplus.cm.utils.CommonUtils;

@RestController
@RequestMapping("/projectApi/manage")
public class ProjectController {
	
	@Value("${console.domain.baseUrl}") String baseUrl;
	
	@Value("${nice.checkplus.siteCode}") String sSiteCode;
	
	@Value("${nice.checkplus.sitePassword}") String sSitePassword;

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

	@PostMapping("/getNiceCheck")
	public RestResult<?> getNiceCheck(
			HttpServletRequest request,
			HttpServletResponse response){
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> niceCheckMap = new HashMap<String, Object>();
		
		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
		
	    
		String sSiteCode = this.sSiteCode;			// NICE로부터 부여받은 사이트 코드
		String sSitePassword = this.sSitePassword;		// NICE로부터 부여받은 사이트 패스워드
		
		String sRequestNumber = "REQ0000000001";			// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로 
		// 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
		sRequestNumber = niceCheck.getRequestNO(sSiteCode);
		
//		CommonUtils.addToSession(request, "REQ_SEQ", this);
		HttpSession sess = request.getSession(true);
		sess.setAttribute("REQ_SEQ", sRequestNumber);
//		HttpSession hs = request.getSession(true);
//		hs.setAttribute("REQ_SEQ" , sRequestNumber);
//		*************************
//		session.setAttribute("REQ_SEQ" , sRequestNumber);	// 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.

		String sAuthType = "";			// 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서
		
		String popgubun 	= "N";		//Y : 취소버튼 있음 / N : 취소버튼 없음
		String customize 	= "";		//없으면 기본 웹페이지 / Mobile : 모바일페이지
		
		String sGender = ""; 			//없으면 기본 선택 값, 0 : 여자, 1 : 남자 
		
		// CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
		//리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~
		String sReturnUrl = this.baseUrl+"/ac/project/checkPlusSuccess";      // 성공시 이동될 URL
		String sErrorUrl = this.baseUrl+"/ac/project/checkPlusFail";          // 실패시 이동될 URL
//		String sReturnUrl = "http://www.test.co.kr/checkplus_success.jsp";      // 성공시 이동될 URL
//		String sErrorUrl = "http://www.test.co.kr/checkplus_fail.jsp";          // 실패시 이동될 URL
		
		String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
							"8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
							"9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
							"7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
							"7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
							"11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
							"9:CUSTOMIZE" + customize.getBytes().length + ":" + customize + 
							"6:GENDER" + sGender.getBytes().length + ":" + sGender;

		String sMessage = "";
		String sEncData = "";
		
		int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
		if( iReturn == 0 )
		{
			sEncData = niceCheck.getCipherData();
		} else if( iReturn == -1) {
			sMessage = "Nice 본인인증 암호화 시스템 에러입니다.";
		} else if( iReturn == -2) {
			sMessage = "Nice 본인인증 암호화 처리오류입니다.";
		}
		else if( iReturn == -3) {
			sMessage = "Nice 본인인증 암호화 데이터 오류입니다.";
		} else if( iReturn == -9) {
			sMessage = "Nice 본인인증 입력 데이터 오류입니다.";
		} else {
			sMessage = "Nice 본인인증 알수 없는 에러 입니다. iReturn : " + iReturn;
		}

		niceCheckMap.put("sEncData", sEncData);
		
		if(sMessage.length() > 0) {
			rtn.setMessage(sMessage);
			rtn.setSuccess(false);
			return rtn;
		}
		
		rtn.setData(niceCheckMap);
		return rtn;
	}
	
	@PostMapping("/getNiceCheckSuccess")
	public RestResult<?> getNiceCheckSuccess(
			@RequestBody Map<String, Object> params,
			HttpServletRequest request){
		RestResult<Object> rtn = new RestResult<Object>();
		
		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
//		String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");
		String sEncodeData =  requestReplace(CommonUtils.getString(params.get("EncodeData")), "encodeData");
		String sSiteCode = this.sSiteCode;				// NICE로부터 부여받은 사이트 코드
		String sSitePassword = this.sSitePassword;			// NICE로부터 부여받은 사이트 패스워드
		
		String sMessage = "정상적으로 문자발신번호가 등록되었습니다.";
		String sPlainData = "";

		int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);
		
		if( iReturn == 0 ) {
			sPlainData = niceCheck.getPlainData();			
			// 데이타를 추출합니다.
			HashMap mapresult = niceCheck.fnParse(sPlainData);
			params.put("callNum", mapresult.get("MOBILE_NO"));
			
			try {
				projectService.saveCallNumByPhone(params);
			} catch (Exception e) {
				sMessage = e.getMessage();
			}
			
		} else if( iReturn == -1) {
			sMessage = "복호화 시스템 오류입니다.";
		} else if( iReturn == -4) {
			sMessage = "복호화 처리 오류입니다.";
		} else if( iReturn == -5) {
			sMessage = "복호화 해쉬 오류입니다.";
		} else if( iReturn == -6) {
			sMessage = "복호화 데이터 오류입니다.";
		} else if( iReturn == -9) {
			sMessage = "입력 데이터 오류입니다.";
		} else if( iReturn == -12) {
			sMessage = "사이트 패스워드 오류입니다.";
		} else {
			sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
		}

		rtn.setMessage(sMessage);
		return rtn;
	}
	
	@PostMapping("/getNiceCheckFail")
	public RestResult<?> getNiceCheckFail(
			@RequestBody Map<String, Object> params){
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> niceCheckMap = new HashMap<String, Object>();
		
		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

		String sEncodeData = requestReplace(CommonUtils.getString(params.get("EncodeData")), "encodeData");

		String sSiteCode = this.sSiteCode;				// NICE로부터 부여받은 사이트 코드
		String sSitePassword = this.sSitePassword;			// NICE로부터 부여받은 사이트 패스워드

		String sCipherTime = "";			// 복호화한 시간
		String sRequestNumber = "";			// 요청 번호
		String sErrorCode = "";				// 인증 결과코드
		String sAuthType = "";				// 인증 수단
		String sMessage = "";
		String sPlainData = "";
		
		int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

		if( iReturn == 0 ) {
			sPlainData = niceCheck.getPlainData();
			sCipherTime = niceCheck.getCipherDateTime();
			
			// 데이타를 추출합니다.
			HashMap mapresult = niceCheck.fnParse(sPlainData);
			
			sRequestNumber 	= (String)mapresult.get("REQ_SEQ");
			sErrorCode 		= (String)mapresult.get("ERR_CODE");
			sAuthType 		= (String)mapresult.get("AUTH_TYPE");
			
			niceCheckMap.put("sCipherTime", sCipherTime);
			niceCheckMap.put("sRequestNumber", sRequestNumber);
			niceCheckMap.put("sErrorCode", sErrorCode);
			niceCheckMap.put("sAuthType", sAuthType);
			
			rtn.setData(niceCheckMap);
		} else if( iReturn == -1) {
			sMessage = "복호화 시스템 에러입니다.";
		} else if( iReturn == -4) {
			sMessage = "복호화 처리오류입니다.";
		} else if( iReturn == -5) {
			sMessage = "복호화 해쉬 오류입니다.";
		} else if( iReturn == -6) {
			sMessage = "복호화 데이터 오류입니다.";
		} else if( iReturn == -9) {
			sMessage = "입력 데이터 오류입니다.";
		} else if( iReturn == -12) {
			sMessage = "사이트 패스워드 오류입니다.";
		} else {
			sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
		}
		
		if(sMessage.length() > 0) {
			rtn.setSuccess(false);
			rtn.setMessage(sMessage);
		}
		
		return rtn;
	}
	
	public String requestReplace (String paramValue, String gubun) {
		String result = "";
		
		if (paramValue != null) {
			paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			paramValue = paramValue.replaceAll("\\*", "");
			paramValue = paramValue.replaceAll("\\?", "");
			paramValue = paramValue.replaceAll("\\[", "");
			paramValue = paramValue.replaceAll("\\{", "");
			paramValue = paramValue.replaceAll("\\(", "");
			paramValue = paramValue.replaceAll("\\)", "");
			paramValue = paramValue.replaceAll("\\^", "");
			paramValue = paramValue.replaceAll("\\$", "");
			paramValue = paramValue.replaceAll("'", "");
			paramValue = paramValue.replaceAll("@", "");
			paramValue = paramValue.replaceAll("%", "");
			paramValue = paramValue.replaceAll(";", "");
			paramValue = paramValue.replaceAll(":", "");
			paramValue = paramValue.replaceAll("-", "");
			paramValue = paramValue.replaceAll("#", "");
			paramValue = paramValue.replaceAll("--", "");
			paramValue = paramValue.replaceAll("-", "");
			paramValue = paramValue.replaceAll(",", "");
		
			if(gubun != "encodeData"){
				paramValue = paramValue.replaceAll("\\+", "");
				paramValue = paramValue.replaceAll("/", "");
				paramValue = paramValue.replaceAll("=", "");
			}
			
			result = paramValue;
		}
		return result;
	}

	/**
	 * 서류인증
	 * @param corpId
	 * @param projectId
	 * @param callNum
	 * @param userId
	 * @param joinFile
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/saveCallNumDoc")
	public RestResult<?> saveCallNumDoc(
			@RequestParam String corpId,
			@RequestParam String projectId,
			@RequestParam String callNum,
			@RequestParam String userId,
			@RequestParam(required = false) MultipartFile joinFile,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		RestResult<Object> rtn = new RestResult<Object>();
		rtn.setSuccess(true);
		
		// 파라미터 정리
		params.put("corpId",			corpId);
		params.put("projectId",			projectId);
		params.put("callNum",			callNum);
		params.put("joinFile",			joinFile);
		params.put("userId",			userId);
		
		try {
			projectService.saveCallNumDoc(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}

	/**
	 * 대리인 일괄등록
	 * @param corpId
	 * @param projectId
	 * @param userId
	 * @param allFile
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/saveCallNumAll")
	public RestResult<?> saveCallNumAll(
			@RequestParam String corpId,
			@RequestParam String projectId,
			@RequestParam String userId,
			@RequestParam(required = false) MultipartFile allFile,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		RestResult<Object> rtn = new RestResult<Object>();
		rtn.setSuccess(true);
		
		// 파라미터 정리
		params.put("corpId",			corpId);
		params.put("projectId",			projectId);
		params.put("allFile",			allFile);
		params.put("userId",			userId);
		
		try {
			projectService.saveCallNumAll(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}

	/**
	 * 대리인 등록
	 * @param corpId
	 * @param projectId
	 * @param callNum
	 * @param userId
	 * @param joinFile
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/saveCallNumPro")
	public RestResult<?> saveCallNumPro(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		
		RestResult<Object> rtn = new RestResult<Object>();
		rtn.setSuccess(true);
		
		try {
			projectService.saveCallNumPro(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}

	/**
	 * 대리인 등록요청 
	 * @param uploadFiles
	 * @param userId
	 * @param corpId
	 * @param reqType
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/saveCallNumProxy")
	public RestResult<?> saveCallNumProxy(
			@RequestParam List<MultipartFile> uploadFiles,
			@RequestParam String userId,
			@RequestParam String corpId,
			@RequestParam(required = false) MultipartFile agreeFile,
			@RequestParam(required = false) MultipartFile reqFile,
			@RequestParam(required = false) MultipartFile sealFile,
			@RequestParam(required = false) MultipartFile authFile,
			@RequestParam(required = false) MultipartFile workFile,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		rtn.setSuccess(true);
		
		Map<String, Object> params = new HashedMap<String, Object>();
		
		params.put("userId", userId);
		params.put("corpId", corpId);
		params.put("agreeFile",			agreeFile);
		params.put("reqFile",			reqFile);
		params.put("sealFile",			sealFile);
		params.put("authFile",			authFile);
		params.put("workFile",			workFile);
		
		try {
			projectService.saveCallNumProxy(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}
	
	// 발신번호관리 조회
	@PostMapping("/getCallNumProxy")
	public RestResult<?> getCallNumProxy(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return projectService.selectCallNumProxy(params);
	}
	
	// 발신번호목록 조회
	@PostMapping("/getCallNumList")
	public RestResult<?> getCallNumList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return projectService.selectCallNumList(params);
	}
	 
	// RCS발신번호목록 조회
	@PostMapping("/getRcsCallNumList")
	public RestResult<?> getRcsCallNumList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return projectService.selectRcsCallNumList(params);
	}
	
	// 문자S발신번호목록 조회
	@PostMapping("/getSmsCallNumList")
	public RestResult<?> getSmsCallNumList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return projectService.selectSmsCallNumList(params);
	}
	
	/**
	 * 프로젝트 발신번호 등록
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/saveProjectCallNum")
	public RestResult<?> saveProjectCallNum(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = projectService.saveProjectCallNum(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}

		return rtn;
	}
	
	/**
	 * 발신번호  연결해제
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/delCallNum")
	public RestResult<?> delCallNum(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			if ("Y".equals(params.get("delYn").toString()) && params.get("chatbotId") != null) {
				projectService.deleteCallbackForApi(params);
			} else {
				projectService.delCallNum(params);
			}
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}

		return rtn;
	}
	
	// API KEY 관리 리스트 
	@PostMapping("/selectApikeyManageList")
	public RestResult<?> selectApikeyManageList(
				@RequestBody Map<String, Object> params,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		return projectService.selectApikeyManageList(params);
    }
	
}
