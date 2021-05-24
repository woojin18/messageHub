package kr.co.uplus.cm.user.controller;

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
import kr.co.uplus.cm.user.service.UserService;
import kr.co.uplus.cm.utils.CommonUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/userApi/manage")
public class UserController {

	@Autowired
	private UserService userSvc;
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

	/**
	 * 사용자 리스트 조회
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
			rtn = userSvc.selectUserList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 사용자 이용정지
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/stopUser")
	public RestResult<?> stopUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = userSvc.stopUser(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 사용자 이용정지 해제
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/releaseUser")
	public RestResult<?> releaseUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = userSvc.releaseUser(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 사용자 삭제
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteUser")
	public RestResult<?> deleteUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = userSvc.deleteUser(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 사용자 수정
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/modifyUser")
	public RestResult<?> modifyUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = userSvc.modifyUser(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 사용자 등록
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/registerUser")
	public RestResult<?> registerUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			// UserId 생성
			String userId = CommonUtils.getCommonId("MBR", 5);
			params.put("userId", userId);

			rtn = userSvc.registerUser(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 사용자 중복체크
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/checkDupcUser")
	public RestResult<?> checkDupcUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {

			rtn = userSvc.checkDupcUser(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
}
