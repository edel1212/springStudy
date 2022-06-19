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
	<button id="uploadBtn">Upload</button>
	
	<script>        
		"use strict"
		document.querySelector("#uploadBtn").addEventListener("click",(e)=>{
			/** 데이터를 담을 객체 */
			let formData = new FormData();
			/** target Input */
			let inputFile = document.querySelector("input[name='fuploadFile']");
			/** 배열 형태로 데이터를 나열 */
			let files = inputFile.files;
			console.log("files",files);
			
			//add FileData to formData
			for(let i of files){
				//FormData 객체에 파일을 주입
				formData.append("uploadFile",i);
			}//for
			console.log("formData",formData);
			
			
			fetch("/uploadAjaxAction",
				{
			      method: "POST",
			      headers: {
			        Accept: "application/json",
			        "Content-Type": false,
			        proceesData : false
			        
			      },
			      data: JSON.stringify({
			        data: formData,
			      }),
			    })
			      .then((response) => response.json())
			      .then((data) => {
						console.log("data",data);
			      })
			      .catch((error) => console.log(error))
			
		});
	</script>
</body>
</html>