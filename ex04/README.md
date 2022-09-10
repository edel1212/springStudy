b<h1> Spring Security </h1>

기본적으로 스프링 시큐리티는 인터셉터와 같은 방식 진행된다.

👿 Spring Security 사용시 주의 할것은

1 . 비동시 통신 시 csrf-token값을 넘겨주자!

- 기억해두어야 header로 값을 넘겨주는것과 body로 값을 넘겨줄 때 key값이 다르다

- header : `X-CSRF-TOKEN` : `${_csrf.token}`

- body : `${_csrf.parameterName}` : `${_csrf.token}`

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
> ✅ 아래와같이 intercept할 url 과 pattern으로 해당 URL에 따른 권한에 맞게 접근을 제한이 가능하다.
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
> ✅위의 방법의 단점은 URL의 개수가 늘어날수록 추가 및 관리가 힘들다는 것이다
>
> \_ 그럴경우 servlet-context.xml의 설정으로 어노테이션으로 처리가 가능하다.
>
> - 1 . servlet-context.xml에 namespaces에 security를 추가
> - 2 . 추가 후 상단에 생긴 security 버전을 지워준다
> - 3 . security:global-method-security 설정 추가
>
> ```xml
> <!-- servlet-context.xml -->
>
> 	<!-- xml상단의 security 버전을 지워줘야한다. -->
> 	<beans:beans
> 		<!-- Code... -->
> 		xsi:schemaLocation="http://www.springframework.org/schema/security
> 		http://www.springframework.org/schema/security/spring-security.xsd"  >
>
> 	<!-- 스프링시큐리트 어노테이션을 사용하기 위한 설정 :: 상단 security 버전을 지워줘야 에러가 안남! -->
> 	<security:global-method-security pre-post-annotations="enabled" secured-annotations="enabled"/>
>
> ```
>
> ✅위의 servlet-context.xml 설정이 되었다면 Cotnroller에서 어노테이션으로 접근 제한이 가능하다
>
> @See : [BoardController.java]("https://github.com/edel1212/basicSpringProject/blob/main/src/main/java/com/yoo/controller/BoardController.java")
>
> ```java
> // Java - controller
> 	//Code...
>
> 	@PreAuthorize("hasAnyRole('ROLE_ADMIN')") // 해당 hasAnyRole ('접근권한') 으로 제한이 가능
> 	@GetMapping("/register")
> 	public String register(Model mdoel) {
> 		log.info("register..");
> 		return "/board/register";
> 	}
>
> ```
>
> 2 . Login-Handler 사용 및 흐림
>
> 🎈 주의사항 : 모든 URL은 Mapping이 가능한 Controller가 필요하다 LoginPage도 예외가 아님!
>
> ```xml
> <!-- security.xml -->
> 	<!-- 로그인 핸들러 -->
>
> 	<!-- CustomLoginSuccessHandler 빈 주입  -->
> 	<bean id="customloginSuccess" class="org.zerock.security.CustomLoginSuccessHandler"/>
>
> 	<!-- ====================================================================
> 		login-processing-url               : 클라이언트 단에서 로그인 정보를 넘김 URL
> 											[ 해당 설정이 없으면 form에서 넘기는 action URL을 알아서 인터셉팅해서 로그인체크를함 ]
> 		login-page                         : 로그인 페이지 PageURL [ "/ABC/DEG/customLogin" ]로 하면 해당 URL을 찾아감!
> 		authentication-success-handler-ref : 성공시 타게될 Handler 빈주입 설정 필요
> 	==================================================================== -->
> 	<security:form-login login-processing-url="/loginReq" login-page="/user/login" authentication-success-handler-ref="customloginSuccess"/>
> ```
>
> ✅ 클라이언트단 전송
>
> 🎈주의 : 기본적으로 spring security는 csrf 공격을 방어하기위해 토큰값을 넘겨줘야한다!
>
> ```jsp
> <!-- jsp -->
>
> 	<!-- form 사용 -->
> 	<form method="post" action="/loginReq">
> 		<input type="text" name="username" value="admin" >
> 		<input type="password" name="password" value="admin" >
> 		<input type="submit" >
> 		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" >
> 		<input type="checkbox" name="remember-me"> Remember Me
> 	</form>
>
> 	<!-- ===================================================================== -->
>
> 	<!-- fetch 사용 -->
> 	<button id="fetchBtn">Fetch Login Btn</button>
>
> 	<script>
>
> 		document.querySelector("#fetchBtn").addEventListener("click",(e)=>{
> 			fetch("/loginReq",{
> 			      method: "POST",
> 			      headers: {
> 			        Accept: "application/json",
> 					//보내는 데이터 타입은 apllication/json 이 아니다!
> 			        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
> 					//해당 Key값은 header에 들어가는 형식이므로 ${_csrf.parameterName}사용❌
> 			        "X-CSRF-TOKEN": "${_csrf.token}",
> 			      },
> 				  // 따라서 보내는 파라미터 형식도 URLSearchParams()를 사용!
> 			      body: new URLSearchParams({
> 			    	  "username" : "admin90",
> 			    	  "password" : "pw90"
> 			      })
> 			}).then((response) => {
> 				/**
> 				* @Description : 성공 시 받아온 값은 response.json()가 불가능하다!
> 				                 로그인 방식은 비동기 처리보다는 form 처리 방식이
> 				                 더 간단하고 처리도 확실해보인다!
> 				**/
> 				console.log(response);
> 				})
>     		.catch((error) => console.log(error));
>
> 		});//click
>
> 	</script>
>
> ```
>
> ✅ 서버단 ID, PW 확인 [ security:authentication-manager ]
>
> 🔽 인증과 권한 처리는 security:authentication-manager의 설정에 맞춰서 한다!
>
> ```xml
> <!-- security-context.xml -->
>
> 	<!-- CustomUserDetailsService 빈 주입  -->
> 	<bean id="customUserDetailsService"  class="com.yoo.security.CustomUserDetailsService"/>
>
> 	<!-- 인증과 권한 처리 -->
> 		<security:authentication-manager>
>
> 			<!--==============================================================
> 				UserDetailsService를 이용한 로그인처리
> 				 ✅ 상단에 bean으로 주입해준 CustomUserDetailsService를 사용
> 				    해당 빈은 UserDetailsService구현한 구현체이며
> 				    로그인 시 Custom 한 service 로직을 사용함
> 				    @see : CustomUserDetailsService.java
> 			==============================================================-->
> 			<security:authentication-provider user-service-ref="customUserDetailsService">
>
> 				<!-- security에서 제공하는 암호화 클래스를 불러서 빈으로 만든 후 암호화에 사용 -->
> 				<security:password-encoder ref="bcryptPasswordEncoder"/>
>
> 			</security:authentication-provider>
>
> 		</security:authentication-manager>
>
> ```
>
> ✅ 로그인 정보 확인 [ UserDetailsService ]
>
> 🎈 주의사항 : 클라이언트단에서 넘겨주는 pw의 key값은 **password**이어야 한다!
>
> 🔽 해당 Class는 UserDetailsService Interface를 구현한 Class이다!
>
> - 1 . login 정보가 들어오면 체크를 하는 Handler이다
> - 2 . UserDetailsService에서는 loadUserByUsername(String params)를 구현하게 한다
> - 3 . 해당 메서드의 반환 타입은 **UserDetails**이다
> - 4 . 메서드의 반환 정보가 null 인지 아닌지로 로그인 성공 여부를 판단한다.
> - 5 . 해당 Detailservice를 구현해서 좋은 점은 Security에 정한 정보뿐만 아닌 내가 정한 컬럼명이나 정보를 추가가 가능하다는 것이다.
> - 6 . 해당 Class를 파악할 때는 다형성을 확인하면서 보도록 하자!
>
> ```java
> // Java
>
> /*******************************************************************************/
> /*************************CustomUserDetailsService*****************************/
> /*******************************************************************************/
> 	/**
> 	 * @Description : 1) 클라이언트 단에서 넘겨준 데이터를 기준으로 UserDetails를 반환하는 Class
> 	 * 				     CustomUser를 이용하여 id , pw, auth를 기존 security에서쓰는
> 	 * 				     username이 아닌 userid 변환 및 내가 원하는 정보를 추가하여 객체 상태로
> 	 *				     전달해 주며 반환값의 유무로 로그인 성공 필패를 분기해준다.
> 	*
> 	*
> 	* 				  	2) 여기서 핵심 로식은 	return vo == null ? null : new CustomUser(vo); 쪽
> 	* 						CustomUser.java의 부모Class의 super()에서 생성자 데이터를 만들어줄때 비밀번호를 체크한다!
> 	* */
> 	@Log4j
> 	public class CustomUserDetailsService implements UserDetailsService{
>
> 		@Autowired
> 		private MemberMapper memberMapper;
>
> 		@Override
> 		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
> 		log.warn("Load User By UserName : " + username);
>
> 		//받아온 ID를 통해 정보를 가져옴 pw 체크를 하진 않음
> 		MemberVO vo = memberMapper.getMemberInfo(username);
>
> 		log.warn("queried by member Mapper :: " + vo);
>
> 		/***
> 		 * 값이 있을 경우 CustomUser에 Mybatis를 통해 얻어온 데이터를
> 		 * 주입하여 객체변수를 return 해준다
> 		 *
> 		 * ✔ 해당 CustomUser객체를 생성할때 pw를 확인한다.
> 		 *    - 해당 class의 부모 class인 User의 생성자에서!
> 		 * */
> 		return vo == null ? null : new CustomUser(vo);
> 	}
>
> 	/*******************************************************************************/
> 	/********************************CustomUser************************************/
> 	/*******************************************************************************/
>
> 	/**
> 	 * @Desripction : 해당 class는 Security에서 제공하는
> 	 *  			  User.java 파일을 상속 받아 사용하며
> 	 *  			  로그인에 관한 정보를 갖는 class이다.
> 	 *
> 	 *  			  ✅ 해당 class를 사용 하는 이유는
> 	 *                   기존에 제공하는 Security의 로그인 정보 말고도
> 	 *                   다른 다양한 정보를 추가하여 사용 가능
> 	 *
> 	 *                ✅ username으로 ID를 사용하는데 이러한 헷갈릴 수
> 	 *                   있는 변수를 내가 커스텀하여 사용 이 가능하단 점이다
> 	 *
> 	 *                ✅ 해당 객체는 부모 class읜 User의 생성에 필요한
> 	 *                   변수를 맞춰서 넣어준 후 인증에 사용학ㅎ
> 	 *                   변수인 MemberVO member에 데이터를 주입
> 	 *                   ex) this.member = vo
> 	 *                   로 넣어주어 불러서 사용이 가능하다는 장점이 있다!
> 	 *                   @see : admin.jsp << security tag를 이용해서 사용중
> 	 *
> 	 *                   * jsp 내 tag 명령어
> 	 *                     - hasRole(??)       		: 해당 권한이 있으면 TRUE를 반환
> 	 *                     - hasAuthority??()  		: 해당 권한이 있으면 TRUE를 반환
> 	 *
> 	 *                     - hasAnyRole([??,??])    : 여러 권한중 하나라도 해당하면 TURE
> 	 *                     - hasAnyAuthority([???]) : 여러 권한중 하나라도 해당하면 TURE
> 	 *
> 	 *                     - principal 				: 현재 사용자의 정보를 의미
> 	 *                     - permitAll				: 모든 사용자에게 허용
> 	 *                     - denyAll				: 모든 사용자에게 거부
> 	 *
> 	 *                     - isAnonymous() 			: 익명의 사용자의 경우 로그인을 하지 않은 경우에 해당
> 	 *                     - isAuthenticated() 		: 인증된 사용자라면 TRUE
> 	 * **/
> 	@Getter
> 	public class CustomUser extends User{
>
> 		private static final long serialVersionUID = 1L;
>
> 		private MemberVO member;
>
> 		public CustomUser(String username
> 						, String password
> 						, Collection<? extends GrantedAuthority> authorities) {
> 			super(username, password, authorities);
> 		}
>
> 		public CustomUser(MemberVO vo) {
> 			super( vo.getUserid()
> 				, vo.getUserpw()
> 				, vo.getAuthList().stream()
> 								.map(auth -> new SimpleGrantedAuthority(auth.getAuth())) // 권한 목록은 SimpleGrantedAuthority을사용해서 변환해 줘야한다!
> 								.collect(Collectors.toList())
> 				);
> 			this.member = vo;
> 		}
>
> 	}
>
>
> }
>
> ```
>
> ✅ 로그인 실패 시 [AccessDeniedHandler]
>
> 🔽 security-conext
>
> ```xml
> <!-- security-context.xml -->
>
> 	<!-- CustomAccessDeniedHandler 빈 주입  -->
>    <bean id="customAccessDenied" class="com.yoo.security.CustomAccessDeniedHandler"/>
>
> 	<!-- 잘못된 접근시 핸들러 -->
> 	<security:http>
> 		<!-- Bean 주입 필요 -->
> 		<security:access-denied-handler ref="customAccessDenied"/>
> 	</security:http>
>
> ```
>
> 🔽 AccessDeniedHandler 구현 Class
>
> ```java
> //Java
>
> /**
> * @Description : 해당 클래스는 AccessDeniedHandler 인터페이스를 직접 구현하였고
> * 				  해당 인터페이스의 메서드는 handle()뿐이기 떄문에
> * 				  HttpServletRequest, HttpServletPesponse를 파라미터로
> * 				  사용할 수 있기때문에 직접적으로 서블릿 API를 이용하여 처리가 가능하다
> *
> *
> *                @See : root-context에 bean을 주입하지 않고
> *                       security-context에서 bean을 주입해줌
> * */
> @Log4j
> public class CustomAccessDeniedHandler implements AccessDeniedHandler{
>
> 	@Override
> 	public void handle(HttpServletRequest request, HttpServletResponse response,
> 			AccessDeniedException accessDeniedException) throws IOException, ServletException {
>
> 		log.error("Access Denied Handler");
>
> 		log.error("Redirect....");
>
> 		// 이동 시켜버린다.
> 		response.sendRedirect("/accessError");
>
> 	}
>
> }
> ```
>
> ✅ 로그인 성공 시
>
> 🎈주의 :customloginSuccess Class는 Interface인 AuthenticationSuccessHandler를 구현 해줘야한다!
>
> 🔽 security-context.xml
>
> ```xml
> <!-- security-context.xml -->
>
>    <!-- CustomLoginSuccessHandler 빈 주입  -->
> 	<bean id="customloginSuccess" class="org.zerock.security.CustomLoginSuccessHandler"/>
>
>    <security:http >
>        <!-- 로그인 핸들러 -->
>        <security:form-login login-processing-url="/loginReq"
> 			login-page="/user/login" authentication-success-handler-ref="customloginSuccess"/>
>    </security:http >
>
> ```
>
> 🔽 AuthenticationSuccessHandler를 구현한 Class
>
> ```java
> //Java
> 	/**
> 	 * @Description : 로그인이 성공하면 타게되는 Class
> 	 *                성공 이후 특정한 동작을 제어하기위해 AuthenticationSuccessHandler를
> 	 *                구현한 후 security-context에서 bean주입을 통해 사용한다.
> 	 * */
> 	@Log4j
> 	public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{
> 		@Override
> 		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
> 				Authentication authentication) throws IOException, ServletException {
>
> 			log.warn("Login Success");
> 			List<String> roleNames = new ArrayList<>();
>
> 			//authentication를 사용해서 로그인 성공한 유저의 데이터를 server단에서 사용가능
> 			CustomUser vo =  (CustomUser) authentication.getPrincipal();
> 			log.info("vo :: " + vo);
>
> 			//권한 확인 한가지 종류의  권한이 아닐수 있으므로 배열로 들어옴
> 			authentication.getAuthorities().forEach(authority -> {
> 				roleNames.add(authority.getAuthority());
> 			});
>
> 			log.warn("ROLE NAMES :: " + roleNames);
>
> 			if(roleNames.contains("ROLE_ADMIN")) {
> 				response.sendRedirect("/sample/admin");
> 				return;
> 			}
>
> 			if(roleNames.contains("ROLE_MEMBER")) {
> 				response.sendRedirect("/sample/member");
> 				return;
> 			}
>
> 			//권한이 없을경우 그냥 기본 페이지로
> 			response.sendRedirect("/");
>
> 		}
>
> 		//__Eof__
> 	}
> ```
>
> 4 . 로그아웃 처리
>
> 🔽security-context.xml
>
> ```xml
> <!-- security-conext.xml -->
>
>    <!-- 로그아웃 시 핸들러 -->
>    <security:http >
>    <!-- 로그아웃 시 쿠키삭제 -->
>    <security:logout logout-url="/logout" logout-success-url="/" invalidate-session="true"
>    delete-cookies="remember-me,JSESSION_ID" /> <!-- JSESSION_ID는 톰겟에서 발행한 쿠키의 이름이다 -->
>    </security:http >
>
>
> ```
>
> 5 . 로그인 성공시 쿠키에 계정 정보를 기억 remember-me 자동로그인 기능
>
> 🔽 security-conext.xml
>
> ```xml
> <!-- security-context.xml -->
>    <!--
> 				자동로그인 설정 :: 커스텀하여 사용할 것이 아니라면 시큐르티 구조에 맞는 table이 생성되어 있어야함
> 				               ex) CREATE TABLE persistent_logins (
> 									username VARCHAR(64) NOT NULL
> 									, series VARCHAR(64) PRIMARY KEY
> 									, token VARCHAR(64) NOT null
> 									, last_used TIMESTAMP NOT null
> 								)
>
> 				✅ @see: [name='rememeber-me']를 사용하여 파라미터 key값을 맞춰
> 				   확인 유무를 전달하는것으 확인 할 수 있다.
> 			-->
> 			<security:remember-me data-source-ref="dataSource" token-validity-seconds="604800"/>
> ```
>
> 🔽 클라이언트단
>
> ```jsp
> <!-- jsp -->
>
> 	<form method="post" action="/login">
> 		<input type="text" name="username" value="admin" >
> 		<input type="password" name="password" value="admin" >
> 		<input type="submit" >
> 		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" >
> 		<!-- 해당 name을 맞춰서 boolean Type으로 값을 던지면 쿠키에 저장된다! -->
> 		<input type="checkbox" name="remember-me"> Remember Me
> 	</form>
>
> ```
>
> 6 . fetch를 사용하여 token값 전달
>
> 🔽 클라이언트단
>
> ```javascript
> // javascript
>    fetch("URL", {
>      method: "POST",
>      headers: {
>        Accept: "application/json", //수신 결과 데이터 타입
>        "Content-Type": "application/json", //송신 파라미터 타입
>        "X-CSRF-TOKEN": csrfToken, //csrf 값전달
>      },
>      body: String(this.bno),
>    })
>      .then((response) => response.json())
>      .then((result) => { ....Code! })
> ```
>
> ---
