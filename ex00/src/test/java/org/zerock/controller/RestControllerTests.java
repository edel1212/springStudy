package org.zerock.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zerock.domain.Ticket;

import com.google.gson.Gson;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration //Controller Test시 꼭 넣어주자!! 안넣어주면 Gson parsing error 나옴!!
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"
	//controller의 component는 servlet-context.xml에서 하므로 추가해 주어야함
   ,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Log4j
public class RestControllerTests {

	@Autowired
	private WebApplicationContext ctx;
	
	/**
	 * 의존성 주입 필요없음
	 * */
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	/**
	 * @description : 1) 아래의 메서드는 JSON으로 전달되는 데이터를 받아서 Ticket타입으로 반환합니다. 
	 *                이를 위해서는 해당 데이터가 JSON이라는 것을 명시해줄 필요가 있는데 MockMvc는 ConterntType()를
	 *                이용해서 전달하는 데이터가 무엇인지 알려줄 수 있다 
	 *                
	 *                2) 코드내의 Gson 라이브러리를 이용하여 java의 객체를 JSON문자열로 변환하기 위해 사용하였다.
	 * */
	@Test
	public void testConvert() throws Exception {
		Ticket ticket = new Ticket();
		ticket.setTno(12);
		ticket.setOwner("Admin");
		ticket.setGrage("AAA");
		
		String jsonStr = new Gson().toJson(ticket);
		log.info(jsonStr);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/sample/ticket").contentType(MediaType.APPLICATION_JSON).content(jsonStr));
	}
	
}
