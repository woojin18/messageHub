package kr.co.uplus.cm.template.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

/**
 * <pre>
 * TemplateService
 * 각 채널별 템플릿 생성 및 템플릿 리스트 관리 Service
 * </pre>
 *
 * @author liam (Lee Sung Min)
 * @Date : 2021.03.25.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@Service
public class TemplateService {

	@Autowired
	private GeneralDao generalDao;

	/**
     * 푸시 템플릿 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectPushTemplateList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_PUSH_TMPLT_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_PUSH_TMPLT_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 푸시 템플릿 저장 처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> savePushTemplate(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        int resultCnt = 0;

        String otherProjectUseYn = CommonUtils.getStrValue(params, "otherProjectUseYn");

        if(!StringUtils.equalsIgnoreCase(otherProjectUseYn, Const.COMM_NO)) {
            params.put("projectId", Const.OTHER_PROJECT_USE_ID);
        }

        // update
        if (params.containsKey("tmpltId") && StringUtils.isNotBlank(CommonUtils.getString(params.get("tmpltId")))) {
            resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_PUSH_TMPLT, params);
        // insert
        } else {
        	// 템플릿ID 취득
            String tmpltId = CommonUtils.getCommonId(Const.TMPLT_PREFIX, 5);
            params.put("tmpltId", tmpltId);
            resultCnt = generalDao.insertGernal(DB.QRY_INSERT_PUSH_TMPLT, params);
        }

        if (resultCnt <= 0) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        } else {
            rtn.setSuccess(true);
            rtn.setData(params);
        }

        return rtn;
    }

    /**
     * 푸시 템플릿 삭제 처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> deletePushTemplate(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        int resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_PUSH_TMPLT, params);
        if (resultCnt <= 0) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        } else {
            rtn.setSuccess(true);
            rtn.setData(params);
        }

        return rtn;
    }

    /**
     * 푸시 템플릿 저장 처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> saveSmsTemplate(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        int resultCnt = 0;
        String otherProjectUseYn = CommonUtils.getStrValue(params, "otherProjectUseYn");

        if(!StringUtils.equalsIgnoreCase(otherProjectUseYn, Const.COMM_NO)) {
            params.put("projectId", Const.OTHER_PROJECT_USE_ID);
        }

        // update
        if (params.containsKey("tmpltId") && StringUtils.isNotBlank(CommonUtils.getString(params.get("tmpltId")))) {
            resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_SMS_TMPLT, params);
        // insert
        } else {
            // 템플릿ID 취득
            String tmpltId = CommonUtils.getCommonId(Const.TMPLT_PREFIX, 5);
            params.put("tmpltId", tmpltId);
            resultCnt = generalDao.insertGernal(DB.QRY_INSERT_SMS_TMPLT, params);
        }

        if (resultCnt <= 0) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        } else {
            rtn.setSuccess(true);
            rtn.setData(params);
        }

        return rtn;
    }

    /**
     * SMS/MMS 템플릿 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectSmsTemplateList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_SMS_TMPLT_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_SMS_TMPLT_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * SMS 템플릿 삭제처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> deleteSmsTmplt(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        int resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_SMS_TMPLT, params);
        if (resultCnt <= 0) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        } else {
            rtn.setSuccess(true);
            rtn.setData(params);
        }

        return rtn;
    }


}
