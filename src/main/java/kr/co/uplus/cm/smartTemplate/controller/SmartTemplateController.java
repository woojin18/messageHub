package kr.co.uplus.cm.smartTemplate.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.smartTemplate.service.SmartTemplateService;
import kr.co.uplus.cm.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * Smart TemplateController
 * 스마트 템플릿 생성 및 리스트 관리 Controller
 * </pre>
 *
 * @author : liam (Lee Sung Min)
 * @Date : 2021.03.25.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@Log4j2
@RestController
@RequestMapping("/smartTemplateApi")
public class SmartTemplateController {

	@Autowired
	private SmartTemplateService smartTemplateService;

    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

	/**
	 * 스마트 상품 리스트 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectSmartProductList")
	public RestResult<?> selectSmartProductList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = smartTemplateService.selectSmartProductList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	
	/**
	 * 스마트 상품 리스트 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectSmartProductInfo")
	public RestResult<?> selectSmartProductInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = smartTemplateService.selectSmartProductInfo(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	
	/**
	 * 스마트 템플릿 리스트 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectSmartTemplateList")
	public RestResult<?> selectSmartTemplateList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = smartTemplateService.selectSmartTemplateList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 스마트 템플릿 단건 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectSmartTemplateInfo")
	public RestResult<?> selectSmartTemplateInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = smartTemplateService.selectSmartTemplateInfo(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 스마트 템플릿 저장처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/insertSmartTemplate")
	public RestResult<?> insertSmartTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = smartTemplateService.insertSmartTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 스마트 템플릿 삭제처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteSmartTemplate")
	public RestResult<?> deleteSmartTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = smartTemplateService.deleteSmartTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 스마트 템플릿 엑셀다운로드
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = "/excelDownloadSmartTemplate")
	public ModelAndView excelDownloadSmartTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {
		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", "스마트 템플릿 리스트");
		map.put("colLabels", new String[] { "템플릿 ID", "템플릿명", "상품유형", "타 프로젝트 사용여부", "등록자", "등록일자" });
        map.put("colIds", new String[] {"tmpltCode", "tmpltTitle", "productName", "otherProjectUseYn", "regId", "regDt"});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", smartTemplateService.selectSmartTemplateList(params).getData());
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "smartTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);

		return model;
	}


    /**
     * 브랜드 리스트 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectBrandList")
    public RestResult<?> selectBrandList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = smartTemplateService.selectBrandList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectCallbackList Error : {}", this.getClass(), e);
        }
        return rtn;
    }
}
