package kr.co.uplus.cm.cash.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import kr.co.uplus.cm.cash.service.CashService;
import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;

@RestController
@RequestMapping("/projectApi/cash")

public class CashController {
	
	@Value("${toss.virAccUrl}") String virAccUrl;
	
	@Autowired
	private CashService cashService ;
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}
	
	@PostMapping("/selectVirAccUrl")
	public RestResult<?> selectVirAccUrl(
			HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		try {
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("virAccUrl", virAccUrl);
			rtn.setData(rtnMap);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다." + e.getMessage());
		}
		
		return rtn;
	}
	
	@PostMapping("/insertWebCashInfo")
	public RestResult<?> insertWebCashInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.insertWebCashInfo(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
//			rtn.setMessage("실패하였습니다.");
			rtn.setMessage("실패하였습니다." + e.getMessage());
		}
		
		return rtn;
	}
	
	// 카드결제 성공
	@GetMapping("/cardSuccess")
	public RedirectView cardSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="paymentKey", required = true) String paymentKey,
			@RequestParam(value="orderId"	, required = true) String orderId,
			@RequestParam(value="amount"	, required = true) int amount
			) {
		RestResult<Object> rtn = new RestResult<Object>();
		String message = "";
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("paymentKey", paymentKey);
			params.put("orderId", orderId);
			params.put("amount", amount);
			
			rtn = cashService.cardSuccess(params);
			
			if(!rtn.isSuccess()) {
				message = URLEncoder.encode(rtn.getMessage(), "UTF-8");
			}
		} catch(Exception e) {
			message = "0";
		}
		
		if(!message.equals("")) {
			return new RedirectView("/ac/cash" + "?pgMessage=" + message);
		}
		
		return new RedirectView("/ac/cash");
	}
	
	// 계좌이체 성공
	@GetMapping("/accTransSuccess")
	public RedirectView accTransSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="paymentKey", required = true) String paymentKey,
			@RequestParam(value="orderId"	, required = true) String orderId,
			@RequestParam(value="amount"	, required = true) int amount
			) {
		RestResult<Object> rtn = new RestResult<Object>();
		String message = "";
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("paymentKey", paymentKey);
			params.put("orderId", orderId);
			params.put("amount", amount);
			
			rtn = cashService.accTransSuccess(params);
			
			if(!rtn.isSuccess()) {
				message = URLEncoder.encode(rtn.getMessage(), "UTF-8");
			}
		} catch(Exception e) {
			message = "0";
		}
		
		if(!message.equals("")) {
			return new RedirectView("/ac/cash" + "?pgMessage=" + message);
		}
		
		return new RedirectView("/ac/cash");
	}
	
	
	// 가상계좌 성공
	@GetMapping("/virAccSuccess")
	public RedirectView virAccSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="paymentKey", required = true) String paymentKey,
			@RequestParam(value="orderId"	, required = true) String orderId,
			@RequestParam(value="amount"	, required = true) int amount
			) {
		RestResult<Object> rtn = new RestResult<Object>();
		String message = "";
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("paymentKey", paymentKey);
			params.put("orderId", orderId);
			params.put("amount", amount);
			
			rtn = cashService.virAccSuccess(params);
			
			if(!rtn.isSuccess()) {
				message = URLEncoder.encode(rtn.getMessage(), "UTF-8");
			}
		} catch(Exception e) {
			message = "0";
		}
		
		if(!message.equals("")) {
			return new RedirectView("/ac/cash" + "?pgMessage=" + message);
		}
		
		return new RedirectView("/ac/cash"+ "?orderId=" + orderId);
	}
	
	// 가상 계좌 입금확인 callback
	// 가상계좌 성공
	@RequestMapping("/virAccResult")
	public void virAccResult(
			HttpServletRequest request) {
		
		try {
			ServletInputStream inputStream = request.getInputStream();
			String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
			
			JSONParser parser = new JSONParser();
			Map<String, Object> paramMap = (Map<String, Object>) parser.parse(messageBody);
			
			cashService.virAccResult(paramMap);
		} catch (Exception e) {
//			log.
		}
	}
	
	// 결제 실패 (종류에 상관없이 동일하게 처리)
	@GetMapping("/paymentFail")
	public RedirectView paymentFail(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="code"		, required = true) String code,
			@RequestParam(value="message"	, required = true) String message
			) {
		
		return new RedirectView("/ac/cash" + "?pgMessage=" + message);
	}
	
	@PostMapping("/selectCashHist")
	public RestResult<?> selectCashHist(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.selectCashHist(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	@PostMapping("/selectVirAccStatus")
	public RestResult<?> selectVirAccStatus(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.selectVirAccStatus(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}

	@PostMapping("/selectVirAccDetail")
	public RestResult<?> selectVirAccDetail(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.selectVirAccDetail(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	@PostMapping("/selectCashBalance")
	public RestResult<?> selectCashBalance(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.selectCashBalance(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	

	@PostMapping("/selectUcubeInfo")
	public RestResult<?> selectUcubeInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.selectUcubeInfo(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	@PostMapping("/selectUcubePopInfo")
	public RestResult<?> selectUcubePopInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.selectUcubePopInfo(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	@PostMapping("/selectProjectInfo")
	public RestResult<?> selectProjectInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.selectProjectInfo(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	@PostMapping("/selectProjectSubBillCode")
	public RestResult<?> selectProjectSubBillCode(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.selectProjectSubBillCode(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	@PostMapping("/insertUbubeInfo")
	public RestResult<?> insertUbubeInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.insertUbubeInfo(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	@PostMapping("/updateProjectBillId")
	public RestResult<?> updateProjectBillId(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.updateProjectBillId(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	// 부서별 정산 저장
	@PostMapping("/saveProjectSubBillCode")
	public RestResult<?> saveProjectSubBillCode(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.saveProjectSubBillCode(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}
	
	@PostMapping("/deleteProjectSubBillCode")
	public RestResult<?> deleteProjectSubBillCode(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = cashService.deleteProjectSubBillCode(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
}
