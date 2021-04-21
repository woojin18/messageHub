package kr.co.uplus.cm.list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cm.common.dto.RestResult;

@Service
public class ListService {
	
	@Autowired
	private ListDao listDao;

	public RestResult<?> testList(Map<String, Object> paramMap) {
		Map<String, Object> resultData = new HashMap<>();
		
		System.out.println("asdfasdfasdfasdf" + paramMap);
		List<Map<String, Object>> listData = listDao.testList(paramMap);
		Integer listDataCnt = listDao.testListCnt(paramMap);
		resultData.put("listData", listData);
		resultData.put("listDataCnt", listDataCnt);
		
		return new RestResult().setData(resultData);
	}

	public void insertList(Map<String, Object> paramMap) throws Exception {
		listDao.insertList(paramMap);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
	public void updateList(Map<String, Object> paramMap) throws Exception {
		listDao.updateList(paramMap);
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
	public void excelUpload(Map<String, Object> paramMap) throws Exception {
		List<Map<String, Object>> uploadList = (List<Map<String, Object>>) paramMap.get("excelJsonData");
		for(Map<String, Object> uploadMap : uploadList) {
			System.out.println("asdfasdfasdf" + uploadMap);
			listDao.insertList(uploadMap);
		}
	}

	public List testXlsList(Map<String, Object> paramMap) {

		return listDao.testList(paramMap);
	}
}
