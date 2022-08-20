<h1> File 업로드 및 다운로드 </h1>
<h3>1 . 파일 업로드 [ form 방식, 비동기 방식 ]</h3>

> - ✅업로드 작업 이전
>   > - @See : [web.xml]("https://github.com/edel1212/springStudy/blob/main/ex03/src/main/webapp/WEB-INF/web.xml") , [servlet-context.xml]("https://github.com/edel1212/springStudy/blob/main/ex03/src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")해당 두파일에 설정이 필요함
>   >   ***

```xml
 <!-- web.xml -->
      <!-- ========================================================== -->
		<!-- web.xml 설정은 WAS 자체의 설정일 뿐이고 스프링에서 업로드 처리는 		-->
		<!-- MultipartResolver라는 타입의 객체를 빈으로 틍록해야한다.             -->
		<!-- 이 bean 등록은 root-context가 아닌 servlet-context에 등록해야함.   -->
		<!-- ========================================================== -->
		<multipart-config>
			<location>C:\\upload\\temp</location>				<!-- 저장될 경로 -->
			<max-file-size>20971520</max-file-size> 			<!-- 1MB * 20 [업로드되는 파일의 저장할 공간과 업로드되는 파일의 최대 크기]-->
        	<max-request-size>41943040</max-request-size> 		<!-- 40MB [한번에 올릴 수 있는 최대 크기]-->
        	<file-size-threshold>20971520</file-size-threshold> <!-- 20MB [특정 사이즈의 메모리사용량]-->
		</multipart-config>


<!-- servlet-context.xml -->
   <!-- ============================================== -->
	<!-- StandardServletMultipartResolver 빈을 스프링에 등록 -->
	<!-- web.xml에도 설정해줘야함!                           -->
	<!-- ============================================== -->
	<beans:bean id="multipartResolver"
		class="org.springframework.web.multipart.support.StandardServletMultipartResolver">
	</beans:bean>
```

> - /uploadForm ( form 방식 )
>   **\_\_** @See : [uploadForm.jsp](https://github.com/edel1212/springStudy/blob/main/ex03/src/main/webapp/WEB-INF/views/uploadForm.jsp)
>
> ---
>
> - /uploadAjax ( 비동기 방식) **\_\_** @See : [uploadAjax.jsp](https://github.com/edel1212/springStudy/blob/main/ex03/src/main/webapp/WEB-INF/views/uploadAjax.jsp) \*주의

    1) form 방식을 사용 할 때 accept-charset 을 설정해 주지 않으면 한글이 깨짐 해방 해당 uploadForm.jsp 확인
    2) pom(메이븐) 설정은 javax.servlet 버전을 바꿔줘야함 ! [artifactId 명도 다름]
    3) @RequestBody를 사용해서 json 데이터를 문자열로 받은 수 있지만 다시 파시을 위해 gson을 메이븐에 추가하여 사용
    > ***

> - UploadController.java 의 uploadNewVerEntity(MultipartFile[] uploadFile) 확인

    4) web.xml에 servlet 버전 변경으로 인해 버전 변경 및 multipart 설정 추가 필요 [ 위치중요 <serlvet>안에 해야함</serlvet> ]
    5) serlvet.xml 에도 multipart 관련 bean 주입 필요!
       > ***

<hr style="margin:25px 0 25px 0"/>

<h3>2. 파일 다운로드 및 삭제</h3>

> - @See : [UploadController.java]("https://github.com/edel1212/springStudy/blob/main/ex03/src/main/java/org/zerock/controller/UploadController.java")
>
> ---
>
> > 하위 method 참고
> >
> > - downloadFile(@RequestHeader("User-Agent") String userAgent ,String fileName)
> > - deleteFile(@RequestBody String req)
> >
> > ---

<hr style="margin:25px 0 25px 0"/>

<h3>3. 잘못 업데이트 된 파일 삭제</h3>

> - **_Quartz 라이브러리를 사용_**
>   ***
>   > - 해당 라이브러리는 일반적으로 스케줄러를 구성하기 위해서 사용한다 내가 설정한 주기별로 특정 프로그램을 실행할수 있다
>   >   ***
>   > - 대량의 데이터를 주기적으로 읽고 쓰는 작업은 Spring Batch 가 적합하지만 설정이 좀 더 복잡함으로 해당 라이브러리로 처리함.
>   >   ***
>   >   > 방법)
>   >   >
>   >   > - 1. pom에 quartz, quartz-jobs를 추가
>   >   > - 2. root-context의 Namespaces에 task를 추가 후 <task-annotation-driven/>을 추가
>   >   > - 3. task를 지정할 패키지 및 파일 생성 후 해당 파일을 root-context에서 context-scan 시킨다.
>   >   > - @See : [FileCheckTask.java]("https://github.com/edel1212/springStudy/blob/main/ex03/src/main/java/org/zerock/task/FileCheckTask.java")
>   >   >
>   >   > ***
