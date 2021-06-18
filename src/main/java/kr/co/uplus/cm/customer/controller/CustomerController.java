package kr.co.uplus.cm.customer.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.config.FileConfig;
import kr.co.uplus.cm.customer.service.CustomerService;
import kr.co.uplus.cm.myPage.service.MyPageService;
import kr.co.uplus.cm.utils.CommonUtils;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private MyPageService myPageService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
    }

    /**
     * 코드값 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/api/public/customer/selectCodeList")
    public RestResult<?> selectCodeList(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = commonService.selectCodeList(params);
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.selectFaqTypeList Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * FAQ 리스트 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/api/public/customer/selectFaqList")
    public RestResult<?> selectFaqList(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = customerService.selectFaqList(params);
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.selectFaqList Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 공지사항 리스트 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/api/public/customer/selectNoticeList")
    public RestResult<?> selectNoticeList(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = customerService.selectNoticeList(params);
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.selectNoticeList Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 1:1 문의 등록
     * @param params
     * @return
     */
    @PostMapping("/api/public/customer/insertQuestBoard")
    public RestResult<?> insertQuestBoard(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = myPageService.saveQnaInfo(params);
        } catch(Exception e) {
            rtn.setFail();
            log.error("{}.insertQuestBoard Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 자료실 리스트 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/api/public/customer/selectLibraryList")
    public RestResult<?> selectLibraryList(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = customerService.selectLibraryList(params);
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.selectNoticeList Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 자료실 파일 다운로드
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping("/api/public/customer/procDownloadLibraryFile")
    public ResponseEntity<Resource> procDownloadLibraryFile(@RequestBody Map<String, Object> params) throws Exception {
        Map<String, Object> fileInfo = customerService.selectAttachFileInfo(params);
        String filePath = CommonUtils.getStrValue(fileInfo, "attachFilePath");
        String fileName = new String(CommonUtils.getStrValue(fileInfo, "attachFileName").getBytes("ksc5601"), "euc-kr");

        if(StringUtils.indexOf(filePath, FileConfig.FileSvcType.LIBRARY.getPath()) < 0) {
            String errorMsg = "unauthorized path access";
            log.error("{}.selectNoticeList Error : {}", this.getClass(), errorMsg);
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
