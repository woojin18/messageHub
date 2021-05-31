package kr.co.uplus.cm.project.controller;

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
import kr.co.uplus.cm.project.service.MemberService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/memberApi/manage")
public class MemberController {

	@Autowired
	private MemberService memberSvc;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}
	
	/**
	 * 멤버 리스트 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectProjectMemberList")
	public RestResult<?> selectProjectMemberList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = memberSvc.selectProjectMemberList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	/**
	 * 멤버 추가
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/saveProjectMember")
	public RestResult<?> saveProjectMember(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = memberSvc.saveProjectMember(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	/**
	 * 멤버삭제
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteProjectMember")
	public RestResult<?> deleteProjectMember(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = memberSvc.deleteProjectMember(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	/**
	 * 사용자 목록 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectUserList")
	public RestResult<?> selectUserList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = memberSvc.selectUserList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	/**
	 * 프로젝트 멤버 등록
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/registerMember")
	public RestResult<?> registerMember(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = memberSvc.registerMember(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
}
