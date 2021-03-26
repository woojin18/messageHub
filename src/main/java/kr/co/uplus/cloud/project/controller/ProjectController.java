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
}
