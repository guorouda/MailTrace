package com.ron.weixin.pereference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import com.ron.weixin.LoginWeixinWEB;
import com.ron.weixin.utility.json.JSONException;

public class Util {
	private static Logger log = Logger.getLogger(Util.class);
	
	public static String SearchFileByName(int i, String remoteFileName){
	    File file = new File("d:\\temp\\" + remoteFileName);
	    BufferedReader reader = null; 
	    
	    try { 
	    	InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");   
	    	reader = new BufferedReader(isr);	    	 
	    	String tempString = null; 
	    	String keyword = null; 
	        while ((tempString = reader.readLine()) != null){ 
	        	String[] tempStrings = tempString.split("\t");
	        	if(i==3){
	        		keyword = tempStrings[10];
	        	}else{
	        		keyword = tempStrings[1];
	        	}
//	        	log.info(keyword);
	        	if(SystemGlobals.searchKeywords(keyword)>0){
	        		log.info("find: " + tempString);
	        		return tempString;
	        	};
	        } 
	        reader.close(); 
	    } catch (IOException e) { 
	    	e.printStackTrace(); 
	    } finally { 
	    	if (reader != null){ 
		        try { 
		        	reader.close(); 
		        } catch (IOException e) { 
		        } 
	    	} 
	    } 
		return null;
	}
	
	
	public static String TimeRound(String HH, String mm){
		if(Integer.parseInt(mm)>=30){
			int hour = Integer.parseInt(HH) + 1;
			return hour==24 ? "00" : (hour+"");			
		}
		return HH;
	}

	
}
