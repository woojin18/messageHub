package kr.co.uplus.cm.project.service;

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

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.model.AuthUser;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class ProjectService {

	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private ApiInterface apiInterface; 

	// 프로젝트 리스트 조회
	@SuppressWarnings("unchecked")
	public RestResult<?> selectProjectList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList("project.selectProjectList", params);
		

		for (int i = 0; i < rtnList.size(); i++) {
			Map<String, Object> rtnMap = (Map<String, Object>) rtnList.get(i);
			JSONParser parser = new JSONParser();
			Object obj = parser.parse( CommonUtils.getString(rtnMap.get("useChGrpInfo")) );

			JSONObject jsonObj = (JSONObject) obj;
			
			String rcsYn	= CommonUtils.getString(jsonObj.get("RCS"));
			String smsmmsYn	= CommonUtils.getString(jsonObj.get("SMS/MMS"));
			String pushYn	= CommonUtils.getString(jsonObj.get("PUSH"));
			String kakaoYn	= CommonUtils.getString(jsonObj.get("KKO"));
			String moYn		= CommonUtils.getString(jsonObj.get("MO"));
			
			String useChStr = "";
			
			if( "Y".equals(rcsYn) )		useChStr += "RCS,";
			if( "Y".equals(smsmmsYn) )	useChStr += "SMS/MMS,";
			if( "Y".equals(pushYn) )	useChStr += "PUSH,";
			if( "Y".equals(kakaoYn) )	useChStr += "KAKAO,";
			if( "Y".equals(moYn) )		useChStr += "MO,";
			
			if( useChStr.length() > 0 ) {
				useChStr = useChStr.substring(0, useChStr.length()-1);
			}
			
			rtnMap.put("rcsYn", rcsYn);
			rtnMap.put("smsmmsYn", smsmmsYn);
			rtnMap.put("pushYn", pushYn);
			rtnMap.put("kakaoYn", kakaoYn);
			rtnMap.put("moYn", moYn);
			rtnMap.put("useCh", useChStr);
		}
		
		rtn.setData(rtnList);

		return rtn;
	}

	// 프로젝트 명 중복 체크
	public RestResult<?> checkProjectNameDuplicate(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);

		int checkCnt = generalDao.selectGernalCount("project.checkProjectNameDuplicate", params);
		
		if (checkCnt > 0) {
			rtn.setSuccess(false);
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}

	// 프로젝트 저장
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void saveProject(Map<String, Object> params) throws Exception {
		// 저장 상태값
		String sts = CommonUtils.getString(params.get("sts"));
		// 사용자 세션
		Map<String, Object> userMap = (Map<String, Object>) params.get("userDto");
		params.put("userId", userMap.get("userId"));

		if ("C".equals(sts)) {
			// 프로젝트 ID
			params.put("projectId", CommonUtils.getCommonId("PJT", 4));
			
			// 고객사가 개발이 안되서 임시로 고객사 코드 입력
			params.put("corpId", userMap.get("corpId"));

			// 사용체널 JSON 값 처리
			String jsonInfo = "";

			jsonInfo += "{";
			jsonInfo += "	\"RCS\"		: \"" + CommonUtils.getString(params.get("radioRcs")) + "\",";
			jsonInfo += "	\"SMS/MMS\"	: \"" + CommonUtils.getString(params.get("radioMms")) + "\",";
			jsonInfo += "	\"PUSH\"	: \"" + CommonUtils.getString(params.get("radioPush")) + "\",";
			jsonInfo += "	\"KKO\"		: \"" + CommonUtils.getString(params.get("radioKko")) + "\",";
			jsonInfo += "	\"MO\"		: \"" + CommonUtils.getString(params.get("radioMo")) + "\"";
			jsonInfo += "}";

			params.put("jsonInfo", jsonInfo);

			// 프로젝트 insert
			generalDao.insertGernal("project.insertProject", params);

			// -------------------------------------------------------------------------------------------------------------------------------------
			// 프로젝트 멤버 추가 처리, OWNER 권한자는 무조건 추가 처리되야함
			// 사용자 세션의 권한을 체크해서 OWNER 일경우 OWNER 유저 입력 처리 안하도록 처리 
			if( !"OWNER".equals(params.get("ROLE_CD")) ) {
				// 사용자 기본 멤버로 추가
				generalDao.insertGernal("project.insertProjectUser", params);
			}
			// -------------------------------------------------------------------------------------------------------------------------------------

			// API관리키 관리 insert ==> 기본 입력 안되기로 처리
			
			// -------------------------------------------------------------------------------------------------------------------------------------
			// 후불의 경우 청구ID 관리 ? ==> API 미개발
//			if ("N".equals(CommonUtils.getString(params.get("pay_type")))) {
//				generalDao.updateGernal("project.updateProjectBillId", params);
//			}
			
		} else if ("U".equals(sts)) {
			// 사용체널 JSON 값 처리
			String jsonInfo = "";

			jsonInfo += "{";
			jsonInfo += "	\"RCS\"		: \"" + CommonUtils.getString(params.get("radioRcs")) + "\",";
			jsonInfo += "	\"SMS/MMS\"	: \"" + CommonUtils.getString(params.get("radioMms")) + "\",";
			jsonInfo += "	\"PUSH\"	: \"" + CommonUtils.getString(params.get("radioPush")) + "\",";
			jsonInfo += "	\"KKO\"		: \"" + CommonUtils.getString(params.get("radioKko")) + "\",";
			jsonInfo += "	\"MO\"		: \"" + CommonUtils.getString(params.get("radioMo")) + "\"";
			jsonInfo += "}";

			params.put("jsonInfo", jsonInfo);
			
			generalDao.updateGernal("project.updateProject", params);
		} else if ("D".equals(sts)) {
			// 테이블 이력 조회 CM_MSG, CM_WEB_MSG
			int projectUseCnt = Integer.parseInt(
					CommonUtils.getString(generalDao.selectGernalCount("project.selectProjectUseCnt", params)));

			if (projectUseCnt == 0) {
				generalDao.deleteGernal("project.deleteProject", params);
				generalDao.deleteGernal("project.deleteProjectUser", params);
				generalDao.deleteGernal("project.deleteProjectApiKey", params);
			} else {
				throw new Exception("사용된 이력이 존재하여 해당 프로젝트는 삭제할 수 없습니다.");
			}

		}

	}

	public RestResult<?> checkPreRegYn(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);

		String checkYn = CommonUtils.getString(generalDao.selectGernalObject("project.checkPreRegYn", params));
		
		if( "Y".equals(checkYn) ) {
			rtn.setSuccess(true);
		} else {
			rtn.setSuccess(false);
		}

		
		return rtn;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void savePreRegExWithUploadFiles(List<MultipartFile> uploadFiles, Map<String, Object> params) throws Exception{
		// 이미 등록되어있는지 확인
		String checkYn = CommonUtils.getString(generalDao.selectGernalObject("project.checkPreRegYn", params));
		
		if( "Y".equals(checkYn) ) {
			throw new Exception("이미 사전등록 예외 대상 사업자로 등록 중 입니다.");
		} else {
			// 첨부파일 JSON 값 처리
			String jsonInfoStr = "{";
			
			for (int i = 0; i < uploadFiles.size(); i++) {
				
				MultipartFile uploadFile = uploadFiles.get(i);
				
				RestResult<Object> rtn = commonService.uploadFile(uploadFile, CommonUtils.getString(params.get("loginId")));
				
				Map<String, Object> rtnMap = (Map<String, Object>) rtn.getData();
				
				jsonInfoStr += "	\"fileName" + (i+1) + "\"		: \"" + CommonUtils.getString(rtnMap.get("attachFileName")) + "\",";
				if( i == (uploadFiles.size() - 1) ) {
					jsonInfoStr += "	\"filePath" + (i+1) + "\"		: \"" + CommonUtils.getString(rtnMap.get("attachFilePath")) + "\"";
				} else {
					jsonInfoStr += "	\"filePath" + (i+1) + "\"		: \"" + CommonUtils.getString(rtnMap.get("attachFilePath")) + "\",";
				}
			}
			
			jsonInfoStr += "}";

			params.put("attachFileList", jsonInfoStr);

			// 특수사업자관리 insert
			generalDao.insertGernal("project.insertSpecualBusi", params);
		}
	}

	@SuppressWarnings("unchecked")
	public void saveRcsChatbotReqForApi(Map<String, Object> params) throws Exception {
		String brandId = CommonUtils.getString(params.get("brandId"));
		String sts = CommonUtils.getString(params.get("sts"));
		String tempYn = "Y";
		Map<String, Object> certiFileRtnMap = new HashMap<String, Object>();
		
		if( "N".equals(tempYn) ) {
			// 가입증명 파일 업로드 처리
			MultipartFile certiFile = (MultipartFile) params.get("certiFile");
			
			RestResult<Object> certiFileRtn = commonService.uploadFile(certiFile, CommonUtils.getString(params.get("loginId")));
			
			certiFileRtnMap = (Map<String, Object>) certiFileRtn.getData();
		}
		
		// 데이터 처리
		Map<String, Object> map = new HashMap<>();
		
		map.put("corpId",		params.get("corpId"));
		
		if( "N".equals(tempYn) ) {
			map.put("subNumCertificate",		params.get("certiFile"));
		} else {
			// 임시
			map.put("subNumCertificate", "/efs/file/console/2021/05/28/10/test1234.png");
		}
		
		
		if( "C".equals(sts) ) {
			// 챗봇 처리
			String chatbotStr = CommonUtils.getString(params.get("chatbots"));
			JSONParser parser = new JSONParser();
			JSONArray chatbotJson = new JSONArray();
			if( !"".equals(chatbotStr) ) {
				chatbotStr = "[" + chatbotStr + "]";
				parser = new JSONParser();
				chatbotJson = (JSONArray) parser.parse(chatbotStr);
				map.put("chatbots",		chatbotJson);
			}
			
			// json object 편하게 보기 위한 용도
			kong.unirest.json.JSONObject jsonParam =  new kong.unirest.json.JSONObject(map);
			System.out.println("----------------------------------------@@@ jsonParam : " + jsonParam);
			
			// 등록요청
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("brandId",	brandId);
			headerMap.put("Content-Type",	"application/json");
			
			// API 통신 처리
			Map<String, Object> result =  apiInterface.post("/console/v1/brand/" + brandId + "/chatbot", map, headerMap);
			
			System.out.println("-----------------------------------------@@@ result : " + result);
			
			// 성공인지 실패인지 체크
			if( "10000".equals(result.get("rslt")) ) {
			} else if ( "500100".equals(result.get("rslt")) ) {
				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
				throw new Exception(errMsg);
			} else {
				String errMsg = CommonUtils.getString(result.get("rsltDesc"));
				throw new Exception(errMsg);
			}
		} else if ( "U".equals(sts) ) {
			// 챗봇 처리
			String chatbotStr = CommonUtils.getString(params.get("chatbots"));
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse( chatbotStr );
			JSONObject jsonObj = (JSONObject) obj;
			map.put("chatbot",		jsonObj);
			
			// json object 편하게 보기 위한 용도
			kong.unirest.json.JSONObject jsonParam =  new kong.unirest.json.JSONObject(map);
			System.out.println("----------------------------------------@@@ jsonParam : " + jsonParam);
			
			// 수정요청
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("brandId",	brandId);
			headerMap.put("chatbotId",	params.get("chatbotId"));
			headerMap.put("Content-Type",	"application/json");
			
			// API 통신 처리
			Map<String, Object> result =  apiInterface.put("/console/v1/brand/" + brandId + "/chatbot/" + params.get("chatbotId"), null, map, headerMap);
			
			System.out.println("-----------------------------------------@@@ result : " + result);
			
			// 성공인지 실패인지 체크
			if( "10000".equals(result.get("rslt")) ) {
			} else if ( "500100".equals(result.get("rslt")) ) {
				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
				throw new Exception(errMsg);
			} else {
				String errMsg = CommonUtils.getString(result.get("rsltDesc"));
				throw new Exception(errMsg);
			}
		}
	}
	
	// 발신번호관리 리스트 조회
	@SuppressWarnings("unchecked")
	public RestResult<?> selectCallbackManageList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		Map<String, Object> pageInfo = (Map<String, Object>) params.get("pageInfo");
		
		if (pageInfo != null && !pageInfo.isEmpty()) {
			int rowNum = generalDao.selectGernalCount(DB.QRY_SELECT_CALLBACK_MANAGE_LIST_CNT, params);
			pageInfo.put("rowNum", rowNum);
			
			rtn.setPageInfo(pageInfo);
		}
		
		List<Object> list = generalDao.selectGernalList(DB.QRY_SELECT_CALLBACK_MANAGE_LIST, params);
				
		rtn.setData(list);

		return rtn;
	}
	
	// 추가발신번호등록요청 브랜드 불러오기
	public RestResult<?> selectApprovalBrandList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> list = generalDao.selectGernalList(DB.QRY_SELECT_CALLBACK_MANAGE_APPROVAL_BRAND_LIST, params);
				
		rtn.setData(list);

		return rtn;
	}
	
	// 발신번호관리 삭제 요청
	@SuppressWarnings("unchecked")
	public void deleteCallbackForApi(Map<String, Object> params) throws Exception {
		String brandId		= CommonUtils.getString(params.get("brandId"));
		String chatbotId	= CommonUtils.getString(params.get("chatbotId"));

		Map<String, Object> apiMap = new HashMap<>();
		apiMap.put("corpId", CommonUtils.getString(params.get("corpId")));
		
		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put("brandId",	brandId);
		headerMap.put("chatbotId",	chatbotId);
		
		// API 통신 처리
		Map<String, Object> result =  apiInterface.delete("/console/v1/brand/" + brandId + "/chatbot/" + chatbotId, null, apiMap, headerMap);
		
		System.out.println("------------------------------------------------- deleteCallbackForApi result : " + result);
		
		// 성공인지 실패인지 체크
		if( "10000".equals(result.get("rslt")) ) {
		} else if ( "500100".equals(result.get("rslt")) ) {
			String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
			throw new Exception(errMsg);
		} else {
			String errMsg = CommonUtils.getString(result.get("rsltDesc"));
			throw new Exception(errMsg);
		}
	}
	
	//분배율 조회(분배서비스ID 없을 때)
	@SuppressWarnings("unchecked")
	public RestResult<?> selectBasicRatio(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_BASICDIS_RATIO, params);
		List<Object> rtnList2 = new ArrayList<Object>();
		HashMap<String,String> typehm = new HashMap<String,String>();
		HashMap<String,String> relayhm = new HashMap<String,String>();
		typehm.put("isExist", "F");
		
		for(int i=0; i<rtnList.size(); i++) {
			try {
				HashMap<String,String> hmrtn = (HashMap<String, String>) rtnList.get(i);
				
				String relayKey = hmrtn.get("CH_RELAY_TYPE");
				String relay = hmrtn.get("RELAY");
				boolean isexist = relayhm.containsKey(relayKey+"relay");
				if(!isexist) {
					if(typehm.containsKey("CH_RELAY_TYPE")) {
						String crt_tmp = typehm.get("CH_RELAY_TYPE");
						crt_tmp += ","+relayKey;
						typehm.replace("CH_RELAY_TYPE", crt_tmp);
					}else {
						typehm.put("CH_RELAY_TYPE", relayKey);
					}
					relayhm.put(relayKey+"relay", relay);
				}else {
					String tmp = relayhm.get(relayKey+"relay");
					tmp += ","+relay;
					relayhm.replace(relayKey+"relay", tmp);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		rtnList2.add(typehm);
		rtnList2.add(relayhm);
		
		rtn.setData(rtnList2);
		
		return rtn;
	}
	
	//분배율 조회(분배서비스ID 있을 때)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RestResult<?> selectRatio(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_DIS_RATIO, params);
		
		List<Object> rtnList2 = new ArrayList<Object>();
		HashMap<String,String> typehm = new HashMap<String,String>();
		HashMap<String,String> relayhm = new HashMap<String,String>();
		
		typehm.put("isExist", "T");
		
		HashMap<String,String> hmrtn = (HashMap<String, String>) rtnList.get(0);	//only one
		String rtnStr = hmrtn.get("CH_DIST_INFO");
		
		relayhm.put("USE_YN", hmrtn.get("USE_YN"));
		relayhm.put("SMART_CH_NAME", hmrtn.get("SMART_CH_NAME"));
		
		kong.unirest.json.JSONObject jo = new kong.unirest.json.JSONObject(rtnStr);
		Iterator it = jo.keys();
		ArrayList ar = new ArrayList();
	    while(it.hasNext())
	    {
	    	String jokey = it.next().toString();
	        ar.add(jokey); // 키 값 저장
	    }
	    typehm.put("CH_RELAY_TYPE", "");
		for(int i=0; i<ar.size(); i++){
			String crtStr = typehm.get("CH_RELAY_TYPE");
			String relayKey = (String) ar.get(i);
			crtStr += relayKey;
			
			kong.unirest.json.JSONArray ja = (kong.unirest.json.JSONArray) jo.get((String) ar.get(i));
			for(int j=0; j<ja.length(); j++) {
				kong.unirest.json.JSONObject jajo = (kong.unirest.json.JSONObject) ja.get(j);
				String relayCh = jajo.getString("relayCh");
				String distRatio = jajo.getString("distRatio");
				
				if(relayhm.containsKey(relayKey+"relay")) {
					String re_tmp = relayhm.get(relayKey+"relay");
					String dis_tmp = relayhm.get(relayKey+"ratio");
					re_tmp += ","+relayCh;
					dis_tmp += ","+distRatio;
					relayhm.replace(relayKey+"relay", re_tmp);
					relayhm.replace(relayKey+"ratio", dis_tmp);
				}else {
					relayhm.put(relayKey+"relay", relayCh);
					relayhm.put(relayKey+"ratio", distRatio);
				}
			}

			if((i+1)<ar.size()) {
				crtStr += ",";
			}
			typehm.replace("CH_RELAY_TYPE", crtStr);
		}

		rtnList2.add(typehm);
		rtnList2.add(relayhm);
		
		
		rtn.setData(rtnList2);

		return rtn;
	}
	
	//중계 채널 등록/수정
	public RestResult<?> saveDisRatio(Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.putAll(params);
		
		rtn.setSuccess(true);
		// 등록 수정 구분
		String iou = CommonUtils.getString(params.get("iou"));

		try {
			int existCode = generalDao.selectGernalCount(DB.QRY_SELECT_DUPLICATE_DISNAME , params);			// 채널 중복 확인
			if ("U".equals(iou)) {	//update -> 기존 데이터 존재해야함 -> 존재하면 update
				if(existCode > 0) {
					//update
					generalDao.updateGernal(DB.QRY_UPDATE_DISTRIBUTIONDATA, params);
					
				} else {
					rtn.setSuccess(false);
					rtn.setMessage("등록되지 않은 중계채널입니다.");
				}
			} else if ("I".equals(iou)) {//insert -> 기존 데이터가 존재하지 않아야함 -> 존재하지 않으면 insert
				if(existCode > 0) {
					rtn.setSuccess(false);
					rtn.setMessage("이미 등록 된 중계채널입니다.");
				}else {
					map.put("SMART_CH_CODE", CommonUtils.getCommonId("SCC", 5));
					generalDao.insertGernal(DB.QRY_INSERT_DISTRIBUTIONDATA, map);
					generalDao.updateGernal(DB.QRY_UPDATE_PROJECT_DISTRIBUTION, map);
				}
			}
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("저장에 실패하였습니다.");
		}

		return rtn;
	}
}
