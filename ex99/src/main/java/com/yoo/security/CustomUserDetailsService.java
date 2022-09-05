package com.yoo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.yoo.domian.MemberVO;
import com.yoo.mapper.MemberMapper;
import com.yoo.security.domain.CustomUser;

import lombok.extern.log4j.Log4j;

/**
 * @Description : 1) 로그인 후 success를 타기전 타는 로직으로 DB를 Custom 사여 사용한것이다.
 * 				     CustomUser를 이용하여 id , pw, auth를 기존 security에서쓰는
 * 				     username이 아닌 userid 변환 및 내가 원하는 정보를 추가하여 객체 변수 상태로
 *				     전달해 주며 그 후 로그인이 success인지 실패인지 확인하는 로직이다!
 *
 *				 
 * 				  	2) 여기서 핵심 로식은 	return vo == null ? null : new CustomUser(vo); 쪽
 * 						CustomUser.java의 생성자 쪽 로직이다!
 * */				
@Log4j
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.warn("Load User By UserName : " + username);
		
		log.info("누가 먼저니??? ::: loadUserByUsername");
		
		//받아온 ID를 통해 정보를 가져옴 pw 체크를 하진 않음
		MemberVO vo = memberMapper.getMemberInfo(username);
		
		log.warn("queried by member Mapper :: " + vo);
		
		/***
		 * 값이 있을 경우 CustomUser에 Mybatis를 통해 얻어온 데이터를
		 * 주입하여 객체변수를 return 해준다
		 * */
		return vo == null ? null : new CustomUser(vo);
	}

	
	
}
