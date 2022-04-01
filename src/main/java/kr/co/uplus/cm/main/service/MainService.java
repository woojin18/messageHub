package kr.co.uplus.cm.main.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class MainService {

    @Autowired
    private GeneralDao generalDao;

    /**
     * 상품 가격정보 리스트 조회
     * 순서정보 없어서 CH_GRP 대신 PRODUCT_CODE로 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectPrdUnitInfoList() throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productCodes", new String[]{Const.MsgProductCode.PUSH.getCode()});
        List<Object> pushList = generalDao.selectGernalList(DB.QRY_SELECT_PRD_UNIT_INFO_LIST, params);
        rtnMap.put("push", pushList);

        params = new HashMap<String, Object>();
        params.put("productCodes", new String[]{
                Const.MsgProductCode.RCS_TMP.getCode()
                , Const.MsgProductCode.RCS_SMS.getCode()
                , Const.MsgProductCode.RCS_LMS.getCode()
                , Const.MsgProductCode.RCS_MMS.getCode()});
        List<Object> rcsList = generalDao.selectGernalList(DB.QRY_SELECT_PRD_UNIT_INFO_LIST, params);
        rtnMap.put("rcs", rcsList);

        params = new HashMap<String, Object>();
        params.put("productCodes", new String[]{
                Const.MsgProductCode.SMS.getCode()
                , Const.MsgProductCode.LMS.getCode()
                , Const.MsgProductCode.MMS.getCode()});
        List<Object> smsList = generalDao.selectGernalList(DB.QRY_SELECT_PRD_UNIT_INFO_LIST, params);
        rtnMap.put("sms", smsList);

        params = new HashMap<String, Object>();
        params.put("productCodes", new String[]{
                Const.MsgProductCode.ALIMTALK.getCode()
                , Const.MsgProductCode.ALIMTALK_IMAGE.getCode()
                , Const.MsgProductCode.FRENDTALK_TEXT.getCode()
                , Const.MsgProductCode.FRENDTALK_IMAGE.getCode()
                , Const.MsgProductCode.FRENDTALK_WIDE.getCode()});
        List<Object> kkoList = generalDao.selectGernalList(DB.QRY_SELECT_PRD_UNIT_INFO_LIST, params);
        rtnMap.put("kko", kkoList);

        rtn.setData(rtnMap);
        return rtn;
    }

}
