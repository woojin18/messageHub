package kr.co.uplus.cloud.template.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cloud.sample.dto.RestResult;
import kr.co.uplus.cloud.template.service.TemplateService;

/**
 * <pre>
 * TemplateController
 * 각 채널별 템플릿 생성 및 리스트 관리 Controller
 * </pre>
 *
 * @author : liam (Lee Sung Min)
 * @Date : 2021.03.25.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@RestController
@RequestMapping("/api/public/template")
public class TemplateController {

	@Autowired
	private TemplateService tmpltSvc;

	/**
	 * 푸시 템플릿 리스트 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectPushTmpltList")
	public RestResult<?> selectPushTemplateList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = tmpltSvc.selectPushTemplateList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}

		return rtn;
	}

	/**
	 * 푸시 템플릿 단건 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectPushTmpltInfo")
    public RestResult<?> selectPushTmpltInfo(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = tmpltSvc.selectPushTemplateList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        }

        return rtn;
    }

	/**
	 * 푸시 템플릿 저장처리
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/savePushTmplt")
    public RestResult<?> savePushTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();

        try {
            rtn = tmpltSvc.savePushTemplate(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        }
        return rtn;
    }

	/**
	 * RCS 템플릿 리스트 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectRcsTmpltList")
	public RestResult<?> selectRcsTemplateList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = tmpltSvc.selectRcsTemplateList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}

		return rtn;
	}

	/**
	 * RCS 템플릿 등록처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/addRcsTmplt")
	public RestResult<?> addRcsTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = tmpltSvc.addRcsTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}

	/**
	 * RCS 템플릿 수정처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/updateRcsTmplt")
	public RestResult<?> updateRcsTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = tmpltSvc.updateRcsTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}

	/**
	 * 알림톡 템플릿 리스트 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectKakaoTalkTmpltList")
	public RestResult<?> selectKakaoTalkTemplateList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = tmpltSvc.selectKakaoTalkTemplateList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}

		return rtn;
	}

	/**
	 * 알림톡 템플릿 등록처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/addKakaoTalkTmplt")
	public RestResult<?> addKakaoTalkTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = tmpltSvc.addKakaoTalkTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}

	/**
	 * 알림톡 템플릿 수정처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/updateKakaoTalkTmplt")
	public RestResult<?> updateKakaoTalkTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = tmpltSvc.updateKakaoTalkTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}

	/**
	 * 친구톡 템플릿 리스트 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectFriendTalkTmpltList")
	public RestResult<?> selectFriendTalkTemplateList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = tmpltSvc.selectFriendTalkTemplateList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}

		return rtn;
	}

	/**
	 * 친구톡 템플릿 등록처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/addFriendTalkTmplt")
	public RestResult<?> addFriendTalkTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = tmpltSvc.addFriendTalkTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}

	/**
	 * 친구톡 템플릿 수정처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/updateFriendTalkTmplt")
	public RestResult<?> updateFriendTalkTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = tmpltSvc.updateFriendTalkTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}

	/**
	 * SMS/MMS 템플릿 리스트 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectSmsTmpltList")
	public RestResult<?> selectSmsTemplateList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = tmpltSvc.selectSmsTemplateList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}

		return rtn;
	}

	/**
	 * SMS/MMS 템플릿 등록처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/addSmsTmplt")
	public RestResult<?> addSmsTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = tmpltSvc.addSmsTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}

	/**
	 * SMS/MMS 템플릿 수정처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/updateSmsTmplt")
	public RestResult<?> updateSmsTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = tmpltSvc.updateSmsTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}

	/**
	 * 통합발송 템플릿 리스트 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectMultiSendTmpltList")
	public RestResult<?> selectMultiSendTemplateList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = tmpltSvc.selectMultiSendTemplateList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}

		return rtn;
	}

	/**
	 * 통합발송 템플릿 등록처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/addMultiSendTmplt")
	public RestResult<?> addMultiSendTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = tmpltSvc.addMultiSendTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}

	/**
	 * 통합발송 템플릿 수정처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/updateMultiSendTmplt")
	public RestResult<?> updateMultiSendTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = tmpltSvc.updateMultiSendTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}

	/**
	 * 스마트발송 템플릿 리스트 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectSmartTmpltList")
	public RestResult<?> selectSmartTemplateList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = tmpltSvc.selectSmartTemplateList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}

		return rtn;
	}

	/**
	 * 스마트발송 템플릿 등록처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/addSmartTmplt")
	public RestResult<?> addSmartTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = tmpltSvc.addSmartTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}

	/**
	 * 스마트발송 템플릿 수정처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/updateSmartTmplt")
	public RestResult<?> updateSmartTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = tmpltSvc.updateSmartTemplate(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		return rtn;
	}

}
