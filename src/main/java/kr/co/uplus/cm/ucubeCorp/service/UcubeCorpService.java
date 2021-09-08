package kr.co.uplus.cm.ucubeCorp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class UcubeCorpService {

	@Autowired GeneralDao generalDao;

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
	public Map<String, Object> ucubeTerminateCorp(Map<String, Object> params) throws Exception {
		Map<String, Object> rtn = new HashMap<String, Object>();
		String status = (CommonUtils.getString(params.get("status"))).toUpperCase();
		
		int cnt = generalDao.selectGernalCount("ucubeCorp.selectCorpListByRegNoCnt", params);
		if(cnt == 0) {
			rtn.put("code", "99998");
			rtn.put("message", "사업자 번호가 존재하지 않거나 이미 해지된 사업자 번호입니다.");
			
			return rtn;
		} else if(!"USE".equals(status) && !"STOP".equals(status) && !"DEL".equals(status)) {
			rtn.put("code", "99999");
			rtn.put("message", "상태값이 잘못되었습니다.(USE, STOP, DEL만 사용 가능합니다.");
			
			return rtn;
		} else {
			// 고객사 테이블 update
			generalDao.updateGernal("ucubeCorp.updateTerminateByRegNo", params);
			params.put("cmdTgt", "CM_CORP");
			generalDao.updateGernal("ucubeCorp.updateCmCmdForRedis", params);
			
			if("DEL".equals(status)) {
				List<Object> corpList = generalDao.selectGernalList("ucubeCorp.selectCorpListByRegNo", params);
				
				for(Object corpObj : corpList) {
					Map<String, Object> corpMap = (Map<String, Object>) corpObj;
					
					// 해지인 경우 api키 테이블 update
					generalDao.updateGernal("ucubeCorp.updateTerminateApikey", corpMap);
					params.put("cmdTgt", "CM_APIKEY");
					generalDao.updateGernal("ucubeCorp.updateCmCmdForRedis", params);
				}
			}
			rtn.put("code", "10000");
			rtn.put("message", "성공");
		}
		return rtn;
	}
}
