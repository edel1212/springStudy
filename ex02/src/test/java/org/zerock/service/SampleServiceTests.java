package org.zerock.service;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.AfterThrowing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.aop.LogAdvice;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class SampleServiceTests {
	
	@Autowired
	private SampleService sampleService;

	@Test
	public void testClass() {
		log.info(sampleService);
		/** @Description : AOP가 잘 적용되어 있기때문에 해당 smapleService의 인스턴스가  com.sun.proxy.$Proxy24 로되어있는것을
		 *                 log로 확인이 가능하다!
		 *                 */
		log.info(sampleService.getClass().getName());
	}


	@Test
	public void testAdd() throws Exception {
		log.info(sampleService.doAdd("12", "34"));
		/**
		 *  Result!
		 *		INFO : org.zerock.aop.LogAdvice - =============================================
		 *		INFO : org.zerock.aop.LogAdvice - ==================Before=====================
		 *	    INFO : org.zerock.aop.LogAdvice - =============================================
		 *	    INFO : org.zerock.service.SampleServiceImpl - Add Method Start!!!
		 *	    INFO : org.zerock.aop.LogAdvice - =============================================
		 *	    INFO : org.zerock.aop.LogAdvice - ==================After======================
		 *	    INFO : org.zerock.aop.LogAdvice - =============================================
		 * */
	}
	
	/** @See : logBeforeWithParamsMapVer Method === 주석 처리되어있음 내가 작성한 코드 */
	@Test
	public void testAddMapVer() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("f", "1");
		map.put("s", "13");
		log.info(sampleService.doAddMapVer(map));
	}
	
	/** @See : @AfterThrowing */
	@Test
	public void testAddException() throws Exception {
		log.info(sampleService.doAdd("ABC", "34"));
		/**
		 *  Result!
		 	INFO : org.zerock.aop.LogAdvice - =============================================
			INFO : org.zerock.aop.LogAdvice - ==================Before=====================
			INFO : org.zerock.aop.LogAdvice - =============================================
			INFO : org.zerock.aop.LogAdvice - params    :: ABC
			INFO : org.zerock.aop.LogAdvice - params    :: 34
			INFO : org.zerock.service.SampleServiceImpl - Add Method Start!!!
			INFO : org.zerock.aop.LogAdvice - =============================================
			INFO : org.zerock.aop.LogAdvice - ==================After======================
			INFO : org.zerock.aop.LogAdvice - =============================================
			INFO : org.zerock.aop.LogAdvice - Exception !!!!!!
			INFO : org.zerock.aop.LogAdvice - exception :::: java.lang.NumberFormatException: For input string: "ABC"
		 * */
	}
	
}
