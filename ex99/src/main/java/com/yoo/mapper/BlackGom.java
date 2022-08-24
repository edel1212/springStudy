package com.yoo.mapper;

import org.apache.ibatis.annotations.Select;

public interface BlackGom {
	
	@Select("SELECT 'Wang!! Wang!!' AS test FROM DUAL")
	public String wanag();
	
	public String wanag2();
	
}
