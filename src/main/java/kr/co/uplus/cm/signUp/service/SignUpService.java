package kr.co.uplus.cm.signUp.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kong.unirest.json.JSONArray;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.crypto.AesEncryptor;
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
		
		// 사업자 등록증 첨부파일
		MultipartFile file = (MultipartFile) paramMap.get("attachFile");
		String ext = commonService.getFileNameExt(file.getOriginalFilename(),1);
		if (!(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("gif") || ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("tiff"))) {
			throw new Exception("파일은 jpg, jpeg, pdf, gif, png, tiff만 허용됩니다.");
		}
		if (file.getSize() > 5 * 1024 * 1024) {
			throw new Exception("파일은 5M 이하 파일만 허용됩니다.");
		}
		MultipartFile file1 = (MultipartFile) paramMap.get("attachFile1");
		ext = commonService.getFileNameExt(file1.getOriginalFilename(),1);
		if (!(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("gif") || ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("tiff"))) {
			throw new Exception("파일은 jpg, jpeg, pdf, gif, png, tiff만 허용됩니다.");
		}
		if (file1.getSize() > 5 * 1024 * 1024) {
			throw new Exception("파일은 5M 이하 파일만 허용됩니다.");
		}
		MultipartFile file2 = (MultipartFile) paramMap.get("attachFile2");
		ext = commonService.getFileNameExt(file2.getOriginalFilename(),1);
		if (!(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("gif") || ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("tiff"))) {
			throw new Exception("파일은 jpg, jpeg, pdf, gif, png, tiff만 허용됩니다.");
		}
		if (file2.getSize() > 5 * 1024 * 1024) {
			throw new Exception("파일은 5M 이하 파일만 허용됩니다.");
		}
		MultipartFile file3 = (MultipartFile) paramMap.get("attachFile3");
		if (file3 != null) {
			ext = commonService.getFileNameExt(file3.getOriginalFilename(),1);
			if (!(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("gif") || ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("tiff"))) {
				throw new Exception("파일은 jpg, jpeg, pdf, gif, png, tiff만 허용됩니다.");
			}
			if (file3.getSize() > 5 * 1024 * 1024) {
				throw new Exception("파일은 5M 이하 파일만 허용됩니다.");
			}
		}
		
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
		
		if ("POST".equals(params.get("feeType"))) {
			// 청구정보 등록 2022-01-10
			paramMap.put("billId", "0");
			paramMap.put("billName", paramMap.get("corpNm")); //청구유형이PROJECT면 고객사명
			paramMap.put("billStatus", "REQ");
			// 월 예상금액 계산
			List productList = generalDao.selectGernalList("use.selectProductUnit", null);
			double smsPrice = 0;
			double rcsPrice = 0;
			double kkoPrice = 0;
			double pushPrice = 0;
			for (Object obj : productList) {
				Map data = (Map) obj;
				if ("SMS/MMS".equals(data.get("chGrp"))) {
					smsPrice = (double) data.get("avgFee");
				} else if ("RCS".equals(data.get("chGrp"))) {
					rcsPrice = (double) data.get("avgFee");
				} else if ("KKO".equals(data.get("chGrp"))) {
					rcsPrice = (double) data.get("avgFee");
				} else if ("PUSH".equals(data.get("chGrp"))) {
					rcsPrice = (double) data.get("avgFee");
				}
			}
			int monthExpAmount = (int) (Integer.parseInt(params.get("smsExpCnt").toString()) * smsPrice + Integer.parseInt(params.get("rcsExpCnt").toString()) * rcsPrice + Integer.parseInt(params.get("kkoExpCnt").toString()) * kkoPrice + Integer.parseInt(params.get("pushExpCnt").toString()) * pushPrice);

			paramMap.put("monthExpAmount", monthExpAmount);
			
			AesEncryptor encrypt = new AesEncryptor(); // 암호화
			if (StringUtils.isNotEmpty((String) paramMap.get("napJumin"))) {
				paramMap.put("napJumin", encrypt.encrypt((String) paramMap.get("napJumin")));
			}
			if (StringUtils.isNotEmpty((String) paramMap.get("bankNo"))) {
				paramMap.put("bankNo", encrypt.encrypt((String) paramMap.get("bankNo")));
			}
			if (StringUtils.isNotEmpty((String) paramMap.get("cardNo"))) {
				paramMap.put("cardNo", encrypt.encrypt((String) paramMap.get("cardNo")));
			}
			if (StringUtils.isNotEmpty((String) paramMap.get("cardValdEndYymm"))) {
				paramMap.put("cardValdEndYymm", encrypt.encrypt((String) paramMap.get("cardValdEndYymm")));
			}
			generalDao.insertGernal("signUp.insertBill", paramMap);
			
		}
		
		// redis update
		// 2020-02-09 스킵 처리 
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
		Map<String, Object> result = apiInterface.get("/console/v1/ucube/customer2/"+regno+"/mode/BS", headerMap);
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
				rtn.setMessage("이메일 인증 유효시간은 24시간 입니다.");
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
	
	//채널 리스트 조회
			@SuppressWarnings("unchecked")
			public RestResult<?> selectStaInformation(Map<String, Object> params) throws Exception {
				RestResult<Object> rtn = new RestResult<Object>();
				rtn.setSuccess(true);
				
				List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_STANDARD_INFO, params);

//				System.out.println(rtnList);
//				List<Object> j_rtnList = new ArrayList<Object>();
				List<Map<String,String>> newhm = new ArrayList<Map<String,String>>();
				if(!rtnList.toString().equals("[null]")){
					for(int i=0; i<rtnList.size(); i++) {		//사실상 1
						try {
							HashMap<String,String> hmrtn = (HashMap<String, String>) rtnList.get(i);
							String rtnStr = hmrtn.get("COMMON_MGR_INFO");
							kong.unirest.json.JSONObject rtnJo = new kong.unirest.json.JSONObject(rtnStr);
							String rtnJson = CommonUtils.getString(rtnJo.get("commonLst"));
							
//							JSONArray ja = new JSONArray(rtnStr);
							JSONArray ja = new JSONArray(rtnJson);
							
							if(rtnStr != null) {
								for(int j=0; j<ja.length(); j++) {
									Map<String, String> joMap = new HashMap<String, String>();
									Iterator<String> keys = ((kong.unirest.json.JSONObject)ja.get(j)).keySet().iterator();
									kong.unirest.json.JSONObject jo = (kong.unirest.json.JSONObject) ja.get(j);
									while(keys.hasNext()) {
										String key = keys.next();
										String value = CommonUtils.getString(((kong.unirest.json.JSONObject)ja.get(j)).get(key));
										joMap.put(key, CommonUtils.getString(value));
									}
									newhm.add(joMap);
								}
								rtn.setData(newhm);
							}
						}catch(Exception e) {
							rtn.setSuccess(false);
							rtn.setMessage("조회에 실패했습니다");
							e.printStackTrace();
						}		
					}
				}

				return rtn;
			}
		
		public String saveBillUcubeInfo(Map<String, Object> params) throws Exception {

				List<Object> rtnList = new ArrayList<>();
				
				List<Object> userList = generalDao.selectGernalList(DB.QRY_SELECT_CMUSER_CORPID, params);
				Map<String,Object> userMap = (Map<String, Object>) userList.get(0);
				String corpId = userMap.get("CORP_ID").toString();
				params.put("handleId", userMap.get("REG_ID").toString());
				
				params.put("corpId", corpId);
				
				int monthExpAmount = (int) generalDao.selectGernalObject(DB.QRY_SELECT_CMBILL_AMOUNT, params);
				String errMsg ="";
				if(3000000>monthExpAmount && !"Y".equals(params.get("isUnCertSign").toString())) {
				
				rtnList =  generalDao.selectGernalList(DB.QRY_SELECT_BILL_LIST, params);
				AesEncryptor decrypt = new AesEncryptor();

					Map<String, Object> deParams = new HashMap<>();
					Map<String, Object> billInfoMap = (Map<String, Object>) rtnList.get(0);
					deParams.put("NAP_JUMIN", decrypt.decrypt(billInfoMap.get("NAP_JUMIN").toString())); 	// 생년월일_사업자번호
					deParams.put("BANK_NO",decrypt.decrypt(billInfoMap.get("BANK_NO").toString())); 		// 계좌번호
					deParams.put("CARD_NO",decrypt.decrypt(billInfoMap.get("CARD_NO").toString()));		// 카드번호
					deParams.put("CARD_VALD_END_YYMM",decrypt.decrypt(billInfoMap.get("CARD_VALD_END_YYMM").toString())); //카드유효기간
			
				String projectIdStr = CommonUtils.getCommonId("PJT", 4);
				
				Map<String, Object> joinMap = new HashMap<>();
				
				params.put("projectId", projectIdStr);
				
				//고객번호 가져오기
				String custNo 	= CommonUtils.getString(generalDao.selectGernalObject("project.selectCustNoForSaveProject", params));
				String salesId	= CommonUtils.getString(generalDao.selectGernalObject("project.selectSalesIdForSaveProject", params));
				
				Map<String, Object> apiCustomerInfo = (Map<String, Object>) apiInterface
						.get("/console/v1/ucube/customer/" + custNo, null).get("data");
				
				joinMap.put("custNo",		custNo);
				joinMap.put("logid",		projectIdStr);
				joinMap.put("indcId",		salesId);
				joinMap.put("mngrId",		salesId);
				
				// 유큐브 서비스 등록
				Map<String, Object> ucubeBillInfoVo = new HashMap<>();

			
				ucubeBillInfoVo.put("billAcntNo", 		null); // 청구계정 번호 (등록시엔 공백으로 입력)
				ucubeBillInfoVo.put("billEmail",		billInfoMap.get("BILL_EMAIL")); // 청구이메일주소 
				ucubeBillInfoVo.put("billKind",			billInfoMap.get("BILL_KIND")); // 청구서유형코드 Y 이메일 문자 C N 우편
				ucubeBillInfoVo.put("billRegNo", 		billInfoMap.get("BILL_REG_NO")); // 청구사업자번호
				ucubeBillInfoVo.put("billZip",			billInfoMap.get("BILL_ZIP")); // 청구우편번호
				ucubeBillInfoVo.put("billJuso", 		billInfoMap.get("BILL_JUSO")); // 청구주소
				ucubeBillInfoVo.put("billJuso2", 		billInfoMap.get("BILL_JUSO2")); // 청구주소상세

				
				Map<String, Object> ucubePymInfoVO = new HashMap<>();

				ucubePymInfoVO.put("napCustKdCd", 		billInfoMap.get("NAP_CUST_KD_CD"));				// 납부자고객구분코드
				ucubePymInfoVO.put("napCmpNm", 			billInfoMap.get("NAP_CMP_NM"));				 	// 납부자명 
				ucubePymInfoVO.put("napJumin", 			deParams.get("NAP_JUMIN")); 										// 생년월일/사업자번호 
				ucubePymInfoVO.put("pymMthdCd",			billInfoMap.get("PAY_MTHD_CD"));					// 납부방법 자동이체 CM 신용카드 CC 지로 GR
				ucubePymInfoVO.put("cardNo",			deParams.get("CARD_NO"));						// 카드번호
				ucubePymInfoVO.put("cardValdEndYymm",	deParams.get("CARD_VALD_END_YYMM"));			// 카드유효기간
				ucubePymInfoVO.put("cardCd",			billInfoMap.get("CARD_CD"));						// 카드코드
				ucubePymInfoVO.put("cardCmpName",		billInfoMap.get("CARD_NAME"));						// 카드사명
				ucubePymInfoVO.put("bankNm",			billInfoMap.get("BANK_NAME"));						// 은행명
				ucubePymInfoVO.put("bankNo",			deParams.get("BANK_NO"));						// 계좌번호
				ucubePymInfoVO.put("bankCd",			billInfoMap.get("BANK_CD"));						// 은행코드
				ucubePymInfoVO.put("cmscode",			"00");											// 승인성공코드
				
				// 서비스 정보
				Map<String, Object> serviceInfo = new HashMap<>();
				
				Map<String, Object> ownerMap = (Map<String, Object>) generalDao
						.selectGernalObject("project.selectOwnerForApi", params);
				
				serviceInfo.put("cmpNm", 				apiCustomerInfo.get("bizCompNm")); // 가입자(상호)명
				serviceInfo.put("ceoNm", 				apiCustomerInfo.get("custNm")); // 대표자 성명 (필수)
				serviceInfo.put("damNm", 				ownerMap.get("damNm")); // 담당자명
				serviceInfo.put("deptNm",				ownerMap.get("deptNm")); // 담당자부서명
				serviceInfo.put("phoneNum", 			ownerMap.get("phoneNum")); // 담당자 전화번호
				serviceInfo.put("cellNum", 				ownerMap.get("cellNum")); // 담당자 핸드폰번호
				serviceInfo.put("faxNum", 				ownerMap.get("faxNum")); // 담당자FAX번호
				serviceInfo.put("damEmail", 			ownerMap.get("damEmail")); // 담당자 Email

				// 채널별 단가객체
				Map<String, Object> cmPriceInfoMap = new HashMap<>();
				
				List<Object> priceList = generalDao.selectGernalList("use.selectUseHistProductUnit", params);
				
				for( int j = 0; j < priceList.size(); j++ ) {
					Map<String, Object> priceMap = (Map<String, Object>) priceList.get(j);
					String productCode = CommonUtils.getString(priceMap.get("productCode"));
					
					// 가격정보
					Map<String, Object> priceInfoMap = new HashMap<>();
					
					// 후불제 가격 정보
					kong.unirest.json.JSONArray postFeeInfoArr = new kong.unirest.json.JSONArray(CommonUtils.getString(priceMap.get("postFeeInfo")));
					
//					if( postFeeInfoArr.length() == 1 ) {
					priceInfoMap.put("unitPrice", postFeeInfoArr.getJSONObject(0).get("POST_FEE"));
					priceInfoMap.put("slideInfo", null);
//					} else 
					// 스라이딩인 경우
					if ( postFeeInfoArr.length() == 3 ) {
						Map<String, Object> slideInfoMap = new HashMap<>();
						
						slideInfoMap.put("cntMore",			postFeeInfoArr.getJSONObject(2).get("FEE_START_CNT"));
						slideInfoMap.put("cntBeLow",		postFeeInfoArr.getJSONObject(0).get("FEE_END_CNT"));
						slideInfoMap.put("priceMore",		postFeeInfoArr.getJSONObject(0).get("POST_FEE"));
						slideInfoMap.put("priceBetween",	postFeeInfoArr.getJSONObject(1).get("POST_FEE"));
						slideInfoMap.put("priceBeLow",		postFeeInfoArr.getJSONObject(2).get("POST_FEE"));
						
						priceInfoMap.put("unitPrice", postFeeInfoArr.getJSONObject(0).get("POST_FEE"));
						priceInfoMap.put("slideInfo", slideInfoMap);
					}
					
					// 채널
					if( "PUSH".equals(productCode) ) {
						// 푸시
						cmPriceInfoMap.put("push", priceInfoMap);
					} else if ( "KALT1".equals(productCode) ) {
						// 알림톡
						cmPriceInfoMap.put("alimtalk", priceInfoMap);
					} else if ( "KFRT1".equals(productCode) ) {
						// 친구톡 TEXT
						cmPriceInfoMap.put("friendtalkTxt", priceInfoMap);
					} else if ( "KFRM2".equals(productCode) ) {
						// 친구톡 이미지
						cmPriceInfoMap.put("friendtalkImg", priceInfoMap);
					} else if ( "KFRM3".equals(productCode) ) {
						// 친구톡 와이드
						cmPriceInfoMap.put("friendtalkWide", priceInfoMap);
					} else if ( "RTPL".equals(productCode) ) {
						// RCS 템플릿
						cmPriceInfoMap.put("rcsTmplt", priceInfoMap);
					} else if ( "RSMS".equals(productCode) ) {
						// RCS SMS
						cmPriceInfoMap.put("rcsSms", priceInfoMap);
					} else if ( "RLMS".equals(productCode) ) {
						// RCS LMS
						cmPriceInfoMap.put("rcsLms", priceInfoMap);
					} else if ( "RMMS".equals(productCode) ) {
						// RCS MMS
						cmPriceInfoMap.put("rcsMms", priceInfoMap);
					} else if ( "SMS".equals(productCode) ) {
						// SMS
						cmPriceInfoMap.put("sms", priceInfoMap);
					} else if ( "LMS".equals(productCode) ) {
						// LMS
						cmPriceInfoMap.put("lms", priceInfoMap);
					} else if ( "MMS".equals(productCode) ) {
						// MMS
						cmPriceInfoMap.put("mms", priceInfoMap);
					}
				}

				joinMap.put("billInfoVo",		ucubeBillInfoVo);
				joinMap.put("pymInfoVO",		ucubePymInfoVO);
				joinMap.put("serviceInfo",		serviceInfo);
				joinMap.put("cmPriceInfo",		cmPriceInfoMap);
				
				kong.unirest.json.JSONObject json2222 =  new kong.unirest.json.JSONObject(joinMap);
				
				// API 통신처리
				Map<String, Object> result =  apiInterface.post("/console/v1/ucube/service/join/cm2", joinMap, null);
//				성공 시
//				{
//					"code": "10000",
//					"message": "성공",
//					"data": {
//						"serviceId": "SB1099",
//						"resultCode": "N0000",
//						"resultMsg": "Success",
//						"resultList": [{
//							"entrNo": "500232358675", (가입번호)
//							"billAcntNo": "532109196140" (청구계정번호)
//						}]
//					}
//				 }

				String serviceId = "";
				String billId = "";			
				
				
				if ("10000".equals(result.get("code"))) {
					Map<String, Object> resultData = (Map<String, Object>) result.get("data");
					List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultData.get("resultList");
					
					serviceId = CommonUtils.getString(resultList.get(0).get("entrNo"));
					billId = CommonUtils.getString(resultList.get(0).get("billAcntNo"));
					kong.unirest.json.JSONObject ucubeInfo = new kong.unirest.json.JSONObject(resultData);

					params.put("serviceId", serviceId);
					params.put("billId", billId);
					params.put("ucubeInfo", ucubeInfo.toString());
					params.put("unCertifiedY", "");

					// 유큐브 정보 인서트
					generalDao.insertGernal("project.insertCmUcube", params);

				} else if ("500100".equals(result.get("code"))) {
					 errMsg = CommonUtils.getString(result.get("message")) + " : "
							+ CommonUtils.getString(result.get("data"));
					throw new Exception(errMsg);
				} else {
//					 {code=21400, message=유큐브 연동 오류, data={serviceId=SB1099, resultCode=icm.err.074
//							, resultMsg=입력한 유치자( juoh )에 해당하는 대리점 정보가 존재하지 않습니다., resultList=null}}
					 errMsg = CommonUtils.getString(result.get("message"));
					Map<String, Object> data = (Map<String, Object>) result.get("data");
					if (data == null) {
						throw new Exception(errMsg);
					} else {
						String msg = CommonUtils.getString(data.get("resultMsg"));
						throw new Exception(msg);
					}
				}		

				
				
				RestResult<?> rtn = selectStaInformation(null);
				List rtnObject = (List) rtn.getData();
				Map<String,Object> rtnMap =  (Map<String, Object>) rtnObject.get(44);
				System.out.println(rtnMap.get("fieldValue"));
				params.put("monthLimitAmount",rtnMap.get("fieldValue"));
				generalDao.updateGernal("signUp.updateBillStatus", params);	

				if(billInfoMap.get("SALES_MAN") != null) {
					params.put("phone", billInfoMap.get("SALES_MAN_NUMBER"));
					params.put("manage","O");			
					commonService.sendNoti("status", params);
				}
				
				params.put("phone", params.get("phoneCerti"));
				params.put("manage","X");
				commonService.sendNoti("status", params);		
				
				}
				return errMsg;
			}
		
		@SuppressWarnings("unchecked")
		@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
		public void deleteUserSignupInfo(Map<String, Object> params) throws Exception{
			
			List<Object> userList = generalDao.selectGernalList(DB.QRY_SELECT_CMUSER_CORPID, params);
			Map<String,Object> userMap = (Map<String, Object>) userList.get(0);
			String corpId = userMap.get("CORP_ID").toString();
			
			params.put("corpId", corpId);
			
			if(!corpId.equals(null) && !corpId.equals("")) {
				generalDao.deleteGernal("signUp.deleteCmuserCorp", params);
				generalDao.deleteGernal("signUp.deleteCmbill", params);
			}

		}
		
		
		@SuppressWarnings("unchecked")
		public RestResult<?> selectBillStatus(Map<String, Object> params) throws Exception {
			
			List<Object> userList = generalDao.selectGernalList(DB.QRY_SELECT_CMUSER_CORPID, params);
			Map<String,Object> userMap = (Map<String, Object>) userList.get(0);
			String corpId = userMap.get("CORP_ID").toString();
			
			params.put("corpId", corpId);
			
			RestResult<Object>	rtn = new RestResult<Object>();
			
			rtn.setData(generalDao.selectGernalList(DB.QRY_SELECT_CMBILL_STATUS, params));
			
			return rtn;
		}	
	
}
