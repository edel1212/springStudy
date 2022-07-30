package org.zerock.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j;

/**
 * @Description : PasswordEncoder 권장으로 테스트를 위해 우회하기 우회하기 위한
 *                우회용 Class이다.
 * */
@Log4j
public class CustomNoOpPasswordEncoder implements PasswordEncoder{
	@Override
	public String encode(CharSequence rawPassword) {
		log.warn("before ::: " + rawPassword);
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		log.warn("mathces ::: " + rawPassword + " : " + encodedPassword);
		return rawPassword.toString().equals(encodedPassword);
	}

}
