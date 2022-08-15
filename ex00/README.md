<h1> 정리 내용 </h1>

- Bean 추가 및 사용방법
- Controller, RESTController 차이
- MVC 흐름
- JUnit 테스트
- Mybatis 사용법

<hr/>

<h3>1) 의존성 주입</h3>
<p><b>✔Controller, Service를 사용할 경우</b></p>
<p> 기본 전제 조건 : Bean에 주입되어 있어야한다 <b>root-context.xml</b> </p>
<strong> ex) </strong> 

>* 방법1. @AllArgsConstructor 를 사용하면 @Autowired, @Setter,@Resource를 사용하지 않아도 된다.
>* 방법2. @Autowired를 사용한다 자동으로 주입된다 [사용할 경우 반드시 기본 생성자가 정의되어 있어야 한다.]
>* 방법3. @Setter(onMethod = @Autowired) [사용할 경우 반드시 기본 생성자가 정의되어 있어야 한다.]
>* 방법4. @Resource는 주입하려고 하는 객체의 이름(id)이 일치하는 객체를 자동으로 주입한다. 
>            [메이븐 주입필요 javax.annotation-api] @Resource(name ="") 이런 식으로 사용도 가능


2) 기본적인 Controller 사용 
  * @See BoardController.java
  
  
3) RESTController 사용
  * @See : FetchController 확인
  

4) 기본적 흐름
  * Controller -> Service -> ServiceImpl -> Mapper -> MapperXml
  

5) root-context.xml 에서는 ?
  - jdbc 설정 및 bean 설정 , aop 및 tx , context 설정이 가능하다
  - 기억해 둘것 context:component-scan은 Namespaces에서 context를 체크해주자
  - mybatis-spring:scan 은 Namespaces에서 mybatis를 체크해주자!! 그리고 스캔법이 위랑 다르니 헷갈리지 말자!
  - jdbc 및 conectionPool , log4j 설정은 @See : root-context.xml, log4j.xml,  log4jdbc.log4j2.properties 확인하자
  

6) MapperXml을 만들지 않고도 쿼리 테스트가 가능하다  
   - ex) @Select("SELECT * FROM tbl_board") 어노테이션 활용 
   @See : BoardMapper.java
   

7) Mapper.java 파일에서 MapperXml을 읽을 수 있는 이유
   - mapper namespace="org.zerock.mapper.BoardMapper"로 경로를 잡아주기 때문임!
   - ..../main/java/...interface 파일경로와  src/main/resources/....xml 파일의 경로와 파일명은 같게 끔 해주자!   
   - xml의 상단 선은 중요함! 
  	 <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">        
	  

8) Test 방법! 
  -	src/test/java 하위 파일 확인하지
  - 중요한건 JUnit test 시 프로젝트 우클릭 -> properties -> Java Build Path 
  	-> Libaries -> add Libary... -> JUnit4 추가 해주자 
  	

9) JDBC POM
  - 해당DB jdbc , connectionPool[히카리 사용],  mybatis, mybatis-spring  	,spring-jdbc , org.bgee.log4jdbc-log4j2
  - 또 하나 중요한건 pom 하단 maven-compiler-plugin 버전을 내가 상단 사용하는 java 버전와 맞춰주자 이유는 @See : pom.xml 최하단 주석 확인
