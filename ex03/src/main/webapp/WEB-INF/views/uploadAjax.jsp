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
		
		//Ȯ���� ���Խ�
		const regex = new RegExp("(.*?)\.(exe|sh\zip|alz)$");
		//5MB
		const maxSize = 524880;
		
		//��� �˻�		
		const checkExtension = (fileName, fileSize)=>{
			if(fileSize >= maxSize){
				alert("���ϻ����� �ʰ�");
				return false;
			}
			if(regex.test(fileName)){
				alert("�ش� ������ ������ ���ε��� �� �����ϴ�.");
				return false;
			}
			return true;
		} 
		
		/******************/
		/*** DTO ����  �� **/
		/*****************/
		document.querySelector("#uploadBtn").addEventListener("click",(e)=>{
			/** �����͸� ���� ��ü */
			let formData = new FormData();
			/** target Input */
			let inputFile = document.querySelector("input[name='fuploadFile']");
			/** �迭 ���·� �����͸� ���� */
			let files = inputFile.files;
			
			for(let i of files){
				//�˻�
				if(!checkExtension(i.name, i.size)) return;
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
		
		
		/************************************************************************************/
		/************************************************************************************/
		
		
		/******************/
		/*** DTO ����  �� **/
		/*****************/
		document.querySelector("#uploadNewBtn").addEventListener("click",(e)=>{
			/** �����͸� ���� ��ü */
			let formData = new FormData();
			/** target Input */
			let inputFile = document.querySelector("input[name='fuploadFile']");
			/** �迭 ���·� �����͸� ���� */
			let files = inputFile.files;
			
			for(let i of files){
				//�˻�
				if(!checkExtension(i.name, i.size)) return;
				//FormData ��ü�� ������ ����
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
			      /* no-cache ���� ��κ��� ���������� max-age=0 �� ������ ���� �����ϴ�. ��, ĳ�ô� ���������� ����Ϸ��� �� ������ ������ ����� ��û�� ������ �մϴ�.  */
			      cache: 'no-cache',
			      body: formData 
			    })
			      .then((response) => response.json())
			      .then((data) => {
						console.log("data",data);
						//file input �ʱ�ȭ
						document.querySelector("input[name='fuploadFile']").value = null;
						
						//���� ��� ����
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