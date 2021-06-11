package kr.co.uplus.cm.integratedTemplate.controller;

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
import kr.co.uplus.cm.integratedTemplate.service.IntegratedTemplateService;
import kr.co.uplus.cm.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * TemplateController
 * 각 채널별 템플릿 생성 및 리스트 관리 Controller
 * </pre>
 *
 * @author : liam (Lee Sung Min)
 * @Date : 2021.03.25.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@Log4j2
@RestController
@RequestMapping("/integratedTemplateApi")
public class IntegratedTemplateController {

	@Autowired
	private IntegratedTemplateService integratedTemplateService;

    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

	/**
	 * 통합 템플릿 리스트 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectIntegratedTemplateList")
	public RestResult<?> selectIntegratedTemplateList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = integratedTemplateService.selectIntegratedTemplateList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 통합 템플릿 단건 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectIntegratedTemplateInfo")
	public RestResult<?> selectIntegratedTemplateInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = integratedTemplateService.selectIntegratedTemplateInfo(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 통합 템플릿 저장처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/insertIntegratedTemplate")
	public RestResult<?> insertIntegratedTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = integratedTemplateService.insertIntegratedTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 통합 템플릿 삭제처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteIntegratedTemplate")
	public RestResult<?> deleteIntegratedTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = integratedTemplateService.deleteIntegratedTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 통합 템플릿 엑셀다운로드
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = "/excelDownloadIntegratedTemplate")
	public ModelAndView excelDownloadIntegratedTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {
		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", "푸시 템플릿 리스트");
		map.put("colLabels", new String[] { "템플릿 ID", "템플릿명", "타 프로젝트 사용여부", "메시지타입", "메시지구분", "등록자", "등록일자" });
        map.put("colIds", new String[] {"tmpltId", "tmpltName", "otherProjectUseYn", "msgTypeName", "msgKindName", "regId", "regDt"});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", integratedTemplateService.selectIntegratedTemplateList(params).getData());
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "pushTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
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
            rtn = integratedTemplateService.selectBrandList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectCallbackList Error : {}", this.getClass(), e);
        }
        return rtn;
    }

}
