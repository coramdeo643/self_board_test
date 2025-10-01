package com.example.demo.__core.config;

import com.example.demo.__core.intercept.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
	private final LoginInterceptor loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry reg) {
		reg.addInterceptor(loginInterceptor)
				.addPathPatterns("/**")
				// API와 update-form만 보호
				.excludePathPatterns(
						"/api/users/join",      // 회원가입 API
						"/api/users/login",     // 로그인 API
						"/users/join-form",     // 회원가입 페이지
						"/users/login-form"     // 로그인 페이지
				);
	}
}
