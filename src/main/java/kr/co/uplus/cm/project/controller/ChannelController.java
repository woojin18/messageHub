package kr.co.uplus.cm.project.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.ResultCode;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.model.AuthUser;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.project.service.ChannelService;
import kr.co.uplus.cm.utils.CommonUtils;

@RestController
@RequestMapping("/projectApi/channel")
public class ChannelController {

	@Autowired
	ChannelService channelService;

	@Autowired
	CommonService commonService;
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

	/**
	 * RCS 브랜드 리스트 조회
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/selectRcsBrandList")
	public RestResult<?> selectRcsBrandList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return channelService.selectRcsBrandList(params);
	}

	/**
	 * 등록 템플릿 상세 조회
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/selectRcsRegTmpltList")
	public RestResult<?> selectRcsRegTmpltList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return channelService.selectRcsRegTmpltList(params);
	}

	/**
	 * 등록 발신번호 상세 조회
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/selectRcsCallbackList")
	public RestResult<?> selectRcsCallbackList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return channelService.selectRcsCallbackList(params);
	}

	/**
	 * API KEY 중복 조회 및 상세조회
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/checkApiKey")
	public RestResult<?> checkApiKey(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = (RestResult<Object>) channelService.checkApiKey(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
	}

	/**
	 * RCS 브랜드 등록요청
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/saveRcsBrandReqForApi")
	public RestResult<?> saveRcsBrandReqForApi(
			@RequestParam String sts,
			@RequestParam String loginId,
			@RequestParam String corpId,
			@RequestParam String projectId,
			@RequestParam String brandId,
			@RequestParam String apiKey,
			@RequestParam String apiSecret,
			@RequestParam String name,
			@RequestParam String description,
			@RequestParam String tel,
			@RequestParam String categoryId,
			@RequestParam String subCategoryId,
			@RequestParam String categoryOpt,
			@RequestParam String zipCode,
			@RequestParam String roadAddress,
			@RequestParam String detailAddress,
			@RequestParam String email,
			@RequestParam String email2,
			@RequestParam String webSiteUrl,
			@RequestParam String mainMdn,
			@RequestParam String mainTitle,
			
			@RequestParam String call,
			@RequestParam String callWeblink,
			@RequestParam String web,
			@RequestParam String webWeblink,
			@RequestParam String store,
			@RequestParam String storeWeblink,
			@RequestParam String order,
			@RequestParam String orderWeblink,
			@RequestParam String buy,
			@RequestParam String buyWeblink,
			@RequestParam String tickets,
			@RequestParam String ticketsWeblink,
			@RequestParam String moreinfo,
			@RequestParam String moreinfoWeblink,
			
			@RequestParam(required = false) MultipartFile profileImgFile,
			@RequestParam(required = false) MultipartFile bgImgFile,
			@RequestParam(required = false) MultipartFile certiFile,
			
			@RequestParam(required = false) String bgImgFilePath,
			@RequestParam(required = false) String profileImgFilePath,
			@RequestParam(required = false) String certiFilePath,
			
			@RequestParam(required = false) String chatbots,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		RestResult<Object> rtn = new RestResult<Object>();
		rtn.setSuccess(true);
		
		// 파라미터 정리
		params.put("sts",				sts);
		params.put("loginId",			loginId);
		params.put("corpId",			corpId);
		params.put("projectId",			projectId);
		params.put("brandId",			brandId);
		params.put("apiKey",			apiKey);
		params.put("apiSecret",			apiSecret);
		params.put("name",				name);
		params.put("description",		description);
		params.put("tel",				tel);
		params.put("categoryId",		categoryId);
		params.put("subCategoryId",		subCategoryId);
		params.put("categoryOpt",		categoryOpt);
		params.put("zipCode",			zipCode);
		params.put("roadAddress",		roadAddress);
		params.put("detailAddress",		detailAddress);
		params.put("email",				email);
		params.put("email2",			email2);
		params.put("webSiteUrl",		webSiteUrl);
		params.put("mainMdn",			mainMdn);
		params.put("mainTitle",			mainTitle);
		
		
		params.put("call",				call);
		params.put("callWeblink",		callWeblink);
		params.put("web",				web);
		params.put("webWeblink",		webWeblink);
		params.put("store",				store);
		params.put("storeWeblink",		storeWeblink);
		params.put("order",				order);
		params.put("orderWeblink",		orderWeblink);
		params.put("buy",				buy);
		params.put("buyWeblink",		buyWeblink);
		params.put("tickets",			tickets);
		params.put("ticketsWeblink",	ticketsWeblink);
		params.put("moreinfo",			moreinfo);
		params.put("moreinfoWeblink",	moreinfoWeblink);
		
		
		params.put("profileImgFile",	profileImgFile);
		params.put("bgImgFile",			bgImgFile);
		params.put("certiFile",			certiFile);
		
		// 임시저장용
		params.put("bgImgFilePath",	bgImgFilePath);
		params.put("profileImgFilePath",	profileImgFilePath);
		params.put("certiFilePath",	certiFilePath);
		
		
		params.put("chatbots",			chatbots);
		
		try {
			channelService.saveRcsBrandReqForApi(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			if("".equals(e.getMessage())) {
				rtn.setMessage("저장에 실패했습니다.");
			} else {
				rtn.setMessage(e.getMessage());
			}
		}
		
		return rtn;
	}
	
	// RCS 브랜드 삭제 요청
	@PostMapping("/deleteRcsBrandForApi")
	public RestResult<?> deleteRcsBrandForApi(
			@RequestBody Map<String, Object> params,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		rtn.setSuccess(true);
		
		try {
			channelService.deleteRcsBrandForApi(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}
	
	/**
	 * PUSH 리스트 조회
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/selectPushManageList")
	public RestResult<?> selectPushManageList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return channelService.selectPushManageList(params);
	}
	
	@PostMapping("/savePushManage")
	public RestResult<?> savePushManage(
			@RequestParam String sts,
			@RequestParam String userId,
			@RequestParam String corpId,
			@RequestParam String projectId,
			@RequestParam String saveProjectId,
			@RequestParam String appId,
			@RequestParam String appNm,
			@RequestParam String fcmPackageName,
			@RequestParam String fcmServerKey,
			@RequestParam String senderId,
			@RequestParam(required = false) MultipartFile apnsCetification,
			@RequestParam String iosAppId,
			@RequestParam String teamKey,
			@RequestParam String keyId,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		RestResult<Object> rtn = new RestResult<Object>();
		rtn.setSuccess(true);
		
		// 파라미터 정리
		params.put("sts",				sts);
		params.put("userId",			userId);
		params.put("corpId",			corpId);
		params.put("projectId",			projectId);
		params.put("saveProjectId",		saveProjectId);
		
		params.put("appId",				appId);
		params.put("appNm",				appNm);
		params.put("fcmPackageName",	fcmPackageName);
		params.put("fcmServerKey",		fcmServerKey);
		params.put("senderId",			senderId);
		params.put("apnsCetification",	apnsCetification);
		params.put("iosAppId",			iosAppId);
		params.put("teamKey",			teamKey);
		params.put("keyId",				keyId);
		
		try {
			channelService.savePushManage(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}
	
	@PostMapping("/deletePushManage")
	public RestResult<?> savePushManage(
			@RequestBody Map<String, Object> params,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		rtn.setSuccess(true);
		
		try {
			channelService.deletePushManage(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}
	
	/**
	 * RCS 브랜드 메시지 포멧정보 리스트 조회
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/selectRcsBrandMsgBaseList")
	public RestResult<?> selectRcsBrandMsgBaseList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return channelService.selectRcsBrandMsgBaseList(params);
	}
	
	/**
	 * MO 수신번호 현황 리스트 조회
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/selectMoCallbackList")
	public RestResult<?> selectMoCallbackList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return channelService.selectMoCallbackList(params);
	}
	
	/**
	 * MO 수신번호 저장
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/saveMoCallback")
	public RestResult<?> saveMoCallback(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			channelService.saveMoCallback(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
	}
	// 카카오톡 API KEY LIST 불러오기
	@PostMapping("/getApiKeyListForKko")
	public RestResult<?> getApiKeyListForKko(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return channelService.getApiKeyListForKko(params);
	}
	// 카카오톡 카테고리 불러오기
	@PostMapping("/getKkoCategory")
	public RestResult<?> getKkoCategory(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return channelService.getKkoCategory(params);
	}
	// 알림톡 토큰 요청
	@PostMapping("/getSenderKeyToken")
	public RestResult<?> getSenderKeyToken(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			channelService.getSenderKeyToken(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
	}
	
	
	@PostMapping("/selectKkoCh")
	public RestResult<?> selectKkoCh(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return channelService.selectKkoCh(params);
	}
	
	@PostMapping("/selectKkoChGroup")
	public RestResult<?> selectKkoChGroup(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return channelService.selectKkoChGroup(params);
	}
	
	@PostMapping("/saveKkoChForApi")
	public RestResult<?> saveKkoChForApi(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			channelService.saveKkoChForApi(params);
		} catch (Exception e) {
			if (params.get("errData") != null) {
				rtn.setSuccess(true);
				rtn.setCode(ResultCode.SS_LOCK);
				rtn.setData(params.get("errData"));
			} else {
				rtn.setSuccess(false);
				rtn.setMessage(e.getMessage());
			}
		}
		return rtn;
	}
	
	@PostMapping("/chkEqualKakaoChannel")
	public RestResult<?> chkEqualKakaoChannel(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			channelService.chkEqualKakaoChannel(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
	}

	@PostMapping("/saveKkoChGroupForApi")
	public RestResult<?> saveKkoChGroupForApi(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			channelService.saveKkoChGroupForApi(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
	}
	
	// 카카오톡 발신 프로필 채널 휴면해제
	@PostMapping("/saveKkoChRecover")
	public RestResult<?> saveKkoChRecover(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			channelService.saveKkoChRecover(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
	}
	
	// 카카오톡 발신 프로필 채널 삭제
	@PostMapping("/delKkoCh")
	public RestResult<?> delKkoCh(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<?> rtn = new RestResult<Object>(true);
		try {
			rtn = channelService.delKkoCh(params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rtn;
	}
	
	// rcs 브랜드 리스트에서 apikey 있는지 확인
	@PostMapping("/findApiKeyFromProject")
	public RestResult<?> findApiKeyFromProject(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return channelService.findApiKeyFromProject(params);
	}
	
	@PostMapping("/checkWebhookUrl")
	public RestResult<?> checkWebhookUrl(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		try {
			URL url = new URL(CommonUtils.getString(params.get("webhookUrl")));
			URLConnection con = url.openConnection();
			HttpURLConnection exitCode = (HttpURLConnection) con;
			
			if(exitCode.getResponseCode() == 200) {
				// url 존재
			} else {
				//exitCode.getResponseCode() == 404
				rtn.setSuccess(false);
				rtn.setMessage("해당 URL를 찾을 수 없습니다.");
			}
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("해당 URL를 찾을 수 없습니다.\n"+e.getMessage());
		}
		return rtn;
	}
	
	// RCS 브랜드 연결 리스트
	@PostMapping("/getRcsBrandConList")
	public RestResult<?> getRcsBrandConList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<Object> rtn = new RestResult<Object>(true);
			
		try {
			rtn = (RestResult<Object>) channelService.getRcsBrandConList(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(rtn.getData());
		return rtn;
	}           
	
	// RCS 브랜드 연결 
	@PostMapping("/saveBrandCon")
	public RestResult<?> saveBrandCon(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<?> rtn = new RestResult<Object>(true);
			
		try {
			rtn = channelService.saveProjectCallNum(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rtn;
	}  
	      
	// RCS 브랜드 연결 해제 
	@PostMapping("/deleteBrandDiscon")
	public RestResult<?> deleteBrandDiscon(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<?> rtn = new RestResult<Object>(true);
		try {
			rtn = channelService.deleteBrandDiscon(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rtn;
	}  
	
	
	//연결 프로젝트 리스트
	@PostMapping("/selectConProjectList")
	public RestResult<?> selectConProjectList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return channelService.selectConProjectList(params);
	}
	
}
