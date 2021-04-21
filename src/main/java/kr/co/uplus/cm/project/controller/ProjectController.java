package kr.co.uplus.cm.project.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.project.service.ProjectService;

@RestController
@RequestMapping("/projectApi/manage")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	// 프로젝트 리스트 조회
	@PostMapping("/selectProjectList")
	public RestResult<?> selectProjectList(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return projectService.selectProjectList(params);
	}

	// 프로젝트명 중복 조회
	@PostMapping("/checkProjectNameDuplicate")
	public RestResult<?> checkProjectNameDuplicate(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return projectService.checkProjectNameDuplicate(params);
	}

	// 프로젝트 저장
	@PostMapping("/saveProject")
	public RestResult<?> saveProject(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		try {
			projectService.saveProject(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
	}
	

	// 발신번호관리 > 사전등록예외대상 사업자 구분 확인
	@PostMapping("/checkPreRegYn")
	public RestResult<?> checkPreRegYn(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return projectService.checkPreRegYn(params);
	}
	
	// 사전등록예외대상 저장
	@PostMapping("/savePreRegEx")
	public RestResult<?> savePreRegEx(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		try {
			projectService.savePreRegEx(params);
			rtn.setSuccess(true);
			rtn.setData(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
	}
}
