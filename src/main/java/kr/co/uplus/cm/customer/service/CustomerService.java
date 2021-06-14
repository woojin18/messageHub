package kr.co.uplus.cm.customer.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class CustomerService {

    @Autowired
    private GeneralDao generalDao;

    /**
     * FAQ 구분 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectFaqTypeList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_FAQ_TYPE_LIST, params);
        rtn.setData(rtnList);
        return rtn;
    }

    /**
     * FAQ 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectFaqList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_FAQ_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_FAQ_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

}
