package com.ron.weixin.ftp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TestFtp {
	final static Log log = LogFactory.getLog(TestFtp.class);
	
	private String ftpIp = "192.168.202.13";       
	private int ftpPort = 21;
	private String ftpUser = "jiangsu";
	private String ftpPasswd = "password";
	private String interfaceCode[] = {"03003", "03004", "03016"};
//	private String province[][] = {{"anhui", "34"}, {"beijing", "11"}, {"chongqing", "50"}, {"fujian", "35"}, {"gansu", "62"}, {"guangdong", "44"}, {"guangxi", "45"}, {"guizhou", "52"}, {"hainan", "46"}, {"hebei", "13"}, {"heilongjiang", "23"}, {"henan", "41"}, {"hubei", "42"}, {"hunan", "43"}, {"jiangsu", "32"}, {"jiangxi", "36"}, {"jilin", "22"}, {"liaoning", "21"}, {"neimenggu", "15"}, {"ningxia", "64"}, {"qinghai", "63"}, {"shandong", "37"}, {"shang3xi", "61"}, {"shanghai", "31"}, {"shanxi", "14"}, {"sichuan", "51"}, {"tianjin", "12"}, {"xinjiang", "65"}, {"xizang", "54"}, {"yunnan", "53"}, {"zhejiang", "33"}};
	private String province[][] = {{"jiangsu", "32"}, {"beijing", "11"}, {"chongqing", "50"}, {"fujian", "35"}, {"gansu", "62"}, {"guangdong", "44"}, {"guangxi", "45"}, {"guizhou", "52"}, {"hainan", "46"}, {"hebei", "13"}, {"heilongjiang", "23"}, {"henan", "41"}, {"hubei", "42"}, {"hunan", "43"}, {"jiangsu", "32"}, {"jiangxi", "36"}, {"jilin", "22"}, {"liaoning", "21"}, {"neimenggu", "15"}, {"ningxia", "64"}, {"qinghai", "63"}, {"shandong", "37"}, {"shang3xi", "61"}, {"shanghai", "31"}, {"shanxi", "14"}, {"sichuan", "51"}, {"tianjin", "12"}, {"xinjiang", "65"}, {"xizang", "54"}, {"yunnan", "53"}, {"zhejiang", "33"}};

	FtpDownloadClienter ftp = new FtpDownloadClienter(ftpIp, ftpPort, ftpUser, ftpPasswd);
			
	
	class GetFile implements Runnable {
		private int x = 0;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (this) {
		        Date d=new Date();   
		        SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");   
		        String remoteFolderPath = "/home/" + province[x][0] + "/" +  df.format(d);
		        String remoteFileName = interfaceCode[0] + "_" + province[x][1] + "_" + df.format(d) + ".dat";    
		        
		        ftp.download(0, remoteFolderPath, remoteFileName, "d:\\Temp");
		        
		        ++x;
		        log.debug(Thread.currentThread().getName()+ "$" + x + ":" + remoteFolderPath + " : " + remoteFileName);
				
			}

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			TestFtp test = new TestFtp();
			GetFile  r = test.new GetFile();
			for(int i = 0; i <= 30; i++){
				Thread t = new Thread(r);
				t.start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
