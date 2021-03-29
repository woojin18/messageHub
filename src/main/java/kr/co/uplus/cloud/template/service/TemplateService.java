package kr.co.uplus.cloud.template.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cloud.sample.dto.RestResult;
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
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectPushTemplateList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList("template.qrySelectPushTemplate", params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 푸시 템플릿 등록처리
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> addPushTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = generalDao.insertGernal("template.qryInsertPushTemplate", params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}

	/**
	 * 푸시 템플릿 수정처리
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> updatePushTemplate(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = generalDao.updateGernal("template.qryUpdatePushTemplate", params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
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

		List<Object> rtnList = generalDao.selectGernalList("template.qrySelectRcsTemplate", params);
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

		List<Object> rtnList = generalDao.selectGernalList("template.qrySelectKakaoTalkTemplate", params);
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

		List<Object> rtnList = generalDao.selectGernalList("template.qrySelectFriendTalkTemplate", params);
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

		int resultCnt = generalDao.insertGernal("template.qryInsertFriendTalkTemplate", params);

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

		int resultCnt = generalDao.updateGernal("template.qryUpdateFriendTalkTemplate", params);

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

		List<Object> rtnList = generalDao.selectGernalList("template.qrySelectSmsTemplate", params);
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

		int resultCnt = generalDao.insertGernal("template.qryInsertSmsTemplate", params);

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

		int resultCnt = generalDao.updateGernal("template.qryUpdateSmsTemplate", params);

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

		List<Object> rtnList = generalDao.selectGernalList("template.qrySelectMultiSendTemplate", params);
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

		int resultCnt = generalDao.insertGernal("template.qryInsertMultiSendTemplate", params);

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

		int resultCnt = generalDao.updateGernal("template.qryUpdateMultiSendTemplate", params);

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

		List<Object> rtnList = generalDao.selectGernalList("template.qrySelectSmartTemplate", params);
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

		int resultCnt = generalDao.insertGernal("template.qryInsertSmartTemplate", params);

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

		int resultCnt = generalDao.updateGernal("template.qryUpdateSmartTemplate", params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}

}
