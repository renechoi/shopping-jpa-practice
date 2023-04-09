package ssipgeukbbok.shoppingjpapractice.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.dto.security.UserAccountPrincipal;
import ssipgeukbbok.shoppingjpapractice.service.UserAccountService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserAccountService userAccountService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		String[] rolesAboveManager = {RoleType.ADMIN.name(), RoleType.SELLER.name()};

		return http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.mvcMatchers(
					HttpMethod.GET,
					"/",
					"/members/**",
					"/item/**",
					"/login",
					"/members/login/error",
					"/main/**",
					"/sr1c/**",
					"/error/**"

				).permitAll()
				.mvcMatchers("/admin/**").hasAnyRole(rolesAboveManager) // 위에 설정한 ROLE이면 접근 가능
				//                        .mvcMatchers(HttpMethod.DELETE, "/**").hasAnyRole(rolesAboveManager)
				.anyRequest().authenticated())

			.formLogin(formLogin ->
				formLogin.loginPage("/members/login")// 사용자 정의 로그인 페이지 URL
					.loginProcessingUrl("/members/login/process")// 로그인 처리 URL
					.defaultSuccessUrl("/") // 로그인 성공 후 이동할 페이지 URL
					.failureUrl("/login") // 로그인 실패시 이동할 페이지 URL
					.usernameParameter("email") // 사용자 계정의 id나 email을 나타내는 파라미터 이름
					.passwordParameter("password") // 사용자 계정의 비밀번호를 나타내는 파라미터 이름
					.successHandler(new AuthenticationSuccessHandler() {
						@Override
						public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {

						}
					})

			)

			.rememberMe()
			.tokenValiditySeconds(3600) // default = 14일
			.alwaysRemember(true) // 리멤버미 기능이 활성화되지 않아도 항상 실행
			.userDetailsService(userDetailsService(userAccountService)).and()





			.logout(logout ->
				logout.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
					.logoutSuccessUrl("/")
					.addLogoutHandler(new LogoutHandler() {
						@Override
						public void logout(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) {

						}
					})
					.logoutSuccessHandler(new LogoutSuccessHandler() {
						@Override
						public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {

						}
					})
					)

			.exceptionHandling()
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
			.and()

			.build();
	}

	@Bean
	public UserDetailsService userDetailsService(UserAccountService userAccountService) {
		return email -> userAccountService.searchUser(email)
			.map(UserAccountPrincipal::from)
			.orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다"));
	}

}
