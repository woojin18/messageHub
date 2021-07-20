package kr.co.uplus.cm.alarm.service;

import java.util.List;
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
public class RecipientService {
	
	@Autowired GeneralDao generalDao;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
	public void saveRecipient(Map<String, Object> params) throws Exception {
		String recipientId = CommonUtils.getString(params.get("recipientId"));
		if (StringUtils.isEmpty(recipientId)) {
			generalDao.insertGernal("alarm.insertRecipient", params);
		} else {
			generalDao.updateGernal("alarm.updateRecipient", params);
		}
		generalDao.deleteGernal("alarm.deleteGroupRecipient2", params);
		List list = (List) params.get("receptGroups");
		if (list != null && list.size() > 0) {
			generalDao.deleteGernal("alarm.insertGroupRecipient2", params);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
	public void delRecipient(Map<String, Object> params) throws Exception {
		generalDao.deleteGernal("alarm.deleteRecipient", params);
		generalDao.deleteGernal("alarm.deleteGroupRecipient2", params);
	}

}
