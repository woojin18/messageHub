package kr.co.uplus.cm.sendMessage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.sendMessage.dto.PushFbInfo;
import kr.co.uplus.cm.sendMessage.dto.PushMsg;
import kr.co.uplus.cm.sendMessage.dto.PushRecvInfo;
import kr.co.uplus.cm.sendMessage.dto.PushRequestDto;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * SendMessageService
 * 각 채널별 메시지 발송처리 Service
 * </pre>
 *
 * @author liam (Lee Sung Min)
 * @Date : 2021.03.29.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@Log4j2
@Service
public class SendMessageService {

    @Autowired
    private GeneralDao generalDao;

    @Autowired
    private CommonService commonService;

    /**
     * APP ID 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectAppIdList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_APP_ID_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 발신번호 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectCallbackList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CALLBACK_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 주소록 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectAddressList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> rtnObj = new HashMap<String, Object>();

        //주소록 그룹 목록 조회
        List<Object> addrGrpList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_GRP_LIST, params);
        List<Object> addrCtgyList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_CTGY_LIST, params);

        rtnObj.put("addrGrpList", addrGrpList);
        rtnObj.put("addrCtgyList", addrCtgyList);
        rtn.setData(rtnObj);

        return rtn;
    }

    /**
     * 주소록 카테고리 구성원 목록 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectCmCuList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_CM_CU_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CM_CU_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * Get 푸시 수신자 리스트
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PushRecvInfo> getRecvInfoLst(Map<String, Object> params, MultipartFile excelFile) throws Exception{
        List<PushRecvInfo> recvInfoLst = new ArrayList<PushRecvInfo>();

        if(params.containsKey("cuInputType")) {

            //직접입력
            if(StringUtils.equals("DICT", (String)params.get("cuInputType"))) {
                List<Object> objList = (List<Object>) params.get("recvInfoLst");
                log.info(objList);

                PushRecvInfo recvInfo = null;
                for(Object recvObj : objList) {
                    recvInfo = (PushRecvInfo) recvObj;
                    log.info(recvInfo);
                    recvInfoLst.add(recvInfo);
                }

            //주소록
            } else if(StringUtils.equals("ADDR", (String)params.get("cuInputType"))) {

            //엑셀
            } else if(StringUtils.equals("EXCEL", (String)params.get("cuInputType"))) {

                //read excelFile
                List<Map<String, Object>> excelList = null;
                List<String> colKeys = new ArrayList<String>();
                List<String> contsVarNms = null;

                if(params.containsKey("requiredCuid") && (Boolean) params.get("requiredCuid")) {
                    colKeys.add("cuid");
                }
                if(params.containsKey("requiredCuPhone") && (Boolean) params.get("requiredCuPhone")) {
                    colKeys.add("phone");
                }
                if(params.containsKey("contsVarNms")) {
                    contsVarNms = (ArrayList<String>)params.get("contsVarNms");
                    for(String varNm : contsVarNms) {
                        colKeys.add(varNm);
                    }
                }
                excelList = commonService.getExcelDataList(excelFile, 2, colKeys);

                PushRecvInfo recvInfo = null;
                Map<String, String> mergeData = null;
                for(Map<String, Object> excelInfo : excelList) {
                    recvInfo = new PushRecvInfo();
                    if(excelInfo.containsKey("cuid")) recvInfo.setCuid((String) excelInfo.get("cuid"));
                    if(excelInfo.containsKey("phone")) recvInfo.setCuid((String) excelInfo.get("phone"));

                    mergeData = new HashMap<String, String>();
                    for(String key : excelInfo.keySet()) {
                        if(!StringUtils.equals(key, "cuid") && !StringUtils.equals(key, "phone")) {
                            mergeData.put(key, (String) excelInfo.get(key));
                        }
                    }
                    recvInfo.setMergeData(mergeData);
                    recvInfoLst.add(recvInfo);
                }

            //전체발송
            } else if(StringUtils.equals("ALL", (String)params.get("cuInputType"))) {

            }
        }

        return recvInfoLst;
    }











































    public String syncSend() {
        System.out.println("sync End");
        return "sync End";
    }

    @Async
    public String asyncSend() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("async End");
        return "async End";
    }

    public void setPushRequestDate(PushRequestDto pushRequestDto) {

        pushRequestDto.setCallback("15441234");
        pushRequestDto.setCampaignId("campaignId01");

        PushMsg pushMsg = new PushMsg();
        pushMsg.setTitle("title01");
        pushMsg.setBody("body01");
        pushRequestDto.setMsg(pushMsg);

        pushRequestDto.setAppId("appId01");

        //TODO ext
        Map<String, String> ext = new HashMap<String, String>();
        ext.put("additionalProp1", "string");
        ext.put("additionalProp2", "string");
        ext.put("additionalProp3", "string");
        pushRequestDto.setExt(ext);

        pushRequestDto.setFileId("111");
        pushRequestDto.setWebReqId("string6338");
        pushRequestDto.setServiceCode("string");

        List<PushRecvInfo> pushRecvInfoList = new ArrayList<PushRecvInfo>();
        PushRecvInfo pushRecvInfo = new PushRecvInfo();
        Map<String, String> mergeData = new HashMap<String, String>();
        mergeData.put("additionalProp1", "string");
        mergeData.put("additionalProp2", "string");
        mergeData.put("additionalProp3", "string");

        pushRecvInfo.setCliKey("1");
        pushRecvInfo.setCuid("String");
        pushRecvInfo.setMergeData(mergeData);
        pushRecvInfoList.add(pushRecvInfo);

        pushRecvInfo = new PushRecvInfo();
        pushRecvInfo.setCliKey("2");
        pushRecvInfo.setCuid("String");
        pushRecvInfo.setMergeData(mergeData);
        pushRecvInfoList.add(pushRecvInfo);
        pushRequestDto.setRecvInfoLst(pushRecvInfoList);
//

        List<PushFbInfo> pushFbInfoList = new ArrayList<PushFbInfo>();
        PushFbInfo pushFbInfo = new PushFbInfo();
        pushFbInfo.setCh("SMS");
        //pushFbInfo.setTitle("제목");
        pushFbInfo.setTitle("title");
        //pushFbInfo.setMsg("SMS 메시지 내용");
        pushFbInfo.setMsg("SMS messge contents");
        pushFbInfo.setFileId("String");
        pushFbInfoList.add(pushFbInfo);
        pushRequestDto.setFbInfoLst(pushFbInfoList);

    }





}
