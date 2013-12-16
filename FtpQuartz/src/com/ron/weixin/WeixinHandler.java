package com.ron.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class WeixinHandler {
	private static Logger log = Logger.getLogger(WeixinHandler.class);
	
	private String TOKEN = "Guo";
	private HttpServletRequest request; 
	private HttpServletResponse response;
	
	public WeixinHandler(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}
	
	public void valid(){
		log.debug("Weixin starting...");
		String echostr = request.getParameter("echostr");
		if(null == echostr||echostr.isEmpty()){
			responseMsg();
		}else{
			if(this.checkSignature()){
				this.print(echostr);
			}else{
				this.print("error");                                                                                                                                                                                                                                                                                                                                         
			}
		}
	}
	
	//自动回复内容
	public void responseMsg(){
		String postStr = null;
		try{
			postStr = this.readStreamParameter(request.getInputStream());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		log.debug(postStr);
		if (null != postStr && !postStr.isEmpty()){
			Document document = null;
			try{
				document = DocumentHelper.parseText(postStr);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(null == document){
				this.print("");
				return;
			}
			
			Element root = document.getRootElement();
            String fromUsername = root.elementText("FromUserName");
            String toUsername = root.elementText("ToUserName");
            String keyword = root.elementTextTrim("Content");
            String time = new Date().getTime() + "";
            String textTpl = "<xml>" +
						"<ToUserName><![CDATA[%1$s]]></ToUserName>" +
						"<FromUserName><![CDATA[%2$s]]></FromUserName>" +
						"<CreateTime>%3$s</CreateTime>" +
						"<MsgType><![CDATA[%4$s]]></MsgType>" +
						"<Content><![CDATA[%5$s]]></Content>" +
						"<FuncFlag>0</FuncFlag>" +
						"</xml>";             
			
			if(null != keyword && !keyword.equals(""))
            {
          		String msgType = "text";
            	String contentStr = "Welcome to wechat world!";
            	String resultStr = String.format(textTpl, fromUsername, toUsername, time, msgType, contentStr);
            	this.print(resultStr);
            }else{
            	this.print("Input something...");
            }

	    }else {
	    	this.print("");
	    }
	}
	
	//微信接口验证
	public boolean checkSignature(){
		String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String token = TOKEN;
        String[] tmpArr = {token, timestamp, nonce};
        Arrays.sort(tmpArr);
        String tmpStr = this.ArrayToString(tmpArr);
        tmpStr = this.SHA1Encode(tmpStr);
        if(tmpStr.equalsIgnoreCase(signature)){
			return true;
		}else{
			return false;
		}
	}
	
	//向请求端发送返回数据
	public void print(String content){
		try{
			response.getWriter().print(content);
			response.getWriter().flush();
			response.getWriter().close();
		}catch(Exception e){
			
		}
	}
	
	//数组转字符串
	public String ArrayToString(String [] arr){
		StringBuffer bf = new StringBuffer();
		for(int i = 0; i < arr.length; i++){
		 bf.append(arr[i]);
		}
		return bf.toString();
	}
	
	//sha1加密
	public String SHA1Encode(String sourceString) {
		String resultString = null;
		try {
		   resultString = new String(sourceString);
		   MessageDigest md = MessageDigest.getInstance("SHA-1");
		   resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}
	
	public final String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
		    	buf.append("0");
		   	}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}
	
	//从输入流读取post参数
	public String readStreamParameter(ServletInputStream in){
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line = reader.readLine())!=null){
				buffer.append(line);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null != reader){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}
	
}
