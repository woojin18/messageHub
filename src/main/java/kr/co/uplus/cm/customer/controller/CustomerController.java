package kr.co.uplus.cm.customer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.customer.service.CustomerService;
import kr.co.uplus.cm.myPage.service.MyPageService;
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
    public RestResult<?> selectCodeList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
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
    public RestResult<?> selectFaqList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
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
    public RestResult<?> selectNoticeList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
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

}
