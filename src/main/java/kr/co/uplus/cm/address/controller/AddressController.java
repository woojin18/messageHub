package kr.co.uplus.cm.address.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.address.service.AddressService;
import kr.co.uplus.cm.common.controller.BaseController;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/addressApi/manage")

public class AddressController extends BaseController {

	@Autowired
	private AddressService addressSvc;
	
	/**
	 * 주소카테고리그룹 리스트 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectAddressCateGrpList")
	public RestResult<?> selectAddressCateGrpList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			//super.setContainIgnoreUserInfo(params);
			rtn = addressSvc.selectAddressCateGrpList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	/**
	 * 주소리스트 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectAddressList")
	public RestResult<?> selectAddressList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = addressSvc.selectAddressList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	/**
	 * 주소록 구성원 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectMemberList")
	public RestResult<?> selectMemberList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			//super.setContainIgnoreUserInfo(params);
			rtn = addressSvc.selectMemberList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	/**
	 * 주소록 등록
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/registerAddr")
	public RestResult<?> registerAddr(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		String newProjectId = (String)params.get("newProjectId");
		
		try {
//			super.setContainIgnoreUserInfo(params);
			params.put("projectId", newProjectId); // 입력받은 값으로 재설정
			rtn = addressSvc.registerAddr(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	/**
	 * 주소록 수정
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/modifyAddr")
	public RestResult<?> modifyAddr(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		String afProjectId = (String)params.get("afProjectId");
		
		try {
//			super.setContainIgnoreUserInfo(params);
			params.put("projectId", afProjectId); // 입력받은 값으로 재설정
			rtn = addressSvc.modifyAddr(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	/**
	 * 프로젝트 리스트 조회
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
//			super.setContainIgnoreUserInfo(params);
			rtn = addressSvc.selectProjectList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
}
