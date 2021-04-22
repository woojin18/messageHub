package kr.co.uplus.cm.project.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class ChannelService {

	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private ApiInterface apiInterface; 

	@Autowired
	private CommonService commonService;

	// RCS 브랜드 조회
	public RestResult<?> selectRcsBrandList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_BRANDLIST, params);

		rtn.setData(rtnList);

		return rtn;
	}

	// RCS 브랜드 템플릿 조회
	public RestResult<?> selectRcsRegTmpltList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_REGTMPLTLIST, params);

		rtn.setData(rtnList);

		return rtn;
	}

	// RCS 브랜드 발신번호 상세 조회
	public RestResult<?> selectRcsCallbackList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_CALLBACKLIST, params);

		rtn.setData(rtnList);

		return rtn;
	}
	
	// API KEY 중복 체크 후 카테고리 가져오기
	@SuppressWarnings("unchecked")
	public RestResult<?> checkApiKey(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		params.put("apiId", "1111");
		params.put("apiSercret", "1111");
		
//		Map<String, Object> result = this.api("/console/v1/brand/categories", params, "GET", "application");
		
		Map<String, Object> getHeaderMap = new HashMap<String, Object>();
		getHeaderMap.put("apiId", "111");
		getHeaderMap.put("apiSercret", "111");
		getHeaderMap.put("svcId", "WEB01");
		
		Map<String, Object> result = apiInterface.get("/console/v1/brand/categories", getHeaderMap);
		
		rtn.setData(result);
		
		return rtn;
	}
	
	/**
	 * API 통신 
	 * @param urlText		호출 URL 뒷부분
	 * @param params		APIKEY 등 발신 정보 들
	 * @param type			GET, POST 타입
	 * @return				MAP 타입으로 리턴
	 * @throws Exception
	 */
	public Map<String, Object> api(String urlText, Map<String, Object> params, String type, String contentType) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String apiUrl = "http://erp.ectech.co.kr:40931";
		String result = "";
		
		JSONParser jParser = new JSONParser();
		JSONObject jObj = new JSONObject();
		
		try {
			URL url = new URL(apiUrl + urlText);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// 파라미터가 아닌 헤더에 세팅해줘야함
			con.setRequestProperty("apiId", CommonUtils.getString(params.get("apiId")));
			con.setRequestProperty("apiSercret", CommonUtils.getString(params.get("apiSercret")));
			con.setRequestProperty("svcId", "WEB01");
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestMethod(type.toUpperCase());	// get post
			
			result = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")).readLine();
			
			jObj = (JSONObject) jParser.parse(result);
			
			returnMap.put("rslt", jObj.get("rslt"));
			returnMap.put("data", jObj.get("data"));
		} catch (Exception e) {
			returnMap.put("rslt", "error");
			returnMap.put("data", e.getMessage());
		}
		
		return returnMap;
	}
	
	// RCS 브랜드 등록 요청
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void saveRcsBrandReqForApi(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		// 임시 파일 패스 사용 
		String tempYn = "Y";
		
		Map<String, Object> profileImgFileRtnMap = new HashMap<String, Object>();
		Map<String, Object> bgImgFileRtnMap = new HashMap<String, Object>();
		Map<String, Object> certiFileRtnMap = new HashMap<String, Object>();
		
		if( "N".equals(tempYn) ) {
			// 프로필 파일 업로드 처리
			MultipartFile profileImgFile = (MultipartFile) params.get("profileImgFile");
			
			RestResult<Object> profileImgFileRtn = commonService.uploadFile(profileImgFile, CommonUtils.getString(params.get("loginId")));
			
			profileImgFileRtnMap = (Map<String, Object>) profileImgFileRtn.getData();
			// 배경 파일 업로드 처리
			MultipartFile bgImgFile = (MultipartFile) params.get("bgImgFile");
			
			RestResult<Object> bgImgFileRtn = commonService.uploadFile(bgImgFile, CommonUtils.getString(params.get("loginId")));
			
			bgImgFileRtnMap = (Map<String, Object>) bgImgFileRtn.getData();
			// 가입증명 파일 업로드 처리
			MultipartFile certiFile = (MultipartFile) params.get("certiFile");
			
			RestResult<Object> certiFileRtn = commonService.uploadFile(certiFile, CommonUtils.getString(params.get("loginId")));
			
			certiFileRtnMap = (Map<String, Object>) certiFileRtn.getData();
		}
		
		// JSON 값 처리
		String jsonInfo = "";

		jsonInfo += "{";
		jsonInfo += "	\"corpId\"		: \""+params.get("corpId")+"\",											";
		jsonInfo += "	\"projectId\"	: \""+params.get("projectId")+"\",										";
		jsonInfo += "	\"regBrand\"	: {																		";
		jsonInfo += "		\"name\"			: \""+params.get("name")+"\",									";
		jsonInfo += "		\"description\"		: \""+params.get("description")+"\",							";
		jsonInfo += "		\"tel\"				: \""+params.get("tel")+"\",									";
//		// 메뉴버튼설정
//		jsonInfo += "		\"menus\"			: {,															";
//		if(!"".equals(CommonUtils.getString(params.get("call")))) {
//			jsonInfo += "			\"call\"			: \""+params.get("call")+"\",							";
//		}
//		if(!"".equals(CommonUtils.getString(params.get("web")))) {
//			jsonInfo += "			\"web\"				: \""+params.get("web")+"\",							";
//		}
//		if(!"".equals(CommonUtils.getString(params.get("store")))) {
//			jsonInfo += "			\"store\"			: \""+params.get("store")+"\",							";
//		}
//		if(!"".equals(CommonUtils.getString(params.get("order")))) {
//			jsonInfo += "			\"order\"			: \""+params.get("order")+"\",							";
//		}
//		if(!"".equals(CommonUtils.getString(params.get("buy")))) {
//			jsonInfo += "			\"buy\"				: \""+params.get("buy")+"\",							";
//		}
//		if(!"".equals(CommonUtils.getString(params.get("ticket")))) {
//			jsonInfo += "			\"ticket\"			: \""+params.get("ticket")+"\",							";
//		}
//		if(!"".equals(CommonUtils.getString(params.get("moreInfo")))) {
//			jsonInfo += "			\"moreInfo\"		: \""+params.get("moreInfo")+"\",						";
//		}
//		// 마지막 쉼표 제거
//		jsonInfo = jsonInfo.substring(0, jsonInfo.length()-1);
//		jsonInfo += "		},																					";
//		// 메뉴버튼설정
		jsonInfo += "		\"categoryId\"		: \""+params.get("categoryId")+"\",								";
		jsonInfo += "		\"subCategoryId\"	: \""+params.get("subCategoryId")+"\",							";
		jsonInfo += "		\"categoryOpt\"		: \""+params.get("categoryOpt")+"\",							";
		jsonInfo += "		\"zipCode\"			: \""+params.get("zipCode")+"\",								";
		jsonInfo += "		\"roadAddress\"		: \""+params.get("roadAddress")+"\",							";
		jsonInfo += "		\"detailAddress\"	: \""+params.get("detailAddress")+"\",							";
		jsonInfo += "		\"email\"			: \""+params.get("email")+ "@" + params.get("email2") + "\",	";
		jsonInfo += "		\"webSiteUrl\"		: \""+params.get("webSiteUrl")+"\"								";
		jsonInfo += "	},																						";
		jsonInfo += "	\"mainMdn\"		: \""+params.get("mainMdn")+"\",										";
		
		if( "N".equals(tempYn) ) {
			jsonInfo += "	\"profileImgFilePath\"	: \""+profileImgFileRtnMap.get("attachFilePath")+"\",		";
			jsonInfo += "	\"bgImgFilePath\"		: \""+bgImgFileRtnMap.get("bgImgFilePath")+"\",				";
			jsonInfo += "	\"certiFilePath\"		: \""+certiFileRtnMap.get("certiFilePath")+"\",				";
		} else {
			jsonInfo += "	\"profileImgFilePath\"	: \"/efs/file/console/2021/04/21/13/sign3.jpg\",			";
			jsonInfo += "	\"bgImgFilePath\"		: \"/efs/file/console/2021/04/21/13/sign3.jpg\",			";
			jsonInfo += "	\"certiFilePath\"		: \"/efs/file/console/2021/04/21/13/sign3.jpg\",			";
		}
		jsonInfo += "	\"chatbots\": [																			";
		jsonInfo += "		{																					";
		jsonInfo += "			\"mdn\"			: \""+params.get("name")+"\",									";
		jsonInfo += "			\"subnum\"		: \""+params.get("name")+"\",									";
		jsonInfo += "			\"rcsReply\"	: \""+params.get("name")+"\",									";
		jsonInfo += "			\"subTitle\"	: \""+params.get("name")+"\",									";
		jsonInfo += "			\"service\"		: \""+params.get("name")+"\",									";
		jsonInfo += "			\"display\"		: \""+params.get("name")+"\",									";
		jsonInfo += "			\"webhook\"		: \""+params.get("name")+"\"									";
		jsonInfo += "		}																					";
		jsonInfo += "	]																						";
		
		// 마지막 쉼표 제거
		//jsonInfo = jsonInfo.substring(0, jsonInfo.length()-1);
		
		jsonInfo += "}";
		
		params.put("jsonInfo", jsonInfo);
		
		// 임시저장
		String sts = CommonUtils.getString(params.get("sts"));
		if( "temp".equals(sts) ) {
			String tempBrandId = "T"+CommonUtils.generationSringToHex("");
			params.put("brandId", tempBrandId);
			params.put("brandReqKey", tempBrandId);
			generalDao.selectGernalList(DB.QRY_INSERT_RCS_BRANDREQ, params);
			
		} else if( "save".equals(sts) ) {
			// 등록요청
			Map<String, Object> getHeaderMap = new HashMap<String, Object>();
			getHeaderMap.put("apiId",		params.get("apiKey"));
			getHeaderMap.put("apiSercret",	params.get("apiSecretKey"));
			getHeaderMap.put("svcId",		"WEB01");
			
			// body에 row 형식으로 json 데이터 API통신처리
			Map<String, Object> result = apiInterface.get("/console/v1/brand/categories", getHeaderMap , getHeaderMap);
			
			rtn.setData(result);
		} else if ( "delete".equals(sts) ) {
			// 삭제요청
		}
	}
	
	public File multipartToFile(MultipartFile mfile) throws Exception {
		File file = new File(mfile.getOriginalFilename());
		mfile.transferTo(file);
		return file;
	}
}


