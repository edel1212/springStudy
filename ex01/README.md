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
> 2 . parameter 처리 \_\_ @See : [SampleController.java]("https://github.com/edel1212/springStudy/blob/main/ex01/src/main/java/org/zerock/controller/SampleController.java")
>
> **ResponseBody일 경우는 @RequestBody로 받아 줘야함!**
>
> > ```java
> > /**  java*/
> > public ResponseEntity<BoardVO> getBoardEntityVer(@RequestBody BoardVO vo) {
> > 	//Code...
> > }
> >
> > ```
>
> **일반 Contorller 경우**
>
> - 객체 형식(**VO**)일경우 그냥 사용 가능!
> - **@RequestParam("???") Type valueName** 으로 사용가능!
> - (@RequestParam("name") **List<String>** nameArr) 배열형식도 가능하다.
>
> - 생성자를 통한 객체형식 배열도 가능함.
>
> ```java
> /** java */
>
> //---- controller ---
> /**
> * @description : SampleDTOList<SmapleDTO> list 형식으로도
>                  받아 올 수 있다 SampleDTO의 형식을 배열로 !!
> * */
> 	@GetMapping("/setUseVOListParamGet")
> 	public String setUseVOListParamGet(SampleDTOList list) {
> 		log.info(list.toString());
> 		return null;
> 	}
>
>
> //---- SampleDTOList ---
>
> @Data
> public class SampleDTOList {
> 	private List<SampleDTO> list;
>
> 	public SampleDTOList() {
> 		this.list = new ArrayList<SampleDTO>();
> 	}
>
> 	@Override
> 	public String toString() {
> 		return "SampleDTOList [list=" + list + "]";
> 	}
>
>
> }
> ```
>
> ---

```java
    ex)
	 private List<SampleDTO> list;
		public SampleDTOList() {
			this.list = new ArrayList<SampleDTO>();
		}
```

> - @ModelAttribute("??") Type valueName 을 사용하면 Model을 사용하지 않아도 화면단 사용가능.
>
> - VO 객체를 만들어 사용하면 Model을 사용하지 않고 바로 화면단 사용가능!

```java
    /** ex) Controller **/
	   public String ex04(SampleDTO dto , @ModelAttribute("page") int page) {
			log.info("dto : " + dto);
			log.info("page : " + page);
			return "/sample/ex04";
		}
		/*****************************************************/

    /*** ex) 화면단 Jsp  ::: "/sample/ex04" ***/
	// Model로 넘기지 않았지만 바로 사용 가능
	  <h1>${sampleDTO}</h1>
	  <h2>${page} </h2>
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

> @See : [CommonExceptionAdvice.java]("https://github.com/edel1212/springStudy/blob/main/ex01/src/main/java/org/zerock/exception/CommonExceptionAdvice.java")
>
> 👿 주의 할점은 해당 CommonExceptionAdvice.java는 Controller이기 때문에
>
> servlet-context.xml에 component-scan이 필요함!
>
> ---

```xml
<!-- xml-->
ex) <!-- servlet-context.xml -->
<context:component-scan base-package="org.zerock.exception" />
```

> 404는 web.xml에서 설정
>
> ---

```xml
<!-- xml -->

<!-- 상단  xsi:schemaLocation 부분 에러로 인해 수정 필요 -->
<!--   javaee;  __ 구분자 필요
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee; https://java.sun.com/xml/ns/javaee/
-->

ex) <!-- web.xml -->
<init-param>
<param-name>throwExceptionIfNoHandlerFound</param-name>
<param-value>true</param-value>
</init-param>
```
