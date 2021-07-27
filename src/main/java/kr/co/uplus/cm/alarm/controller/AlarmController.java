package kr.co.uplus.cm.alarm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.uplus.cm.alarm.service.AlarmService;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/alarm")
public class AlarmController {

	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private AlarmService alarmSvc;

	// 알람타입 목록 조회
	@PostMapping("/selectAlarmList")
	public RestResult<?> selectAlarmList(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("alarm.selectAlarmList", params);
		rtn.setData(rtnList);
		return rtn;
	}

	// 알람타입 조회
	@PostMapping("/selectAlarm")
	public RestResult<?> selectAlarm(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map data = (Map) generalDao.selectGernalObject("alarm.selectAlarm", params);

        ObjectMapper om =new ObjectMapper();
        
		String coltDayWeekJson = (String) data.get("coltDayWeekJson");
		data.put("coltDayWeek", om.readValue(coltDayWeekJson, HashMap.class));
		String dayLimitJson = (String) data.get("dayLimitJson");
		data.put("dayLimit", om.readValue(dayLimitJson, HashMap.class));
		String mthLimitJson = (String) data.get("mthLimitJson");
		data.put("mthLimit", om.readValue(mthLimitJson, HashMap.class));
		
		data.put("list1", generalDao.selectGernalList("alarm.selectCollectTargetApiList", params));
		data.put("list2", generalDao.selectGernalList("alarm.selectAlarmReceptGroupList", params));
		data.put("list3", generalDao.selectGernalList("alarm.selectAlarmRecipientList", params));
		
		rtn.setData(data);
		return rtn;
	}

	// APIKEY 목록 조회
	@PostMapping("/selectApikeyList")
	public RestResult<?> selectApikeyList(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("alarm.selectApikeyList", params);
		rtn.setData(rtnList);
		return rtn;
	}

	// 수집 대상 APIKEY 목록 조회
	@PostMapping("/selectCollectTargetApiList")
	public RestResult<?> selectCollectTargetApiList(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("alarm.selectCollectTargetApiList", params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	// 알람 저장
	@PostMapping("/saveAlarm")
	public RestResult<?> saveRecipient(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		alarmSvc.saveAlarm(params);
		return rtn;
	}
	
	// 알람 삭제 
	@PostMapping("/delAlarm")
	public RestResult<?> delAlarm(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		alarmSvc.delAlarm(params);
		return rtn;
	}
}
