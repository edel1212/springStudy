package org.zerock.aop;

import java.util.Arrays;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

/**
 * @Description : 1) Advice와 관련 어노테이션들은 내부적으로 Pointcut을 지정함
 *                   Pointcut은 별도의 @Pointcut 으로도 지정이 가능함
 *                </br>
 *                </br>
 *                2) 아래 메서드의 @Berfoe 또는 @After 어노테이션 내부의 execution...문자열은
 *                   AspectJ의 포현식이다.  execution의 경우 접근제한자와 특정 클래스의 메서드를 지정할 수있다
 *                   - 맨앞의 "*"의 의미는 접근제한자를 의미하고 맨뒤의 "*"은 클래스의 이름과 메서드의 이름을 의미한다
 *                </br>
 *                </br>	
 *                3) Aspect 어노테이션 종류
 *                   - @Before				// Target의 JoinPoint를 호출하기 전에 실행되는 코드 코드 자체에 관여는 불가능함
 *					 - @AfterReturning		// 모든 실행이 정살적으로 이루어진 후에 동작하는 코드
 *					 - @AfterThrowing		// 예외가 발생한 뒤에 동작하는 코드
 *					 - @After				// 정상적으로 실행되거나 예외가 발생했을 때 구분 없이 실행되는 코드
 *					 - @Around				// 메서드의 실행 자체를 제어할 수 있는 가장 강력한 코드 직접 대상 메서드를 호출하고 결과나 예외를 처리 가능함
* */ 
@Aspect 	/** @Description : 해당 클래스의 객체가 Aspect를 구현 한 것임을 나타내기 위함! */
@Log4j  	/** @Descriptonn : 로깅용 */
@Component 	/** @Description : AOP와 관련은 없지만 Spring에서 Bean을 인식하기 위해서 사용함! --- 없을 경우 AOP 작동 X */
public class LogAdvice {

	@Before( "execution(* org.zerock.service.SampleService*.*(..))" )
	public void logBefore() {
		log.info("=============================================");
		log.info("==================Before=====================");
		log.info("=============================================");
	}
	
	
	/**
	 * @Descripton : 해당 메서드는 parameter를 log 에 남갈수있다!
	 *               기존 log만 남기는것과 다른것은  execution 부분에 메서드명과  && args가 들어간다는 것이다
	 * */
	@Before( "execution(* org.zerock.service.SampleService*.doAdd(String, String)) && args(str1, str2) " )
	public void logBeforeWithParams(String str1, String str2) {
		log.info("params    :: " + str1);
		log.info("params    :: " + str2);
	}
	
	
	/** @Description : 나의 방법 하지만 && args를 이용해서 설정은 간단히 파라미터를 찾아서 기록할떄는 
	 * 				   유용하지만 파라미터가 다른 여러종류의 메서드에 적용하기엔 
	 *  			   간단하지가 않음 그래서 @Around 와 @ProceedingJoinPoint를 이용해서 해결이 가능함 
	 *  
	 * @Before( "execution(* org.zerock.service.SampleService*.*(..)) && args(map) "
	 * ) public void logBeforeWithParamsMapVer(Object map) {
	 * log.info("params    :: " + map); }
	 */
	@After( "execution(* org.zerock.service.SampleService*.*(..))" )
	public void logAfter() {
		log.info("=============================================");
		log.info("==================After======================");
		log.info("=============================================");
	}
	
	
	/**
	 * @Descrption : 코드를 실행하다 보면 파라미터값이 잘못되어서 예외가 발생하는데 AOP의 @AfterThrowing 어노테이션을
	 *               사용하면 지저오딘 대상이 예외를 발생한 후에 동작하면서 문제를 찾는데 도움을 준다
	 * */
	@AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))", throwing="ex")
	public void logException(Exception ex) {
		log.info("Exception !!!!!!");
		log.info("exception :::: " + ex);
	}
	
	/**
	 * @Description : @Around 와 ProceedingJoinPoint를 사용하면 좀 더 구체적인 처리가 가능하다
	 *				  @Around는 조금 특별하게 동작하는데 직접 대상 메서드를 실행할 수있느 권한을 가지고
	 *  			  메서드의 실행 전과 실행 후에 처리가 가능하다
	 *  
	 *   			  @Around 와 ProceedingJoinPoint 두가지를 결합해서 파라미터나 예외등을 한번에 처리가 가능하다 
	 *   
	 *   			  @Before 등과 달리 @Around가 적용되는 메서드의 경우에는 리턴 타입이  void가 아닌 타입으로 설정하고
	 *                메서드의 실행 결과 역시 직접 반환 형태로 작성해야한다
	 *                
	 *                실행 결과를 보면 @Aroind가 먼저 실행된 후 @Before 등이 실행된 후에 메서드가 실행되는 시간이 로그에
	 *                기록되는것을 확인할 수있다.
	 *                
	 *                		INFO : org.zerock.aop.LogAdvice - Target  ::: org.zerock.service.SampleServiceImpl@68ace111
							INFO : org.zerock.aop.LogAdvice - Param   ::: [ABC, 34]
							INFO : org.zerock.aop.LogAdvice - =============================================
							INFO : org.zerock.aop.LogAdvice - ==================Before=====================
							INFO : org.zerock.aop.LogAdvice - =============================================
							INFO : org.zerock.aop.LogAdvice - params    :: ABC
							INFO : org.zerock.aop.LogAdvice - params    :: 34
							INFO : org.zerock.service.SampleServiceImpl - Add Method Start!!!
							INFO : org.zerock.aop.LogAdvice - TIME ::: 10
							INFO : org.zerock.aop.LogAdvice - =============================================
							INFO : org.zerock.aop.LogAdvice - ==================After======================
							INFO : org.zerock.aop.LogAdvice - ============================================
	 * */
	@Around( "execution(* org.zerock.service.SampleService*.*(..))" )
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
