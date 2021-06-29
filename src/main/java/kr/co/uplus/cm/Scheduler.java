package kr.co.uplus.cm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.uplus.cm.login.service.AuthService;

@Component
public class Scheduler {

	@Autowired
	private AuthService authService;
	
	/**
	 * 10분 단위로 권한정보를 업데이트 한다. 
	 */
	@Scheduled(initialDelay = 600000, fixedRate = 600000)
	public void initRole() {
		authService.init();		
	}
}
