package org.zerock.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.stream.IntStream;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml" 
						/** @Description : security 설정도 읽기위해 아래 경로도 추가해 줘야함!  */
						,"file:src/main/webapp/WEB-INF/spring/security-context-Coustom-DB-Ver.xml"})
public class MemberTests {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private DataSource dataSource;
	
	//테스트용 계정 추가
	@Test
	public void testInsertMember() {
		
		String sql = "insert into tbl_member(userid,userpw, username) values(?,?,?)";
		
		IntStream.range(0, 100).forEach((num)->{
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				//DB연결
				con = dataSource.getConnection();
				//SQL 주입
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(2, passwordEncoder.encode("pw"+num));
				
				if(num < 70) {
					pstmt.setString(1, "user"+num);
					pstmt.setString(3, "일반사용자"+num);
				} else if(num < 80 ) {
					pstmt.setString(1, "manager"+num);
					pstmt.setString(3, "운영자"+num);
				} else {
					pstmt.setString(1, "admin"+num);
					pstmt.setString(3, "관리자"+num);
				}
				pstmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				if(con != null) {
					try {
						con.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				} 
			}
		});
	}//testInsertMember
	
	
	//테스트용 권한 추가
	@Test
	public void testInsertAuth() {
		
		String sql = "insert into tbl_member_auth(userid,auth) values(?,?)";
		
		IntStream.range(0, 100).forEach((num)->{
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				//DB연결
				con = dataSource.getConnection();
				//SQL 주입
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(2, passwordEncoder.encode("pw"+num));
				
				if(num < 70) {
					pstmt.setString(1, "user"+num);
					pstmt.setString(2, "ROLE_USER");
				} else if(num < 80 ) {
					pstmt.setString(1, "manager"+num);
					pstmt.setString(2, "ROLE_MEMBER");
				} else {
					pstmt.setString(1, "admin"+num);
					pstmt.setString(2, "ROLE_ADMIN");
				}
				pstmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				if(con != null) {
					try {
						con.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				} 
			}
		});
	}//testInsertMember
	
	
}
