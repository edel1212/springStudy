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
 
 
2) 파일 다운로드 및 삭제
 - UploadController.java 
 	downloadFile(@RequestHeader("User-Agent") String userAgent ,String fileName) 확인 
 	deleteFile(@RequestBody String req) 확인 
