package com.ron.weixin.pereference;


public class SystemGlobals implements VariableStore {
	
	private static SystemGlobals globals = new SystemGlobals();
	private int fileLength = 0;
	
	private SystemGlobals(){}
	
//	public static void initGlobals(){
//		SystemGlobals globals = new SystemGlobals();
//	}
	
	public static void setFileLength(int fileLength){
		globals.fileLength = fileLength;
	}
	
	public static int getFileLength(){
		return globals.fileLength;
	}

	@Override
	public String getVariableValue(String variableName) {
		// TODO Auto-generated method stub
		return null;
	}

}
