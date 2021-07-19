package kr.co.uplus.cm.cash.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/projectApi/cash")

public class CashController {
	@Autowired
	private CashService cashService ;
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
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
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
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
			rtn.setSuccess(false);
			rtn.setMessage("0");
		}
		
		if(!message.equals("")) {
			return new RedirectView("http://localhost:3000/ac/cash" + "?pgMessage=" + message);
		}
		
		return new RedirectView("http://localhost:3000/ac/cash");
	}
	
	@GetMapping("/cardFail")
	public RedirectView cardFail(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="code"		, required = true) String code,
			@RequestParam(value="message"	, required = true) String message
			) {
		
		return new RedirectView("http://localhost:3000/ac/cash" + "?pgMessage=" + message);
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
			rtn.setMessage("실패하였습니다.");
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
