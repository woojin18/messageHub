package kr.co.uplus.cm.urlInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.urlInfo.service.UrlInfoService;
import kr.co.uplus.cm.utils.DateUtil;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
@RequestMapping("/uc/urlInfo")
public class UrlInfoController {

    @Autowired
    private UrlInfoService urlInfoService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
    }

    /**
     * 단축URL 목록 조회
     * @param params
     * @return RestResult
     */
    @PostMapping("/selectUrlInfoList")
    public RestResult<?> selectUrlInfoList(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = urlInfoService.selectUrlInfoList(params);
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.selectUrlInfoList Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 단축URL 통계 목록 조회
     * @param params
     * @return RestResult
     */
    @PostMapping("/selectUrlInfoStatList")
    public RestResult<?> selectUrlInfoStatList(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = urlInfoService.selectUrlInfoStatList(params);
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.selectUrlInfoStatList Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 단축URL 통계 상세 조회
     * @param params
     * @return RestResult
     */
    @PostMapping("/selectUrlInfoStatDetail")
    public RestResult<?> selectUrlInfoStatDetail(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = urlInfoService.selectUrlInfoStatDetail(params);
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.selectUrlInfoStatDetail Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 단축URL 생성
     * @param params
     * @return RestResult
     */
    @PostMapping("/insertUrlInfo")
    public RestResult<?> insertUrlInfo(@RequestBody Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = urlInfoService.insertUrlInfo(params);
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.insertUrlInfo Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 단축URL 삭제
     * @param params
     * @return
     */
    @PostMapping("/deleteUrlInfo")
    public RestResult<?> deleteUrlInfo(@RequestBody Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = urlInfoService.deleteUrlInfo(params);
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.deleteUrlInfo Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
	 * 단축URL 엑셀 다운로드
	 * @param params
	 * @return
	 */
	@PostMapping(path = "/excelDownUrlInfoStatList")
	public ModelAndView excelDownUrlInfoStatList(@RequestBody Map<String, Object> params) throws Exception {
		Object colDataList  = urlInfoService.excelDownUrlInfoStatList(params).getData();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", 		"단축URL+ & 통계");
		map.put("colLabels", 		new String[] { "제목", "단축URL+ ID", "원본URL", "등록일", "만료일", "총 발송수", "클릭수", "클릭율"});
		map.put("colIds", 			new String[] {"title", "urlId", "orgUrl", "regDt", "expDt", "totCnt", "conCnt", "per"});
		map.put("numColIds", 		new String[] {});
		map.put("figureColIds", 	new String[] {});
		map.put("colDataList", 		colDataList);

		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
		model.addObject("excelFileName", "UrlInfoStatList_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);

		return model;
	}
}
