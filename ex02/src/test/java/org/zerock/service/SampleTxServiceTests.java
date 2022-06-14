package org.zerock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class SampleTxServiceTests {
	
	/**
	 * 
	 * @description : pom에 .. maria db 주입해주자 .. 오류나서 찾느라 시간날림..
	 * 
	 * */
	
	
	@Autowired
	private SampleTxService sampleTxService;
	
	/**
	 * @Desecription : 1) Sample2Mapper에서는 허용 범위를 넘는 파라미터를 던져서
	 *                    하나만 터지게끔 유도
	 *                 - 1번과 같이 하면 tbl_sample1에는 값이 들어가고 tbl_sample2에는 들어가지 않는 문제가 발생
	 *                 
	 *                 2) 그래서 pom -> root-context 에서 주입 및 bean 등록한 transaction(tx)를 사용하면 된다
	 *                 - pom과 root-context에 설정을 완료 했으면 사용하려는 service단에서 @Transactional 어노테이션을 달아주자!
	 *                   @See : SampleTxServiceImpl.java
	 *                    적용 후 확인 하면 둘 중 하나라도 실패하면 둘다 insert가 되지 않는다!
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
