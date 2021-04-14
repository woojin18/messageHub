package kr.co.uplus.cloud.template.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cloud.common.consts.Const;
import kr.co.uplus.cloud.common.consts.DB;
import kr.co.uplus.cloud.common.dto.RestResult;
import kr.co.uplus.cloud.dto.PageDto;
import kr.co.uplus.cloud.utils.CommonUtils;
import kr.co.uplus.cloud.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

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
@Log4j2
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
            PageDto pageDto = rtn.getPageDto();
            pageDto.setPageInfo(params);
            //카운트 쿼리 실행
            int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_PUSH_TMPLT_LIST_CNT, params);
            pageDto.setTotCnt(listCnt);
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
    public RestResult<Object> savePushTemplate(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        int resultCnt = 0;

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
	 * RCS 템플릿 리스트 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectRcsTemplateList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_TMPLT, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * RCS 템플릿 등록처리
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> addRcsTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		// RCS 템플릿 등록요청 API 호출
		rtn.setSuccess(true);

		return rtn;
	}

	/**
	 * RCS 템플릿 수정처리
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> updateRcsTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		// RCS 템플릿 수정요청 API 호출
		rtn.setSuccess(true);

		return rtn;
	}

	/**
	 * 알림톡 템플릿 리스트 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectKakaoTalkTemplateList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_KAKAOTALK_TMPLT, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 알림톡 템플릿 등록처리
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> addKakaoTalkTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		// 알림톡 템플릿 등록요청 API 호출
		rtn.setSuccess(true);

		return rtn;
	}

	/**
	 * 알림톡 템플릿 수정처리
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> updateKakaoTalkTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		// 알림톡 템플릿 수정요청 API 호출
		rtn.setSuccess(true);

		return rtn;
	}

	/**
	 * 친구톡 템플릿 리스트 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectFriendTalkTemplateList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_FRIENDTALK_TMPLT, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 친구톡 템플릿 등록처리
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> addFriendTalkTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = generalDao.insertGernal(DB.QRY_INSERT_FRIENDTALK_TMPLT, params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}

	/**
	 * 친구톡 템플릿 수정처리
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> updateFriendTalkTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_FRIENDTALK_TMPLT, params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}

	/**
	 * SMS/MMS 템플릿 리스트 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectSmsTemplateList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_SMS_TMPLT, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * SMS/MMS 템플릿 등록처리
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> addSmsTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = generalDao.insertGernal(DB.QRY_INSERT_SMS_TMPLT, params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}

	/**
	 * SMS/MMS 템플릿 수정처리
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> updateSmsTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_SMS_TMPLT, params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}

	/**
	 * 통합발송 템플릿 리스트 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectMultiSendTemplateList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_MULTISEND_TMPLT, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 통합발송 템플릿 등록처리
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> addMultiSendTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = generalDao.insertGernal(DB.QRY_INSERT_MULTISEND_TMPLT, params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}

	/**
	 * 통합발송 템플릿 수정처리
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> updateMultiSendTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_MULTISEND_TMPLT, params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}

	/**
	 * 스마트발송 템플릿 리스트 조회
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectSmartTemplateList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_SMART_TMPLT, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 스마트발송 템플릿 등록처리
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> addSmartTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = generalDao.insertGernal(DB.QRY_INSERT_SMART_TMPLT, params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}

	/**
	 * 스마트발송 템플릿 수정처리
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> updateSmartTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_SMART_TMPLT, params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}
}
