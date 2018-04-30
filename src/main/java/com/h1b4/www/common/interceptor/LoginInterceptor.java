package com.h1b4.www.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override 
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//로그인이 되어 있는지를 판단해서 
		//세션 획득
		HttpSession session = request.getSession();
		//값이 있는지 판단
		String member_id = (String)session.getAttribute("loginId");
		if(member_id == null){	
			//로그인이 되어있지 않을 경우에는 로그인 폼으로 이동
			
			String path = request.getContextPath();
			response.sendRedirect(path+"/member/loginForm");//항상 여기로 절대 경로로설정
			return false;  
			
		}

		//로그인이 되어있지 않을 경우에는 로그인 폼으로 이동
		//루트 경로를 구한다.
		

		//로그인이 되어 있을 경우에는 원래 요청 주소로 이동 
	
		return super.preHandle(request, response, handler);
	}
	
	
	
}
