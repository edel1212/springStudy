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
	<!-- �Ű��� �Һκ��� from �κ��� enctype="multipart/form-data"�� �����ϴ°��̴�  -->
	<!-- input �κп��� multiple �� �ϳ��� input���� �������� ������ ���� �� �ְԲ� ��    -->
	<!-- ================================================================== -->
	<form action="uploadFormAction" method="POST" enctype="multipart/form-data">
		<input type="file" name="uploadFile" multiple>
		<button>Submit</button>
	</form>

</body>
</html>