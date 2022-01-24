package kr.co.uplus.cm.cash.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import kr.co.uplus.cm.cash.service.CashService;
import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.DateUtil;
import kr.co.uplus.cm.utils.GeneralDao;

@RestController
@RequestMapping("/projectApi/cash")

public class CashController {

	@Autowired
	private GeneralDao generalDao;
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
//			rtn.setMessage("실패하였습니다.");
			rtn.setMessage("실패하였습니다." + e.getMessage());
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
			message = "0";
		}
		
		if(!message.equals("")) {
			return new RedirectView("/ac/cash" + "?pgMessage=" + message);
		}
		
		return new RedirectView("/ac/cash");
	}
	
	@GetMapping("/cardFail")
	public RedirectView cardFail(
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

	// 정산 조회
	@PostMapping("/selectCalList")
	public RestResult<?> selectCalList(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount("cash.selectCalListCnt", params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList("cash.selectCalList", params);
        rtn.setData(rtnList);

		return rtn;
	}

	// 부서 조회
	@PostMapping("/selectDeptList")
	public RestResult<?> selectDeptList(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
        List<Object> rtnList = generalDao.selectGernalList("cash.selectDeptList", params);
        rtn.setData(rtnList);

		return rtn;
	}

	// 청구계정 조회
	@PostMapping("/selectBillList")
	public RestResult<?> selectBillList(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
        List<Object> rtnList = generalDao.selectGernalList("cash.selectBillList", params);
        rtn.setData(rtnList);

		return rtn;
	}
	
	// 부서 저장
	@PostMapping("/saveDept")
	public RestResult<?> saveDept(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		cashService.saveDept(params);
		return rtn;
	}
	
	// 부서 삭제
	@PostMapping("/delDept")
	public RestResult<?> delDept(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		cashService.delDept(params);
		return rtn;
	}
	

	/**
	 * 정산금액 엑셀다운로드
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(path = "/excelDownloadCalList")
	public ModelAndView excelDownloadCalList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {
		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		params.put("svcType", request.getHeader("svcType"));
		
		map.put("sheetTitle", "정산금액");
		map.put("colLabels", new String[] {"정산년월", "청구유형", "프로젝트/부서명", "상품명", "건수", "정산금액"});
		map.put("colIds", new String[] {"ym", "billType", "projectName", "productName", "cnt", "amt"});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", generalDao.selectGernalList("cash.selectCalList", params));
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
		model.addObject("excelFileName", "calList"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);

		return model;
	}
}
