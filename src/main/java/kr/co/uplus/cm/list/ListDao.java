package kr.co.uplus.cm.list;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.uplus.config.mybatis.cmd.SqlSessionCmd;

@Repository
public class ListDao {
	@Autowired
	private SqlSessionCmd sqlSession;

	public List<Map<String, Object>> testList(Map<String, Object> paramMap) {
		return sqlSession.selectList("list.listTest", paramMap);
	}

	public Integer testListCnt(Map<String, Object> paramMap) {
		return sqlSession.selectCount("list.listTestCnt", paramMap);
	}

	public void insertList(Map<String, Object> paramMap) {
		int seq = sqlSession.selectOne("list.selectListSeq");
		paramMap.put("seq", seq);
		sqlSession.insert("list.insertList", paramMap);
	}

	public void updateList(Map<String, Object> paramMap) {
		sqlSession.update("list.updateList", paramMap);
	}

}
