package kr.co.uplus.cm.urlInfo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.urlInfo.service.UrlInfoService;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
@RequestMapping("/uc/urlInfo")
public class UrlInfoController {

    @Autowired
    private UrlInfoService urlInfoService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
    }

    /**
     * 단축URL 목록 조회
     * @param params
     * @return RestResult
     */
    @PostMapping("/selectUrlInfoList")
    public RestResult<?> selectUrlInfoList(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = urlInfoService.selectUrlInfoList(params);
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.selectUrlInfoList Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 단축URL 생성
     * @param params
     * @return RestResult
     */
    @PostMapping("/insertUrlInfo")
    public RestResult<?> insertUrlInfo(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = urlInfoService.insertUrlInfo(params);
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.insertUrlInfo Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 단축URL 삭제
     * @param params
     * @return
     */
    @PostMapping("/deleteUrlInfo")
    public RestResult<?> deleteUrlInfo(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = urlInfoService.deleteUrlInfo(params);
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.deleteUrlInfo Error : {}", this.getClass(), e);
        }
        return rtn;
    }
}
