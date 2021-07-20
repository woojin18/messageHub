package kr.co.uplus.cm.alarm.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReceptGroupService {
	
	@Autowired GeneralDao generalDao;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
	public void saveReceptGroup(Map<String, Object> params) throws Exception {
		String recipientId = CommonUtils.getString(params.get("receptGroupId"));
		if (StringUtils.isEmpty(recipientId)) {
			generalDao.insertGernal("alarm.insertReceptGroup", params);
		} else {
			generalDao.updateGernal("noti.updateReceptGroup", params);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
	public void delReceptGroup(Map<String, Object> params) throws Exception {
		generalDao.deleteGernal("alarm.deleteReceptGroup", params);
	}

}
