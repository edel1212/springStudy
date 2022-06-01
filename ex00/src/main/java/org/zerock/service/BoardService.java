package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;

public interface BoardService {
	
	/**
	 * @description : 1) mapper 또는 DAO [이름은 둘중 하나로!]패키지 내부의 Interface에서
	 *                   src/main/resource 에서 만든 xml에서 사용할 queryID를 지정해준다 
	 * 				  2) service 패키지에서 Interface를 만든 후 사용할 
	 *                   queryId에 맞는 함수를 한번 더 명세해준다.
	 *                3) 사용할 class(*Impl)에서 1번에서 만든 interface를 구현한 class에서
	 *                   함수를 구현 후 mapper 또는 DAO 에서를 변수로 불러온 후
	 *                   비즈니스 로직을 구현한 후 return 시킨다!
	 * */
	public List<BoardVO> getList();
	
	public BoardVO getBoard(Long bno);

	public int register(BoardVO vo);
	
	public int update(BoardVO vo);
	
	public int delete(Long bno);
	
}
