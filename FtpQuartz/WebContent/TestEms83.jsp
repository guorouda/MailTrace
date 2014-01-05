<%@page import="com.ron.weixin.pereference.SystemGlobals, com.ron.ems.Ems83, net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
Ems83 s = new Ems83();
JSONObject jo = s.GetDetail("5066980369701");
%>
<script>
(function showJSON(){ 
//	var user =  
//    {    
//       "username":"andy" ,
//       "age":20 ,
//       "info": { "tel": "123456", "cellphone": "98765"},
//	   "address": 
//	       [ 
//                {"city":"beijing", "postcode":"222333"},
//                {"city":"newyork", "postcode":"555666"}
//	       ] 
//    };
	
//	  alert(user.username); 
//    alert(user.age); 
//	  alert(user.info.cellphone); 
//    alert(user.address[0].city); 
//    alert(user.address[0].postcode); 
    
	var step =<%=jo%>;
	console.debug(step);
	for(var i=0; i < step.rows.length; i++){
		document.write(step.rows[i].time + ":" + step.rows[i].station + ":" + step.rows[i].action + ":" + step.rows[i].comment + "</br>");
	}
//	alert(step.rows[1].time);
//	alert(step.rows[1].station);
//	alert(step.rows[1].action);
//	alert(step.rows[1].comment);
})(); 
</script>
</body>
</html>