package kr.co.uplus.cm.statisticsUser.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class StatisticsUserService {
	@Autowired
	private GeneralDao generalDao;

	@Autowired
	private CommonService commonService;

	/**
	 * 일별월별 전송건수 및 비율 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectSendByList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_STATIS_SND_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	/**
	 * 일별월별 전송건수 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectSendByCntList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = null;
		
		String dateStatus = (String)params.get("dateStatus");
		
		if("DAY".equals(dateStatus)) {
			rtnList	= generalDao.selectGernalList(DB.QRY_SELECT_STATIS_SND_CNT_LIST, params);
		} else {
			rtnList	= generalDao.selectGernalList(DB.QRY_SELECT_STATIS_SND_MONTH_CNT_LIST, params);
		}
		rtn.setData(rtnList);
		return rtn;
	}
}
