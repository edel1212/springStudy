package org.zerock.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class CommonController {

	/**
	 * @Description : security-context에서 지정한  접근 거부시 URL을 Mapping할 
	 *                Controller이다
	 * */
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		
		log.info("access Denied ::: " + auth);
		
		model.addAttribute("msg", "Access Denied Black Gom Wang.. Wang");
		
	}
	
	/**
	 * @Description : 1) security-context에서 설정 되어 넘어옴
	 * 
	 * 				  2) 에러 또는 로그아웃 시 다시한번 해당 controller를
	 *                   불러와서 하단 조건문에서 체크 후 반환한다.
	 *                   
	 *                   
	 * @See         : customLogin.jsp
	 *                - csrf 및 form 주석 확인
	 *                
	 *                - 스프링 시큐리티에서 POST 방식을 이용하는 경우 기본적으로
	 *                  CSRF 토큰을 사용하게 되는데 사용 이유는 사이트간 위조 방지를
	 *                  목적으로 특정 값의 토큰을 사용하는 방식이다.
	 *                -    
	 *                   
	 * */
	@GetMapping("/customLogin")
	public void loginPage(String error, String logout, Model model) {
		
		log.info("error ::" + error);
		log.info("logout ::" + logout);
		
		//에러가 있을경우
		if(error != null) {
			model.addAttribute("error", "Login Error Check Your Account");
		}
		
		//로그아웃 시
		if(logout != null) {
			model.addAttribute("logout", "Logout!!");
		}
		
	}
	
}
