package kr.co.uplus.cm.common;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * Application
 *
 * </pre>
 *
 * @author : savrani (Son Wang Gu)
 * @Date : 2019. 3. 22.
 * @Version :
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling
@Log4j2
@ComponentScans(value = { 
		@ComponentScan("kr.co.uplus.cm"),
		@ComponentScan("kr.co.uplus.config.mybatis"),
		@ComponentScan("kr.co.uplus.config.mongo"),
	})
public class CloudWebApplication {

	public static void main(String[] args) throws Exception {
		if (System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME) == null) {
			System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "local");
		}

		SpringApplication.run(CloudWebApplication.class, args);
	}

	@PostConstruct
	public void onStartup() {
		log.info("################ System-up start ################");
	}

	@PreDestroy
	public void onExit() {
		log.info("################ System-down start ################");
	}
}
