package kr.co.uplus.cm.statisticsAdmin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class StatisticsAdminService {
	@Autowired
	private GeneralDao generalDao;

	@Autowired
	private CommonService commonService;

	/**
	 * 일별 전송건수 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectStatisList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = null;
		rtnList	= generalDao.selectGernalList(DB.QRY_SELECT_STATIS_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	/**
	 * 프로젝트 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectProjectList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_STATIS_PROJECT_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}
}
