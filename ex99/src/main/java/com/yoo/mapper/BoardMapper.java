package com.yoo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.yoo.domian.BoardVO;

public interface BoardMapper {
	@Select("SELECT * FROM tbl_board")
	public List<BoardVO> getBoard();
}
