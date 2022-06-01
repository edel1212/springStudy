package org.zerock.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/test/*")
@AllArgsConstructor
public class TestController {

	private BoardService boardService;
	
	@GetMapping(value="fetchTest")
	public String fetchTest() {
		return "/test/fetchTest";
	}
	
	@PostMapping(value="restCallTest")
	@ResponseBody
	public List<BoardVO> restCalltest() {
		return boardService.getList();
	}
	
}
