package kr.co.uplus.cm.ucubeCorp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.ucubeCorp.service.UcubeCorpService;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class UcubeCorpController {

	@Autowired
	private UcubeCorpService corpSvc;

	// 유큐브 미납 고객사 해지
	@ResponseBody
	@GetMapping("/api/public/ucubeTerminateCorp/{regNo}/{status}")
	public Map<String, Object> ucubeTerminateCorp(@PathVariable("regNo") String regNo,
			@PathVariable("status") String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> rtn = new HashMap<String, Object>();
		params.put("regNo", regNo);
		params.put("status", status);
		params.put("delReason", "미납 해지");

		try {
			rtn = corpSvc.ucubeTerminateCorp(params);
		} catch (Exception e) {
			rtn.put("message", "오류가 발생하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
}
