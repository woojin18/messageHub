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

import kr.co.uplus.cm.common.consts.Const.CmdTgt;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.config.FileConfig;
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
		String corpId = CommonUtils.getString(params.get("corpId"));

		if ("C".equals(sts)) {
			// 프로젝트 ID
			params.put("projectId", CommonUtils.getCommonId("PJT", 4));
			// 후불의 경우 청구ID 관리
			if ("N".equals(CommonUtils.getString(params.get("payType")))) {
				// 고객번호 가져오기
				String custNo	= CommonUtils.getString(generalDao.selectGernalObject("project.selectCustNoForSaveProject", params));
				String regNo	= CommonUtils.getString(generalDao.selectGernalObject("project.selectRegNoForSaveProject", params));
				String billId	= CommonUtils.getString(params.get("billId"));
				
				// 청구아이디 신규 등록해야할 때
				if( "".equals(billId) ) {
					// 아무튼 api
				}
				
				// 리스트 재조회해서 가져오기
				Map<String, Object> getHeaderMap = new HashMap<String, Object>();
				
				List<Map<String, Object>> resultLists = (List<Map<String, Object>>) ((Map<String, Object>) apiInterface.get("/console/v1/ucube/bill/custList?mode=C&custNo=" + custNo, getHeaderMap).get("data")).get("resultList");
				
				Map<String, Object> billDataMap = new HashMap<>();
				for( int i = 0; i < resultLists.size(); i++ ) {
					if( billId.equals(resultLists.get(i).get("billAcntNo")) ) {
						billDataMap = resultLists.get(i);
					}
				}
				
				// CM_UCUBE 조회해서 없으면 인서트 처리
				Map<String, Object> ucubeBillInfoVo = new HashMap<>();
				
				ucubeBillInfoVo.put("billAcntNo",	billDataMap.get("billAcntNo"));
				ucubeBillInfoVo.put("billEmail",	billDataMap.get("billEmailAddr"));
				ucubeBillInfoVo.put("billKind",		"N");	// Y 이메일 N 우편
				ucubeBillInfoVo.put("billRegNo",	regNo);
				ucubeBillInfoVo.put("billZip",		billDataMap.get("custAddrZip"));
				ucubeBillInfoVo.put("billJuso",		billDataMap.get("custVilgAddr"));
				ucubeBillInfoVo.put("billJuso2",	billDataMap.get("custVilgAddr"));
				
				Map<String, Object> ucubePymInfoVO = new HashMap<>();
				// 납부자고객구분코드(II:개인, GC:일반법인, GD:상장법인, EX:개인사업자, GG:LG그룺 관계사, GH:유한회사, GJ:합자회사, GL:LGT사업자용, 
				// GM:장애인_국가유공단체, GN:LGT 인정법인, GO:합명회사, GP:공공기관, GR:로밍용, GS:주주사, GU:특수학교, GV:아동복지시설)
				ucubePymInfoVO.put("napCustKdCd",	"GC");
				ucubePymInfoVO.put("napCmpNm",		billDataMap.get("custNm"));
				ucubePymInfoVO.put("napJumin",		regNo);
				
				// UCUBE 서비스 등록 API
				Map<String, Object> ucubeServiceMap = new HashMap<>();
				
				ucubeServiceMap.put("ucubeBillInfoVo",	ucubeBillInfoVo);
				ucubeServiceMap.put("ucubePymInfoVO",	ucubePymInfoVO);
				
				ucubeServiceMap.put("custNo",		billDataMap.get("custNo"));
				ucubeServiceMap.put("logid",		billDataMap.get("logid"));
				ucubeServiceMap.put("indcId",		billDataMap.get("indcId"));
				ucubeServiceMap.put("mngrId",		billDataMap.get("mngrId"));
				ucubeServiceMap.put("rcsCode",		billDataMap.get("rcsCode"));
				ucubeServiceMap.put("rcsMeta",		billDataMap.get("rcsMeta"));
				ucubeServiceMap.put("rcsAdd1Meta",	billDataMap.get("napCustKdCd"));
				ucubeServiceMap.put("rcsAdd2Meta",	billDataMap.get("napCustKdCd"));
				ucubeServiceMap.put("rcsAdd3Meta",	billDataMap.get("napCustKdCd"));
				ucubeServiceMap.put("rcsAdd4Meta",	billDataMap.get("napCustKdCd"));
				
				// API 통신처리
				Map<String, Object> result =  apiInterface.post("/console/v1/ucube/service/join", ucubeServiceMap, null);
				String serviceId = "";
				
				if( "10000".equals(result.get("code")) ) {
					serviceId = CommonUtils.getString((Map<String, Object>)((Map<String, Object>)result.get("data")).get("serviceId"));
				} else if ( "500100".equals(result.get("code")) ) {
					String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
					throw new Exception(errMsg);
				} else {
					String errMsg = CommonUtils.getString(result.get("message"));
					throw new Exception(errMsg);
				}
				
				params.put("serviceId", serviceId);
				
				// 존재하는지 조회
				int cmUcubeDupllicate = generalDao.selectGernalCount("project.selectCmUcubeDupllicate", params);
				if( cmUcubeDupllicate == 0 ) {
					Map<String, Object> insertProjectCmUcubeSaveMap = new HashMap<>();
					insertProjectCmUcubeSaveMap.put("corpId", corpId);
					insertProjectCmUcubeSaveMap.put("billId", billId);
					insertProjectCmUcubeSaveMap.put("ucubeInfo", ucubeServiceMap);
					
					generalDao.insertGernal("project.insertProjectCmUcube", insertProjectCmUcubeSaveMap);
				}
				
			}

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
			// 프로젝트 멤버 추가 처리
			// 사용자 세션의 권한을 체크해서 OWNER 일경우 OWNER 유저 입력 처리 안하도록 처리 
			if( !"OWNER".equals(params.get("ROLE_CD")) ) {
				// 사용자 기본 멤버로 추가
				generalDao.insertGernal("project.insertProjectUser", params);
			}
			// -------------------------------------------------------------------------------------------------------------------------------------
			
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

	@SuppressWarnings({ "static-access" })
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
				String fileName = "";
				String pattern = "[\"!@#$%^&'.*]";
				String preFileName = commonService.getFileNameExt(uploadFile.getOriginalFilename(),0).replaceAll(pattern, "");
				String ext = commonService.getFileNameExt(uploadFile.getOriginalFilename(),1);
				fileName = preFileName+"."+ext;
				
				String fileId = commonService.uploadFile2(uploadFile, CommonUtils.getString(params.get("loginId")));
				Map<String, Object> fileMap = new HashMap<>();
				fileMap.put("fileId", fileId);
				
				String attachFilePath = CommonUtils.getString(generalDao.selectGernalObject("common.selectFilePathByFileId", fileMap));
				
				jsonInfoStr += "	\"fileName" + (i+1) + "\"		: \"" + fileName + "\",";
				if( i == (uploadFiles.size() - 1) ) {
					jsonInfoStr += "	\"filePath" + (i+1) + "\"		: \"" + attachFilePath + "\"";
				} else {
					jsonInfoStr += "	\"filePath" + (i+1) + "\"		: \"" + attachFilePath + "\",";
				}
			}
			
			jsonInfoStr += "}";

			params.put("attachFileList", jsonInfoStr);

			// 특수사업자관리 insert
			generalDao.insertGernal("project.insertSpecualBusi", params);
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public void saveRcsChatbotReqForApi(Map<String, Object> params) throws Exception {
		String brandId = CommonUtils.getString(params.get("brandId"));
		String sts = CommonUtils.getString(params.get("sts"));
		String tempYn = "N";
		Map<String, Object> certiFileRtnMap = new HashMap<String, Object>();
		String certiFilePath = "";

		if( "N".equals(tempYn) ) {
			// 가입증명 파일 업로드 처리
//			MultipartFile certiFile = (MultipartFile) params.get("certiFile");
//			
//			RestResult<Object> certiFileRtn = commonService.uploadFile(certiFile, CommonUtils.getString(params.get("loginId")));
//			
//			certiFileRtnMap = (Map<String, Object>) certiFileRtn.getData();
			
			String uploadDirPath = FileConfig.getFilePath(FileConfig.FileSvcType.LIBRARY);
			
			MultipartFile certiFile = (MultipartFile) params.get("certiFile");
			
			String certiFileSeq = commonService.uploadFile(certiFile, CommonUtils.getString(params.get("loginId")), uploadDirPath);
			
			Map<String, Object> certiFileMap = new HashMap<String, Object>();
			certiFileMap.put("fileId", certiFileSeq);
//			System.out.println("------------------------------------------------@ certiFileSeq : " + certiFileSeq);
			certiFilePath = CommonUtils.getString(generalDao.selectGernalObject("common.selectFilePathByFileId", certiFileMap));
//			System.out.println("------------------------------------------------@ certiFilePath : " + certiFilePath);
		}
		
		// 데이터 처리
		Map<String, Object> map = new HashMap<>();
		
		map.put("corpId",		params.get("corpId"));
		
		if( "N".equals(tempYn) ) {
			map.put("subNumCertificate",		certiFilePath);
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
			//System.out.println("----------------------------------------@@@ jsonParam : " + jsonParam);
			
			// 등록요청
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("brandId",	brandId);
			headerMap.put("Content-Type",	"application/json");
			
			// API 통신 처리
			Map<String, Object> result =  apiInterface.post("/console/v1/brand/" + brandId + "/chatbot", map, headerMap);
			
			//System.out.println("-----------------------------------------@@@ result : " + result);
			
			// 성공인지 실패인지 체크
			if( "10000".equals(result.get("code")) ) {
			} else if ( "500100".equals(result.get("code")) ) {
				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
				throw new Exception(errMsg);
			} else {
				String errMsg = CommonUtils.getString(result.get("message"));
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
			//System.out.println("----------------------------------------@@@ jsonParam : " + jsonParam);
			
			// 수정요청
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("brandId",	brandId);
			headerMap.put("chatbotId",	params.get("chatbotId"));
			headerMap.put("Content-Type",	"application/json");
			
			// API 통신 처리
			Map<String, Object> result =  apiInterface.put("/console/v1/brand/" + brandId + "/chatbot/" + params.get("chatbotId"), null, map, headerMap);
			
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
	
	// 발신번호관리 리스트 조회
	public RestResult<?> selectCallbackManageList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		if(params.containsKey("pageNo")
				&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize")
				&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_CALLBACK_MANAGE_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CALLBACK_MANAGE_LIST, params);
		rtn.setData(rtnList);

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
	
	//분배율 조회(분배서비스ID 없을 때)
	@SuppressWarnings("unchecked")
	public RestResult<?> selectBasicRatio(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_BASICDIS_RATIO, params);
		List<Object> rtnList2 = new ArrayList<Object>();
		HashMap<String,String> typehm = new HashMap<String,String>();
		HashMap<String,String> relayhm = new HashMap<String,String>();
		HashMap<String,String> vendorYnhm = new HashMap<String,String>();
		
		typehm.put("isExist", "F");
		
		for(int i=0; i<rtnList.size(); i++) {
			try {
				HashMap<String,String> hmrtn = (HashMap<String, String>) rtnList.get(i);
				
				String relayKey = hmrtn.get("relayChType");
				String relay = hmrtn.get("relay");
				String vendorYn = hmrtn.get("vendorYn");
				boolean isexist = relayhm.containsKey(relayKey+"relay");
				if(!isexist) {
					if(typehm.containsKey("chRelayType")) {
						String crt_tmp = typehm.get("chRelayType");
						crt_tmp += ","+relayKey;
						typehm.replace("chRelayType", crt_tmp);
					}else {
						typehm.put("chRelayType", relayKey);
					}
					relayhm.put(relayKey+"relay", relay);
					vendorYnhm.put(relayKey+"vendorYn", vendorYn);
				}else {
					String tmp = relayhm.get(relayKey+"relay");
					tmp += ","+relay;
					relayhm.replace(relayKey+"relay", tmp);
					vendorYnhm.put(relayKey+"vendorYn", vendorYn);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		rtnList2.add(typehm);
		rtnList2.add(relayhm);
		rtnList2.add(vendorYnhm);
		
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
		HashMap<String,String> vendorYnhm = new HashMap<String,String>();
		
		typehm.put("isExist", "T");
		
		HashMap<String,String> hmrtn = (HashMap<String, String>) rtnList.get(0);	//only one
		String rtnStr = hmrtn.get("chDistInfo");
		
		relayhm.put("USE_YN", hmrtn.get("useYn"));
		relayhm.put("SMART_CH_NAME", hmrtn.get("smartChName"));
		
		kong.unirest.json.JSONObject jo = new kong.unirest.json.JSONObject(rtnStr);
		Iterator it = jo.keys();
		ArrayList ar = new ArrayList();
	    while(it.hasNext())
	    {
	    	String jokey = it.next().toString();
	        ar.add(jokey); // 키 값 저장
	    }
	    typehm.put("chRelayType", "");
		for(int i=0; i<ar.size(); i++){
			String crtStr = typehm.get("chRelayType");
			String relayKey = (String) ar.get(i);
			crtStr += relayKey;
			
			kong.unirest.json.JSONArray ja = (kong.unirest.json.JSONArray) jo.get((String) ar.get(i));
			for(int j=0; j<ja.length(); j++) {
				kong.unirest.json.JSONObject jajo = (kong.unirest.json.JSONObject) ja.get(j);
				String relayCh = jajo.getString("relayCh");
				String distRatio = jajo.getString("distRatio");
				String vendorYn = jajo.getString("vendorYn");
				
				if(relayhm.containsKey(relayKey+"relay")) {
					String re_tmp = relayhm.get(relayKey+"relay");
					String dis_tmp = relayhm.get(relayKey+"ratio");
					String vendorYn_tmp = relayhm.get(relayKey+"vendorYn");
					re_tmp += ","+relayCh;
					dis_tmp += ","+distRatio;
					relayhm.replace(relayKey+"relay", re_tmp);
					relayhm.replace(relayKey+"ratio", dis_tmp);
					vendorYnhm.replace(relayKey+"vendorYn", vendorYn_tmp);
				}else {
					relayhm.put(relayKey+"relay", relayCh);
					relayhm.put(relayKey+"ratio", distRatio);
					vendorYnhm.put(relayKey+"vendorYn", vendorYn);
				}
			}

			if((i+1)<ar.size()) {
				crtStr += ",";
			}
			typehm.replace("chRelayType", crtStr);
		}

		rtnList2.add(typehm);
		rtnList2.add(relayhm);
		rtnList2.add(vendorYnhm);
		
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
			// redis 테이블 처리
			commonService.updateCmCmdForRedis(CmdTgt.SMART_CH);
			
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("저장에 실패하였습니다.");
		}

		return rtn;
	}

	@SuppressWarnings("unchecked")
	public RestResult<?> selectBillIdForApi(Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		
		// 추후 가져오는 쪽 작업해야하ㅣㅁ
		String custNo = "1008105145";
		
		Map<String, Object> getHeaderMap = new HashMap<String, Object>();
		
		Map<String, Object> result = apiInterface.get("/console/v1/ucube/bill/custList?mode=C&custNo=" + custNo, getHeaderMap);
		
		
		if( "10000".equals(result.get("code")) ) {
			result.put("billDataList", ((Map<String, Object>)result.get("data")).get("resultList"));
		} else {
			result.put("billDataList", null);
		}
		
		rtn.setData(result.get("data"));
		
		return rtn;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RestResult<?> selectDistDetail(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		//rtn.setData(generalDao.selectGernalList("project.selectDistDetail", params).get(0));
		
		List<Object> rtnList2 = new ArrayList<Object>();
		HashMap<String,String> typehm = new HashMap<String,String>();
		HashMap<String,String> relayhm = new HashMap<String,String>();
		
		HashMap<String,String> hmrtn = (HashMap<String, String>) generalDao.selectGernalList("project.selectDistDetail", params).get(0);
		
		
		String rtnStr = hmrtn.get("distInfo");
		
		relayhm.put("DIS_NAME", hmrtn.get("disName"));
		
		kong.unirest.json.JSONObject jo = new kong.unirest.json.JSONObject(rtnStr);
		Iterator it = jo.keys();
		ArrayList ar = new ArrayList();
	    while(it.hasNext())
	    {
	    	String jokey = it.next().toString();
	        ar.add(jokey); // 키 값 저장
	    }
	    typehm.put("chRelayType", "");
		for(int i=0; i<ar.size(); i++){
			String crtStr = typehm.get("chRelayType");
			String relayKey = (String) ar.get(i);
			crtStr += relayKey;
			
			kong.unirest.json.JSONArray ja = (kong.unirest.json.JSONArray) jo.get((String) ar.get(i));
			for(int j=0; j<ja.length(); j++) {
				kong.unirest.json.JSONObject jajo = (kong.unirest.json.JSONObject) ja.get(j);
				String relayCh = jajo.getString("cidGroup");
				String distRatio = jajo.getString("distRatio");
				
				if(relayhm.containsKey(relayKey+"cidGroup")) {
					String re_tmp = relayhm.get(relayKey+"cidGroup");
					String dis_tmp = relayhm.get(relayKey+"ratio");
					re_tmp += ","+relayCh;
					dis_tmp += ","+distRatio;
					relayhm.replace(relayKey+"cidGroup", re_tmp);
					relayhm.replace(relayKey+"ratio", dis_tmp);
				}else {
					relayhm.put(relayKey+"cidGroup", relayCh);
					relayhm.put(relayKey+"ratio", distRatio);
				}
			}

			if((i+1)<ar.size()) {
				crtStr += ",";
			}
			typehm.replace("chRelayType", crtStr);
		}
		
		rtnList2.add(hmrtn.get("distId"));
		rtnList2.add(typehm);
		rtnList2.add(relayhm);
		
		rtn.setData(rtnList2);
		
		return rtn;
	}
	
}
