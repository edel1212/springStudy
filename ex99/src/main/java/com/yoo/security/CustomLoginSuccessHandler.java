package com.yoo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

/**
 * @Description : 로그인이 성공하면 타게되는 Class
 *                성공 이후 특정한 동작을 제어하기위해 AuthenticationSuccessHandler를 
 *                구현한 후 security-context에서 bean주입을 통해 사용한다. 
 * */
@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		log.warn("Login Success");
		log.info("누가 먼저니??? ::: CustomLoginSuccessHandler");
		List<String> roleNames = new ArrayList<>();
		
		//권한 확인 한가지 종류의  권한이 아닐수 있으므로 배열로 들어옴
		authentication.getAuthorities().forEach(authority -> {
			roleNames.add(authority.getAuthority());
		});
	
		log.warn("ROLE NAMES :: " + roleNames);
		
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/sample/admin");
			return;
		}
		
		if(roleNames.contains("ROLE_MEMBER")) {
			response.sendRedirect("/sample/member");
			return;
		}
		
		//권한이 없을경우 그냥 기본 페이지로
		response.sendRedirect("/");
		
	}

	//__Eof__
}
 