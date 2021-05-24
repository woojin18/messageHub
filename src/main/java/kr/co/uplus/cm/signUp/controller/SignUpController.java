package kr.co.uplus.cm.signUp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.signUp.service.SignUpService;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/public/signUp")
public class SignUpController {
	
	@Autowired GeneralDao generalDao;
	
	@Autowired SignUpService signUpSvc;
	
	@Autowired ApiInterface apiInterface;
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

	// 회원
	@RequestMapping("test")
	public RedirectView test (RedirectAttributes redirect, HttpServletResponse response, HttpServletRequest req) {
		req.setAttribute("test", "test");
		return new RedirectView("http://localhost:3000/signUpMain");
	}
	
	// 도메인 이름 체크
	@PostMapping("/domainChk")
	public int domainChk(@RequestBody Map<String, Object> params) throws Exception {
		return generalDao.selectGernalCount(DB.QRY_SELECT_DOMAIN_CHK_COUNT, params);
	}
	
	// 인증 메일 발송 후 cm_user insert
	@PostMapping("/insertEmailUser")
	public RestResult<?> insertEmailUser(@RequestBody Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		String userId = CommonUtils.getCommonId("SIGN", 5);
		
		
		params.put("userId", userId);
		params.put("regId", userId);
		
		try {
			signUpSvc.insertEmailUser(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("회원 가입에 실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	// 회원 가입
	@PostMapping("/insertSignUp")
	public RestResult<?> insertSignUp(@RequestBody Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			signUpSvc.insertSignUp(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("회원 가입에 실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	// 사용 약관 가져오기
	@PostMapping("/selectUseTerms")
	public RestResult<?> selectUseTerms(@RequestBody Map<String, Object> params) throws Exception {
		return signUpSvc.selectUseTerms(params);
	}
	
	// api 통신 테스트
	@PostMapping("/apiTest")
	public void apiTest () throws Exception {
		
		// get 예제 브랜드 카테고리 조회
		Map<String, Object> getHeaderMap = new HashMap<String, Object>();
		getHeaderMap.put("apiId", "111");
		getHeaderMap.put("apiSercret", "111");
		getHeaderMap.put("svcId", "WEB01");
		Map<String, Object> getResult = apiInterface.get("/console/v1/brand/categories", getHeaderMap);
		
		// post 예제 카카오 알림톡 발송 API
		Map<String, Object> postHeaderMap = new HashMap<String, Object>();
		String svcId = "WEB01";
		postHeaderMap.put("apiKey", "111");
		postHeaderMap.put("svcId", "WEB01");
		
		Map<String, Object> postParamMap = new HashMap<String, Object>();
		postParamMap.put("callback" , "15441234");
		
		Map<String, Object> postResult = apiInterface.post("/console/v1/alimtalk/" + svcId, postParamMap, postHeaderMap);
	}
	
}
 