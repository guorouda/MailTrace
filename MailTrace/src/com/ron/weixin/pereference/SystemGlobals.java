package com.ron.weixin.pereference;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;


public class SystemGlobals implements VariableStore {
	private static SystemGlobals globals = new SystemGlobals();
	
	private String defaultConfig;
	private Properties defaults = new Properties();
	
	private int fileLength = 0;
	private List<String> tempKeywords = new ArrayList<String>();
	
	private Map<String, Properties> FilesMap = new HashMap<String, Properties>();
	private Map<String, Properties> GetFilesMap = new HashMap<String, Properties>();
	private Map<String, Properties> DaySwitchMap = new HashMap<String, Properties>();
	
	private CloseableHttpClient Weixinhttpclient = null;
	private CloseableHttpClient Emshttpclient = null;
	
	public static Logger log = Logger.getLogger(SystemGlobals.class);
	
	private SystemGlobals(){}
	
	public static void initGlobals(String appPath, String mainConfigurationFile){
		globals = new SystemGlobals();
		
		globals.defaultConfig = mainConfigurationFile;
		globals.defaults = new Properties();
		
		globals.defaults.put(ConfigKeys.APPLICATION_PATH, appPath);
		globals.defaults.put(ConfigKeys.DEFAULT_CONFIG, mainConfigurationFile);
		
		SystemGlobals.loadDefaults();
		
		CookieStore WeixincookieStore = new BasicCookieStore();
		RequestConfig WeixinrequestConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
				.build();
		globals.Weixinhttpclient = HttpClients
				.custom()
				.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0")				
				.setDefaultRequestConfig(WeixinrequestConfig)
				.setDefaultCookieStore(WeixincookieStore)
				.build();
		
		CookieStore EmscookieStore = new BasicCookieStore();
		RequestConfig EmsrequestConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
				.build();
		globals.Emshttpclient = HttpClients
				.custom()
				.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0")				
				.setDefaultRequestConfig(EmsrequestConfig)
				.setDefaultCookieStore(EmscookieStore)
				.build();
		
		String[] s = SystemGlobals.getDefaultsValue("file.code").split(",");
		String[] p = SystemGlobals.getDefaultsValue("province.code").split(",");
		
		for(int i=0; i <= s.length - 1; i++){
			Properties a = new Properties();
			for(int j = 0; j <= p.length -1; j++){
				a.setProperty(p[j].split(":")[1].trim(), "0");
			}
			globals.FilesMap.put(s[i].trim(), a);
			
			Properties b = new Properties();
			for(int j = 0; j <= p.length -1; j++){
				b.setProperty(p[j].split(":")[1].trim(), "0");
			}
			globals.GetFilesMap.put(s[i].trim(), b);
			
			Properties c = new Properties();
			for(int j = 0; j <= p.length -1; j++){
				c.setProperty(p[j].split(":")[1].trim(), "0");
			}
			globals.DaySwitchMap.put(s[i].trim(), c);
		}
		
	}
	
	public static void print(){
		Iterator<Map.Entry<String, Properties>> iter = globals.DaySwitchMap.entrySet().iterator(); 
		while (iter.hasNext()) { 
			Map.Entry<String, Properties> entry = (Map.Entry<String, Properties>)iter.next(); 
			Object key = entry.getKey(); 
			Object val = entry.getValue(); 
			log.info(key);
			log.info(val.toString());
		} 
	}
	
	public static void setKeywords(String keyword){
		globals.tempKeywords.add(keyword);
	    Collections.sort(globals.tempKeywords);
	}
	
	public static int searchKeywords(String keywords){
		return Collections.binarySearch(globals.tempKeywords, keywords);
	}
	
	public static String listKeywords(){
		String keywords = "";
		Iterator<String> it = globals.tempKeywords.iterator();
		while (it.hasNext()){
			keywords += "," + it.next();
		}
		return keywords;
	}
	
	public static void setFileLength(int fileLength){
		globals.fileLength = fileLength;
	}
	
	public static int getFileLength(){
		return globals.fileLength;
	}
	
	public static void resetFileLength(){
		globals.fileLength = 0;
	}
	
	public static void setProvinceFile(String key, String provice, String fileLength){
//		globals.Files.get(index).setProperty(provice, fileLength);
		globals.GetFilesMap.get(key).setProperty(provice, fileLength);
	}

	public static String getProvinceFile(String key, String province){
		return globals.FilesMap.get(key).getProperty(province);
	}
	
	public static void setProvinceGetFile(String key, String provice, String value){
		globals.GetFilesMap.get(key).setProperty(provice, value);
	}

	public static String getProvinceGetFile(String key, String province){
		return globals.GetFilesMap.get(key).getProperty(province);
	}
	
	public static void setDaySwitch(String key, String provice, String value){
		globals.DaySwitchMap.get(key).setProperty(provice, value);
	}

	public static String getDaySwitch(String key, String province){
		log.info(key + ":" + province);
		return globals.DaySwitchMap.get(key).getProperty(province);
	}
	
	public static CloseableHttpClient getEmsHttpclient(){
		return globals.Emshttpclient;
	}
	
	public static void loadDefaults() {
		try {
			FileInputStream input = new FileInputStream(globals.defaultConfig);
			globals.defaults.load(input);
			input.close();
		} catch (IOException e) {
		}
	}
	
	public static String getDefaultsValue(String variableName){
		return globals.defaults.getProperty(variableName);
	}
	
	// TODO
	public static String getValue(String variableName){
		return SystemGlobals.getValue(variableName);
	}
	
	@Override
	public String getVariableValue(String variableName) {
		// TODO Auto-generated method stub
		return null;
	}

}
