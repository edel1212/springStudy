package com.yoo.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.yoo.domian.MemberVO;

import lombok.Getter;

/**
 * @Desripction : 해당 class는 Security에서 제공하는
 *  			  User.java 파일을 상속 받아 사용하며
 *  			  로그인에 관한 정보를 갖는 class이다.
 *  
 *  			  ✅ 해당 class를 사용 하는 이유는 
 *                   기존에 제공하는 Security의 로그인 정보 말고도
 *                   다른 다양한 정보를 추가하여 사용 가능
 *                
 *                ✅ username으로 ID를 사용하는데 이러한 헷갈릴 수
 *                   있는 변수를 내가 커스텀하여 사용 이 가능하단 점이다
 *                   
 *                ✅ 해당 객체는 부모 class읜 User의 생성에 필요한
 *                   변수를 맞춰서 넣어준 후 인증에 사용학ㅎ
 *                   변수인 MemberVO member에 데이터를 주입
 *                   ex) this.member = vo 
 *                   로 넣어주어 불러서 사용이 가능하다는 장점이 있다!
 *                   @see : admin.jsp << security tag를 이용해서 사용중
 *                   
 *                   * jsp 내 tag 명령어
 *                     - hasRole(??)       		: 해당 권한이 있으면 TRUE를 반환
 *                     - hasAuthority??()  		: 해당 권한이 있으면 TRUE를 반환
 *                     
 *                     - hasAnyRole([??,??])    : 여러 권한중 하나라도 해당하면 TURE
 *                     - hasAnyAuthority([???]) : 여러 권한중 하나라도 해당하면 TURE   
 *                     
 *                     - principal 				: 현재 사용자의 정보를 의미
 *                     - permitAll				: 모든 사용자에게 허용
 *                     - denyAll				: 모든 사용자에게 거부
 *                     
 *                     - isAnonymous() 			: 익명의 사용자의 경우 로그인을 하지 않은 경우에 해당
 *                     - isAuthenticated() 		: 인증된 사용자라면 TRUE
 * **/
@Getter
public class CustomUser extends User{

	private static final long serialVersionUID = 1L;
	
	private MemberVO member;
	
	public CustomUser(String username
					, String password
					, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public CustomUser(MemberVO vo) {
		super( vo.getUserid()
			 , vo.getUserpw()
			 , vo.getAuthList().stream()
			 				   .map(auth -> new SimpleGrantedAuthority(auth.getAuth())) // 권한 목록은 SimpleGrantedAuthority을사용해서 변환해 줘야한다!
			 				   .collect(Collectors.toList())
			 );
		this.member = vo;
	}

}
