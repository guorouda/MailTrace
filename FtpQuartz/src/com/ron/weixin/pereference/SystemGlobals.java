package com.ron.weixin.pereference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class SystemGlobals implements VariableStore {
	private static SystemGlobals globals = new SystemGlobals();
	
	private int fileLength = 0;
	List<String> tempKeywords = new ArrayList<String>();
	
	private static String[] interfaceCode = {"03003", "03004", "03005", "03016"}; 
	private static String province[][] = {{"anhui", "34"}, {"beijing", "11"}, {"chongqing", "50"}, {"fujian", "35"}, {"gansu", "62"}, {"guangdong", "44"}, {"guangxi", "45"}, {"guizhou", "52"}, {"hainan", "46"}, {"hebei", "13"}, {"heilongjiang", "23"}, {"henan", "41"}, {"hubei", "42"}, {"hunan", "43"}, {"jiangsu", "32"}, {"jiangxi", "36"}, {"jilin", "22"}, {"liaoning", "21"}, {"neimenggu", "15"}, {"ningxia", "64"}, {"qinghai", "63"}, {"shandong", "37"}, {"shang3xi", "61"}, {"shanghai", "31"}, {"shanxi", "14"}, {"sichuan", "51"}, {"tianjin", "12"}, {"xinjiang", "65"}, {"xizang", "54"}, {"yunnan", "53"}, {"zhejiang", "33"}};
	private List<Properties> Files = new ArrayList<Properties>();
	private List<Properties> GetFile = new ArrayList<Properties>();
	private List<Properties> DaySwitch = new ArrayList<Properties>();
	
	private SystemGlobals(){}
	
	public static void initGlobals(){
		globals = new SystemGlobals();
		
		for(int i=0; i <= interfaceCode.length - 1; i++){
			Properties a = new Properties();
			for(int j = 0; j<= province.length - 1; j++){
				a.setProperty(province[j][1], "0");
			}
			globals.Files.add(a);
			
			Properties b = new Properties();
			for(int j = 0; j<= province.length - 1; j++){
				b.setProperty(province[j][1], "0");
			}
			globals.GetFile.add(b);
			
			Properties c = new Properties();
			for(int j = 0; j<= province.length - 1; j++){
				c.setProperty(province[j][1], "0");
			}
			globals.DaySwitch.add(c);
			
		}
		
		globals.tempKeywords.add("1153213584700");
		Collections.sort(globals.tempKeywords);
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
	
	public static void setProvinceFile(int index, String provice, String fileLength){
		globals.Files.get(index).setProperty(provice, fileLength);
	}

	public static String getProvinceFile(int index, String province){
		return globals.Files.get(index).getProperty(province);
	}
	
	
	public static void setProvinceGetFile(int index, String provice, String value){
		globals.GetFile.get(index).setProperty(provice, value);
	}

	public static String getProvinceGetFile(int index, String province){
		return globals.GetFile.get(index).getProperty(province);
	}
	
	public static void setDaySwitch(int index, String provice, String value){
		globals.DaySwitch.get(index).setProperty(provice, value);
	}

	public static String getDaySwitch(int index, String province){
		return globals.DaySwitch.get(index).getProperty(province);
	}
	
	@Override
	public String getVariableValue(String variableName) {
		// TODO Auto-generated method stub
		return null;
	}

}
