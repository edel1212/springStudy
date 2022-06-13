package org.zerock.service;

import org.springframework.stereotype.Service;
import org.zerock.mapper.Sample1Mapper;
import org.zerock.mapper.Sample2Mapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class SampleTxServiceImpl implements SampleTxService{

	private Sample1Mapper sample1Mapper;
	
	private Sample2Mapper sample2Mapper;
	
	@Override
	public void addData(String value) {
		log.info("addData...");
		
		log.info("sample1 Mapper.............");
		sample1Mapper.insertCol1(value);
		
		log.info("sample2 Mapper.............");
		sample2Mapper.insertCol2(value);
		
		log.info("end Service.............");
	}

}
