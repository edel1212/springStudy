<h1> 정리 내용 </h1>

- Controler URL 매핑
- Contorller Parameter 받기
- RESTController
- ResponseEntity Type Contoller
- 500, 404 에러 페이지 처리

<hr style="margin:25px 0 25px 0"/>
<h3>1) Controller</h3>

> 1 . URL 처리
>
> - method= {RequestMethod.GET , RequestMethod.??? , RequestMethod.??}
>   처럼 배열을 사용해 한가지 URL로 여러 요청 Method를 처리 가능
> - @GetMapping("??"), @PostMapping("??") 처럼 어노테이션으로 구분 요청 메서드 구분 가능
>
> - Produces, Consumes를 사용하여 데이터 제한이 가능하다.
>   > - @Consumes : 수신 하고자하는 데이터 포맷을 정의한다. 🎈**요청 데이터 타입 제한하기 (consumes)**
>   >
>   > 👿 주의사항! @GetMapping(value = "/URL" , <<== 와 같이 value ="URL "해줘야함!
>   >
>   > ```java
>   > /**Java */
>   >
>   > //Controller
>   > /**
>   > * 이 핸들러에서는 body에 담긴 데이터의 타입이
>   > * APPLIACTION_JSON_UTF8일 경우의 요청만을 처리한다는 의미입니다.
>   > * 따라서 요청시 헤더에 꼭 application/json 이 존재해야합니다.
>   > */
>   > @GetMapping(value = "/test" , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
>   > @ResponseBody
>   > public String hello(){
>   > return "hello";
>   > }
>   >
>   > //////////////////////////////////////////////////////////////////////////////////
>   >  //Junit Test
>   > @Test
>   > public void test1() throws Exception{
>   > 	log.info(
>   > 		mockMvc.perform(MockMvcRequestBuilders.get("/urlTest/test")
>   > 		//.contentType(MediaType.APPLICATION_FORM_URLENCODED)) 에러 APPLICATION_FORM_URLENCODED❌
>   > 		  .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)) //👍 성공 !
>   > 			);
>   > }
>   >
>   > ```
>   >
>   > - @Produces : 출력하고자 하는 데이터 포맷을 정의한다. 🎈**응답 데이터 제한하기(produces,headers)**
>   >
>   > ```java
>   > /**Java */
>   >
>   > //Controller
>   > @PostMapping( value ="/hello", produces = MediaType.TEXT_PLAIN_VALUE)
>   > @ResponseBody
>   > public String hello(){
>   >
>   > 	return "hello";
>   >
>   > }
>   >
>   > //////////////////////////////////////////////////////////////////////////////////
>   > //Junit Test
>   > @Test
>   > 	public void testHello()throws Exception{
>   > 	    mockMvc.perform(MockMvcRequestBuilders.post("/hello")
>   > 	    		.accept(MediaType.TEXT_PLAIN_VALUE)); // 👍 맞춰주면된다!
>   > }
>   > ```
>   >
>   > ***
>
> ---
>
> 2 . parameter 처리
>
> - 객체 형식(VO)
> - @RequestParam("name") String name,@RequestParam ("age")int age 처럼 받아올 수 도있음
> - (@RequestParam("name") List<String> nameArr) 배열도 가능하다
>
> - (SampleDTOList list) List도 가능 SampleDTOList Class를 보면 생성자를 통해 Clsss 변수를 초기화 해줌.

```java
    ex)
	 private List<SampleDTO> list;
		public SampleDTOList() {
			this.list = new ArrayList<SampleDTO>();
		}
```

> - 페이지로 데이터 전달 시 VO객체를 만들어서 데이터를 받을때
>   ( Model model )을 사용하지 않아도 바로 view단에서 사용이 가능하다
> - @ModelAttribute을 사용하면 객체 VO가 아니여도 받아온 그대로 전달 가능함.

```java
    ex) Controller
	   public String ex04(SampleDTO dto , @ModelAttribute("page") int page) {
			log.info("dto : " + dto);
			log.info("page : " + page);
			return "/sample/ex04";
		}
		/*****************************************************/
	  ex) View 	// "/sample/ex04"
	  ${sampleDTO} , ${page} // Model로 넘기지 않았지만 바로 사용 가능
```

> - Controlle의 메서드 type이 String이 아니어도 void일경우 ULR명을 따라서 찾아감
>   :: **_servlet-conext 의 설정 획인 및 해당 Controller의 RequestMapper을 확인하자_**

```java
    ex) Controller
	  @GetMapping("/urlMove")
	   public vod goFole() {
			log.info("해당 URL에 맞는 파일로 이동함!");
		}
		/*****************************************************/
	  ex) View 	// "/urlMove
	  //urlMove.jsp로 이동!
```

> - 비동기 통신과 같은 데이터만 주고 받을 경우
>   > ResponseEntity 타입의 메서드를 사용하거나 @ResponseBody, @RestController를 사욯하면 된다.

```java
   ex)	//ResponseEntity방식
		/**
		 *  ResponseEntity는 좀더 세심하게 통신 컨트롤이 가능하다!
		 * **/
		@GetMapping("/ex07")
		public ResponseEntity<String> ex07(){
			log.info("/ex07...");
			//{"name" : "yoo"};
			String msg = "{\"name : \" : \"yoo\"}";
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", "applcation/json;charset=UTF-8");
			return new ResponseEntity<>(msg, header, HttpStatus.OK);
		}
```

```java
 ex) //ResponseBody 방식
		/**
		 * 1. 사용하려는 메서드 상단에 @ResponseBody 를 붙여줘야함
		 *    단! 사용하려는 Controller Class에서 전체가 비동기 통신을 하는
		 *        컨트롤러라면 @RestController를 사용하면 메서드마다 붙여줄 필요가 없다
		 *
		 * 2. 주의 해야할점은 파라미터를 받을 경우 앞에 @RequestBody 어노테션을
		 *    붙여줘야 인식이 가능가능!
		 *
		 * 3. javascript에서도 요청 시 파라미터를 string으로 풀어서 전달해 줘야함!
		 *
		*/

		//Java
		@PostMapping("/registerReply")
			public int registerReply(@RequestBody ReplyVO vo){
			log.info("registerReply...");
			return replyService.registerReply(vo);
		}


		//javascript 데이터 전송(fetch 사용)
	    fetch("/RESTAPI/registerReply"{
			method: "POST",
			headers: {
			Accept: "application/json",  		// 받는 타입
			"Content-Type": "application/json", //넘기는 데이트
        }, body: JSON.stringify(modiObj)  //[중요✔] JSON.stringify 문자로 넘겨야함
		}).....
```

> - 여러개의 파일을 받을 경우 받을 경우

```java
	ex)
	@PostMapping("/exUploadPost")
		public void exUploadPost(ArrayList<MultipartFile> files) {
		files.forEach(file->{
		log.info("------------");
		log.info("name : " + file.getOriginalFilename());
		log.info("size : " + file.getSize());
		});
	}
```

<hr style="margin:25px 0 25px 0"/>

<h3>2) 500 or 404 처리</h3>

> - @See : [CommonExceptionAdvice.java]("https://github.com/edel1212/springStudy/blob/main/ex01/src/main/java/org/zerock/exception/CommonExceptionAdvice.java")
>   > 단 CommonExceptionAdvice.java파일을 확인 하되
>   > 기억할 포인트는

- 500에러 처리는 servlet-context에서 설정

```xml
ex) <!-- servlet-context.xml -->
<context:component-scan base-package="org.zerock.exception" />
```

- 404는 web.xml에서 설정

```xml
ex) <!-- web.xml -->
<init-param>
<param-name>throwExceptionIfNoHandlerFound</param-name>
<param-value>true</param-value>
</init-param>
```
