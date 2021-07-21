package kr.co.uplus.cm.signUp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
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

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void insertSignUp(Map<String, Object> params) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
				
		paramMap.putAll(params);
		
		// userid
		String userId =  CommonUtils.getCommonId("MBR", 7);
		paramMap.put("userId", userId);
		paramMap.put("loginId", paramMap.get("loginId"));
		paramMap.put("cmRole", "OWNER");
		
		// 사용자 비밀번호 암호화
		SHA sha512 = new SHA(512);
		String encPwd = sha512.encryptToBase64(CommonUtils.getString(paramMap.get("password")));
		paramMap.put("password", encPwd);
		
		// corp_id
		String corpId = CommonUtils.getCommonId("COM", 7);
		paramMap.put("corpId", corpId);
		
		generalDao.insertGernal(DB.QRY_INSERT_CM_USER, paramMap);
		
		// 고객 번호
		String custNo = CommonUtils.getString(paramMap.get("custNo"));
		
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
				throw new Exception(CommonUtils.getString(((Map<String, Object>) result.get("data")).get("resultMsg")));
			}

			// 사업자 등록증 첨부파일
			MultipartFile file = (MultipartFile) paramMap.get("attachFile");
			
			String fileSeq = "";
			
			String uploadDirPath = FileConfig.getFilePath(FileConfig.FileSvcType.BIZ_REG_CARD);
			
			if(file != null) {
				fileSeq = commonService.uploadFile(file, null, uploadDirPath);

				paramMap.put("fileId", fileSeq);
			}
		}
		// 고객사 등록
		 generalDao.insertGernal(DB.QRY_INSERT_CM_CORP, paramMap);
		
		// redis update
		commonService.updateCmCmdForRedis("CM_CORP");
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void insertEmailUser(Map<String, Object> params) throws Exception {
		// 난수 생성은 이전 로직에서 처리해야됨 이메일 발송모듈 추가후 삭제 예정 _서동욱
		String randomNum = CommonUtils.randomGeneration(10);
		params.put("ranDomNum", randomNum);
		params.put("cmRole", "OWNER");
		
		generalDao.insertGernal(DB.QRY_INSERT_CM_USER, params);
		
		
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
	
}
