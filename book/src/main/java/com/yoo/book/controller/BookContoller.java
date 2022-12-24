package com.yoo.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yoo.book.domain.PersonVO;

import lombok.extern.log4j.Log4j2;

@RequestMapping("/book")
@Log4j2
@Controller
public class BookContoller {

	@GetMapping("/list")
	public void getList(PersonVO personVO, Model model) {
		model.addAttribute("result", personVO);
		log.info("personVO ::: " + personVO);
		log.info("12123");
	}
	
	
	// URL 로 데이터 받는 방식 [API -ajax 로 call 함!!]
	@ResponseBody
	@GetMapping("/apiGet/{name}")
	public String apiTest(@PathVariable String name) {
		log.info("name :: " + name);
		return name; 
	}
	
}
