package kr.co.uplus.cm.rcsTemplateSend.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.rcsTemplateSend.Service.RcsTemplateSendService;
import kr.co.uplus.cm.utils.CommonUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/uc/rcsTemplateSend")
public class RcsTemplateSendController {
	
	@Autowired
	private RcsTemplateSendService rcsTemplateSendSvc;
	
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
	public RestResult<?> sendRcsData(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			// 예약 발송일 경우 금액 계산을 하지 않음. (DB insert 처리는 service에서 처리)
			Map<String, Object> data = (Map<String, Object>) params.get("data");
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
			log.error("{} Error : {}", this.getClass(), e);
			
			// 금액계산에서 오류가 생기면 API 통신을하지 않고 로직 종료
			return rtn;
		}
		
		try {
			String templateRadioBtn = CommonUtils.getString(params.get("templateRadioBtn"));
			
			boolean real = (boolean) params.get("real"); // 테스트 발송일 경우 테스트 발신자 리스트로 치환
			if(!real) {
				Map<String, Object> data = (Map<String, Object>) params.get("data");
				String testRecvInfoLst = CommonUtils.getString(data.get("testRecvInfoLst"));
				data.put("RecvInfoLst", testRecvInfoLst);
			}
			
			// 유형에 따라서 통신 세팅 모듈 case 처리
			if("des".equals(templateRadioBtn) || "cell".equals(templateRadioBtn)) {
				// 서술형, 스타일형
				rtn = rcsTemplateSendSvc.sendRcsDataTemplate(params);
			} else if("text".equals(templateRadioBtn)) {
				// 미승인형
				rtn = rcsTemplateSendSvc.sendRcsDataNonTemplate(params);
			} else if ("SS000000".equals(templateRadioBtn) || "SL000000".equals(templateRadioBtn) || "SMwThM00".equals(templateRadioBtn) || "SMwThT00".equals(templateRadioBtn)) {
				// SMS,LMS, 세로형
				rtn = rcsTemplateSendSvc.sendRcsDataFormat(params);
				
			} else {
				// 캐러셀 형
				rtn = rcsTemplateSendSvc.sendRcsDataCarousel(params);
			}
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
			e.printStackTrace();
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
}
