package kr.co.uplus.cm.rcsTemplateSend.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class RcsTemplateSendService {
	
	@Autowired
	private GeneralDao generalDao;

	public RestResult<Object> rcsTemplatePopInit(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> result = generalDao.selectGernalList(DB.QRY_SELECT_BRAND_LIST, params);
		
		rtn.setData(result);
				
		return rtn;
	}

}
