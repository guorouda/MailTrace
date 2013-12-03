package com.ron.weixin.pereference;

import java.util.Properties;


public class SystemGlobals implements VariableStore {
	
	private static SystemGlobals globals = new SystemGlobals();
	private Properties provinces = new Properties();
	private int fileLength = 0;
	private static String province[][] = {{"anhui", "34"}, {"beijing", "11"}, {"chongqing", "50"}, {"fujian", "35"}, {"gansu", "62"}, {"guangdong", "44"}, {"guangxi", "45"}, {"guizhou", "52"}, {"hainan", "46"}, {"hebei", "13"}, {"heilongjiang", "23"}, {"henan", "41"}, {"hubei", "42"}, {"hunan", "43"}, {"jiangsu", "32"}, {"jiangxi", "36"}, {"jilin", "22"}, {"liaoning", "21"}, {"neimenggu", "15"}, {"ningxia", "64"}, {"qinghai", "63"}, {"shandong", "37"}, {"shang3xi", "61"}, {"shanghai", "31"}, {"shanxi", "14"}, {"sichuan", "51"}, {"tianjin", "12"}, {"xinjiang", "65"}, {"xizang", "54"}, {"yunnan", "53"}, {"zhejiang", "33"}};
	
	private SystemGlobals(){}
	
	public static void initGlobals(){
		globals = new SystemGlobals();
		for(int i = 0; i<= 30; i++){
			globals.provinces.setProperty(province[i][1], "0");
		}
	}
	
	public static void setFileLength(int fileLength){
		globals.fileLength = fileLength;
	}
	
	public static int getFileLength(){
		return globals.fileLength;
	}
	
	public static void setProvince(String provice, String fileLength){
		globals.provinces.setProperty(provice, fileLength);
	}

	public static String getProvince(String province){
		return globals.provinces.getProperty(province);
	}
	
	@Override
	public String getVariableValue(String variableName) {
		// TODO Auto-generated method stub
		return null;
	}

}
