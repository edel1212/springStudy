package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller //Spring Bean에서 인식 가능하게 끔 처리
@Log4j
@RequestMapping("/board/*") // URL 설정
/**
 * @description : ✔controller는 service에 의존적이기에
 * 				  @Autowired 를 service에 사용하거나
 * 			      아래와 같이 @AllArgsConstructor를 사용하여
 * 				  생성자에 주입해줘야한다!!
 * */
@AllArgsConstructor 
public class BoardController {
	//@Autowired  //@AllArgsConstructor를 사용하였음
	private BoardService boardService;
	
	/**
	 * @description : ✔여기서 각각 메서드에서 parameter로 전달하는 Model은
	 *  			  서버에서 jsp[화면]으로 데이터를 던질때 jsp가 읽을 수 있도록
	 *   			  데이터를 넣어서 넘기는 개체이다!
	 *   
	 * @See 		: 최근에는 잘 jsp를 잘 사용하지 않아서 별로 사용하지 않지만
	 * 				  기억은 해두자!
	 * */
	
	@GetMapping("/list")
	public void  list(Model model) {
		log.info("list");
		model.addAttribute("list", boardService.getList());
	}
	
	/**
	 * @description : 해당 메서드는 위와 다르게 String을 Return 타입으로 정한 후
	 * 				  RedirectAttributes를 파마리터로 지정한다
	 *   			  이유) 등록작업이 끝난 후 다시 목록 화면전환으로 이동하기 위함이다
	 *                     추가적으로 새롭게 등록된 게시물의 번호를 같이 전달하기 위해서
	 *                     RedirectAttributes를 사용한다. 리턴시에는 "redirect:" 접두어를
	 *                     사용하여 이를 이용하면 MVC 내부적으로 response.sendRedirect()를 처리해준다!
	 *   			  
	 * */
	@PostMapping("/register")
	public String resister(BoardVO vo , RedirectAttributes rttr) {
		log.info("register" + vo);
		boardService.register(vo);
		rttr.addFlashAttribute("result", vo.getBno());
		return "redirect:/board/list";
	}
	
	/**
	 * @description : 해당 메서드에서는 bno를 넘길떄 @RequestPram()을 사용하였는데
	 *                그 이유는 좀더 명시적으로 처리하기 위함이다
	 *                 
	 * @See         : 파라미터 이름과 변수 이름을 기준으로 동작하기 떄문에 생략해도 무방하긴 함!     
	 * */
	@GetMapping("/get")
	public void getData(@RequestParam("bno") Long bno ,Model model) {
		log.info("getData");
		model.addAttribute("board", boardService.getBoard(bno));
	}
	
	@PostMapping("modify")
	public String modify(BoardVO vo , RedirectAttributes rttr) {
		log.info("modify" + vo);
		if(boardService.update(vo) == 1) {
			rttr.addFlashAttribute("result", "succes");
		}
		return "rediect:/board/list";
	}
	
	@PostMapping("remove")
	public String remvoe (@RequestParam("bno") Long bno , RedirectAttributes rttr) {
		log.info("remove Controller");
		if(boardService.delete(bno) == 1) {
			rttr.addFlashAttribute("result","seucces");
		}
		return "rediect:/board/list";
	}
	
}
