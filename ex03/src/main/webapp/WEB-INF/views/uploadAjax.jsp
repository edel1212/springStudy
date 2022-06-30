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
							if(!obj['image']){
								str += "<li style='display:flex'><img style='width:25px;margin-right:5px;' src='/resources/img/file.png'>"+obj['fileName']+"</li>";
							} else { 
								/**
								 * @See : encodeURIComponent()�� ��������� ������ 
								          "��û Ÿ�ٿ��� ��ȿ���� ���� ���ڰ� �߰ߵǾ����ϴ�. ��ȿ�� ���ڵ��� RFC 7230�� RFC 3986�� ���ǵǾ� �ֽ��ϴ�."
								          ��� ��������!
								*/
								const fileCallPath =  encodeURIComponent( obj['uploadPath'] + "/" +"s_"+obj['uuid']+"_"+obj['fileName']);
								
							    str += "<li style='display:flex'>";
							   	<!-- ��ο� �������� / �տ� ������ .. ����η� �ؾ��� .. -->
							    str +=  "<img style='width:25px;margin-right:5px;' src='/display?fileName="+fileCallPath+"'>";
							    str +=  obj['fileName'];
							    str +=  "</li>";
							}
						})
						uploadRstUL.insertAdjacentHTML("beforeEnd",str );
			      })
			      .catch((error) => console.log(error));
		});
		
	</script>
</body>
</html>