package kr.co.uplus.cm.address.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class AddressService {
	@Autowired
	private GeneralDao generalDao;

	/**
	 * 주소카테고리그룹 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectAddressCateGrpList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ADDRESS_CATE_GRP_LIST, params);
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
		//List<Object> addrGrpList = generalDao.selectGernalList(DB.QRY_SELECT_ADDRESS_GRP_LIST, params);
		Object addrGrp = generalDao.selectGernalObject(DB.QRY_SELECT_ADDRESS_GRP, params);
		List<Object> addrCtgyList = generalDao.selectGernalList(DB.QRY_SELECT_ADDRESS_CATE_LIST, params);
		
		rtnObj.put("addrGrp", addrGrp);
		rtnObj.put("addrCtgyList", addrCtgyList);
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
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_ADDRESS_MEM_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ADDRESS_MEM_LIST, params);
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
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_PROJECT_LIST, params);
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
	public RestResult<Object> deleteMember(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		
		int resultCnt = 0;
		
		ArrayList<Integer> memberList = (ArrayList<Integer>)params.get("memberList");
		
		for (Integer member : memberList) {
			params.put("cuInfoId", member);
			resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_ADDR_MEMBER, params);
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
}
