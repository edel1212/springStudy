package org.zerock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTest {
	
	@Autowired
	private BoardService service;
	
	@Test
	public void testExist() {
		log.info(service);
		/**
		 * @description : assertNotNull 란?  객체가 Null인지 아닌지 체크하는 JUnit4 함수
		 * */
		assertNotNull(service);
	}
	
	/**
	 * @description : ✔Error로 인해 많이 애먹었음 그이유는
	 *               @See : root-contex.xml 부분에서 scan하는 부분을
	 *               <context:component-scan base-package="org.zerock.service"/>  [ 정답 ]
	 *               <mybatis-spring:scan base-package="org.zerock.service"/>     [ 오답 ]
	 *               컴포넌트 스캔이 아닌 mybatis 스캔으로 해서 오류가 났었던거임!!!
	 *               헷갈리지 말자!!e
	 * */
	@Test
	public void testGetList() {
		log.info("getList............");
		service.getList().forEach((i)->log.info(i));
	}
	
	@Test
	public void testRegister() {
		log.info("regisger Test...");
		BoardVO vo = new BoardVO();
		vo.setTitle("title");
		vo.setContent("content");
		vo.setWriter("writer");
		service.register(vo);
	}
	
	@Test
	public void updateTest() {
		BoardVO vo = new BoardVO();
		vo.setBno(5L);
		vo.setContent("updateTest");
		vo.setTitle("updateTitle");
		vo.setWriter("updateWriter");
		service.update(vo);
	}
	
	@Test
	public void deleteTest() {
		Long bno = 4L;
		//service.delete(bno);
	}
	
}
