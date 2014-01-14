package com.ron.weixin.pereference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class Util {
	private static Logger log = Logger.getLogger(Util.class);
	
	public static String SearchFileByName(String remoteFileName){
	    File file = new File(SystemGlobals.getDefaultsValue("download.path") + remoteFileName);
	    BufferedReader reader = null; 
	    
	    try { 
	    	String[] rfn = remoteFileName.split("_");
	    	InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");   
	    	reader = new BufferedReader(isr);	    	 
	    	String tempString = null; 
	    	String keyword = null; 
	        while ((tempString = reader.readLine()) != null){ 
	        	String[] tempStrings = tempString.split("\t");
	        	switch(rfn[0]){
	        		case "03003":
	        			keyword = tempStrings[1];
	        			break;
	        		case "03004":
	        			keyword = tempStrings[1];
	        			break;
	        		case "03005":
	        			keyword = tempStrings[1];
	        			break;
	        		case "03016":
	        			keyword = tempStrings[10];
	        			break;
	        	}
//	        	log.info("keyword: " + keyword);
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
