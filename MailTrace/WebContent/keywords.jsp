<%@page import="com.ron.weixin.pereference.SystemGlobals"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>List Keywords</title>
</head>
<body>
<%
out.print(SystemGlobals.listKeywords() + "</br>");
out.print(SystemGlobals.getDefaultsValue("download.path") + "</br>");
out.print(SystemGlobals.getDefaultsValue("file.code") + "</br>");
out.print(SystemGlobals.getDaySwitch("03003", "32") + "</br>");;
SystemGlobals.setDaySwitch("03003", "32", "11");
out.print(SystemGlobals.getDaySwitch("03003", "32") + "</br>");
//SystemGlobals.print();
%>
</body>
</html>