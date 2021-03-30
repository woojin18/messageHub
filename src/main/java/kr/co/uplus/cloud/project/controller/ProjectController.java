package kr.co.uplus.cloud.project.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cloud.project.service.ProjectService;
import kr.co.uplus.cloud.sample.dto.RestResult;
import kr.co.uplus.cloud.utils.CommonUtils;

@RestController
@RequestMapping("/projectApi/manage")
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	// 프로젝트 리스트 조회
	@PostMapping("/selectProjectList")
	public RestResult<?> selectProjectList(
				@RequestBody Map<String, Object> params,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {

		// commonutils에 집어 넣을것
		int paging = Integer.parseInt(CommonUtils.getString(params.get("paging")));
		int rows = Integer.parseInt(CommonUtils.getString(params.get("rows")));
		
		int rowsFront = (paging-1)*rows;
		
		params.put("rowsFront", rowsFront);
		params.put("rowsEnd", rows);
		
		return projectService.selectProjectList(params);
    }
	
	// 프로젝트명 중복 조회
	@PostMapping("/checkProjectNameDuplicate")
	public RestResult<?> checkProjectNameDuplicate(
				@RequestBody Map<String, Object> params,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		
		return projectService.checkProjectNameDuplicate(params);
    }
	

	// 프로젝트 저장
	@PostMapping("/saveProject")
	public RestResult<?> saveProject(
				@RequestBody Map<String, Object> params,
				HttpServletRequest request,
				HttpServletResponse response) {
		System.err.println("////////////////////////////////////////////////////");
		RestResult<Object> rtn = new RestResult<Object>(true);
		try {
			projectService.saveProject(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		return rtn;
    }
}
