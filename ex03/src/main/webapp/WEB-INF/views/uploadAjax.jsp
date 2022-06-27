<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	
	<div class="uploadDiv">
		<input type="file" name="fuploadFile" multiple >
	</div>
	<button id="uploadBtn">uploadAjaxAction</button>
	<button id="uploadNewBtn">uploadNewAction</button>
	
	<div class="uploadResult">
		<ul>
			<!-- script -->
		</ul>
	</div>
	
	<script>        
		"use strict"
		
		//확장자 정규식
		const regex = new RegExp("(.*?)\.(exe|sh\zip|alz)$");
		//5MB
		const maxSize = 524880;
		
		//허용 검사		
		const checkExtension = (fileName, fileSize)=>{
			if(fileSize >= maxSize){
				alert("파일사이즈 초과");
				return false;
			}
			if(regex.test(fileName)){
				alert("해당 종류의 파일은 업로드할 수 없습니다.");
				return false;
			}
			return true;
		} 
		
		/******************/
		/*** DTO 적용  전 **/
		/*****************/
		document.querySelector("#uploadBtn").addEventListener("click",(e)=>{
			/** 데이터를 담을 객체 */
			let formData = new FormData();
			/** target Input */
			let inputFile = document.querySelector("input[name='fuploadFile']");
			/** 배열 형태로 데이터를 나열 */
			let files = inputFile.files;
			
			for(let i of files){
				//검사
				if(!checkExtension(i.name, i.size)) return;
				//FormData 객체에 파일을 주입
				formData.append("uploadFile",i);
			}//for
			
			/** @Desciption :
				1) formData는 console.log로 보이지 않음 보안 정책 때문임!!
				   - console.log("formData",formData);
		
				2) formData를 보낼때, header 부분은 브라우저가 자동으로 설정해주기 때문에
				   Content-Type을 application/x-www-form-urlencoded ..등 따로 지정할 필요가 없습니다.

			*/
			for (let key of formData.keys()) {
			  console.log(key);
			}

			for (let value of formData.values()) {
			  console.log(value);
			}
			
			fetch("/uploadAjaxAction",
				{
			      method: "POST",
			      /* no-cache 값은 대부분의 브라우저에서 max-age=0 과 동일한 뜻을 가집니다. 즉, 캐시는 저장하지만 사용하려고 할 때마다 서버에 재검증 요청을 보내야 합니다.  */
			      cache: 'no-cache',
			      body: formData 
			    })
			      .then((response) => response.json())
			      .then((data) => {
						console.log("data",data);
			      })
			      .catch((error) => console.log(error))
		});
		
		
		/************************************************************************************/
		/************************************************************************************/
		
		
		/******************/
		/*** DTO 적용  후 **/
		/*****************/
		document.querySelector("#uploadNewBtn").addEventListener("click",(e)=>{
			/** 데이터를 담을 객체 */
			let formData = new FormData();
			/** target Input */
			let inputFile = document.querySelector("input[name='fuploadFile']");
			/** 배열 형태로 데이터를 나열 */
			let files = inputFile.files;
			
			for(let i of files){
				//검사
				if(!checkExtension(i.name, i.size)) return;
				//FormData 객체에 파일을 주입
				formData.append("uploadFile",i);
			}//for
			
			for (let key of formData.keys()) {
			  console.log(key);
			}

			for (let value of formData.values()) {
			  console.log(value);
			}
			
			fetch("/uploadNewAction",
				{
			      method: "POST",
			      /* no-cache 값은 대부분의 브라우저에서 max-age=0 과 동일한 뜻을 가집니다. 즉, 캐시는 저장하지만 사용하려고 할 때마다 서버에 재검증 요청을 보내야 합니다.  */
			      cache: 'no-cache',
			      body: formData 
			    })
			      .then((response) => response.json())
			      .then((data) => {
						console.log("data",data);
						//file input 초기화
						document.querySelector("input[name='fuploadFile']").value = null;
						
						//파일 목록 생성
						const uploadRstUL = document.querySelector(".uploadResult ul");
						let str = "";
						data.forEach((obj)=>{
						    str += "<li>"+obj['fileName']+"</li>"
						})
						uploadRstUL.insertAdjacentHTML("beforeEnd",str );
			      })
			      .catch((error) => console.log(error));
		});
		
		
	</script>
</body>
</html>