package kr.co.uplus.cm.alarm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.alarm.service.RecipientService;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/alarm")
public class RecipientController {

	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private RecipientService recipientSvc;

	// 수신자 목록 조회
	@PostMapping("/selectRecipientList")
	public RestResult<?> selectRecipientList(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount("alarm.selectRecipientListCnt", params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList("alarm.selectRecipientList", params);
        rtn.setData(rtnList);

		return rtn;
	}

	// 수신자 목록 조회 (검색팝업용)
	@PostMapping("/selectRecipientList2")
	public RestResult<?> selectRecipientList2(@RequestBody Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("alarm.selectRecipientList2", params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	// 수신자 저장
	@PostMapping("/saveRecipient")
	public RestResult<?> saveRecipient(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		recipientSvc.saveRecipient(params);
		return rtn;
	}
	
	// 수신자 삭제 
	@PostMapping("/delRecipient")
	public RestResult<?> delRecipient(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		recipientSvc.delRecipient(params);
		return rtn;
	}

}
