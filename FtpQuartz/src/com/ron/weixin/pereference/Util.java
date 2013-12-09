package com.ron.weixin.pereference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class Util {
	private static Logger log = Logger.getLogger(Util.class);
	
	public static void SearchFileByName(String remoteFileName){
	    File file = new File("d:\\temp\\" + remoteFileName);
	    BufferedReader reader = null; 
	    
	    try { 
	    	reader = new BufferedReader(new FileReader(file)); 
	    	String tempString = null; 
	        while ((tempString = reader.readLine()) != null){ 
	     //   	log.info(tempString);
	        	if(SystemGlobals.searchKeywords(tempString.substring(2, 15))>0){
	        		log.info("find: " + tempString);
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
		
	}
	
	
	public static String TimeRound(String HH, String mm){
		if(Integer.parseInt(mm)>=30){
			return (Integer.parseInt(HH) + 1) + "";			
		}
		
		return HH;
	}
	
}
