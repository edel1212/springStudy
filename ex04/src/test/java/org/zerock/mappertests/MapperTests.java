package org.zerock.mappertests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberMapper;
import org.zerock.mapper.TimeMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MapperTests {

	@Autowired
	private MemberMapper mapper;
	@Autowired
	private TimeMapper timeMapper;
	
	@Test
	public void test() {
		timeMapper.getTime();
	}
	
	@Test
	public void memberTest() {
		MemberVO vo = mapper.getMemberInfo("admin90");
		log.info(vo);
		
		//vo.getAuthList().forEach(System.out::println);
	}
	
}
