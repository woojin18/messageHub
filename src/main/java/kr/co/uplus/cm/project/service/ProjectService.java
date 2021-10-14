package kr.co.uplus.cm.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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


	@Autowired
	private BaseInfoService baseInfoService; 

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
			
			if( "Y".equals(smsmmsYn) )	useChStr += "SMS/MMS,";
			if( "Y".equals(rcsYn) )		useChStr += "RCS,";
			if( "Y".equals(kakaoYn) )	useChStr += "KAKAO,";
			if( "Y".equals(pushYn) )	useChStr += "PUSH,";
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
	public void saveProject(HttpServletRequest request, Map<String, Object> params) throws Exception {
		// 저장 상태값
		String sts = CommonUtils.getString(params.get("sts"));
		// 사용자 세션
		Map<String, Object> userMap = (Map<String, Object>) params.get("userDto");
		params.put("userId", userMap.get("userId"));
		String corpId = CommonUtils.getString(params.get("corpId"));

		if ("C".equals(sts)) {
			// 프로젝트 ID
			String projectIdStr = CommonUtils.getCommonId("PJT", 4);
			params.put("projectId", projectIdStr);
			// 후불의 경우 청구ID 관리
			if ("POST".equals(CommonUtils.getString(params.get("payType")))) {
				// 고객번호 가져오기
				String custNo	= CommonUtils.getString(generalDao.selectGernalObject("project.selectCustNoForSaveProject", params));
				String regNo	= CommonUtils.getString(generalDao.selectGernalObject("project.selectRegNoForSaveProject", params));
				String salesId	= CommonUtils.getString(generalDao.selectGernalObject("project.selectSalesIdForSaveProject", params));
				
				Map<String, Object> apiCustomerInfo = (Map<String, Object>) apiInterface.get("/console/v1/ucube/customer/" + custNo, null).get("data");
				
				Map<String, Object> joinMap = new HashMap<>();
				
				joinMap.put("custNo",		custNo);
				joinMap.put("logid",		projectIdStr);
				joinMap.put("indcId",		salesId);
				joinMap.put("mngrId",		salesId);
				
				// 유큐브 서비스 등록 
				Map<String, Object> ucubeBillInfoVo = new HashMap<>();
				String billEmail = CommonUtils.getString(apiCustomerInfo.get("emailAddr"));
				if( "".equals(billEmail) ) {
					billEmail = CommonUtils.getString(generalDao.selectGernalObject("project.selectEmailForSaveProject", params));
				}
				
				ucubeBillInfoVo.put("billAcntNo",	null);										// 청구계정 번호 (등록시엔 공백으로 입력)
				ucubeBillInfoVo.put("billEmail",	billEmail);									// 청구이메일주소 ?
				ucubeBillInfoVo.put("billKind",		"N");										// 청구서유형코드 Y 이메일 N 우편
				ucubeBillInfoVo.put("billRegNo",	regNo);										// 청구사업자번호
				ucubeBillInfoVo.put("billZip",		apiCustomerInfo.get("custAddrZip"));		// 청구우편번호
				ucubeBillInfoVo.put("billJuso",		apiCustomerInfo.get("custVilgAbvAddr"));	// 청구주소
				ucubeBillInfoVo.put("billJuso2",	apiCustomerInfo.get("custVilgBlwAddr"));	// 청구주소상세
				
				Map<String, Object> ucubePymInfoVO = new HashMap<>();
				
				ucubePymInfoVO.put("napCustKdCd",	apiCustomerInfo.get("custKdCd"));			// 납부자고객구분코드
				ucubePymInfoVO.put("napCmpNm",		apiCustomerInfo.get("custNm"));				// 납부자명 ???
				ucubePymInfoVO.put("napJumin",		regNo);										// 생년월일/사업자번호 ????
				
				// 서비스 정보
				Map<String, Object> serviceInfo = new HashMap<>();
				Map<String, Object> ownerMap	= (Map<String, Object>) generalDao.selectGernalObject("project.selectOwnerForApi", params);
				
				serviceInfo.put("cmpNm",			apiCustomerInfo.get("bizCompNm"));			// 가입자(상호)명 
				serviceInfo.put("ceoNm",			apiCustomerInfo.get("custNm"));				// 대표자 성명 (필수)
				serviceInfo.put("damNm",			ownerMap.get("damNm"));						// 담당자명
				serviceInfo.put("deptNm",			ownerMap.get("deptNm"));					// 담당자부서명
				serviceInfo.put("phoneNum",			ownerMap.get("phoneNum"));					// 담당자 전화번호
				serviceInfo.put("cellNum",			ownerMap.get("cellNum"));					// 담당자 핸드폰번호
				serviceInfo.put("faxNum",			ownerMap.get("faxNum"));					// 담당자FAX번호
				serviceInfo.put("damEmail",			ownerMap.get("damEmail"));					// 담당자 Email
				
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
				joinMap.put("cmPriceInfo",	cmPriceInfoMap);
				
				kong.unirest.json.JSONObject json2222 =  new kong.unirest.json.JSONObject(joinMap);
				
				//System.out.println("-------------------------------------------!!!!!!!!! requset body json : " + json2222);
				
				// API 통신처리
				Map<String, Object> result =  apiInterface.post("/console/v1/ucube/service/join/cm", joinMap, null);
				
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
				
				Map<String, Object> resultData = (Map<String, Object>) result.get("data");
				List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultData.get("resultList");
				
				if( "10000".equals(result.get("code")) ) {
					serviceId = CommonUtils.getString(resultList.get(0).get("entrNo"));
					billId = CommonUtils.getString(resultList.get(0).get("billAcntNo"));
					kong.unirest.json.JSONObject ucubeInfo =  new kong.unirest.json.JSONObject(resultData);
					
					params.put("serviceId", serviceId);
					params.put("billId", billId);
					params.put("ucubeInfo", ucubeInfo.toString());
					
					// 유큐브 정보 인서트
					generalDao.insertGernal("project.insertCmUcube", params);
					
				} else if ( "500100".equals(result.get("code")) ) {
					String errMsg = CommonUtils.getString(result.get("message")) + " : " + CommonUtils.getString(result.get("data"));
					throw new Exception(errMsg);
				} else {
//					 {code=21400, message=유큐브 연동 오류, data={serviceId=SB1099, resultCode=icm.err.074
//							, resultMsg=입력한 유치자( juoh )에 해당하는 대리점 정보가 존재하지 않습니다., resultList=null}}
					String errMsg = CommonUtils.getString(result.get("message"));
					String msg = CommonUtils.getString(((Map<String, Object>) result.get("data")).get("resultMsg"));
					if( !"".equals(msg) ) {
						throw new Exception(errMsg + " : " + msg);
					} else {
						throw new Exception(errMsg);
					}
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
			// 사용자 세션의 권한을 체크해서 OWNER가 아닐 경우 OWNER 사용자는 추가되도록 처리 
			
			if( !"OWNER".equals(request.getHeader("roleCd"))) {
				// OWNER 사용자 기본 멤버로 추가
				List<Object> ownerList = generalDao.selectGernalList("project.selectProjectOwnerUser", params);
				
				for(int j = 0; j < ownerList.size(); j++) {
					Map<String, Object> ownerMap = (Map<String, Object>) ownerList.get(j);
					ownerMap.put("projectId", params.get("projectId"));
					ownerMap.put("corpId", params.get("corpId"));
					ownerMap.put("userId", ownerMap.get("userId"));
					generalDao.insertGernal("project.insertProjectUser", ownerMap);
				}
			} 
			generalDao.insertGernal("project.insertProjectUser", params);
			// -------------------------------------------------------------------------------------------------------------------------------------
			
			
			// 프로젝트 신규 등록시 '웹 APIKEY 기본' APIKEY 키를 자동생성한다. 2021-10-14
			Map<String, Object> apikey = new HashMap<String, Object>();
			apikey.put("newProjectId", projectIdStr);
//			apikey.put("apiKey", "");
//			apikey.put("apiPwdConfirm", "");
			apikey.put("corpId", params.get("corpId"));
			apikey.put("apiPwd", "");
			apikey.put("ipChkYn", "N");
			apikey.put("daySenderChkYn", "N");
			apikey.put("monSenderChkYn", "N");
			apikey.put("apiKeyName", "웹 APIKEY 기본");
			apikey.put("status", "USE");
			apikey.put("rptYn", "Y");
			apikey.put("dupChkYn", "Y");
			apikey.put("webSenderYn", "Y");
//			apikey.put("daySenderLimitAmount", "");
//			apikey.put("monSenderLimitAmount", "");
			apikey.put("saveStatus", "R");
			baseInfoService.saveApiKey(apikey);
			
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
			// 테이블 이력조회 ->> CM_MSG, CM_WEB_MSG에서 API KEY 숫자로 변경
			int projectUseCnt = generalDao.selectGernalList("baseInfo.selectApiKeyList", params).size();
			

			if (projectUseCnt == 0) {
				generalDao.deleteGernal("project.deleteProject", params);
				generalDao.deleteGernal("project.deleteProjectUser", params);
				generalDao.deleteGernal("project.deleteProjectApiKey", params);
			} else {
				throw new Exception("사용된 이력이 존재하여 해당 프로젝트는 삭제할 수 없습니다.");
			}

		}
		

		// redis 테이블 처리
		commonService.updateCmCmdForRedis("CM_PROJECT");

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
			String uploadDirPath = FileConfig.getFilePath(FileConfig.FileSvcType.LIBRARY);
			
			for (int i = 0; i < uploadFiles.size(); i++) {
				
				MultipartFile uploadFile = uploadFiles.get(i);
				String fileName = "";
				String pattern = "[\"!@#$%^&'.*]";
				String preFileName = commonService.getFileNameExt(uploadFile.getOriginalFilename(),0).replaceAll(pattern, "");
				String ext = commonService.getFileNameExt(uploadFile.getOriginalFilename(),1);
				fileName = preFileName+"."+ext;
				
				Map<String, Object> fileMap = commonService.uploadFile2(uploadFile, CommonUtils.getString(params.get("loginId")), uploadDirPath);
				fileMap.put("fileId", fileMap.get("rtnSeq"));
				
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
			String apiKey = CommonUtils.getString(generalDao.selectGernalObject("channel.selectApikeyForApi",params));
			if( "".equals(apiKey) ) { throw new Exception("API Key를 등록 후, 진행 가능합니다. 프로젝트 기본정보 탭에서 API Key를 등록해주세요."); }
			headerMap.put("apiKey",		apiKey);
			headerMap.put("brandId",	brandId);
			headerMap.put("Content-Type",	"application/json");
			
			// API 통신 처리
			Map<String, Object> result =  apiInterface.post("/console/v1/rcs/brand/" + brandId + "/chatbot", map, headerMap);
			
			//System.out.println("-----------------------------------------@@@ result : " + result);
			
			// 성공인지 실패인지 체크
			if( "10000".equals(result.get("code")) ) {
			} else if ( "500100".equals(result.get("code")) ) {
				String errMsg = CommonUtils.getString(result.get("message"));
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
			String apiKey = CommonUtils.getString(generalDao.selectGernalObject("channel.selectApikeyForApi",params));
			if( "".equals(apiKey) ) { throw new Exception("API Key를 등록 후, 진행 가능합니다. 프로젝트 기본정보 탭에서 API Key를 등록해주세요."); }
			headerMap.put("apiKey",		apiKey);
			headerMap.put("brandId",	brandId);
			headerMap.put("chatbotId",	params.get("chatbotId"));
			headerMap.put("Content-Type",	"application/json");
			
			// API 통신 처리
			Map<String, Object> result =  apiInterface.put("/console/v1/rcs/brand/" + brandId + "/chatbot/" + params.get("chatbotId"), null, map, headerMap);
			
			// 성공인지 실패인지 체크
			if( "10000".equals(result.get("code")) ) {
			} else if ( "500100".equals(result.get("code")) ) {
				String errMsg = CommonUtils.getString(result.get("message"));
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
		String apiKey = CommonUtils.getString(generalDao.selectGernalObject("channel.selectApikeyForApi",params));
		if( "".equals(apiKey) ) { throw new Exception("API Key를 등록 후, 진행 가능합니다. 프로젝트 기본정보 탭에서 API Key를 등록해주세요."); }
		headerMap.put("apiKey",		apiKey);
		headerMap.put("brandId",	brandId);
		headerMap.put("chatbotId",	chatbotId);
		
		// API 통신 처리
		Map<String, Object> result =  apiInterface.delete("/console/v1/rcs/brand/" + brandId + "/chatbot/" + chatbotId, null, apiMap, headerMap);
		
		// 성공인지 실패인지 체크
		if( "10000".equals(result.get("code")) ) {
		} else if ( "500100".equals(result.get("code")) ) {
			String errMsg = CommonUtils.getString(result.get("message"));
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
					
					// redis 테이블 처리
					commonService.updateCmCmdForRedis(CmdTgt.PROJECT);
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

	public RestResult<?> selectCorpDistId(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		String distId = CommonUtils.getString(generalDao.selectGernalObject("project.selectCorpDistId", params));
		rtn.setData(distId);
		return rtn;
	}
	
}
