package kr.co.uplus.cm.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

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
		
		if (checkCnt > 0) {
			rtn.setSuccess(false);
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}

	// 프로젝트 저장
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void saveProject(Map<String, Object> params) throws Exception {
		// 저장 상태값
		String sts = CommonUtils.getString(params.get("sts"));
		// 사용자 세션
		Map<String, Object> userMap = (Map<String, Object>) params.get("userDto");
		params.put("userId", userMap.get("userId"));

		if ("C".equals(sts)) {
			// 프로젝트 ID ( 시분초 + pjt )
			params.put("projectId", CommonUtils.generationSringToHex("pjt"));
			// 고객사가 개발이 안되서 임시로 고객사 코드 입력
			params.put("corpId", userMap.get("corpId"));

			// 사용체널 JSON 값 처리
			String jsonInfo = "";

			jsonInfo += "{";
			jsonInfo += "	\"RCS\"		: \"" + CommonUtils.getString(params.get("radioRcs")) + "\",";
			jsonInfo += "	\"SMS/MMS\"	: \"" + CommonUtils.getString(params.get("radioMms")) + "\",";
			jsonInfo += "	\"PUSH\"	: \"" + CommonUtils.getString(params.get("radioPush")) + "\",";
			jsonInfo += "	\"KAKAO\"	: \"" + CommonUtils.getString(params.get("radioKko")) + "\",";
			jsonInfo += "	\"MO\"		: \"" + CommonUtils.getString(params.get("radioMo")) + "\"";
			jsonInfo += "}";

			params.put("jsonInfo", jsonInfo);

			// 프로젝트 insert
			generalDao.insertGernal("project.insertProject", params);

			// -------------------------------------------------------------------------------------------------------------------------------------
			// 프로젝트 멤버 추가 처리, OWNER 권한자는 무조건 추가 처리되야함
			// 사용자 세션의 권한을 체크해서 OWNER 일경우 OWNER 유저 입력 처리 안하도록 처리 
			if( !"OWNER".equals(params.get("ROLE_CD")) ) {
				// 사용자 기본 멤버로 추가
				generalDao.insertGernal("project.insertProjectUser", params);
			}
			// -------------------------------------------------------------------------------------------------------------------------------------

			// API관리키 관리 insert ==> 기본 입력 안되기로 처리
			
			// -------------------------------------------------------------------------------------------------------------------------------------
			// 후불의 경우 청구ID 관리 ? ==> API 미개발
//			if ("N".equals(CommonUtils.getString(params.get("pay_type")))) {
//				generalDao.updateGernal("project.updateProjectBillId", params);
//			}
			
		} else if ("U".equals(sts)) {
			// 사용체널 JSON 값 처리
			String jsonInfo = "";

			jsonInfo += "{";
			jsonInfo += "	\"RCS\"		: \"" + CommonUtils.getString(params.get("radioRcs")) + "\",";
			jsonInfo += "	\"SMS/MMS\"	: \"" + CommonUtils.getString(params.get("radioMms")) + "\",";
			jsonInfo += "	\"PUSH\"	: \"" + CommonUtils.getString(params.get("radioPush")) + "\",";
			jsonInfo += "	\"KAKAO\"	: \"" + CommonUtils.getString(params.get("radioKko")) + "\",";
			jsonInfo += "	\"MO\"		: \"" + CommonUtils.getString(params.get("radioMo")) + "\"";
			jsonInfo += "}";

			params.put("jsonInfo", jsonInfo);
			
			generalDao.updateGernal("project.updateProject", params);
		} else if ("D".equals(sts)) {
			// 테이블 이력 조회 CM_MSG, CM_WEB_MSG
			int projectUseCnt = Integer.parseInt(
					CommonUtils.getString(generalDao.selectGernalCount("project.selectProjectUseCnt", params)));

			if (projectUseCnt == 0) {
				generalDao.deleteGernal("project.deleteProject", params);
				generalDao.deleteGernal("project.deleteProjectUser", params);
				generalDao.deleteGernal("project.deleteProjectApiKey", params);
			} else {
				throw new Exception("사용된 이력이 존재하여 해당 프로젝트는 삭제할 수 없습니다.");
			}

		}

	}

	public RestResult<?> checkPreRegYn(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);

		String checkYn = CommonUtils.getString(generalDao.selectGernalObject("project.checkPreRegYn", params));
		
		if( "Y".equals(checkYn) ) {
			rtn.setSuccess(true);
		} else {
			rtn.setSuccess(false);
		}

		
		return rtn;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void savePreRegEx(Map<String, Object> params) throws Exception{
		// 이미 등록되어있는지 확인
		String checkYn = CommonUtils.getString(generalDao.selectGernalObject("project.checkPreRegYn", params));
		
		if( "Y".equals(checkYn) ) {
			throw new Exception("이미 사전등록 예외 대상 사업자로 등록 중 입니다.");
		} else {
			// 첨부파일 JSON 값 처리
			String jsonInfoStr = "";

			jsonInfoStr += "{";
			jsonInfoStr += "	\"fileName1\"		: \"" + CommonUtils.getString(params.get("fileName1")) + "\",";
			jsonInfoStr += "	\"filePath1\"		: \"" + CommonUtils.getString(params.get("filePath1")) + "\",";
			jsonInfoStr += "	\"fileName2\"		: \"" + CommonUtils.getString(params.get("fileName2")) + "\",";
			jsonInfoStr += "	\"filePath2\"		: \"" + CommonUtils.getString(params.get("filePath2")) + "\",";
			jsonInfoStr += "	\"fileName3\"		: \"" + CommonUtils.getString(params.get("fileName3")) + "\",";
			jsonInfoStr += "	\"filePath3\"		: \"" + CommonUtils.getString(params.get("filePath3")) + "\",";
			jsonInfoStr += "	\"fileName4\"		: \"" + CommonUtils.getString(params.get("fileName4")) + "\",";
			jsonInfoStr += "	\"filePath4\"		: \"" + CommonUtils.getString(params.get("filePath4")) + "\"";
			jsonInfoStr += "}";

			params.put("attachFileList", jsonInfoStr);

			// 특수사업자관리 insert
			generalDao.insertGernal("project.insertSpecualBusi", params);
		}
	}
}
