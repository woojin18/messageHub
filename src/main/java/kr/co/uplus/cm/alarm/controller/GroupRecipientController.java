package kr.co.uplus.cm.alarm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.alarm.service.GroupRecipientService;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/alarm")
public class GroupRecipientController {

	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private GroupRecipientService groupRecipientSvc;

	// 수신그룹별수신자 목록 조회
	@PostMapping("/selectGroupRecipientList")
	public RestResult<?> selectGroupRecipientList(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("alarm.selectGroupRecipientList", params);
		rtn.setData(rtnList);
		return rtn;
	}

	// 수신그룹별수신자 목록 조회
	@PostMapping("/selectGroupRecipientList1")
	public RestResult<?> selectGroupRecipientList1(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("alarm.selectGroupRecipientList1", params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	// 수신그룹별수신자 저장
	@PostMapping("/saveGroupRecipient")
	public RestResult<?> saveGroupRecipient(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		groupRecipientSvc.saveGroupRecipient(params);
		return rtn;
	}
	
	// 수신그룹별수신자 삭제 
	@PostMapping("/delGroupRecipient")
	public RestResult<?> delGroupRecipient(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		groupRecipientSvc.delGroupRecipient(params);
		return rtn;
	}

}
