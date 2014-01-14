<%@page import="com.ron.weixin.LoginWeixinWEB, java.io.IOException, java.net.URISyntaxException, org.apache.http.client.ClientProtocolException, com.ron.weixin.utility.json.JSONException"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Test WeiXin</title>
</head>
<body>
<%
LoginWeixinWEB lww = new LoginWeixinWEB();
try {
	lww.Login("sunrong@ymail.com", "aaaa1111");
	lww.SendMessage("content", "2386949502");
	//lww.SendMessage("content", "970109862");
} catch (ClientProtocolException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (URISyntaxException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (JSONException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
%>

</body>
</html>