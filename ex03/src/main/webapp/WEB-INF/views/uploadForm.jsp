<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<!-- ================================================================== -->
	<!-- 신경써야 할부분은 from 부분의 enctype="multipart/form-data"로 지정하는것이다  -->
	<!-- input 부분에는 multiple 로 하나의 input으로 여러가지 파일을 받을 수 있게끔 함    -->
	<!-- ================================================================== -->
	<form action="uploadFormAction" method="POST" enctype="multipart/form-data">
		<input type="file" name="uploadFile" multiple>
		<button>Submit</button>
	</form>

</body>
</html>