package kr.co.uplus.cloud.sendMessage.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cloud.common.dto.RestResult;
import kr.co.uplus.cloud.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * SendMessageService
 * 각 채널별 메시지 발송처리 Service
 * </pre>
 * 
 * @author liam (Lee Sung Min)
 * @Date : 2021.03.29.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@Log4j2
@Service
public class SendMessageService {

	@Autowired
	private GeneralDao generalDao;

	/**
	 * 푸시 메시지 발송처리
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> sendPushMessage(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		// 푸시 발송 G/W API 호출

		// 푸시 발송내역 저장
		int resultCnt = generalDao.insertGernal("sendMessage.qryInsertSendPushMessage", params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}
}
