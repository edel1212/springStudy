package com.yoo.book.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TestClass {

	@Autowired
	private TestMapper mapper;
	
	@Test
	public void getTime() {
		System.out.print(mapper.getTime());
	}
	
}
