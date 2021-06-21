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
        params.put("productCodes", new String[]{Const.MsgProductCode.getType("PUSH")});
        List<Object> pushList = generalDao.selectGernalList(DB.QRY_SELECT_PRD_UNIT_INFO_LIST, params);
        rtnMap.put("push", pushList);

        params = new HashMap<String, Object>();
        params.put("productCodes", new String[]{
                Const.MsgProductCode.getType("RCS_TMP")
                , Const.MsgProductCode.getType("RCS_SMS")
                , Const.MsgProductCode.getType("RCS_LMS")
                , Const.MsgProductCode.getType("RCS_MMS")});
        List<Object> rcsList = generalDao.selectGernalList(DB.QRY_SELECT_PRD_UNIT_INFO_LIST, params);
        rtnMap.put("rcs", rcsList);

        params = new HashMap<String, Object>();
        params.put("productCodes", new String[]{
                Const.MsgProductCode.getType("SMS")
                , Const.MsgProductCode.getType("LMS")
                , Const.MsgProductCode.getType("MMS")});
        List<Object> smsList = generalDao.selectGernalList(DB.QRY_SELECT_PRD_UNIT_INFO_LIST, params);
        rtnMap.put("sms", smsList);

        params = new HashMap<String, Object>();
        params.put("productCodes", new String[]{
                Const.MsgProductCode.getType("ALIM_TALK")
                , Const.MsgProductCode.getType("FRENDTALK_TEXT")
                , Const.MsgProductCode.getType("FRENDTALK_IMAGE")
                , Const.MsgProductCode.getType("FRENDTALK_WIDE")});
        List<Object> kkoList = generalDao.selectGernalList(DB.QRY_SELECT_PRD_UNIT_INFO_LIST, params);
        rtnMap.put("kko", kkoList);

        rtn.setData(rtnMap);
        return rtn;
    }

}
