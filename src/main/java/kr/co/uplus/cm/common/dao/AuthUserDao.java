package kr.co.uplus.cm.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.Menu;
import kr.co.uplus.cm.common.model.AuthUser;
import kr.co.uplus.config.mybatis.cmd.SqlSessionCmd;
import yoyozo.template.InnerMsg;

@Repository
public class AuthUserDao extends InnerMsg {

	@Autowired
	private SqlSessionCmd sqlSession;

	public AuthUser getByUsername(String username) {
		AuthUser authUser = sqlSession.selectOne(DB.QRY_SELECT_USERNAME, username);
		if (authUser == null)
			setErrMsg(sqlSession.getErrMsg());

		return authUser;
	}

	public int setAuthUserRegister(AuthUser user) {
		int n_res = sqlSession.insert(DB.QRY_INSERT_USERNAME, user);
		if (n_res < 0)
			setErrMsg(sqlSession.getErrMsg());

		return n_res;
	}
	
	public List<Menu> getMenuForRole(Map params) {
		List<Menu> list = sqlSession.selectList("login.getMenuForRole", params);
		return list;
	}
}
