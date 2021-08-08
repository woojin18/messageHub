package kr.co.uplus.cm.common.config;

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

import kr.co.uplus.cm.common.crypto.AesEncryptor;
import kr.co.uplus.cm.common.crypto.Sha256PasswordEncoder;
import kr.co.uplus.cm.common.filter.VueStaticFilter;
import kr.co.uplus.cm.common.handler.LoginFailureHandler;
import kr.co.uplus.cm.common.handler.LoginSuccessHandler;
import kr.co.uplus.cm.common.jwt.JwtAuthCookieFilter;
import kr.co.uplus.cm.common.jwt.JwtAuthHeaderFilter;
import kr.co.uplus.cm.common.jwt.JwtExceptionFilter;
import kr.co.uplus.cm.common.jwt.JwtProperties;
import kr.co.uplus.cm.login.service.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	public static final String LOGIN_FORM_URL = "/login/**";
	public static final String LOGIN_API_URL = "/api/auth/login";
	public static final String LOGIN_FAIL_URL = "/login?error=true";
	public static final String LOGIN_SUCC_AC_URL = "/ac/home";
	public static final String LOGIN_SUCC_UC_URL = "/uc/home";
	public static final String LOGOUT_URL = "/api/auth/logout";
	public static final String MENUBAR_URL = "/api/auth/getMenuForRole";
	private static final String API_URL = "/api/**";
	public static final String PUBLIC_PAGE_URL = "/public/**";
	public static final String PUBLIC_API_URL = "/api/public/**"; // 내부에서 인증없이 호출하는 API
	public static final String LIST_API_URL = "/listCtl/**";
	public static final String PROJECT_API_URL = "/projectApi/**";
	public static final String[] REST_API_URLS = { API_URL };
	public static final String MESSAGESTATUS_API_URL = "/messageStatusApi/**";
	public static final String INTEGRATEDTEMPLATE_API_URL = "/integratedTemplateApi/**";
	public static final String INTEGRATEDSEND_API_URL = "/integratedSendApi/**";
	public static final String SMARTTEMPLATE_API_URL = "/smartTemplateApi/**";
	public static final String SMARTSEND_API_URL = "/smartSendApi/**";
	public static final String COMMON_API_URL = "/commonApi/**";
	public static final String USER_CONSOLE_URL = "/uc/**";
	public static final String ADMIN_CONSOLE_URL = "/ac/**";
	public static final String ADMIN_SIGN = "/sign/**";

	public static final String LOGIN_ID_PARAM = "userId";
	@SuppressWarnings("unused")
	private static final String LOGIN_PWD_PARAM = "userPwd";

	public static final String AC_SVC_TP_CD = "AC";
	public static final String UC_SVC_TP_CD = "UC";

	@Autowired
	private UserDetailsService userDetailsService;

    @Autowired
    private AuthService authService;

	@Autowired
	private JwtProperties jwtProps;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/se2/**","/assets/**");
	}

	@Bean
	public Filter jwtAuthFilter() {
		return new JwtAuthCookieFilter(jwtProps);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new VueStaticFilter(), UsernamePasswordAuthenticationFilter.class) // Vue에서 호출시 화면관련 URL은 forward
				.addFilterBefore(new JwtExceptionFilter(authService), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JwtAuthHeaderFilter(jwtProps), UsernamePasswordAuthenticationFilter.class);

		http
			.cors().and()
			// csrf 사용하지 않음
			.csrf().disable()
			// Spring Security가 HttpSession 객체를 생성하지 않도록 설정
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.headers().contentTypeOptions().disable()
			.and()
			.headers().frameOptions().disable()
			.and()
			.exceptionHandling()
				.authenticationEntryPoint(new MixedAuthenticationEntryPoint(LOGIN_FORM_URL, REST_API_URLS))
			.and()
				.authorizeRequests()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // CORS preflight 요청은 인증처리를 하지 않도록 설정
				.antMatchers(PUBLIC_API_URL, PUBLIC_PAGE_URL).permitAll()
				.antMatchers("/", PUBLIC_API_URL, LOGIN_FORM_URL, LOGIN_API_URL, LOGOUT_URL, LIST_API_URL,
						PROJECT_API_URL, MENUBAR_URL, MESSAGESTATUS_API_URL, INTEGRATEDTEMPLATE_API_URL,
						INTEGRATEDSEND_API_URL, SMARTTEMPLATE_API_URL,SMARTSEND_API_URL, COMMON_API_URL,
						USER_CONSOLE_URL, ADMIN_CONSOLE_URL, ADMIN_SIGN).permitAll()
				.antMatchers(API_URL).authenticated()
				.anyRequest().authenticated();
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
