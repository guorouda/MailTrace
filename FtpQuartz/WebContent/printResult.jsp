<%@page import="com.ron.weixin.pereference.SystemGlobals, com.ron.ems.Ems83, net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
if(request.getParameter("mailnum") == null){
	out.print("no mailnum");
	return;
}
String mailnum = request.getParameter("mailnum");
Ems83 s = new Ems83();
//JSONObject jo = s.GetDetail("5066980369701");
JSONObject jo = s.GetDetail(mailnum);
%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>邮件详细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
-->
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/> 
	
<link rel="stylesheet"  href="css/jquery.mobile-1.3.2.min.css">
<script src="js/jquery.js"></script>
<script src="js/jquery.mobile-1.3.2.min.js"></script>
	
<link rel="stylesheet"  href="css/index.css">
<script src="js/index.js"></script>
<style type="text/css">
.status{ 
    background-image: url(images/sta2.png);
    background-repeat:no-repeat;
    background-position:center;
    width:50px;
    height:50px;
}

.status1{ 
    background-image: url(images/state1.png);
    background-repeat:no-repeat;
    background-position:center;
    width:50px;
    height:50px;
}

.status2{ 
    background-image: url(images/state2.png);
    background-repeat:no-repeat;
    background-position:center;
    width:50px;
    height:50px;
}

.status3{ 
    background-image: url(images/state3.png);
    background-repeat:no-repeat;
    background-position:center;
    width:50px;
    height:50px;
}

.status4{ 
    background-image: url(images/state4.png);
    background-repeat:no-repeat;
    background-position:center;
    width:50px;
    height:50px;
}

.orderTable{
    border:1px solid #BBBBBB;
    margin-top:1px;
    font-size:14px;
}

.orderTable tr td{
    height:32px;
    padding-left:8px; 
}

.orderTable .title{
    background-color:#D5D5D5;
    color:#FE9D10;
    font-size:14px;
    font-weight:normal;  
    text-shadow:none;
}

.orderTable .content{
    background-color:#FCFBFB;
    font-size:14px;
    height:28px;
    font-weight:normal;  
    padding-left:12px; 
}

.orderListTable{
    border:1px solid #BBBBBB;
    font-size:14px;
}

.orderListTable tr th{
    height:35px;

}

.orderListTable tr .td0{
    /**border-bottom:1px solid #D6D6D6;*/
    background-color:#F4F4F4;
}

.orderListTable tr .td1{
    /**border-bottom:1px solid #D6D6D6;*/
    background-color:#ffffff;
}

.orderListTable tr .td20{
    /**border-bottom:1px solid #D6D6D6;*/
   	color: #ffa40d;
    background-color:#F4F4F4;
}

.orderListTable tr .td21{
    /**border-bottom:1px solid #D6D6D6;*/
   	color: #ffa40d;
    background-color:#ffffff;
}
</style>

<script type="text/javascript">
   function goBack(){       
      var url="";
      window.location.href=url;
   }
</script>
</head>
<body>
    <table class="pageTitle">
       <tr>
       <td class="logo">&nbsp;</td>
       <td class="title">邮件详情</td>
       <td class="btn">
 		<!--          <a href="#" onclick="goBack();" data-role="button"  data-mini="true"   data-inline="true" data-icon="back"  data-theme="b" >返回</a> -->
       </td>
       </tr>
    </table>
    <div id="myCollapsibleSet" data-role="collapsible-set" data-content-theme="c" data-corners="false" style="margin:0px;">
    
    <div data-role="collapsible" data-theme="b" data-content-theme="b" data-collapsed="false" >
	<div data-role="collapsible" data-theme="b" data-content-theme="b" data-collapsed="false" >
	<h3>邮件：<%=mailnum%></h3><p>
	<table width="100%" border="0" cellspacing="0" cellspacing="0" class="orderListTable"><tr class="ui-bar-b"><th width="40px">时间</th><th width="40px">状态</th><th >操作</th></tr>
	<script>
	(function showJSON(){ 
		var step =<%=jo%>;
		console.debug(step);
		for(var i=1; i < step.rows.length; i++){
			var temp;
			if(i%2 == 1){
				temp = '<td class="status2" width="50px" style="background-color:#ffffff;"></td>';
			}else{
				temp = '<td class="status2" width="50px" style="background-color:#f4f4f4;"></td>';
			}
			document.write('<tr height="50px;">' + '<td class="td' + i%2 + '" align="left" style="padding-left:15px;">' + step.rows[i].time  +  '</td>' + temp + '<td class="td' + i%2 + '" align="left" style="padding-left:15px;">' + step.rows[i].action + '->' +  step.rows[i].comment + '</br>' +  step.rows[i].station  +  '</td>'  +  '</tr>');
		}
	})(); 
</script>	
</table>
</p>
</div>
</div>

</body>
</html>