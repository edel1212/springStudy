<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>/sample/admin page</h1>
	
	<p>principal : <sec:authentication property="principal"/> </p>
	<p>memberVO  : <sec:authentication property="principal.member"/> </p>
	<p>������̸�   : <sec:authentication property="principal.member.userName"/> </p>
	<p>�����ID   : <sec:authentication property="principal.member.userid"/> </p>
	<p>�����pw   : <sec:authentication property="principal.member.userpw"/> </p>
	<p>����� ���� ���   : <sec:authentication property="principal.member.authList"/> </p>
	
	<a href="/customLogout">logoutPage</a>
</body>
</html>