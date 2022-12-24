package com.yoo.book.service;

import org.springframework.stereotype.Service;

import com.yoo.book.mapper.TestMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class yooServiceImpl implements yooService{

	private final TestMapper mapper;

	@Override
	public String getTime() {
		// TODO Auto-generated method stub
		return mapper.getTime();
	}
	
}
