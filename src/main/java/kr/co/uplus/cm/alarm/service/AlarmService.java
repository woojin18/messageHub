package kr.co.uplus.cm.alarm.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AlarmService {
	
	@Autowired GeneralDao generalDao;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
	public void saveAlarm(Map<String, Object> params) throws Exception {
		String alarmId = CommonUtils.getString(params.get("alarmId"));
		
        ObjectMapper om = new ObjectMapper();
        
		params.put("coltDayWeekJson", om.writeValueAsString(params.get("coltDayWeek")));
		params.put("dayLimitJson", om.writeValueAsString(params.get("dayLimit")));
		params.put("mthLimitJson", om.writeValueAsString(params.get("mthLimit")));
		
		if (StringUtils.isEmpty(alarmId)) {
			generalDao.insertGernal("alarm.insertAlarm", params);
		} else {
			generalDao.updateGernal("alarm.updateAlarm", params);
		}

		generalDao.deleteGernal("alarm.deleteCollectTargetApi", params);
		generalDao.deleteGernal("alarm.deleteAlarmReceptGroup", params);
		generalDao.deleteGernal("alarm.deleteAlarmRecipient", params);
		List list1 = (List) params.get("list1");
		if (list1 != null && list1.size() > 0) {
			generalDao.insertGernal("alarm.insertCollectTargetApi", params);
		}
		List list2 = (List) params.get("list2");
		if (list2 != null && list2.size() > 0) {
			generalDao.insertGernal("alarm.insertAlarmReceptGroup", params);
		}
		List list3 = (List) params.get("list3");
		if (list3 != null && list3.size() > 0) {
			generalDao.insertGernal("alarm.insertAlarmRecipient", params);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
	public void delAlarm(Map<String, Object> params) throws Exception {
		generalDao.deleteGernal("alarm.deleteCollectTargetApi", params);
		generalDao.deleteGernal("alarm.deleteAlarmReceptGroup", params);
		generalDao.deleteGernal("alarm.deleteAlarmRecipient", params);
		generalDao.deleteGernal("alarm.deleteAlarm", params);
	}

}
