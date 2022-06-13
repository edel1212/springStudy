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
	 * @description : pom에 .. maria db 주입해주자 .. 오류나서 찾느라 시간날림..
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
