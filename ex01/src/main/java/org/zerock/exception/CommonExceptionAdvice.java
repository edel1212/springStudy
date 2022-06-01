package org.zerock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

/**
 * @description : 예외 처리용 controller 
 *                * @ControllerAdvice는 공통 관심사로 관리하겠다는 의미 
 *                해당 객체가 스프링의 컨트롤러에서 발생하는 예외를 처리하는 존재임을 명시하는 용도
 *                * @ExceptionHandler(Exception.class)는 해당 메서드가 들어가는 예외타입을 처리한다는 것을 의미
 *                해당 어노테이션의 속성으로 Exception클래스를 지정하였으므로 모든 예외의 대한 처리를 except()메서드에서
 *                가능하게 했다
 *                !!!중요!!!✔
 *                ✔ 해당 controller를 사용하려면 servlet-context.xml에 등록이 필요하다!!
 *                
 * @see 		 : 500 error 와 404 error 는 따로 처리 하는것이 좋다!
 * 		  		   @500 error의 경우에는 아래와 같이 지정해주면 되지만
 * 		  		   @404 error의 경우에는 web.xml에 따로 처리를 해줘야한다 
 *        		   <init-param></init-param> 참고	              
 * */
@ControllerAdvice //AOP 이용!
@Log4j
public class CommonExceptionAdvice {

	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		log.info("Exection...." + ex.getMessage());
		/**
		 * 구체적인 error내용을 보고싶다면 Model을 이용해서 화면에 전달하면 좋다!
		 * 이런식으로 moedl.addAttribute를 통해 넘기면 해당 jsp에서 확인 가능!!
		 * */
		model.addAttribute("exception", ex);
		log.error(model);
		return "error_page";
	}
	
	/**
	 * @description : 404의 경우 web.xml에 지정이 필요하다
	 * */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex) {
		return "custom404";
	}
}
