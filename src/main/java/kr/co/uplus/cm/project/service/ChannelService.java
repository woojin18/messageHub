package kr.co.uplus.cm.project.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.config.FileConfig;
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

		if(params.containsKey("pageNo")
				&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize")
				&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_RCS_BRANDLIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}
		
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
//			inputVal.put("apiSecret",	rtnMap.get("projectId"));
			inputVal.put("name",		brandInfo.get("name"));
			inputVal.put("description",	brandInfo.get("description"));
			inputVal.put("tel",			brandInfo.get("tel"));
			
			// 체널관리
			List<Map<String, Object>> menusList = (List<Map<String, Object>>) brandInfo.get("menus");
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
			inputVal.put("tickets",			false);
			inputVal.put("ticketsWeblink",	"");
			inputVal.put("moreinfo",		false);
			inputVal.put("moreinfoWeblink",	"");
			
			if(menusList != null) {
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
					if( "tickets".equals(menus.get("buttonType")) ) {
						inputVal.put("tickets",			menus.get("buttonType"));
						inputVal.put("ticketsWeblink",		menus.get("weblink"));
					}
					if( "moreinfo".equals(menus.get("buttonType")) ) {
						inputVal.put("moreinfo",			menus.get("buttonType"));
						inputVal.put("moreinfoWeblink",		menus.get("weblink"));
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
			inputVal.put("rcsReply",		brandInfo.get("projectId"));
			
			// 쳇봇(발신번호 관리)
			List<Map<String, Object>> chatbotsList = (List<Map<String, Object>>) brandInfo.get("chatbots");
			// 임시저장된 경우
			if(chatbotsList != null) {
				for (int j = 0; j < chatbotsList.size(); j++) {
					Map<String, Object> chatbotMap = chatbotsList.get(j);
					// 메인발신 체크
					if(CommonUtils.getString(brandInfo.get("mainMdn")).equals(chatbotMap.get("mdn"))) {
						inputVal.put("mainTitle",			chatbotMap.get("subTitle"));
						inputVal.put("mainMdn",				chatbotMap.get("mdn"));
					}
					
				}
				
				inputVal.put("chatbots",		brandInfo.get("chatbots"));
			} else {
				// 실제 쳇봇 테이블에 입력된 경우
				inputVal.put("mainMdn",			rtnMap.get("mainMdn"));
				inputVal.put("mainTitle",		rtnMap.get("mainTitle"));
			}
			
			rtnMap.put("inputVal", inputVal);
			rtnMap.put("status", brandInfo.get("status"));
		}
		
		rtn.setData(rtnList);

		return rtn;
	}

	// RCS 브랜드 템플릿 조회
	public RestResult<?> selectRcsRegTmpltList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		if(params.containsKey("pageNo")
				&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize")
				&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_RCS_REGTMPLTLIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_REGTMPLTLIST, params);
		rtn.setData(rtnList);
		
		return rtn;
	}

	// RCS 브랜드 발신번호 상세 조회
	public RestResult<?> selectRcsCallbackList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		if(params.containsKey("pageNo")
				&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize")
				&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_RCS_BRAND_LIST_CALLBACK_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_BRAND_LIST_CALLBACK_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}
	
	// API KEY 중복 체크 후 카테고리 가져오기
	@SuppressWarnings("unchecked")
	public RestResult<?> checkApiKey(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		Map<String, Object> getHeaderMap = new HashMap<String, Object>();
		getHeaderMap.put("apiId", CommonUtils.getString(params.get("apiKey")));
		getHeaderMap.put("apiSecret", CommonUtils.getString(params.get("apiSecret")));
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("cateData", apiInterface.get("/console/v1/brand/categories", getHeaderMap));
		
		String brandId = CommonUtils.getString(params.get("brandId"));
		
		if( !"".equals(brandId) ) {
			Map<String, Object> getHeaderMap2 = new HashMap<String, Object>();
			getHeaderMap2.put("apiId", CommonUtils.getString(params.get("apiKey")));
			getHeaderMap2.put("apiSecret", CommonUtils.getString(params.get("apiSecret")));
			getHeaderMap2.put("brandId", brandId);
			
			// 파라미터 정리
			Map<String, Object> inputVal = new HashMap<>();
			List<Map<String, Object>> resultLists = (List<Map<String, Object>>) apiInterface.get("/console/v1/brand/" + brandId, getHeaderMap).get("data");
			
			Map<String, Object> rtnMap = resultLists.get(0);
			
			inputVal.put("apiKey",		CommonUtils.getString(params.get("apiKey")));
			inputVal.put("apiSecret",	CommonUtils.getString(params.get("apiSecret")));
			inputVal.put("corpId",		rtnMap.get("corpId"));
			inputVal.put("projectId",	rtnMap.get("projectId"));
			inputVal.put("name",		rtnMap.get("name"));
			inputVal.put("description",	rtnMap.get("description"));
			inputVal.put("tel",			rtnMap.get("tel"));
			
			// 체널관리
			List<Map<String, Object>> menusList = (List<Map<String, Object>>) rtnMap.get("menus");
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
			inputVal.put("tickets",			false);
			inputVal.put("ticketsWeblink",	"");
			inputVal.put("moreinfo",		false);
			inputVal.put("moreinfoWeblink",	"");
			
			if(menusList != null) {
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
					if( "tickets".equals(menus.get("buttonType")) ) {
						inputVal.put("tickets",			menus.get("buttonType"));
						inputVal.put("ticketsWeblink",		menus.get("weblink"));
					}
					if( "moreinfo".equals(menus.get("buttonType")) ) {
						inputVal.put("moreinfo",			menus.get("buttonType"));
						inputVal.put("moreinfoWeblink",		menus.get("weblink"));
					}
				}
			}
			
			inputVal.put("categoryId",		rtnMap.get("categoryId"));
			inputVal.put("subCategoryId",	rtnMap.get("subCategoryId"));
			inputVal.put("categoryOpt",		rtnMap.get("categoryOpt"));
			inputVal.put("zipCode",			rtnMap.get("zipCode"));
			inputVal.put("roadAddress",		rtnMap.get("roadAddress"));
			inputVal.put("detailAddress",	rtnMap.get("detailAddress"));
			
			List<Map<String, Object>> fileList = (List<Map<String, Object>>) rtnMap.get("mediaUrl");
			if(menusList != null) {
				for(int j = 0; j < fileList.size(); j++){
					Map<String, Object> file = fileList.get(j);
					String typeName = CommonUtils.getString(file.get("typeName"));
					if( "background".equals(typeName) ) {
						inputVal.put("preBgImg",		file.get("url"));
						inputVal.put("bgImgFilePath",	file.get("url"));
					}
					if( "profile".equals(typeName) ) {
						inputVal.put("preProfileImg",		file.get("url"));
						inputVal.put("profileImgFilePath",	file.get("url"));
					}
				}
			}
			
			if( rtnMap.get("email") != null ) {
				if( rtnMap.get("email").toString().indexOf("@") > 0 ) {
					inputVal.put("email",			rtnMap.get("email").toString().split("@")[0]);
					inputVal.put("email2",			rtnMap.get("email").toString().split("@")[1]);
				} else {
					inputVal.put("email",			"");
				}
			}
			
			
			inputVal.put("webSiteUrl",		rtnMap.get("webSiteUrl"));
			inputVal.put("rcsReply",		rtnMap.get("projectId"));
			
			// 쳇봇데이터
			List<Object> chatbotList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_BRAND_CHATBOT, params);
			if(chatbotList.size() > 0) {
				Map<String, Object> chatbotMap = (Map<String, Object>) chatbotList.get(0);
				inputVal.put("mainMdn",			chatbotMap.get("mainMdn"));
				inputVal.put("mainTitle",		chatbotMap.get("mainTitle"));
			} else {
				inputVal.put("mainMdn",			"");
				inputVal.put("mainTitle",		"");
			}
			
			result.put("inputVal", inputVal);
		}
		
		rtn.setData(result);
		
		return rtn;
	}
	
	@SuppressWarnings("static-access")
	public String checkFileSizeExtension(MultipartFile file) {
		boolean rtnboolean = true;
		// 이미지 업로드 용량 유효성 체크
		if (file.getSize() > 1000000) {
			rtnboolean = false;
		}
		
		// 확장자
		String fileExtension = commonService.getFileNameExt(file.getOriginalFilename(),1);
		if( "jpg".equals(fileExtension) ) {
		} else if ( "png".equals(fileExtension) ) {
		} else {
			rtnboolean =  false;
		}
		
		if( !rtnboolean ) {
			return "최대사이즈 : 1080X1080px / 1:1 비율 권장 / 파일형식 : jpg, png (최대 1MB)에 맞지않습니다.";
		} else {
			return "";
		}
	}
	
	// RCS 브랜드 등록 요청
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void saveRcsBrandReqForApi(Map<String, Object> params) throws Exception {
		// 임시 파일 패스 사용 
		String tempYn = "N";
		
		String profileImgFilePath = "";
		String bgImgFilePath = "";
		String certiFilePath = "";
		
		if( "N".equals(tempYn) ) {
			String uploadDirPath = FileConfig.getFilePath(FileConfig.FileSvcType.LIBRARY);
			// 프로필 파일 업로드 처리
			MultipartFile profileImgFile = (MultipartFile) params.get("profileImgFile");
			
			String profileImgFileCheckStr = checkFileSizeExtension(profileImgFile);
			if( !"".equals(profileImgFileCheckStr) ) {
				throw new Exception("프로필 이미지의 형식이 "+profileImgFileCheckStr);
			}
			
			String profileImgFileSeq = commonService.uploadFile(profileImgFile, CommonUtils.getString(params.get("loginId")), uploadDirPath);
			
			Map<String, Object> profileImgFileMap = new HashMap<String, Object>();
			profileImgFileMap.put("fileId", profileImgFileSeq);
			profileImgFilePath = CommonUtils.getString(generalDao.selectGernalObject("common.selectFilePathByFileId", profileImgFileMap));
			
			// 배경 파일 업로드 처리
			MultipartFile bgImgFile = (MultipartFile) params.get("bgImgFile");
			
			String bgImgFileCheckStr = checkFileSizeExtension(bgImgFile);
			if( !"".equals(bgImgFileCheckStr) ) {
				throw new Exception("백그라운드 이미지의 형식이 "+bgImgFileCheckStr);
			}
			
			String bgImgFileSeq = commonService.uploadFile(bgImgFile, CommonUtils.getString(params.get("loginId")), uploadDirPath);
			
			Map<String, Object> bgImgFileMap = new HashMap<String, Object>();
			bgImgFileMap.put("fileId", bgImgFileSeq);
			bgImgFilePath = CommonUtils.getString(generalDao.selectGernalObject("common.selectFilePathByFileId", bgImgFileMap));
			
			// 가입증명 파일 업로드 처리
			MultipartFile certiFile = (MultipartFile) params.get("certiFile");
			
			String certiFileSeq = commonService.uploadFile(certiFile, CommonUtils.getString(params.get("loginId")), uploadDirPath);
			
			Map<String, Object> certiFileMap = new HashMap<String, Object>();
			certiFileMap.put("fileId", certiFileSeq);
			certiFilePath = CommonUtils.getString(generalDao.selectGernalObject("common.selectFilePathByFileId", certiFileMap));
		} else {
			profileImgFilePath	= "/efs/file/console/2021/05/28/10/test1234.png";
			bgImgFilePath		= "/efs/file/console/2021/05/28/10/test1234.png";
			certiFilePath		= "/efs/file/console/2021/05/28/10/test1234.png";
		}
		
		// 데이터 처리
		List<Object> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		String brandId = CommonUtils.getString(params.get("brandId"));
		
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
		if(!"".equals(CommonUtils.getString(params.get("tickets"))) && Boolean.parseBoolean(CommonUtils.getString(params.get("tickets"))) != false) {
			Map<String, Object> menusMap = new HashMap<>();
			menusMap.put("buttonType",	"tickets");
			menusMap.put("weblink",		params.get("ticketsWeblink"));
			menusList.add(menusMap);
		}
		if(!"".equals(CommonUtils.getString(params.get("moreinfo"))) && Boolean.parseBoolean(CommonUtils.getString(params.get("moreinfo"))) != false) {
			Map<String, Object> menusMap = new HashMap<>();
			menusMap.put("buttonType",	"moreinfo");
			menusMap.put("weblink",		params.get("moreinfoWeblink"));
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
		
		map.put("profileImgFilePath",	profileImgFilePath);
		map.put("bgImgFilePath",		bgImgFilePath);
		map.put("certiFilePath",		certiFilePath);
		
		String chatbotStr = CommonUtils.getString(params.get("chatbots"));
		JSONParser parser = new JSONParser();
		JSONArray chatbotJson = new JSONArray();
		if( !"".equals(chatbotStr) ) {
			chatbotStr = "[" + chatbotStr + "]";
			parser = new JSONParser();
			chatbotJson = (JSONArray) parser.parse(chatbotStr);
			map.put("chatbots",		chatbotJson);
		}
		
		// map to json
		kong.unirest.json.JSONObject json2222 =  new kong.unirest.json.JSONObject(map);
		
		System.out.println("-------------------------------------------!!!!!!!!! requset body json : " + json2222);
//		list.add(json2222);
		list.add(map);
		
		// 임시저장
		String sts = CommonUtils.getString(params.get("sts"));
		if( "save".equals(sts) ) {
			ObjectMapper mapper = new ObjectMapper();
			
			Map<String, Object> brandInfo = new HashMap<>();
			
			brandInfo.put("corpId",			params.get("corpId"));
			brandInfo.put("projectId",		params.get("projectId"));
			brandInfo.put("name",			params.get("name"));
			brandInfo.put("description",	params.get("description"));
			brandInfo.put("tel",			params.get("tel"));
			brandInfo.put("menus",			menusList);
			brandInfo.put("categoryId",		params.get("categoryId"));
			brandInfo.put("subCategoryId",	params.get("subCategoryId"));
			brandInfo.put("categoryOpt",	params.get("categoryOpt"));
			brandInfo.put("zipCode",		params.get("zipCode"));
			brandInfo.put("roadAddress",	params.get("roadAddress"));
			brandInfo.put("detailAddress",	params.get("detailAddress"));
			brandInfo.put("email",			params.get("email")+ "@" + params.get("email2"));
			brandInfo.put("webSiteUrl",		params.get("webSiteUrl"));
			brandInfo.put("mainMdn",		params.get("mainMdn"));
			brandInfo.put("profileImgFilePath",	profileImgFilePath);
			brandInfo.put("bgImgFilePath",		bgImgFilePath);
			brandInfo.put("certiFilePath",		certiFilePath);
			brandInfo.put("chatbots",		chatbotJson);	// 임시저장 시, 쳇봇들을 관리하기 위해 저장
			String brandInfoStr = mapper.writeValueAsString(brandInfo);
			params.put("brandInfo", brandInfoStr);
			
			// 임시저장된 것을 수정 시
			if( !"".equals(brandId) && "T".equals(brandId.substring(0, 1)) ) {
				generalDao.updateGernal(DB.QRY_UPDATE_RCS_BRANDREQ, params);
			} else {
			// 임시저장 입력 시
				String tempBrandId = CommonUtils.getCommonId("T", 4);
				params.put("brandId", tempBrandId);
				params.put("brandReqKey", "TEMP");
				
				// 브랜드 저장
				generalDao.insertGernal(DB.QRY_INSERT_RCS_BRANDREQ, params);
				
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
					
					generalDao.insertGernal(DB.QRY_INSERT_RCS_CHATBOTREQ, chatbotObj);
				}
				
			}
			
		} else if( "approval".equals(sts) ) {
			// 등록요청
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("apiId",		params.get("apiKey"));
			headerMap.put("apiSecret",	params.get("apiSecret"));
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(map);
			Map<String, Object> bodyMap = new HashMap<String, Object>();
			
			bodyMap.put("list", json);
			
			if( !"".equals(brandId) && "T".equals(brandId.substring(0, 1)) ) {
				// 임시저장된것을 승인 처리하는 경우 임시저장된 브랜드와 쳇봇 삭제처리
				generalDao.insertGernal(DB.QRY_DELETE_RCS_TEMP_CHATBOTREQ, params);
				generalDao.insertGernal(DB.QRY_DELETE_RCS_TEMP_BRANDREQ, params);
			}
			
			// API 통신 처리
			Map<String, Object> result =  apiInterface.listPost("/console/v1/brand/", list, headerMap);
			
//			System.out.println("-------------------------------------------@@@ result : " + result);
//			System.out.println("-------------------------------------------@@@ headerMap : " + headerMap);
//			System.out.println("-------------------------------------------@@@ list : " + list);
			// 성공인지 실패인지 체크
			if( "10000".equals(result.get("code")) ) {
			} else if ( "500100".equals(result.get("code")) ) {
				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
				throw new Exception(errMsg);
			} else {
				String errMsg = CommonUtils.getString(result.get("message"));
				throw new Exception(errMsg);
			}
		} if( "update".equals(sts) ) {
			// 수정요청
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("apiId",		params.get("apiKey"));
			headerMap.put("apiSecret",	params.get("apiSecret"));
			headerMap.put("brandId",	brandId);
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(map);
			Map<String, Object> bodyMap = new HashMap<String, Object>();
			bodyMap.put("list", json);
			
			Map<String, Object> result =  apiInterface.put("/console/v1/brand/" + brandId, null, map, headerMap);
			
			// 성공인지 실패인지 체크
			if( "10000".equals(result.get("code")) ) {
			} else if ( "500100".equals(result.get("code")) ) {
				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
				throw new Exception(errMsg);
			} else {
				String errMsg = CommonUtils.getString(result.get("message"));
				throw new Exception(errMsg);
			}
		} else if ( "delete".equals(sts) ) {
			// 삭제요청
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("apiId",		params.get("apiKey"));
			headerMap.put("apiSecret",	params.get("apiSecret"));
			headerMap.put("brandId",	brandId);
			
			Map<String, Object> result =  apiInterface.listDelete("/console/v1/brand/" + brandId, list, headerMap);
			
			// 성공인지 실패인지 체크
			if( "10000".equals(result.get("code")) ) {
			} else if ( "500100".equals(result.get("code")) ) {
				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
				throw new Exception(errMsg);
			} else {
				String errMsg = CommonUtils.getString(result.get("message"));
				throw new Exception(errMsg);
			}
		}
	}
	
	// 발신번호관리 삭제 요청
	@SuppressWarnings("unchecked")
	public void deleteRcsBrandForApi(Map<String, Object> params) throws Exception {

		Map<String, Object> apiBodyMap = new HashMap<>();
		apiBodyMap.put("corpId", CommonUtils.getString(params.get("corpId")));
		apiBodyMap.put("projectId", CommonUtils.getString(params.get("projectId")));
		
		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put("apiId",		CommonUtils.getString(params.get("apiKey")));
		headerMap.put("apiSecret",	CommonUtils.getString(params.get("apiSecret")));
		headerMap.put("brandId",	CommonUtils.getString(params.get("brandId")));
		
		// API 통신 처리
		Map<String, Object> result =  apiInterface.delete("/console/v1/brand/" + CommonUtils.getString(params.get("brandId")), null, apiBodyMap, headerMap);
		
		System.out.println("------------------------------------------------- deleteRcsBrandForApi result : " + result);
		
		// 성공인지 실패인지 체크
		if( "10000".equals(result.get("code")) ) {
		} else if ( "500100".equals(result.get("code")) ) {
			String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
			throw new Exception(errMsg);
		} else {
			String errMsg = CommonUtils.getString(result.get("message"));
			throw new Exception(errMsg);
		}
	}
	
	public File multipartToFile(MultipartFile mfile) throws Exception {
		File file = new File(mfile.getOriginalFilename());
		mfile.transferTo(file);
		return file;
	}
	
	// PUSH 조회
	public RestResult<?> selectPushManageList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_PUSH_MANAGE_LIST, params);
		
		rtn.setData(rtnList);

		return rtn;
	}
	
	// PUSH 저장
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void savePushManage(Map<String, Object> params) throws Exception {
		// 
		String sts = CommonUtils.getString(params.get("sts"));
		
		// P8 인증서 정리
		if( params.get("apnsCetification") != null ) {
			MultipartFile apnsCetification = (MultipartFile) params.get("apnsCetification");
			
			params.put("apnsFileName", apnsCetification.getOriginalFilename());
			
			File apnsCertifile = this.convert(apnsCetification);
			if(apnsCertifile.exists()){
				FileInputStream inputStream = null;
				try {
					byte[] byteArray = new byte[(int) apnsCertifile.length()];
					inputStream = new FileInputStream(apnsCertifile);
					inputStream.read(byteArray);  // 인증서를 byte[]에 담는다.
					params.put("apnsCetificationByteArray", byteArray);
				} catch (Exception e){
					e.printStackTrace();
				}finally {
					try {
						if (inputStream != null) {
							inputStream.close();
						}
					} catch (Exception e) {
						inputStream = null;
					} finally {
						inputStream = null;
					}
				}
			}
		} else {
			params.put("apnsCetificationByteArray", null);
		}
		
		if( "C".equals(sts) ) {
			int cnt= generalDao.selectGernalCount(DB.QRY_SELECT_PUSH_MANAGE_LIST_CNT, params);
			
			if( cnt >= 5 ) {
				throw new Exception("한 프로젝트엔 최대 5개의 PUSH만 등록 가능합니다.");
			}
			
			if( !"".equals(CommonUtils.getString(params.get("iosAppId"))) ) {
				if( params.get("apnsCetificationByteArray") == null  ) {
					throw new Exception("저장시에는 P8인증서가 필수입니다.");
				}
			}
			
			// push id 생성
			String appId = CommonUtils.getCommonId("APP" + CommonUtils.getString(params.get("corpId")), 10);
			params.put("appId", appId);
			generalDao.insertGernal(DB.QRY_INSERT_PUSH_MANAGE, params);
		} else if( "U".equals(sts) ) {
			generalDao.updateGernal(DB.QRY_UPDATE_PUSH_MANAGE, params);
		}
	}
	
	// 파일변환용
	public File convert(MultipartFile mfile) throws IOException {
		File file = new File(mfile.getOriginalFilename());
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(mfile.getBytes());
		fos.close();
		return file;
	}

	// push 삭제
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void deletePushManage(Map<String, Object> params) throws Exception {
		// 삭제 벨리데이션 추가 필요
		String valildationYn = "Y";
		
		if( "Y".equals(valildationYn) ) {
			generalDao.deleteGernal(DB.QRY_DELETE_PUSH_MANAGE, params);
		}
	}
	
	// RCS 브랜드 조회
	@SuppressWarnings("unchecked")
	public RestResult<?> selectRcsBrandMsgBaseList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_BRAND_MSGBASE_LIST, params);
				
		for (int i = 0; i < rtnList.size(); i++) {
			// 파라미터 정리
			Map<String, Object> rtnMap = (Map<String, Object>) rtnList.get(i);
			
			JSONParser jParser = new JSONParser();
			JSONObject brandInfo = (JSONObject) jParser.parse(CommonUtils.getString(rtnMap.get("messagebaseInfo")));
			
			Map<String, Object> guideInfo = (Map<String, Object>) brandInfo.get("guideInfo");
			rtnMap.put("imageWidth",			guideInfo.get("imageWidth"));			// 이미지(px)
			rtnMap.put("imageHeight",			guideInfo.get("imageHeight"));			// 이미지(px)
			
//			Map<String, Object> detailInfo = (Map<String, Object>) guideInfo.get("detailInfo");
//			rtnMap.put("productCardType",		detailInfo.get("productCardType"));		// 상품코드
			
			Map<String, Object> policyInfo = (Map<String, Object>) brandInfo.get("policyInfo");
			rtnMap.put("cardCount",				policyInfo.get("cardCount"));			// 카드장수
			rtnMap.put("maxTitleSize",			policyInfo.get("maxTitleSize"));		// 제목글자수
			rtnMap.put("maxDescriptionSize",	policyInfo.get("maxDescriptionSize"));	// 본문글자수
			rtnMap.put("maxButtonCount",		policyInfo.get("maxButtonCount"));		// 버튼개수
			rtnMap.put("maxMediaSize",			policyInfo.get("maxMediaSize"));		// 이미지 용량
		}
		
		rtn.setData(rtnList);

		return rtn;
	}
	
	// MO 수신번호 조회
	@SuppressWarnings("unchecked")
	public RestResult<?> selectMoCallbackList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		Map<String, Object> pageInfo = (Map<String, Object>) params.get("pageInfo");
		
		if (pageInfo != null && !pageInfo.isEmpty()) {
			int rowNum = generalDao.selectGernalCount(DB.QRY_SELECT_MO_CALLBACK_LIST_CNT, params);
			pageInfo.put("rowNum", rowNum);
			
			rtn.setPageInfo(pageInfo);
		}
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_MO_CALLBACK_LIST, params);
		
		rtn.setData(rtnList);

		return rtn;
	}
	
	// MO 수신번호 저장
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void saveMoCallback(Map<String, Object> params) throws Exception {
		String sts = CommonUtils.getString(params.get("sts"));
		
		if( "C".equals(sts) ) {
			String apiKey = CommonUtils.getString(generalDao.selectGernalObject("channel.selectApikeyForMoApi",params)); 
			params.put("apiKey", apiKey);
			
			Map<String, Object> apiBodyMap = new HashMap<>();
			apiBodyMap.put("moNumber",		CommonUtils.getString(params.get("moNumber")));
			apiBodyMap.put("apiKey",		apiKey);
			apiBodyMap.put("moType",		CommonUtils.getString(params.get("moType")));
			apiBodyMap.put("projectId",		CommonUtils.getString(params.get("projectId")));
			apiBodyMap.put("useYn",			"Y");
			apiBodyMap.put("webhookUrl",	"");
			
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("type",		sts);
			
//			// API 통신 처리
//			Map<String, Object> result =  apiInterface.post("/redis/v1/moCallback/" + sts, null, apiBodyMap, headerMap);
//			
//			System.out.println("------------------------------------------------- saveMoCallback result : " + result);
//			
//			// 성공인지 실패인지 체크
//			if( "10000".equals(result.get("code")) ) {
//			} else if ( "500100".equals(result.get("code")) ) {
//				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
//				throw new Exception(errMsg);
//			} else {
//				String errMsg = CommonUtils.getString(result.get("message"));
//				throw new Exception(errMsg);
//			}
			
			generalDao.insertGernal(DB.QRY_INSERT_MO_CALLBACK, params);
		} else if( "U".equals(sts) ) {
			String apiKey = CommonUtils.getString(params.get("apiKey")); 
			params.put("apiKey", apiKey);
			
			Map<String, Object> apiBodyMap = new HashMap<>();
			apiBodyMap.put("moNumber",		CommonUtils.getString(params.get("moNumber")));
			apiBodyMap.put("apiKey",		apiKey);
			apiBodyMap.put("moType",		CommonUtils.getString(params.get("moType")));
			apiBodyMap.put("projectId",		CommonUtils.getString(params.get("projectId")));
			apiBodyMap.put("useYn",			"Y");
			apiBodyMap.put("webhookUrl",	"");
			
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("type",		sts);
			
//			// API 통신 처리
//			Map<String, Object> result =  apiInterface.post("/redis/v1/moCallback/" + sts, null, apiBodyMap, headerMap);
//			
//			System.out.println("------------------------------------------------- saveMoCallback U result : " + result);
//			
//			// 성공인지 실패인지 체크
//			if( "10000".equals(result.get("code")) ) {
//			} else if ( "500100".equals(result.get("code")) ) {
//				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
//				throw new Exception(errMsg);
//			} else {
//				String errMsg = CommonUtils.getString(result.get("message"));
//				throw new Exception(errMsg);
//			}
			
			generalDao.updateGernal(DB.QRY_UPDATE_MO_CALLBACK, params);
		} else if( "D".equals(sts) ) {
			String apiKey = CommonUtils.getString(params.get("apiKey")); 
			params.put("apiKey", apiKey);
			
			Map<String, Object> apiBodyMap = new HashMap<>();
			apiBodyMap.put("moNumber",		CommonUtils.getString(params.get("moNumber")));
			apiBodyMap.put("apiKey",		apiKey);
			apiBodyMap.put("moType",		CommonUtils.getString(params.get("moType")));
			apiBodyMap.put("projectId",		CommonUtils.getString(params.get("projectId")));
			apiBodyMap.put("useYn",			"Y");
			apiBodyMap.put("webhookUrl",	"");
			
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("type",		sts);
			
//			// API 통신 처리
//			Map<String, Object> result =  apiInterface.post("/redis/v1/moCallback/" + sts, null, apiBodyMap, headerMap);
//			
//			// 성공인지 실패인지 체크
//			if( "10000".equals(result.get("code")) ) {
//			} else if ( "500100".equals(result.get("code")) ) {
//				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
//				throw new Exception(errMsg);
//			} else {
//				String errMsg = CommonUtils.getString(result.get("message"));
//				throw new Exception(errMsg);
//			}
			
			generalDao.deleteGernal(DB.QRY_DELETE_MO_CALLBACK, params);
		}
		
		// redis 테이블 처리
		commonService.updateCmCmdForRedis("CM_MO_CALLBACK");
	}

	public RestResult<?> getApiKeyListForKko(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> rtnList = generalDao.selectGernalList("channel.selectApikeyListForKko", params);
		
		rtn.setData(rtnList);

		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	public RestResult<?> getKkoCategory(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		Map<String, Object> getHeaderMap = new HashMap<String, Object>();
		
		Map<String, Object> result = apiInterface.post("/console/v1/kko/senderkey/category/all", getHeaderMap);
		
		// 성공인지 실패인지 체크
		if( "10000".equals(result.get("code")) ) {
			rtn.setData(result);
		} else if ( "500100".equals(result.get("code")) ) {
			String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
			throw new Exception(errMsg);
		} else {
			String errMsg = CommonUtils.getString(result.get("message"));
			throw new Exception(errMsg);
		}
		
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	public RestResult<?> getSenderKeyToken(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		String apiKey = CommonUtils.getString(params.get("apiKey")); 
		
		Map<String, Object> getHeaderMap = new HashMap<String, Object>();
		getHeaderMap.put("apiKey", apiKey);
		
		Map<String, Object> apiBodyMap = new HashMap<>();
		apiBodyMap.put("phoneNumber",	CommonUtils.getString(params.get("phoneNumber")));
		apiBodyMap.put("kkoChId",		CommonUtils.getString(params.get("kkoChId")));
		
		Map<String, Object> result = apiInterface.post("/console/v1/kko/senderkey/token", null, apiBodyMap, getHeaderMap);
		
		// 성공인지 실패인지 체크
		if( "10000".equals(result.get("code")) ) {
			rtn.setData(result);
		} else if ( "500100".equals(result.get("code")) ) {
			String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
			throw new Exception(errMsg);
		} else {
			String errMsg = CommonUtils.getString(result.get("message"));
			throw new Exception(errMsg);
		}
		
		return rtn;
	}
	
	public RestResult<?> selectKkoCh(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		if(params.containsKey("pageNo")
				&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize")
				&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount("channel.selectKkoChCount", params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList("channel.selectKkoCh", params);
		rtn.setData(rtnList);
		
		return rtn;
	}

	public RestResult<?> selectKkoChGroup(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> list = generalDao.selectGernalList("channel.selectKkoChGroup", params);
		
		rtn.setData(list);
		
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void saveKkoChForApi(Map<String, Object> params) throws Exception {
		String sts = CommonUtils.getString(params.get("sts"));
		
		if( "C".equals(sts) ) {
			
			// 토큰 API 통신 처리
			Map<String, Object> apiBodyMap = new HashMap<>();
			apiBodyMap.put("phoneNumber",	CommonUtils.getString(params.get("phoneNumber")));
			apiBodyMap.put("token",			CommonUtils.getString(params.get("token")));
			apiBodyMap.put("categoryCode",	CommonUtils.getString(params.get("categoryCode")));
			apiBodyMap.put("kkoChId",		CommonUtils.getString(params.get("kkoChId")));
			
			String apiKey = CommonUtils.getString(params.get("apiKey"));
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("apiKey",		apiKey);
			headerMap.put("commonProjectYN",		"N");
			
			
			Map<String, Object> result =  apiInterface.post("/console/v1/kko/senderkey/channel/create", null, apiBodyMap, headerMap);
			
			//System.out.println("------------------------------------------------- saveKkoChForApi result : " + result);
			
			// 성공인지 실패인지 체크
			if( "10000".equals(result.get("code")) ) {
			} else if ( "500100".equals(result.get("code")) ) {
				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
				throw new Exception(errMsg);
			} else {
				String errMsg = CommonUtils.getString(result.get("message"));
				throw new Exception(errMsg);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void saveKkoChGroupForApi(Map<String, Object> params) throws Exception {
		// 이미 등록된 정보와 맞는지 매칭
		String grpKey		= CommonUtils.getString(params.get("grpKey"));
		String senderKey	= CommonUtils.getString(params.get("senderKey"));
		
		
		Map<String, Object> apiBodyMap = new HashMap<>();
		
		Map<String, Object> headerMap = new HashMap<String, Object>();
		String apiKey = CommonUtils.getString(generalDao.selectGernalObject("channel.selectApikeyForMoApi",params));
		headerMap.put("apiKey",		apiKey);
		
		
		Map<String, Object> result =  apiInterface.get("/console/v1/kko/senderkey/group/all", null, apiBodyMap, headerMap);
		
		System.out.println("------------------------------------------------- result : " + result);
		
		// 성공인지 실패인지 체크
		if( "10000".equals(result.get("code")) ) {
		} else if ( "500100".equals(result.get("code")) ) {
			String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
			throw new Exception(errMsg);
		} else {
			String errMsg = CommonUtils.getString(result.get("message"));
			throw new Exception(errMsg);
		}
		
		List<Map<String, Object>> kkoListApi = (List<Map<String, Object>>) result.get("data");
		
		if( kkoListApi.size() == 0 ) {
			throw new Exception("등록된 카카오 그룹 정보가 없습니다.");
		} else {
			for( Map<String, Object> kkoMapApi : kkoListApi ) {
				String grpKeyApi		= CommonUtils.getString(kkoMapApi.get("grpKey"));
				String senderKeyApi		= CommonUtils.getString(kkoMapApi.get("senderKey")); 
				
				if( grpKeyApi.equals(grpKey) && senderKeyApi.equals(senderKey) ) {
					Map<String, Object> saveMap = new HashMap<>();
					saveMap.putAll(params);
					generalDao.insertGernal("channel.insertKkoChGroupForApi", params);
				} else {
					throw new Exception("등록된 카카오 그룹 정보가 없습니다.");
				}
			}
		}
	}
}