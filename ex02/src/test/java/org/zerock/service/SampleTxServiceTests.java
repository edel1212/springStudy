package org.zerock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SampleTxServiceTests {
	
	/**
	 * 
	 * @description : pom에 .. maria db 주입해주자 .. 오류나서 찾느라 시간날림..
	 * 
	 * */
	
	
	@Autowired
	private SampleTxService sampleTxService;
	
	/**
	 * @Desecription : Sample2Mapper에서는 허용 범위를 넘는 파라미터를 던져서
	 *                 하나만 터지게끔 유도
	 * */
	@Test
	public void testLongText() {
		String str = "asdasdasdasdasdasdasd"
				+ "asdasdasdasdasdasdasd"
				+ "asdasdasdasdadas"
				+ "dasdasdasdasdasdasdasd"
				+ "asdasdasdasdasdasd"
				+ "asdasdasdasdasdasd"
				+ "asdasdasdasdasdasd"
				+ "asdasdasdasdasdasdas"
				+ "dasdasdasdasasdasdasd"
				+ "asdasdasdasdasd"
				+ "asdasdasdasdasd"
				+ "asdasdasdasdasdasd"
				+ "asdasdasd";
		sampleTxService.addData(str);
	} 
	
}
