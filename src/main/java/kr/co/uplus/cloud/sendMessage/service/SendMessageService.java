package kr.co.uplus.cloud.sendMessage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cloud.common.consts.DB;
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
     * APP ID 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectAppIdList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_APP_ID_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 발신번호 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectCallbackList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CALLBACK_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 주소록 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectAddressList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> rtnObj = new HashMap<String, Object>();

        //주소록 그룹 목록 조회
        List<Object> addrGrpList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_GRP_LIST, params);
        List<Object> addrCtgyList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_CTGY_LIST, params);

        rtnObj.put("addrGrpList", addrGrpList);
        rtnObj.put("addrCtgyList", addrCtgyList);
        rtn.setData(rtnObj);

        return rtn;
    }

    /**
     * 주소록 카테고리 구성원 목록 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectCmCuList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CM_CU_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }



















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
