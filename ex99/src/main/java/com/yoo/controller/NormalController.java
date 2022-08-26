package com.yoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yoo.domian.BoardVO;
import com.yoo.mapper.BoardMapper;
import com.yoo.mapper.TimeMapper;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("normal/*")
public class NormalController {
	
	@Autowired
	private TimeMapper timeMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	
	@GetMapping("imVoid")
	public void voidType(Model model) {
		model.addAttribute("time", timeMapper.getTime());
		log.info("im void!!");
	}
	
	@GetMapping("imString")
	public String StringType(Model model) {
		log.info("im String!!");
		model.addAttribute("time", timeMapper.getTime());
		return "normal/imString";
	}
	
	/*********************************************************************************/
	@GetMapping("asyncTestJsp")
	public void asyncTestJsp() {
		log.info("asyncTestJsp!");
	}
	
	@PostMapping("asyncTest")
	@ResponseBody
	public List<BoardVO> async() {		
		log.info("async");
		return boardMapper.getBoard();
	}
	
	//__Eof__
}
