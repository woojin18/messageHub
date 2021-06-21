package kr.co.uplus.cm.main.controller;

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
import kr.co.uplus.cm.main.service.MainService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/public/main")
public class MainController {

    @Autowired
    private MainService mainService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
    }

    /**
     * 상품 가격정보 리스트 조회
     * @param params
     * @return
     */
    @PostMapping("/selectPrdUnitInfoList")
    public RestResult<?> selectPrdUnitInfoList(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = mainService.selectPrdUnitInfoList();
        } catch (Exception e) {
            rtn.setFail();
            log.error("{}.selectPrdUnitInfoList Error : {}", this.getClass(), e);
        }
        return rtn;
    }

}
