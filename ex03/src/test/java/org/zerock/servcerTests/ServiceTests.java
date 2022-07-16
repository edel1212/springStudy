package org.zerock.servcerTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.service.BoardService;
import org.zerock.task.FileCheckTask;

import lombok.extern.log4j.Log4j;

@Log4j
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTests {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private FileCheckTask FileCheckTask;
	
	
	@Test
	public void getOldFiles() {
		log.info("getOldFiles!!!");
		boardService.getOldFiles().forEach(System.out::println);;
	}
	
	/**
	 * @Description : Task Test
	 * */
	@Test
	public void taskTest() {
		try {
			FileCheckTask.checkFiles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
