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

/**
 * @Description : JDBC 테스트와 다른 점은 DataSource Test는 내가 
 *                root-context.xml 에 설정한 파일을 읽어서 DataSource를
 *                테스트 한다는 점이다! 따라서 @RunWith 와 @ContextConfiguration 이 필요한것이다!
 * **/
@RunWith(SpringJUnit4ClassRunner.class) //테스트시 필요한 클래스를 지정, pom의 spring-test가 추가되어있어야 한다!
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
			/**
			 * @Description :  JDBC에서는  con 변수를 만들때
			 * 					DriverManager.getConnection(..code..);
			 * 					를 사용했지만 해당 방법은 root-context에 서 주입된
			 * 					dataSource를 의존성 주입을 통해 불러 사용!
			 * */
			Connection con = dataSource.getConnection();
			log.info(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Mybatis Test!
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
