<h1> 정리 내용 </h1>

- Bean 추가 및 사용방법
- Controller, RESTController 차이
- MVC 흐름
- JUnit 테스트
- Mybatis 사용법

<hr style="margin:25px 0 25px 0"/>

<h3>1) 의존성 주입</h3>
<p><b>✔ Controller, Service를 사용할 경우</b></p>
<p> 기본 전제 조건 : Bean에 주입되어 있어야한다 <b>root-context.xml</b> </p>
<strong> ex) </strong>

> - 1 ) @AllArgsConstructor 를 사용하면 @Autowired,@Resource를 사용하지 않아도 된다.
> - 2 ) @Autowired를 사용 자동으로 주입된다
> - 3 ) @Setter(onMethod = @Autowired) [사용할 경우 반드시 기본 생성자가 정의되어 있어야 한다.]
> - 4 ) @Resource(name="??")는 객체의 이름(id)이 일치하는 ServiceImpl을 자동으로 주입한다.
>   사용시 : [메이븐 javax.annotation-api 추가 필요]
>
> ---

<hr style="margin:25px 0 25px 0"/>

<h3>2) URL 매핑 방법</h3>

> ✅ URL별 화면 이동법
>
> > 기본적으로 **servlet-context.xml**의 directory경로 설정을 따른다.
> >
> > 👿 주의해야할 점은 class의 @RequestMapping("URL")가 void의 경우 경로에 포함 되고 String일경우는
> > 따로 작성해 줘야한다는 것이다!
> >
> > ---
>
> - void Type : 요청받은 GET-URL의 이름을 따서 jsp를 반환
> - String Type : return 하는 문자열의 이름을 따서 jsp를 반환
>
> ---

<hr style="margin:25px 0 25px 0"/>

<h3>3) 기본적인 Controller 사용 </h3>

@See : [BoardController.java](https://github.com/edel1212/springStudy/blob/main/ex00/src/main/java/org/zerock/controller/BoardController.java)

<hr style="margin:25px 0 25px 0"/>

<h3>4) RESTController 사용 </h3>

> 일반 Controller 와 다른점은 @RestController가 사용된다는 점이다
>
> ✅ 화면은 반환하는 controller가 아니라 데이터를 반환 목적으로 쓰는 Controller
>
> Method마다 @ResponseBody가 필요없음!
>
> 😡 주의사항 controller 단 parameter 받을 경우 @ReuqestBody가 필요함!
>
> ```java
> // Java
> public ResponseEntity<BoardVO>  getBoardEntityVer(@RequestBody BoardVO vo) {
>    //Code..
> }
>
> ```
>
> ---

@See : [FetchController.java](https://github.com/edel1212/springStudy/blob/main/ex00/src/main/java/org/zerock/controller/FetchController.java)

> fetch 사용법
>
> 👿주의 사항
>
> - parameter는 fetch의 body에 넣어줘야함 :: headers{ "Content-Type" : 맞춰줘야함! }
> - 데이터를 response(반환) 시킬때 json 형태라면
>   > pom.xml : jackson-databind, jackson-dataformat-xml, gson 추가가 필요하다!
>   >
>   > ***
>
> ---

```javascript
/**
 * javascript
 * **/

fetch("URL", {
  method: "POST", // *GET, POST, PUT, DELETE 등
  mode: "cors", // no-cors, *cors, same-origin
  cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
  credentials: "same-origin", // include, *same-origin, omit
  headers: {
    Accept: "application/json", // 요청받은 데이터 타입
    "Content-Type": "application/json", //내가 보내는 파라미터 형식
    // 'Content-Type': 'application/x-www-form-urlencoded',
  },
  //redirect: 'follow', // manual, *follow, error
  referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
  body: JSON.stringify(data), // body의 데이터 유형은 반드시 "Content-Type" 헤더와 일치해야 함
})
  .then((status) => {
    /**
     * @Description : fetch의 단점은 catch 분에서 error를 명확하게
     * 				 표현을 못해준다는 점이다 따라서 status를 사용해서 에러 코드를 확인하는 방법으로 처리
     */
    console.log(status);
    return status;
  })
  .then((response) => response.json())
  .then((data) => {
    console.log(data);
  })
  .catch((err) => console.log(err));
```

> ResponseEntity 사용법
>
> 👿주의 사항 : URL 경로를 조심하자!
>
> ---

```java
  /**
    Java
    - 아래는 예시 원하는 대로 커스텀해서 상태 및 header를 컨트롤해주자!
  */

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
```

<hr style="margin:25px 0 25px 0"/>

<h3>5) 기본적 흐름 </h3>
 <p>✔ Controller -> Service -> ServiceImpl -> Mapper -> Mapper.xml </p>

<hr style="margin:25px 0 25px 0"/>

<h3>6) root-context.xml ❔ </h3>

> - Bean 주입 Class 경로 설정 ,Aop ,Tx, jdbc 설정이 가능하다
> - 기억해 둘것 context:component-scan은 Namespaces에서 context를 체크해 줘야한다
> - mybatis-spring:scan 은 Namespaces에서 mybatis를 체크해줘야한다 **componet와 헷갈리지 말자**
> - jdbc 및 conectionPool , log4j 설정은 **_하위 링크를 확인_**
>   > @See : [root-context.xml](https://github.com/edel1212/springStudy/blob/main/ex00/src/main/webapp/WEB-INF/spring/root-context.xml)
>   > , [log4j.xml](https://github.com/edel1212/springStudy/blob/main/ex00/src/main/resources/log4j.xml)
>   > , [log4jdbc.log4j2.properties](https://github.com/edel1212/springStudy/blob/main/ex00/src/main/resources/log4jdbc.log4j2.properties)
>
> ---

<hr style="margin:25px 0 25px 0"/>
<h3>7) Mapper.xml을 만들지 않고도 쿼리 사용이 가능함.</h3>
<p>✔ Mapeer를 사용할 interface의 메서드에 어노테이션 사용.</p>
  ex)

> - @Select("SELECT \* FROM tbl_board") 어노테이션 활용
>   > @See : [BoardMapper.java](https://github.com/edel1212/springStudy/blob/main/ex00/src/main/java/org/zerock/mapper/BoardMapper.java)
>   >
>   > ***

<hr style="margin:25px 0 25px 0"/>

<h3>8) Mapper_Interface => Mapper.xml 읽는 연결 방법 및 주의사항</h3>

> - xml의 상단 xml이 myBatis를 사용할것이라는 선언을 해줘야함

```xml
ex)
 <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
```

> - mapper namespace="org.zerock.mapper.BoardMapper" 와 같이 네임스페이스와 SqlId를 맞춰줘야한다.
> - ..../main//java/mapper/interface파일 경로와 src/main/resources/....xml
>   > ✅ **파일의 경로와 파일명은 같게** 끔 해주자! **_ ✔ 또한 namespace도 꼭 확인해주자!!✔_**
>   >
>   > 해당 설정을 바꾸고 싶다면 MyBatis Mapper 파일 설정을 변경해주면 된다 >> [링크]("https://bigfat.tistory.com/98")
>
> ---

<hr style="margin:25px 0 25px 0"/>

<h3>9) Junit Test 방법</h3>

> 1 . JDBC 및 service , Mapper 테스트
>
> **✅ 중요 :** JUnit이 없을 경우 => 프로젝트 우 클릭 => properties => Java Build Path
> => Libaries => add Libary... => JUnit4 추가 해주자
>
> - JDBC, Mybatis를 테스트 할 경우 해당 설정 확인!
> - log4j.xml 파일은 **/src/main/resource에만 있으면 된다!** ❌/src/test/resource 경로에는 없어도 된다!
>
> ---

테스트 예제

> - [JDBCTests.java]("https://github.com/edel1212/springStudy/blob/main/ex00/src/test/java/org/zerock/persistence/JDBCTests.java") - JDBC 연결 테스트
> - [DataSourceTests.java]("https://github.com/edel1212/springStudy/blob/main/ex00/src/test/java/org/zerock/persistence/DataSourceTests.java") - Root-context에서 설정한 DataSource 테스트

필요 메이븐 목록

> - spring-jdbc
> - 사용 DB-jdbc
> - 커넥션풀을 사용할경우 추가
> - log4jdbc-log4j2-jdbc4
> - mybatis
> - mybatis-spring
> - spring-test [이게 없으면 springRun class 가 안잡힘]
>
> ---

> 2 . RestController 테스트
>
> > ✅ 중요 :: pom의 servlet 버전을 올려줘야한다 3.1 이하버전은 SessionCookieConfig를 못찾는 에러가 있음
> >
> > ✅ @WebAppConfigurationf를 꼭 추가해주자!
> >
> > @참고 파일 : [RestControllerTests.java]("https://github.com/edel1212/springStudy/blob/main/ex00/src/test/java/org/zerock/controller/RestControllerTests.java")
> >
> > ```xml
> >   <!-- pom.xml -->
> >
> >   <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
> > 		<dependency>
> > 		    <groupId>javax.servlet</groupId>
> > 		    <artifactId>javax.servlet-api</artifactId>
> > 		    <version>3.1.0</version>
> > 		    <scope>provided</scope>
> > 		</dependency>
> > ...
> > ```
> >
> > ---

<hr style="margin:25px 0 25px 0"/>

<h3>10) JDBC 사용 시 메이븐 설정</h3>

> - 해당DB jdbc , connectionPool[히카리 사용], mybatis, mybatis-spring ,spring-jdbc , org.bgee.log4jdbc-log4j2
> - 또 하나 중요한건 pom 하단 maven-compiler-plugin 버전을 내가 상단 사용하는 java 버전와 맞춰야함!!!
> - 이유는 링크 확인 @See : [pom.xml](https://github.com/edel1212/springStudy/blob/main/ex00/pom.xml)
>
> - =============================================================================
>
>   **Query를 사용할 경우 쿼리와 결과값을 보기위한 설정**
>
> - 1 . 메이븐에 log4jdbc-log4j2-jdbc4 설정
> - 2 . root-context.xml에 JDBC 설정 변경
>   > @See : [root-context.xml](https://github.com/edel1212/springStudy/blob/main/ex00/src/main/webapp/WEB-INF/spring/root-context.xml)
>
> ---

```

```
