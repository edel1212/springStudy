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
	
}
