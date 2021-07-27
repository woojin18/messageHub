package kr.co.uplus.cm.use.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kong.unirest.json.JSONArray;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;
@Log4j2
@Service
public class UseHistoryService {

	@Autowired
	private GeneralDao generalDao;

	@Autowired
	private CommonService commonService;
	
	/**
	 * 이용내역 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectUseHistory(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> rtnObj = new HashMap<String, Object>();

		// 금액 관련 변수 선언
		BigDecimal sumAmount = new BigDecimal(0);
		BigDecimal addAmount = new BigDecimal(0);
		BigDecimal totalSumAmount = new BigDecimal(0);
		BigDecimal totalTax = new BigDecimal(0);
		String payType = "";
		BigDecimal prepaidAmount = new BigDecimal(0);
		BigDecimal postpaidAmount = new BigDecimal(0);
		BigDecimal prepaidTaxAmount = new BigDecimal(0);
		BigDecimal postpaidTaxAmount = new BigDecimal(0);

		Map<String, Object> rtnMap = null;
		JSONParser parser = new JSONParser();
		Object obj = null;
		org.json.simple.JSONObject jsonObj = null;

		String rcsYn	= "";
		String smsmmsYn	= "";
		String pushYn	= "";
		String kakaoYn	= "";
		String moYn		= "";
		String useChStr = "";

		if(params.containsKey("pageNo")
				&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize")
				&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_USE_HISTORY_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_USE_HISTORY_LIST, params);

		for (int i = 0; i < rtnList.size(); i++) {
			// 사용 채널 그룹 한라인으로 처리
			rtnMap		= (Map<String, Object>) rtnList.get(i);
			obj			= parser.parse( CommonUtils.getString(rtnMap.get("useChGrpInfo")) );
			jsonObj		= (org.json.simple.JSONObject) obj;
			rcsYn		= CommonUtils.getString(jsonObj.get("RCS"));
			smsmmsYn	= CommonUtils.getString(jsonObj.get("SMS/MMS"));
			pushYn		= CommonUtils.getString(jsonObj.get("PUSH"));
			kakaoYn		= CommonUtils.getString(jsonObj.get("KKO"));
			moYn		= CommonUtils.getString(jsonObj.get("MO"));
			useChStr	= "";

			if( "Y".equals(rcsYn) )		useChStr += "RCS,";
			if( "Y".equals(smsmmsYn) )	useChStr += "SMS/MMS,";
			if( "Y".equals(pushYn) )	useChStr += "PUSH,";
			if( "Y".equals(kakaoYn) )	useChStr += "KAKAO,";
			if( "Y".equals(moYn) )		useChStr += "MO,";
			
			if( useChStr.length() > 0 ) {
				useChStr = useChStr.substring(0, useChStr.length()-1);
			}

			rtnMap.put("useCh", useChStr);
			payType = CommonUtils.getString(rtnMap.get("payType"));

			// 요금 총합계
			addAmount = new BigDecimal(CommonUtils.getString(rtnMap.get("sumChGrpAmount"))); //프로젝트 개별 금액
			sumAmount = sumAmount.add(addAmount); // 프로젝트 합계금액

			// 선불,후불요금 합계
			if(payType.equalsIgnoreCase("PRE")) {
				prepaidAmount = prepaidAmount.add(addAmount); // 선불요금
			} else if(payType.equalsIgnoreCase("POST")) {
				postpaidAmount = postpaidAmount.add(addAmount); // 후불요금
			}
		}

		prepaidTaxAmount = prepaidAmount.multiply(new BigDecimal("0.10"));
		postpaidTaxAmount = postpaidAmount.multiply(new BigDecimal("0.10"));
		totalTax = sumAmount.multiply(new BigDecimal("0.10")); // 부가세 10%
		totalSumAmount = sumAmount.add(totalTax); // 합계금액 + 부가세

		rtnObj.put("totalSumAmount"		, totalSumAmount.setScale(0, RoundingMode.FLOOR).toString());
		rtnObj.put("sumAmount"			, sumAmount.setScale(0, RoundingMode.FLOOR).toString());
		rtnObj.put("prepaidAmount"		, prepaidAmount.setScale(0, RoundingMode.FLOOR).toString());
		rtnObj.put("postpaidAmount"		, postpaidAmount.setScale(0, RoundingMode.FLOOR).toString());
		rtnObj.put("prepaidTaxAmount"	, prepaidTaxAmount.setScale(0, RoundingMode.FLOOR).toString());
		rtnObj.put("postpaidTaxAmount"	, postpaidTaxAmount.setScale(0, RoundingMode.FLOOR).toString());
		rtnObj.put("useHistoryList"		, rtnList);
		
		
		rtn.setData(rtnObj);
		return rtn;
	}
	
	/**
	 * 고객별 상품 단가관리 상세
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public RestResult<?> selectCorpProductUnitDetail(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
//		cm_console.CM_CORP_PRODUCT_UNIT에 해당 고객사id 존재 여부 확인
		int isExists = generalDao.selectGernalCount(DB.QRY_SELECT_ISEXISTS_CORP_PRODUCTUNIT, params);
		paramMap.put("isExists", isExists);
		paramMap.putAll(params);
		
		List<Object> rcsList = new ArrayList<Object>();
		List<Object> pushList = new ArrayList<Object>();
		List<Object> smsmmsList = new ArrayList<Object>();
		List<Object> kkoList = new ArrayList<Object>();
		
		list = generalDao.selectGernalList(DB.QRY_SELECT_CORP_PRODUCTUNIT, paramMap);
		for(Object map : list) {
			Map<String, Object> listMap = (Map<String, Object>) map;
			if(listMap.get("smartChProductYn").equals("Y")) {
				listMap.replace("smartChProductYn", "스마트");
			}else {
				listMap.replace("smartChProductYn", "기본");
			}
			
			String tobeFeeInfo = CommonUtils.getString(listMap.get("tobeFeeInfo"));
			if(tobeFeeInfo != null && !"".equals(tobeFeeInfo)) {
				int cnt = 0;
				JSONArray jsonList = new JSONArray(tobeFeeInfo);
				for(Object obj : jsonList) {
					Map<String, Object> rtnMap = new HashMap<String, Object>();
					kong.unirest.json.JSONObject jsonObj = (kong.unirest.json.JSONObject) obj;
					rtnMap.put("tobeFee", jsonObj.get("POST_FEE"));			// 후불 요금
					rtnMap.put("orgTobeFee", jsonObj.get("POST_FEE"));		// 변경 가격 비교를 위한 후불 요금
					rtnMap.put("feeCnt", jsonObj.get("FEE_START_CNT") + " ~ " + jsonObj.get("FEE_END_CNT"));	// 후불 과금
					rtnMap.put("feeStartCnt", jsonObj.get("FEE_START_CNT"));
					rtnMap.put("feeEndCnt", jsonObj.get("FEE_END_CNT"));
					rtnMap.put("tobeInfoLength", jsonList.length());		// 후불 요금 jsonlist length
					rtnMap.put("tobeInfoIdx", cnt);							// 후불 요금 index
					rtnMap.putAll(listMap);
					if( "RCS".equals(listMap.get("ch")) ) {
						rcsList.add(rtnMap);
					} else if( "PUSH".equals(listMap.get("ch")) ) {
						pushList.add(rtnMap);
					} else if( "SMS".equals(listMap.get("ch")) || "MMS".equals(listMap.get("ch")) ) {
						smsmmsList.add(rtnMap);
					} else if( "ALIMTALK".equals(listMap.get("ch")) || "FRIENDTALK".equals(listMap.get("ch")) ) {
						kkoList.add(rtnMap);
					}
					//rtnList.add(rtnMap);
					cnt++;
				}
			
			} else {
				listMap.put("tobeFee", "");
				listMap.put("orgTobeFee", "");
				listMap.put("feeCnt", "");
				listMap.put("feeStartCnt", "");
				listMap.put("feeEndCnt", "");
				listMap.put("tobeInfoLength", 0);
				listMap.put("tobeInfoIdx", 0);
				if( "RCS".equals(listMap.get("ch")) ) {
					rcsList.add(listMap);
				} else if( "PUSH".equals(listMap.get("ch")) ) {
					pushList.add(listMap);
				} else if( "SMS".equals(listMap.get("ch")) || "MMS".equals(listMap.get("ch")) ) {
					smsmmsList.add(listMap);
				} else if( "ALIMTALK".equals(listMap.get("ch")) || "FRIENDTALK".equals(listMap.get("ch")) ) {
					kkoList.add(listMap);
				}
				//rtnList.add(listMap);
			}
		}
		
		returnMap.put("rcsList", rcsList);
		returnMap.put("pushList", pushList);
		returnMap.put("smsmmsList", smsmmsList);
		returnMap.put("kkoList", kkoList);
		
		rtn.setData(returnMap);
		
		return rtn;
	}
	
	/**
	 * 최근 6개월 이용건수 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectSixMonthUseCount(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> rtnObj = new HashMap<String, Object>();

		// 이용건수추이
		List<Object> sixMonthUseCnt = generalDao.selectGernalList(DB.QRY_SELECT_SIX_MONTH_USE_CNT, params);
		// 이용서비스별추이
		List<Object> sixMonthChanUseCnt = generalDao.selectGernalList(DB.QRY_SELECT_SIX_MONTH_CHAN_USE_CNT, params);

		rtnObj.put("sixMonthUseCnt", sixMonthUseCnt);
		rtnObj.put("sixMonthChanUseCnt", sixMonthChanUseCnt);
		rtn.setData(rtnObj);
		return rtn;
	}
	
	/**
	 * 최근 6개월 선불 결제금액 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectSixMonthPrePayAmtList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_SIX_MONTH_PRE_PAY_AMT, params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	/**
	 * 최근 6개월 후불 청구금액(이용금액포함) 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectSixMonthDefPayAmt(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_SIX_MONTH_DEF_PAY_AMT, params);
		rtn.setData(rtnList);
		return rtn;
	}
}
