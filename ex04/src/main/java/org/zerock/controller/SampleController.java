package org.zerock.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {

	/**
	 * @Description : 1) 스프링 시큐리티에서 가장 중요한 역할을 하는 존재는
	 *                   AuthenticationManager(인증 매니저)이다 이것이 가장
	 *                   최상단의 위치해 있으며 다양한 방식의 인증을 처리할 수 
	 *                   있는 구조로 되어있다!
	 *                
	 *				-----------------------------------------         
	 *                
	 *                AuthenZticationManager
	 *                
	 *                          🔽
	 *                          
	 *                    ProviderManager ( 실제 인증 작업을 진행하며 이때 인증된 정보에는
	 *                    				    권한에 대한 정보를 같이 전달하게 되는데 이 처리는
	 *                                      UserDetailsServcie 라는 존재와 관련이 있다. )
	 *                                      
	 *                                      * UserDetailsServcie?
	 *                                        - 인터페이스의 구현체로 실제로 사용자의 정보와
	 *                                          사용자가 가진 권한의 정보를 처리해서 반환하게 된다. 
	 *                    		🔽
	 *                    
	 *                    	하위.......  
	 *                    
	 *				-----------------------------------------
	 *
	 *         		 2) 개발자가 스프링 시큐리티를 커스터마이징 하느 방식은 크게
	 *         			AuthenticationProvider를 직접 구현하는 방식과 실제 처리를
	 *                  담당하는 UserDetialsService를 구현하는 형태를 구현하는것 만으로 충분 하지만
	 *                  새로운 프로토콜이나 인증 구현 방식을 직접 구현하는 경우에는 AuthentocationProvider
	 *                  인터페이스를 직접 구현해서 사용해야함
	 * */
	
	@GetMapping("/all")
	public void doAll() {
		log.info("dp all cam access everbody");
	}
	
	@GetMapping("/member")
	public void doMember() {
		log.info("logined member");
	}
	
	@GetMapping("/admin")
	public void doAdmin() {
		log.info("admin only");
	}
	
	@GetMapping("/blackGome")
	public void doGom() {
		log.info("yoo only");
	}
	
	
	/** = = = = = = = = = = = = = = = = =  = = = = = = = = */
	/**
	 * @Description : 매번 security 설정의 <security:http>에 URL을 등록하는것은
	 *                굉장히 번거러운 일이기에 어노테이션을 이용해서 설정하여 사용이 가능하다
	 *                
	 *                ✅ 단 주의해야할 것은 해당 어노테이션을 사용하라면 security를 설정한 xml이 아닌
	 *                   <b>
	 *                   	servlet-context.xml의 namespace에 security를 체크해줘야한다는 것이다!
	 *                   	 - 그 후 <security:global-method-security/ >설정을 해주면된다
	 *                         @see : servlet-context.xml 
	 *                   </b> ㄴ
	 * */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	@GetMapping("/annoMember")
	public void doAnnoMember() {
		log.info("Logined Annotation member!");
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/annoAdmin")
	public void doAnnoAdmin() {
		log.info("Logined annotation Admin!");
	}
}
