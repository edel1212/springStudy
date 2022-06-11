package org.zerock.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;


@Service
@Log4j
public class SampleServiceImpl implements SampleService{

	@Override
	public Integer doAdd(String str1, String str2) throws Exception {
		log.info("Add Method Start!!!");
		return Integer.valueOf(str1) + Integer.valueOf(str2);
	}

	@Override
	public Integer doAddMapVer(Map<String, String> map) throws Exception {
		log.info("Add Method MapVersion Start!!!");
		return Integer.valueOf(map.get("f")) +  Integer.valueOf(map.get("s"))  ;
	}

}
