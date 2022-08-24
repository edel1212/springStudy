package com.yoo.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yoo.mapper.BlackGom;
import com.yoo.mapper.TimeMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MapperTest {
	@Autowired
	private TimeMapper timeMapper;
	
	@Autowired
	private BlackGom blackGom;
	
	@Test
	public void testGetTime() {
		log.info(timeMapper.getTime());
	}
	
	@Test
	public void testGetTime2() {
		log.info(timeMapper.getTime2());
	}
	
	@Test
	public void wangTest() {
		System.out.println(blackGom.wanag());
	}
	
	@Test
	public void wangWangTest() {
		System.out.println(blackGom.wanag2());
	}
	
	
	//,log4j 다르게,

}
