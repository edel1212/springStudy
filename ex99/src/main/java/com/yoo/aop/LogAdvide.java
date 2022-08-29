package com.yoo.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect 	/** @Description : 해당 클래스의 객체가 Aspect를 구현 한 것임을 나타내기 위함! */
@Log4j  	/** @Descriptonn : 로깅용 */
@Component 	/** @Description : AOP와 관련은 없지만 Spring에서 Bean을 인식하기 위해서 사용함! --- 없을 경우 AOP 작동 X */
public class LogAdvide {
	@Before( "execution(* com.yoo.service.*.*(..))" )
	public void logBefore() {
		log.info("=============================================");
		log.info("==================Before=====================");
		log.info("=============================================");
	}
	
	/**
	 * @Descrption : 코드를 실행하다 보면 파라미터값이 잘못되어서 예외가 발생하는데 AOP의 @AfterThrowing 어노테이션을
	 *               사용하면 지저오딘 대상이 예외를 발생한 후에 동작하면서 문제를 찾는데 도움을 준다
	 * */
	@AfterThrowing(pointcut = "execution(* org.zerock.service.*.*(..))", throwing="ex")
	public void logException(Exception ex) {
		log.info("Exception !!!!!!");
		log.info("exception :::: " + ex);
	}
	
	@Around( "execution(* org.zerock.service.*.*(..))" )
	public Object logTime( ProceedingJoinPoint pjp ) {
		
		long start = System.currentTimeMillis();
		
		log.info("TargetClass  ::: " + pjp.getTarget());
		log.info("Param   ::: " + Arrays.toString(pjp.getArgs())) ;
		
		//invoke method (호출하는 메서드)
		Object result = null;
		
		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		log.info("TIME ::: " + (end - start));
		
		return result;
	}
}
