package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	/**
	 * @description : spring 4.3부터는 단일 파라미터를 받을경우 안써도 되고 또한
	 *                @AllArgsConstructor를 사용하여 모든 파라미터를 이용하는 생성자를 자동으로 
	 *                만들어주어서 따로 @Autowired를 안써줘도 된다!!
	 * */
	//@Autowired
	private BoardMapper mapper;
	

	@Override
	public List<BoardVO> getList() {
		// TODO Auto-generated method stub
		log.info("get List......");
		return mapper.getList();
	}

	@Override
	public BoardVO getBoard(Long bno) {
		// TODO Auto-generated method stub
 		return mapper.getBoard(bno);
	}

	@Override
	public int register(BoardVO vo) {
		// TODO Auto-generated method stub
		return mapper.register(vo);
	}

	@Override
	public int update(BoardVO vo) {
		// TODO Auto-generated method stub
		return mapper.update(vo);
	}

	@Override
	public int delete(Long bno) {
		// TODO Auto-generated method stub
		return mapper.delete(bno);
	}

}
