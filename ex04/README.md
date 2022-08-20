<h1> Spring Security </h1>

기본적으로 스프링 시큐리티는 인터셉터와 같은 방식 진행된다.

<hr style="margin:25px 0 25px 0"/>
<h3>1 ) pom 설정</h3>

> - spring-security-web, spring-security-config, spring-security-core, spring-security-taglibs
>
>   @See : [pom.xml]("https://github.com/edel1212/springStudy/blob/main/ex04/pom.xml")
>
> ✅ 해당 4개 jar파일은 메이븐에 주입해줘야하며 **_버전은 꼭 같아야한다!_**

<hr style="margin:25px 0 25px 0"/>
<h3>2 ) sercurity-context!</h3>

root-context.xml에서 진행해도 괜찮지만, 좀 더 의미에 맞게 개발하기 위해 xml을 나눠서 개발하는 방식을 선호함.

> 방법은 총 4가지를 나눠서 사용함.
>
> 🎈기본 설정 방법은
>
> > - pom에 스프링 설정 메이븐을 추가해준다.
> > - spring security를 설정한 xml을 만든다.
> > - namespaces에서 **security** 체크 해준다.
> >
> >   👿여기서 중요한건 해당 xml의 상단 xsi:schemaLocation=의 security 버전을 지워줘야 에러가 안난다.
> >
> > - web.xml에서 내가 사용하려는 security를 읽을 수 있도록 설정해준다.
> >
> > ```xml
> > <!-- web.xml -->
> > <context-param>
> > <param-name>contextConfigLocation<param-name>
> > <param-value>/WEB-INF/spring/root-context.xml
> >     /WEB-INF/spring/security-context-Final-Ver.xml
> >     </param-value>
> > 	</context-param>
> >
> >     <!-- ================================================== -->
> > 	<!-- spring security가 스프링 MVC에서 사용되기 위해서 필터를 이용   -->
> > 	<!-- 하여 스프링 동작에 관여하도록 설정                          -->
> > 	<!-- ************************************************** -->
> > 	<!-- filter와 mapping만으로는  springSecurityFilterChain  -->
> > 	<!-- 빈이 제대로 설정되지 않아서 에러가 발생함 ( 스프링  시큐리티 설정 파일을  -->
> > 	<!-- 찾을 수 없기 때문임 ) 따라서 security-context.xml을 읽을 수 -->
> > 	<!-- 있도록 추가 설정을 해줘야함. -->
> > 	<!-- ================================================== -->
> > 	<filter>
> >        <filter-name>springSecurityFilterChain</filter-name>
> >        <filter-class>
> >            org.springframework.web.filter.DelegatingFilterProxy
> >        </filter-class>
> >    </filter>
> >
> > 	<filter-mapping>
> >        <filter-name>springSecurityFilterChain</filter-name>
> >        <url-pattern>/*</url-pattern>
> >    </filter-mapping>
> >
> > ```
> >
> > ---
>
> - 1 . DB를 사용하지 않는 방법
> - 2 . DB를 사용하는 방법
> - 3 . DB와 Mybatis를 함께 사용하는 방법
> - 4 . 최종 버전
