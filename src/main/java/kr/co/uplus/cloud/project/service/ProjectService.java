package kr.co.uplus.cloud.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cloud.sample.dto.RestResult;
import kr.co.uplus.cloud.utils.CommonUtils;
import kr.co.uplus.cloud.utils.GeneralDao;

@Service
public class ProjectService {
	
	@Autowired
	private GeneralDao generalDao;
	
	// 프로젝트 리스트 조회
	public RestResult<?> selectProjectList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> rtnList = generalDao.selectGernalList("project.selectProjectList", params);
		
		rtn.setData(rtnList);
		
		return rtn;
	}
	
	// 프로젝트 명 중복 체크
	public RestResult<?> checkProjectNameDuplicate(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		int checkCnt = generalDao.selectGernalCount("project.checkProjectNameDuplicate", params);
		
		if( checkCnt > 0 ) {
			rtn.setSuccess(false);
		} else {
			rtn.setSuccess(true);
		}
		
		return rtn;
	}
	
	// 프로젝트 저장
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
	public void saveProject(Map<String, Object> params) throws Exception {
		// 저장 상태값
		String sts = CommonUtils.getString(params.get("sts"));
		// 사용자 세션
		Map<String, Object> userMap = (Map<String, Object>) params.get("userDto");
		params.put("user_id", userMap.get("userId"));
		
		if( "C".equals(sts) ) {
			// SEQ MAX 값 처리 (임시)
			int project_max_id = Integer.parseInt(CommonUtils.getString(generalDao.selectGernalObject("project.selectProjectMaxId", params)));
			
			params.put("project_id", project_max_id);
			// 고객사가 개발이 안되서 임시로 고객사 코드 입력
			params.put("corp_id", "CORP_ID");
			
			
			// 사용체널 JSON 값 처리
			String price_info_json = "";
			
			price_info_json += "{";
			price_info_json += "	\"RCS\"		: \"" + CommonUtils.getString(params.get("radio_rcs"))	+ "\",";
			price_info_json += "	\"SMS/MMS\"	: \"" + CommonUtils.getString(params.get("radio_mms"))	+ "\",";
			price_info_json += "	\"PUSH\"	: \"" + CommonUtils.getString(params.get("radio_push"))	+ "\",";
			price_info_json += "	\"KAKAO\"	: \"" + CommonUtils.getString(params.get("radio_kko"))	+ "\",";
			price_info_json += "	\"MO\"		: \"" + CommonUtils.getString(params.get("radio_mo"))	+ "\"";
			price_info_json += "}";
			
			params.put("price_info", price_info_json);
			
			// 프로젝트 insert
			generalDao.insertGernal("project.insertProject", params);
			
			// -------------------------------------------------------------------------------------------------------------------------------------
			// 프로젝트 멤버 추가 처리, OWNER 권한자는 무조건 추가 처리되야함
			// CM_PROJECT_USER 
			// USER_PRIVILEGETYPE 컬럼이 뭔지 확인 필요함...
			
			// 사용자 세션의 권한을 체크해서 OWNER 가 아닐경우 OWNER 유저 입력 처리 ==> 현재 권한쪽 작업이 안되어있어서 주석 처리
//				if( !"OWNER".equals(params.get("ROLE_CD")) ) {
//					Map<String, Object> ownerUserMap = (Map<String, Object>) generalDao.selectGernalObject("project.selectProjectOwnerUser", params);
//					ownerUserMap.put("project_id", project_max_id);
//					
//					generalDao.insertGernal("project.insertProjectUser", ownerUserMap);
//				}
			// 사용자 기본 멤버로 추가
			generalDao.insertGernal("project.insertProjectUser", params);
			
			// -------------------------------------------------------------------------------------------------------------------------------------
			
			
			// API관리키 관리 insert
			// API 채번 룰 필요 --> 임시로 max값
			int api_max_id = Integer.parseInt(CommonUtils.getString(generalDao.selectGernalObject("project.selectApikeyMaxId", params)));
			params.put("api_key", api_max_id);
			// not null 조건들의 디폴트 값 필요함 ==> 다 널처리
			generalDao.insertGernal("project.insertProjectApiKey", params);
			
			// -------------------------------------------------------------------------------------------------------------------------------------
			// 후불의 경우 청구ID 관리 ? 
			if( "N".equals(CommonUtils.getString(params.get("pay_type"))) ) {
				generalDao.updateGernal("project.updateProjectBillId", params);
			}
		} else if ( "U".equals(sts) ) {
			generalDao.updateGernal("project.updateProject", params);
		} else if ( "D".equals(sts) ) {
			// 테이블 이력 조회 CM_MSG, CM_WEB_MSG
			int projectUseCnt = Integer.parseInt(CommonUtils.getString(generalDao.selectGernalCount("project.selectProjectUseCnt", params)));
			
			if( projectUseCnt == 0 ) {
				generalDao.deleteGernal("project.deleteProject", params);
				generalDao.deleteGernal("project.deleteProjectUser", params);
				generalDao.deleteGernal("project.deleteProjectApiKey", params);
			} else {
				throw new Exception("사용된 이력이 존재하여 해당 프로젝트는 삭제할 수 없습니다.");
			}
			
		}
		
	}

}
