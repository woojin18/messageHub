package kr.co.uplus.cm.home.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.home.service.HomeService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/home")
public class HomeController {

	@Autowired
	private HomeService homeSvc;

	/**
	 * 대시보드 프로젝트 정보 조회
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectProjectInfo")
	public RestResult<?> selectProjectInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = homeSvc.selectProjectInfo(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{}.selectProjectInfo Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 대시보드 프로젝트 리스트 조회
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectProjectList")
	public RestResult<?> selectProjectList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = homeSvc.selectProjectList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{}.selectProjectList Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 대시보드 공지사항 리스트 조회
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectNoticeList")
	public RestResult<?> selectNoticeList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = homeSvc.selectNoticeList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{}.selectNoticeList Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 대시보드 채널별 성공/실패 카운트 조회
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectChTotCntInfo")
	public RestResult<?> selectChTotCntInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = homeSvc.selectChTotCntInfo(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{}.selectNoticeList Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 대시보드 일자별 채널 성공/실패 카운트 조회
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectChSuccFailCntList")
	public RestResult<?> selectChSuccFailCntList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = homeSvc.selectChSuccFailCntList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{}.selectNoticeList Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 대시보드 일자별 실패코드 조회
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectChFailCodeList")
	public RestResult<?> selectChFailCodeList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = homeSvc.selectChFailCodeList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{}.selectNoticeList Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 대시보드 채널별 성공/실패현황 조회
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectDayStatsList")
	public RestResult<?> selectDayStatsList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = homeSvc.selectDayStatsList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{}.selectNoticeList Error : {}", this.getClass(), e);
		}

		return rtn;
	}
}
