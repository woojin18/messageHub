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
import kr.co.uplus.cm.customer.service.CustomerService;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
    }

    /**
     * FAQ 구분 리스트 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/api/public/customer/selectFaqTypeList")
    public RestResult<?> selectFaqTypeList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = customerService.selectFaqTypeList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
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
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectFaqList Error : {}", this.getClass(), e);
        }

        return rtn;
    }


}
