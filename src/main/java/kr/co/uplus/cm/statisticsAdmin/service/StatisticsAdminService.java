package kr.co.uplus.cm.statisticsAdmin.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.utils.CommonUtils;
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
		
		String searchDateType = CommonUtils.getString(params.get("searchDateType"));
		String searchEndDate = CommonUtils.getString(params.get("searchEndDate"));
		String searchEndMonth = CommonUtils.getString(params.get("searchEndMonth"));
		
		Date date = new Date();
		
		SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatMonth = new SimpleDateFormat("yyyy-MM");
		
		// 검색 날짜가 '오늘' 또는 '이번달'이 포함이 되는경우 CH_STAT_CH_MIN TABLE을 UNION 처리 함
		if("DAY".equals(searchDateType)) {
			String day = formatDay.format(date); 
			if(day.equals(searchEndDate)) {
				params.put("chMin", "chMin");
				params.put("searchMin", formatDay);
			}
		} else {
			String month = formatMonth.format(date);
			if(month.equals(searchEndMonth)) {
				params.put("chMin", "chMin");
				params.put("searchMin", formatMonth);
			}
		}
		
		// 서비스 검색조건 분기처리
		String searchChanType = CommonUtils.getString(params.get("searchChanType"));
		if("SMSMMS".equals(searchChanType)) {
			ArrayList<Object> chArr = new ArrayList<>();
			boolean sms = (boolean) params.get("sms");
			boolean lms = (boolean) params.get("lms");
			boolean mms = (boolean) params.get("mms");
			if(sms) {
				chArr.add("SMS");
			}
			if(lms) {
				chArr.add("LMS");
			}
			if(mms) {
				chArr.add("MMS");
			}
			params.put("chArr", chArr);
		} else if ("RCS".equals(searchChanType)) {
			ArrayList<Object> chArr = new ArrayList<>();
			boolean rcsSms = (boolean) params.get("rcsSms");
			boolean rcsLms = (boolean) params.get("rcsLms");
			boolean rcsMms = (boolean) params.get("rcsMms");
			boolean rcsTpl = (boolean) params.get("rcsTpl");
			if(rcsSms) {
				chArr.add("RSMS");
			}
			if(rcsLms) {
				chArr.add("RLMS");
			}
			if(rcsMms) {
				chArr.add("RMMS");
			}
			if(rcsTpl) {
				chArr.add("RTPL");
			}
			params.put("chArr", chArr);
		} else if("KAKAO".equals(searchChanType)) {
			ArrayList<Object> chArr = new ArrayList<>();
			boolean friendTalk = (boolean) params.get("friendTalk");
			boolean alimTalk = (boolean) params.get("alimTalk");
			if(friendTalk) {
				chArr.add("FRIENDTALK");
			}
			if(alimTalk) {
				chArr.add("ALIMTALK");
			}
			params.put("chArr", chArr);
		} else if("PUSH".equals(searchChanType)) {
			ArrayList<Object> chArr = new ArrayList<>();
			chArr.add("PUSH");
			params.put("chArr", chArr);
		} else if("MO".equals(searchChanType)) {
			ArrayList<Object> chArr = new ArrayList<>();
			boolean smsMo = (boolean) params.get("smsMo");
			boolean lmsMo = (boolean) params.get("lmsMo");
			boolean mmsMo = (boolean) params.get("mmsMo");
			if(smsMo) {
				chArr.add("SMSMO");
			}
			if(lmsMo) {
				chArr.add("LMSMO");
			}
			if(mmsMo) {
				chArr.add("MMSMO");
			}
			params.put("chArr", chArr);
		}
		
		
		// 월별/일별 검색 조건에 따른 쿼리 분기처리
		// 카카오 통계는 PRODUCT_CODE 가 기준이 아니고 CH가 기준이 되서 쿼리 분기 처리
		List<Object> rtnList = null;
		if("KAKAO".equals(searchChanType)) {
			if("DAY".equals(searchDateType)) {
				rtnList	= generalDao.selectGernalList(DB.QRY_SELECT_STATIS_LIST_DAY_KKO, params);
			} else {
				rtnList	= generalDao.selectGernalList(DB.QRY_SELECT_STATIS_LIST_MONTH_KKO, params);
			}
		} else {
			if("DAY".equals(searchDateType)) {
				rtnList	= generalDao.selectGernalList(DB.QRY_SELECT_STATIS_LIST_DAY, params);
			} else {
				rtnList	= generalDao.selectGernalList(DB.QRY_SELECT_STATIS_LIST_MONTH, params);
			}
		}
		
		
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
