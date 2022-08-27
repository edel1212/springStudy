package com.yoo.controller;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoo.domian.BoardVO;
import com.yoo.domian.statusVo;
import com.yoo.service.BoardSvc;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("imRest/*")
@Log4j
public class RestTypeController {

	@Autowired
	private BoardSvc boardSvc ;
	
	@PostMapping("/getList")
	public List<BoardVO> test() {
		log.info("fetchTest!!!");
		List<BoardVO> result = boardSvc.getList();
		return result;
	}
	
	/**
	 * @Descripion : Spring Framework에서 제공하는 클래스 중 HttpEntity라는 클래스가 존재한다. 
	 * 				이것은 HTTP 요청(Request) 또는 응답(Response)에 
	 * 				해당하는 HttpHeader와 HttpBody를 포함하는 클래스이다. 
	 * 			    
	 *              ✅ ResponseEntity는 HttpStatus, HttpHeaders, HttpBody 3가지를 포함하여 반환이 가능하다.
	 * 				
	 * 				- 에러 코드와 같은 HTTP상태 코드를 전송하고 싶은 데이터와 함께 전송할 수 있기 때문에 좀 더 세밀한 제어가 필요한 경우 사용
	 * 				- http header에는 (요청/응답)에 대한 요구사항이
	 *              - http body에는 그 내용이
	 *              - Response header 에는 웹서버가 웹브라우저에 응답하는 메시지가 들어있고, Reponse body에 데이터 값이 들어가있다고 합니다.
	 * 
	 * 				 1) <반환 데이터 타입>
	 *               2) 필수적으로 HttpStatus를 매개변수에 추가하여 return 해줘야한다.
	 *  
	 *  
	 * */
	@GetMapping("/getData/{bno}")
	public ResponseEntity<statusVo> getData(@PathVariable Long bno){
		log.info("bno :::" + bno);
		//반환할 Map
		statusVo result = new statusVo();
		
		//Header Data Setting
		HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		//Body Data Setting
		List<BoardVO> dataList = boardSvc.getList();
		result.setDataList(dataList);
		result.setMsg("성공입니다.");
		result.setStatus("OK");
		
		return new ResponseEntity<statusVo>(result, header, HttpStatus.OK);
	}
	
	@PostMapping("/getListTypeResEntity")
	public ResponseEntity<List<BoardVO>> getList(){
		log.info("ResponeEntitiy : getList.");
		List<BoardVO> reslut = boardSvc.getList();
		return new ResponseEntity<List<BoardVO>>(reslut, HttpStatus.OK);
	}
	
	//성공 메세지
	@PostMapping("/successMsg")
	public ResponseEntity<String> successMsg(){
		log.info("suscessMsg!!!");
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
	
	
}	
