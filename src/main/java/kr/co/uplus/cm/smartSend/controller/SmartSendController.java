package kr.co.uplus.cm.smartSend.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.MultipartFileDTO;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.smartSend.service.SmartSendService;
import kr.co.uplus.cm.sendMessage.dto.PushRequestData;
import kr.co.uplus.cm.sendMessage.dto.RecvInfo;
import kr.co.uplus.cm.sendMessage.dto.SmartRequestData;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * SendController
 * 스마트발송 관리 Controller
 * </pre>
 *
 * @author : liam (Lee Sung Min)
 * @Date : 2021.03.25.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@Log4j2
@RestController
@RequestMapping("/smartSendApi")
public class SmartSendController {

    @Autowired
    private CommonService commonService;
    
	@Autowired
	private SmartSendService smartSendService;

    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}


    /**
     * 스마트 발송처리
     *
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(path="/smartSendMessage")
    public RestResult<?> smartSendMessage(HttpServletRequest request, HttpServletResponse response
            , @ModelAttribute MultipartFileDTO multipartFileDTO) throws Exception {

        List<RecvInfo> recvInfoLst = null;
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> sParam = new HashMap<String, Object>();
        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> rtnMap = new HashMap<>();
        SmartRequestData requestData = null;

        try {
            params = commonService.setUserInfo(multipartFileDTO.getParams());
            String testSendYn = CommonUtils.getStrValue(params, "testSendYn");
            log.info("{}.smartSendMessage Start ====> params : {}", this.getClass(), params);
//System.out.println("smartSendController 010");
            /** 유효성 체크 */
            requestData = smartSendService.setSmartSendData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info("{}.smartSendMessage validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }
            log.info("{}.smartSendMessage pushRequestData: {}", this.getClass(), requestData.toString());
//System.out.println("smartSendMessage 020");
            /** 스마트발송 수신자 리스트*/
            recvInfoLst = smartSendService.getRecvInfoLst(params, multipartFileDTO.getFile());
            if(recvInfoLst == null || recvInfoLst.size() == 0) {
                rtn.setSuccess(false);
                rtn.setMessage("잘못된 스마트발송 수신자 정보입니다.");
                return rtn;
            }
//System.out.println("smartSendController 030");
            /** 예약건인지 확인 */
            String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
            if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
                return smartSendService.insertSmartCmWebMsg(rtn, params, requestData, recvInfoLst);
            }
//System.out.println("smartSendMessage 040");
            /** 잔액확인 */
            String payType = smartSendService.selectPayType(params);
//System.out.println("smartSendController 041  payType: "+payType);
            //선불일경우
            if(StringUtils.equals(payType, Const.COMM_YES)) {
                //남은 금액 조회
                BigDecimal rmAmount = smartSendService.getRmAmount(params);
System.out.println("smartSendController 042  rmAmount: "+rmAmount);                
                //개당 가격 조회
                List<String> productCodes = new ArrayList<String>();
                
                //cm_bo.CM_PRODUCT_UNIT WHERE SMART_CH_PRODUCT_YN = 'Y' 에서
                //PREE_FEE 정보를 가져온다
System.out.println("smartSendController 042-1  productCode: "+CommonUtils.getStrValue(params, "productCode"));                
                productCodes.add(CommonUtils.getStrValue(params, "productCode"));
                //스마트 전송은 아래 개별 사항이 필요없음
//                String[] chTypeArr = (CommonUtils.getStrValue(params, "chTypeList")).split(",");
//                for(String s : chTypeArr) {
//
//					if(s.equalsIgnoreCase("PUSH")) productCodes.add(Const.MsgProductCode.getType(Const.Ch.PUSH));
//					if(s.equalsIgnoreCase("RCS")) productCodes.add(Const.MsgProductCode.getType(Const.Ch.RCS));
//					if(s.equalsIgnoreCase("SMSMMS")) {
//						if((CommonUtils.getStrValue(params, "smsSendType")).equalsIgnoreCase("S")) {
//							productCodes.add(Const.MsgProductCode.getType(Const.Ch.SMS));
//						}else if((CommonUtils.getStrValue(params, "smsSendType")).equalsIgnoreCase("M")) {
//							productCodes.add(Const.MsgProductCode.getType(Const.Ch.MMS));
//						}
//					}
//					if(s.equalsIgnoreCase("FRIENDTALK")) {
//						productCodes.add(Const.MsgProductCode.getType(Const.Ch.FRIENDTALK));
//					}
//					if(s.equalsIgnoreCase("ALIMTALK")) {
//						productCodes.add(Const.MsgProductCode.getType(Const.Ch.ALIMTALK));
//					}
//                }
                
                if(params.containsKey("rplcSendType")
                        && !CommonUtils.isEmptyValue(params, "rplcSendType")
                        && !StringUtils.equals((CharSequence) params.get("rplcSendType"), "NONE")) {
                    productCodes.add(Const.MsgProductCode.getType(CommonUtils.getStrValue(params, "rplcSendType")));
                }

                sParam = new HashMap<>();
                sParam.put("corpId", CommonUtils.getStrValue(params, "corpId"));
                sParam.put("productCodes", productCodes);
                BigDecimal feePerOne = smartSendService.selectMsgFeePerOne(sParam);
                BigDecimal feePerAll = feePerOne.multiply(new BigDecimal(recvInfoLst.size()));
System.out.println("smartSendController 043  feePerOne: "+feePerOne);   
System.out.println("smartSendController 044  feePerAll: "+feePerAll);   
                if(rmAmount.compareTo(feePerAll) < 0) {
                    if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                        rtn.setSuccess(false);
                        rtn.setMessage("잔액 부족으로 메시지를 발송할 수 없습니다.");
                        return rtn;
                    } else {
                        rtnMap.put("feeMsg", "잔액 부족으로 메시지가 발송되지 않을 수도 있습니다.");
                    }
                }
            }
//System.out.println("smartSendController 050  testSendYn : "+testSendYn);
            /** 테스트발송(동기화) */
            if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                return smartSendService.testSendSmartMsg(params, requestData, recvInfoLst);
            }

        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.smartSendMessage Error : {}", this.getClass(), e);
            return rtn;
        }
//System.out.println("smartSendController 060");
        /** 비동기화 발송 */
        try {
            List<Object> reSendCdList = smartSendService.reSendCdList(null);
            log.info("{}.smartSendMessage aSync API send Start ====>", this.getClass());
//System.out.println("smartSendController 061");            
            smartSendService.sendSmartMsgAsync(rtn, 0, params, requestData, recvInfoLst, reSendCdList);
        } catch (Exception e) {
            log.info("{}.smartSendMessage aSync API send Error : {}", this.getClass(), e);
        }
        rtn.setMessage("스마트 발송 요청처리 되었습니다.");

        rtn.setSuccess(true);
        rtn.setData(rtnMap);

        return rtn;
    }


    /**
     * 푸시 발송 수신자 엑셀업로드 템플릿 다운로드
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @PostMapping(path = "/excelDownSendSmartRecvTmplt")
    public ModelAndView excelDownSendSmartRecvTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) throws Exception {

        List<String> colLabels = new ArrayList<String>();
        if(params.containsKey("requiredCuid") && (Boolean) params.get("requiredCuid")) {
            colLabels.add("APP 로그인 ID");
        }
        if(params.containsKey("requiredCuPhone") && (Boolean) params.get("requiredCuPhone")) {
            colLabels.add("휴대폰 번호");
        }
        if(params.containsKey("contsVarNms")) {
            List<String> contsVarNms = (ArrayList<String>)params.get("contsVarNms");
            for(String varNm : contsVarNms) {
                colLabels.add(varNm);
            }
        }

        List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetTitle", "Template");
        map.put("colLabels", colLabels.toArray(new String[0]));
        map.put("colIds", new String[] {});
        map.put("numColIds", new String[] {});
        map.put("figureColIds", new String[] {});
        map.put("colDataList", new ArrayList<T>());
        sheetList.add(map);

        ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "smartTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
        model.addObject("sheetList", sheetList);

        return model;
    }



}
