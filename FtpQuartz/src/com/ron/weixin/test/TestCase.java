package com.ron.weixin.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ron.weixin.ftp.FtpDownloadClienter;
import com.ron.weixin.pereference.SystemGlobals;

public class TestCase {
	private static Logger log = Logger.getLogger(TestCase.class);
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFtp(){
        String ftpIp = "192.168.202.13";       
        int ftpPort = 21;
        String ftpUser = "jiangsu";
        String ftpPasswd = "password";
        
        String province[] = {"anhui", "beijing", "chongqing", "fujian", "gansu", "guangdong", "guangxi", "guizhou", "hainan", "hebei", 
        					"heilongjiang", "henan", "hubei", "hunan", "jiangsu", "jiangxi", "jilin", "liaoning", "neimenggu", "ningxia", 
        					"qinghai", "shandong", "shang3xi", "shanghai", "shanxi", "sichuan", "tianjin", "xinjiang", "xizang", "yunnan", 
        					"zhejiang"};
        
        
        Date d=new Date();   
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");   
       
        FtpDownloadClienter ftp = new FtpDownloadClienter( ftpIp, ftpPort, ftpUser, ftpPasswd);
        String remoteFolderPath = "/home/" + province[14] + "/" +  df.format(d);
        String remoteFileName = "03003_32_" +df.format(d)+".dat";    
        log.debug(remoteFolderPath);
        log.debug(remoteFileName);
        ftp.download(0, remoteFolderPath, remoteFileName, "d:\\Temp");
        log.info("complete: " + SystemGlobals.getFileLength());
	}
	
	
	

}
