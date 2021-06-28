package kr.co.uplus.cm.signUp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void insertSignUp(Map<String, Object> params) throws Exception {

		// 업태 업종 코드값 조회
		String busiTypeCd = "";				// 업태 코드값
		String busiClassCd = "";			// 업종 코드값
		String busiType = CommonUtils.getString(params.get("busiType"));			// 업태
		String busiClass = CommonUtils.getString(params.get("busiClass"));			// 업태
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("codeName1", busiType);
		paramMap.put("codeTypeCd", "CORP_CUST_UPTAE");
		
		busiTypeCd = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_CODEVAL1_BY_CODENAME1, paramMap));

		paramMap.clear();
		paramMap.put("codeName1", busiClass);
		paramMap.put("codeTypeCd", "CORP_CUST_UPJONG");
		
		busiClassCd = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_CODEVAL1_BY_CODENAME1, paramMap));
		
		paramMap.clear();
		
		paramMap.putAll(params);
		paramMap.put("busiType", busiTypeCd);
		paramMap.put("busiClass", busiClassCd);

		// 사용자 비밀번호 암호화
		SHA sha512 = new SHA(512);
		String encPwd = sha512.encryptToBase64(CommonUtils.getString(paramMap.get("password")));
		paramMap.put("password", encPwd);
		
		// 첨부파일
		MultipartFile file = (MultipartFile) paramMap.get("attachFile");
		
		String fileSeq = "";
		
		String uploadDirPath = FileConfig.getFilePath(FileConfig.FileSvcType.BIZ_REG_CARD);
		
		if(file != null) {
			fileSeq = commonService.uploadFile(file, null, uploadDirPath);

			paramMap.put("fileId", fileSeq);
		}
		// 고객사 등록
//		generalDao.insertGernal(DB.QRY_INSERT_CM_CORP, params);
		// owner 등록 (이메일 발송때 insert된 data에 update 처리)
//		generalDao.updateGernal(DB.QRY_UPDATE_CM_USER, params);
		
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
		
		// 개인정보수집 일경우에만 예전 약관을 가져오도록 처리
		if("PRI".equals(srcTermsCd)) {
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
		
		if("10000".equals(result.get("rslt"))) {
			Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
			List<Map<String, Object>> list =  (List<Map<String, Object>>) dataMap.get("resultList");
			rtn.setData(list);
		} else {
			throw new Exception(CommonUtils.getString(result.get("rsltDesc")));
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
		
		if("10000".equals(result.get("rslt"))) {
			Map<String, Object> data = (Map<String, Object>) result.get("data");
			rtn.setData(data);
		} else {
			throw new Exception(CommonUtils.getString(result.get("rsltDesc")));
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
		
		if("10000".equals(result.get("rslt"))) {
			if(CommonUtils.isEmptyObject(result.get("data"))) {
				throw new Exception("조회된 주소가 없습니다.");
			} else {
				List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("data");
				rtn.setData(list);
			}
		} else {
			throw new Exception(CommonUtils.getString(result.get("rsltDesc")));
		}
		
		return rtn;
	}
	
}
