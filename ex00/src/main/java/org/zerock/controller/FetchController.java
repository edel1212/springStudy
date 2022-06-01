package org.zerock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 *           
	 *           *produces에서는 반환할 데이터형식을 정의해주는것이다!
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
	
	
}
