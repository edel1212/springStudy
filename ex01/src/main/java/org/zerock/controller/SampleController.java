package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;


@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {

	@RequestMapping("")
	public void basic() {	
		log.info("basic.......");
	}

	/**
	 * @description : value : URL , method : get post put delete 배열 형태로 주입가능
	 * **/
	@RequestMapping(value="/basic", method= {RequestMethod.GET})
	public void basicGet(){
		log.info("baisc get!!...");
	}
	
	
	/**
	 * See: ~Mapping으로 get , post, put , delete 등 설정 가능!
	 * */
	@GetMapping("basicOnlyGet")
	public void basicGetOnly() {
		log.info("basic only get!!!!");
	}
	
	@PostMapping("basicOnlyPost")
	public void basicPostOnly() {
		log.info("basic post only !!");
	}
	
	/**
	 * @description : 자동으로 DTO/VO 형식으로 파라미터를 가져온다!
	 * */
	@GetMapping("/autoParamGet")
	public String parameterGet(SampleDTO dto) {
		log.info(dto);
		return "";
	}
	
	/**
	 * @description : 받는 형식에 맞춰서 parameter 설정가능!! DTO/VO가 필요없다
	 * */
	@RequestMapping(value="/setTypeParmaGet", method= {RequestMethod.GET})
	public String setTypeParamGet(@RequestParam("name") String name,@RequestParam("age")int age) {
		log.info("name ==> "+name);
		log.info("age ==> "+age);
		return null;
	}
	
	/**
	 * @description : 이런식으로 배열로 받는것도 가능하다
	 * */
	@GetMapping("/setListTypeParamGet")
	public String setListTypeParamGet(@RequestParam("name") List<String> nameArr) {
		log.info(nameArr);
		return null;
	}
	
	/**
	 * @description : SampleDTOList<SmapleDTO> list 형식으로도 받아 올 수 있다 SampleDTO의 형식을 배열로 !!
	 * */
	@GetMapping("/setUseVOListParamGet")
	public String setUseVOListParamGet(SampleDTOList list) {
		log.info(list.toString());
		return null;
	}
	
	/**
	 * @description : Failed to convert property value of type 'java.lang.String' to 
	 * 				  required type 'java.util.Date' for property 'dueDate'; nested exception is
	 *  			  org.springframework.core.convert.ConversionFailedException: Failed
	 *   			  to convert from type [java.lang.String] to type [java.util.Date]
	 *     			  for value '2022-02-03'
	 *     
	 *     			  @see : InintBinder가 없을경우 위의 에러가 나오나 해당 어노테이션 없이도 DTO 자체에서도 해당 Date형식 변수에 
	 *                       @DateFimeFormat 를 사용 해서 처리가 가능하다!! 확인은 TodoDTO의 dueDate 변수를 확인하자!  
	 * */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	
	@GetMapping("/howtoGetDate")
	public String howtoGetDate(TodoDTO todo) {
		log.info("todo : "+todo);
		return null;
	}
	
	/**
	 * @description : 따로 Model로 넘겨주지 않아도 SampleDTO경우 자동으로 jsp에서 ${sampleDTO}로 받아 사용이
	 *                가능하다 spring에서 자동 앞 대문자를 소문자로 변환하여 전달해주지만 기본형인 page의 경우 view단에서 나오지 않음!!
	 *                
	 *                @see : 따라서  int page 부분을 ModelAtrribute로 묶어주면 값이 넘어간다
	 * */
	@GetMapping("/ex04")
	//public String ex04(SampleDTO dto , int page) {
	public String ex04(SampleDTO dto , @ModelAttribute("page") int page) {
		log.info("dto : " + dto);
		log.info("page : " + page);
		return "/sample/ex04";
	}
	
	/**
	 * @description : 굳이 method의 type이 String일 필요는 없다!
	 *                void 형식이여도 가능!! 단 .jsp의 file명은 URL 명을 따른다!
	 * */
	@GetMapping("ex05")
	public void typeIsVoid() {
		log.info("jsp File same is URL");
	}
	
	/**
	 * @description : JSON 넘기기! pom에 jackson.core 필수이며
	 *                따로 화면이 jsp파일이 없어도 바로 내가 넘긴 dto가 화면에 찍힌다!
	 * */
	@GetMapping("ex06")
	public @ResponseBody SampleDTO ex06(){
		SampleDTO dto = new SampleDTO();
		dto.setAge(30);
		dto.setName("yoo");
		return dto;
	}
	
	/**
	 * @description : Http 해더 정보나 데이터 정보를 보내기 위해서는
	 *                ResponseEntity 데이터 Type로 보내주면 된다!
	 * */
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07(){
		log.info("/ex07...");
		//{"name" : "yoo"};
		String msg = "{\"name : \" : \"yoo\"}";
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "applcation/json;charset=UTF-8");
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
	
	/**
	 * @description : file upload URL
	 * */
	@GetMapping("exUpload")
	public void exUpload() {
		log.info("/exUpload~~!");
	}
	
	/**
	 * @description : exUpload.jsp의 form 에서 post형식으로 던진
	 *                file을 받음 
	 * @param : ArrayList<MultipartFile> {files}                
	 * */
	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		files.forEach(file->{
			log.info("------------");
			log.info("name : " + file.getOriginalFilename());
			log.info("size : " + file.getSize());
		});
	}
	
}
