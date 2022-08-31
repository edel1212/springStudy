package com.yoo.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("upload")
public class UploadController {
	
	/**
	 * @Description : 일반 POST방식
	 * 
	 * @See 		: uploadForm.jsp
	 * 
	 *                문제) ❌해당 URL을 사용해서 uploadFormAction로 POST 파일 업로드시
	 *					   한글이 깨진다.  
	 *				
	 *				  이유) 클라이언트에서 euc-kr로 인코딩 되어 utf-8 인코딩 셋을 
	 *					   사용하는 서버로 전송되는 과정에서 한글이 깨지는 것이다.
	 *		
	 *
	 *  			  해결방법) accept-charset="UTF-8"를 form에 추가해준다.
	 *  					  폼 데이터를 서버로 전송할 때 사용되는 문자 인코딩(character encoding) 방식을 명시하는 것이다.
	 *                        - ex) <from ... enctype="multipart/form-data"  accept-charset="UTF-8">
	 *                 
 	 * */
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}
	
	/**
	 * @Desription :  비동기 통신 방식
	 * 
	 * @See 		: uploadAjax.jsp
	 * 
	 *                ✔ 해당 URL을 사용해서 uploadFormAction로 POST 파일 업로드시
	 *					 한글이 깨지지 않는다!!
	 * */
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	
	/**
	 * @Description : 1) 파일처리는 스프리에서 제공하는 MultipartFile(interface)를 사용
	 *                   화면에서 넘기는 여러가지 파일은 배열로 받아서 처리가 간능함
	 *                
	 *                2) MultipartFile이 가지고 있는 Method
	 *                  - getName() 			:: 파라미터의 이름 input Tag의 이름
	 *                  - getOriginalFileName 	:: 업로드 되는 파일 이름
	 *                  - isEmpty() 			:: 파일이 존재 유/무
	 *                  - getSize() 			:: 업로드되는 파일의 크기
	 *                  - getBytes() 			:: byte[]로 파일 데이터 변환
	 *                  - transferTo(File file) :: 파일을 저장함
	 *                  
	 *@See			: uploadFormAction.jsp                  
	 * */
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		for(MultipartFile multipartFile : uploadFile) {
			
			//저장될 file Folder 경로
			String uploadFolder = "C:\\upload" ;
			
			log.info("--------------------------------------------");
			log.info("Upload File Name :::: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size ::::"  + multipartFile.getSize());
			
			//(경로 , 파일 이름 그대로 확장자도 같이 넘어감!)
			File saveFile = new File(uploadFolder,multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			
		}
	}
	
	
	
	
}
