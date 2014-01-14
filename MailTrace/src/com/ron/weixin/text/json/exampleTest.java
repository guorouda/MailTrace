/**
 * 
 */
package com.ron.weixin.text.json;

import java.io.UnsupportedEncodingException;

import com.ron.weixin.utility.json.JSONException;
import com.ron.weixin.utility.json.JSONObject;
import com.ron.weixin.utility.json.jsonManager;
import com.ron.weixin.utility.json.jsonNode;

/**
 * @author suansuan
 *
 */
public class exampleTest {

	public static void main(String args[]) throws UnsupportedEncodingException, JSONException{
		String ex="{\"Ret\": 302,\"ErrMsg\": \"/cgi-bin/home?t=home/index&lang=zh_CN&token=1795962904\",\"ShowVerifyCode\": 0,\"ErrCode\": 0}";
		
		jsonNode node=new jsonNode();
		jsonManager jm=new jsonManager();
		String str = new String(ex.getBytes(), "utf-8");
		JSONObject obj = new JSONObject(str);
		node=jm.doParse(obj);
		
		System.out.println(node.sz);
		
		
	}
	
	
	
}
