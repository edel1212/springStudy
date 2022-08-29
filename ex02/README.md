<h1>AOP 설정 및 Transaction 설정</h1>

<h3>1 . AOP 설정 요약</h3>

> - 1 ) aspectJ 와 aspectJ weaver를 pom에 추가해준다.
>
> ```xml
> <!-- pom.xml -->
>
> 		<!--**************************주의***************************  -->
> 		<!--✅pom 상단 properties버전을 올려주자 -->
> 		<org.aspectj-version>1.9.0</org.aspectj-version>
> 		<org.slf4j-version>1.7.25</org.slf4j-version>
> 		<!-- ******************************************************* -->
>
> 		<!-- AspectJ -->
> 		<dependency>
> 			<groupId>org.aspectj</groupId>
> 			<artifactId>aspectjrt</artifactId>
> 			<version>${org.aspectj-version}</version>
> 		</dependency>
>
> 		<!-- AspectJ weaver 스프링 AOP 처리가 된 객체를 생성할때 사용할 라이브러리 -->
> 		<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
> 		<dependency>
> 		    <groupId>org.aspectj</groupId>
> 		    <artifactId>aspectjweaver</artifactId>
> 		    <version>1.9.9.1</version>
> 		    <scope>runtime</scope>
> 		</dependency>
>
>
> ```
>
> - 2 ) root-context에서 namesapces에서 AOP를 추가해준다
>
> - 3 ) root-context에 aop:aspectj-autoproxy도 추가해준다
>
> ```xml
> <!-- root-context.xml -->
>
>   	<!-- aspectj add -->
> 	<aop:aspectj-autoproxy/>
>
> ```
>
> - 4 ) root-context에 component:scan을 사용해서 aop 적용할 패키지명을 bean에 주입해주자.
>
> ```xml
> <!--root-context.xml -->
>
> 	<!-- Aop Scan -->
> 	<context:component-scan base-package="targetPackage"/>
>
> ```
>
> - AOP handler 구현 내용 \_\_\_ @See : [LogAdvice.java](https://github.com/edel1212/springStudy/blob/main/ex02/src/main/java/org/zerock/aop/LogAdvice.java)
>
> ---

<hr style="margin:25px 0 25px 0"/>

<h3>2 . Transaction 설정 요약 </h3>

> 🎈 참고 \_\_\_ @See : [root-conext.xml](https://github.com/edel1212/springStudy/blob/main/ex02/src/main/webapp/WEB-INF/spring/root-context.xml)
>
> - 1 ) 메이븐 및 root-context에 JDBC 설정을 해준다
> - 2 ) 메이븐에 spring-tx를 추가해준다.
>
> ```xml
> <!-- pom.xml -->
>
> 	<!-- https://mvnrepository.com/artifact/org.springframework/spring-tx -->
> 		<dependency>
> 			<groupId>org.springframework</groupId>
> 			<artifactId>spring-tx</artifactId>
> 			<version>${org.springframework-version}</version>
> 		</dependency>
> ```
>
> - 3 ) root-context의 namespaces에 tx를 추가 해준다.
> - 4 ) root-context에 tx관련 설정을 추가 해주자
>
> ```xml
> <!-- root-context.xml -->
>
> 	<!--
> 		 @Description : 1) 트랜잭션을 관리하는 빈을 등록하고 어노테이션 기반으로 트랜잭션을
> 					       설정 할수 있도록 아래 태그를 작성한다.
>
> 					    2) bean에 등록된 transactionManager와 tx:annotation-driven
> 					       설정이 추가된 후에는 트랜잭션이 필요한 상황을 만들어서 어노테이션을 추가하는
> 					       방식으로 설정하게 됨.
> 	 -->
>
>    <tx:annotation-driven/>
>
>     <bean id="transactionManager"  class="org.springframework.jdbc.datasource.DataSourceTransactionManager" >
>     	<!-- ref="dataSource"는 내가 상단에 설정한 JDBC Bean Id 값임! -->
>     	<property name="dataSource" ref="dataSource"/>
>     </bean>
> ```
>
> - 5 ) 이 후 적용할 Service의 메서드에 @Transactional 어노테이션을 추가 해주면 해당 메서드는 Transaction의 관리 대상이된다.
>
> 🎈 참고 \_\_\_ @See : [SampleTxServiceImpl.java](https://github.com/edel1212/springStudy/blob/main/ex02/src/main/java/org/zerock/service/SampleTxServiceImpl.java)
>
> >
>
> ---
>
> ```
>
> ```
