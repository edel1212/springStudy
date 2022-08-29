package com.yoo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoo.domian.BoardVO;
import com.yoo.mapper.BoardMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BoardSvcImpl implements BoardSvc{

	@Autowired
	private BoardMapper boardMapper;
	
	@Transactional
	@Override
	public List<BoardVO> getList() {
		log.info("Serive :: getList");
		return boardMapper.getBoard();
	}

}
