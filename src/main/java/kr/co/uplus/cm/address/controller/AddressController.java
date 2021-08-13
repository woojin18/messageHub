package kr.co.uplus.cm.address.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.uplus.cm.address.service.AddressService;
import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.MultipartFileDTO;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.sendMessage.dto.RecvInfo;
import kr.co.uplus.cm.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/addressApi/manage")

public class AddressController {

	@Autowired
	private AddressService addressSvc;

	@Autowired
	private CommonService commonService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

	/**
	 * 주소카테고리그룹 리스트 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectAddressCateGrpList")
	public RestResult<?> selectAddressCateGrpList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = addressSvc.selectAddressCateGrpList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 주소리스트 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectAddressList")
	public RestResult<?> selectAddressList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = addressSvc.selectAddressList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 주소록 구성원 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectMemberList")
	public RestResult<?> selectMemberList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = addressSvc.selectMemberList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 주소록 등록
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/registerAddr")
	public RestResult<?> registerAddr(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = addressSvc.registerAddr(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 주소록 수정
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/modifyAddr")
	public RestResult<?> modifyAddr(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = addressSvc.modifyAddr(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 프로젝트 리스트 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectProjectList")
	public RestResult<?> selectProjectList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = addressSvc.selectProjectList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 수신자 목록 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectCmCuList")
	public RestResult<?> selectCmCuList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = addressSvc.selectCmCuList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 구성원 등록
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/registerMember")
	public RestResult<?> registerMember(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = addressSvc.registerMember(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 구성원 삭제
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteMember")
	public RestResult<?> deleteMember(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = addressSvc.deleteMember(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 수신자 목록 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectReceiverList")
	public RestResult<?> selectReceiverList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = addressSvc.selectReceiverList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

	/**
	 * 수신자 등록/수정
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/saveReceiver")
	public RestResult<?> saveReceiver(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = addressSvc.saveReceiver(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 수신자 목록 조회 엑셀다운로드
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = "/excelDownloadReceiverList")
	public ModelAndView excelDownloadReceiverList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {
		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", "수신자 리스트");
		map.put("colLabels", new String[] { "수신자명", "앱로그인ID", "휴대폰번호", "사용여부"});
		map.put("colIds", new String[] {"cuName", "cuid", "hpNumber", "useYnName"});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", addressSvc.excelDownloadReceiverList(params).getData());
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
		model.addObject("excelFileName", "receiverList_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);

		return model;
	}

	/**
	 * 수신자 엑셀업로드 템플릿
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = "/excelUploadTemplate")
	public ModelAndView excelUploadTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {
		List<String> colLabels = new ArrayList<String>();
		colLabels.add("수신자명");
		colLabels.add("앱로그인ID");
		colLabels.add("휴대폰번호");
		
		List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetTitle", "수신자 리스트");
		map.put("colLabels", colLabels.toArray(new String[0]));
		map.put("colIds", new String[] {});
		map.put("numColIds", new String[] {});
		map.put("figureColIds", new String[] {});
		map.put("colDataList", new ArrayList<T>());
		sheetList.add(map);

		ModelAndView model = new ModelAndView("commonXlsxView");
		model.addObject("excelFileName", "ReceiverTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		model.addObject("sheetList", sheetList);

		return model;
	}
	/**
	 * 수신자 엑셀 업로드
	 * @param request
	 * @param response
	 * @param multipartFileDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path="/excelUploadReceiver")
		public RestResult<?> excelUploadReceiver(HttpServletRequest request, HttpServletResponse response
				, @ModelAttribute MultipartFileDTO multipartFileDTO) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			params = commonService.setUserInfo(multipartFileDTO.getParams());
			rtn = addressSvc.registerReceiverExcel(params, multipartFileDTO.getFile());
			log.info("{}.excelUploadReceiver Start ====> params : {}", this.getClass(), params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{}.excelUploadReceiver Error : {}", this.getClass(), e);
			return rtn;
		}
		return rtn;
	}

	/**
	 * 수신자 삭제
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteReceiver")
	public RestResult<?> deleteReceiver(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = addressSvc.deleteReceiver(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	/**
	 * 주소카테고리 등록/수정
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/saveAddressCategory")
	public RestResult<?> saveAddressCategory(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = addressSvc.saveAddressCategory(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	/**
	 * 주소 카테고리 삭제
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteAddressCategory")
	public RestResult<?> deleteAddressCategory(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = addressSvc.deleteAddressCategory(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}

		return rtn;
	}
	
	// 주소록 삭제
	@PostMapping("/deleteAddress")
	public RestResult<?> deleteAddress(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		try {
			rtn = addressSvc.deleteAddress(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}

		return rtn;
	}
	
}
