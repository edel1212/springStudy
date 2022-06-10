package org.zerock.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect 	/** @Description : 해당 클래스의 객체가 Aspect를 구현 한 것임을 나타내기 위함! */
@Log4j  	/** @Descriptonn : 로깅용 */
@Component 	/** @Description : AOP와 관련은 없지만 Spring에서 Bean을 인식하기 위해서 사용함! */
public class LogAdvice {

	/**
	 * @Description : 1) Advice와 관련 어노테이션들은 내부적으로 Pointcut을 지정함
	 *                   Pointcut은 별도의 @Pointcut 으로도 지정이 가능함
	 *                
	 *                2) 아래 메서드의 @Berfoe 또는 @After 어노테이션 내부의 execution...문자열ㅇ,ㄴ
	 *                   AspectJ의 포현식이다.  execution의 경우 접근제한자와 특정 클래스의 메서드를 지정할 수있다
	 *                   - 맨앞의 "*"의 의미는 접근제한자를 의미하고 맨뒤의 "*"은 클래스의 이름과 메서드의 이름을 의미한다
	 *              
	 *                3) Aspect 어노테이션 종류
	 *                   - @Before				// Target의 JoinPoint를 호출하기 전에 실행되는 코드 코드 자체에 관여는 불가능함
	 *					 - @AfterReturning		// 모든 실행이 정살적으로 이루어진 후에 동작하는 코드
	 *					 - @AfterThrowing		// 예외가 발생한 뒤에 동작하는 코드
	 *					 - @After				// 정상적으로 실행되거나 예외가 발생했을 때 구분 없이 실행되는 코드
	 *					 - @Around				// 메서드의 실행 자체를 제어할 수 있는 가장 강력한 코드 직접 대상 메서드를 호출하고 결과나 예외를 처리 가능함
	 * */ 
	
	
	
	@Before( "execution(* org.zerock.service.SampleService*.*(..))" )
	public void logBefore() {
		log.info("=============================================");
		log.info("==================Before=====================");
		log.info("=============================================");
	}
	
	@After( "execution(* org.zerock.service.SampleService*.*(..))" )
	public void logAfter() {
		log.info("=============================================");
		log.info("==================After======================");
		log.info("=============================================");
	}
}
