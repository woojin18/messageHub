package kr.co.uplus.cloud.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.uplus.cloud.common.consts.DB;
import kr.co.uplus.cloud.common.model.AuthUser;
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
}
