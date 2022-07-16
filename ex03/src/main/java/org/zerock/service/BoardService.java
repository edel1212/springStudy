package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardAttachVO;

public interface BoardService {
	public List<BoardAttachVO> getOldFiles();
}
