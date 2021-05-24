package kr.co.uplus.cm.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;

@RestController
@RequestMapping("/listCtl")
public class ListController {

	@Autowired
	private ListService listSvc;
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/test")
	public RestResult<?> test(@RequestBody Map paramMap) {

		// commonutils에 집어 넣을것
		int paging = Integer.parseInt(CommonUtils.getString(paramMap.get("paging")));
		int rows = Integer.parseInt(CommonUtils.getString(paramMap.get("rows")));

		int rowsFront = (paging - 1) * rows;

		paramMap.put("rowsFront", rowsFront);
		paramMap.put("rowsEnd", rows);

		return listSvc.testList(paramMap);
	}

	@PostMapping("/insertList")
	public RestResult<?> insertList(@RequestBody Map paramMap) {
		RestResult<?> restResult = new RestResult<>(true);

		try {
			listSvc.insertList(paramMap);
		} catch (Exception e) {
			restResult.setSuccess(false);
		}

		return restResult;
	}

	@PostMapping("/updateList")
	public RestResult<?> updateList(@RequestBody Map paramMap) {
		RestResult<?> restResult = new RestResult<>(true);

		try {
			listSvc.updateList(paramMap);
		} catch (Exception e) {
			restResult.setSuccess(false);
		}

		return restResult;
	}

	@PostMapping("/excelUpload")
	public RestResult<?> excelUpload(@RequestBody Map paramMap) {
		RestResult<?> restResult = new RestResult<>(true);
		try {
			listSvc.excelUpload(paramMap);
		} catch (Exception e) {
			restResult.setSuccess(false);
		}

		return restResult;

	}

	@PostMapping(path = "/excelDownload")
	public ModelAndView excelDownload(@RequestBody Map paramMap) {

		paramMap.put("rowsFront", 0);
		paramMap.put("rowsEnd", 100);

		List list = listSvc.testXlsList(paramMap);
		ModelAndView model = new ModelAndView("sampleXls");
		model.addObject("list", list);

		return model;

	}

	@PostMapping(path = "/xlsxDownload", produces = "application/vnd.ms-excel")
	public ModelAndView xlsxDownload(@RequestParam(value = "col1", defaultValue = "") String col1,
			@RequestParam(value = "col2", defaultValue = "") String col2,

			@RequestParam(value = "sheetTitle", defaultValue = "") String sheetTitle,
			@RequestParam(value = "excelFileName", defaultValue = "") String excelFileName,
			@RequestParam(value = "colLabels", required = false) String[] colLabels,
			@RequestParam(value = "colIds", required = false) String[] colIds,
			@RequestParam(value = "numColIds", required = false) String[] numColIds,
			@RequestParam(value = "figureColIds", required = false) String[] figureColIds, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("col1", col1);
		params.put("col2", col2);
		params.put("rowsFront", 0);
		params.put("rowsEnd", 100);
		List list = listSvc.testXlsList(params);

		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", sheetTitle);
		map.put("colLabels", colLabels);
		map.put("colIds", colIds);
		map.put("numColIds", numColIds);
		map.put("figureColIds", figureColIds);
		map.put("colDataList", list);
		sheetList.add(map);

//		ModelAndView model = new ModelAndView("commonXlsView");
		ModelAndView model = new ModelAndView("commonXlsxView");
		model.addObject("excelFileName", excelFileName);
		model.addObject("sheetList", sheetList);

		return model;
	}
}
