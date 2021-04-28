package kr.co.uplus.cm.project.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import kr.co.uplus.cm.common.consts.Const;
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
	@SuppressWarnings("unchecked")
	public RestResult<?> selectRcsBrandList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_BRANDLIST, params);

		for (int i = 0; i < rtnList.size(); i++) {
			// 파라미터 정리
			Map<String, Object> inputVal = new HashMap<>();
			
			Map<String, Object> rtnMap = (Map<String, Object>) rtnList.get(i);
			
			JSONParser jParser = new JSONParser();
			JSONObject brandInfo = (JSONObject) jParser.parse(CommonUtils.getString(rtnMap.get("brandInfo")));
			
			inputVal.put("corpId",		rtnMap.get("corpId"));
			inputVal.put("projectId",	rtnMap.get("projectId"));
//			inputVal.put("apiKey",		rtnMap.get("projectId"));
//			inputVal.put("apiSecretKey",	rtnMap.get("projectId"));
			inputVal.put("name",		brandInfo.get("name"));
			inputVal.put("description",	brandInfo.get("description"));
			inputVal.put("tel",			brandInfo.get("tel"));
			
			// 체널관리
			List<Map<String, Object>> menusList = (List<Map<String, Object>>) brandInfo.get("menus");
			if(menusList != null) {
				// 초기화
				inputVal.put("call",			false);
				inputVal.put("callWeblink",		"");
				inputVal.put("web",				false);
				inputVal.put("webWeblink",		"");
				inputVal.put("store",			false);
				inputVal.put("storeWeblink",	"");
				inputVal.put("order",			false);
				inputVal.put("orderWeblink",	"");
				inputVal.put("buy",				false);
				inputVal.put("buyWeblink",		"");
				inputVal.put("ticket",			false);
				inputVal.put("ticketWeblink",	"");
				inputVal.put("moreInfo",		false);
				inputVal.put("moreInfoWeblink",	"");
				
				for(int j = 0; j < menusList.size(); j++){
					Map<String, Object> menus = menusList.get(j);
					
					if( "call".equals(menus.get("buttonType")) ) {
						inputVal.put("call",			menus.get("buttonType"));
						inputVal.put("callWeblink",		menus.get("weblink"));
					}
					if( "web".equals(menus.get("buttonType")) ) {
						inputVal.put("web",			menus.get("buttonType"));
						inputVal.put("webWeblink",		menus.get("weblink"));
					}
					if( "store".equals(menus.get("buttonType")) ) {
						inputVal.put("store",			menus.get("buttonType"));
						inputVal.put("storeWeblink",		menus.get("weblink"));
					}
					if( "order".equals(menus.get("buttonType")) ) {
						inputVal.put("store",			menus.get("buttonType"));
						inputVal.put("storeWeblink",		menus.get("weblink"));
					}
					if( "buy".equals(menus.get("buttonType")) ) {
						inputVal.put("buy",			menus.get("buttonType"));
						inputVal.put("buyWeblink",		menus.get("weblink"));
					}
					if( "ticket".equals(menus.get("buttonType")) ) {
						inputVal.put("ticket",			menus.get("buttonType"));
						inputVal.put("ticketWeblink",		menus.get("weblink"));
					}
					if( "moreInfo".equals(menus.get("buttonType")) ) {
						inputVal.put("moreInfo",			menus.get("buttonType"));
						inputVal.put("moreInfoWeblink",		menus.get("weblink"));
					}
				}
			}
			
			
			
			inputVal.put("categoryId",		brandInfo.get("categoryId"));
			inputVal.put("subCategoryId",	brandInfo.get("subCategoryId"));
			inputVal.put("categoryOpt",		brandInfo.get("categoryOpt"));
			inputVal.put("zipCode",			brandInfo.get("zipCode"));
			inputVal.put("roadAddress",		brandInfo.get("roadAddress"));
			inputVal.put("detailAddress",	brandInfo.get("detailAddress"));
			
			inputVal.put("preProfileImg",	brandInfo.get("profileImgFilePath"));
			inputVal.put("preBgImg",		brandInfo.get("bgImgFilePath"));
			inputVal.put("profileImgFilePath",	brandInfo.get("profileImgFilePath"));
			inputVal.put("bgImgFilePath",	brandInfo.get("bgImgFilePath"));
			inputVal.put("certiFilePath",	brandInfo.get("certiFilePath"));
			
			if( brandInfo.get("email") != null ) {
				if( brandInfo.get("email").toString().indexOf("@") > 0 ) {
					inputVal.put("email",			brandInfo.get("email").toString().split("@")[0]);
					inputVal.put("email2",			brandInfo.get("email").toString().split("@")[1]);
				} else {
					inputVal.put("email",			"");
				}
			}
			
			
			inputVal.put("webSiteUrl",		brandInfo.get("webSiteUrl"));
			inputVal.put("mainMdn",			brandInfo.get("mainMdn"));
			inputVal.put("rcsReply",		brandInfo.get("projectId"));
			
			// 쳇봇(발신번호 관리)
			List<Map<String, Object>> chatbotsList = (List<Map<String, Object>>) brandInfo.get("chatbots");
			if(chatbotsList != null) {
				for (int j = 0; j < chatbotsList.size(); j++) {
					Map<String, Object> chatbotMap = chatbotsList.get(j);
					
				}
			}
//			inputVal.put("email",	rtnMap.get("projectId"));
//			inputVal.put("email",	rtnMap.get("projectId"));
//			inputVal.put("email",	rtnMap.get("projectId"));
//			inputVal.put("email",	rtnMap.get("projectId"));
//			inputVal.put("email",	rtnMap.get("projectId"));
			
			rtnMap.put("inputVal", inputVal);
			rtnMap.put("status", brandInfo.get("status"));
		}
		
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
		
		Map<String, Object> getHeaderMap = new HashMap<String, Object>();
		getHeaderMap.put("apiId", CommonUtils.getString(params.get("apiKey")));
		getHeaderMap.put("apiSercret", CommonUtils.getString(params.get("apiSecretKey")));
		
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void saveRcsBrandReqForApi(Map<String, Object> params) throws Exception {
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
		
		// 데이터 처리
		List<Object> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		
		map.put("corpId",		params.get("corpId"));
		map.put("projectId",	params.get("projectId"));
		
		Map<String, Object> regBrandMap = new HashMap<>();
		regBrandMap.put("name",			params.get("name"));
		regBrandMap.put("description",	params.get("description"));
		regBrandMap.put("tel",			 params.get("tel"));
		
		// 메뉴 리스트
		List menusList = new ArrayList<>();
		if(!"".equals(CommonUtils.getString(params.get("call"))) && Boolean.parseBoolean(CommonUtils.getString(params.get("call"))) != false) {
			Map<String, Object> menusMap = new HashMap<>();
			menusMap.put("buttonType",	"call");
			menusMap.put("weblink",		params.get("callWeblink"));
			menusList.add(menusMap);
		}
		if(!"".equals(CommonUtils.getString(params.get("web"))) && Boolean.parseBoolean(CommonUtils.getString(params.get("web"))) != false) {
			Map<String, Object> menusMap = new HashMap<>();
			menusMap.put("buttonType",	"web");
			menusMap.put("weblink",		params.get("webWeblink"));
			menusList.add(menusMap);
		}
		if(!"".equals(CommonUtils.getString(params.get("store"))) && Boolean.parseBoolean(CommonUtils.getString(params.get("store"))) != false) {
			Map<String, Object> menusMap = new HashMap<>();
			menusMap.put("buttonType",	"store");
			menusMap.put("weblink",		params.get("storeWeblink"));
			menusList.add(menusMap);
		}
		if(!"".equals(CommonUtils.getString(params.get("order"))) && Boolean.parseBoolean(CommonUtils.getString(params.get("order"))) != false) {
			Map<String, Object> menusMap = new HashMap<>();
			menusMap.put("buttonType",	"order");
			menusMap.put("weblink",		params.get("orderWeblink"));
			menusList.add(menusMap);
		}
		if(!"".equals(CommonUtils.getString(params.get("buy"))) && Boolean.parseBoolean(CommonUtils.getString(params.get("buy"))) != false) {
			Map<String, Object> menusMap = new HashMap<>();
			menusMap.put("buttonType",	"buy");
			menusMap.put("weblink",		params.get("buyWeblink"));
			menusList.add(menusMap);
		}
		if(!"".equals(CommonUtils.getString(params.get("ticket"))) && Boolean.parseBoolean(CommonUtils.getString(params.get("ticket"))) != false) {
			Map<String, Object> menusMap = new HashMap<>();
			menusMap.put("buttonType",	"ticket");
			menusMap.put("weblink",		params.get("ticketWeblink"));
			menusList.add(menusMap);
		}
		if(!"".equals(CommonUtils.getString(params.get("moreInfo"))) && Boolean.parseBoolean(CommonUtils.getString(params.get("moreInfo"))) != false) {
			Map<String, Object> menusMap = new HashMap<>();
			menusMap.put("buttonType",	"moreInfo");
			menusMap.put("weblink",		params.get("moreInfoWeblink"));
			menusList.add(menusMap);
		}
		regBrandMap.put("menus",		menusList);
		// 메뉴 끝
		regBrandMap.put("categoryId",		params.get("categoryId"));
		regBrandMap.put("subCategoryId",	params.get("subCategoryId"));
		regBrandMap.put("categoryOpt",		params.get("categoryOpt"));
		regBrandMap.put("zipCode",			params.get("zipCode"));
		regBrandMap.put("roadAddress",		params.get("roadAddress"));
		regBrandMap.put("detailAddress",	params.get("detailAddress"));
		regBrandMap.put("email",			params.get("email")+ "@" + params.get("email2"));
		regBrandMap.put("webSiteUrl",		params.get("webSiteUrl"));
		
		map.put("regBrand",		regBrandMap);
		
		map.put("mainMdn",		params.get("mainMdn"));
		
		if( "N".equals(tempYn) ) {
			map.put("profileImgFilePath",	params.get("profileImgFilePath"));
			map.put("bgImgFilePath",		params.get("profileImgFilePath"));
			map.put("certiFilePath",		params.get("profileImgFilePath"));
		} else {
			// 임시
			map.put("profileImgFilePath", "/efs/file/console/2021/04/26/12/test1234.png");
			map.put("bgImgFilePath", "/efs/file/console/2021/04/26/12/test1234.png");
			map.put("certiFilePath", "/efs/file/console/2021/04/26/12/test1234.png");
		}
		
		
		String chatbotStr = CommonUtils.getString(params.get("chatbots"));
		JSONParser parser = new JSONParser();
		JSONArray chatbotJson = new JSONArray();
		if( !"".equals(chatbotStr) ) {
			chatbotStr = "[" + chatbotStr + "]";
			parser = new JSONParser();
			chatbotJson = (JSONArray) parser.parse(chatbotStr);
			map.put("chatbots",		chatbotJson);
		}
		
		list.add(map);
		
		// 임시저장
		String sts = CommonUtils.getString(params.get("sts"));
		if( "temp".equals(sts) ) {
			String tempBrandId = CommonUtils.getCommonId("T", 4);
			params.put("brandId", tempBrandId);
			params.put("brandReqKey", tempBrandId);
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(map);
			
			params.put("brandInfo", json);
			// 브랜드 저장
			generalDao.selectGernalList(DB.QRY_INSERT_RCS_BRANDREQ, params);
			
			// 챗봇 저장
			for( int j = 0; j < chatbotJson.size(); j++ ) {
				JSONObject chatbotObj = (JSONObject) chatbotJson.get(j);
				String tempChatId = CommonUtils.getCommonId("Cb", 4);
				chatbotObj.put("brandId", tempBrandId);
				chatbotObj.put("chatbotId", tempChatId);
				chatbotObj.put("corpId", params.get("corpId"));
				
				Map<String, Object> chatbotInfo = new HashMap<>();
				chatbotInfo.put("subNum", chatbotObj.get("mdn"));
				chatbotInfo.put("brandId", tempBrandId);
				chatbotInfo.put("display", "01");
				chatbotInfo.put("groupId", null);
				chatbotInfo.put("service", "a2p");
				chatbotInfo.put("subTitle", chatbotObj.get("subTitle"));
				chatbotInfo.put("updateId", "uplus");
				chatbotInfo.put("chatbotId", tempChatId);
				if( CommonUtils.getString(params.get("mainMdn")).equals(chatbotObj.get("mdn")) ) {
					chatbotInfo.put("isMainNum", true);
				} else {
					chatbotInfo.put("isMainNum", false);
				}
				String chatbotInfoStr = mapper.writeValueAsString(chatbotInfo);
				chatbotObj.put("chatbotInfo", chatbotInfoStr);
				
				generalDao.selectGernalList(DB.QRY_INSERT_RCS_CHATBOTREQ, chatbotObj);
			}
			
		} else if( "save".equals(sts) ) {
			// 등록요청
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("apiId",		params.get("apiKey"));
			headerMap.put("apiSercret",	params.get("apiSecretKey"));
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(map);
			Map<String, Object> bodyMap = new HashMap<String, Object>();
			bodyMap.put("list", json);
			
			Map<String, Object> result =  apiInterface.listPost("/console/v1/brand", list, headerMap);
			
			// 성공인지 실패인지 체크
			if( !"10000".equals(result.get("rslt")) ) {
				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
				throw new Exception(errMsg);
			}
		} if( "mod".equals(sts) ) {
			// 수정요청
			String brandId = CommonUtils.getString(params.get("brandId"));
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("apiId",		params.get("apiKey"));
			headerMap.put("apiSercret",	params.get("apiSecretKey"));
			headerMap.put("brandId",	brandId);
			
			Map<String, Object> bodyMap = new HashMap<String, Object>();
			bodyMap.put("list", list);
			
			Map<String, Object> result =  apiInterface.listPut("/console/v1/brand/" + brandId, list, headerMap);
			
			// 성공인지 실패인지 체크
			if( !"10000".equals(result.get("rslt")) ) {
				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
				throw new Exception(errMsg);
			}
		} else if ( "delete".equals(sts) ) {
			// 삭제요청
			String brandId = CommonUtils.getString(params.get("brandId"));
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("apiId",		params.get("apiKey"));
			headerMap.put("apiSercret",	params.get("apiSecretKey"));
			headerMap.put("brandId",	brandId);
			
			Map<String, Object> result =  apiInterface.listDelete("/console/v1/brand/" + brandId, list, headerMap);
			
			// 성공인지 실패인지 체크
			if( !"10000".equals(result.get("rslt")) ) {
				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
				throw new Exception(errMsg);
			}
		}
	}
	
	public File multipartToFile(MultipartFile mfile) throws Exception {
		File file = new File(mfile.getOriginalFilename());
		mfile.transferTo(file);
		return file;
	}
}


