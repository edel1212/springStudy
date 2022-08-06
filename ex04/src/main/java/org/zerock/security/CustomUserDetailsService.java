package org.zerock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberMapper;
import org.zerock.security.domain.CustomUser;

import lombok.extern.log4j.Log4j;

/**
 * @Description :
 * */
@Log4j
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.warn("Load User By UserName : " + username);
		
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
