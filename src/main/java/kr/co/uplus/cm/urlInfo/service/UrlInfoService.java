package kr.co.uplus.cm.urlInfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.common.utils.SecureRandomStringUtils;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class UrlInfoService {

	@Autowired
	private CommonService commonService;

    @Autowired
    private GeneralDao generalDao;

    /**
     * 단축URL 목록 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectUrlInfoList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_URL_INFO_LIST_CNT, params);
                params.put("totCnt", listCnt);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_URL_INFO_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }


    /**
     * 단축URL 통계 목록 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectUrlInfoStatList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_URL_INFO_LIST_CNT, params);
                params.put("totCnt", listCnt);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_URL_INFO_STAT_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 단축URL 통계 상세 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectUrlInfoStatDetail(Map<String, Object> params) throws Exception {
    	// 유입채널 조회
        List<Object> rtnList1 = generalDao.selectGernalList(DB.QRY_SELECT_URL_INFO_STAT_CHART1, params);

        // 타임라인(일) 조회
        List<Object> rtnList2 = generalDao.selectGernalList(DB.QRY_SELECT_URL_INFO_STAT_CHART2, params);

        // 타임라인(시간) 조회
//        List<Object> rtnList3 = generalDao.selectGernalList(DB.QRY_SELECT_URL_INFO_STAT_CHART3, params);

        Map<String, List<Object>> rtnMap = new HashMap<String, List<Object>>();
        rtnMap.put("chart1", rtnList1);
        rtnMap.put("chart2", rtnList2);
//        rtnMap.put("chart3", rtnList3);

        RestResult<Object> rtn = new RestResult<Object>();
        rtn.setData(rtnMap);

        return rtn;
    }

    /**
	 * 단축URL 생성
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> insertUrlInfo(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		// 단축 URL ID생성시 6자리 숫자/문자 랜덤 생성
		String urlId = SecureRandomStringUtils.randomAlphanumeric(6);
		params.put("urlId", urlId);

		int resultCnt = generalDao.insertGernal(DB.QRY_INSERT_URL_INFO, params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);

			// redis 테이블 처리
			commonService.updateCmCmdForRedis("CM_URL_INFO");
		}

		return rtn;
	}

	/**
	 * 단축URL 삭제
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> deleteUrlInfo(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();
		int resultCnt = generalDao.insertGernal(DB.QRY_DELETE_URL_INFO, params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);

			// redis 테이블 처리
			commonService.updateCmCmdForRedis("CM_URL_INFO");
		}

		return rtn;
	}
}
