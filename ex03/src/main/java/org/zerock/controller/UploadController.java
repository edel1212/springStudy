package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
	/**
	 * @Description : 일반 POST방식
	 * 
	 * @See 		: uploadForm.jsp
	 * 
	 *                문제) ❌해당 URL을 사용해서 uploadFormAction로 POST 파일 업로드시
	 *					   한글이 깨진다.  
	 *				
	 *				  이유) 클라이언트에서 euc-kr로 인코딩 되어 utf-8 인코딩 셋을 
	 *					   사용하는 서버로 전송되는 과정에서 한글이 깨지는 것이다.
	 *		
	 *
	 *  			  해결방법) accept-charset="UTF-8"를 form에 추가해준다.
	 *  					  폼 데이터를 서버로 전송할 때 사용되는 문자 인코딩(character encoding) 방식을 명시하는 것이다.
	 *                        - ex) <from ... enctype="multipart/form-data"  accept-charset="UTF-8">
	 *                 
 	 * */
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}
	
	/**
	 * @Desription :  비동기 통신 방식
	 * 
	 * @See 		: uploadAjax.jsp
	 * 
	 *                ✔ 해당 URL을 사용해서 uploadFormAction로 POST 파일 업로드시
	 *					 한글이 깨지지 않는다!!
	 * */
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	
	/**
	 * @Description : 1) 파일처리는 스프리에서 제공하는 MultipartFile(interface)를 사용
	 *                   화면에서 넘기는 여러가지 파일은 배열로 받아서 처리가 간능함
	 *                
	 *                2) MultipartFile이 가지고 있는 Method
	 *                  - getName() 			:: 파라미터의 이름 input Tag의 이름
	 *                  - getOriginalFileName 	:: 업로드 되는 파일 이름
	 *                  - isEmpty() 			:: 파일이 존재 유/무
	 *                  - getSize() 			:: 업로드되는 파일의 크기
	 *                  - getBytes() 			:: byte[]로 파일 데이터 변환
	 *                  - transferTo(File file) :: 파일을 저장함
	 *                  
	 *@See			: uploadFormAction.jsp                  
	 * */
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		for(MultipartFile multipartFile : uploadFile) {
			
			//저장될 file Folder 경로
			String uploadFolder = "C:\\upload" ;
			
			log.info("--------------------------------------------");
			log.info("Upload File Name :::: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size ::::"  + multipartFile.getSize());
			
			//(경로 , 파일 이름 그대로 확장자도 같이 넘어감!)
			File saveFile = new File(uploadFolder,multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			
		}
	}
	
	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("update ajax post ......");
		
		String uploadFolder = "C:\\upload";
		
		//make folder
		File uploadPath = new File(uploadFolder,getFolder());/** ( 부모 파일경로 , 하위 경로 )  */
		log.info("upload path :   " + uploadPath);
		//해당경로의 directory가 없을 경우
		if(uploadPath.exists() == false) {
			/**
			 * @See : mkdir은 상위 디렉터리가 없는 경우 생성하지 못하지만 mkdirs는 상위 디렉터리가 없으면 생성한다.
			 *  
			 *        ex)   File file = new File("D:\\테스트\\1\\2\\3");
							if(file.mkdir()){
							    System.out.println("디렉터리 생성 성공");
							} else{
							    System.out.println("디렉터리 생성 실패");
							}
							
							---------------------------------------------
							
							File file = new File("D:\\테스트\\1\\2\\3");
							if(file.mkdirs()){
							    System.out.println("디렉터리 생성 성공");
							} else{
							    System.out.println("디렉터리 생성 실패");
							}
			 * */
			//해당 경로 이름의 폴더 생성
			uploadPath.mkdirs(); //yyyy/MM/dd folder
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("--------------------------------------------");
			log.info("Upload File Name :::: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size ::::"  + multipartFile.getSize());
			
			// FileName
			String uploadFileName =  multipartFile.getOriginalFilename();

			//파일 이름 중복을 처리하기 위한 UUID 생성
			UUID uuid = UUID.randomUUID();
			//생성한 UUID 와 기존 파일이름을 합침 _를 구분자로 이용
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			//Create File Object
			//File saveFile = new File(uploadFolder,uploadFileName); <<이전 방법 바로 부모 Path에 dir생성 및 거기에 파일 추가
			File saveFile = new File(uploadPath,uploadFileName); 
			try {
				// saveFile 객체에는 내가만든 path와 파일Name이 들어있음
				multipartFile.transferTo(saveFile);
				
				//Image파일경우 썸네일 생성
				if(checkImageType(saveFile)) {
					//FileOutputStream 을사용해서 받아온 이미지를 만들고
					FileOutputStream thumbnail = new FileOutputStream(
							new File(uploadPath,"s_"+uploadFileName) );
					
					
					/**
					 * @Parameters : is The InputStream from which to obtainimage data.
					 *               os The OutputStream to send thumbnail data to.
					 *               width The width of the thumbnail.
					 *               height The height of the thumbnail.
					 *               
					 * @See        :  maven에 주입한 Thumbnail을 이용해서 섬테일을 생성
					 *				  그만들어진 이미지를 100x100으로 다시만들음(이름이 같으므로 앞에것은지워짐! )              
					 * */
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(),thumbnail,100,100);
					thumbnail.close();
				}
				
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}//for
		
	}
	
	/**
	 * @Description : 파일의 정보를 가지고있는 AttachFileDTO를 사용한 방법
	 * */
	@PostMapping(value = "/uploadNewAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadNewVerEntity(MultipartFile[] uploadFile){
		
		List<AttachFileDTO>  list = new ArrayList<AttachFileDTO>();
		String uploadFolder = "C:\\upload";
		
		String uploadFolderPath = getFolder();
		
		//Make Folder - - - - - -
		File uploadPath = new File(uploadFolder,uploadFolderPath);
		
		if(uploadPath.exists()  == false) { // 해당 디렉토리가 없을경우
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			attachDTO.setFileName(uploadFileName);
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			try {
				File saveFile = new File(uploadPath,uploadFileName); 
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				if(checkImageType(saveFile)) {
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
				
				list.add(attachDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**@See : HttpStatus.? 를 BadGateway로 하여도 return 및 파일은 만들어짐 다만 console에 해당 상태에 맞는 에러 로그는 뜸*/
		return new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);
	}
	
	/**
	 * @Description : 오늘 날짜에 맞는 문자열 생성
	 * */
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar today = Calendar.getInstance();
		String str = sdf.format(today.getTime());
		//File.separator "-" 를 파일 구문자로 바꾸는 코드
		return str.replace("-", File.separator);
	}
	
	/**
	 * @Description : 업로든 되는 파일이 image인지 체크
	 * */
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			log.info("contentType ::: " + contentType);
			return contentType.startsWith("image");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @Descirption : @Params으로 받은 이름을 기준으로
	 *                파일의 이미지를 가져오는 메서드
	 *                
	 *                <byte[]>로 이미지 파일의 데이터를 전송할 떄 신경 써야 
	 *                할것은 브라우저에 보여줘야하는 MINE 타입이 파일의 종류에 따라
	 *                달라지므로 header에 add를 해줘서 처리!
	 *                
	 * @See         : header에 MINE 타입을 정해주지 않으면 이미지도 깨진 글씨로 나옴!               
	 *                
	 * */
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("fileName :: " + fileName);
		
		File file = new File("C:\\upload\\"+fileName);
		
		log.info("file :: " + file);
	
		ResponseEntity<byte[]> result = null;
		
		try {
			//Content-Type 을 담아줄 Header 객체
			HttpHeaders header = new HttpHeaders();
			
			/** @Description : 해당파일의 확장자로 MINE 타입을 반환합니다 */
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			log.info("header ::: " + header); //header ::: [Content-Type:"image/png"]
			
			/**
			 * @Description : FileCopyUtils.copyToByteArray() 란?
			 * 				  지정한 Reader의 내용들을 String에 copy한다 완료되면 Reader를 닫는다.
			 * 				  리턴값은 카피된 String을 리턴한다(빈값이 들어올수있다.)
			 * */
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/***
	 * @Description : 첨부파일 다운로드는 서버에서 MIME 타입을 다운로드로 지정하고, 적절한 
	 *                헤더 메세지를 통해서 다운로드 이름을 지정해서 처리하게 해야하낟.
	 *                
	 *                🎉여서 중요한건 이미지와 달리 다운로드는 MIME타입이 고정되어어서
	 *                produces= MediaType.APPLICATION_OCTET_STREAM_VALUE
	 *                (application/octet-stream) 로 지정!  
	 *                
	 *                ResponseEntity<byte[]>를 사용해서 구현할 수 있지만
	 *                아래의 예제는 <Resource>를 이용해서 좀 더 간단하게 처리
	 *                
	 * */
	@GetMapping(value="/testdownload", produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> testDownloadFile(String fileName){
		log.info("download FileName ::: " + fileName);
		
		Resource resource = new FileSystemResource("c:\\upload\\"+fileName);
		
		log.info("resource ::: " + resource);
		
		/**
		 * @See : 해당 경로로 요청 시 브라우저 상에는 아무런 반응이 없지만 서버에는 로그가 찍힘!
		 *        INFO : org.zerock.controller.UploadController - download FileName ::: /2022/06/27/file.png
		 *		  INFO : org.zerock.controller.UploadController - resource ::: file [c:\\upload\2022\06\27\file.png]
		 *	
		 * **/
		
		return null;
	}
	
	
	/**
	 * @Desription : 1) MINE 타입은 다운로드를 할수있는 'application/octet-steam'으로 지정
	 * 
	 *               2) 다운로드시 저장 되는 이름은 'Content-Disposition'을 이용하여 지정
	 *                  - 사용 이유는 파일 이름에 대한 문자열 처리는 파일 이름이 한글인 경우 지정할 떄 깨지는 문제를 막기 위해서임 
	 *               
	 *               3) @RequestHeader("User-Agent") String userAgent를 이용하여 브라우저의 정류나 모바일인지 데스크탑인지
	 *                  브라우저 프로그램 종류등을 알수 있다.
	 * **/
	@GetMapping(value = "/download" , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent // 디바이스의 정보를 할수있 정보
												,String fileName){ //파일명 기간+파일명
		
		log.info("fileName :::" + fileName);//fileName :::/2022/06/27/file.png
		
		log.info("userAgent ::: " + userAgent);
		/**userAgent ::: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) 
		* Chrome/103.0.0.0 Safari/537.36
		*/
		
		//지정경로의 File을 가져와 객체로만듬
		Resource resource = new FileSystemResource("c://upload//"+fileName);
	
		log.info("resource ::: " + resource) ;// resource ::: file [c:\\upload\2022\06\27\file.png]
		
		if(resource.exists() == false) { //resource가 존재하지 않을 경우!
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		/***
		 * @Description : 파일 다운로드시 UUID 명 삭제
		 * 
		 * @See         : 여기서 중요한건 파일 다운로드시 파일명은 HttpHeaders에서 정해진다는
		 *                것이다!!!!!
		 * **/
		String resoucreName = resource.getFilename();
		
		/**
		 * 등등 브라우저를 나눠서 처리도 가능함!  
		 * */
		if(userAgent.contains("Edge")) {
			log.info("Edge!!!");
		}
		String resourceOriginalName =	resoucreName.substring(resoucreName.lastIndexOf("_")+1);
		
		log.info("resoucreName::: " + resoucreName);//resoucreName::: file.png
		log.info("resourceOriginalName::: " + resourceOriginalName);//resoucreName::: file.png
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			headers.add(
							  "Content-Disposition"
//							, "attachment ; filename = " + new String(resoucreName.getBytes("UTF-8")
							, "attachment ; filename = " + new String(resourceOriginalName.getBytes("UTF-8")
							, "ISO-8859-1")
						);
			log.info("headers ::: " + headers);//headers ::: [Content-Disposition:"attachment ; filename = file.png"]
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
		
	}
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(@RequestBody String req) throws Exception{
		/**
		 * @Description : 문자열로 받은 req를 gson(json-simple 를 pom에 추가)을
		 *  			  사용해서 JSON 형식으로 파싱하여 사용! jackson이 메이븐에 추가 
		 *  			  되어있다면 VO가 있다면 자동으로 파싱해줌!
		 * **/
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(req);	
		log.info("jsonObject  ::: "  + jsonObject);
		
		log.info("fileName  ::: " + jsonObject.get("fileName"));
		String fileName = (String) jsonObject.get("fileName");
		
		log.info("type  ::: " +jsonObject.get("type"));
		String type = (String) jsonObject.get("type");
		
		File file;
		
		try {
			file = new File("C:\\upload\\"+ URLDecoder.decode(fileName,"UTF-8"));
			file.delete(); //파일 삭제
			
			if("image".equals(type)) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				file = new File(largeFileName);
				file.delete();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("delete",HttpStatus.OK);
	}

	
	
}
