package kr.co.uplus.cm.rcsTemplate.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class RcsTemplateService {
	
	@Autowired
	private GeneralDao generalDao;

	public RestResult<Object> selectRcsTemplateList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		String inputTag =  CommonUtils.getString(params.get("inputTag"));
		String searchTag = CommonUtils.getString(params.get("searchTag"));
		
		// input 값이 있으면 해당 searchTag의 값을 구분해서 파라미터를 다시 세팅
		if(!"".equals(inputTag)) {
			switch(searchTag) {
			case "temNm": params.put("temNm", inputTag);
				break;
			case "temId": params.put("temId", inputTag);
				break;
			case "brandNm": params.put("brandNm", inputTag);
				break;
			default: break;
			}
		}
		

		if(params.containsKey("pageNo") && CommonUtils.isNotEmptyObject(params.get("pageNo")) && params.containsKey("listSize") && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_RCS_TEMPLATE_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_TEMPLATE_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

	public RestResult<Object> corpBrandCnt(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		int resultCnt = generalDao.selectGernalCount(DB.QRY_SELECT_CORP_BRAND_CNT, params);
		
		rtn.setData(resultCnt);
		
		return rtn;
	}

}
