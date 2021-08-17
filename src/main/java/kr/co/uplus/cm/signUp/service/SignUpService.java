package kr.co.uplus.cm.signUp.service;

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
import org.springframework.mail.javamail.JavaMailSender;
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
import kr.co.uplus.cm.utils.MailHandler;
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
	
	@Value("${spring.mail.username}")
	String mailHost;
	
	@Value("${console.domain.baseUrl}") String baseUrl;
	
	@Autowired
	private JavaMailSender mailSender;

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> insertSignUp(Map<String, Object> params) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		RestResult<Object> rtn = new RestResult<Object>(true);
		paramMap.putAll(params);
		
		// userid
		String userId =  CommonUtils.getCommonId("MBR", 7);
		paramMap.put("userId", userId);
		paramMap.put("cmRole", "OWNER");

		try {
			// 아이디 중복 check
			int cnt = generalDao.selectGernalCount(DB.QRY_SELECT_USER_DUPC_CNT, paramMap);
			
			if(cnt > 0) {
				rtn.setSuccess(false);
				rtn.setMessage("이미 등록된 아이디입니다.");
				return rtn;
			}
			
			// 사용자 비밀번호 암호화
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16];
			random.nextBytes(bytes);
			String salt = new String(Base64.getEncoder().encode(bytes));
			paramMap.put("salt", salt);
			
			SHA sha256 = new SHA(256);
			String encPwd = sha256.encryptToBase64(salt+ CommonUtils.getString(paramMap.get("password")));
			paramMap.put("password", encPwd);
			
			// 고객 번호
			String custNo = CommonUtils.getString(paramMap.get("custNo"));
			int existCustNo = generalDao.selectGernalCount(DB.QRY_SELECT_EXISTS_CUSTNO, paramMap);
			
			if(existCustNo > 0) {
				rtn.setSuccess(false);
				rtn.setMessage("이미 등록된 고객사입니다. 신규 고객사를 등록해주세요.");
				return rtn;
			}

			// corp_id
			String corpId = CommonUtils.getCommonId("COM", 7);
			paramMap.put("corpId", corpId);

			generalDao.insertGernal(DB.QRY_INSERT_CM_USER, paramMap);
			
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
				apiBodyMap.put("cmpNm",			paramMap.get("cmpNm"));			// 가입자(상호)명
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
					rtn.setSuccess(false);
					rtn.setMessage(CommonUtils.getString(((Map<String, Object>) result.get("data")).get("resultMsg")+"\n회원가입에 실패하였습니다."));
					return rtn;
				}
			}
			
			// 사업자 등록증 첨부파일
			MultipartFile file = (MultipartFile) paramMap.get("attachFile");
			
			String fileSeq = "";
			
			String uploadDirPath = FileConfig.getFilePath(FileConfig.FileSvcType.BIZ_REG_CARD);
			
			if(file != null) {
				fileSeq = commonService.uploadFile(file, userId, uploadDirPath);

				paramMap.put("fileId", fileSeq);
			}
			
			// 고객사 등록
			generalDao.insertGernal(DB.QRY_INSERT_CM_CORP, paramMap);
			
			// redis update
			commonService.updateCmCmdForRedis("CM_CORP");
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("회원 가입에 실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
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
			cal.add(Calendar.HOUR, 1);
			String				time		= sdformat.format(cal.getTime());
			params.put("time", time);
			
			generalDao.insertGernal(DB.QRY_INSERT_MAIL_CERTIFY, params);
			
			params.put("location", "/sign/signUpMain");
			
//			this.sendMail(params, "/sign/signUpMain");
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
		
		if("10000".equals(result.get("code"))) {
			Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
			List<Map<String, Object>> list =  (List<Map<String, Object>>) dataMap.get("resultList");
			rtn.setData(list);
		} else {
			throw new Exception(CommonUtils.getString(result.get("message")));
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
		
		if("10000".equals(result.get("code"))) {
			Map<String, Object> data = (Map<String, Object>) result.get("data");
			data.put("rtnCustNm", CommonUtils.setMaskingUserNm(CommonUtils.getString(data.get("custNm"))));
			rtn.setData(data);
		} else {
			throw new Exception(CommonUtils.getString(result.get("message")));
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
		
		return rtn;
	}

	@Deprecated
	public void sendMail(Map<String, Object> params, String location) throws Exception {
		String time = CommonUtils.getString(params.get("time"));
		String[] timeArr = time.split(" ");
		
		MailHandler mailHandler = new MailHandler(mailSender);
		
		// 받는 사람
		mailHandler.setTo(CommonUtils.getString(params.get("email")));
		// 보내는 사람
		mailHandler.setFrom(mailHost);
		// 제목
		mailHandler.setSubject("이메일 본인인증");
		
		String html = "";
		
		html += "<div style='width:640px; min-height:600px; margin:0 auto; background:#fff; padding:38px 64px 87px 64px; box-sizing:border-box; position:relative; font-family:\"Noto Sans KR\", sans-serif'>";
		html += "<div style='border-bottom:1px solid #9F9F9F; padding-bottom:30px; margin-bottom:30px'>";
		html += "<img src='" + this.baseUrl + "/se2/images/" + "userLogo.svg' alt='유플러스 통합메시징 클라우드' />";
		html += "</div>";
		html += "<div style='font-size:14px; line-height:24px'>이메일 인증을 진행해주세요.<br/>";
		html += "안녕하세요. NHN Cloud를 이용해주셔서 감사합니다.<br/>";
		html += "본인 이메일이 맞는지 확인하고 있습니다.<br/>";
		html += "아래 [인증] 버튼을 클릭하면 다음 단계로 진행할 수 있습니다.<br/><br/>";
		html += "인증 링크 유효시간 : " + timeArr[0];
		html += "<span style='color:#FD004E'>" + timeArr[1];
		html += "</span> (UTC+09:00)</div>";
		html += "<a href='"+this.baseUrl+location+"?authKey="+params.get("authKey")+"' target='_blank' title='인증' style='display:inline-block; width:120px; text-align:center; background:#E93978; color:#fff; border-radius:4px; line-height:40px; text-decoration:none; font-size:16px; margin:40px 0;'>인증</a>";
		html += "<div style='position:absolute; left:0; bottom:0; width:100%; box-sizing:border-box; padding:0 64px'>";
		html += "<div style='border-top:1px solid #E6E6E6; color:#858585; font-size:11px; padding:20px 0;'>Copyright©LG Plus Corp. All Rights Reserved.</div>";
		html += "</div>";
		html += "</div>";
		
		mailHandler.setText(html, true);
		
		mailHandler.send();
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
	
}
