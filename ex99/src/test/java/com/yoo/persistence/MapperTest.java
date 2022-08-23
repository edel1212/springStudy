package com.yoo.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yoo.mapper.TimeMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MapperTest {
	@Autowired
	private TimeMapper timeMapper;
	
	
	@Test
	public void testGetTime() {
		log.info(timeMapper.getTime());
	}
	
	@Test
	public void testGetTime2() {
		log.info(timeMapper.getTime2());
	}
	
	//TODO : 이어서작업할것 dir 경로 다르게 , namesapce 다르게 ,log4j 다르게, test에는 log4 없으면 안돼?
}
