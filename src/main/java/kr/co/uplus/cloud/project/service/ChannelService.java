package kr.co.uplus.cloud.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cloud.common.consts.DB;
import kr.co.uplus.cloud.common.dto.RestResult;
import kr.co.uplus.cloud.utils.GeneralDao;

@Service
public class ChannelService {

	@Autowired
	private GeneralDao generalDao;

	public RestResult<?> selectRcsBrandList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_BRANDLIST, params);

		rtn.setData(rtnList);

		return rtn;
	}

}
