package kr.co.uplus.cloud.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cloud.sample.dto.RestResult;
import kr.co.uplus.cloud.utils.GeneralDao;

@Service
public class ProjectService {
	
	@Autowired
	private GeneralDao generalDao;

	public RestResult<?> selectProjectList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> rtnList = generalDao.selectGernalList("channel.selectProjectList", params);
		
		rtn.setData(rtnList);
		
		return rtn;
	}

}
