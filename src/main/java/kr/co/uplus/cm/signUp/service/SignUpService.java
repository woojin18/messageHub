package kr.co.uplus.cm.signUp.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.config.FileConfig;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import kr.co.uplus.cm.xss.XssPreventer;
import lombok.extern.log4j.Log4j2;
import yoyozo.security.SHA;

@Log4j2
@Service
public class SignUpService {

	@Autowired
	private GeneralDao generalDao;

	@Autowired
	private ApiInterface apiInterface;
	
	@Autowired
	private CommonService commonService;
	
	@Value("${console.domain.baseUrl}") String baseUrl;

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void insertSignUp(Map<String, Object> params) throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		RestResult<Object> rtn = new RestResult<Object>(true);
		paramMap.putAll(params);
		
		// userid
		String userId =  CommonUtils.getCommonId("MBR", 7);
		paramMap.put("userId", userId);
		paramMap.put("cmRole", "OWNER");

		// escape
		String corpNm = CommonUtils.getString(paramMap.get("corpNm"));		// 사업자명
		corpNm = XssPreventer.unescape(corpNm);
		paramMap.put("corpNm", corpNm);
		
		String ceoNm = CommonUtils.getString(paramMap.get("ceoNm"));		// 대표자명
		ceoNm = XssPreventer.unescape(ceoNm);
		paramMap.put("ceoNm", ceoNm);
		
		String woplaceAddress = CommonUtils.getString(paramMap.get("woplaceAddress"));		// 주소
		woplaceAddress = XssPreventer.unescape(woplaceAddress);
		paramMap.put("woplaceAddress", woplaceAddress);
		
		String woplaceAddressDetail = CommonUtils.getString(paramMap.get("woplaceAddressDetail"));	// 상세주소
		woplaceAddressDetail = XssPreventer.unescape(woplaceAddressDetail);
		paramMap.put("woplaceAddressDetail", woplaceAddressDetail);
		
		String upjongNm = CommonUtils.getString(paramMap.get("upjongNm"));		// 업종명
		upjongNm = XssPreventer.unescape(upjongNm);
		paramMap.put("upjongNm", upjongNm);
		
		String uptaeNm = CommonUtils.getString(paramMap.get("uptaeNm"));		// 업태명
		uptaeNm = XssPreventer.unescape(uptaeNm);
		paramMap.put("uptaeNm", uptaeNm);

		if(CommonUtils.getString(paramMap.get("loginId")).length() > 100) {
			throw new Exception("아이디는 100자 이상 등록하실 수 없습니다.");
		}
		// 아이디 중복 check
		int cnt = generalDao.selectGernalCount(DB.QRY_SELECT_USER_DUPC_CNT, paramMap);
		if(cnt > 0) {
			throw new Exception("이미 등록된 아이디입니다.");
		}
		
		// 비밀번호 유효성 검사
		try {
			commonService.pwdResularExpressionChk(CommonUtils.getString(paramMap.get("password")));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		// 사용자 비밀번호 암호화
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16];
			random.nextBytes(bytes);
			String salt = new String(Base64.getEncoder().encode(bytes));
			paramMap.put("salt", salt);
			
			SHA sha512 = new SHA(512);
			String encPwd = sha512.encryptToBase64(salt+ CommonUtils.getString(paramMap.get("password")));
			paramMap.put("password", encPwd);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("회원 가입에 실패하였습니다.");
		}
		
		
		// corp_id
		String corpId = CommonUtils.getCommonId("COM", 7);
		paramMap.put("corpId", corpId);

		// 기존 삭제처리된 사용자 LOGIN 아이디 변경
		generalDao.updateGernal(DB.QRY_UPDATE_DEL_USER_LOGINID, params);
		generalDao.insertGernal(DB.QRY_INSERT_CM_USER, paramMap);

		// 고객 번호
		String custNo = CommonUtils.getString(paramMap.get("custNo"));
		
		// 고객사 중복 허용 여부 확인
		String dupliCustYn = CommonUtils.getString(generalDao.selectGernalObject("signUp.selectCustNoDuplicateYn", paramMap));
		
		if("N".equals(dupliCustYn)) {
			int existCustNo = generalDao.selectGernalCount(DB.QRY_SELECT_EXISTS_CUSTNO, paramMap);
			
			if(existCustNo > 0) {
				throw new Exception("이미 등록된 고객사입니다. 신규 고객사를 등록해주세요.");
			}
		}
		
		// 고객번호가 없는 경우 신규 고객사 등록
		if("".equals(custNo)) {
			Map<String, Object> codeMap = new HashMap<String, Object>();
			codeMap.put("codeTypeCd", "CORP_CUST_UPJONG");
			codeMap.put("useYn", "Y");
			codeMap.put("codeName1", CommonUtils.getString(paramMap.get("busiClass")));

			// 업종 코드 조회
			String upjongCd = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_CODEVAL1_BY_CODENAME1, codeMap));

			Map<String, Object> apiBodyMap = new HashMap<String, Object>();
			apiBodyMap.put("custKdCd",		paramMap.get("custKdCd"));		// 고객유형
			apiBodyMap.put("ctype",			paramMap.get("ctype"));			// 기본 / 개인
			apiBodyMap.put("corpNo",		paramMap.get("corpNo"));		// 법인번호
			apiBodyMap.put("persNo",		paramMap.get("persNo"));		// 주민번호
			apiBodyMap.put("cmpNm",			paramMap.get("corpNm"));			// 가입자(상호)명
			apiBodyMap.put("zipcode",		paramMap.get("zipCode"));		// 우편번호
			apiBodyMap.put("juso",			paramMap.get("woplaceAddress"));			// 주소
			apiBodyMap.put("juso2",			paramMap.get("woplaceAddressDetail"));			// 상세주소
			apiBodyMap.put("damEmail",		paramMap.get("loginId"));		// 담당자 이메일
			apiBodyMap.put("coInfo1",		paramMap.get("coInfo1"));		// 본인인증 토큰(개인사업자 필수)
			apiBodyMap.put("genderCode",	paramMap.get("genderCode"));	// 성별
			apiBodyMap.put("corpTel",		paramMap.get("wireTel"));		// 대표 전화번호
			apiBodyMap.put("regNo",			paramMap.get("regno"));			// 사업자 번호
			apiBodyMap.put("ceoNm",			paramMap.get("ceoNm"));			// 대표자
			apiBodyMap.put("uptaeNm",		paramMap.get("busiType"));		// 업태명
			apiBodyMap.put("upjongNm",		paramMap.get("busiClass"));		// 업종명
			apiBodyMap.put("upjong",		upjongCd);						// 업종
			apiBodyMap.put("vatExmptKdCd",	paramMap.get("vatExmptKdCd"));	// 부가세 면제
			
			Map<String, Object> result = apiInterface.post("/console/v1/ucube/customer", null, apiBodyMap, null);
			if("10000".equals(result.get("code"))) {
				custNo = CommonUtils.getString(((Map<String, Object>) result.get("data")).get("resultMsg"));
				paramMap.put("custNo", custNo);
			} else {
				throw new Exception(CommonUtils.getString(((Map<String, Object>) result.get("data")).get("resultMsg")+"\n회원가입에 실패하였습니다."));
			}
		}
		
		// 사업자 등록증 첨부파일
		MultipartFile file = (MultipartFile) paramMap.get("attachFile");
		MultipartFile file1 = (MultipartFile) paramMap.get("attachFile1");
		MultipartFile file2 = (MultipartFile) paramMap.get("attachFile2");
		MultipartFile file3 = (MultipartFile) paramMap.get("attachFile3");
		
		String fileSeq = "";
		String fileSeq1 = "";
		String fileSeq2 = "";
		String fileSeq3 = "";
		
		String uploadDirPath = FileConfig.getFilePath(FileConfig.FileSvcType.BIZ_REG_CARD);

		try {
			if(file != null) {
				fileSeq = commonService.uploadFile(file, userId, uploadDirPath);
				fileSeq1 = commonService.uploadFile(file1, userId, uploadDirPath);
				fileSeq2 = commonService.uploadFile(file2, userId, uploadDirPath);
				paramMap.put("fileId", fileSeq);
				paramMap.put("fileId1", fileSeq1);
				paramMap.put("fileId2", fileSeq2);
				// 대리인 위임장은 필수값이 아니므로 case 처리
				
				if(file3 != null) {
					fileSeq3 = commonService.uploadFile(file3, userId, uploadDirPath);
					paramMap.put("fileId3", fileSeq3);
				}
			}
		} catch (Exception e) {
			throw new Exception("회원 가입에 실패하였습니다.");
		}
		String salesMan = CommonUtils.getString(paramMap.get("salesMan"));
		// 영업사원 미선택인 경우 박진표 default
		if("".equals(salesMan) || salesMan == null) {
//			salesMan = "pjp1440";
//			default : 코드타입이 'SALES_MAN'인 코드 val 3이 'Y' 값인 영업사원
			salesMan = CommonUtils.getString(generalDao.selectGernalObject("signUp.selectDefaultSalesManId", paramMap));
		}
		paramMap.put("salesMan", salesMan);
		
		// 고객사 등록
		generalDao.insertGernal(DB.QRY_INSERT_CM_CORP, paramMap);
		
		// redis update
		commonService.updateCmCmdForRedis("CM_CORP");
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void insertEmailCertify(Map<String, Object> params) throws Exception{
		
		params.put("loginId", params.get("email"));
		
		int cnt = generalDao.selectGernalCount(DB.QRY_SELECT_USER_DUPC_CNT, params);
		if(cnt > 0) {
			throw new Exception("이미 회원가입이 완료된 이메일입니다.");
		} else {
			// 난수 생성은 이전 로직에서 처리해야됨 이메일 발송모듈 추가후 삭제 예정 _서동욱
			String randomNum = CommonUtils.randomGeneration(10);
			params.put("authKey", randomNum);
			
			SimpleDateFormat	sdformat	= new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			Calendar			cal			= Calendar.getInstance();
			cal.add(Calendar.HOUR, 24);
			String				time		= sdformat.format(cal.getTime());
			params.put("time", time);
			
			generalDao.insertGernal(DB.QRY_INSERT_MAIL_CERTIFY, params);
			
			params.put("location", "/sign/signUpMain");
			
			commonService.sendNoti("mail", params);

		}
	}

	public RestResult<?> selectUseTerms(Map<String, Object> params) throws Exception {
		RestResult<Object>	rtn = new RestResult<Object>();
		Map<String, Object>	rtnResult = new HashMap<String, Object>();
		String				srcTermsCd = CommonUtils.getString(params.get("srcTermsCd"));
		// 현재 약관
		Object rtnMap = generalDao.selectGernalObject(DB.QRY_SELECT_CM_USE_TERMS, params);
		rtnResult.put("useTerms", rtnMap);
		
		// 서비스 이용약관 일경우에만 예전 약관을 가져오도록 처리
		if("SVC".equals(srcTermsCd)) {
			// 예전 약관
			List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CM_USE_TERMS_PRI_VERSION, params);
			rtnResult.put("preUseTerms", rtnList);
		}
		
		rtn.setData(rtnResult);

		return rtn;
		
	}

	@SuppressWarnings("unchecked")
	public RestResult<?> selectCorpCustList(Map<String, Object> params) throws Exception {
		RestResult<Object>	rtn = new RestResult<Object>();
		Map<String, Object> headerMap = new HashMap<String, Object>();
		String regno	= CommonUtils.getString(params.get("regno"));		// 사업자번호
		headerMap.put("mode",		"BS");		// BS 사업자 번호
		headerMap.put("searchNo",	regno);
		
		// API 통신 처리
		Map<String, Object> result = apiInterface.get("/console/v1/ucube/customer/"+regno+"/mode/BS", headerMap);
		if(result == null) {
			throw new Exception("고객사 목록 조회 중 오류가 발생하였습니다.");
		} else {
			if("10000".equals(result.get("code"))) {
				Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
				List<Map<String, Object>> list =  (List<Map<String, Object>>) dataMap.get("resultList");
				rtn.setData(list);
			} else {
				throw new Exception(CommonUtils.getString(result.get("message")));
			}
		}
		
		return rtn;
	}

	@SuppressWarnings("unchecked")
	public RestResult<?> selectSelCorpCustInfo(Map<String, Object> params) throws Exception {
		RestResult<Object>	rtn = new RestResult<Object>();
		Map<String, Object> headerMap = new HashMap<String, Object>();
		String custNo	= CommonUtils.getString(params.get("custNo"));		// 고객번호
		headerMap.put("custNo", custNo);
		
		// API 통신 처리
		Map<String, Object> result = apiInterface.get("/console/v1/ucube/customer/"+custNo, headerMap);
		if(result == null) {
			throw new Exception("고객사 조회 중 오류가 발생하였습니다.");
		} else {
			if("10000".equals(result.get("code"))) {
				Map<String, Object> data = (Map<String, Object>) result.get("data");
				data.put("rtnCustNm", CommonUtils.setMaskingUserNm(CommonUtils.getString(data.get("custNm"))));
				rtn.setData(data);
			} else {
				throw new Exception(CommonUtils.getString(result.get("message")));
			}
		}
		
		return rtn;
	}

	@SuppressWarnings("unchecked")
	public RestResult<Object> selectCustAddr(Map<String, Object> params) throws Exception {
		RestResult<Object>	rtn = new RestResult<Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("type", "R");		// 도로명
		paramMap.put("roadNm", CommonUtils.getString(params.get("roadNm")));		// 도로명
		paramMap.put("sggBldNm", CommonUtils.getString(params.get("sggBldNm")));	// 건물명
		paramMap.put("bldRpstNo", CommonUtils.getString(params.get("bldRpstNo")));	// 건물지번
		
		// API 통신 처리
		Map<String, Object> result = apiInterface.get("/console/v1/ucube/juso", paramMap, null);
		if(result == null) {
			throw new Exception("주소 목록 중 오류가 발생하였습니다.");
		} else {
			if("10000".equals(result.get("code"))) {
				if(CommonUtils.isEmptyObject(result.get("data"))) {
					throw new Exception("조회된 주소가 없습니다.");
				} else {
					List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("data");
					rtn.setData(list);
				}
			} else {
				throw new Exception(CommonUtils.getString(result.get("message")));
			}
		}
		
		return rtn;
	}

	public RestResult<Object> certifyMailByAuthKey(Map<String, Object> params) throws Exception {
		RestResult<Object>	rtn = new RestResult<Object>();
		
		Map<String, Object> certifyMap = (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_CHK_MAIL_CERTIFY_BY_AUTHKEY, params);
		if(!certifyMap.isEmpty()) {
			// 유효 만료 시간 여부 확인
			SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String formatTime = format.format(now);
			
			// 현재시간
			Date nowDt = format.parse(formatTime);
			// 유효시간
			Date certifyDt = format.parse(CommonUtils.getString(certifyMap.get("mailCertifyDt")));
			if(certifyDt.getTime() < nowDt.getTime()) {
				rtn.setSuccess(false);
				rtn.setMessage("이메일 인증 유효시간은 1시간 입니다.");
				return rtn;
			}
			rtn.setSuccess(true);
			rtn.setData(certifyMap);
		} else {
			rtn.setSuccess(false);
			rtn.setMessage("메일 인증 실패하였습니다.");
		}
		return rtn;
	}

	public RestResult<Object> selectSalesManList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CODE, params);
		
		// 영업사원 핸드폰 번호 마스킹 처리
		if("SALES_MAN".equals(params.get("codeTypeCd"))) {
			for(Object rtnObj : rtnList) {
				Map<String, Object> rtnMap = (Map<String, Object>) rtnObj;
				String maskPNum = "";
				String pNum = CommonUtils.getString(rtnMap.get("codeVal4"));
				if(pNum.contains("-")) {
					// 010-0000-0000
					String maskMid = "";
					String maskLast = "";
					String[] pNumArr = pNum.split("-");
					for(int i = 0; i < pNumArr[1].length(); i++) {
						maskMid += "*";
					}
					maskLast += "**"+pNumArr[2].substring(pNumArr[2].length()-2, pNumArr[2].length());
					maskPNum = pNumArr[0]+"-"+maskMid+"-"+maskLast;
					
					rtnMap.put("codeVal4", maskPNum);
				} else {
					// 01000000000
					String firstPNum = "";
					String midPNum = "";
					String lastPNum = "";
					
					firstPNum = pNum.substring(0, 3);
					midPNum = pNum.substring(3, pNum.length()-4);
					lastPNum = pNum.substring(pNum.length()-4, pNum.length());
					
					String maskMid = "";
					String maskLast = "";
					for(int i = 0; i < midPNum.length(); i++) {
						maskMid += "*";
					}
					maskLast += "**"+lastPNum.substring(lastPNum.length()-2, lastPNum.length());
					maskPNum = firstPNum +"-"+ maskMid +"-"+ maskLast;
					
					rtnMap.put("codeVal4", maskPNum);
				}
			}
		}
		rtn.setData(rtnList);

		return rtn;
	}
	
}
