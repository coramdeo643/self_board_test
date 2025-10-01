package com.example.demo.__core.intercept;

import com.example.demo.__core.common.Def;
import com.example.demo.__core.errors.except.Ex401;
import com.example.demo.user.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		System.out.println("Intercept preHandle : " + request.getRequestURL());
		SessionUser sessionUser = (SessionUser) session.getAttribute(Def.S_USER);

		request.setAttribute(Def.S_USER, sessionUser);

		String uri = request.getRequestURI();
		if(uri.startsWith("/api/") && sessionUser == null) {
			throw new Ex401("Login is required");
		}
		if((uri.equals("/board/save-form") || uri.matches("/users/\\d+/update-form"))
		&& sessionUser == null) {
			throw new Ex401("Login is required");
		}
		return true;
	}
}
