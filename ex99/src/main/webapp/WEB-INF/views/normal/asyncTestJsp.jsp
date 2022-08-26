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
		    method: 'POST', // *GET, POST, PUT, DELETE 등
		    mode: 'cors', // no-cors, *cors, same-origin
		    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
		    credentials: 'same-origin', // include, *same-origin, omit
		    headers: {
		    	"Accept": "application/json",// 요청받은 데이터 타입
		    	"Content-Type": "application/json" //내가 보내는 파라미터 형식
		      // 'Content-Type': 'application/x-www-form-urlencoded',
		    },
		    //redirect: 'follow', // manual, *follow, error
		    referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
		    //body: JSON.stringify(data), // body의 데이터 유형은 반드시 "Content-Type" 헤더와 일치해야 함
		  })
			.then((status) => {
				/**
				* @Description : fetch의 단점은 catch 분에서 error를 명확하게
				* 				 표현울 못해준다는 점이다 따라서 status를 사용해서 에러 코드를 확인하는 방법으로 처리
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