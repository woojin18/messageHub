package kr.co.uplus.cloud.sample.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cloud.gw.model.mongo.SampleMsgInfo;
import kr.co.uplus.cloud.sample.consts.ResultCode;
import kr.co.uplus.cloud.sample.consts.Role;
import kr.co.uplus.cloud.sample.consts.UserStatus;
import kr.co.uplus.cloud.sample.crypto.AesEncryptor;
import kr.co.uplus.cloud.sample.crypto.Sha256PasswordEncoder;
import kr.co.uplus.cloud.sample.dao.AuthUserDao;
import kr.co.uplus.cloud.sample.dto.RestResult;
import kr.co.uplus.cloud.sample.model.AuthUser;
import kr.co.uplus.config.mongo.cmd.MongoCmd;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SampleService {

	@Autowired
	private Sha256PasswordEncoder sha256;
	
	@Autowired
	private AesEncryptor aesEncryptor;
	
	@Autowired
	private MongoCmd mongoCmd;

	@Autowired
	private AuthUserDao dao;
	
	
	public RestResult<?> register(AuthUser user, HttpServletRequest request, HttpServletResponse response) {
		user.setUserPwd(sha256.encode(user.getPassword()));
		user.setStatus(UserStatus.USE);
		user.setRole(Role.USER);
		
		try {
			int n_res = dao.setAuthUserRegister(user);
			if (n_res <= 0) {
				return new RestResult<String>(false).setCode(ResultCode.SE_DB);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new RestResult<String>(false).setCode(ResultCode.SE_DB);
		}
		
		return new RestResult<String>(true).setCode(ResultCode.SUCCESS);
	}
	
	public RestResult<?> aesEncrypt(AuthUser user) {
		String encPwd = aesEncryptor.encrypt(user.getPassword());
		String decPwd = aesEncryptor.decrypt(encPwd);
		
		HashMap<String, String> encryptMap = new HashMap<String, String>();
		encryptMap.put("encPwd", encPwd);
		encryptMap.put("decPwd", decPwd);
		
		return new RestResult<HashMap<String, String>>(true).setCode(ResultCode.SUCCESS).setData(encryptMap);
	}
	
	public RestResult<?> getMongodbData() {
		
		String msgkey = "testtest";
		SampleMsgInfo msgInfo2 = mongoCmd.findOne("msgkey", msgkey, SampleMsgInfo.class);
		
		return new RestResult<SampleMsgInfo>(true).setCode(ResultCode.SUCCESS).setData(msgInfo2);
	}
}
