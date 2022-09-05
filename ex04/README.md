b<h1> Spring Security </h1>

기본적으로 스프링 시큐리티는 인터셉터와 같은 방식 진행된다.

<hr style="margin:25px 0 25px 0"/>
<h3>1 ) pom 설정</h3>

> ✅ 메이븐 추가
>
> 🎈주의 사항 : 4개의 버전이 모두 같아야한다!
>
> ```xml
> <!-- pom.xml -->
> 		<!-- ================================================== -->
> 		<!-- spring security를 위한 maven 주입 4개의 버전이 같아야함!   -->
> 		<!-- security-web,  security-config, 				    -->
> 		<!-- security-core,  security-config,   				-->
> 		<!-- ================================================== -->
> 		<!-- https://mvnrepository.com/artifact/org.springframework.security/spring-security-web -->
> 		<dependency>
> 		    <groupId>org.springframework.security</groupId>
> 		    <artifactId>spring-security-web</artifactId>
> 		    <version>${org.springframework-version}</version>
> 		</dependency>
>
> 		<!-- https://mvnrepository.com/artifact/org.springframework.security/spring-security-config -->
> 		<dependency>
> 		    <groupId>org.springframework.security</groupId>
> 		    <artifactId>spring-security-config</artifactId>
> 		    <version>${org.springframework-version}</version>
> 		</dependency>
>
> 		<!-- https://mvnrepository.com/artifact/org.springframework.security/spring-security-core -->
> 		<dependency>
> 		    <groupId>org.springframework.security</groupId>
> 		    <artifactId>spring-security-core</artifactId>
> 		    <version>${org.springframework-version}</version>
> 		</dependency>
>
> 		<!-- https://mvnrepository.com/artifact/org.springframework.security/spring-security-taglibs -->
> 		<dependency>
> 		    <groupId>org.springframework.security</groupId>
> 		    <artifactId>spring-security-taglibs</artifactId>
> 		    <version>${org.springframework-version}</version>
> 		</dependency>
> ```
>
> ---

<hr style="margin:25px 0 25px 0"/>
<h3>2 ) sercurity-context</h3>

> root-context.xml에서 security설정을 해서 사용해도 되지만
>
> 좀 더 의미에 맞게 개발하기 위해 xml을 나눠서 개발하는 방식을 선호함.
>
> ✅ 연습 방법은 총 4가지를 나눠서 사용함.
>
> 🎈기본 설정
>
> - 1 . pom에 security 메이븐을 추가해준다. (4개)
> - 2 .security의 Handler를 읽게 설정할 xml을 만든다.
> - 3 .해당 만들어 놓은 xml의 namespaces에서 **security** 체크 해준다.
>
> 👿여기서 중요한건 해당 xml의 상단 xsi:schemaLocation=의 security 버전을 지워줘야 에러가 안난다.
>
> ```xml
> <!-- security-conext.xml -->
>
> <beans xsi:schemaLocation="http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security-5.1.xsd "> <!-- 해당 버전부분을 지워줘야함 --> ></beans>
>
> <!-- 수정 후 ▼ -->
>
> <beans xsi:schemaLocation="http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd ">
> </beans>
>
> ```
>
> - 4 . web.xml에서 security-context.xml을 읽을 수 있도록 설정해준다.
>
> 🎈주의 사항 첫번째 : context-param 내부의 param-valu 안에 작성되어야한다.
>
> 🎈주의 사항 두번째 : filter설정 과 그 설정한 filter를 읽을 수 있도록 filter-mapping이 필요하다
>
> ```xml
>   <!-- web.xml -->
>   <context-param>
>   	<param-name>contextConfigLocation<param-name>
>  	 	<param-value>/WEB-INF/spring/root-context.xml
>       	/WEB-INF/spring/security-context-Final-Ver.xml
>   	</param-value>
>   </context-param>
>
>       <!-- ================================================== -->
>   	<!-- spring security가 스프링 MVC에서 사용되기 위해서 필터를 이용   -->
>   	<!-- 하여 스프링 동작에 관여하도록 설정                          -->
>   	<!-- ************************************************** -->
>   	<!-- filter와 mapping만으로는  springSecurityFilterChain  -->
>   	<!-- 빈이 제대로 설정되지 않아서 에러가 발생함 ( 스프링  시큐리티 설정 파일을  -->
>   	<!-- 찾을 수 없기 때문임 ) 따라서 security-context.xml을 읽을 수 -->
>   	<!-- 있도록 추가 설정을 해줘야함. -->
>   	<!-- ================================================== -->
>   	<filter>
>          <filter-name>springSecurityFilterChain</filter-name>
>          <filter-class>
>              org.springframework.web.filter.DelegatingFilterProxy
>          </filter-class>
>      </filter>
>
>   	<filter-mapping>
>          <filter-name>springSecurityFilterChain</filter-name>
>          <url-pattern>/*</url-pattern>
>      </filter-mapping>
>
> ```
>
> **security-conext.xml \_ 설정**
>
> - 1 . DB를 사용하지 않는 방법
>   > - [security-context-NoDB-Ver.xml]("https://github.com/edel1212/springStudy/blob/main/ex04/src/main/webapp/WEB-INF/spring/security-context-NoDB-Ver.xml")
> - 2 . DB를 사용하는 방법
>   > - [security-context-DB-Ver.xml]("https://github.com/edel1212/springStudy/blob/main/ex04/src/main/webapp/WEB-INF/spring/security-context-DB-Ver.xml)
> - 3 . DB와 Mybatis를 함께 사용하는 방법
>   > - [security-context-Mybatis-DB-Ver.xml]("https://github.com/edel1212/springStudy/blob/main/ex04/src/main/webapp/WEB-INF/spring/security-context-Mybatis-DB-Ver.xml)
> - 4 . 최종 믹스 버전
>   > - [security-context-Final-Ver.xml]("https://github.com/edel1212/springStudy/blob/main/ex04/src/main/webapp/WEB-INF/spring/security-context-Final-Ver.xml)
>
> ---

<hr style="margin:25px 0 25px 0"/>
<h3>3 ) security 사용 패턴</h3>

> 1 . 권한별 접근을 제한할 URL을 설정이 가능하다
>
> ✅ 아래와같이 intercept할 url 과 pattern으로 해당 권한에 따른 접근을 체한할 수 있지만
>
> ```xml
>
>    <!-- security-context.xml -->
>
>    <security:http >
>        <!-- URL별 권한 -->
>        <security:intercept-url pattern="/sample/All" access="permitAll"/>
> 		<security:intercept-url pattern="/sample/member" access="hasRole('ROLE_MEMBER')"/>
> 		<security:intercept-url pattern="/sample/admin" access="hasRole('ROLE_ADMIN')"/>
>    </security:http>
>
> ```
>
> **_ TODO :: 2022-09-05 여기서 부터 이어서 수정 작성해주자!_**
>
> 그렇게 되면 번거롭게 계속 추가 해줘야하는 수고가있다 따라서 아래와 같은 방법이 아닌 @PreAuthorize으로도 가능하다.
>
> 🎈단 이것은 [serlvet-context.xml]("https://github.com/edel1212/basicSpringProject/blob/main/src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml") 에 security:global-method-security 설정을 해줘야함!
>
> @See : [BoardController.java]("https://github.com/edel1212/basicSpringProject/blob/main/src/main/java/com/yoo/controller/BoardController.java")

> 2 . 로그인 및 로그인 성공 시 가게 될 handler를 작성 연결이 가능하다.
>
> ✅ 여기서 id="customloginSuccess" 로 빈 주입된 Class는 로그인이 성공 됐을 경우 타게되는 Class 이며 AuthenticationSuccessHandler르 구현 해줘야한다!
>
> @See : [CustomLoginSuccessHandler.java]("https://github.com/edel1212/basicSpringProject/blob/main/src/main/java/com/yoo/security/CustomLoginSuccessHandler.java)

```xml
    <!-- CustomLoginSuccessHandler 빈 주입  -->
	<bean id="customloginSuccess" class="org.zerock.security.CustomLoginSuccessHandler"/>

    <security:http >
        <!-- 로그인 핸들러 -->
        <security:form-login login-page="/customLogin" authentication-success-handler-ref="customloginSuccess"/>
    </security:http >
```

> 3 . 권한에 맞지 않는 잘못된 접근일 경우 hanlder 작성도 가능하다.
> ✅ customAccessDenied는 AccessDeniedHandler를 구현한 Class로 접근이 제한된 페이지 도달 시 작성이 가능하다.
>
> @See : [CustomAccessDeniedHandler.java]("https://github.com/edel1212/basicSpringProject/blob/main/src/main/java/com/yoo/security/CustomAccessDeniedHandler.java)

```xml

    <security:http >
        <!-- 잘못된 접근시 핸들러 -->
		<security:access-denied-handler ref="customAccessDenied"/>
    </security:http >
```

> 4 . 로그아웃 처리

```xml
    <!-- 로그아웃 시 핸들러 -->
    <security:http >
        <!-- 로그아웃 시 쿠키삭제 -->
			<security:logout logout-url="/logout" logout-success-url="/" invalidate-session="true"
				delete-cookies="remember-me,JSESSION_ID" /> <!-- JSESSION_ID는 톰겟에서 발행한 쿠키의 이름이다 -->
    </security:http >
```

> 5 . 쿠키에 계정 정보를 기억 remember-me 자동로그인 기능

```xml
    <!--
				자동로그인 설정 :: 커스텀하여 사용할 것이 아니라면 시큐르티 구조에 맞는 table이 생성되어 있어야함
				               ex) CREATE TABLE persistent_logins (
									username VARCHAR(64) NOT NULL
									, series VARCHAR(64) PRIMARY KEY
									, token VARCHAR(64) NOT null
									, last_used TIMESTAMP NOT null
								)

				✅ @see: [name='rememeber-me']를 사용하여 파라미터 key값을 맞춰
				   확인 유무를 전달하는것으 확인 할 수 있다.
			-->
			<security:remember-me data-source-ref="dataSource" token-validity-seconds="604800"/>
```

> 5 . DB를 이용하여 로그인 확인을 하기 위해선

- 회원의 정보 table의 형식을 맞춰줘야 하는 번거로움이 있는데 그럴때는 핸들러를 생성하여 내가 custom하여 DB를 만들어 줄 수있다.
- CustomUserDetailsService에서 처리하지만 해당 Class에서 반환 하는 **_new CustomUser(vo)_** 쪽의 생성자 쪽이 핵심 로직이다.

```xml
    <!-- ----------------------------------------------------------------------- -->
    ✅여기서 중요한 것은 시큐리티에서 지원하는 비밀번호 암호화인
    **org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder** 를 빈으로 주입해 준 후
    <security:authentication-provider>
    </security:authentication-provider>
    안에 넣어줘야 한다는 것이다!
    <!-- ----------------------------------------------------------------------- -->
    <!-- BCryptPasswordEncoder 빈 주입  -->
		<bean id="bcryptPasswordEncoder" class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder"/>


    <!-- CustomUserDetailsService 빈 주입  -->
	<bean id="customUserDetailsService"  class="org.zerock.security.CustomUserDetailsService"/>

   <!-- 인증과 권한 처리 -->
		<security:authentication-manager>
			<!-- UserDetailsService를 이용한 로그인처리
					 ✅ 상단에 bean으로 주입해준 CustomUserDetailsService를 사용
					    해당 빈은 UserDetailsService구현한 구현체이며
					    로그인 시 Custom 한 service 로직을 사용함
					    @see : CustomUserDetailsService.java -->
			<security:authentication-provider user-service-ref="customUserDetailsService">
				<!-- security에서 제공하는 클래스를 불러서 빈으로 만든 후 암호화에 사용 -->
				<security:password-encoder ref="bcryptPasswordEncoder"/>
			</security:authentication-provider>
		</security:authentication-manager>

```

```java
/**
* java
*/
    @Log4j
    public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.warn("Load User By UserName : " + username);

		//받아온 ID를 통해 정보를 가져옴 pw 체크를 하진 않음
		MemberVO vo = memberMapper.getMemberInfo(username);

		log.warn("queried by member Mapper :: " + vo);

		/***
		 * 값이 있을 경우 CustomUser에 Mybatis를 통해 얻어온 데이터를
		 * 주입하여 객체변수를 return 해준다
		 * */
		return vo == null ? null : new CustomUser(vo);
	}


/*************************************************************************************************/
/*************************************************************************************************/
/*************************************************************************************************/
/*************************************************************************************************/
/*************************************************************************************************/


/**
 * @Desripction : 해당 class는 Security에서 제공하는
 *  			  User.java 파일을 상속 받아 사용하며
 *  			  로그인에 관한 정보를 갖는 class이다.
 *
 *  			  ✅ 해당 class를 사용 하는 이유는
 *                   기존에 제공하는 Security의 로그인 정보 말고도
 *                   다른 다양한 정보를 추가하여 사용 가능
 *
 *                ✅ username으로 ID를 사용하는데 이러한 헷갈릴 수
 *                   있는 변수를 내가 커스텀하여 사용 이 가능하단 점이다
 *
 *                ✅ 해당 객체는 부모 class읜 User의 생성에 필요한
 *                   변수를 맞춰서 넣어준 후 인증에 사용학ㅎ
 *                   변수인 MemberVO member에 데이터를 주입
 *                   ex) this.member = vo
 *                   로 넣어주어 불러서 사용이 가능하다는 장점이 있다!
 *                   @see : admin.jsp << security tag를 이용해서 사용중
 *
 *                   * jsp 내 tag 명령어
 *                     - hasRole(??)       		: 해당 권한이 있으면 TRUE를 반환
 *                     - hasAuthority??()  		: 해당 권한이 있으면 TRUE를 반환
 *
 *                     - hasAnyRole([??,??])    : 여러 권한중 하나라도 해당하면 TURE
 *                     - hasAnyAuthority([???]) : 여러 권한중 하나라도 해당하면 TURE
 *
 *                     - principal 				: 현재 사용자의 정보를 의미
 *                     - permitAll				: 모든 사용자에게 허용
 *                     - denyAll				: 모든 사용자에게 거부
 *
 *                     - isAnonymous() 			: 익명의 사용자의 경우 로그인을 하지 않은 경우에 해당
 *                     - isAuthenticated() 		: 인증된 사용자라면 TRUE
 * **/
@Getter
public class CustomUser extends User{

	private static final long serialVersionUID = 1L;

	private MemberVO member;

	public CustomUser(String username
					, String password
					, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public CustomUser(MemberVO vo) {
		super( vo.getUserid()
			 , vo.getUserpw()
			 , vo.getAuthList().stream()
			 				   .map(auth -> new SimpleGrantedAuthority(auth.getAuth())) // 권한 목록은 SimpleGrantedAuthority을사용해서 변환해 줘야한다!
			 				   .collect(Collectors.toList())
			 );
		this.member = vo;
	}

}


```
