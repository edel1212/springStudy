package org.zerock.controller;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Ticket;

import lombok.extern.log4j.Log4j;

/**
 * @description : 1) RestController는 jsp와 달리 순수한 데잍를 반환하는 형태이므로 
 *                다양한 포켓의 데이터를 전송할 수 있다
 *                2) 기존에 사용하던 @Controller는 문자열을 반환하는 경우 JSP의 파일 이름으로 화면을 불러오지만
 *                @RestController의 경우에는 문자열을 반환하는 경우 순수 데이터를 반환한다!
 * 				
 * 
 * @See         : ✔ JSON이나 XML 형식을 넘길 거면 pom.xml에 json에 관련된 meaven 주입 필수!
 * */
@RestController
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
	
	/**
	 * @description : 아래의 @GetMapping에서 사용된 produces는 해당 메서드가 생성하는 MIME 타입을 의미한다
	 *                아래와 같이 문자열로 직접 지정할수도 있고 메서드 내의 MediaType이라는 클래스를 이용할 수도 있음!
	 * */
	@GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		log.info("MINE TYPE : :: " + MediaType.TEXT_PLAIN_VALUE );
		return "문자 전달 테스트";
	}
	
	@GetMapping(value = "/getSample", produces = {MediaType.APPLICATION_XML_VALUE,
												  MediaType.APPLICATION_JSON_UTF8_VALUE})
	public BoardVO getSemple() {
		log.info("getSample Data");
		BoardVO vo = new BoardVO();
		vo.setBno(12L);
		vo.setContent("content");
		vo.setTitle("yoo");
		return vo;
	}
	
	/**
	 * @description : 1) GetMapping어노테이션 내부에서 produces를 생략이 가능하다!!
	 *                2) 해당 url을 불러오면 ex) http://localhost:8081/sample/getSample2는 xml로 화면 출력
	 *                                      http://localhost:8081/sample/getSample2.json는 json으로 화면 출력
	 * */
	@GetMapping(value = "/getSample2")
	public BoardVO getSample2() {
		log.info("getSample Data2");
		BoardVO vo = new BoardVO();
		vo.setBno(20L);
		vo.setContent("content2");
		vo.setTitle("yoo2");
		return vo;
	}
	
	@GetMapping(value = "/getList")
	public List<BoardVO> getList(){
		log.info("getList");
		List<BoardVO> voList = new LinkedList<BoardVO>();
		for(int i = 0 ; i < 10 ; i ++){
			BoardVO data = new BoardVO();
			data.setBno( Long.valueOf(i)  );
			voList.add(data);
		}
 		return voList;
	}
	
	@GetMapping(value = "/getMap")
	public Map<String, BoardVO> getMap(){
		log.info("getMap ....");
		Map<String, BoardVO> map = new HashMap<String, BoardVO>();
		BoardVO vo = new BoardVO();
		vo.setBno(12L);
		vo.setContent("content");
		vo.setTitle("title");
		map.put("First", vo);
		return map;
	}
	
	/**
	 * @description : REST 방식으로 호출하는 경우는 화면 자체가 아니라 데이터 자체를 전소앟느 방식임
	 *                따라서 데이터를 요청한 쪽에서는 데이터가 정상적인 데이터인지 비정삭저깅ㄴ 데이터인지를 구분할 수있는 방법응 제공해야한다.
	 *                아래에서 사용한 ResponseEntity는 데이터와 함께 HTTP 헤더의 상태 메세지를 함께 전달하는 용도로 사용하어
	 *                받는 입장에서는 확실하게 결과를 확인 가능함!
	 *                
	 * @See         :  테스트에 사용하는 URL - localhost:8081/sample/check.json?height=140&weight=60   (502 error)
	 * @                                -  localhost:8081/sample/check.json?height=180&weight=60   (200 정상)
	 * */
	@GetMapping(value = "/check" ,params = {"height","weight"})
	public ResponseEntity<BoardVO> check(Double height, Double weight){
		BoardVO vo = new BoardVO();
		vo.setBno(15L);
		vo.setContent(String.valueOf(height));
		vo.setWriter(String.valueOf(weight));
		
		ResponseEntity<BoardVO> result = null;
		
		if(height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}
	
	/**
	 * @description : 1) REST 방식에서는  URL 내에 최대한 많은 정보를 담으려고 노력하는데 
	 *  		      예전에는 ?(parameter형식) 뒤에 추가되는 쿼리 스트링이라는 형태로 파라미터를 이용하여 전달되는 데이터들이
	 *   			  REST방식에서는 경로의 일부로 차용되는 경우가 있는데 그럴경우에 @PathVariable 을 사용함!
	 *   
	 *                2) @PathVariable 을 적용하고 싶은 경우 {}를 이용하요 변수명을 지정하고 지정된 이름의 변수값을 얻을 수있다
	 *                여기서 중요한건 기본형 자료형은 사용 불가능!! int (X) --> Integer (O)    
	 * */
	@GetMapping(value="/product/{cat}/{pid}")
	public String[] getPath(
				@PathVariable("cat") String cat, @PathVariable("pid") Integer pid
			) {
		return new String[] {"category : " + cat , "product : " + pid};
	}
	
	/**
	 * @description : 아래에서 사용된 @RequestBody 는 전달(request)된 요청(body)의 내용을 이용해서
	 * 		 		  해당 파라미터의 타입으로 변환을 요구한다.
	 *  
	 *                내부적으로 HttpMessageConverter 타입의 객체들을 이용해서 다양한 포맷의 입력 데이터를 변환할 수 있다.
	 *                대부분의 경우에는 JSON데이터를 서버에 보내서 원하는 타입의 객체로 변환하는 용도로 사용되지만 경우에 따라서는 원하는 포맷의 데이터를 보내고
	 *                이를 해석해서ㅏ 원하는 타입으로 사용하기도 한다.
	 *                
	 * @See         : 여기서 중요한건 아래의 매서드는 다른 메서드와 달리 요청을 받는 타입으 GET이 아닌 POST이다
	 *                이것은 @ReqeustBody가 말그대로 요청한 내용을 처리하기 때문에 일반적인 파라미터 전달 방식을 사용할수 없기 떄문이다.                
	 * */
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("convert .... ticket :: " + ticket);
		return ticket;
	}
	
}
