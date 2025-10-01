package com.example.demo.__core.config;

import com.example.demo.__core.intercept.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig {
	private final LoginInterceptor loginInterceptor;

	public void addInterceptors(InterceptorRegistry reg) {
		reg.addInterceptor(loginInterceptor)
				.addPathPatterns("/board/**", "/user/**", "/reply/**")
				.excludePathPatterns("/board/{id:\\d+");
	}
}
