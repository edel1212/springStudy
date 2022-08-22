package org.zerock.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.TimeMapper;

import lombok.extern.log4j.Log4j;

/**
 * ✅ 잊지말아야 하는것은 내가 읽으려는 interface를 꼭 
 *    !!! 🎈 batis-spring:scan 스캔으로 빈등록을 해줘야한다는 것이다!!
 *    !!! 👿 context:component-scan 아님!!👿
 *     * **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MapperTest {

	/**
	 * @description : Interface로 만든 추상화된 함수를 읽어옴
	 * */
	@Autowired
	private TimeMapper timeMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	/**
	 * @description : src/main/resource 하위 mapper folder를 만드는 곳을
	 *                잘확인하자 !!!
	 *                
	 *                @see : META-INF 에 만들어서 오류가 났었음~!!👿👿👿
	 * */                
	@Test
	public void testGetTime() {
		log.info(timeMapper.getTime());
	}
	
	/**
	 * @description : 아래에서 조회하는 boardMapper의 getList 쿼리의 resultType은 List<BoardVO>이다
	 *                ✔따라서!! 해당 xml의 resultType이 정의 되어있어야한다!! 중요 안하면 error 발생
	 *                @See : src/main/resource/org/zerock/mapper/BoardMapper.xml
	 * **/
	@Test
	public void boardMapeerTest() {
		boardMapper.getList().forEach(i->log.info(i));
	}
	
	@Test
	public void getBoard() {
		BoardVO vo = boardMapper.getBoard(4L);
		boardMapper.getBoard(vo.getBno());
	}
	
}
