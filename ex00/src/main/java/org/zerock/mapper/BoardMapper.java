package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;



public interface BoardMapper {
	
	/**
	 * @description : @Select란 ? xml을 사용하지 않고 Select Annotation을 달아서 쿼리를 넣고
	 *                사용이 가능하지만 잘쓰지 않음 이런게 있다고 기억만 해두자 
	 * **/
	@Select("SELECT * FROM tbl_board")
	public List<BoardVO> getListAnnotation();
	
	public List<BoardVO> getList();
	
	public BoardVO getBoard(Long bno);
	
	public int register(BoardVO vo);
	
	public int update(BoardVO vo);
	
	public int delete(Long bno);
}
