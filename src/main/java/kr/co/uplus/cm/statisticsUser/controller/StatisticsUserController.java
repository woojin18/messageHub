package kr.co.uplus.cm.statisticsUser.controller;

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

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.statisticsUser.service.StatisticsUserService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/statisticsUserApi/list")

public class StatisticsUserController {

	@Autowired
	private StatisticsUserService statisticsUserSvc;

	@Autowired
	private CommonService commonService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

	/**
	 * 전송건수 및 비율 리스트 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectSendByList")
	public RestResult<?> selectSendByList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = statisticsUserSvc.selectSendByList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	/**
	 * 전송건수 리스트 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectSendByCntList")
	public RestResult<?> selectSendByCntList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = statisticsUserSvc.selectSendByCntList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
}
