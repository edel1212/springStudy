<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<style type="text/css">
		#wrap >div {
			float: left;
		}
	</style>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>


<div id="wrap">
	<div></div>  
	<div></div>
	<div></div>
	<div></div>
</div>

</body>
</html>
