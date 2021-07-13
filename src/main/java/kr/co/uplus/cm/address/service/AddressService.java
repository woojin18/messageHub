package kr.co.uplus.cm.address.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.dto.CamelDto;
import kr.co.uplus.cm.sendMessage.dto.RecvInfo;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class AddressService {
	@Autowired
	private GeneralDao generalDao;

	@Autowired
	private CommonService commonService;
	
	/**
	 * 주소카테고리그룹 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectAddressCateGrpList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_CATE_GRP_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}

	
	/**
	 * 주소리스트 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectAddressList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> rtnObj = new HashMap<String, Object>();
		
		//주소록 그룹 목록 조회
		Object addrCateGrp = generalDao.selectGernalObject(DB.QRY_SELECT_ADDR_CATE_GRP, params);
		List<Object> addrCateList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_CATE_LIST, params);
		
		rtnObj.put("addrCateGrp", addrCateGrp);
		rtnObj.put("addrCateList", addrCateList);
		rtn.setData(rtnObj);
		return rtn;
	}
	
	/**
	 * 주소록 구성원
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectMemberList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		if(params.containsKey("pageNo")
				&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize")
				&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_ADDR_MEM_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_MEM_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	/**
	 * 주소록 등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> registerAddr(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		int resultCnt = 0;
		resultCnt = generalDao.insertGernal(DB.QRY_INSERT_ADDR_CATE_GRP, params);
		
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}
	
	/**
	 * 주소록 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> modifyAddr(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		int resultCnt = 0;
		resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_ADDR_CATE_GRP, params);
		
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}
	
	/**
	 * 프로젝트 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectProjectList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_PROJECT_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	/**
	 * 수신자 리스트 조회
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
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_ADDR_CM_CU_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_CM_CU_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	/**
	 * 구성원 등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> registerMember(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		int resultCnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.putAll(params);
		
		ArrayList<Integer> memberList = (ArrayList<Integer>)map.get("memberList");
		
		for (Integer member : memberList) {
			map.put("cuInfoId", member);
			resultCnt = generalDao.insertGernal(DB.QRY_INSERT_ADDR_MEMBER_LIST, map);
		}
		
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}
	
	/**
	 * 구성원 삭제
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> deleteMember(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.putAll(params);
		
		int resultCnt = 0;
		
		ArrayList<String> memberList = (ArrayList<String>)params.get("memberList");
		String[] memberInfo = null;
		
		for (String member : memberList) {
			memberInfo = member.split(","); // checkbox로 가져온 PK를 구분자(,)로 분리
			map.put("cuInfoId", memberInfo[0]);
			map.put("addressCategoryId", memberInfo[1]);
			resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_ADDR_MEMBER, map);
		}
		
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}
	
	/**
	 * 수신자 목록 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectReceiverList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		if(params.containsKey("pageNo")
				&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize")
				&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_ADDR_RCVR_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_RCVR_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	/**
	 * 수신자 목록 조회 엑셀다운로드
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> excelDownloadReceiverList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		if(params.containsKey("pageNo")
				&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize")
				&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_ADDR_RCVR_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_RCVR_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	/**
	 * 수신자 등록/수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> saveReceiver(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		int resultCnt = 0;
		
		String status = (String)params.get("status");
		if(status.equals("R")) { // 등록
			resultCnt = generalDao.insertGernal(DB.QRY_INSERT_ADDR_RCVR, params);
		} else { // 수정
			resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_ADDR_RCVR, params);
		}
		
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}
	
	/**
	 * 수신자 엑셀 업로드
	 * @param params
	 * @param excelFile
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> registerReceiverExcel(Map<String, Object> params, MultipartFile excelFile) throws Exception {
		//read excelFile
		RestResult<Object> rtn = new RestResult<Object>();
		List<Map<String, Object>> excelList = null;
		List<String> colKeys = new ArrayList<String>();
		int resultCnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.putAll(params);
		
		colKeys.add("cuName");
		colKeys.add("cuid");
		colKeys.add("hpNumber");
		
		excelList = commonService.getExcelDataList(excelFile, 2, colKeys);
		
		for(Map<String, Object> excelInfo : excelList) {
			for(String key : excelInfo.keySet()) {
				if(key == null || "".equals(key)) continue; // 빈행처리
				if("hpNumber".equals(key)) {
					map.put(key, ((String) excelInfo.get(key)).replaceAll("-", "").trim());
				} else {
					map.put(key, ((String) excelInfo.get(key)).trim());
				}
				
			}
			map.put("useYn", "Y");
			map.put("loginId", (String)params.get("userId"));
			map.put("corpId", (String)params.get("corpId"));
			resultCnt = generalDao.insertGernal(DB.QRY_INSERT_ADDR_RCVR, map);
			map = new HashMap<String, Object>();
		}

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}
	
	/**
	 * 수신자 삭제
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> deleteReceiver(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		int resultCnt = 0;
		resultCnt = generalDao.selectGernalCount(DB.QRY_SELECT_ADDR_RCVR_CNT, params);

		if (resultCnt > 0) {
			rtn.setSuccess(false);
			rtn.setMessage("구성원에서 먼저 삭제하세요");
			return rtn;
		}

		resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_ADDR_RCVR, params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}
	
	/**
	 * 주소카테고리 등록/수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> saveAddressCategory(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		int resultCnt = 0;
		String saveStatus = (String)params.get("saveStatus");
		
		if("I".equals(saveStatus)) {
			resultCnt = generalDao.insertGernal(DB.QRY_INSERT_ADDR_CATE, params);
		} else {
			resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_ADDR_CATE, params);
		}
		
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}
	
	/**
	 * 주소 카테고리 삭제
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> deleteAddressCategory(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		int resultCnt = 0;
		
		// 하위카테고리 포함 구성원 삭제
		generalDao.deleteGernal(DB.QRY_DELETE_ADDR_CATE_MEMBER, params);
		
		// 하위카테고리 포함 삭제
		resultCnt = generalDao.updateGernal(DB.QRY_DELETE_ADDR_CATE, params);
		
		
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}
}
