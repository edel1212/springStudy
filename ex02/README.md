<h1>AOP 설정 및 Transaction 설정</h1>

<h3>1 . AOP 설정 요약</h3>

> - 1 ) aspectJ 와 aspectJ weaver를 pom에 추가해준다.
>
> ---

```xml
<!-- AspectJ -->
		<dependency>
			<groupId>org.aspectj</groupId>
			<artifactId>aspectjrt</artifactId>
			<version>${org.aspectj-version}</version>
		</dependency>

		<!-- AspectJ weaver 스프링 AOP 처리가 된 객체를 생성할때 사용할 라이브러리 -->
		<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
		<dependency>
		    <groupId>org.aspectj</groupId>
		    <artifactId>aspectjweaver</artifactId>
		    <version>1.9.9.1</version>
		    <scope>runtime</scope>
		</dependency>

  <!--**************************주의***************************  -->
    <org.aspectj-version>1.9.0</org.aspectj-version>
    <org.slf4j-version>1.7.25</org.slf4j-version>
    ✔✔✔버전을 꼭 확인을 하자✔✔✔
    <!-- ******************************************************* -->

```

> - 2 ) root-context에서 namesapces에서 AOP를 추가해준다
> - 3 ) root-context에 aop:aspectj-autoproxy도 추가해준다
>   > @See : [root-conext.xml](https://github.com/edel1212/springStudy/blob/main/ex02/src/main/webapp/WEB-INF/spring/root-context.xml)
> - 4 ) component:scan을 사용해서 aop 적용할 패키지명을 bean에 주입해주자.
> - 5 ) 다음 적용한 package에 java파일을만들어 사용하자 @See:
>   > @See : [LogAdvice.java](https://github.com/edel1212/springStudy/blob/main/ex02/src/main/java/org/zerock/aop/LogAdvice.java)
>
> ---

<hr style="margin:25px 0 25px 0"/>

<h3>2 . Transaction 설정 요약</h3>

> - 1 ) pom 과 root-context에 jdbc 설정을 해주자
> - 2 ) root-context의 namespaces에 tx를 추가해주자
> - 3 ) bean에 tx관련 설정을 주입해주자
>   > @See : [root-conext.xml](https://github.com/edel1212/springStudy/blob/main/ex02/src/main/webapp/WEB-INF/spring/root-context.xml)
> - 4 ) 이 후 적용할 메서드에 @Transactional를 추가해주자
>   > @See : [SampleTxServiceImpl.java](https://github.com/edel1212/springStudy/blob/main/ex02/src/main/java/org/zerock/service/SampleTxServiceImpl.java)
>   >
>   > > ✅@Transactional을 달아줘서 설정함!✅
>
> ---
