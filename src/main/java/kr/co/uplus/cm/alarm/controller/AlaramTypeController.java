package kr.co.uplus.cm.alarm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/alarm")
public class AlaramTypeController {

	@Autowired
	private GeneralDao generalDao;

	// 알람타입 목록 조회
	@PostMapping("/selectAlarmTypeList")
	public RestResult<?> selectAlarmTypeList(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("alarm.selectAlarmTypeList", params);
		rtn.setData(rtnList);
		return rtn;
	}

	// 알람타입 조회
	@PostMapping("/selectAlarmType")
	public RestResult<?> selectAlarmType(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Object data = generalDao.selectGernalObject("alarm.selectAlarmType", params);
		rtn.setData(data);
		return rtn;
	}
}
