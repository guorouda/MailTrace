package com.ron.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ron.weixin.pereference.SystemGlobals;

public class WeixinHandler {
	private static Logger log = Logger.getLogger(WeixinHandler.class);
	
	private String TOKEN = "Guo";
	//private String mailToken = "#dqG542Sdg";
	
	private String fromUsername = null;
	private String toUsername = null;
	private String RX_TYPE = null;
	private String keyword = null;
	
	
	private String postStr = null;
	private Element root = null;
	
	private HttpServletRequest request; 
	private HttpServletResponse response;
	
	public WeixinHandler(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
		super();
		this.postStr = this.readStreamParameter(request.getInputStream());
		if(this.postStr == null || this.postStr.isEmpty() ){
			this.request = request;
		}else{
			Document document = null;
			document = DocumentHelper.parseText(this.postStr);
			
			if(null == document){
				this.print("");
				return;
			}
			
			this.root = document.getRootElement();
			this.RX_TYPE = root.elementText("MsgType");
			this.fromUsername = root.elementText("FromUserName");
			this.toUsername = root.elementText("ToUserName");
			this.keyword = root.elementText("Content");
			
			log.info(this.postStr);
			log.info(this.RX_TYPE);
		}
		
		this.response = response;
	}
	
	public void valid() throws UnsupportedEncodingException{
		log.debug("Weixin starting...");
		if (null != this.postStr && !this.postStr.isEmpty()){
			switch(this.RX_TYPE){
				case "text":
					SystemGlobals.setKeywords(this.keyword.trim());
					responseMsg(this.fromUsername, this.toUsername, this.toUsername + ":" + this.keyword + new String(" 加入跟踪.".getBytes("UTF-8"), "ISO-8859-1"));
					break;
				case "event":
					if(this.root.elementText("Event").equals("subscribe")){
						replySubscribe();
					}
					break;
			}
		}else{
			String echostr = request.getParameter("echostr");
			if(this.checkSignature()){
				this.print(echostr);
			}else{
				this.print("error");                                                                                                                                                                                                                                                                                                                                         
			}
		}
	}
	
	//subscribe自动回复内容
	public void replySubscribe() throws UnsupportedEncodingException{
	    String time = new Date().getTime() + "";
	    String textTpl = "<xml>" +
			"<ToUserName><![CDATA[%1$s]]></ToUserName>" +
			"<FromUserName><![CDATA[%2$s]]></FromUserName>" +
			"<CreateTime>%3$s</CreateTime>" +
			"<MsgType><![CDATA[%4$s]]></MsgType>" +
			"<Content><![CDATA[%5$s]]></Content>" +
			"<FuncFlag>0</FuncFlag>" +
			"</xml>";             
		
       	String msgType = "text";
       	String contentStr = "南京邮政速递物流公司欢迎您，请输入邮件号进行查询！";
       	String resultStr = String.format(textTpl, fromUsername, toUsername, time, msgType, new String(contentStr.getBytes("UTF-8"), "ISO-8859-1"));
       	this.print(resultStr);

	}
	
	public void responseMsgOne(String contentStr){
		this.responseMsg(fromUsername, toUsername, contentStr);
	}
	
	//自动回复内容
	private void responseMsg(String fromUsername, String toUsername, String contentStr){
	    String time = new Date().getTime() + "";
	    String textTpl = "<xml>" +
			"<ToUserName><![CDATA[%1$s]]></ToUserName>" +
			"<FromUserName><![CDATA[%2$s]]></FromUserName>" +
			"<CreateTime>%3$s</CreateTime>" +
			"<MsgType><![CDATA[%4$s]]></MsgType>" +
			"<Content><![CDATA[%5$s]]></Content>" +
			"<FuncFlag>0</FuncFlag>" +
			"</xml>";             
	
       	String msgType = "text";
       	String resultStr = String.format(textTpl, fromUsername, toUsername, time, msgType, contentStr);
       	this.print(resultStr);

	}
	
	//微信接口验证
	public boolean checkSignature(){
		String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        if(signature == null || timestamp == null || nonce == null){
        	return false;
        }
        log.debug(signature + "|" + timestamp + "|" + nonce);
        
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
