package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
	/**
	 * 일반 POST방식
	 * */
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
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
	
	/**
	 * @Desription :  비동기 통신 방식
	 * 
	 * @See 		: uploadAjax.jsp
	 * */
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
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
}
