package kr.co.uplus.cm.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.project.service.ChannelService;
import kr.co.uplus.cm.utils.CommonUtils;

@RestController
@RequestMapping("/projectApi/channel")
public class ChannelController {

	@Autowired
	ChannelService channelService;

	@PostMapping("/selectRcsBrandList")
	public RestResult<?> selectRcsBrandList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// commonutils에 집어 넣을것
		int paging = Integer.parseInt(CommonUtils.nvl(CommonUtils.getString(params.get("paging")), "1"));
		int rows = Integer.parseInt(CommonUtils.nvl(CommonUtils.getString(params.get("rows")), "100"));

		int rowsFront = (paging - 1) * rows;

		params.put("rowsFront", rowsFront);
		params.put("rowsEnd", rows);

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

		// commonutils에 집어 넣을것
//		int paging = Integer.parseInt(CommonUtils.getString(params.get("paging")));
//		int rows = Integer.parseInt(CommonUtils.getString(params.get("rows")));
//
//		int rowsFront = (paging - 1) * rows;
//
//		params.put("rowsFront", rowsFront);
//		params.put("rowsEnd", rows);

		return channelService.selectRcsRegTmpltList(params);
	}

	/**
	 * 등록 발신번호 상세 조회
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/selectRcsCallbackList")
	public RestResult<?> selectRcsCallbackList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// commonutils에 집어 넣을것
//		int paging = Integer.parseInt(CommonUtils.getString(params.get("paging")));
//		int rows = Integer.parseInt(CommonUtils.getString(params.get("rows")));
//
//		int rowsFront = (paging - 1) * rows;
//
//		params.put("rowsFront", rowsFront);
//		params.put("rowsEnd", rows);

		return channelService.selectRcsCallbackList(params);
	}
	
	/**
	 * API KEY 중복 조회
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/checkApiKey")
	public RestResult<?> checkApiKey(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return channelService.checkApiKey(params);
	}
	
	/**
	 * RCS 브랜드 등록요청
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
			@RequestParam String apiSecretKey,
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
			@RequestParam String mainMdn,
			
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
			@RequestParam String ticket,
			@RequestParam String ticketWeblink,
			@RequestParam String moreInfo,
			@RequestParam String moreInfoWeblink,
			
			@RequestParam(required = false) MultipartFile profileImgFile,
			@RequestParam(required = false) MultipartFile bgImgFile,
			@RequestParam(required = false) MultipartFile certiFile,
			
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
		params.put("apiSecretKey",		apiSecretKey);
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
		params.put("mainMdn",			mainMdn);
		
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
		params.put("ticket",			ticket);
		params.put("ticketWeblink",		ticketWeblink);
		params.put("moreInfo",			moreInfo);
		params.put("moreInfoWeblink",	moreInfoWeblink);
		
		
		params.put("profileImgFile",	profileImgFile);
		params.put("bgImgFile",			bgImgFile);
		params.put("certiFile",			certiFile);
		
		params.put("chatbots",			chatbots);
		
		System.out.println("----------------------------------------params : " + params);
		
//		try {
			channelService.saveRcsBrandReqForApi(params);
			rtn.setSuccess(true);
//		} catch (Exception e) {
//			rtn.setSuccess(false);
//			rtn.setMessage(e.getMessage());
//		}
		
		return rtn;
	}
}
