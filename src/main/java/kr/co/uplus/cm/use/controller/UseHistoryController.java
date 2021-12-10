package kr.co.uplus.cm.use.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.use.service.UseHistoryService;
import kr.co.uplus.cm.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/useApi")

public class UseHistoryController {

	@Autowired
	private UseHistoryService useHistorySvc;

	@Autowired
	private CommonService commonService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}
	
	/**
	 * 이용내역 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/selectUseHistory")
	public RestResult<?> selectUseHistory(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();
//		try {
			rtn = useHistorySvc.selectUseHistory(params);
//		} catch (Exception e) {
//			rtn.setSuccess(false);
//			rtn.setMessage("실패하였습니다.");
//			log.error("{} Error : {}", this.getClass(), e);
//		}
		return rtn; 
	}
	
	/**
	 * 이용내역 엑셀다운로드
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(path = "/excelDownloadUseHistory")
	public ModelAndView excelDownloadUseHistory(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {
		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", "이용내역");
		map.put("colLabels", new String[] { "프로젝트", "이용채널", "결제유형", "신청일", "건수", "이용요금"});
		map.put("colIds", new String[] {"projectName", "useCh", "payTypeName", "regDt", "cnt", "sumChGrpAmount"});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", ((Map<String, Object>)useHistorySvc.selectUseHistory(params).getData()).get("useHistoryList"));
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
		model.addObject("excelFileName", "useHistory_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);

		return model;
	}
	
	/**
	 * 고객별 상품 단가관리 상세
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectCorpProductUnitDetail")
	public RestResult<?> selectCorpProductUnitDetail(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<?> rtn = new RestResult<Object>();
		try {
			rtn = useHistorySvc.selectCorpProductUnitDetail(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 최근 6개월 이용건수 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectSixMonthUseCount")
	public RestResult<?> selectSixMonthUseCount(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = useHistorySvc.selectSixMonthUseCount(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	/**
	 * 최근 6개월 선불 결제금액 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectSixMonthPrePayAmtList")
	public RestResult<?> selectSixMonthPrePayAmtList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = useHistorySvc.selectSixMonthPrePayAmtList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 최근 6개월 후불 청구금액(이용금액포함) 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectSixMonthDefPayAmt")
	public RestResult<?> selectSixMonthDefPayAmt(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = useHistorySvc.selectSixMonthDefPayAmt(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	/**
	 * 이용상세 목록
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectUseDetailList")
	public RestResult<?> selectUseDetailList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = useHistorySvc.selectUseDetailList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 이용상세 목록 엑셀다운로드
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(path = "/excelDownloadUseDetailList")
	public ModelAndView excelDownloadStatisList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {
		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("sheetTitle", "이용상세");
		map.put("colLabels", new String[] { "날짜", "사업자/프로젝트", "합계 건수", "SMS 건수", "LMS 건수", "MMS 건수", "RCS 템플릿 건수", "RCS SMS 건수", "RCS LMS 건수", "RCS MMS 건수", "알림톡 건수", "친구톡 Text 건수", "친구톡 이미지 건수", "친구톡 와이드 건수", "PUSH 건수", "합계 금액", "SMS 금액", "LMS 금액", "MMS 금액", "RCS 템플릿 금액", "RCS SMS 금액", "RCS LMS 금액", "RCS MMS 금액", "알림톡 금액", "친구톡 Text 금액", "친구톡 이미지 금액", "친구톡 와이드 금액", "PUSH 금액"});
		map.put("colIds", new String[] {"ymd", "projectName", "succCnt", "smsCnt", "lmsCnt", "mmsCnt", "rtplCnt", "rsmsCnt", "rlmsCnt", "rmmsCnt", "kalt1Cnt", "kfrt1Cnt", "kfrm2Cnt", "kfrm3Cnt", "pushCnt", "succAmt", "smsAmt", "lmsAmt", "mmsAmt", "rtplAmt", "rsmsAmt", "rlmsAmt", "rmmsAmt", "kalt1Amt", "kfrt1Amt", "kfrm2Amt", "kfrm3Amt", "pushAmt"});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", useHistorySvc.selectUseDetailList(params).getData());
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
		model.addObject("excelFileName", "useDetailList"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);

		return model;
	}
	
}

