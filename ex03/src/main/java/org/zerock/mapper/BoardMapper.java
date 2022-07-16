package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardAttachVO;



public interface BoardMapper {
	public List<BoardAttachVO> getOldFiles();
}
