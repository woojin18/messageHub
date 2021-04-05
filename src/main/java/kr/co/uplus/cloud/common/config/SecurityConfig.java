package kr.co.uplus.cloud.common.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import kr.co.uplus.cloud.common.crypto.AesEncryptor;
import kr.co.uplus.cloud.common.crypto.Sha256PasswordEncoder;
import kr.co.uplus.cloud.common.filter.VueStaticFilter;
import kr.co.uplus.cloud.common.handler.LoginFailureHandler;
import kr.co.uplus.cloud.common.handler.LoginSuccessHandler;
import kr.co.uplus.cloud.common.jwt.JwtAuthCookieFilter;
import kr.co.uplus.cloud.common.jwt.JwtAuthHeaderFilter;
import kr.co.uplus.cloud.common.jwt.JwtExceptionFilter;
import kr.co.uplus.cloud.common.jwt.JwtProperties;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String LOGIN_FORM_URL = "/login";
	public static final String LOGIN_API_URL = "/api/auth/login";
	public static final String SAMPLE_API_URL = "/sample/**"; 
	public static final String LOGIN_FAIL_URL = "/login?error=true";
	public static final String LOGIN_SUCC_URL = "/home";
	public static final String LOGOUT_URL = "/api/auth/logout";
	private static final String API_URL = "/api/**";
	public static final String PUBLIC_API_URL = "/api/public/**"; // 내부에서 인증없이 호출하는 API
	public static final String LIST_API_URL = "/listCtl/**";
	public static final String PROJECT_API_URL = "/projectApi/**";
	public static final String[] REST_API_URLS = { API_URL };

	public static final String LOGIN_ID_PARAM = "userId";
	@SuppressWarnings("unused")
	private static final String LOGIN_PWD_PARAM = "userPwd";

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtProperties jwtProps;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Bean
	public Filter jwtAuthFilter() {
		return new JwtAuthCookieFilter(jwtProps);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new VueStaticFilter(), UsernamePasswordAuthenticationFilter.class) // Vue에서 호출시 화면관련 URL은 /
																								// forward
				.addFilterBefore(new JwtExceptionFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JwtAuthHeaderFilter(jwtProps), UsernamePasswordAuthenticationFilter.class);

		http.cors().and().csrf().disable()
				// Spring Security가 HttpSession 객체를 생성하지 않도록 설정
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().headers()
				.contentTypeOptions().disable().and().headers().frameOptions().disable().and().exceptionHandling()
				.authenticationEntryPoint(new MixedAuthenticationEntryPoint(LOGIN_FORM_URL, REST_API_URLS)).and()
				.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // CORS preflight 요청은
																								// 인증처리를 하지 않도록 설정
				.antMatchers("/", PUBLIC_API_URL, LOGIN_FORM_URL, LOGIN_API_URL, SAMPLE_API_URL, LOGOUT_URL, LIST_API_URL,
						PROJECT_API_URL)
				.permitAll().antMatchers(API_URL).authenticated().anyRequest().authenticated();
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public Sha256PasswordEncoder passwordEncoder() {
		return new Sha256PasswordEncoder();
	}

	@Bean
	public AesEncryptor aesEncryptor() {
		return new AesEncryptor();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public LoginSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler();
	}

	@Bean
	public LoginFailureHandler loginFailureHandler() {
		return new LoginFailureHandler(LOGIN_FAIL_URL);
	}
}
