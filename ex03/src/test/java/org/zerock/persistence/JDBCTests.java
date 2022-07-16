package org.zerock.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTests {
	
	/**
	 * @description : DB연결 확인시 가장 선행 되어야 하는 테스트
	 * 				  JUNIT Library 유무 확인 필수!
	 * */
	
	static {
		try {
			/**
			 * @description : Class.forName의 역할
			 *                class는 JVM에서 동작할 클래시들의 정보를 묘사하는 일종의 메타클레스
			 *                인데 보통 static 블럭에서 사용하기에 반환값을 받는 변수가 없어도 참조카운트가
			 *                0이 되어서 GC에 의해서 죽지 않음!!
			 *                
			 *                * 동적 로딩이라 생각해도된다.
			 *                동적로딩이란? : 어떠한 클래스가 로딩 될지 모르기 떄문에 Class 클래스의
			 *                forName()함수를 이용해서 해당 클래스를 메모리로 로드하는것!
			 * */
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnection() {
		try {
			Connection con = DriverManager.getConnection(
					"jdbc:mariadb://127.0.0.1:3310/yoodb",
	                "root",
	                "123");
			log.info(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
