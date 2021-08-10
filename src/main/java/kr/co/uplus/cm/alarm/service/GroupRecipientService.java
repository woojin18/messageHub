package kr.co.uplus.cm.alarm.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class GroupRecipientService {
	
	@Autowired GeneralDao generalDao;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
	public void saveGroupRecipient(Map<String, Object> params) throws Exception {
		generalDao.insertGernal("alarm.insertGroupRecipient", params);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
	public void delGroupRecipient(Map<String, Object> params) throws Exception {
		generalDao.deleteGernal("alarm.deleteGroupRecipient", params);
	}

}
