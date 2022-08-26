<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h1>Hi Im asyncTestJsp</h1>
	
	<p id="writeTxt"></p>
	
	<script>
		fetch("asyncTest", {
		    method: 'POST', // *GET, POST, PUT, DELETE ��
		    mode: 'cors', // no-cors, *cors, same-origin
		    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
		    credentials: 'same-origin', // include, *same-origin, omit
		    headers: {
		    	"Accept": "application/json",// ��û���� ������ Ÿ��
		    	"Content-Type": "application/json" //���� ������ �Ķ���� ����
		      // 'Content-Type': 'application/x-www-form-urlencoded',
		    },
		    //redirect: 'follow', // manual, *follow, error
		    referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
		    //body: JSON.stringify(data), // body�� ������ ������ �ݵ�� "Content-Type" ����� ��ġ�ؾ� ��
		  })
			.then((status) => {
				/**
				* @Description : fetch�� ������ catch �п��� error�� ��Ȯ�ϰ�
				* 				 ǥ���� �����شٴ� ���̴� ���� status�� ����ؼ� ���� �ڵ带 Ȯ���ϴ� ������� ó��
				*/
				console.log(status);
				return status;
			})
			.then((response) => response.json())
		  	.then((data) => {
		  		console.log(data);
		  		//document.querySelector("#writeTxt").innerHtml(data)
		  		})
		  	.catch((err)=> console.log(err));
	</script>
	
</body>
</html>