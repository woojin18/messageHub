package kr.co.uplus.cm.common.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.MultipartFileDTO;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/commonApi")
public class CommonController {

    @Autowired
    private CommonService commonService;
    
	@Autowired
	private GeneralDao generalDao;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
    }

    // 이미지 업로드
    @PostMapping("/uploadImage")
    public RestResult<?> uploadImage(
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute MultipartFileDTO multipartFileDTO) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        try {
            rtn = commonService.uploadImgFile(multipartFileDTO.getFile()
                    , commonService.setUserInfo(multipartFileDTO.getParams()));
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("파일등록에 실패하였습니다.");
            log.error("{}.uploadImage Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    // 고객사별 이미지 조회
    @PostMapping("/selectImageList")
    public RestResult<?> selectImageFile(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = commonService.selectImageList(params);
        } catch(Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectImageFile Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    // 고객사별 이미지 삭제
    @PostMapping("/deleteImageFile")
    public RestResult<?> deleteImageFile(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = commonService.deleteImageFile(params);
        } catch(Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.deleteImageFile Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 이미지 URL 정보 조회
     * @param params
     * @return
     */
    @PostMapping("/selectImageUrlInfo")
    public RestResult<?> selectImageUrlInfo(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = commonService.selectImageUrlInfo(params);
        } catch(Exception e) {
            rtn.setFail("실패하였습니다.");
            log.error("{}.selectImageUrlInfo Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    // 파일 업로드
    // 사용체널, 이미지 확장자 등의 문제가 있어서 파일 업로드 따로 땀
    @PostMapping("/uploadFile")
    public RestResult<?> uploadFile(
            @RequestParam MultipartFile uploadFile,
            @RequestParam String loginId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        try {
            rtn = commonService.uploadFile(uploadFile, loginId);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("파일등록에 실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 코드값 조회
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping("/selectCodeList")
	public RestResult<?> selectCodeList(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = commonService.selectCodeList(params);
		} catch(Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		}

		return rtn;
	}

    // 파일 다운로드
	//filepath를 그대로 사용할 경우 보안 이슈가 있을 수 있다.
	//FILE_ID 로 가져와서 다운로드 하는 방식으로 변경해야될듯하다.
    @PostMapping("/file/downloadFile")
    public ResponseEntity<Resource> downloadFile(
            @RequestBody Map<String, Object> params,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        log.info("{}.downloadFile - params : {}", this.getClass(), params);
        
//        FILE_ID로 ATTACH_FILE_PATH 조회
        String filePath = CommonUtils.getString(generalDao.selectGernalObject("common.selectFilePathByFileId", params));
        log.info("{}.downloadFile - filePath : {}", this.getClass(), filePath);

        //TODO : 삭제
        File f = new File(filePath);
        if(!f.exists()) {
            log.error("{}.downloadFile - FILE NOT EXISTS");
        }

        log.info("{}.downloadFile - file down Start ===>");

        Path path = Paths.get(filePath);
        String contentType = Files.probeContentType(path);
        String fileName = new String(CommonUtils.getString(params.get("fileNm")).getBytes("ksc5601"), "euc-kr");

        log.info("{}.downloadFile - file down get contentType Done. ===>");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        log.info("{}.downloadFile - file down set HttpHeader Done. ===>");

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
}
