package kr.co.uplus.cloud.list;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.uplus.cloud.sample.dto.RestResult;
import kr.co.uplus.cloud.utils.CommonUtils;

@RestController
@RequestMapping("/listCtl")
public class ListController {

	@Autowired
	private ListService listSvc;
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/test")
	public RestResult<?> test(
			@RequestBody Map paramMap) {
		
		// commonutils에 집어 넣을것
		int paging = Integer.parseInt(CommonUtils.getString(paramMap.get("paging")));
		int rows = Integer.parseInt(CommonUtils.getString(paramMap.get("rows")));
		
		int rowsFront = (paging-1)*rows;
		
		paramMap.put("rowsFront", rowsFront);
		paramMap.put("rowsEnd", rows);
		
		return listSvc.testList(paramMap);
	}
	
	@PostMapping("/insertList")
	public RestResult<?> insertList(
			@RequestBody Map paramMap) {
		RestResult<?> restResult = new RestResult<>(true);
		
		try {
			listSvc.insertList(paramMap);
		} catch (Exception e) {
			restResult.setSuccess(false);
		}
		
		return restResult;
	}
	
	@PostMapping("/updateList")
	public RestResult<?> updateList(
			@RequestBody Map paramMap) {
		RestResult<?> restResult = new RestResult<>(true);
		
		try {
			listSvc.updateList(paramMap);
		} catch (Exception e) {
			restResult.setSuccess(false);
		}
		
		return restResult;
	}
	
	@PostMapping("/excelUpload")
	public RestResult<?> excelUpload (
			@RequestBody Map paramMap) {
		RestResult<?> restResult = new RestResult<>(true);
		try {
			listSvc.excelUpload(paramMap);
		} catch (Exception e) {
			restResult.setSuccess(false);
		}
		
		return restResult;
		
	}

	@PostMapping(path="/excelDownload")
	public ModelAndView excelDownload(@RequestBody Map paramMap) {

		paramMap.put("rowsFront", 0);
		paramMap.put("rowsEnd", 100);
		
		List list = listSvc.testXlsList(paramMap);
		ModelAndView model = new ModelAndView("sampleXls");
		model.addObject("list", list);
		
		return model;
		
	}
}
