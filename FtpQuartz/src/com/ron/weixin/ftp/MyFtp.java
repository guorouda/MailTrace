package com.ron.weixin.ftp;

import org.apache.log4j.Logger;


public class MyFtp {
	private static Logger log = Logger.getLogger(MyFtp.class);
	
	private String ftpIp = "192.168.202.13";       
	private int ftpPort = 21;
	private String ftpUser = "jiangsu";
	private String ftpPasswd = "password";
//	private String interfaceCode[] = {"03003", "03004", "03016"};
//	private String province[][] = {{"anhui", "34"}, {"beijing", "11"}, {"chongqing", "50"}, {"fujian", "35"}, {"gansu", "62"}, {"guangdong", "44"}, {"guangxi", "45"}, {"guizhou", "52"}, {"hainan", "46"}, {"hebei", "13"}, {"heilongjiang", "23"}, {"henan", "41"}, {"hubei", "42"}, {"hunan", "43"}, {"jiangsu", "32"}, {"jiangxi", "36"}, {"jilin", "22"}, {"liaoning", "21"}, {"neimenggu", "15"}, {"ningxia", "64"}, {"qinghai", "63"}, {"shandong", "37"}, {"shang3xi", "61"}, {"shanghai", "31"}, {"shanxi", "14"}, {"sichuan", "51"}, {"tianjin", "12"}, {"xinjiang", "65"}, {"xizang", "54"}, {"yunnan", "53"}, {"zhejiang", "33"}};

	FtpDownloadClienter ftp = new FtpDownloadClienter(ftpIp, ftpPort, ftpUser, ftpPasswd);
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		log.debug("Ftp startting...");
		
		MyFtp myFtp = new MyFtp();		
		try{
	//		myFtp.ftp.openConnection();
			FtpThread ftpThread = new FtpThread(myFtp.ftp);
			for(int i = 0; i <= 30; i++){
				Thread t = new Thread(ftpThread);
				t.start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			myFtp.ftp.closeConnection();
		}
	}


}
