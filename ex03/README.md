# spring_ex03

File 업로드 및 다운로드

1) 파일 업로드
 - 기본적으로 본 예제 프로젝트에서는 form 전달 방식 대신 비동기(fetch)을 위주로 사용함. 
   => /uploadForm (form 방식)
   => /uploadAjax (비동기 방식)
   *주의 
    1) form 방식을 사용 할 때 accept-charset 을 설정해 주지 않으면 한글이 깨짐 해방 해당 uploadForm.jsp 확인
    2) pom(메이븐) 설정은 javax.servlet 버전을 바꿔줘야함 ! [artifactId 명도 다름]
    3) @RequestBody를 사용해서 json 데이터를 문자열로 받은 수 있지만 다시 파시을 위해 gson을 메이븐에 추가하여 사용
 - UploadController.java 의  uploadNewVerEntity(MultipartFile[] uploadFile) 확인 
    4) web.xml에 servlet 버전 변경으로 인해 버전 변경 및 multipart 설정 추가 필요 [ 위치중요 <serlvet>안에 해야함</serlvet> ]
    5) serlvet.xml 에도 multipart 관련 bean 주입 필요!
 
2) 파일 다운로드 및 삭제
 - UploadController.java 
 	downloadFile(@RequestHeader("User-Agent") String userAgent ,String fileName) 확인 
 	deleteFile(@RequestBody String req) 확인 

 	
3) 잘못 업데이트 된 파일 삭제
 - Quartz 라이브러리를 사용 
   => 해당 라이브러리는 일반적으로 스케줄러를 구성하기 위해서 사용한다 내가 설정한 주기별로 특정 프로그램을 실행할수 있다
      대량의 데이터를 주기적으로 읽고 쓰는 작업은 Spring Batch 가 적합하지만 설정이 좀 더 복잡함으로 해당
      라이브러리로 처리함.
   => 방법) 1) pom에 quartz, quartz-jobs를 추가
           2) root-context의 Namespaces에 task를 추가 후  <task-annotation-driven/>을 추가
           3) task를 지정할 패키지 및 파일 생성 후 해당 파일을 root-context에서 context-scan 시킨다. 
              @See :     FileCheckTast.java
              
                          