package kr.co.uplus.cm.messageStatus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

//import bin.main.kr.co.uplus.cm.common.type.MongoConf;
//import bin.main.kr.co.uplus.cm.gw.model.mongo.CmMsgInfoDto;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.type.MongoConf;
import kr.co.uplus.cm.gw.model.mongo.CmMsgInfoDto;
import kr.co.uplus.cm.messageStatus.service.MessageStatusService;
import kr.co.uplus.cm.utils.DateUtil;
import kr.co.uplus.config.mongo.cmd.MongoCmd;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/messageStatusApi")
public class MessageStatusController {

	@Autowired
	MessageStatusService messageStatusService;

	@Autowired
	private MongoCmd mongoCmd;

	// 메시지 현황 리스트 조회
	@PostMapping("/selectMessageStatusList")
	public RestResult<?> selectMessageStatusList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//return messageStatusService.selectMessageStatusList(params);
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = messageStatusService.selectMessageStatusList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}


	/**
	 * 메시지 현황 엑셀다운로드
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = "/excelDownloadMessageStatus")
	public ModelAndView excelDownloadMessageStatus(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {
		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", "메시지 현황 리스트");
		map.put("colLabels", new String[] { "메시지", "발송일시", "발송타입", "테그", "Push로그인ID", "수신자휴대폰", "결과" });
        map.put("colIds", new String[] {"message", "regDt", "senderTypeNm", "campaignId", "PushCuid", "phone", "gwResultNm"});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", messageStatusService.selectMessageStatusList(params).getData());
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "messageStatus_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);

		return model;
	}


	// 메시지 현황 상세 조회
	@PostMapping("/selectMessageStatusDetail")
	public RestResult<?> selectMessageStatusDetail(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String msgKey 		= params.get("msgKey").toString();
		log.debug("msgKey : "+msgKey);

		Query query = new Query(Criteria.where("msgKey").is(msgKey));
		CmMsgInfoDto msgInfo = mongoCmd.findOne(query, CmMsgInfoDto.class, MongoConf.CM_MSG_INFO.key);
		String msg = "";//메시지 내용
		String msgTitle = "";//메시지 제목

		if(msgInfo != null) {
			msg = msgInfo.getMsg();
		}
		RestResult<Object> rtn = new RestResult<Object>();

		rtn = messageStatusService.selectMessageStatusDetail(params);

		List<HashMap<String, Object>> mapList = (List<HashMap<String, Object>>) rtn.getData();
		HashMap<String,Object> hMap = mapList.get(0);
		hMap.put("body", msg);

		mapList.set(0, hMap);

		rtn.setData(mapList);

		return rtn;
	}


	// 웹 현황 리스트 조회
	@PostMapping("/selectWebSendList")
	public RestResult<?> selectWebSendList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//return messageStatusService.selectMessageStatusList(params);
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = messageStatusService.selectWebSendList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}


	// 웹 실패 현황 리스트 조회
	@PostMapping("/selectWebSendFailList")
	public RestResult<?> selectWebSendFailList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//return messageStatusService.selectMessageStatusList(params);
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = messageStatusService.selectWebSendFailList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}


	/**
	 * 웹 현황 엑셀다운로드
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = "/excelDownloadWebSend")
	public ModelAndView excelDownloadWebSend(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {

		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", "웹 발송 현황 리스트");
		map.put("colLabels", new String[] { "발송자", "전체발송건수", "발송타입", "푸시", "RCS", "알림톡", "친구톡", "sms", "발송실패", "발송일자" });
        map.put("colIds", new String[] {"corpName", "senderCnt", "senderTypeNm", "push", "rcs", "alimTalk", "friendTalk", "sms", "sendFailCnt", "regDt"});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", messageStatusService.selectWebSendList(params).getData());
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "webSend_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);

		return model;
	}


	/**
	 * 웹 실패 현황 엑셀다운로드
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = "/excelDownloadWebSendFail")
	public ModelAndView excelDownloadWebSendFail(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {

		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", "웹 발송 실패 현황 리스트");
		map.put("colLabels", new String[] { "수신자 ID", "수신자 이름", "수신자 휴대폰" });
        map.put("colIds", new String[] {"cuInfoId", "cuName", "hpNumber"});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", messageStatusService.selectWebSendFailList(params).getData());
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "webSendFail_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);

		return model;
	}



	// 예약발송 리스트 조회
	@PostMapping("/selectBookingSendList")
	public RestResult<?> selectBookingSendList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {


		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = messageStatusService.selectBookingSendList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}


	// 예약발송 취소
	@PostMapping("/cancelBookingSend")
	public RestResult<?> cancelBookingSend(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = messageStatusService.cancelBookingSend(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}




	/**
	 * 예약발송 엑셀다운로드
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = "/excelDownloadBookingSend")
	public ModelAndView excelDownloadBookingSend(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {

		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", "예약발송 현황 리스트");
		map.put("colLabels", new String[] { "발송자", "발송채널", "푸시", "RCS", "알림톡", "친구톡", "sms", "대체발송", "등록일시", "발송일자"  });
        map.put("colIds", new String[] {"corpName", "senderTypeNm", "push", "rcs", "alimTalk", "friendTalk", "sms", "replcSend", "regDt", "reqDt"});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", messageStatusService.selectBookingSendList(params).getData());
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "bookingSend_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);

		return model;
	}

	/*
    public static Map<String,Object> convertJSONstringToMap(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();

        map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        return map;
    }*/


	// 메시지 현황 상세 조회
	@PostMapping("/selectBookingMsgInfo")
	public RestResult<?> selectBookingMsgInfo(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String msgKey 		= params.get("msgKey").toString();
		log.debug("msgKey : "+msgKey);

		Query query = new Query(Criteria.where("msgKey").is(msgKey));
		CmMsgInfoDto msgInfo = mongoCmd.findOne(query, CmMsgInfoDto.class, MongoConf.CM_MSG_INFO.key);
		String msg = "";//메시지 내용
		String msgTitle = "";//메시지 제목

		if(msgInfo != null) {
			msg = msgInfo.getMsg();
		}
		RestResult<Object> rtn = new RestResult<Object>();

		List<HashMap<String, Object>> mapList = (List<HashMap<String, Object>>) rtn.getData();
		HashMap<String,Object> hMap = mapList.get(0);
		hMap.put("msg", msg);

		mapList.set(0, hMap);

		rtn.setData(mapList);

		return rtn;
	}


	// MO 수신 리스트 조회
	@PostMapping("/selectMoReceptionList")
	public RestResult<?> selectMoReceptionList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = messageStatusService.selectMoReceptionList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * MO 수신 리스트 엑셀다운로드
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = "/excelDownloadMoReceptionStatus")
	public ModelAndView excelDownloadMoReceptionStatus(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {

		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", "MO 수신 리스트");
		map.put("colLabels", new String[] { "MO타입", "MO수신번호", "MO발신번호", "상품코드", "MO제목", "이통사", "상태", "등록일자"  });
        map.put("colIds", new String[] {"moType", "moNumber", "moCallback", "productCode", "moTitle", "telco", "codeVal", "moRecvDt"});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", messageStatusService.selectMoReceptionList(params).getData());
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "moReceptionStatus_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);

		return model;
	}


	// MO 상태값 리스트 조회
	@PostMapping("/selectConditionList")
	public RestResult<?> selectConditionList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = messageStatusService.selectConditionList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}


	// 수신번호 리스트 조회
	@PostMapping("/selectReceptionNumberList")
	public RestResult<?> selectReceptionNumberList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = messageStatusService.selectReceptionNumberList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

}
