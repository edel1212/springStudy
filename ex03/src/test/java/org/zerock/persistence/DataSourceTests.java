package org.zerock.persistence;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) //테스트시 필요한 클래스를 지정.
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")//어떤 설정 정보를 읽어 들여야 하는지를 명시한다
@Log4j
public class DataSourceTests {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * @description : 1) dataSource 확인은 JDBC 연결 확인이 선행 된 후 진행 해야한다!
	 * 				  
	 *                2) JUNIT test를 하기 위해서는
	 *                프로젝트 library에 JUNIT을 추가해주어야 한다!
	 *                프포젝트 우클릭 -> properties -> java build path -> library -> add 해주기!
	 * */
	@Test
	public void testConnection() {
		try {
			Connection con = dataSource.getConnection();
			log.info(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMybatis() {
		try {
			Connection con = dataSource.getConnection();
			SqlSession session = sqlSessionFactory.openSession();
			log.info(con);
			log.info(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
