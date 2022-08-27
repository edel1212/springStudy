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
	
	
	<script>
	console.log("----------------------------------------------------------");
	console.log("Object type async Controller!");
	console.log("----------------------------------------------------------");
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
				//console.log(status);
				return status;
			})
			.then((response) => response.json())
		  	.then((data) => {
		  		//console.log(data);
		  		//document.querySelector("#writeTxt").innerHtml(data)
		  		})
		  	.catch((err)=> console.log(err));
		
		console.log("----------------------------------------------------------");
		console.log("ResponseEntity type async Controller!");
		console.log("----------------------------------------------------------");
		
		/**
		 *	@Description : ��û�ϴ� URL�� ���� �ٸ��Ƿ� �����θ� ���������Ѵ�! 
		*/
		/* fetch("/imRest/getListTypeResEntity",{
				method: 'POST'
			    , mode: 'cors' 
			    , cache: 'no-cache' 
			    , credentials: 'same-origin'
			    , headers: {
			    	"Accept": "application/json",// ��û���� ������ Ÿ��
			    	"Content-Type": "application/json" //���� ������ �Ķ���� ����
			    }
			    , referrerPolicy: 'no-referrer' 
			  })
			  	.then((status) => {
					console.log(status);
					return status;
				})
				.then((response) => response.json())
			  	.then((data) => console.log(data))
			  	.catch((err)=> console.log(err)); */
		
		console.log("----------------------------------------------------------");
		console.log("ResponseEntity type async Controller! Ver2");
		console.log("----------------------------------------------------------");
		
		fetch("/imRest/getData/1993",{
				method: 'GET'
			    , mode: 'cors' 
			    , cache: 'no-cache' 
			    , credentials: 'same-origin'
			    , headers: {
			    	"Accept": "application/json",// ��û���� ������ Ÿ��
			    	"Content-Type": "application/json" //���� ������ �Ķ���� ����
			    }
			    , referrerPolicy: 'no-referrer' 
			  })
			  	.then((status) => {
					console.log(status);
					return status;
				})
				.then((response) => response.json())
			  	.then((data) => console.log(data))
			  	.catch((err)=> console.log(err));
		
	</script>
	
</body>
</html>