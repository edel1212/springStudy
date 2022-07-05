package org.zerock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/fetch/*")
@Log4j
@AllArgsConstructor
public class FetchController {
	
	/**
	 * @See : Controller와 RestController의 차이점
	 *       ✔이 둘의 가장 큰 차이점은 @RestController에서는 HTTP Response Body가 생성되는 방식이라는 것이다! ✔
	 *      
	 *      1) 기존의 MVC Controller  : view 기술 주로 사용하며 view 화면을 return 해주거나 
	 *                                파라미터에 Model model을 사용하여 화면과 데이터를 같이 넘김 ex) BoardController.java의 list()메서드 확인
	 *      				
	 *      2) RestFull Controller  : Data를 return 하는것이 주된 용도 이며 JSON/XML 형식식의 GTTP 응답을 작성하여 return 한다.
	 *
	 *
	 * @See : ResponseEntity란?
	 *        1) 개발자가 직접 결과 데이터와 Http 상세 코드를 직접 제어할 수 있는 Class로 개발자는
	 *           404나 500 Error 와 같은 Http 상태코드를 전송하고 싶은 데이터와 함께 전송할 수 있기 
	 *           좀 더 세밀한 제어가 필요할 경우에 용한다.
	 *           
	 *        2) ResponseEntity에서 사용하는 consumes와 produces란?
	 *           요청을 받을 시 데이터를 강제함으로써 오류 상황을 줄이 위해 사용한다
	 *           
	 *           *consumes에서는 화면에서 받아오는 데이터형식을 정의 하므로
	 *           화면서에서 요청시에도 헤더에서도 같은 형식으로 보내줘야하고
	 *           ex) 
	 * 		         @GetMapping("/test", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 *				 @ResponseBody
	 *			     public String hello(){
	 *			    	return "hello";
	 *			     }
	 *			     - 위와 같이 작성하면 서버에서 이핸들러에서는 body에 담긴 데이터 타입이 
	 *			       APPLIACTION_JSON_UTF8일 경우의 요청만을 처리한다는 의미입니다.
	 *				   따라서 요청시 헤더에 꼭 application/json 이 존재해야합니다.
	 *			     
	 *           
	 *           *produces에서는 반환할 데이터형식을 정의해주는것이다!
	 *           ex) 
	 *           	@PostMapping( value ="/hello", produces = MediaType.TEXT_PLAIN_VALUE)
	 *				@ResponseBody
	 *				public String hello(){
	 *					return "hello";
	 *			 	}
     *				- 위 와같은 경우 응답 타입을 TEXT_PLAIN_VALUE으로만 하겠다는 의미이다.
     *				  아래 코드로 test해보면 406 Error 가 나는것 을 볼수있다.
	 *                mockMvc.perform(post("/hello")
	 *		    		.accept(MediaType.APPLICATION_JSON_UTF8))
	 *		            .andDo(print())
	 *		            .andExpect(status().isOK());
	 *           
	 *           ✔ 알아둘 사항
	 *           	html form 태그를 사용하여 post 방식으로 요청하거나
	 *				JQuery의 ajax 등의 요청을 할 때 default Content-Type은 "application/json"이 아니라
	 *				"application/x-www-form-urlencoded'이다.✔
	 *           
	 * */
	
	private BoardService boardService;
	
	@PostMapping("/getList")
	public List<BoardVO> test() {
		log.info("fetchTest!!!");
		List<BoardVO> result = boardService.getList();
		return result;
	}
	
	@PostMapping(value="/getListEntityVer"
			, produces= {MediaType.APPLICATION_JSON_UTF8_VALUE,MediaType.TEXT_XML_VALUE})
	public ResponseEntity<List<BoardVO>>  getListEntityVer() {
		log.info("Use ResponseEntity");
		List<BoardVO> result = boardService.getList();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/getBoardEntityVer"
			, produces= {MediaType.APPLICATION_JSON_UTF8_VALUE,MediaType.TEXT_XML_VALUE})
	public ResponseEntity<BoardVO>  getBoardEntityVer(@RequestBody BoardVO vo) {
		log.info("Use ResponseEntity");
		log.info("get Bno !!! :::: " + vo.getBno());
		BoardVO result = boardService.getBoard(vo.getBno());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * ✔✔ 굳이 fetch 에서 데이터를 받아올때 constorller 단에서 VO가 있을필요가 없음 ✔✔
	 * 		public ResponseEntity<BoardVO> getBoardEntityVer(@RequestBody String vo) {
	 * 		요렇게 해도 잘 받아옴!!
	 * 		
	 * 		ex)
	 * 			@PostMapping("/deleteFile")
	 *		@ResponseBody
	 *		public ResponseEntity<String> deleteFile(@RequestBody String req){
	 *			
	 *			//단 문자열이기에 meavn에서 받은 jackson을 사용하여 파싱해서 써야함!
	 *			log.info("req ::: " + req);
	 *		
	 *			return new ResponseEntity<String>(HttpStatus.OK);
	 *		}
	 * 	 * */
	
}
