package kr.co.uplus.cm.signUp.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.signUp.service.SignUpService;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/public/signUp")
public class SignUpController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8690584852883891055L;

	@Autowired GeneralDao generalDao;
	
	@Autowired SignUpService signUpSvc;
	
	@Autowired ApiInterface apiInterface;
	
	@Value("${nice.checkplus.siteCode}") String sSiteCode;
	
	@Value("${nice.checkplus.sitePassword}") String sSitePassword;
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

	// 회원
	@RequestMapping("test")
	public RedirectView test (RedirectAttributes redirect, HttpServletResponse response, HttpServletRequest req) {
		req.setAttribute("test", "test");
		return new RedirectView("http://localhost:3000/signUpMain");
	}
	
	// 도메인 이름 체크
	@PostMapping("/domainChk")
	public int domainChk(@RequestBody Map<String, Object> params) throws Exception {
		return generalDao.selectGernalCount(DB.QRY_SELECT_DOMAIN_CHK_COUNT, params);
	}
	
	// 인증 메일 발송 후 cm_user insert
	@PostMapping("/insertEmailUser")
	public RestResult<?> insertEmailUser(@RequestBody Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		String userId = CommonUtils.getCommonId("SIGN", 5);
		
		
		params.put("userId", userId);
		params.put("regId", userId);
		
		try {
			signUpSvc.insertEmailUser(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("회원 가입에 실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	// 회원 가입
//	@PostMapping("/insertSignUp")
//	public RestResult<?> insertSignUp(@RequestBody Map<String, Object> params) throws Exception {
//		
//		RestResult<Object> rtn = new RestResult<Object>();
//		try {
//			signUpSvc.insertSignUp(params);
//			rtn.setSuccess(true);
//		} catch (Exception e) {
//			rtn.setSuccess(false);
//			rtn.setMessage("회원 가입에 실패하였습니다.");
//			log.error("{} Error : {}", this.getClass(), e);
//		}
//		return rtn;
//	}
	@PostMapping("/insertSignUp")
	public RestResult<?> insertSignUp(
			@RequestParam(required=true) String loginId,				// 아이디
			@RequestParam(required=true) String password,				// 비밀번호
			@RequestParam(required=true) String smsCertifyYn,			// sms 인증 여부
			@RequestParam(required=true) String phoneCerti,				// user 핸드폰 번호
			@RequestParam(required=true) String regno,					// 사업자 번호
			@RequestParam(required=true) String custNo,					// 고객번호
			@RequestParam(required=true) String corpNm,					// 고객사 명
			@RequestParam(required=true) String ceoNm,					// 대표자 명
			@RequestParam(required=true) String busiType,				// 업태
			@RequestParam(required=true) String busiClass,				// 업종
			@RequestParam(required=true) String zipCode,				// 우편번호
			@RequestParam(required=true) String woplaceAddress,			// 주소
			@RequestParam(required=true) String woplaceAddressDetail,	// 상세주소
			@RequestParam(required=false) String wireTel,				// 유선전화번호
			@RequestParam(required=true) MultipartFile attachFile,		// 사업자 등록증
			@RequestParam(required=true) String domainName,				// 도메인
			@RequestParam(required=true) String custKdCd,				// 고객유형
			@RequestParam(required=false) String custrnmNo,				// 고객식별번호
			@RequestParam(required=false) String coInfo,				// 본인인증 토큰 (개인사업자 필수)
			@RequestParam(required=false) String genderCode				// 성별 (1: 남성,2: 여성)
			) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginId", loginId);
		paramMap.put("password", password);
		paramMap.put("smsCertifyYn", smsCertifyYn);
		paramMap.put("phoneCerti", phoneCerti);
		paramMap.put("regno", regno);
		paramMap.put("custNo", custNo);
		paramMap.put("ceoNm", ceoNm);
		paramMap.put("busiType", busiType);
		paramMap.put("busiClass", busiClass);
		paramMap.put("zipCode", zipCode);
		paramMap.put("woplaceAddress", woplaceAddress);
		paramMap.put("woplaceAddressDetail", woplaceAddressDetail);
		paramMap.put("wireTel", wireTel);
		paramMap.put("attachFile", attachFile);
		paramMap.put("domainName", domainName);
		
		// 유큐브 파라미터
		paramMap.put("custKdCd", custKdCd);
		if(!"GEX".equals(custKdCd) && !"II".equals(custKdCd)) {
			paramMap.put("ctype", "1");					// 기본
			paramMap.put("corpNo", custrnmNo);			// 법인번호
		} else {
			paramMap.put("ctype", "2");					// 개인
			paramMap.put("persNo", custrnmNo);			// 개인 사업자 주민번호(YYYYMMDD)
		}
		paramMap.put("cmpNm", corpNm);
		paramMap.put("coInfo1", coInfo);
		paramMap.put("genderCode", genderCode);
		paramMap.put("ceoNm", ceoNm);
		paramMap.put("vatExmptKdCd", "N");
		
		try {
			signUpSvc.insertSignUp(paramMap);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("회원 가입에 실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	// 사용 약관 가져오기
	@PostMapping("/selectUseTerms")
	public RestResult<?> selectUseTerms(@RequestBody Map<String, Object> params) throws Exception {
		return signUpSvc.selectUseTerms(params);
	}
	
	// api 통신 테스트
	@PostMapping("/apiTest")
	public void apiTest () throws Exception {
		
		// get 예제 브랜드 카테고리 조회
		Map<String, Object> getHeaderMap = new HashMap<String, Object>();
		getHeaderMap.put("apiId", "111");
		getHeaderMap.put("apiSercret", "111");
		getHeaderMap.put("svcId", "WEB01");
		Map<String, Object> getResult = apiInterface.get("/console/v1/brand/categories", getHeaderMap);
		
		// post 예제 카카오 알림톡 발송 API
		Map<String, Object> postHeaderMap = new HashMap<String, Object>();
		String svcId = "WEB01";
		postHeaderMap.put("apiKey", "111");
		postHeaderMap.put("svcId", "WEB01");
		
		Map<String, Object> postParamMap = new HashMap<String, Object>();
		postParamMap.put("callback" , "15441234");
		
		Map<String, Object> postResult = apiInterface.post("/console/v1/alimtalk/" + svcId, postParamMap, postHeaderMap);
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
		String sReturnUrl = "http://localhost:3000/checkPlusSuccess";      // 성공시 이동될 URL
		String sErrorUrl = "http://localhost:3000/checkPlusFail";          // 실패시 이동될 URL
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
		}
		
		rtn.setData(niceCheckMap);
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
	
	@PostMapping("/getNiceCheckSuccess")
	public RestResult<?> getNiceCheckSuccess(
			@RequestBody Map<String, Object> params,
			HttpServletRequest request){
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> niceCheckMap = new HashMap<String, Object>();
		
		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
//		String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");
		String sEncodeData =  requestReplace(CommonUtils.getString(params.get("EncodeData")), "encodeData");
		String sSiteCode = this.sSiteCode;				// NICE로부터 부여받은 사이트 코드
		String sSitePassword = this.sSitePassword;			// NICE로부터 부여받은 사이트 패스워드
		
		String sCipherTime = "";			// 복호화한 시간
		String sRequestNumber = "";			// 요청 번호
		String sResponseNumber = "";		// 인증 고유번호
		String sAuthType = "";				// 인증 수단
		String sName = "";					// 성명
		String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)
		String sConnInfo = "";				// 연계정보 확인값 (CI_88 byte)
		String sBirthDate = "";				// 생년월일(YYYYMMDD)
		String sGender = "";				// 성별
		String sNationalInfo = "";			// 내/외국인정보 (개발가이드 참조)
		String sMobileNo = "";				// 휴대폰번호
		String sMobileCo = "";				// 통신사
		String sMessage = "";
		String sPlainData = "";

		int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);
		
		if( iReturn == 0 ) {
			sPlainData = niceCheck.getPlainData();
			sCipherTime = niceCheck.getCipherDateTime();
			
			// 데이타를 추출합니다.
			HashMap mapresult = niceCheck.fnParse(sPlainData);
			
			sRequestNumber  = (String)mapresult.get("REQ_SEQ");
			sResponseNumber = (String)mapresult.get("RES_SEQ");
			sAuthType		= (String)mapresult.get("AUTH_TYPE");
			sName			= (String)mapresult.get("NAME");
//			sName			= (String)mapresult.get("UTF8_NAME"); //charset utf8 사용시 주석 해제 후 사용
			sBirthDate		= (String)mapresult.get("BIRTHDATE");
			sGender			= (String)mapresult.get("GENDER");
			sNationalInfo  	= (String)mapresult.get("NATIONALINFO");
			sDupInfo		= (String)mapresult.get("DI");
			sConnInfo		= (String)mapresult.get("CI");
			sMobileNo		= (String)mapresult.get("MOBILE_NO");
			sMobileCo		= (String)mapresult.get("MOBILE_CO");
			
			// 유큐브 고객사 등록시 남자 1 / 여자 2
			if("0".equals(sGender)) {
				sGender = "2";
			}
			
			Object session_sRequestNumber = request.getSession(true).getAttribute("REQ_SEQ");
//			String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
			if(!sRequestNumber.equals(session_sRequestNumber)) {
				sMessage = "세션값 불일치 오류입니다.";
				sResponseNumber = "";
				sAuthType = "";
			} else { 
				niceCheckMap.put("sCipherTime", sCipherTime);
				niceCheckMap.put("sRequestNumber", sRequestNumber);
				niceCheckMap.put("sResponseNumber", sResponseNumber);
				niceCheckMap.put("sAuthType", sAuthType);
				niceCheckMap.put("sName", sName);
				niceCheckMap.put("sBirthDate", sBirthDate);
				niceCheckMap.put("sGender", sGender);
				niceCheckMap.put("sNationalInfo", sNationalInfo);
				niceCheckMap.put("sDupInfo", sDupInfo);
				niceCheckMap.put("sConnInfo", sConnInfo);
				niceCheckMap.put("sMobileNo", sMobileNo);
				niceCheckMap.put("sMobileCo", sMobileCo);
				
				rtn.setData(niceCheckMap);
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
		
		if(sMessage.length() > 0) {
			rtn.setSuccess(false);
			rtn.setMessage(sMessage);
		}
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
	
	/**
	 * 사업자 번호로 고객사 리스트 조회
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/selectCorpCustList")
	public RestResult<?> selectCorpCustList(
			@RequestBody Map<String, Object> params){
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = (RestResult<Object>) signUpSvc.selectCorpCustList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
	}
	
	/**
	 * 선택한 고객 정보
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/selectSelCorpCustInfo")
	public RestResult<?> selectSelCorpCustInfo(
			@RequestBody Map<String, Object> params){
		RestResult<Object> rtn = new RestResult<Object>();
		
		try {
			rtn = (RestResult<Object>) signUpSvc.selectSelCorpCustInfo(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/selectCustAddr")
	public RestResult<?> selectCustAddr(
			@RequestBody Map<String, Object> params){
		RestResult<Object> rtn = new RestResult<Object>();
		
		try {
			rtn = (RestResult<Object>) signUpSvc.selectCustAddr(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
	}
}
 