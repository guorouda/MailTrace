package com.ron.weixin.test;

public class testStringFormat {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        String textTpl = "<xml>"+
					"<ToUserName><![CDATA[%1$s]]></ToUserName>"+
					"<FromUserName><![CDATA[%2$s]]></FromUserName>"+
					"<CreateTime>%3$s</CreateTime>"+
					"<MsgType><![CDATA[%4$s]]></MsgType>"+
					"<Content><![CDATA[%5$s]]></Content>"+
					"<FuncFlag>0</FuncFlag>"+
					"</xml>";             
		
      	String resultStr = String.format(textTpl, "fromUsernam1", "toUsername1", "time1", "msgType1", "contentStr1");
      	System.out.println(resultStr);
	}

}
