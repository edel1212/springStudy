package org.zerock.service;

import org.springframework.stereotype.Service;


@Service
public class SampeServiceImpl implements SampleService{

	@Override
	public Integer doAdd(String str1, String str2) throws Exception {
		return Integer.valueOf(str1) + Integer.valueOf(str2);
	}

}
