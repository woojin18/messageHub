package kr.co.uplus.cm.template.controller;

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
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.sendMessage.dto.AlimTalkTmpltRequestData;
import kr.co.uplus.cm.template.service.TemplateService;
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
@RequestMapping("/uc/template")
public class TemplateController {

    @Autowired
    private TemplateService tmpltSvc;

    @Autowired
    private CommonService commonService;

    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

    /**
     * 푸시 템플릿 리스트 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectPushTmpltList")
    public RestResult<?> selectPushTemplateList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.selectPushTemplateList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectPushTemplateList Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 푸시 템플릿 단건 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectPushTmpltInfo")
    public RestResult<?> selectPushTmpltInfo(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.selectPushTemplateList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectPushTmpltInfo Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 푸시 템플릿 저장처리
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/savePushTmplt")
    public RestResult<?> savePushTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.savePushTemplate(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.savePushTmplt Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 푸시 템플릿 삭제처리
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/deletePushTmplt")
    public RestResult<?> deletePushTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.deletePushTemplate(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.deletePushTmplt Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 푸쉬템플릿 엑셀다운로드
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/excelDownloadPushTmplt")
    public ModelAndView excelDownloadPushTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) throws Exception {
        List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetTitle", "푸시 템플릿 리스트");
        map.put("colLabels", new String[] { "템플릿 ID", "템플릿명", "타 프로젝트 사용여부", "메시지타입", "메시지구분", "등록자", "등록일자" });
        map.put("colIds", new String[] {"tmpltId", "tmpltName", "projectIdNm", "msgTypeName", "msgKindName", "regNm", "regDt"});
        map.put("numColIds", new String[] {});
        map.put("figureColIds", new String[] {});
        map.put("colDataList", tmpltSvc.selectPushTemplateList(params).getData());
        sheetList.add(map);

        ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "pushTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
        model.addObject("sheetList", sheetList);

        return model;
    }

     /**
     * SMS 템플릿 저장처리
     *
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/saveSmsTmplt")
    public RestResult<?> saveSmsTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.saveSmsTemplate(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.saveSmsTmplt Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * SMS 템플릿 리스트 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectSmsTmpltList")
    public RestResult<?> selectSmsTmpltList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.selectSmsTemplateList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectSmsTmpltList Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * SMS 템플릿 정보 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectSmsTmpltInfo")
    public RestResult<?> selectSmsTmpltInfo(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.selectSmsTemplateList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectSmsTmpltInfo Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * SMS 템플릿 삭제처리
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/deleteSmsTmplt")
    public RestResult<?> deleteSmsTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.deleteSmsTmplt(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.deleteSmsTmplt Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * SMS 템플릿 엑셀다운로드
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/excelDownloadSmsTmplt")
    public ModelAndView excelDownloadSmsTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) throws Exception {
        List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetTitle", "SMS 템플릿 리스트");
        map.put("colLabels", new String[] { "템플릿 ID", "템플릿명", "타 프로젝트 사용여부", "발송유형", "등록자", "등록일자" });
        map.put("colIds", new String[] {"tmpltId", "tmpltName", "projectIdNm", "senderType", "regNm", "regDt"});
        map.put("numColIds", new String[] {});
        map.put("figureColIds", new String[] {});
        map.put("colDataList", tmpltSvc.selectSmsTemplateList(params).getData());
        sheetList.add(map);

        ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "smsTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
        model.addObject("sheetList", sheetList);

        return model;
    }

    /**
     * 친구톡 템플릿 저장
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/saveFrndTalkTmplt")
    public RestResult<?> saveFrndTalkTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.saveFrndTalkTmplt(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.saveFrndTalkTmplt Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 친구톡 템플릿 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectFrndTalkList")
    public RestResult<?> selectFrndTalkList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.selectFrndTalkList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectFrndTalkList Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 친구톡 템플릿 정보 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectFrndTalkInfo")
    public RestResult<?> selectFrndTalkInfo(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.selectFrndTalkList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectFrndTalkInfo Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 친구톡 템플릿 엑셀 다운로드
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/excelDownloadFrndTalkTmplt")
    public ModelAndView excelDownloadFrndTalkTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) throws Exception {
        List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetTitle", "친구톡 템플릿 리스트");
        map.put("colLabels", new String[] { "템플릿 ID", "템플릿명", "메시지구분", "등록자", "등록일자" });
        map.put("colIds", new String[] {"tmpltId", "tmpltName", "msgKindName", "regNm", "regDt"});
        map.put("numColIds", new String[] {});
        map.put("figureColIds", new String[] {});
        map.put("colDataList", tmpltSvc.selectFrndTalkList(params).getData());
        sheetList.add(map);

        ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "frndTalkTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
        model.addObject("sheetList", sheetList);

        return model;
    }

    /**
     * 친구톡 템플릿 삭제처리
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/deleteFrndTalkTmplt")
    public RestResult<?> deleteFrndTalkTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.deleteFrndTalkTmplt(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.deleteFrndTalkTmplt Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 카카오 발신 프로필키 목록 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectSenderKeyList")
    public RestResult<?> selectSenderKeyList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.selectSenderKeyList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectSenderKeyList Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 카카오 템플릿 카테고리 그룹 목록 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectKkoTmpltCatGrpList")
    public RestResult<?> selectKkoTmpltCatGrpList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.selectKkoTmpltCatGrpList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectKkoTmpltCatGrpList Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 카카오 템플릿 카테고리 목록 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectKkoTmpltCatList")
    public RestResult<?> selectKkoTmpltCatList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.selectKkoTmpltCatList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectKkoTmpltCatList Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 알림톡 템플릿 승인요청
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/procApprvRequestKkoTmplt")
    public RestResult<?> procApprvRequestKkoTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {

        RestResult<Object> rtn = new RestResult<Object>();
        AlimTalkTmpltRequestData requestData = null;

        try {
            log.info("{}.procApprvRequestKkoTmplt Start ====> params : {}", this.getClass(), params);

            /** 유효성 체크 */
            requestData = tmpltSvc.setAlimTalkTmpltRequestData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info("{}.procApprvRequestKkoTmplt validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }

            /** 알림톡 템플릿 승인요청 처리 */
            return tmpltSvc.procApprvRequestKkoTmplt(requestData, params);

        } catch (Exception e) {
            rtn.setFail("실패하였습니다.");
            log.error("{}.procApprvRequestKkoTmplt Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 알림톡 템플릿 리스트 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectAlimTalkTmpltList")
    public RestResult<?> selectAlimTalkTmpltList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.selectAlimTalkTmpltList(params);
        } catch (Exception e) {
            rtn.setFail("실패하였습니다.");
            log.error("{}.selectAlimTalkTmpltList Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 카카오 템플릿 삭제 요청
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/procDeleteRequestKkoTmplt")
    public RestResult<?> procDeleteRequestKkoTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();

        try {
            rtn = tmpltSvc.procDeleteRequestKkoTmplt(params);
        } catch (Exception e) {
            rtn.setFail("실패하였습니다.");
            log.error("{}.procDeleteRequestKkoTmplt Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 알림톡 템플릿 다운로드
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/excelDownloadAlimTalkTmplt")
    public ModelAndView excelDownloadAlimTalkTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) throws Exception {
        List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetTitle", "알림톡 템플릿 리스트");
        map.put("colLabels", new String[] { "템플릿 코드", "템플릿명", "상태", "발신프로필 타입", "발신 프로필/그룹", "최종수정일자" });
        map.put("colIds", new String[] {"tmpltCode", "tmpltName", "tmpltStatCodeName", "senderKeyTypeName", "senderKey", "updDt"});
        map.put("numColIds", new String[] {});
        map.put("figureColIds", new String[] {});
        map.put("colDataList", tmpltSvc.selectAlimTalkTmpltList(params).getData());
        sheetList.add(map);

        ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "alimTalkTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
        model.addObject("sheetList", sheetList);

        return model;
    }

    /**
     * 친구톡 템플릿 정보 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectAlimTalkInfo")
    public RestResult<?> selectAlimTalkInfo(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.selectAlimTalkTmpltList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectFrndTalkInfo Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 카카오 템플릿 검수 요청
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/procInspectRequestKkoTmplt")
    public RestResult<?> procInspectRequestKkoTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();

        try {
            rtn = tmpltSvc.procInspectRequestKkoTmplt(params);
        } catch (Exception e) {
            rtn.setFail("실패하였습니다.");
            log.error("{}.procInspectRequestKkoTmplt Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 알림톡 템플릿 승인요청
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/procUpdateRequestKkoTmplt")
    public RestResult<?> procUpdateRequestKkoTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {

        RestResult<Object> rtn = new RestResult<Object>();
        AlimTalkTmpltRequestData requestData = null;

        try {
            log.info("{}.procUpdateRequestKkoTmplt Start ====> params : {}", this.getClass(), params);

            /** Set User Info */
            params = commonService.setUserInfo(params);
            log.info("{}.procUpdateRequestKkoTmplt Set User Info ====> params : {}", this.getClass(), params);

            /** 유효성 체크 */
            requestData = tmpltSvc.setAlimTalkTmpltRequestData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info("{}.procUpdateRequestKkoTmplt validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }

            /** 알림톡 템플릿 수정 요청 처리 */
            return tmpltSvc.procUpdateRequestKkoTmplt(requestData, params);

        } catch (Exception e) {
            rtn.setFail("실패하였습니다.");
            log.error("{}.procUpdateRequestKkoTmplt Error : {}", this.getClass(), e);
        }

        return rtn;
    }

}
