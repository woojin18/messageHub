package kr.co.uplus.cm.rcsTemplateSend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.MultipartFileDTO;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.rcsTemplateSend.Service.RcsTemplateSendService;
import kr.co.uplus.cm.sendMessage.dto.RecvInfo;
import kr.co.uplus.cm.sendMessage.service.SendMessageService;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/uc/rcsTemplateSend")
public class RcsTemplateSendController {
	
	@Autowired
	private RcsTemplateSendService rcsTemplateSendSvc;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private SendMessageService sendMsgService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}
	
	@PostMapping("/rcsTemplatePopInit")
	public RestResult<?> rcsTemplatePopInit(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rtn = rcsTemplateSendSvc.rcsTemplatePopInit(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/rcsTemplateSearch")
	public RestResult<?> rcsTemplateSearch(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rtn = rcsTemplateSendSvc.rcsTemplateSearch(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/rcsTemplateDetail")
	public RestResult<?> rcsTemplateDetail(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rtn = rcsTemplateSendSvc.rcsTemplateDetail(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/selectCallbackList")
	public RestResult<?> selectCallbackList(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rtn = rcsTemplateSendSvc.selectCallbackList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/rcsMsgSave")
	public RestResult<?> rcsMsgSave(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rtn = rcsTemplateSendSvc.rcsMsgSave(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/selectRcsMsgList")
	public RestResult<?> selectRcsMsgList(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rtn = rcsTemplateSendSvc.selectRcsMsgList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error(e.getStackTrace());
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/deleteRcsTmpMsgbase")
	public RestResult<?> deleteRcsTmpMsgbase(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rcsTemplateSendSvc.deleteRcsTmpMsgbase(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	@PostMapping("/selectRcsMsgDetail")
	public RestResult<?> selectRcsMsgDetail(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rtn = rcsTemplateSendSvc.selectRcsMsgDetail(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			e.printStackTrace();
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/sendRcsData")
	public RestResult<?> sendRcsData(HttpServletRequest request,
			 @ModelAttribute MultipartFileDTO multipartFileDTO) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		Map<String, Object> params = new HashMap<String, Object>();
		List<RecvInfo> recvInfoLst = null;
		
		try {
			// params 세팅
			params = commonService.setUserInfo(multipartFileDTO.getUnescapeParams());
			
			Map<String, Object> data = (Map<String, Object>) params.get("data");
			/** 알림톡 수신자 리스트*/
			boolean real = (boolean) params.get("real");
			if(real) {
				recvInfoLst = sendMsgService.getRecvInfoLst(data, multipartFileDTO.getFile());
				if(recvInfoLst == null || recvInfoLst.size() == 0) {
					log.error("{}.sendRcsData Error : {}", this.getClass(), rtn.getMessage());
					rtn.setFail(Const.SendMsgErrorSet.INVALID_RECIPIENT_INFO);
					return rtn;
				}
			}
			
			// 예약 발송일 경우 금액 계산을 하지 않음. (DB insert 처리는 service에서 처리)
			String rsrvSendYn = CommonUtils.getString(data.get("rsrvSendYn"));
			if("N".equals(rsrvSendYn)) {
				// 금액 세팅
				String resultMsg = rcsTemplateSendSvc.setAccountSendMessage(params);
				rtn.setMessage(resultMsg);
			}

		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
			e.printStackTrace();
			log.error("{}.sendRcsData Error : {}", this.getClass(), e);
			
			// 금액계산에서 오류가 생기면 API 통신을하지 않고 로직 종료
			return rtn;
		}
		
		try {
			String templateRadioBtn = CommonUtils.getString(params.get("templateRadioBtn"));
			
			boolean real = (boolean) params.get("real"); // 테스트 발송일 경우 테스트 발신자 리스트로 치환
			if(!real) {
				Map<String, Object> data = (Map<String, Object>) params.get("data");
				ArrayList<Map<String, Object>> testRecvInfoLst = (ArrayList<Map<String, Object>>) data.get("testRecvInfoLst");
				data.put("recvInfoLst", testRecvInfoLst);
			}
			
			// 유형에 따라서 통신 세팅 모듈 case 처리
			if("des".equals(templateRadioBtn) || "cell".equals(templateRadioBtn)) {
				// 서술형, 스타일형
				rtn = rcsTemplateSendSvc.sendRcsDataTemplate(params, multipartFileDTO.getFile());
			} else if("text".equals(templateRadioBtn)) {
				// 미승인형
				rtn = rcsTemplateSendSvc.sendRcsDataNonTemplate(params, multipartFileDTO.getFile());
			} else if ("SS000000".equals(templateRadioBtn) || "SL000000".equals(templateRadioBtn) || "SMwThM00".equals(templateRadioBtn) || "SMwThT00".equals(templateRadioBtn)) {
				// SMS,LMS, 세로형
				rtn = rcsTemplateSendSvc.sendRcsDataFormat(params, multipartFileDTO.getFile());
				
			} else {
				// 캐러셀 형
				rtn = rcsTemplateSendSvc.sendRcsDataCarousel(params, multipartFileDTO.getFile());
			}
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
			e.printStackTrace();
			log.error("{}.sendRcsData Error : {}", this.getClass(), e);
			sendMsgService.sendMsgErrorNoti(Const.ApiWatchNotiMsg.SEND_RCS_FAIL);
		}

		return rtn;
	}
	
	/**
	 * 푸시 발송 수신자 엑셀업로드 템플릿 다운로드
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(path = "/excelDownSendRcsRecvTmplt")
	public ModelAndView excelDownSendRcsRecvTmplt(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {

		List<String> colLabels = new ArrayList<String>();
		colLabels.add("휴대폰 번호");
		if(params.containsKey("contsVarNms")) {
			List<String> contsVarNms = (ArrayList<String>)params.get("contsVarNms");
			for(String varNm : contsVarNms) {
				colLabels.add(varNm);
			}
		}

		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", "Template");
		map.put("colLabels", colLabels.toArray(new String[0]));
		map.put("colIds", new String[] {});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", new ArrayList<T>());
		sheetList.add(map);
		
		ModelAndView model = new ModelAndView("commonXlsxView");
		model.addObject("excelFileName", "rcsTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);
		
		return model;
	}
}
