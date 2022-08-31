<h1> File 업로드 및 다운로드 </h1>
<h3>1 . 파일 업로드 [ form 방식, 비동기 방식 ]</h3>

> ✅web.xml 설정
>
> 🎈- 주의사항
>
> 1 ) <multipart-config> 설정은 <servlet>target</servlet>안에 있어야한다.
>
> 2 ) web.xml의 상단 버전을 pom에서의 javax.servlet-api 버전과 맞게 해줘야한다.
>
> 👍 javax.servlet-api은 기본 2.5버전이지만 3 버전대로 pom에서 버전을 올려주자!
>
> ```xml
> <!-- web.xml -->
>       <!-- ========================================================== -->
> 		<!-- web.xml 설정은 WAS 자체의 설정일 뿐이고 스프링에서 업로드 처리는 		-->
> 		<!-- MultipartResolver라는 타입의 객체를 빈으로 틍록해야한다.             -->
> 		<!-- 이 bean 등록은 root-context가 아닌 servlet-context에 등록해야함.   -->
> 		<!-- ========================================================== -->
> 		<multipart-config>
> 			<location>C:\\upload\\temp</location>				<!-- 저장될 경로 -->
> 			<max-file-size>20971520</max-file-size> 			<!-- 1MB * 20 [업로드되는 파일의 저장할 공간과 업로드되는 파일의 최대 크기]-->
>        	<max-request-size>41943040</max-request-size> 		<!-- 40MB [한번에 올릴 수 있는 최대 크기]-->
>        	<file-size-threshold>20971520</file-size-threshold> <!-- 20MB [특정 사이즈의 메모리사용량]-->
> 		</multipart-config>
> ```
>
> ✅servlet-context.xml 설정
>
> ```xml
> <!-- servlet-context.xml -->
>    <!-- ============================================== -->
> 	<!-- StandardServletMultipartResolver 빈을 스프링에 등록 -->
> 	<!-- web.xml에도 설정해줘야함!                           -->
> 	<!-- ============================================== -->
> 	<beans:bean id="multipartResolver"
> 		class="org.springframework.web.multipart.support.StandardServletMultipartResolver">
> 	</beans:bean>
>
> ```
>
> ✅uploadForm ( form 방식 )
> **\_\_** @See : [uploadForm.jsp](https://github.com/edel1212/springStudy/blob/main/ex03/src/main/webapp/WEB-INF/views/uploadForm.jsp)
>
> 🎈- 주의사항
>
> 1 ) form 방식을 사용 할 때 accept-charset 을 설정해 주지 않으면 한글이 깨지므로 form에 설정 필요
>
> **- view**
>
> ```jsp
> <!-- jsp -->
> 	<!-- ================================================================== -->
> 	<!-- 신경써야 할부분은 from 부분의 enctype="multipart/form-data"로 지정하는것이다  -->
> 	<!-- input 부분에는 multiple 로 하나의 input으로 여러가지 파일을 받을 수 있게끔 함    -->
> 	<!-- ================================================================== -->
> 	<form action="/upload/uploadFormAction" method="POST" enctype="multipart/form-data"  accept-charset="UTF-8">
> 		<input type="file" name="uploadFile" multiple>
> 		<button>Submit</button>
> 	</form>
> ```
>
> **- controller**
>
> ```java
> //Java
> 	/**
> 	 * @Description : 1) 파일처리는 스프리에서 제공하는 MultipartFile(interface)를 사용
> 	 *                   화면에서 넘기는 여러가지 파일은 배열로 받아서 처리가 간능함
> 	 *
> 	 *                2) MultipartFile이 가지고 있는 Method
> 	 *                  - getName() 			:: 파라미터의 이름 input Tag의 이름
> 	 *                  - getOriginalFileName 	:: 업로드 되는 파일 이름
> 	 *                  - isEmpty() 			:: 파일이 존재 유/무
> 	 *                  - getSize() 			:: 업로드되는 파일의 크기
> 	 *                  - getBytes() 			:: byte[]로 파일 데이터 변환
> 	 *                  - transferTo(File file) :: 파일을 저장함
> 	 *
> 	 *@See			: uploadFormAction.jsp
> 	 * */
> 	@PostMapping("/uploadFormAction")
> 	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
> 		for(MultipartFile multipartFile : uploadFile) {
>
> 			//저장될 file Folder 경로
> 			String uploadFolder = "C:\\upload" ;
>
> 			log.info("--------------------------------------------");
> 			log.info("Upload File Name :::: " + multipartFile.getOriginalFilename());
> 			log.info("Upload File Size ::::"  + multipartFile.getSize());
>
> 			//(경로 , 파일 이름 그대로 확장자도 같이 넘어감!)
> 			File saveFile = new File(uploadFolder,multipartFile.getOriginalFilename());
>
> 			try {
> 				multipartFile.transferTo(saveFile);
> 			} catch (Exception e) {
> 				log.error(e.getMessage());
> 			}
>
> 		}
> 	}
> ```
>
> ✅uploadAjax ( 비동기 방식) **\_\_** @See : [uploadAjax.jsp](https://github.com/edel1212/springStudy/blob/main/ex03/src/main/webapp/WEB-INF/views/uploadAjax.jsp)
>
> 🎈- 주의사항
>
> 1 ) parameter를 받을때 @RequestBody를 사용해서 json 데이터를 문자로 직렬화하여 받은 수 있지만 메이븐에 gson을
>
> 추가하여 Java Object 형식으로 받아서 사용이 가능함
>
> **- pom.xml**
>
> ```xml
> <!-- pom.xml -->
> 	<!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
> 		<dependency>
> 		    <groupId>com.google.code.gson</groupId>
> 		    <artifactId>gson</artifactId>
> 		    <version>2.9.0</version>
> 		</dependency>
> ```
>
> **- controller**
>
> ```java
> //Java
>
> 	/**
> 	 * @Description : 오늘 날짜에 맞는 문자열 생성
> 	 * */
> 	private String getFolder() {
> 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
> 		Calendar today = Calendar.getInstance();
> 		String str = sdf.format(today.getTime());
> 		//File.separator "-" 를 파일 구문자로 바꾸는 코드
> 		return str.replace("-", File.separator);
> 	}
>
> 	/**
> 	 * @Description : 업로든 되는 파일이 image인지 체크
> 	 * */
> 	private boolean checkImageType(File file) {
> 		try {
> 			String contentType = Files.probeContentType(file.toPath());
> 			log.info("contentType ::: " + contentType);
> 			return contentType.startsWith("image");
> 		} catch (Exception e) {
> 			e.printStackTrace();
> 		}
> 		return false;
> 	}
>
> 	/**
> 	 * @Description : 파일의 정보를 가지고있는 AttachFileDTO를 사용한 방법
> 	 * */
> 	@PostMapping(value = "/uploadNewAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
> 	@ResponseBody
> 	public ResponseEntity<List<AttachFileDTO>> uploadNewVerEntity(MultipartFile[] uploadFile){
>
> 		List<AttachFileDTO>  list = new ArrayList<AttachFileDTO>();
> 		String uploadFolder = "C:\\upload";
>
> 		String uploadFolderPath = getFolder();
>
> 		//Make Folder - - - - - -
> 		File uploadPath = new File(uploadFolder,uploadFolderPath);
>
> 		if(uploadPath.exists()  == false) { // 해당 디렉토리가 없을경우
> 			/**
> 			 * @See : mkdir은 상위 디렉터리가 없는 경우 생성하지 못하지만 mkdirs는 상위 디렉터리가 없으면 생성한다.
> 			 *
> 			 *        ex)   File file = new File("D:\\테스트\\1\\2\\3");
> 							if(file.mkdir()){
> 							    System.out.println("디렉터리 생성 성공");
> 							} else{
> 							    System.out.println("디렉터리 생성 실패");
> 							}
>
> 							---------------------------------------------
>
> 							File file = new File("D:\\테스트\\1\\2\\3");
> 							if(file.mkdirs()){
> 							    System.out.println("디렉터리 생성 성공");
> 							} else{
> 							    System.out.println("디렉터리 생성 실패");
> 							}
> 			 * */
> 			uploadPath.mkdirs();
> 		}
>
> 		for(MultipartFile multipartFile : uploadFile) {
> 			AttachFileDTO attachDTO = new AttachFileDTO();
>
> 			String uploadFileName = multipartFile.getOriginalFilename();
> 			attachDTO.setFileName(uploadFileName);
> 			UUID uuid = UUID.randomUUID();
> 			uploadFileName = uuid.toString() + "_" + uploadFileName;
> 			try {
> 				File saveFile = new File(uploadPath,uploadFileName);
> 				multipartFile.transferTo(saveFile);
>
> 				attachDTO.setUuid(uuid.toString());
> 				attachDTO.setUploadPath(uploadFolderPath);
>
> 				if(checkImageType(saveFile)) {
> 					attachDTO.setImage(true);
>
> 					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
>
> 					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
> 					thumbnail.close();
> 				}
>
> 				list.add(attachDTO);
> 			} catch (Exception e) {
> 				e.printStackTrace();
> 			}
> 		}
> 		/**@See : HttpStatus.? 를 BadGateway로 하여도 return 및 파일은 만들어짐 다만 console에 해당 상태에 맞는 에러 로그는 뜸*/
> 		return new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);
> 	}
>
> ```

<hr style="margin:25px 0 25px 0"/>

<h3>2. 파일 다운로드 및 삭제</h3>

> ✅ 다운로드
>
> ```java
> //Java
> 	/**
> 	 * @Desription : 1) MINE 타입은 다운로드를 할수있는 'application/octet-steam'으로 지정
> 	 *
> 	 *               2) 다운로드시 저장 되는 이름은 'Content-Disposition'을 이용하여 지정
> 	 *                  - 사용 이유는 파일 이름에 대한 문자열 처리는 파일 이름이 한글인 경우 지정할 떄 깨지는 문제를 막기 위해서임
> 	 *
> 	 *               3) @RequestHeader("User-Agent") String userAgent를 이용하여 브라우저의 정류나 모바일인지 데스크탑인지
> 	 *                  브라우저 프로그램 종류등을 알수 있다.
> 	 * **/
> 	@GetMapping(value = "/download" , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
> 	@ResponseBody
> 	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent // 디바이스의 정보를 할수있 정보
> 												,String fileName){ //파일명 기간+파일명
>
> 		log.info("fileName :::" + fileName);//fileName :::/2022/06/27/file.png
>
> 		log.info("userAgent ::: " + userAgent);
> 		/**userAgent ::: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)
> 		* Chrome/103.0.0.0 Safari/537.36
> 		*/
>
> 		//지정경로의 File을 가져와 객체로만듬
> 		Resource resource = new FileSystemResource("c://upload//"+fileName);
>
> 		log.info("resource ::: " + resource) ;// resource ::: file [c:\\upload\2022\06\27\file.png]
>
> 		if(resource.exists() == false) { //resource가 존재하지 않을 경우!
> 			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
> 		}
>
> 		/***
> 		 * @Description : 파일 다운로드시 UUID 명 삭제
> 		 *
> 		 * @See         : 여기서 중요한건 파일 다운로드시 파일명은 HttpHeaders에서 정해진다는
> 		 *                것이다!!!!!
> 		 * **/
> 		String resoucreName = resource.getFilename();
>
> 		/**
> 		 * 등등 브라우저를 나눠서 처리도 가능함!
> 		 * */
> 		if(userAgent.contains("Edge")) {
> 			log.info("Edge!!!");
> 		}
> 		String resourceOriginalName =	resoucreName.substring(resoucreName.lastIndexOf("_")+1);
>
> 		log.info("resoucreName::: " + resoucreName);//resoucreName::: file.png
> 		log.info("resourceOriginalName::: " + resourceOriginalName);//resoucreName::: file.png
>
> 		HttpHeaders headers = new HttpHeaders();
>
> 		try {
> 			headers.add(
> 							  "Content-Disposition"
> //							, "attachment ; filename = " + new String(resoucreName.getBytes("UTF-8")
> 							, "attachment ; filename = " + new String(resourceOriginalName.getBytes("UTF-8")
> 							, "ISO-8859-1")
> 						);
> 			log.info("headers ::: " + headers);//headers ::: [Content-Disposition:"attachment ; filename = file.png"]
> 		} catch (UnsupportedEncodingException e) {
> 			e.printStackTrace();
> 		}
>
> 		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
>
> 	}
> ```
>
> ✅ 파일삭제
>
> ```java
> //Java
> 	@PostMapping("/deleteFile")
> 	@ResponseBody
> 	public ResponseEntity<String> deleteFile(@RequestBody String req) throws Exception{
> 		/**
> 		 * @Description : 문자열로 받은 req를 gson(json-simple 를 pom에 추가)을
> 		 *  			  사용해서 JSON 형식으로 파싱하여 사용! jackson이 메이븐에 추가
> 		 *  			  되어있다면 VO가 있다면 자동으로 파싱해줌!
> 		 * **/
> 		JSONParser parser = new JSONParser();
> 		JSONObject jsonObject = (JSONObject) parser.parse(req);
> 		log.info("jsonObject  ::: "  + jsonObject);
>
> 		log.info("fileName  ::: " + jsonObject.get("fileName"));
> 		String fileName = (String) jsonObject.get("fileName");
>
> 		log.info("type  ::: " +jsonObject.get("type"));
> 		String type = (String) jsonObject.get("type");
>
> 		File file;
>
> 		try {
> 			file = new File("C:\\upload\\"+ URLDecoder.decode(fileName,"UTF-8"));
> 			file.delete(); //파일 삭제
>
> 			/** Path를 사용해서 delete 방법 @See : basicSpringProject -> BoardServiceImpl 확인
>
> 				Path file = Paths.get("C:\\upload\\"
> 										+	attach.getUploadPath()
> 										+ "\\"
> 										+ attach.getUuid()
> 										+ "_"
> 										+ attach.getFileName());
> 				/**
> 				@Description : Files.deleteIfExists() 를 사용하면,
> 						파일이 존재하는 경우에는 파일을 삭제하고,
> 						파일이 존재하지 않는 경우에는 파일을 삭제하지 않고, false를 리턴합니다.
> 						(delete()의 경우에는, 파일이 존재하지 않는 경우 NoSuchFileException이 발생했지만,
> 						deleteIfExist()의 경우에는 Exception이 발생하지 않습니다.)
> 				//Delete
> 				Files.deleteIfExists(file);
> 				*/
>
>
> 			if("image".equals(type)) {
> 				String largeFileName = file.getAbsolutePath().replace("s_", "");
> 				file = new File(largeFileName);
> 				file.delete();
> 			}
> 		} catch (UnsupportedEncodingException e) {
> 			// TODO: handle exception
> 			e.printStackTrace();
> 			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
> 		}
>
> 		return new ResponseEntity<String>("delete",HttpStatus.OK);
> 	}
> ```

<hr style="margin:25px 0 25px 0"/>

<h3>3. 잘못 업데이트 된 파일 삭제 ::: 🎈Quartz 라이브러리를 사용</h3>

> ✅Quartz란?
>
> - 해당 라이브러리는 일반적으로 스케줄러를 구성하기 위해서 사용한다 내가 설정한 주기별로 특정 프로그램을 실행할수 있다
>
> - 대량의 데이터를 주기적으로 읽고 쓰는 작업은 Spring Batch 가 적합하지만 설정이 좀 더 복잡하고으로 해당 기능은 가운 기능이기에 해당 라이브러리로 처리함.
>
> 🎈방법
>
> - 1 ) pom에 quartz, quartz-jobs를 추가
>
> ```xml
> <!-- pom.xml -->
> 		<!-- ==================================== -->
> 		<!-- 스케쥴링을 위해 추가   -->
> 		<!-- ==================================== -->
> 		<!-- https://mvnrepository.com/artifact/org.quartz-scheduler/quartz -->
> 		<dependency>
> 		    <groupId>org.quartz-scheduler</groupId>
> 		    <artifactId>quartz</artifactId>
> 		    <version>2.3.0</version>
> 		</dependency>
>
> 		<!-- https://mvnrepository.com/artifact/quartz/quartz -->
> 		<dependency>
> 		    <groupId>org.quartz-scheduler</groupId>
> 		    <artifactId>quartz</artifactId>
> 		    <version>2.3.0</version>
> 		</dependency>
> ```
>
> - 2 ) root-context의 Namespaces에 task를 추가 후 task-annotation-driven을 추가
>
> ```xml
> <!-- root-context.xml -->
> 	<task:annotation-driven/>
> ```
>
> - 3 ) task를 지정할 패키지 및 파일 생성 \_\_ @See : [FileCheckTask.java]("https://github.com/edel1212/springStudy/blob/main/ex03/src/main/java/org/zerock/task/FileCheckTask.java")
>
> 🎈주의사항
>
> @Component를 사용해서 bean에 등록되게 끔 해주자
>
> ```java
> //Java
> @Log4j
> @Component
> public class FileCheckTask {
>
> 	@Autowired
> 	private BoardService boardService;
>
> 	private String getFolderYesterDay() {
> 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
>
> 		Calendar cal = Calendar.getInstance();
>
> 		cal.add(Calendar.DATE, -1);
>
> 		String str = sdf.format(cal.getTime());
>
> 		return str.replace("-", File.separator);
>
> 	}
>
> 	/**
> 	 * @Description : corn은 원래 유닉스 계열에서 사용되는 스케줄러 프로그램 이름이지만
> 	 *                워낙 다른곳에서 많이 사용되다보니 각종 언어나 기술에 맞는 라이브러리
> 	 *                형태로 많이 사용된다
> 	 *
> 	 *                <순서대로 의미>
> 	 *                첫번째 : 초(0~59)
> 	 *                두번째 : 분(0~59)
> 	 *                세번째 : 시간(0~23)
> 	 *                네번째 : 월(1~31)
> 	 *                다섯번째 : 일(1~12)
> 	 *                여섯번째 : 주(1~7)
> 	 *                일골번째 : 연도(설정에따라 다름)
> 	 *
> 	 *                <문자의미>
> 	 *                * : 모든수
> 	 *                ? : 제외
> 	 *                - : 기간
> 	 *                , : 특정시간
> 	 *                / : 시작 시간과 반복시간
> 	 *                L : 마지막
> 	 *                W : 가까운 평일
> 	 *
> 	 * **/
> 	@Scheduled(cron = "0 * * * * *")
> 	public void checkFiles()throws Exception {
>
> 		log.warn("File Check Task run ....................");
> 		log.warn(new Date());
>
> 		//get Old FileDate
> 		List<BoardAttachVO> oldfileList = boardService.getOldFiles();
>
> 		/**
> 		 * @Description :  Paths.get(" C:\\upload"  저기 저 앞에 C앞에 공백 때문에 에러남
> 		 *                 한시간 삽질함 .. 잊지말자 공백 ,... 꼭 확인하자
> 		 *
> 		 *                 * 에러 내용 : java.nio.file.InvalidPathException: Illegal char <:>
> 	     */
> 		//DB에서 받아온 데이터를 기준으로 파일 경로+파일명을 만듬
> 		List<Path> fileListPahts = oldfileList.stream()
> 											  .map( vo -> Paths.get("C:\\upload"
> 													  			   , vo.getUploadPath() + "\\"
> 													  			   + vo.getUuid() + "_" + vo.getFileName() ))
> 											  .collect(Collectors.toList());
> 		// 이미지파일 경우 썸네일도 추출
> 		oldfileList.stream()
> 				   .filter((vo)->vo.isFileType())
> 				   .map((vo)->Paths.get("C:\\upload"
> 			  			   , vo.getUploadPath() + "\\s_"
> 			  			   + vo.getUuid() + "_" + vo.getFileName() ))
> 				   .forEach(p->fileListPahts.add(p));
>
> 		log.warn("=======================================");
>
> 		fileListPahts.forEach(p->log.warn("Base File List Up ::: " + p));
>
> 		//files in yesterday directory
> 		File targetDir = Paths.get("C:\\Upload",getFolderYesterDay()).toFile();
>
> 		File[] removeFiles = targetDir.listFiles( (file) ->
> 													fileListPahts.contains(file.toPath()) == false
> 												);
>
> 		log.warn("--------------------- 최종 삭제 목록 -----------------------");
>
> 		Arrays.asList(removeFiles).forEach(data -> {
> 				log.warn("Delete Success List ::: " + data);
> 				data.delete();
> 			});
>
> 	}
>
> }
> ```
>
> - 4 ) task를 돌릴 파일을 root-context에서 context-scan 시켜준다
>
> ```xml
> <!-- root-context -->
> 	<!-- 해당 class는 컴포넌트 어노테이션을 꼭 달아주자! -->
> 	<context:component-scan base-package="target!">
> 	</context:component-scan>
> ```
>
> ---
