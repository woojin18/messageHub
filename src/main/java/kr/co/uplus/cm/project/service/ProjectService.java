package kr.co.uplus.cm.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
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
			
			String rcsYn	= CommonUtils.getString(rtnMap.get("rcsYn"));
			String smsmmsYn	= CommonUtils.getString(rtnMap.get("smsmmsYn"));
			String pushYn	= CommonUtils.getString(rtnMap.get("pushYn"));
			String kakaoYn	= CommonUtils.getString(rtnMap.get("kakaoYn"));
			String moYn		= CommonUtils.getString(rtnMap.get("moYn"));
			
			String useChStr = "";
			
			if( "Y".equals(rcsYn) )		useChStr += "RCS,";
			if( "Y".equals(smsmmsYn) )	useChStr += "SMS/MMS,";
			if( "Y".equals(pushYn) )	useChStr += "PUSH,";
			if( "Y".equals(kakaoYn) )	useChStr += "KAKAO,";
			if( "Y".equals(moYn) )		useChStr += "MO,";
			
			if( useChStr.length() > 0 ) {
				useChStr = useChStr.substring(0, useChStr.length()-1);
			}
			
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
			jsonInfo += "	\"SMSMMS\"	: \"" + CommonUtils.getString(params.get("radioMms")) + "\",";
			jsonInfo += "	\"PUSH\"	: \"" + CommonUtils.getString(params.get("radioPush")) + "\",";
			jsonInfo += "	\"KAKAO\"	: \"" + CommonUtils.getString(params.get("radioKko")) + "\",";
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
			jsonInfo += "	\"SMSMMS\"	: \"" + CommonUtils.getString(params.get("radioMms")) + "\",";
			jsonInfo += "	\"PUSH\"	: \"" + CommonUtils.getString(params.get("radioPush")) + "\",";
			jsonInfo += "	\"KAKAO\"	: \"" + CommonUtils.getString(params.get("radioKko")) + "\",";
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
		String tempYn = "Y";
		
		Map<String, Object> certiFileRtnMap = new HashMap<String, Object>();
		
		if( "N".equals(tempYn) ) {
			// 가입증명 파일 업로드 처리
			MultipartFile certiFile = (MultipartFile) params.get("certiFile");
			
			RestResult<Object> certiFileRtn = commonService.uploadFile(certiFile, CommonUtils.getString(params.get("loginId")));
			
			certiFileRtnMap = (Map<String, Object>) certiFileRtn.getData();
		}
		
		// 데이터 처리
		List<Object> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		//String brandId = CommonUtils.getString(params.get("brandId"));
		
		// 임시
		String brandId = "BR.s37xUZ49h7";
		
		map.put("corpId",		params.get("corpId"));
		
		if( "N".equals(tempYn) ) {
			map.put("subNumCertificate",		params.get("profileImgFilePath"));
		} else {
			// 임시
			map.put("subNumCertificate", "/efs/file/console/2021/04/26/12/test1234.png");
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
		
		// 등록요청
		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put("brandId",	brandId);
		
		// API 통신 처리
		Map<String, Object> result =  apiInterface.post("/console/v1/brand/" + brandId + "/chatbot", map, headerMap);
		
		// 성공인지 실패인지 체크
		if( !"10000".equals(result.get("rslt")) ) {
			String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
			throw new Exception(errMsg);
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
	
	// 발신번호관리 수정 요청
	@SuppressWarnings("unchecked")
	public void updateCallbackForApi(Map<String, Object> params) throws Exception {
		// 상세정보 가져오기
		Map<String, Object> callbackDetail = (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_CALLBACK_MANAGE_DETAIL, params);
		
		String brandId		= CommonUtils.getString(callbackDetail.get("brandId"));
		String chatbotId	= CommonUtils.getString(params.get("chatbotId"));
		
		// request body 조정
		System.out.println("-------------------------------------@@ " + callbackDetail);
		
		Map<String, Object> apiMap = new HashMap<>();
		
		apiMap.put("corpId", CommonUtils.getString(callbackDetail.get("corpId")));
		apiMap.put("subNumCertificate", "");
		
		Map<String, Object> chahbotMap = new HashMap<>();
		chahbotMap.put("mdn", callbackDetail.get("mdn"));
		chahbotMap.put("rcsReply", callbackDetail.get("rcsReply"));
		chahbotMap.put("subTitle", callbackDetail.get("subTitle"));
		chahbotMap.put("service", "a2p");
		chahbotMap.put("display", "01");
		
		apiMap.put("chatbot", chahbotMap);
		
		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put("brandId",	brandId);
		headerMap.put("chatbotId",	chatbotId);
		
		// API 통신 처리
		Map<String, Object> result =  apiInterface.put("/console/v1/brand/" + brandId + "/chatbot/" + chatbotId, null, apiMap, headerMap);
		
		System.out.println("------------------------------------------------- resultresultresult : " + result);
		
		// 성공인지 실패인지 체크
		if( !"10000".equals(result.get("rslt")) ) {
			String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
			throw new Exception(errMsg);
		}
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
		
		System.out.println("------------------------------------------------- resultresultresult : " + result);
		
		// 성공인지 실패인지 체크
		if( !"10000".equals(result.get("rslt")) ) {
			String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
			throw new Exception(errMsg);
		}
	}
}
