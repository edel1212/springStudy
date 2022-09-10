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
 * @Description : 1) 클라이언트 단에서 넘겨준 데이터를 기준으로 UserDetails를 반환하는 Class
 * 				     CustomUser를 이용하여 id , pw, auth를 기존 security에서쓰는
 * 				     username이 아닌 userid 변환 및 내가 원하는 정보를 추가하여 객체 상태로
 *				     전달해 주며 반환값의 유무로 로그인 성공 필패를 분기해준다.
 *
 *				 
 * 				  	2) 여기서 핵심 로식은 	return vo == null ? null : new CustomUser(vo); 쪽
 * 						CustomUser.java의 부모Class의 super()에서 생성자 데이터를 만들어줄때 비밀번호를 체크한다!
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
		 * 
		 * ✔ 해당 CustomUser객체를 생성할때 pw를 확인한다.
		 *    - 해당 class의 부모 class인 User의 생성자에서!o
		 * */
		return vo == null ? null : new CustomUser(vo);
	}

	
	
}
