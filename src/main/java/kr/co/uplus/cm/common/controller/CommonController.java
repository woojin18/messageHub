package kr.co.uplus.cm.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/public/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    // 이미지 업로드
    @SuppressWarnings("unchecked")
    @PostMapping("/uploadImage")
    public RestResult<?> uploadImage(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam MultipartFile uploadFile,
            @RequestParam(value = "paramString") String paramString) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> params = mapper.readValue(paramString, Map.class);

        try {
            rtn = commonService.uploadImgFile(uploadFile, params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("파일등록에 실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
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
            log.error("{} Error : {}", this.getClass(), e);
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
            log.error("{} Error : {}", this.getClass(), e);
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

}
