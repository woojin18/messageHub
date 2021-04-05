package kr.co.uplus.cloud.common.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("app.props.jwt")
@Data
public class JwtProperties {
	private String keyString;

	private int privateTokenExpiration; // seconds
	private String part1;
	private String part2;

	private int accessTokenExpiration; // seconds
	private int refreshTokenExpiration; // seconds
	private String header;
	private String prefix;
}
