package com.ron.weixin.pereference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class SystemGlobals implements VariableStore {
	
	private int fileLength = 0;
	private List<String> keywords = new ArrayList<String>();
	private String[] tempKeywords;
	
	private static SystemGlobals globals = new SystemGlobals();
	//private Properties provinces = new Properties();
	private static String[] interfaceCode = {"03003", "03004", "03005", "03016"}; 
	private static String province[][] = {{"anhui", "34"}, {"beijing", "11"}, {"chongqing", "50"}, {"fujian", "35"}, {"gansu", "62"}, {"guangdong", "44"}, {"guangxi", "45"}, {"guizhou", "52"}, {"hainan", "46"}, {"hebei", "13"}, {"heilongjiang", "23"}, {"henan", "41"}, {"hubei", "42"}, {"hunan", "43"}, {"jiangsu", "32"}, {"jiangxi", "36"}, {"jilin", "22"}, {"liaoning", "21"}, {"neimenggu", "15"}, {"ningxia", "64"}, {"qinghai", "63"}, {"shandong", "37"}, {"shang3xi", "61"}, {"shanghai", "31"}, {"shanxi", "14"}, {"sichuan", "51"}, {"tianjin", "12"}, {"xinjiang", "65"}, {"xizang", "54"}, {"yunnan", "53"}, {"zhejiang", "33"}};
	
	private List<Properties> Files = new ArrayList<Properties>();
	
	private SystemGlobals(){}
	
	public static void initGlobals(){
		globals = new SystemGlobals();
		
		for(int i=0; i <= interfaceCode.length - 1; i++){
			Properties a = new Properties();
			for(int j = 0; j<= province.length - 1; j++){
				a.setProperty(province[j][1], "0");
			}
			globals.Files.add(a);
		}
		
		globals.setKeywords();
	}
	
	private void setKeywords(){
		globals.keywords.add("5068885154501");
		globals.keywords.add("1080388462500");
		globals.keywords.add("1046484508605");
		globals.keywords.add("1046154508605");
		globals.keywords.add("1046186508605");
		globals.tempKeywords = new String[globals.keywords.size()];
	    globals.keywords.toArray(tempKeywords);
	    Arrays.sort(globals.tempKeywords);
	}
	
	public static int searchKeywords(String keywords){
		return Arrays.binarySearch(globals.tempKeywords, keywords);
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
	
	public static void setProvince(int index, String provice, String fileLength){
		globals.Files.get(index).setProperty(provice, fileLength);
	}

	public static String getProvince(int index, String province){
		return globals.Files.get(index).getProperty(province);
	}
	
	@Override
	public String getVariableValue(String variableName) {
		// TODO Auto-generated method stub
		return null;
	}

}
