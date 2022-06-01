<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<button id="" onClick="onClickEvent()" >Send</button>
	<button id="" onClick="onClickEvent2()" >EntitySend</button>
</body>
<script>
	"use strict"
	const path = "/" + window.location.pathname.split("/")[1];
	
	function onClickEvent(){
		callBackFunc();
	}
	function onClickEvent2(){
		callBackFunc2();
	}

	/**
	*@description : RestController인 FetchController에 요청함
	**/
	function callBackFunc(){
		fetch(path+"/fetch/getList", {
			  method: "POST",
			  headers: {
			    "Accept": "application/json",
			  }
			}).then((response)=>(response.json()))
			  .then((data)=>console.log(data))
			  .catch(error=>console.log(error))
	}
	
	function callBackFunc2(){
		fetch(path+"/fetch/getListEntityVer", {
			  method: "POST",
			  headers: {
			    "Accept": "application/json",
			  }
			}).then((response)=>(response.json()))
			  .then((data)=>console.log(data))
			  .catch(error=>console.log(error))
	}

	/**
	*@description : RestController가 가닌 @ResponseBoady에 요청함
	*/
 	fetch(path+"/test/restCallTest", {
		  method: "POST",
		  headers: {
		    "Accept": "application/json",
		    "Content-Type": "application/json"
		  }
		}).then((response)=>(response.json()))
		  .then((data)=>console.log(data))
		  .catch(error=>console.log(error)) 
		
	/**
	*@description : body를 사용해서 parameter를 전달함
	*
	*@See         : 1) body에서 데이터를 전달 시 에  JSON.stringify({})를 사용하지
	*                  않고 넘겨주면 from Array value (token `JsonToken.START_ARRAY`) 에러가 난다!!
    *				   ✔요청 전문을 JSON 포멧으로 직렬화화여 가장 중요한 body 옵션에 설정해 줘야한다
	*                
	*               2) 요청을 받은 Controller에서도 파라미터 앞에 @RequestBody를 사용해주자!   
	*/
 	fetch(path+"/fetch/getBoardEntityVer", {
		  method: "POST",
		  headers: {
		    "Accept": "application/json",
		    "Content-Type": "application/json"
		  },
		  body : JSON.stringify({
			  "bno" : 6
		  })
		}).then((response)=>(response.json()))
		  .then((data)=>console.log(data))
		  .catch(error=>console.log(error))	 
		
		
</script>
</html>