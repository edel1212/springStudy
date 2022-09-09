<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>Custom Login Page</h1>
	<h2><c:out value="${error}"/></h2>
	<h2><c:out value="${lgout}"/></h2>
	
	<!-- ************************************* 
		 여기에서 form에 post로 login을 받는
	     URL을 받는 Controller를 만든적이 없지만
	     이상없이 작동하고 있다 그 이유는
		  기본 Default로 login URL이 만들어져있음 
	*************************************-->
	<form method="post" action="loginReq">
		<input type="text" name="username" value="admin" >
		<input type="password" name="password" value="admin" >
		<input type="submit" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" >
		<input type="checkbox" name="remember-me"> Remember Me
	</form>
	
	<button id="fetchBtn">Fetch Login Btn</button>
	<script>	
		document.querySelector("#fetchBtn").addEventListener("click",(e)=>{
			fetch("/loginReq",{
			      method: "POST",
			      headers: {
			        Accept: "application/json",
			        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
			        "X-CSRF-TOKEN": "${_csrf.token}",
			      },
			      body: new URLSearchParams({
			    	  "username" : "admin90",
			    	  "password" : "pw90" 
			      })
			}).then((response) => {
				/**
				* @Description : 성공 시 받아온 값은 response.json()가 불가능하다!
				                 로그인 방식은 비동기 처리보다는 form 처리 방식이
				                 더 간단하고 처리도 확실해보인다! 
				**/
				console.log(response);
				})
      		.catch((error) => console.log(error));
			
		});//click
			
	</script>
	
	
</body>
</html>