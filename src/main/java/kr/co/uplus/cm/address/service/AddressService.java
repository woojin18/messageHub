package kr.co.uplus.cm.address.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
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
}
