package kr.co.uplus.cloud.common.controller;

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

import kr.co.uplus.cloud.common.dto.RestResult;
import kr.co.uplus.cloud.common.service.CommonService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/public/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    // 이미지 업로드
    @PostMapping("/uploadImage")
    public RestResult<?> uploadImage(
            //@RequestBody MultipartFile uploadFile,
            @RequestParam MultipartFile uploadFile,
            @RequestParam String useCh,
            @RequestParam String loginId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        try {
            rtn = commonService.uploadImgFile(uploadFile, useCh, loginId);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("파일등록에 실패하였습니다.");
            log.error("File Upload Exception : {}", e);
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
            params.put("corp_id", "TEST_CORP_ID");    //TODO : 고객 ID(로그인세션에서 가져오자)
            rtn = commonService.selectImageList(params);
        } catch(Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        }

        return rtn;
    }

}
