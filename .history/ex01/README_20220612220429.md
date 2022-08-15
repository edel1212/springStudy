# spring_ex01

1) Controller
  -  method= {RequestMethod.GET , RequestMethod.??? , RequestMethod.???} 등을 사용해
     한가지 URL로 받는 여러가지를 받을 수있게 끔 가능
     
     
  - @GetMapping, @PostMapping .... 등 어노테이션으로 구분도 가능
  
  
  - 받는 파라미터를 객체 형식(VO) , @RequestParam("name") String 	name,@RequestParam	("age")int age  등으로 가능
  
  
  - (@RequestParam("name") List<String> nameArr) 배열로도 가능하다
  
  
  - (SampleDTOList list) List로도 가능 SampleDTOList Class를 보면 생성자를 통해 Clsss 변수를
    초기화 해줌
    
    ex) private List<SampleDTO> list;	
		public SampleDTOList() {
			this.list = new ArrayList<SampleDTO>();
		}
  
  
  - response 시 데이터를 넘겨줄때 request 시 VO객체를 만들어서 데이터를 받을때 따로 
   ( Model model )을 사용하지 않아도 바로 view단에서 사용이 가능하고 만약 int 혹은 String의 경우
   @ModelAttribute 을 사용하면 된다
   
   ex) Controller 
	   public String ex04(SampleDTO dto , @ModelAttribute("page") int page) {
			log.info("dto : " + dto);
			log.info("page : " + page);
			return "/sample/ex04";
		}
	  view 	
	  ${sampleDTO} , ${page} 로 가능
	
	  
  - 페이지 이동 시 굳이 반환 type이 String이 아니어도 된다 void일경우 ULR명을 따름!
     *servlet-conext 의 설정 획인 및 해당 Controller의 RequestMapper을 확인하자  	  
     
    
   - URL을 받고 화면 이동 및 데이터를 전달할때는 Model을 사용하지만 정말 데이터만 전달하고 싶을 경우에는
     @ResponseBody 혹은 ResponseEntity Type를 사욯하면 된다
   	 
   	 
   	 ex) ResponseBody 사용 시 주의 할점은 
   	     1. @ResponseBody 를 붙여줘야하지만 Controller Class에서 @RestController 를 사용할 경우 해당 Class의
   	        모든 Method는 @ResponseBody가 필요 없다
   	     2. 파라미터를 받을 경우 @RequestBody를 꼭 사용해 줘야하고
   	        view 단에서는 script에서 아래 설정을 확인하고 해주자
   	         {
              method: "POST",
              headers: {
                Accept: "application/json",  		// 받는 타입
                "Content-Type": "application/json", //넘기는 데이트 
              },
              body: JSON.stringify(modiObj),  //JSON.stringify 문자로 넘겨야함
   	                                
   		 @PostMapping("/registerReply")
		 public int registerReply(@RequestBody ReplyVO vo){
			log.info("registerReply...");
			return replyService.registerReply(vo);
		 }
   
   	 ex) ResponseEntity 사용 시 아래와 같이 사용 차이점은 ResponseEntity는 좀더 세심하게 컨트롤이 가능함!
		@GetMapping("/ex07")
		public ResponseEntity<String> ex07(){
			log.info("/ex07...");
			//{"name" : "yoo"};
			String msg = "{\"name : \" : \"yoo\"}";
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", "applcation/json;charset=UTF-8");
			return new ResponseEntity<>(msg, header, HttpStatus.OK);
		}
   
   - 파일을 여러개 받을 경우 
   
   	 ex) @PostMapping("/exUploadPost")
		public void exUploadPost(ArrayList<MultipartFile> files) {
			files.forEach(file->{
				log.info("------------");
				log.info("name : " + file.getOriginalFilename());
				log.info("size : " + file.getSize());
			});
		}
		
		
2) Controller 예외 500 or 404 처리		
  - CommonExceptionAdvice.java파일을 확인 하되
  
    기억할 포인트는 
    1. 500에러 처리는 servlet-context에서 설정
	<context:component-scan base-package="org.zerock.exception" />
	2.404는  web.xml에서 설정한다는 것이다		
	<init-param>
        <param-name>throwExceptionIfNoHandlerFound</param-name>
        <param-value>true</param-value>
    </init-param>
		