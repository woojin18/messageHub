package kr.co.uplus.cm.myPage.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.config.FileConfig;
import kr.co.uplus.cm.myPage.service.MyPageService;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.FileDownUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/myPage")
public class MyPageController {
	
	@Autowired
	private MyPageService myPageSvc;
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}
	
	/**
	 * 회원 정보
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectMemberInfo")
	public RestResult<?> selectMemberInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = myPageSvc.selectMemberInfo(params);
			
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	/**
	 * 전화번호 변경시 인증번호 생성
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/setCertifyNumber")
	public RestResult<?> setCertifyNumber(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			myPageSvc.setCertifyNumber(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
//			rtn.setMessage("인증번호 전송에 실패하였습니다.");
			rtn.setMessage(e.getMessage());
			log.error("{} Error : {}", this.getClass(), e);
		}
		
		return rtn;
	}
	
	/**
	 *  회원정보 저장
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/saveMemberInfo")
	public RestResult<?> saveMemberInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = myPageSvc.saveMemberInfo(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}

	/**
	 * 문의 내역 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectQnaList")
	public RestResult<?> selectQnaList(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = myPageSvc.selectQnaList(params);
			
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	/**
	 * 문의내역 등록 및 수정
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/saveQnaInfo")
	public RestResult<?> saveQnaInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = myPageSvc.saveQnaInfo(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	/**
	 * 문의 내역 삭제
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteQnaInfo")
	public RestResult<?> deleteQnaInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = myPageSvc.deleteQnaInfo(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	/**
	 * 비밀번호 확인
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/checkPassword")
	public RestResult<?> checkPassword(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = myPageSvc.checkPassword(params);
			
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	/**
	 * 고객사 정보
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectCorpInfo")
	public RestResult<?> selectCorpInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = myPageSvc.selectCorpInfo(params);
			
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("고객사 정보 조회에 실패하였습니다.");
		}
		
		return rtn;
	}
	
	/**
	 *  회원정보 저장
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/saveCorpInfo")
	public RestResult<?> saveCorpInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = myPageSvc.saveCorpInfo(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	@PostMapping("/selectProejctList")
	public RestResult<?> selectProejctList(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = myPageSvc.selectProejctList(params);
			
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}
		
		return rtn;
	}
	
	@PostMapping("/downloadRegCardImage")
	public ResponseEntity<Resource> downloadRegCardImage(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) throws Exception {
		
		Map<String, Object> fileInfo = myPageSvc.selectAttachFileInfo(params);
		String filePath = CommonUtils.getStrValue(fileInfo, "attachFilePath");
		String fileName = FileDownUtil.getFileNm(FileDownUtil.getBrowser(request), CommonUtils.getStrValue(fileInfo, "attachFileName"));
		
		if(StringUtils.indexOf(filePath, FileConfig.FileSvcType.BIZ_REG_CARD.getPath()) < 0) {
			String errorMsg = "unauthorized path access";
			log.error("{}.procDownloadLibraryFile Error : {}", this.getClass(), errorMsg);
			throw new Exception(errorMsg);
		}
		
		Path path = Paths.get(filePath);
		String contentType = Files.probeContentType(path);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
		
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
}
