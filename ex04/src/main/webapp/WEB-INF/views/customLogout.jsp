<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/logout" method="post">
		<input type="text" name="${_csrf.parameterName}" value="${_csrf.token }" />
		<button>로그아웃</button>
	</form>
</body>
</html>