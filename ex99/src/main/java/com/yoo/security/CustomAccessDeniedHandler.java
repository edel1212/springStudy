package com.yoo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;

/**
 * @Description : 해당 클래스는 AccessDeniedHandler 인터페이스를 직접 구현하였고
 * 				  해당 인터페이스의 메서드는 handle()뿐이기 떄문에 
 * 				  HttpServletRequest, HttpServletPesponse를 파라미터로
 * 				  사용할 수 있기때문에 직접적으로 서블릿 API를 이용하여 처리가 가능하다
 * 
 *                
 *                @See : root-context에 bean을 주입하지 않고
 *                       security-context에서 bean을 주입해줌
 * */
@Log4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		log.error("Access Denied Handler");
		
		log.error("Redirect....");
		
		// 이동 시켜버린다.
		response.sendRedirect("/accessError");
		
	}

	
	
}
