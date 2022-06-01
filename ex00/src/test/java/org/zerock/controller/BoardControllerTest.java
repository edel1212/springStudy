package org.zerock.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"
						//controller의 component는 servlet-context.xml에서 하므로 추가해 주어야함
					   ,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" 
})
/**
 * @description : Servlet의 SerlvetContext를 용하기 위해서는 @WebAppConfiguration을 사용해아한다.
 * */
@WebAppConfiguration
@Log4j
public class BoardControllerTest {
	
	@Autowired
	private WebApplicationContext ctx;
	
	/**
	 * @description : MockMvs는 말그대로 가짜 mvc라 생각하면 된다
	 *                가까로 URL과 파라미터등을 브라우저에서 사용하는것 처럼 만들어 Controller를 실행해 볼수 있다
	 * */
	private MockMvc mockMvc;
	
	/**
	 * @description : import할 떄 JUnit을 이용해야하는데
	 *                해당 @Before가 적용된 메서드는 모든 테스트전에 매번 실행되는 메서드가 된다. 
	 * */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	/**
	 * @description : 해당 테스트시 에러가있었음
	 *               이유) meavn의 javax.servlet 버전이 3버전 밑이 었기에
	 *                    session객체를 생성하지 못해서였음 따라서 해당 메이든의
	 *                    버전을 3.1.0으로 올리는 조치를 위함! 
	 * */
	@Test
	public void testList() throws Exception {
		log.info(
					mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
					.andReturn()
					.getModelAndView()
					.getModelMap()
				);
	}
	
	@Test
	public void testRegister() throws Exception {
		log.info(
					mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
							//아래와 같이 param을 사용해서 parameter 전달이 가능하다.
							.param("title", "테스트")
							.param("content","테스트 contents")
							.param("writer","test writer"))
					.andReturn()
					.getFlashMap()
				);
	}
	
	@Test
	public void testBoad() throws Exception {
		log.info(
					mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
							.param("bno", "10"))
					.andReturn()
					.getModelAndView()
					.getModelMap()
				);
	}
	
	@Test
	public void testModify() throws Exception{
		
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
				.param("bno", "10")
				.param("content", "modifyContent")
				.param("writer", "modifyWriter")
				.param("title", "titleModify"))
		.andReturn()
		.getModelAndView()
		.getViewName();
		log.info("ResultPageName" + resultPage);
	}
	
	@Test
	public void testRemove() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
				.param("bno", "5")
				).andReturn()
				.getModelAndView()
				.getViewName();
		log.info("ResultPage Name ::: " + resultPage);
	}
}
