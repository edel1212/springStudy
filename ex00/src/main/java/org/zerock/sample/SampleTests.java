package org.zerock.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) //테스트시 필요한 클래스를 지정.
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")//어떤 설정 정보를 읽어 들여야 하는지를 명시한다
@Log4j
public class SampleTests {

	@Autowired
	private Restaurant restaurant;
	
	/**
	 * @description : SampleHotel에서는 setter 또는 @Autowired 를 사용하지 않고
	 *                생성사에서 주입하여 사용!! 
	 * */
	@Autowired
	private SampleHotel sampleHotel;
	
	@Test
	public void testExist() {
		assertNotNull(restaurant);
		
		log.info(restaurant);
		log.info("---------------------");
		log.info(restaurant.getChef());
	}
	
	@Test
	public void testExistHotel() {
		assertNotNull(sampleHotel);
		log.info(sampleHotel);
		log.info("---------------------");
		log.info(restaurant.getChef());
		//@NonNull을 지정해 주지않았음!
		//log.info(sampleHotel.getCheckClass());
	}
	
}
