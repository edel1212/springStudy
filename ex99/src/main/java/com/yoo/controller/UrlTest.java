package com.yoo.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("urlTest/*")
public class UrlTest {
	
	@GetMapping("go1")
	public void goPage() {
		log.info("go1!!");
	}
	
	@GetMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String hello(){
		log.info("test!!");
    	return "hello";
    }
	
	@PostMapping( value ="/hello", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String helloPost(){

		return "hello";
	    
	 }
}	
