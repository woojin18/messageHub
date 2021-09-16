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

    /**
     * 공지사항 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectNoticeList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_NOTICE_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_NOTICE_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 자료실 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectLibraryList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_LIBRARY_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_LIBRARY_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 첨부파일 정보 조회
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectAttachFileInfo(Map<String, Object> params) throws Exception {
        return (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_ATTACH_FILE_INFO, params);
    }

    /**
     * 약관 개정이력 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectCmUseTermsHistList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CM_USE_TERMS_HIST_LIST, params);
        rtn.setData(rtnList);
        return rtn;
    }

    /**
     * 약관 정보 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectCmUseTermsInfo(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        Object rtnInfo = generalDao.selectGernalObject(DB.QRY_SELECT_CM_USE_TERMS_INFO, params);
        rtn.setData(rtnInfo);
        return rtn;
    }

}
