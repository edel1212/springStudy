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

<h3>2) 기본적인 Controller 사용 </h3>

@See : [BoardController.java](https://github.com/edel1212/springStudy/blob/main/ex00/src/main/java/org/zerock/controller/BoardController.java)

<hr style="margin:25px 0 25px 0"/>

<h3>3) RESTController 사용 </h3>

@See : [FetchController.java](https://github.com/edel1212/springStudy/blob/main/ex00/src/main/java/org/zerock/controller/FetchController.java)

<hr style="margin:25px 0 25px 0"/>

<h3>4) 기본적 흐름 </h3>
 <p>✔ Controller -> Service -> ServiceImpl -> Mapper -> Mapper.xml </p>

<hr style="margin:25px 0 25px 0"/>

<h3>5) root-context.xml ❔ </h3>

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
<h3>6) Mapper.xml을 만들지 않고도 쿼리 사용이 가능함.</h3>
<p>✔ Mapeer를 사용할 interface의 메서드에 어노테이션 사용.</p>
  ex)

> - @Select("SELECT \* FROM tbl_board") 어노테이션 활용
>   > @See : [BoardMapper.java](https://github.com/edel1212/springStudy/blob/main/ex00/src/main/java/org/zerock/mapper/BoardMapper.java)
>   >
>   > ***

<hr style="margin:25px 0 25px 0"/>

<h3>7) Mapper_Interface => Mapper.xml 읽는 연결 방법 및 주의사항</h3>

> - xml의 상단 xml이 myBatis를 사용할것이라는 선언을 해줘야함

```xml
ex)
 <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
```

> - mapper namespace="org.zerock.mapper.BoardMapper" 와 같이 네임스페이스와 SqlId를 맞춰줘야한다.
> - ..../main//java/mapper/interface파일 경로와 src/main/resources/....xml
>   > ✅ **파일의 경로와 파일명은 같게** 끔 해주자! **_ ✔ 또한 namespace도 꼭 확인해주자!!✔_**
>   >
>   > 해당 설정을 바꾸고 싶다면 MyBatis Mapper 파일 설정을 변경해주면 된다 [링크]("https://bigfat.tistory.com/98")
>
> ---

<hr style="margin:25px 0 25px 0"/>

<h3>8) Junit Test 방법</h3>

> - **✔ 중요 :** JUnit이 없을 경우 => 프로젝트 우 클릭 => properties => Java Build Path
>   => Libaries => add Libary... => JUnit4 추가 해주자
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

<hr style="margin:25px 0 25px 0"/>

<h3>9) JDBC 사용 시 메이븐 설정</h3>

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
