package kr.co.uplus.cm.alarm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.alarm.service.ReceptGroupService;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/alarm")
public class ReceptGroupController {

	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private ReceptGroupService receptGroupSvc;

	// 수신그룹 목록 조회
	@PostMapping("/selectReceptGroupList")
	public RestResult<?> selectReceptGroupList(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("alarm.selectReceptGroupList", params);
		rtn.setData(rtnList);
		return rtn;
	}

	// 수신그룹 목록 조회(Select박스용)
	@PostMapping("/selectReceptGroupList2")
	public RestResult<?> selectReceptGroupList2(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("alarm.selectReceptGroupList2", params);
		rtn.setData(rtnList);
		return rtn;
	}

	// 수신자별 수신그룹 목록 조회(Select박스용)
	@PostMapping("/selectReceptGroupList3")
	public RestResult<?> selectReceptGroupList3(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("alarm.selectReceptGroupList3", params);
		rtn.setData(rtnList);
		return rtn;
	}

	// 수신자별 수신그룹 목록 조회(알람관리-알람수신팝업용)
	@PostMapping("/selectReceptGroupList4")
	public RestResult<?> selectReceptGroupList4(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("alarm.selectReceptGroupList4", params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	// 수신그룹 저장
	@PostMapping("/saveReceptGroup")
	public RestResult<?> saveReceptGroup(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		receptGroupSvc.saveReceptGroup(params);
		return rtn;
	}
	
	// 수신그룹 삭제 
	@PostMapping("/delReceptGroup")
	public RestResult<?> delReceptGroup(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		receptGroupSvc.delReceptGroup(params);
		return rtn;
	}

}
