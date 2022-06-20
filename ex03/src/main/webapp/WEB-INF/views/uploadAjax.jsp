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
			/** �����͸� ���� ��ü */
			let formData = new FormData();
			/** target Input */
			let inputFile = document.querySelector("input[name='fuploadFile']");
			/** �迭 ���·� �����͸� ���� */
			let files = inputFile.files;
			console.log("files",files);
			
			for(let i of files){
				//FormData ��ü�� ������ ����
				formData.append("uploadFile",i);
			}//for
			
			/** @Desciption :
				1) formData�� console.log�� ������ ���� ���� ��å ������!!
				   - console.log("formData",formData);
		
				2) formData�� ������, header �κ��� �������� �ڵ����� �������ֱ� ������
				   Content-Type�� application/x-www-form-urlencoded ..�� ���� ������ �ʿ䰡 �����ϴ�.

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
			      /* no-cache ���� ��κ��� ���������� max-age=0 �� ������ ���� �����ϴ�. ��, ĳ�ô� ���������� ����Ϸ��� �� ������ ������ ����� ��û�� ������ �մϴ�.  */
			      cache: 'no-cache',
			      body: formData 
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